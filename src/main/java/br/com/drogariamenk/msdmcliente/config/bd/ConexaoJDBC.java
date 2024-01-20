package br.com.drogariamenk.msdmcliente.config.bd;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class ConexaoJDBC {

    @Bean
    public ComboPooledDataSource ConnectionFactory() {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/clientes?createDatabaseIfNotExist=True");
        comboPooledDataSource.setUser("postgres");
        comboPooledDataSource.setPassword("1234");
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
