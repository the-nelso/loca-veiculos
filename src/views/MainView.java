package views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import views.cliente.ClienteView;
import views.devolucao.DevolucaoView;
import views.locacao.LocacaoView;
import views.veiculo.VeiculoView;
import views.veiculo.VendaVeiculoView;

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
        JFrame frame = new JFrame("Locação de Veiculos");
        JButton openLocacaoButton = new JButton("Abrir Locação");
        JButton openClienteButton = new JButton("Abrir Clientes");
        JButton openVeiculoButton = new JButton("Abrir Veículos");
        JButton openDevolucaoButton = new JButton("Devolver Veículos");
        JButton openVenderButton = new JButton("Vender Veículos");
        
        openVenderButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new VendaVeiculoView();
			}
		});
        
        openDevolucaoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DevolucaoView();
			}
		});

        openVeiculoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new VeiculoView();
            }
        });

        openClienteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ClienteView();
            }
        });

        openLocacaoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LocacaoView();
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
