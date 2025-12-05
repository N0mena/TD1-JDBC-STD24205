package org.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private List<Category> categories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private final String sql_select_category ;
    private DBConnection dbconnection;

    public DataRetriever(List<Category> categories, List<Product> product, String select_category) {
        this.categories = categories;
        this.products = product;
        this.sql_select_category = "select id, name from Product_category";
    }

    public List<Category> getAlLCategories() {
        try(Connection conn = DriverManager.getConnection(dbconnection.getUrl(),dbconnection.getUser(), dbconnection.getPassword());
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql_select_category));

        while(rs.next()){
            int key;
            String val;

            key = rs.getInt("id");
            if(rs.wasNull()){
                key = -1;
            }
            val = rs.getString(2);
            if(rs.wasNull()){
                val = null;
            }
            
        } catch(SQLException e){
            e.printStackTrace();
        }

    }

    public List<Product> getProductList(int page, int size) {
        List<Product> productList = products.stream()

    }



}
