package model.veiculo.motocicleta;

import model.locacao.Locacao;
import model.veiculo.Veiculo;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;
import model.veiculo.motocicleta.modelo.ModeloMotocicleta;

public class Motocicleta extends Veiculo{
	private ModeloMotocicleta modeloMotocicleta;
	
	public Motocicleta(Estado estado, Marca marca, Locacao locacao, Categoria categoria, double valorCompra, String placa, int ano, ModeloMotocicleta modeloMotocicleta) {
		super(estado, marca, locacao, categoria, valorCompra, placa, ano);
		this.modeloMotocicleta = modeloMotocicleta;
	}

	@Override
	public double getValorDiariaLocacao() {
		if(this.categoria.equals(Categoria.POPULAR)) {
			return 70.00;
		}
		if(this.categoria.equals(Categoria.INTERMEDIARIO)) {
			return 200.00;
		}
		if(this.categoria.equals(Categoria.LUXO)) {
			return 350.00;
		}
		
		return 0;
		
	}

	public ModeloMotocicleta getModeloMotocicleta() {
		return modeloMotocicleta;
	}
	
}
