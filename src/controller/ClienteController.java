package controller;

import model.dao.cliente.ClienteDao;
import views.cliente.ClienteView;

public class ClienteController {
	private ClienteView view;
	private ClienteDao modelDao;
	
	public ClienteController(ClienteView view, ClienteDao modelDao) {
        this.view = view;
        this.modelDao = modelDao;
    }
}
