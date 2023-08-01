package com.algonquin.loggy.beans;

import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

public class FileUpload {
    private String fileName;
    private String contentType;
    private byte[] fileData;

    public FileUpload(Part part) {
        this.fileName = getFileName(part);
        this.contentType = part.getContentType();
        this.fileData = readDataFromPart(part);
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        String[] parts = contentDisposition.split(";");

        for (String partInfo : parts) {
            if (partInfo.trim().startsWith("filename")) {
                return partInfo.substring(partInfo.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }

    private byte[] readDataFromPart(Part part) {
        try (InputStream input = part.getInputStream()) {
            return input.readAllBytes();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters and setters (optional) for the properties

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getFileData() {
        return fileData;
    }

    public void setFileData(byte[] fileData) {
        this.fileData = fileData;
    }
}
