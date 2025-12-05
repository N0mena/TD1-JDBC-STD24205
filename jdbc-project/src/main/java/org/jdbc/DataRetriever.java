package org.jdbc;

import java.util.ArrayList;
import java.util.List;

public class DataRetriever {
    private List<Category> categories = new ArrayList<>();
    private List<Product> products;

    public DataRetriever(List<Category> categories, List<Product> product) {
        this.categories = categories;
        this.products = product;
    }
    public void addCategory(Category category) {
        categories.add(category);
    }
    public List<Category> getAlLCategories() {
        return categories;
    }



}
