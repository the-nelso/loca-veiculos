package views.veiculo;

import javax.swing.*;
import javax.swing.text.MaskFormatter;

import control.Dados;
import model.veiculo.Veiculo;
import model.veiculo.automovel.Automovel;
import model.veiculo.automovel.modelo.ModeloAutomovel;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;
import model.veiculo.motocicleta.Motocicleta;
import model.veiculo.motocicleta.modelo.ModeloMotocicleta;
import model.veiculo.van.Van;
import model.veiculo.van.modelo.ModeloVan;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class VeiculoView extends JFrame {
	
	private JComboBox<String> tipoComboBox;
    private JComboBox<Marca> marcaComboBox;
    private JComboBox<Estado> estadoComboBox;
    private JComboBox<Categoria> categoriaComboBox;
    private JComboBox<String> modeloComboBox;
    private JFormattedTextField valorDeCompraField;
    private JFormattedTextField placaField;
    private List<Veiculo> listaVeiculos;

    public VeiculoView() {
        super("Cadastro de Veículos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        
        listaVeiculos = Dados.getVeiculos();
        tipoComboBox = new JComboBox<>();
        tipoComboBox.addItem("Automovel");
        tipoComboBox.addItem("Motocicleta");
        tipoComboBox.addItem("Van");
        marcaComboBox = new JComboBox<>(Marca.values());
        estadoComboBox = new JComboBox<>(Estado.values());
        categoriaComboBox = new JComboBox<>(Categoria.values());
        modeloComboBox = new JComboBox<>();

        tipoComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarModeloComboBox();
            }
        });

        atualizarModeloComboBox();

        try {
            MaskFormatter mascaraMonetaria = new MaskFormatter("R$###.###,##");
            valorDeCompraField = new JFormattedTextField(mascaraMonetaria);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            MaskFormatter mascaraPlaca = new MaskFormatter("UUU-####");
            placaField = new JFormattedTextField(mascaraPlaca);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        JButton incluirVeiculoButton = new JButton("Incluir Veículo");
        incluirVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                incluirVeiculo();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Tipo do Veículo:"));
        inputPanel.add(tipoComboBox);
        inputPanel.add(new JLabel("Marca:"));
        inputPanel.add(marcaComboBox);
        inputPanel.add(new JLabel("Estado:"));
        inputPanel.add(estadoComboBox);
        inputPanel.add(new JLabel("Categoria:"));
        inputPanel.add(categoriaComboBox);
        inputPanel.add(new JLabel("Modelo:"));
        inputPanel.add(modeloComboBox);
        inputPanel.add(new JLabel("Valor de Compra:"));
        inputPanel.add(valorDeCompraField);
        inputPanel.add(new JLabel("Placa:"));
        inputPanel.add(placaField);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        mainPanel.add(incluirVeiculoButton, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private void incluirVeiculo() {
        Marca marca = (Marca) marcaComboBox.getSelectedItem();
        Estado estado = (Estado) estadoComboBox.getSelectedItem();
        Categoria categoria = (Categoria) categoriaComboBox.getSelectedItem();
        String modelo = (String) modeloComboBox.getSelectedItem();
        double valorDeCompra = Double.parseDouble(valorDeCompraField.getText().replaceAll("[^\\d.]", ""));
        String placa = placaField.getText();
        String tipo = tipoComboBox.getSelectedItem().toString();
        
        Veiculo veiculo;
        
        
        if (tipo.equals("Automovel")) {
            ModeloAutomovel modeloAutomovel = ModeloAutomovel.valueOf(modelo);
            veiculo = new Automovel(estado, marca, null, categoria, valorDeCompra, placa, 2023, modeloAutomovel);
        } else if (tipo.equals("Motocicleta")) {
            ModeloMotocicleta modeloMotocicleta = ModeloMotocicleta.valueOf(modelo);
            veiculo = new Motocicleta(estado, marca, null, categoria, valorDeCompra, placa, 2023, modeloMotocicleta);
        } else if (tipo.equals("Van")) {
        	ModeloVan modeloVan = ModeloVan.valueOf(modelo);
        	veiculo = new Van(estado, marca, null, categoria, valorDeCompra, placa, 2023, modeloVan);
        } else {
            veiculo = null;
        }

        if (veiculo != null) {
        	listaVeiculos.add(veiculo);
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao criar veículo", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void atualizarModeloComboBox() {
        String tipoSelecionado = tipoComboBox.getSelectedItem().toString();

        modeloComboBox.removeAllItems();

        for(ModeloAutomovel mod : ModeloAutomovel.values()) {
        	if(mod.getTipo().toString().equals(tipoSelecionado)) {
        		modeloComboBox.addItem(mod.name());
        	}
        }
        for(ModeloMotocicleta mod : ModeloMotocicleta.values()) {
        	if(mod.getTipo().toString().equals(tipoSelecionado)) {
        		modeloComboBox.addItem(mod.name());
        	}
        }
        for(ModeloVan mod : ModeloVan.values()) {
        	if(mod.getTipo().toString().equals(tipoSelecionado)) {
        		modeloComboBox.addItem(mod.name());
        	}
        }
    }

    private void limparCampos() {
        marcaComboBox.setSelectedIndex(0);
        estadoComboBox.setSelectedIndex(0);
        categoriaComboBox.setSelectedIndex(0);
        modeloComboBox.setSelectedIndex(0);
        valorDeCompraField.setValue(null);
        placaField.setValue(null);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new VeiculoView();
            }
        });
    }
}
