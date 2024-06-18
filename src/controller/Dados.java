package controller;

import model.cliente.Cliente;
import model.locacao.Locacao;
import model.veiculo.Veiculo;

import java.util.ArrayList;
import java.util.List;

public class Dados {
    private static List<Cliente> clientes = new ArrayList<>();
    private static List<Veiculo> veiculos = new ArrayList<>();
    private static List<Locacao> locacoes = new ArrayList<>();

    public static List<Cliente> getClientes() {
        return clientes;
    }

    public static void adicionarCliente(Cliente cliente) {
        clientes.add(cliente);
    }

    public static List<Veiculo> getVeiculos() {
        return veiculos;
    }

    public static void adicionarVeiculo(Veiculo veiculo) {
        veiculos.add(veiculo);
    }

    public static List<Locacao> getLocacoes() {
        return locacoes;
    }

    public static void adicionarLocacao(Locacao locacao) {
        locacoes.add(locacao);
    }
}

