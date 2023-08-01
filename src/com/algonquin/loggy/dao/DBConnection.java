package com.algonquin.loggy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database Schema
    // CREATE DATABASE loggy DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
    // CREATE TABLE logs (uuid CHAR(40) NOT NULL PRIMARY KEY, title CHAR(128),
    // content TEXT, createTimestamp Date);

    public static Connection getConnectionToDatabase() {
        Connection connection = null;

        System.out.println("getConnectionToDatabase.");

        try {
            // db settings
            String dbName = "loggy";
            String dbUser = "root";
            String dbPassword = "";

            // load the driver class
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("MySQL JDBC Driver Registered!");

            // get hold of the DriverManager
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName
                    + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                    dbUser, dbPassword);

        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();

        }

        catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

        }

        if (connection != null) {
            System.out.println("Connection made to DB!");

        }

        return connection;
    }

}
