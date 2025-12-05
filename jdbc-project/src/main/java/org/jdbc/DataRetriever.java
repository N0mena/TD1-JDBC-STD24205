package org.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private List<Category> categories = new ArrayList<>();
    private List<Product> products = new ArrayList<>();
    private final String select_category ;
    private Connection connection;

    public DataRetriever(List<Category> categories, List<Product> product, String select_category) {
        this.categories = categories;
        this.products = product;
        this.select_category = "select id, name from Product_category";

    }

    public List<Category> getAlLCategories() {
        try(Connection conn = DBConnection.getDBConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(select_category)){
            while (rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("user");
            }
        }
    }



    public List<Product> getProductList(int page, int size) {
        List<Product> productList = products.stream()

    }




}
