package model.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	private static Properties properties;

	private ConnectionFactory() {
	}

	public static Connection getConnection() throws SQLException, IOException {
		readProperties();
		String url = properties.getProperty("db.url");
		String user = properties.getProperty("db.user");
		String pwd = properties.getProperty("db.pwd");
		return DriverManager.getConnection(url, user, pwd);
	}

	private static void readProperties() throws IOException {
	    if (properties == null) {
	        Properties props = new Properties();
	        // Carrega o arquivo como um recurso dentro do JAR
	        InputStream inputStream = ConnectionFactory.class.getClassLoader().getResourceAsStream("model/dao/application.properties");
	        if (inputStream == null) {
	            throw new FileNotFoundException("Arquivo application.properties não encontrado.");
	        }
	        props.load(inputStream);
	        properties = props;
	    }
	}

}
