package controller.locacao;

import java.util.List;

import model.dao.locacao.LocacaoDaoSql;
import model.locacao.Locacao;

public class LocacaoController {
	
    private LocacaoDaoSql locacaoDao;

    public LocacaoController() {
        locacaoDao = LocacaoDaoSql.getLocacaoDaoSql();
    }

    public void adicionarLocacao(Locacao locacao) {
        try {
            locacaoDao.add(locacao);
        } catch (Exception e) {
            // Tratar exceção adequadamente
            e.printStackTrace();
        }
    }

    public List<Locacao> listarLocacoes() {
        try {
            return locacaoDao.getAll();
        } catch (Exception e) {
            // Tratar exceção adequadamente
            e.printStackTrace();
            return null;
        }
    }

    public Locacao buscarLocacaoPorId(Long id) {
        try {
            return locacaoDao.getById(id);
        } catch (Exception e) {
            // Tratar exceção adequadamente
            e.printStackTrace();
            return null;
        }
    }

    public void atualizarLocacao(Locacao locacao) {
        try {
            locacaoDao.update(locacao);
        } catch (Exception e) {
            // Tratar exceção adequadamente
            e.printStackTrace();
        }
    }

    public void excluirLocacao(Locacao locacao) {
        try {
            locacaoDao.delete(locacao);
        } catch (Exception e) {
            // Tratar exceção adequadamente
            e.printStackTrace();
        }
    }
}
