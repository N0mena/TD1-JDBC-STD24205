package org.jdbc;

import java.time.Instant;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {
            DBConnection db = new DBConnection();
            DataRetriever retriever = new DataRetriever(db);

            System.out.println("Test category list");
            List<Category> allCategories = retriever.getAlLCategories();
            allCategories.forEach(System.out::println);

            System.out.println("Test products list by page and size");
            retriever.getProductsByCriteria();

            System.out.println("Filter by productName = 'Dell'");
            retriever.getProductsByCriteria("Dell", null, null, null)
                    .forEach(System.out::println);

            System.out.println(" Filter by categoryName = 'info' ");
            retriever.getProductsByCriteria(null, "info", null, null)
                    .forEach(System.out::println);

            System.out.println(" Filter by productName = 'iPhone'  & categoryName = 'mobile' ");
            retriever.getProductsByCriteria("iPhone", "mobile", null, null)
                    .forEach(System.out::println);

            System.out.println("Filter by date");
            retriever.getProductsByCriteria(
                    null,
                    null,
                    Instant.parse("2024-02-01T00:00:00Z"),
                    Instant.parse("2024-03-01T23:59:59Z")
            ).forEach(System.out::println);

            System.out.println(" Filter by productName = 'Samsung'  & categoryName = 'bureau' ");
            retriever.getProductsByCriteria("Samsung", "bureau", null, null)
                    .forEach(System.out::println);

            System.out.println(" Filter by productName = 'Sony'  & categoryName = 'informatique' ");
            retriever.getProductsByCriteria("Sony", "informatique", null, null)
                    .forEach(System.out::println);

            System.out.println("Filter by categoryName & dates ");
            retriever.getProductsByCriteria(
                    null,
                    "audio",
                    Instant.parse("2024-01-01T00:00:00Z"),
                    Instant.parse("2024-12-01T23:59:59Z")
            ).forEach(System.out::println);

            System.out.println("No Filter");
            retriever.getProductsByCriteria(null, null, null, null)
                    .forEach(System.out::println);

            System.out.println();



        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
