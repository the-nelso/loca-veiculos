package model.veiculo;

import java.util.Calendar;

import model.cliente.Cliente;
import model.locacao.Locacao;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;

public abstract class Veiculo implements VeiculoI{
	protected Long id;
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
	
	public Veiculo() {
	}
	
	@Override
	public void locar(Long id, int dias, Calendar data, Cliente cliente) {
		Locacao locacao = new Locacao(id, dias, getValorDiariaLocacao(), data, cliente, this);
		this.estado = Estado.LOCADO;
		this.locacao = locacao;
	}
	
	@Override
	public double getValorParaVenda() {
		double valorParaVenda = (this.valorCompra - this.ano)*(0.15*this.valorCompra) < valorCompra * 0.1 ? valorCompra * 0.1 : (this.valorCompra - this.ano)*(0.15*this.valorCompra);
		return valorParaVenda;
	}
	
	public abstract double getValorDiariaLocacao();
	
	public abstract String getModelo();
	
	public abstract String getTipo();
	
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getValorCompra() {
		return valorCompra;
	}

	public void setValorCompra(double valorCompra) {
		this.valorCompra = valorCompra;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
	
	
}
