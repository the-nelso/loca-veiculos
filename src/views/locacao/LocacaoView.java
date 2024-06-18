package views.locacao;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
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
import javax.swing.JTextField;

import controller.cliente.ClienteController;
import controller.locacao.LocacaoController;
import controller.veiculo.VeiculoController;
import model.cliente.Cliente;
import model.locacao.Locacao;
import model.table.VeiculoTableModel;
import model.veiculo.Veiculo;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;

public class LocacaoView extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField filtroClienteField;
	private JComboBox<String> tipoVeiculoComboBox;
	private JComboBox<String> marcaComboBox;
	private JComboBox<String> categoriaComboBox;
	private JTextField numeroDiasField;
	private JTable veiculosTable;
	private VeiculoTableModel veiculoTableModel;

	private List<Veiculo> listaVeiculos;
	private List<Cliente> listaClientes;
	private List<Locacao> listaLocacoes;

	private LocacaoController locacaoController;
	private ClienteController clienteController;
	private VeiculoController veiculoController;

	public LocacaoView(LocacaoController locacaoController, ClienteController clienteController,
			VeiculoController veiculoController) {
		this.locacaoController = locacaoController;
		this.clienteController = clienteController;
		this.veiculoController = veiculoController;

		setTitle("Locação de Veículos");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLayout(new BorderLayout());

		filtroClienteField = new JTextField();
		tipoVeiculoComboBox = new JComboBox<>(new String[] { "", "Automovel", "Van", "Motocicleta" });
		marcaComboBox = new JComboBox<>();
		marcaComboBox.addItem("");
		for (Marca marca : Marca.values()) {
			marcaComboBox.addItem(marca.name());
		}
		categoriaComboBox = new JComboBox<>(new String[] { "", "POPULAR", "INTERMEDIARIO", "LUXO" });
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
				updateTable();
			}
		});

		marcaComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateTable();
			}
		});

		categoriaComboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		Cliente clienteSelecionado = getClienteSelecionado();

		if (selectedRow >= 0) {
			if (filtroClienteField.getText() != null && numeroDiasField.getText() != null
					&& clienteSelecionado != null) {
				try {
					Veiculo veiculoSelecionado = listaVeiculos.get(selectedRow);

					int numeroDias = Integer.parseInt(numeroDiasField.getText());
					Locacao locacao = new Locacao(null, numeroDias,
							numeroDias * veiculoSelecionado.getValorDiariaLocacao(), currentDate, clienteSelecionado,
							veiculoSelecionado);
					locacaoController.adicionarLocacao(locacao);
					veiculoSelecionado.locar(locacao.getId(), numeroDias, currentDate, clienteSelecionado);
					veiculoController.atualizarVeiculo(veiculoSelecionado);
					updateTable();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this, "Preencha os campos de cliente e número de dias.", "Erro",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(this, "Não foi encontrado um cliente.", "Erro",
						JOptionPane.ERROR_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(this, "Selecione um veículo para locar.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
	}

	private Cliente getClienteSelecionado() {
		String campoFiltrado = filtroClienteField.getText().trim().toLowerCase();

		List<Cliente> clientes = clienteController.obterListaClientes();

		for (Cliente cliente : clientes) {
			String nomeCliente = cliente.getNome().toLowerCase().trim();
			String cpfCliente = cliente.getCpf().toLowerCase().trim();
			String sobrenomeCliente = cliente.getSobrenome().toLowerCase().trim();
			if (nomeCliente.equalsIgnoreCase(campoFiltrado) || cpfCliente.equalsIgnoreCase(campoFiltrado)
					|| sobrenomeCliente.equalsIgnoreCase(campoFiltrado)) {
				return cliente;
			}
		}

		return null;
	}

	private void updateTable() {
		this.listaVeiculos = veiculoController.listarVeiculosDisponiveis();
		this.listaLocacoes = locacaoController.listarLocacoes();
		this.listaClientes = clienteController.obterListaClientes();

		String categoriaSelecionada = categoriaComboBox.getSelectedItem().toString();
		String marcaSelecionada = marcaComboBox.getSelectedItem().toString();
		String tipoSelecionado = tipoVeiculoComboBox.getSelectedItem().toString();

		List<Veiculo> veiculosDisponiveis = listaVeiculos.stream()
				.filter(veiculo -> veiculo.getEstado() != Estado.LOCADO && veiculo.getEstado() != Estado.VENDIDO)
				.filter(veiculo -> categoriaSelecionada.isEmpty()
						|| veiculo.getCategoria().toString().equals(categoriaSelecionada))
				.filter(veiculo -> marcaSelecionada.isEmpty() || veiculo.getMarca().toString().equals(marcaSelecionada))
				.filter(veiculo -> tipoSelecionado.isEmpty() || veiculo.getTipo().equals(tipoSelecionado))
				.collect(Collectors.toList());

		veiculoTableModel.setVeiculos(veiculosDisponiveis);
		veiculosTable.setModel(veiculoTableModel);
	}
}
