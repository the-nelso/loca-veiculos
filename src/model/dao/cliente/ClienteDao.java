package model.dao.cliente;

import java.util.List;

import model.cliente.Cliente;
import model.dao.Dao;

public interface ClienteDao extends Dao<Cliente>{
    public void delete(List<Cliente> lista) throws Exception;    
}
