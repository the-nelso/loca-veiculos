package views.veiculo;

import model.table.VeiculoTableModel;
import model.veiculo.Veiculo;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;

import javax.swing.*;

import control.Dados;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VendaVeiculoView extends JFrame {
    private JComboBox<String> tipoVeiculoComboBox;
    private JComboBox<Marca> marcaComboBox;
    private JComboBox<String> categoriaComboBox;
    private JTable veiculosTable;
    private VeiculoTableModel veiculoTableModel;

    private List<Veiculo> listaVeiculos;

    public VendaVeiculoView() {
        setTitle("Venda de Veículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tipoVeiculoComboBox = new JComboBox<>(new String[]{"Automóvel", "Van", "Motocicleta"});
        marcaComboBox = new JComboBox<>(Marca.values());
        categoriaComboBox = new JComboBox<>(new String[]{"POPULAR", "INTERMEDIARIO", "LUXO"});
        veiculosTable = new JTable();
        veiculoTableModel = new VeiculoTableModel();
        listaVeiculos = Dados.getVeiculos();

        JPanel filtroPanel = new JPanel(new FlowLayout());
        filtroPanel.add(new JLabel("Tipo Veículo:"));
        filtroPanel.add(tipoVeiculoComboBox);
        filtroPanel.add(new JLabel("Marca:"));
        filtroPanel.add(marcaComboBox);
        filtroPanel.add(new JLabel("Categoria:"));
        filtroPanel.add(categoriaComboBox);

        add(filtroPanel, BorderLayout.NORTH);
        add(new JScrollPane(veiculosTable), BorderLayout.CENTER);

        JButton venderVeiculoButton = new JButton("Vender Veículo");
        venderVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                venderVeiculo();
            }
        });

        add(venderVeiculoButton, BorderLayout.SOUTH);

        updateTable();

        setVisible(true);
    }

    private void venderVeiculo() {
        int selectedRow = veiculosTable.getSelectedRow();
        if (selectedRow >= 0) {
            Veiculo veiculoSelecionado = veiculoTableModel.getVeiculo(selectedRow);

            veiculoSelecionado.vender();

            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para vender.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        listaVeiculos = Dados.getVeiculos();

        String categoriaSelecionada = categoriaComboBox.getSelectedItem().toString();
        String marcaSelecionada = marcaComboBox.getSelectedItem().toString();
        String tipoSelecionado = tipoVeiculoComboBox.getSelectedItem().toString();
        
        List<Veiculo> veiculosDisponiveis = listaVeiculos.stream()
                .filter(veiculo -> veiculo.getEstado() != Estado.LOCADO && veiculo.getEstado() != Estado.VENDIDO)
                .filter(veiculo -> categoriaSelecionada.isEmpty() || veiculo.getCategoria().toString().equals(categoriaSelecionada))
                .filter(veiculo -> marcaSelecionada.isEmpty() || veiculo.getMarca().toString().equals(marcaSelecionada))
                .filter(veiculo -> tipoSelecionado.isEmpty() || veiculo.getTipo().equals(tipoSelecionado))
                .collect(Collectors.toList());

        veiculoTableModel.setVeiculos(veiculosDisponiveis);
        veiculosTable.setModel(veiculoTableModel);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VendaVeiculoView();
            }
        });
    }
}
