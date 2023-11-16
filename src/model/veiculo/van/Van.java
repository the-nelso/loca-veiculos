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

	@Override
	public double getValorDiariaLocacao() {
		if(this.categoria.equals(categoria.POPULAR)) {
			return 200.00;
		}
		if(this.categoria.equals(categoria.INTERMEDIARIO)) {
			return 400.00;
		}
		if(this.categoria.equals(categoria.LUXO)) {
			return 600.00;
		}
		return 0;
	}
	
}
