package model.dao.veiculo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.dao.ConnectionFactory;
import model.veiculo.Veiculo;
import model.veiculo.automovel.Automovel;
import model.veiculo.automovel.modelo.ModeloAutomovel;
import model.veiculo.categoria.Categoria;
import model.veiculo.estado.Estado;
import model.veiculo.marca.Marca;
import model.veiculo.motocicleta.Motocicleta;
import model.veiculo.motocicleta.modelo.ModeloMotocicleta;
import model.veiculo.van.Van;
import model.veiculo.van.modelo.ModeloVan;

public class VeiculoDaoSql implements VeiculoDao {
    private final String insert = "INSERT INTO Veiculo (estado, marca, categoria, valorCompra, placa, ano, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
    private final String insertAutomovel = "INSERT INTO Automovel (veiculo_id, modelo_automovel_id) value (?, ?)";
    private final String insertVan = "INSERT INTO Van (veiculo_id, modelo_van_id) value (?, ?)";
    private final String insertMotocicleta = "INSERT INTO Motocicleta (veiculo_id, modelo_motocicleta_id) value (?, ?)";
    private final String select = "select v.id, v.estado, v.marca, v.categoria, v.valorCompra, v.placa, v.ano, v.tipo, case when a.veiculo_id is not null then ma.nome when vv.veiculo_id is not null then mv.nome when m.veiculo_id is not null then mm.nome else null end as modelo from Veiculo v left join Automovel a on v.id = a.veiculo_id left join ModeloAutomovel ma on a.modelo_automovel_id = ma.id left join Van vv on v.id = vv.veiculo_id left join ModeloVan mv on vv.modelo_van_id = mv.id left join Motocicleta m on v.id = m.veiculo_id left join ModeloMotocicleta mm on m.modelo_motocicleta_id = mm.id;";
    private final String selectDisponiveis = "select v.id, v.estado, v.marca, v.categoria, v.valorCompra, v.placa, v.ano, v.tipo, case when a.veiculo_id is not null then ma.nome when vv.veiculo_id is not null then mv.nome when m.veiculo_id is not null then mm.nome else null end as modelo from Veiculo v left join Automovel a on v.id = a.veiculo_id left join ModeloAutomovel ma on a.modelo_automovel_id = ma.id left join Van vv on v.id = vv.veiculo_id left join ModeloVan mv on vv.modelo_van_id = mv.id left join Motocicleta m on v.id = m.veiculo_id left join ModeloMotocicleta mm on m.modelo_motocicleta_id = mm.id where v.estado != 'LOCADO';";
    private final String selectLocados = "select v.id, v.estado, v.marca, v.categoria, v.valorCompra, v.placa, v.ano, v.tipo, case when a.veiculo_id is not null then ma.nome when vv.veiculo_id is not null then mv.nome when m.veiculo_id is not null then mm.nome else null end as modelo from Veiculo v left join Automovel a on v.id = a.veiculo_id left join ModeloAutomovel ma on a.modelo_automovel_id = ma.id left join Van vv on v.id = vv.veiculo_id left join ModeloVan mv on vv.modelo_van_id = mv.id left join Motocicleta m on v.id = m.veiculo_id left join ModeloMotocicleta mm on m.modelo_motocicleta_id = mm.id where v.estado = 'LOCADO';";
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
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                veiculo.setId(rs.getLong(1));
            }
            
            if(veiculo.getTipo().equalsIgnoreCase("automovel")) {
            	try(PreparedStatement stmtAuto = connection.prepareStatement(insertAutomovel)){
            		stmtAuto.setLong(1, veiculo.getId());
            		stmtAuto.setLong(2, veiculo.getModeloAutomovel().getId());
            		stmtAuto.execute();
            	}
            }else if(veiculo.getTipo().equalsIgnoreCase("motocicleta")) {
            	try(PreparedStatement stmtMoto = connection.prepareStatement(insertMotocicleta)){
            		stmtMoto.setLong(1, veiculo.getId());
            		stmtMoto.setLong(2, veiculo.getModeloMotocicleta().getId());
            		stmtMoto.execute();
            	}
            } else if(veiculo.getTipo().equalsIgnoreCase("van")) {
            	try(PreparedStatement stmtVan = connection.prepareStatement(insertVan)){
            		stmtVan.setLong(1, veiculo.getId());
            		stmtVan.setLong(2, veiculo.getModeloVan().getId());
            		stmtVan.execute();
            	}
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
    
    public List<Veiculo> getLocados() throws Exception {
        List<Veiculo> veiculos = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectLocados);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Veiculo veiculo = createVeiculoFromResultSet(rs);
                veiculos.add(veiculo);
            }
        }
        return veiculos;
    }
    
    public List<Veiculo> getDisponiveis() throws Exception {
        List<Veiculo> veiculos = new ArrayList<>();
        try (Connection connection = ConnectionFactory.getConnection();
             PreparedStatement stmt = connection.prepareStatement(selectDisponiveis);
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
        stmt.setString(3, veiculo.getCategoria().name());
        stmt.setDouble(4, veiculo.getValorCompra());
        stmt.setString(5, veiculo.getPlaca());
        stmt.setInt(6, veiculo.getAno());
        stmt.setString(7, veiculo.getTipo());
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
        automovel.setCategoria(Categoria.valueOf(rs.getString("categoria")));
        automovel.setPlaca(rs.getString("placa"));
        automovel.setAno(rs.getInt("ano"));
        
        return automovel;
    }

    private Motocicleta createMotocicletaFromResultSet(ResultSet rs) throws Exception {
        Motocicleta motocicleta = new Motocicleta();
        motocicleta.setId(rs.getLong("id"));
        motocicleta.setEstado(Estado.valueOf(rs.getString("estado")));
        motocicleta.setMarca(Marca.valueOf(rs.getString("marca")));
        motocicleta.setModeloMotocicleta(ModeloMotocicleta.valueOf(rs.getString("modelo")));
        motocicleta.setCategoria(Categoria.valueOf(rs.getString("categoria")));
        motocicleta.setPlaca(rs.getString("placa"));
        motocicleta.setAno(rs.getInt("ano"));
        
        return motocicleta;
    }

    private Van createVanFromResultSet(ResultSet rs) throws Exception {
        Van van = new Van();
        van.setId(rs.getLong("id"));
        van.setEstado(Estado.valueOf(rs.getString("estado")));
        van.setMarca(Marca.valueOf(rs.getString("marca")));
        van.setModeloVan(ModeloVan.valueOf(rs.getString("modelo")));
        van.setCategoria(Categoria.valueOf(rs.getString("categoria")));
        van.setPlaca(rs.getString("placa"));
        van.setAno(rs.getInt("ano"));
        
        return van;
    }
}
