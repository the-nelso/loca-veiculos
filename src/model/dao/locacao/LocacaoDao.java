package model.dao.locacao;

import java.util.List;

import model.dao.Dao;
import model.locacao.Locacao;

public interface LocacaoDao extends Dao<Locacao>{
	public void delete(List<Locacao> lista) throws Exception;
}
