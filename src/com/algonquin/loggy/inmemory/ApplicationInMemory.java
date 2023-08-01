package com.algonquin.loggy.inmemory;

import java.util.*;

import com.algonquin.loggy.beans.Log;
import com.algonquin.loggy.beans.TextLog;
import com.algonquin.loggy.services.ApplicationService;

public class ApplicationInMemory implements ApplicationService {

    private Map<String, List<Log>> logs;

    public ApplicationInMemory() {
        this.logs = new LinkedHashMap<String, List<Log>>();
    }

    @Override
    public Map<String, List<Log>> readLogs() {
        return logs;
    }

    /*public List<Log> readLogs(String id) {
        return logs.get(id);
    }*/

    @Override
    public List<Log> readLog(String id) {
        return logs.get(id);
    }

    @Override
    public void createLog(Log log) {
        List<Log> logs = this.logs.get(log.getId());
        logs.add(log);

    }

    @Override
    public void updateLog(Log log) {
        List<Log> logs = this.logs.get(log.getId());
        Log log1 = findLog(logs, log);
        if(log.getFile()==null)log.setFile(log1.getFile());
        logs.remove(log1);
        logs.add(log);
    }

    @Override
    public void deleteLog(String id) {
        logs.remove(UUID.fromString(id));
    }

    @Override
    public void createOrUpdateLog(Log log) {
        List<Log> locallog = readLog(log.getId().toString());
        Log log1 = findLog(locallog, log);
        if (log1 == null) {
            createLog(log);
        } else {
            updateLog(log);
        }
    }

    @Override
    public void addUser(String id) {
        logs.put(id,new ArrayList<Log>());
    }

    @Override
    public boolean isUserAvailable(String id) {
        List<Log> logs = this.logs.get(id);
        return logs != null;
    }

    @Override
    public List<Log> getLogsOfUser(String uid) {
        return this.logs.get(uid);
    }


    public Log findLog(List<Log> logs,Log log){
        Log te = null;
        for (Log temp : logs) {
            if(temp.getLogId().equals(log.getLogId())){
                te=temp;
            }
        }
        return te;
    }

    public Log getLogOfUser(String uid,String logId){
        List<Log> logsOfUser = getLogsOfUser(uid);
        TextLog textLog = new TextLog();
        textLog.setLogId(logId);
        Log log = findLog(logsOfUser, textLog);
        return new TextLog(log.getTitle(),log.getContent(),log.getId(),log.getFile(),log.getLogId());
    }

    public int getNewLogId(String uid){
        List<Log> logsOfUser = getLogsOfUser(uid);
        if(logsOfUser.size()==0)return 1;
        int maxId = Integer.MIN_VALUE;
        for (Log log : logsOfUser) {
            if(Integer.parseInt(log.getLogId()) > maxId){
                maxId = Integer.parseInt(log.getLogId());
            }
        }
        return maxId+1;
    }

}
