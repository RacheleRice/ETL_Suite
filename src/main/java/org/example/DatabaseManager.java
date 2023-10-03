package org.example;

import java.sql.*;

public class DatabaseManager {
    private Connection conn;

    public void createGrantsTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS grants (" +
                "id SERIAL PRIMARY KEY," +
                "tax_period_end_date DATE," +
                "return_type_code VARCHAR(10)," +
                "tax_period_begin_date DATE," +
                "ein VARCHAR(15)," +
                "filer_name VARCHAR(255)," +
                "filer_street VARCHAR(255)," +
                "filer_city VARCHAR(100)," +
                "filer_state VARCHAR(10)," +
                "filer_zip VARCHAR(15)," +
                "recipient_name VARCHAR(255)," +
                "grant_amount INT," +
                "recipient_street VARCHAR(255)," +
                "recipient_city VARCHAR(100)," +
                "recipient_state VARCHAR(10)," +
                "recipient_zip VARCHAR(15)," +
                "recipient_relationship VARCHAR(50)," +
                "recipient_foundation_status VARCHAR(50)," +
                "grant_purpose TEXT" +
                ");";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Error creating grants table: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public DatabaseManager() {
        //initialize connection
        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/grants", "postgres", "postgres1");
            createGrantsTable();
        } catch (SQLException e) {
            System.out.println("Error initializing a database connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void insertGrantInfo(GrantInfo grantInfo) {
        //Insert grantInfo into database
        String insertSQL = "INSERT INTO grants (" +
                "tax_period_end_date," +
                "return_type_code," +
                "tax_period_begin_date," +
                "ein," +
                "filer_name," +
                "filer_street," +
                "filer_city," +
                "filer_state," +
                "filer_zip," +
                "recipient_name," +
                "grant_amount," +
                "recipient_street," +
                "recipient_city," +
                "recipient_state," +
                "recipient_zip," +
                "recipient_relationship," +
                "recipient_foundation_status," +
                "grant_purpose" +
                ") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setDate(1, grantInfo.getTaxPeriodEndDate());
            pstmt.setString(2, grantInfo.getReturnTypeCode());
            pstmt.setDate(3, grantInfo.getTaxPeriodBeginDate());
            pstmt.setString(4, grantInfo.getEin());
            pstmt.setString(5, grantInfo.getFilerName());
            pstmt.setString(6, grantInfo.getFilerStreet());
            pstmt.setString(7, grantInfo.getFilerCity());
            pstmt.setString(8, grantInfo.getFilerState());
            pstmt.setString(9, grantInfo.getFilerZip());
            pstmt.setString(10, grantInfo.getRecipientName());
            pstmt.setInt(11, grantInfo.getGrantAmount());
            pstmt.setString(12, grantInfo.getRecipientStreet());
            pstmt.setString(13, grantInfo.getRecipientCity());
            pstmt.setString(14, grantInfo.getRecipientState());
            pstmt.setString(15, grantInfo.getRecipientZip());
            pstmt.setString(16, grantInfo.getRecipientRelationship());
            pstmt.setString(17, grantInfo.getRecipientFoundationStatus());
            pstmt.setString(18, grantInfo.getGrantPurpose());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting grant info into database: " + e.getMessage());
            e.printStackTrace();
        }

    }
}
