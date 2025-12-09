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

        String sql_select1 = "SELECT p.id, p.name, p.creation_date, c.id AS category_id, c.name AS category_name  + " +
                "FROM product p JOIN category c ON p.category_id = c.id + " +
                "ORDER BY p.id LIMIT ? OFFSET ? ";

        PreparedStatement st = dbconnection.getDBConnection().prepareStatement(sql_select1);

        st.setInt(1, size);
        st.setInt(2, offset);

        ResultSet rs = st.executeQuery();

        while(rs.next()) {
            Category category = new Category(
                    rs.getInt("id"),
                    rs.getString("name")
            );
            Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getTimestamp("creationDate").toInstant(),
                    categories.get(rs.getInt("category_id"))
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
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder("SELECT p.id, p.name, p.creation_date, c.id AS category_id, " +
                "c.name AS category_name FROM product p  + JOIN category c ON p.category_id = c.id + WHERE 1=1 ");

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

        for (int i = 0; i < params.size(); i++) {
            st.setObject(i + 1, params.get(i));
        }

        ResultSet rs = st.executeQuery();
        while(rs.next()){
            Category c = new Category(
                    rs.getInt("id"),
                    rs.getString("name")
            );
            Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getTimestamp("creationDate").toInstant(),
                    categories.get(rs.getInt("category_id"))
            );
            productListByCriteria.add(p);
        }
        return  productListByCriteria;

    }

    public List<Product> getProductsByCriteriaAndPage(
            String productName, String categoryName, Instant creationMin, Instant creationMax, int page, int size) throws SQLException {

        List<Product> finalList = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        int offset = (page - 1) * size;

        StringBuilder sql = new StringBuilder(
                "SELECT p.id, p.name, p.creation_date, c.id AS category_id, c.name AS category_name " +
                        "FROM product p JOIN category c ON p.category_id = c.id WHERE 1=1 "
        );

        if (productName != null) {
            sql.append("AND p.name ILIKE ? ");
            params.add("%" + productName + "%");
        }

        if (categoryName != null) {
            sql.append("AND c.name ILIKE ? ");
            params.add("%" + categoryName + "%");
        }

        if (creationMin != null) {
            sql.append("AND p.creation_date >= ? ");
            params.add(Timestamp.from(creationMin));
        }

        if (creationMax != null) {
            sql.append("AND p.creation_date <= ? ");
            params.add(Timestamp.from(creationMax));
        }

        sql.append("ORDER BY p.id LIMIT ? OFFSET ?");

        PreparedStatement st = dbconnection.getDBConnection().prepareStatement(sql.toString());

        int index = 1;
        for (Object param : params) {
            st.setObject(index++, param);
        }

        st.setInt(index++, size);
        st.setInt(index, offset);

        ResultSet rs = st.executeQuery();

        while (rs.next()) {
            Category category = new Category(
                    rs.getInt("category_id"),
                    rs.getString("category_name")
            );

            Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getTimestamp("creation_date").toInstant(),
                    categories.get(rs.getInt("category_id"))
            );

            finalList.add(p);
        }

        rs.close();
        st.close();
        return finalList;

    }
}