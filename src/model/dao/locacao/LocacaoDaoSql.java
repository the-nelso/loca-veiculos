package model.dao.locacao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.cliente.Cliente;
import model.dao.ConnectionFactory;
import model.locacao.Locacao;
import model.veiculo.Veiculo;
import model.veiculo.automovel.Automovel;
import model.veiculo.motocicleta.Motocicleta;
import model.veiculo.van.Van;

public class LocacaoDaoSql implements LocacaoDao{
	private ConnectionFactory connectionFactory;
	private final String insert = "insert into Locacao(dias,valor,data,cliente_id) values (?,?,?,?)";
	private final String insertLocacao = "insert into LocacaoVeiculo(locacao_id,veiculo_id) values (?,?)";
	private final String select = "select l.*, v.id as veiculo_id, v.tipo from Locacao l left join LocacaoVeiculo lv on l.id = lv.locacao_id left join Veiculo v on lv.veiculo_id = v.id;";
	private final String update = "update Locacao set id=?,dias=?,valor=?,data=?,cliente_id=? WHERE id=?";
	private final String delete = "delete from Locacao WHERE id=?";
	private final String selectById = "select l.*, v.id as veiculo_id from Locacao l left join LocacaoVeiculo lv on l.id = lv.locacao_id left join Veiculo v on lv.veiculo_id = v.id where l.id=?;";
	
	private LocacaoDaoSql() {
	}

	private static LocacaoDaoSql dao;

	public static LocacaoDaoSql getLocacaoDaoSql() {
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
						Statement.RETURN_GENERATED_KEYS);
				PreparedStatement stmtAdicionaAux = connection.prepareStatement(insertLocacao, Statement.RETURN_GENERATED_KEYS);) {
			// seta os valores
			stmtAdiciona.setInt(1, locacao.getDias());
			stmtAdiciona.setDouble(2, locacao.getValor());
			stmtAdiciona.setDate(3, Date.valueOf(locacao.getData()));
			stmtAdiciona.setLong(4, locacao.getCliente().getId());
			// executa
			stmtAdiciona.execute();
			// Seta o id do contato
			ResultSet rs = stmtAdiciona.getGeneratedKeys();
			rs.next();
			Long i = rs.getLong(1);
			locacao.setId(i);
			
			stmtAdicionaAux.setLong(1, locacao.getId());
			stmtAdicionaAux.setLong(2, locacao.getVeiculo().getId());
			
			stmtAdicionaAux.execute();
		}
	}

	@Override
	public List<Locacao> getAll() throws Exception {
		try (Connection connection = ConnectionFactory.getConnection();
				PreparedStatement stmtLista = connection.prepareStatement(select);
				ResultSet rs = stmtLista.executeQuery();) {
			List<Locacao> locacoes = new ArrayList<Locacao>();
			while (rs.next()) {
				Long id = rs.getLong("id");
				int dias = rs.getInt("dias");
				double valor = rs.getDouble("valor");
				Date data = rs.getDate("data");
				Long cliente_id = rs.getLong("cliente_id");
				Long veiculo_id = rs.getLong("veiculo_id");
				String tipo = rs.getString("tipo");
				
				Cliente cliente = new Cliente();
				Veiculo veiculo = null;
				
				cliente.setId(cliente_id);
				if(tipo.equalsIgnoreCase("automovel")) {
					veiculo = new Automovel();
				}else if(tipo.equalsIgnoreCase("van")) {
					veiculo = new Van();
				}else if(tipo.equalsIgnoreCase("motocicleta")) {
					veiculo = new Motocicleta();
				}
				
				veiculo.setId(veiculo_id);
				
				// adicionando o objeto à lista
				locacoes.add(new Locacao(id, dias, valor, data.toLocalDate(), cliente, veiculo));
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
					Long cliente_id = rs.getLong("cliente_id");
					Long veiculo_id = rs.getLong("veiculo_id");
					String tipo = rs.getString("tipo");
					
					Cliente cliente = new Cliente();
					Veiculo veiculo = null;
					
					cliente.setId(cliente_id);
					if(tipo.equalsIgnoreCase("automovel")) {
						veiculo = new Automovel();
					}else if(tipo.equalsIgnoreCase("van")) {
						veiculo = new Van();
					}else if(tipo.equalsIgnoreCase("motocicleta")) {
						veiculo = new Motocicleta();
					}
					
					veiculo.setId(veiculo_id);
					return new Locacao(id, dias, valor, data.toLocalDate(), cliente, veiculo);
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
