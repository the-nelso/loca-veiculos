package controller.devolucao;

import java.util.List;

import model.dao.veiculo.VeiculoDaoSql;
import model.veiculo.Veiculo;

public class DevolucaoController {
	private VeiculoDaoSql veiculoDao;

	public DevolucaoController() {
		veiculoDao = VeiculoDaoSql.getVeiculoDaoSql();
	}

	public List<Veiculo> getVeiculosLocados() {
		try {
			return veiculoDao.getLocados();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void devolverVeiculo(Veiculo veiculo) {
		try {
			veiculoDao.update(veiculo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
