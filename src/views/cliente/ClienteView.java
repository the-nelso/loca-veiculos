package views.cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import controller.cliente.ClienteController;
import model.cliente.Cliente;
import model.table.ClienteTableModel;

public class ClienteView extends JFrame {

    private ClienteTableModel tableModel;
    private JTextField cpfField;
    private JTextField rgField;
    private JTextField nomeField;
    private JTextField sobrenomeField;
    private JTextField enderecoField;
    private ClienteController controller;

    public ClienteView(ClienteController controller) {
        super("Cadastro de Clientes");
        this.controller = controller;

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

        tableModel.setClientes(controller.obterListaClientes());

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
            controller.adicionarCliente(cliente);
            tableModel.setClientes(controller.obterListaClientes());
            tableModel.fireTableDataChanged();
        }
    }

    private void editarCliente(int selectedRow) {
        if (selectedRow != -1) {
            Cliente cliente = tableModel.getClienteAt(selectedRow);
            ClienteDialog dialog = new ClienteDialog(this, "Editar Cliente", cliente);
            Cliente clienteAtualizado = dialog.showDialog();

            if (clienteAtualizado != null) {
                clienteAtualizado.setId(cliente.getId());  // Manter o ID do cliente original
                controller.atualizarCliente(clienteAtualizado);
                tableModel.setClientes(controller.obterListaClientes());
                tableModel.fireTableDataChanged();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para editar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void excluirCliente(int selectedRow) {
        if (selectedRow != -1) {
            Cliente cliente = tableModel.getClienteAt(selectedRow);
            controller.excluirCliente(cliente);
            tableModel.setClientes(controller.obterListaClientes());
            tableModel.fireTableDataChanged();
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cliente para excluir.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
