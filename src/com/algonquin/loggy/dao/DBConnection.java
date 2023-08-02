package com.algonquin.loggy.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;
    private String dbName = "loggy";
    private String dbUser = "root";
    private String dbPassword = "1234";
    // Database Schema
    // CREATE DATABASE loggy DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
    // CREATE TABLE logs (uuid CHAR(40) NOT NULL PRIMARY KEY, title CHAR(128),
    // content TEXT, createTimestamp Date);

    private DBConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbName
                        + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
                dbUser, dbPassword);
    }

    public Connection getConnectionToDatabase() {
        System.out.println("getConnectionToDatabase.");
        /*try {
            // db settings


            // load the driver class

            System.out.println("MySQL JDBC Driver Registered!");

            // get hold of the DriverManager


        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();

        }

        catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

        }*/
        return connection;
    }

    public static DBConnection getInstance() throws SQLException, ClassNotFoundException {
        return dbConnection==null?dbConnection=new DBConnection():dbConnection;
    }

}
