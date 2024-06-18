package model.locacao;

import java.util.Calendar;

import model.cliente.Cliente;
import model.veiculo.Veiculo;

public class Locacao {
	private Long id;
	private int dias;
	private double valor;
	private Calendar data;
	private Cliente cliente;
	private Veiculo veiculo;
	
	public Locacao(Long id, int dias, double valor, Calendar data, Cliente cliente, Veiculo veiculo) {
		this.cliente = cliente;
		this.id = id;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setDias(int dias) {
		this.dias = dias;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public void setData(Calendar data) {
		this.data = data;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
}
