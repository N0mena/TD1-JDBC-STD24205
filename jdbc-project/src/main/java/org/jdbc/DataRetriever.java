package org.jdbc;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;



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

    public List<Product> getProductsByCriteria
            (String productName, String categoryName, Instant creationMin, Instant creationMax) throws SQLException {


        List<Product> productListByCriteria = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT p.id, p.name, p.creation_date, c.id AS category_id, " +
                "c.name AS category_name FROM product p  + JOIN category c ON p.category_id = c.id + WHERE 1=1 ");

        List<Object> params = new ArrayList<>();

        if (productName != null) {
            sql.append("AND p.name LIKE ?");
            params.add("%" + productName + "%");
        }

        if (categoryName != null) {
            sql.append("AND p.category LIKE ?");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append("AND p.creation_date BETWEEN ? AND ?");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append("AND p.creation_date BETWEEN ? AND ?");
            params.add(Timestamp.from(creationMax));
        }

        PreparedStatement st = dbconnection.getDBConnection().prepareStatement(sql.toString());

        for (Object param : params) {
            st.setObject(1, param);
        }

        ResultSet rs = st.executeQuery();
        while(rs.next()){
            Product p =new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getTimestamp("creation_date").toInstant()
            );
            Category c = new Category(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getCategory(c)
            );
            productListByCriteria.add(p);
        }
        return  productListByCriteria;

    }



}




