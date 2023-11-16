package model.veiculo.motocicleta;

import model.locacao.Locacao;
import model.veiculo.Veiculo;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;
import model.veiculo.motocicleta.modelo.ModeloMotocicleta;

public class Motocicleta extends Veiculo{
	ModeloMotocicleta modeloMotocicleta;
	
	public Motocicleta(Estado estado, Marca marca, Locacao locacao, Categoria categoria, double valorCompra, String placa, int ano, ModeloMotocicleta modeloMotocicleta) {
		super(estado, marca, locacao, categoria, valorCompra, placa, ano);
		this.modeloMotocicleta = modeloMotocicleta;
	}

	@Override
	public double getValorDiariaLocacao() {
		if(this.categoria.equals(categoria.POPULAR)) {
			return 70.00;
		}
		if(this.categoria.equals(categoria.INTERMEDIARIO)) {
			return 200.00;
		}
		if(this.categoria.equals(categoria.LUXO)) {
			return 350.00;
		}
		
		return 0;
		
	}
	
}
