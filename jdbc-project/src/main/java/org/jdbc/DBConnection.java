package org.jdbc;


import java.sql.*;

public class DBConnection {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    private String url = "jdbc:postgresql://localhost:5432/product_management_db";
    private String user = "product_manager_user";
    private String password = "123456";

    public DBConnection( String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Connection getDBConnection() {
        try(Connection conn = DriverManager.getConnection(url, user, password)){
            System.out.println("Connected to the database") ;
        } catch (SQLException e){
            e.printStackTrace();
        };
        return null;
    }


}
