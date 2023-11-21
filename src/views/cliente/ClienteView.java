package views.cliente;

import javax.swing.*;

import control.Dados;

import java.util.List;

import model.cliente.Cliente;
import model.locacao.Locacao;
import model.table.ClienteTableModel;
import model.veiculo.estado.Estado;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClienteView extends JFrame {

    private ClienteTableModel tableModel;

    private JTextField cpfField;
    private JTextField rgField;
    private JTextField nomeField;
    private JTextField sobrenomeField;
    private JTextField enderecoField;
    
    private List<Cliente> listaClientes;
    private List<Locacao> listaLocacao;

    public ClienteView() {
        super("Cadastro de Clientes");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 300);
        setLocationRelativeTo(null);

        tableModel = new ClienteTableModel();

        JTable table = new JTable(tableModel);

        cpfField = new JTextField(10);
        rgField = new JTextField(10);
        nomeField = new JTextField(10);
        sobrenomeField = new JTextField(10);
        enderecoField = new JTextField(20);
        listaClientes = Dados.getClientes();
        
        listaClientes = Dados.getClientes();
        listaLocacao = Dados.getLocacoes();
        tableModel.setClientes(listaClientes);

        JButton addButton = new JButton("Adicionar");
        JButton editButton = new JButton("Editar");
        JButton deleteButton = new JButton("Excluir");

        JToolBar toolBar = new JToolBar();
        toolBar.add(addButton);
        toolBar.add(editButton);
        toolBar.add(deleteButton);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarCliente();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarCliente(table.getSelectedRow());
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirCliente(table.getSelectedRow());
            }
        });

        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.add(new JLabel("CPF:"));
        inputPanel.add(cpfField);
        inputPanel.add(new JLabel("RG:"));
        inputPanel.add(rgField);
        inputPanel.add(new JLabel("Nome:"));
        inputPanel.add(nomeField);
        inputPanel.add(new JLabel("Sobrenome:"));
        inputPanel.add(sobrenomeField);
        inputPanel.add(new JLabel("Endereço:"));
        inputPanel.add(enderecoField);


        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(toolBar, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(table), BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private void adicionarCliente() {
        ClienteDialog dialog = new ClienteDialog(this, "Adicionar Cliente", null);
        Cliente cliente = dialog.showDialog();

        if (cliente != null) {
            listaClientes.add(cliente);

            tableModel.setClientes(listaClientes);
            tableModel.fireTableDataChanged();
        }
    }

    private void editarCliente(int selectedRow) {
        if (selectedRow != -1) {
            Cliente cliente = tableModel.getClienteAt(selectedRow);
            ClienteDialog dialog = new ClienteDialog(this, "Editar Cliente", cliente);
            Cliente clienteAtualizado = dialog.showDialog();

            if (clienteAtualizado != null) {
                listaClientes.set(selectedRow, clienteAtualizado);

                tableModel.setClientes(listaClientes);
                tableModel.fireTableDataChanged();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente(int selectedRow) {
        if (selectedRow != -1) {
        	Cliente clienteRemovido = tableModel.getClienteAt(selectedRow);
        	for(Locacao locacao : listaLocacao) {
        		if(locacao.getCliente().equals(clienteRemovido) && locacao.getVeiculo().getEstado() == Estado.LOCADO) {
        			JOptionPane.showMessageDialog(this, "O Cliente não pode ser removido pois possui pelo menos uma locação.", "Erro", JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        	}
        	listaClientes.remove(clienteRemovido);

            tableModel.setClientes(listaClientes);
            tableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ClienteView();
            }
        });
    }
}
