package com.algonquin.loggy.dao;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

import com.algonquin.loggy.beans.FileUpload;
import com.algonquin.loggy.beans.Log;
import com.algonquin.loggy.beans.TextLog;
import com.algonquin.loggy.services.ApplicationService;

public class ApplicationDao implements ApplicationService {

    private DBConnection dbConnection ;

    public ApplicationDao() {
        try {
            dbConnection = DBConnection.getInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createLog(Log log) {
        Connection con = dbConnection.getConnectionToDatabase();
        PreparedStatement stmt = null;

        try {
            // Assuming 'logs' table has columns named 'uuid', 'title', 'content', 'createTimestamp', 'FileName', 'fileType', 'fileFileData', and 'user_id'
            String query = "INSERT INTO logs (uuid, title, content, createTimestamp, FileName, fileType, fileFileData, user_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, log.getLogId());
            stmt.setString(2, log.getTitle());
            stmt.setString(3, log.getContent());
            stmt.setDate(4, new java.sql.Date(log.getCreateTimestamp().getTime())); // Assuming log.getCreateTimestamp() returns a java.util.Date
            stmt.setString(5, log.getFile().getFileName());
            stmt.setString(6, log.getFile().getContentType());
            stmt.setBytes(7, log.getFile().getFileData());
            stmt.setString(8, log.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any exceptions that might occur during database operations
        } finally {
            // Close the statement but not the connection
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateLog(Log log) {
        Connection con = dbConnection.getConnectionToDatabase();
        PreparedStatement stmt = null;
        String query ;
        int nu;
        if(log.getFile()==null){
            query="UPDATE logs SET title = ?, content = ?, createTimestamp = ? WHERE uuid = ?";
            nu=4;
        }else {
            query="UPDATE logs SET title = ?, content = ?, createTimestamp = ?, FileName = ?, fileType = ?, fileFileData = ? WHERE uuid = ?";
            nu=7;
        }
        try {
            stmt = con.prepareStatement(query);
            stmt.setString(1, log.getTitle());
            stmt.setString(2, log.getContent());
            stmt.setDate(3, new java.sql.Date(log.getCreateTimestamp().getTime())); // Assuming log.getCreateTimestamp() returns a java.util.Date
            if(log.getFile()!=null){
                stmt.setString(5, log.getFile().getContentType());
                stmt.setBytes(6, log.getFile().getFileData());
                stmt.setString(4, log.getFile().getFileName());
            }
            stmt.setString(nu, log.getLogId());

            int i = stmt.executeUpdate();
            System.out.println(i);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void deleteLog(Log log) {
        Connection con = dbConnection.getConnectionToDatabase();
        PreparedStatement stmt = null;

        try {

            String query = "DELETE FROM logs WHERE uuid = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, log.getLogId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void createOrUpdateLog(Log log) {
        Log existingLog = getLogOfUser(log.getLogId(), log.getId());

        if (existingLog == null) {
            createLog(log);
        } else {
            updateLog(log);
        }
    }

    @Override
    public void addUser(String name) {
        Connection con = dbConnection.getConnectionToDatabase();
        PreparedStatement stmt = null;
        try {
            String query = "INSERT INTO users (name, date) VALUES (?, ?)";
            stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setDate(2, Date.valueOf(LocalDate.now()));
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isUserAvailable(String name) {
        Connection con = dbConnection.getConnectionToDatabase();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT COUNT(*) FROM users WHERE name = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, name);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }

            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public List<Log> getLogsOfUser(String uid) {
        Connection con = dbConnection.getConnectionToDatabase();
        List<Log> logs = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT uuid, title, content, createTimestamp, FileName, fileType, fileFileData FROM logs  WHERE user_id = ? order by createTimestamp DESC";
            stmt = con.prepareStatement(query);
            stmt.setString(1, uid);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Log log = new TextLog();
                log.setId(uid);
                log.setLogId(rs.getString("uuid"));
                log.setTitle(rs.getString("title"));
                log.setContent(rs.getString("content"));
                log.setCreateTimestamp(rs.getDate("createTimestamp"));
                FileUpload file = new FileUpload();
                file.setFileName(rs.getString("FileName"));
                file.setContentType(rs.getString("fileType"));
                file.setFileData(rs.getBytes("fileFileData"));
                log.setFile(file);
                logs.add(log);
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return logs;
    }

    @Override
    public Log getLogOfUser(String uid, String logId) {
        Connection con = dbConnection.getConnectionToDatabase();
        Log log = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT uuid, title, content, createTimestamp, FileName, fileType, fileFileData FROM logs WHERE user_id = ? AND uuid = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, uid);
            stmt.setString(2, logId);
            rs = stmt.executeQuery();

            if (rs.next()) {
                log = new TextLog();
                log.setLogId(logId);
                log.setId(uid);
                log.setTitle(rs.getString("title"));
                log.setContent(rs.getString("content"));
                log.setCreateTimestamp(rs.getDate("createTimestamp"));
                FileUpload file = new FileUpload();
                file.setFileName(rs.getString("FileName"));
                file.setContentType(rs.getString("fileType"));
                file.setFileData(rs.getBytes("fileFileData"));
                log.setFile(file);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return log;
    }

    @Override
    public int getNewLogId(String uid) {
        Connection con = dbConnection.getConnectionToDatabase();
        int newLogId = 1;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String query = "SELECT MAX(CAST(uuid AS UNSIGNED)) AS maxLogId FROM logs";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            if (rs.next()) {
                int maxLogId = rs.getInt("maxLogId");
                newLogId = maxLogId + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {

            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return newLogId;
    }
}
