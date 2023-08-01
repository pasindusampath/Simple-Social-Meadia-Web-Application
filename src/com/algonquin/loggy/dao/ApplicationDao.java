package com.algonquin.loggy.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.algonquin.loggy.beans.Log;
import com.algonquin.loggy.beans.TextLog;
import com.algonquin.loggy.services.ApplicationService;

public class ApplicationDao implements ApplicationService {

    private DBConnection dbConnection ;

    public ApplicationDao() {
        dbConnection = new DBConnection();
    }

    @Override
    public Map<String, List<Log>> readLogs() {
        return null;
    }

    @Override
    public List<Log> readLog(String id) {
        return null;
    }

    @Override
    public void createLog(Log log) {

    }

    @Override
    public void updateLog(Log log) {

    }

    @Override
    public void deleteLog(Log log) {

    }

    @Override
    public void createOrUpdateLog(Log log) {

    }

    @Override
    public void addUser(String id) {

    }

    @Override
    public boolean isUserAvailable(String id) {
        return false;
    }

    @Override
    public List<Log> getLogsOfUser(String uid) {
        return null;
    }

    @Override
    public Log getLogOfUser(String uid, String logId) {
        return null;
    }

    @Override
    public int getNewLogId(String uid) {
        return 0;
    }

    /*public Map<UUID, Log> readLogs() {
        Log log = null;
        Map<UUID, Log> logs = new LinkedHashMap<UUID, Log>();

        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get all the log
            String sql = "select * from logs;";
            PreparedStatement statement = connection.prepareStatement(sql);

            // execute query, get resultset and return User info
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                log = new TextLog();
                log.setId(UUID.fromString(set.getString("uuid")));
                log.setTitle(set.getString("title"));
                log.setContent(set.getString("content"));
                // log.setCreateTimestamp(Date.parse(set.getDate("createTimestamp")));
                logs.put(log.getId(), log);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return logs;
    }

    public Log readLog(String id) {
        Log log = null;
        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get the log
            String sql = "select * from logs where uuid=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);

            // execute query, get resultset and return Log info
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                log = new TextLog();
                log.setId(UUID.fromString(set.getString("uuid")));
                log.setTitle(set.getString("title"));
                log.setContent(set.getString("content"));
            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return log;
    }

    public void createLog(Log log) {
        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get the log
            String sql = "insert into logs (uuid, title, content) values (?, ?, ?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, log.getId().toString());
            statement.setString(2, log.getTitle());
            statement.setString(3, log.getContent());

            // execute query, update resultset
            statement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void updateLog(Log log) {
        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get the log
            String sql = "update logs set title=?, content=? where uuid=?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, log.getTitle());
            statement.setString(2, log.getContent());
            statement.setString(3, log.getId().toString());

            // execute query, update resultset
            statement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void deleteLog(String id) {
        try {
            // get connection to database
            Connection connection = DBConnection.getConnectionToDatabase();

            // write select query to get the log
            String sql = "delete from logs where uuid=?;";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, UUID.fromString(id).toString());

            // execute query, delete resultset
            statement.execute();

        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void createOrUpdateLog(Log log) {
        Log locallog = readLog(log.getId().toString());
        if (locallog == null) {
            createLog(log);
        } else {
            updateLog(log);
        }
    }*/

}
