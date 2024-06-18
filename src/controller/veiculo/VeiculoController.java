package controller.veiculo;

import java.util.List;

import model.dao.veiculo.VeiculoDaoSql;
import model.veiculo.Veiculo;

public class VeiculoController {
	private VeiculoDaoSql veiculoDao;

	public VeiculoController() {
		veiculoDao = VeiculoDaoSql.getVeiculoDaoSql();
	}

	public void adicionarVeiculo(Veiculo veiculo) {
		try {
			veiculoDao.add(veiculo);
		} catch (Exception e) {
			// Tratar exceção adequadamente
			e.printStackTrace();
		}
	}

	public List<Veiculo> listarVeiculos() {
		try {
			return veiculoDao.getAll();
		} catch (Exception e) {
			// Tratar exceção adequadamente
			e.printStackTrace();
			return null;
		}
	}

	public Veiculo buscarVeiculoPorId(Long id) {
		try {
			return veiculoDao.getById(id);
		} catch (Exception e) {
			// Tratar exceção adequadamente
			e.printStackTrace();
			return null;
		}
	}

	public void atualizarVeiculo(Veiculo veiculo) {
		try {
			veiculoDao.update(veiculo);
		} catch (Exception e) {
			// Tratar exceção adequadamente
			e.printStackTrace();
		}
	}

	public void excluirVeiculo(Veiculo veiculo) {
		try {
			veiculoDao.delete(veiculo);
		} catch (Exception e) {
			// Tratar exceção adequadamente
			e.printStackTrace();
		}
	}
}
