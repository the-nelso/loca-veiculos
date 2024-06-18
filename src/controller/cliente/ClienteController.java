package controller.cliente;

import java.util.List;
import model.cliente.Cliente;
import model.dao.cliente.ClienteDaoSql;

public class ClienteController {

    private ClienteDaoSql clienteDao;

    public ClienteController() {
        clienteDao = ClienteDaoSql.getClienteDaoSql();
    }

    public List<Cliente> obterListaClientes() {
        try {
            return clienteDao.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void adicionarCliente(Cliente cliente) {
        try {
            clienteDao.add(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void atualizarCliente(Cliente cliente) {
        try {
            clienteDao.update(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void excluirCliente(Cliente cliente) {
        try {
            clienteDao.delete(cliente);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
