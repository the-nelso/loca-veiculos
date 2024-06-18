package views;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import controller.cliente.ClienteController;
import controller.locacao.LocacaoController;
import controller.veiculo.VeiculoController;
import views.cliente.ClienteView;
import views.locacao.LocacaoView;
import views.veiculo.VeiculoView;

public class MainView {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Locação de Veículos");
        JButton openLocacaoButton = new JButton("Abrir Locação");
        JButton openClienteButton = new JButton("Abrir Clientes");
        JButton openVeiculoButton = new JButton("Abrir Veículos");
        JButton openDevolucaoButton = new JButton("Devolver Veículos");
        JButton openVenderButton = new JButton("Vender Veículos");

        // Criação dos controladores
        ClienteController clienteController = new ClienteController();
        LocacaoController locacaoController = new LocacaoController();
        VeiculoController veiculoController = new VeiculoController();
        // DevolucaoController devolucaoController = new DevolucaoController();
        // VendaVeiculoController vendaVeiculoController = new VendaVeiculoController();

        openVenderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new VendaVeiculoView(vendaVeiculoController);
            }
        });

        openDevolucaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // new DevolucaoView(devolucaoController);
            }
        });

        openVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VeiculoView(veiculoController);
            }
        });

        openClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteView(clienteController);
            }
        });

        openLocacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LocacaoView(locacaoController, clienteController, veiculoController);
            }
        });

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(openLocacaoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        buttonPanel.add(openClienteButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        buttonPanel.add(openVeiculoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        buttonPanel.add(openDevolucaoButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        buttonPanel.add(openVenderButton, gbc);

        frame.getContentPane().setLayout(new BorderLayout());
        frame.getContentPane().add(buttonPanel, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
