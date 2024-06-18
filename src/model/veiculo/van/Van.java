package model.veiculo.van;

import model.locacao.Locacao;
import model.veiculo.Veiculo;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;
import model.veiculo.van.modelo.ModeloVan;

public class Van extends Veiculo{
	private ModeloVan modeloVan;

	public Van(Estado estado, Marca marca, Locacao locacao, Categoria categoria, double valorCompra, String placa, int ano, ModeloVan modeloVan) {
		super(estado, marca, locacao, categoria, valorCompra, placa, ano);
		this.modeloVan = modeloVan;
	}

	public Van() {
	}

	@Override
	public double getValorDiariaLocacao() {
		if(this.categoria.equals(Categoria.POPULAR)) {
			return 200.00;
		}
		if(this.categoria.equals(Categoria.INTERMEDIARIO)) {
			return 400.00;
		}
		if(this.categoria.equals(Categoria.LUXO)) {
			return 600.00;
		}
		return 0;
	}

	public ModeloVan getModeloVan() {
		return modeloVan;
	}

	@Override
	public String getModelo() {
		return modeloVan.name();
	}
	
	@Override
	public String getTipo() {
		return "Van";
	}

	public void setModeloVan(ModeloVan modeloVan) {
		this.modeloVan = modeloVan;
	}
	
}
