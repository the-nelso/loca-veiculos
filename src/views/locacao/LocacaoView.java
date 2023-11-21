package views.locacao;

import javax.swing.*;

import control.Dados;
import model.cliente.Cliente;
import model.locacao.Locacao;
import model.table.VeiculoTableModel;
import model.veiculo.Veiculo;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class LocacaoView extends JFrame {
    private JTextField filtroClienteField;
    private JComboBox<String> tipoVeiculoComboBox;
    private JComboBox<Marca> marcaComboBox;
    private JComboBox<String> categoriaComboBox;
    private JTextField numeroDiasField;
    private JTable veiculosTable;
    private VeiculoTableModel veiculoTableModel;

    private List<Veiculo> listaVeiculos;
    private List<Cliente> listaClientes;
    private List<Locacao> listaLocacoes;
    
    public LocacaoView() {
        setTitle("Locação de Veículos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        filtroClienteField = new JTextField();
        tipoVeiculoComboBox = new JComboBox<>(new String[]{"" ,"Automovel", "Van", "Motocicleta"});
        marcaComboBox = new JComboBox<>(Marca.values());
        categoriaComboBox = new JComboBox<>(new String[]{"", "POPULAR", "INTERMEDIARIO", "LUXO"});
        numeroDiasField = new JTextField();
        veiculosTable = new JTable();
        veiculoTableModel = new VeiculoTableModel();
        
        filtroClienteField.setPreferredSize(new Dimension(150, filtroClienteField.getPreferredSize().height));
        numeroDiasField.setPreferredSize(new Dimension(50, numeroDiasField.getPreferredSize().height));

        JPanel filtroPanel = new JPanel(new FlowLayout());
        filtroPanel.add(new JLabel("Filtrar Cliente:"));
        filtroPanel.add(filtroClienteField);
        filtroPanel.add(new JLabel("Tipo Veículo:"));
        filtroPanel.add(tipoVeiculoComboBox);
        filtroPanel.add(new JLabel("Marca:"));
        filtroPanel.add(marcaComboBox);
        filtroPanel.add(new JLabel("Categoria:"));
        filtroPanel.add(categoriaComboBox);
        filtroPanel.add(new JLabel("Número de Dias:"));
        filtroPanel.add(numeroDiasField);

        add(filtroPanel, BorderLayout.NORTH);
        add(new JScrollPane(veiculosTable), BorderLayout.CENTER);

        JButton locarButton = new JButton("Locar");
        locarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                locarVeiculo();
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
        
        add(locarButton, BorderLayout.SOUTH);

        updateTable();

        setVisible(true);
    }

    private void locarVeiculo() {
        int selectedRow = veiculosTable.getSelectedRow();
        LocalDate currentDate = LocalDate.now();

        Calendar calendar = Calendar.getInstance();
        calendar.set(currentDate.getYear(), currentDate.getMonthValue() - 1, currentDate.getDayOfMonth());
        Cliente clienteSelecionado = getClienteSelecionado();
        
        if (selectedRow >= 0) {
        	if(filtroClienteField.getText() != null && numeroDiasField.getText() != null && clienteSelecionado != null) {
        		try {
        			Veiculo veiculoSelecionado = listaVeiculos.get(selectedRow);
                    
                    int numeroDias = Integer.parseInt(numeroDiasField.getText());
                    Locacao locacao = new Locacao(numeroDias, numeroDias*veiculoSelecionado.getValorDiariaLocacao(), calendar, clienteSelecionado, veiculoSelecionado);
                    listaLocacoes.add(locacao);
                    veiculoSelecionado.locar(numeroDias, calendar, clienteSelecionado);
                    updateTable();
        		}catch(Exception e) {
        			JOptionPane.showMessageDialog(this, "Preencha os campos de cliente e numero de dias.", "Erro", JOptionPane.ERROR_MESSAGE);
        		}
        		
        	}else {
        		JOptionPane.showMessageDialog(this, "Não foi encontrado um cliente.", "Erro", JOptionPane.ERROR_MESSAGE);
        	}
            
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um veículo para locar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Cliente getClienteSelecionado() {
        String campoFiltrado = filtroClienteField.getText().trim().toLowerCase();

        for (Cliente cliente : listaClientes) {
            String nomeCliente = cliente.getNome().toLowerCase();
            String cpfCliente = cliente.getCpf().toLowerCase();
            String sobrenomeCliente = cliente.getSobrenome().toLowerCase();
            if (nomeCliente.equalsIgnoreCase(campoFiltrado)) {
                return cliente;
            } else if(cpfCliente.equalsIgnoreCase(campoFiltrado)) {
            	return cliente;
            } else if(sobrenomeCliente.equalsIgnoreCase(campoFiltrado)) {
            	return cliente;
            }
        }

        return null;
    }


    private void updateTable() {
    	listaVeiculos = Dados.getVeiculos();
        listaClientes = Dados.getClientes();
        listaLocacoes = Dados.getLocacoes();

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
    
    public static void main(String [] args) {
    	SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LocacaoView();
            }
        });
    }
}

