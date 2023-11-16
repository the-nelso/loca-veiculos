package model.veiculo;

import java.util.Calendar;

import model.cliente.Cliente;
import model.locacao.Locacao;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;

public abstract class Veiculo implements VeiculoI{
	protected Estado estado;
	protected Marca marca;
	protected Locacao locacao;
	protected Categoria categoria;
	protected double valorCompra;
	protected String placa;
	protected int ano;
	
	public Veiculo(Estado estado, Marca marca, Locacao locacao, Categoria categoria, double valorCompra, String placa, int ano) {
		this.estado = estado;
		this.marca = marca;
		this.locacao = locacao;
		this.categoria = categoria;
		this.valorCompra = valorCompra;
		this.placa = placa;
		this.ano = ano;
	}
	
	@Override
	public void locar(int dias, Calendar data, Cliente cliente) {
		Locacao locacao = new Locacao(dias, getValorDiariaLocacao(), data, cliente);
		this.estado = Estado.LOCADO;
		this.locacao = locacao;
	}
	
	@Override
	public double getValorParaVenda() {
		double valorParaVenda = (this.valorCompra - this.ano)*(0.15*this.valorCompra) < valorCompra * 0.1 ? valorCompra * 0.1 : (this.valorCompra - this.ano)*(0.15*this.valorCompra);
		return valorParaVenda;
	}
	
	public abstract double getValorDiariaLocacao();
	
	@Override
	public void vender() {
		this.estado = Estado.VENDIDO;
	}
	
	@Override
	public void devolver() {
		this.estado = Estado.DISPONIVEL;
	}

	@Override
	public Estado getEstado() {
		return estado;
	}

	@Override
	public Marca getMarca() {
		return marca;
	}
	
	@Override
	public Locacao getLocacao() {
		return locacao;
	}
	
	@Override
	public Categoria getCategoria() {
		return categoria;
	}
	
	@Override
	public String getPlaca() {
		return placa;
	}
	
	@Override
	public int getAno() {
		return ano;
	}
	
	
}
