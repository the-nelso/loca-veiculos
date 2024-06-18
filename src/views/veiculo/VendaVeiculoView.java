package views.veiculo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.veiculo.VendaVeiculoController;
import model.table.VeiculoTableModel;
import model.veiculo.Veiculo;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;

public class VendaVeiculoView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1623522177289908752L;
	private JComboBox<String> tipoVeiculoComboBox;
    private JComboBox<Marca> marcaComboBox;
    private JComboBox<String> categoriaComboBox;
    private JTable veiculosTable;
    private VeiculoTableModel veiculoTableModel;

    private List<Veiculo> listaVeiculos;
    
    private VendaVeiculoController vendaVeiculoController;
    
    public VendaVeiculoView(VendaVeiculoController vendaVeiculoController) {
    	this.vendaVeiculoController = vendaVeiculoController;
    	
        setTitle("Venda de Veículos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        tipoVeiculoComboBox = new JComboBox<>(new String[]{"Automovel", "Van", "Motocicleta"});
        marcaComboBox = new JComboBox<>(Marca.values());
        categoriaComboBox = new JComboBox<>(new String[]{"POPULAR", "INTERMEDIARIO", "LUXO"});
        veiculosTable = new JTable();
        veiculoTableModel = new VeiculoTableModel();
        listaVeiculos = vendaVeiculoController.getVeiculosDisponiveis();

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
        
        tipoVeiculoComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateTable();
			}
		});
        
        marcaComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateTable();
			}
		});
        
        categoriaComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				updateTable();
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
            
            vendaVeiculoController.venderVeiculo(veiculoSelecionado);
            
            updateTable();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para vender.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable() {
        listaVeiculos = vendaVeiculoController.getVeiculosDisponiveis();

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
}
