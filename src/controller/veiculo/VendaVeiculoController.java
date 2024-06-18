package controller.veiculo;

import java.util.List;

import model.dao.veiculo.VeiculoDaoSql;
import model.veiculo.Veiculo;

public class VendaVeiculoController {
	private VeiculoDaoSql veiculoDao;

	public VendaVeiculoController() {
		veiculoDao = VeiculoDaoSql.getVeiculoDaoSql();
	}

	public List<Veiculo> getVeiculosDisponiveis() {
		try {
			return veiculoDao.getDisponiveis();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void venderVeiculo(Veiculo veiculo) {
		try {
			veiculoDao.update(veiculo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
