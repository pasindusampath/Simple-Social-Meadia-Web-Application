package com.algonquin.loggy.services;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import com.algonquin.loggy.beans.Log;


public interface ApplicationService {

    public Map<String, List<Log>> readLogs();

    public List<Log> readLog(String id);

    public void createLog(Log log);

    public void updateLog(Log log);

    public void deleteLog(Log log);

    public void createOrUpdateLog(Log log);

    public void addUser(String id);

    public boolean isUserAvailable(String id);

    public List<Log> getLogsOfUser(String uid);

    public Log getLogOfUser(String uid,String logId);
    public int getNewLogId(String uid);

}
