package model.veiculo.automovel;

import model.locacao.Locacao;
import model.veiculo.Veiculo;
import model.veiculo.automovel.modelo.ModeloAutomovel;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;

public class Automovel extends Veiculo{
	private ModeloAutomovel modeloAutomovel;

	public Automovel(Estado estado, Marca marca, Locacao locacao, Categoria categoria, double valorCompra, String placa, int ano, ModeloAutomovel modeloAutomovel) {
		super(estado, marca, locacao, categoria, valorCompra, placa, ano);
		this.modeloAutomovel = modeloAutomovel;
	}
	
	public ModeloAutomovel getModeloAutomovel() {
		return modeloAutomovel;
	}

	@Override
	public double getValorDiariaLocacao() {
		if(this.categoria.equals(Categoria.POPULAR)) {
			return 100.00;
		}
		if(this.categoria.equals(Categoria.INTERMEDIARIO)) {
			return 300.00;
		}
		if(this.categoria.equals(Categoria.LUXO)) {
			return 450.00;
		}
		
		return 0;
	}
	
	@Override
	public String getModelo() {
		return modeloAutomovel.name();
	}
	
	@Override
	public String getTipo() {
		return "Automovel";
	}
}
