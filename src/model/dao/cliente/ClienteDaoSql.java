package model.dao.cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.cliente.Cliente;
import model.dao.ConnectionFactory;

public class ClienteDaoSql implements ClienteDao {
	private ConnectionFactory connectionFactory;
	private final String insert = "insert into Cliente (cpf,rg,nome,sobrenome,endereco) values (?,?,?,?,?,?)";
	private final String select = "select * from Cliente";
	private final String update = "update Cliente set cpf=?, rg=?, nome=?, sobrenome=?, endereco=? WHERE id=?";
	private final String delete = "delete from Cliente WHERE id=?";
	private final String selectById = "select * from Cliente where id=?";

	private ClienteDaoSql() {
	}

	private static ClienteDaoSql dao;

	public static ClienteDaoSql getContatoDaoSql() {
		if (dao == null)
			return dao = new ClienteDaoSql();
		else
			return dao;
	}

	public ClienteDaoSql(ConnectionFactory conFactory) {
		this.connectionFactory = conFactory;
	}

	@Override
	public void add(Cliente cliente) throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtAdiciona = connection.prepareStatement(insert,
						Statement.RETURN_GENERATED_KEYS);) {
			// seta os valores
			stmtAdiciona.setString(1, cliente.getCpf());
			stmtAdiciona.setString(2, cliente.getRg());
			stmtAdiciona.setString(3, cliente.getNome());
			stmtAdiciona.setString(4, cliente.getSobrenome());
			stmtAdiciona.setString(5, cliente.getEndereco());
			// executa
			stmtAdiciona.execute();
			// Seta o id do contato
			ResultSet rs = stmtAdiciona.getGeneratedKeys();
			rs.next();
			long i = rs.getLong(1);
			cliente.setId(i);
		}
	}

	@Override
	public List<Cliente> getAll() throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtLista = connection.prepareStatement(select);
				ResultSet rs = stmtLista.executeQuery();) {
			List<Cliente> clientes = new ArrayList<Cliente>();
			while (rs.next()) {
				// Contato contato = new Contato();
				long id = rs.getLong("id");
				String cpf = rs.getString("cpf");
				String rg = rs.getString("rg");
				String nome = rs.getString("nome");
				String sobrenome = rs.getString("sobrenome");
				String endereco = rs.getString("endereco");

				// adicionando o objeto à lista
				clientes.add(new Cliente(cpf, rg, nome, sobrenome, endereco, id));
			}

			return clientes;
		}
	}

	@Override
	public Cliente getById(Long id) throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtBusca = connection.prepareStatement(selectById)) {
			stmtBusca.setLong(1, id);
			try (ResultSet rs = stmtBusca.executeQuery()) {
				if (rs.next()) {
					String cpf = rs.getString("cpf");
					String rg = rs.getString("rg");
					String nome = rs.getString("nome");
					String sobrenome = rs.getString("sobrenome");
					String endereco = rs.getString("endereco");
					return new Cliente(cpf, rg, nome, sobrenome, endereco, id);
				}
			}
		}
		return null;
	}

	@Override
	public void update(Cliente cliente) throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtAtualiza = connection.prepareStatement(update);) {

			stmtAtualiza.setString(1, cliente.getCpf());
			stmtAtualiza.setString(2, cliente.getRg());
			stmtAtualiza.setString(3, cliente.getNome());
			stmtAtualiza.setString(4, cliente.getSobrenome());
			stmtAtualiza.setString(5, cliente.getEndereco());
			stmtAtualiza.setLong(6, cliente.getId());
			stmtAtualiza.executeUpdate();
		}
	}

	@Override
	public void delete(Cliente cliente) throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtExcluir = connection.prepareStatement(delete);) {
			stmtExcluir.setLong(1, cliente.getId());
			stmtExcluir.executeUpdate();
		}
	}

	@Override
	public void deleteAll() throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				Statement stmtDeleteAll = connection.createStatement()) {
			stmtDeleteAll.executeUpdate("DELETE FROM Cliente");
		}
	}

	@Override
	public void delete(List<Cliente> clientes) throws Exception {
		for (Cliente cliente : clientes) {
			delete(cliente);
		}
	}

}
