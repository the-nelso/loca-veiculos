package views.cliente;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.cliente.Cliente;

public class ClienteDialog extends JDialog {

    private JTextField cpfField;
    private JTextField rgField;
    private JTextField nomeField;
    private JTextField sobrenomeField;
    private JTextField enderecoField;

    private Cliente cliente;

    public ClienteDialog(Frame parent, String title, Cliente cliente) {
        super(parent, title, true);
        this.cliente = cliente;

        cpfField = new JTextField(10);
        rgField = new JTextField(10);
        nomeField = new JTextField(10);
        sobrenomeField = new JTextField(10);
        enderecoField = new JTextField(20);

        JButton salvarButton = new JButton("Salvar");
        JButton cancelarButton = new JButton("Cancelar");

        salvarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                salvarCliente();
            }
        });

        cancelarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelar();
            }
        });

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
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

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(salvarButton);
        buttonPanel.add(cancelarButton);

        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        if (cliente != null) {
            preencherCampos();
        }

        setSize(300, 200);
        setLocationRelativeTo(parent);
    }

    private void preencherCampos() {
        cpfField.setText(cliente.getCpf());
        rgField.setText(cliente.getRg());
        nomeField.setText(cliente.getNome());
        sobrenomeField.setText(cliente.getSobrenome());
        enderecoField.setText(cliente.getEndereco());
    }

    private void salvarCliente() {
        String cpf = cpfField.getText();
        String rg = rgField.getText();
        String nome = nomeField.getText();
        String sobrenome = sobrenomeField.getText();
        String endereco = enderecoField.getText();

        if (!cpf.isEmpty() && !rg.isEmpty() && !nome.isEmpty() && !sobrenome.isEmpty() && !endereco.isEmpty()) {
            if (cliente == null) {
                cliente = new Cliente(cpf, rg, nome, sobrenome, endereco, -1L);
            } else {
                cliente.setCpf(cpf);
                cliente.setRg(rg);
                cliente.setNome(nome);
                cliente.setSobrenome(sobrenome);
                cliente.setEndereco(endereco);
            }
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        cliente = null;

        dispose();
    }

    public Cliente showDialog() {
        setVisible(true);
        return cliente;
    }
}

