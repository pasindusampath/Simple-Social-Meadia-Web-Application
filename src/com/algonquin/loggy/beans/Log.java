package com.algonquin.loggy.beans;

import java.util.Date;
import java.util.UUID;

public abstract class Log {
    private String id;
    private String logId;
    private String title;
    private String content;
    private Date createTimestamp;
    private FileUpload file;
    public Log() {
    }

    public Log(String title, String content,String uid,FileUpload file) {
        this.id = uid;
        this.title = title;
        this.content = content;
        this.createTimestamp = new Date();
        this.file = file;
    }

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the id
     */
    public String getLogId() {
        return logId;
    }

    /**
     * @param id the id to set
     */
    public void setLogId(String id) {
        this.logId = id;
    }



    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the createTimestamp
     */
    public Date getCreateTimestamp() {
        return createTimestamp;
    }

    /**
     * @param createTimestamp the createTimestamp to set
     */

    public void setCreateTimestamp(Date createTimestamp) {
        this.createTimestamp = createTimestamp;
    }


    public FileUpload getFile() {
        return file;
    }

    public void setFile(FileUpload file) {
        this.file = file;
    }
}
