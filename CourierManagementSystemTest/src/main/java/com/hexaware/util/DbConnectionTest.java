package com.hexaware.util;

import com.hexaware.exception.DbConnectionException;

import java.sql.Connection;

public class DbConnectionTest {

    public static void main(String[] args) {
        Connection conn = null;
        try {
            conn = DbConnectionUtil.getDbConnection();
            if (conn != null) {
                System.out.println("Database connected successfully!");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (DbConnectionException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            DbConnectionUtil.closeConnection(conn);
        }
    }
}
