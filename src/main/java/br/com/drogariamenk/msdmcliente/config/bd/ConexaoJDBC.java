package br.com.drogariamenk.msdmcliente.config.bd;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class ConexaoJDBC {

    @Value("${endereco.database}")
    private String database;

    @Value("${endereco.username}")
    private String username;

    @Value("${endereco.password}")
    private String password;

    @Bean
    public ComboPooledDataSource ConnectionFactory() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl(database);
        comboPooledDataSource.setUser(username);
        comboPooledDataSource.setPassword(password);
        comboPooledDataSource.setMaxPoolSize(15);
        return comboPooledDataSource;
    }

    @Bean
    public Connection conectandoComBancoDeDados() {
        Connection connection;
        try {
            return connection = ConnectionFactory().getConnection();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;

    }
}
