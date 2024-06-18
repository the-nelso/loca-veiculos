package model.dao.veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.ConnectionFactory;
import model.dao.cliente.ClienteDaoSql;
import model.veiculo.Veiculo;
import model.veiculo.automovel.Automovel;
import model.veiculo.automovel.modelo.ModeloAutomovel;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;
import model.veiculo.motocicleta.Motocicleta;
import model.veiculo.motocicleta.modelo.ModeloMotocicleta;
import model.veiculo.van.Van;
import model.veiculo.van.modelo.ModeloVan;

public class VeiculoDaoSql implements VeiculoDao {
    private final String insert = "INSERT INTO Veiculo (estado, marca, locacao_id, categoria, valorCompra, placa, ano, tipo, modelo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private final String select = "SELECT * FROM Veiculo";
    private final String update = "UPDATE Veiculo SET estado=?, marca=?, locacao_id=?, categoria=?, valorCompra=?, placa=?, ano=?, tipo=?, modelo=? WHERE id=?";
    private final String delete = "DELETE FROM Veiculo WHERE id=?";
    private final String selectById = "SELECT * FROM Veiculo WHERE id=?";
    
    private VeiculoDaoSql() {
	}

	private static VeiculoDaoSql dao;

	public static VeiculoDaoSql getVeiculoDaoSql() {
		if (dao == null)
			return dao = new VeiculoDaoSql();
		else
			return dao;
	}
    
    @Override
    public void add(Veiculo veiculo) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {

            setPreparedStatement(veiculo, stmt);

            stmt.setString(8, veiculo.getTipo()); // Definindo o tipo específico de veículo
            stmt.setString(9, getModeloString(veiculo)); // Definindo o modelo específico de veículo

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                veiculo.setId(rs.getLong(1));
            }
        }
    }

    @Override
    public List<Veiculo> getAll() throws Exception {
        List<Veiculo> veiculos = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(select);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veiculo veiculo = createVeiculoFromResultSet(rs);
                veiculos.add(veiculo);
            }
        }
        return veiculos;
    }

    @Override
    public Veiculo getById(Long id) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectById)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createVeiculoFromResultSet(rs);
                }
            }
        }
        return null;
    }

    @Override
    public void update(Veiculo veiculo) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(update)) {

            setPreparedStatement(veiculo, stmt);
            stmt.setString(8, veiculo.getTipo());
            stmt.setString(9, getModeloString(veiculo));
            stmt.setLong(10, veiculo.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void delete(Veiculo veiculo) throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(delete)) {

            stmt.setLong(1, veiculo.getId());
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteAll() throws Exception {
        try (Connection connection = ConnectionFactory.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM Veiculo");
        }
    }

    private void setPreparedStatement(Veiculo veiculo, PreparedStatement stmt) throws Exception {
        stmt.setString(1, veiculo.getEstado().name());
        stmt.setString(2, veiculo.getMarca().name());
        stmt.setLong(3, veiculo.getLocacao().getId());
        stmt.setString(4, veiculo.getCategoria().name());
        stmt.setDouble(5, veiculo.getValorCompra());
        stmt.setString(6, veiculo.getPlaca());
        stmt.setInt(7, veiculo.getAno());
    }

    private String getModeloString(Veiculo veiculo) {
        if (veiculo instanceof Automovel) {
            return ((Automovel) veiculo).getModeloAutomovel().name();
        } else if (veiculo instanceof Motocicleta) {
            return ((Motocicleta) veiculo).getModeloMotocicleta().name();
        } else if (veiculo instanceof Van) {
            return ((Van) veiculo).getModeloVan().name();
        } else {
            throw new IllegalArgumentException("Tipo de veículo desconhecido: " + veiculo.getClass().getSimpleName());
        }
    }

    private Veiculo createVeiculoFromResultSet(ResultSet rs) throws Exception {
        String tipo = rs.getString("tipo");
        Veiculo veiculo;
        switch (tipo) {
            case "Automovel":
                veiculo = createAutomovelFromResultSet(rs);
                break;
            case "Van":
                veiculo = createVanFromResultSet(rs);
                break;
            case "Motocicleta":
                veiculo = createMotocicletaFromResultSet(rs);
                break;
            default:
                throw new IllegalArgumentException("Tipo de veículo desconhecido: " + tipo);
        }
        veiculo.setId(rs.getLong("id"));
        return veiculo;
    }

    private Automovel createAutomovelFromResultSet(ResultSet rs) throws Exception {
        Automovel automovel = new Automovel();
        automovel.setId(rs.getLong("id"));
        automovel.setEstado(Estado.valueOf(rs.getString("estado")));
        automovel.setMarca(Marca.valueOf(rs.getString("marca")));
        automovel.setModeloAutomovel(ModeloAutomovel.valueOf(rs.getString("modelo")));
        // Definir demais atributos específicos de Automovel aqui
        return automovel;
    }

    private Motocicleta createMotocicletaFromResultSet(ResultSet rs) throws Exception {
        Motocicleta motocicleta = new Motocicleta();
        motocicleta.setId(rs.getLong("id"));
        motocicleta.setEstado(Estado.valueOf(rs.getString("estado")));
        motocicleta.setMarca(Marca.valueOf(rs.getString("marca")));
        motocicleta.setModeloMotocicleta(ModeloMotocicleta.valueOf(rs.getString("modelo")));
        // Definir demais atributos específicos de Motocicleta aqui
        return motocicleta;
    }

    private Van createVanFromResultSet(ResultSet rs) throws Exception {
        Van van = new Van();
        van.setId(rs.getLong("id"));
        van.setEstado(Estado.valueOf(rs.getString("estado")));
        van.setMarca(Marca.valueOf(rs.getString("marca")));
        van.setModeloVan(ModeloVan.valueOf(rs.getString("modelo")));
        // Definir demais atributos específicos de Van aqui
        return van;
    }
}
