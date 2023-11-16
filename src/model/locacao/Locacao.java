package model.locacao;

import java.util.Calendar;

import model.cliente.Cliente;

public class Locacao {
	private int dias;
	private double valor;
	private Calendar data;
	private Cliente cliente;
	
	public Locacao(int dias, double valor, Calendar data, Cliente cliente) {
		this.cliente = cliente;
		this.dias = dias;
		this.valor = valor;
		this.data = data;
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

}
