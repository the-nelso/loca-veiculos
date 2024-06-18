package views.devolucao;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.devolucao.DevolucaoController;
import model.table.VeiculoTableModel;
import model.veiculo.Veiculo;
import model.veiculo.estado.Estado;

public class DevolucaoView extends JFrame {
    private JTable veiculoTable;
    private List<Veiculo> listaVeiculos;
    private VeiculoTableModel veiculoTableModel;

    private DevolucaoController devolucaoController;
    
    public DevolucaoView(DevolucaoController devolucaoController) {
    	this.devolucaoController = devolucaoController;
    	
        setTitle("Devolução de Veículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        veiculoTable = new JTable();
        veiculoTableModel = new VeiculoTableModel();

        add(new JScrollPane(veiculoTable), BorderLayout.CENTER);

        JButton devolverVeiculoButton = new JButton("Devolver Veículo");
        devolverVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                devolverVeiculo();
            }
        });

        add(devolverVeiculoButton, BorderLayout.SOUTH);

        updateTable();

        setVisible(true);
    }

    private void devolverVeiculo() {
        int selectedRow = veiculoTable.getSelectedRow();

        if (selectedRow >= 0) {
            Veiculo veiculoSelecionado = veiculoTableModel.getVeiculo(selectedRow);
            veiculoSelecionado.devolver();
            devolucaoController.devolverVeiculo(veiculoSelecionado);
            
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione uma locação para devolver veículo.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        listaVeiculos = devolucaoController.getVeiculosLocados();

        List<Veiculo> veiculosDisponiveis = listaVeiculos.stream()
                .filter(veiculo -> veiculo.getEstado() == Estado.LOCADO)
                .collect(Collectors.toList());

        veiculoTableModel.setVeiculos(veiculosDisponiveis);
        veiculoTable.setModel(veiculoTableModel);
    }
}

