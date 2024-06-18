package model.dao.locacao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.cliente.Cliente;
import model.dao.ConnectionFactory;
import model.dao.cliente.ClienteDao;
import model.locacao.Locacao;

public class LocacaoDaoSql implements LocacaoDao{
	private ConnectionFactory connectionFactory;
	private final String insert = "insert into Locacao(id,dias,valor,data,cliente_id) values (?,?,?,?)";
	private final String select = "select * from Locacao";
	private final String update = "update Locacao set id=?,dias=?,valor=?,data=?,cliente_id=? WHERE id=?";
	private final String delete = "delete from Locacao WHERE id=?";
	private final String selectById = "select * from Locacao where id=?";
	
	private LocacaoDaoSql() {
	}

	private static LocacaoDaoSql dao;

	public static LocacaoDaoSql getContatoDaoSql() {
		if (dao == null)
			return dao = new LocacaoDaoSql();
		else
			return dao;
	}

	public LocacaoDaoSql(ConnectionFactory conFactory) {
		this.connectionFactory = conFactory;
	}
	
	@Override
	public void add(Locacao locacao) throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtAdiciona = connection.prepareStatement(insert,
						Statement.RETURN_GENERATED_KEYS);) {
			// seta os valores
			stmtAdiciona.setLong(1, locacao.getId());
			stmtAdiciona.setInt(2, locacao.getDias());
			stmtAdiciona.setDouble(3, locacao.getValor());
			stmtAdiciona.setDate(4, Date.valueOf(locacao.getData().toString()));
			stmtAdiciona.setLong(5, locacao.getCliente().getId());
			// executa
			stmtAdiciona.execute();
			// Seta o id do contato
			ResultSet rs = stmtAdiciona.getGeneratedKeys();
			rs.next();
			long i = rs.getLong(1);
			locacao.setId(i);
		}
	}

	@Override
	public List<Locacao> getAll() throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtLista = connection.prepareStatement(select);
				ResultSet rs = stmtLista.executeQuery();) {
			List<Locacao> locacoes = new ArrayList<Locacao>();
			while (rs.next()) {
				// Contato contato = new Contato();
				long id = rs.getLong("id");
				int dias = rs.getInt("dias");
				double valor = rs.getDouble("valor");
				Date data = rs.getDate("data");
				long cliente_id = rs.getLong("cliente_id");

				// adicionando o objeto à lista
				// locacoes.add(new Locacao(id, dias, valor, data, null , null));
			}

			return locacoes;
		}
	}

	@Override
	public Locacao getById(Long id) throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtBusca = connection.prepareStatement(selectById)) {
			stmtBusca.setLong(1, id);
			try (ResultSet rs = stmtBusca.executeQuery()) {
				if (rs.next()) {
					int dias = rs.getInt("dias");
					double valor = rs.getDouble("valor");
					Date data = rs.getDate("data");
					long cliente_id = rs.getLong("cliente_id");
					//return new Cliente(cpf, rg, nome, sobrenome, endereco, id);
				}
			}
		}
		return null;
	}

	@Override
	public void update(Locacao locacao) throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtAtualiza = connection.prepareStatement(update);) {

			stmtAtualiza.setLong(1, locacao.getId());
			stmtAtualiza.setInt(2, locacao.getDias());
			stmtAtualiza.setDouble(3, locacao.getValor());
			stmtAtualiza.setDate(4, Date.valueOf(locacao.getData().toString()));
			stmtAtualiza.setLong(5, locacao.getCliente().getId());
			stmtAtualiza.executeUpdate();
		}
		
	}

	@Override
	public void delete(Locacao locacao) throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtExcluir = connection.prepareStatement(delete);) {
			stmtExcluir.setLong(1, locacao.getId());
			stmtExcluir.executeUpdate();
		}
		
	}

	@Override
	public void deleteAll() throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				Statement stmtDeleteAll = connection.createStatement()) {
			stmtDeleteAll.executeUpdate("DELETE FROM Locacao");
		}
	}

	@Override
	public void delete(List<Locacao> locacoes) throws Exception {
		for (Locacao locacao: locacoes) {
			delete(locacao);
		}
	}
}
