package model.dao.veiculo;

import java.util.List;

import model.dao.Dao;
import model.veiculo.Veiculo;

public interface VeiculoDao extends Dao<Veiculo>{
	public List<Veiculo> getLocados() throws Exception;
	public List<Veiculo> getDisponiveis() throws Exception;
}
