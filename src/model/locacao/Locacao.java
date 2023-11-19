package model.locacao;

import java.util.Calendar;

import model.cliente.Cliente;
import model.veiculo.Veiculo;

public class Locacao {
	private int dias;
	private double valor;
	private Calendar data;
	private Cliente cliente;
	private Veiculo veiculo;
	
	public Locacao(int dias, double valor, Calendar data, Cliente cliente, Veiculo veiculo) {
		this.cliente = cliente;
		this.dias = dias;
		this.valor = valor;
		this.data = data;
		this.veiculo = veiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}


	public Calendar getData() {
		return data;
	}


	public double getValor() {
		return valor;
	}
	
	public int getDias() {
		return dias;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

}
