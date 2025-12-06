package org.jdbc;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.UIManager.getInt;

public class DataRetriever {
    private List<Category> categories;
    private List<Product> products;
    private DBConnection dbconnection;

    public DataRetriever(List<Category> categories, List<Product> products, DBConnection dbconnection) {
        this.categories = categories;
        this.products = products;
        this.dbconnection = dbconnection;
    }

    public List<Category> getAlLCategories() throws SQLException {
        List<Category> categoryList = new ArrayList<>();

        String sql_select = "Select id, name from category";
        PreparedStatement st = dbconnection.getDBConnection().prepareStatement(sql_select);
        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Category c = new Category(
                    rs.getInt("id"),
                    rs.getString("name")
            );
            categoryList.add(c);
        }
        rs.close();
        st.close();
        return categoryList;
    }

    public List<Product> getProductList(int page, int size) throws SQLException {
        List<Product> productList = new ArrayList<>();

        int offset = (page - 1) * size;

        String sql_select1 = "Select id, name from product order by id limit ? offset? ";

        PreparedStatement st = dbconnection.getDBConnection().prepareStatement(sql_select1);

        st.setInt(1, offset);
        st.setInt(2, size);

        ResultSet rs = st.executeQuery();

        while(rs.next()) {
            Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getFloat("price"),
                    rs.getString("category")
            );
            productList.add(p);
        }
        rs.close();
        st.close();

        return  productList;
    }




}
