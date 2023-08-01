<%@ page import="com.algonquin.loggy.beans.Log" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Log Details</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            margin: 20px;
        }

        h1 {
            color: #007bff;
            text-align: center;
        }

        form {
            max-width: 600px;
            margin: 0 auto;
            background-color: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
        }

        label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        input[type="text"],
        textarea {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        textarea {
            resize: vertical;
            height: 200px;
        }

        img {
            display: block;
            max-width: 300px;
            height: auto;
            margin: 10px auto;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #007bff;
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        audio,
        video {
            display: block;
            width: 100%;
            max-width: 300px;
            margin: 10px auto;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"]:hover {
            background-color: #0056b3;
        }
        input[type="file"] {
            display: none;
        }

        .custom-file-upload {
            display: inline-block;
            padding: 8px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }

        .custom-file-upload:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<h1>Log Details</h1>
<%Log log=(Log) request.getAttribute("log");%>

<form action="/test/logs" method="post" enctype="multipart/form-data">
    <input type="hidden" name="uid" value="<%=log.getId()%>">
    <input type="hidden" name="id" value="<%=log.getLogId()%>">
    <input type="hidden" name="action" value="updateLog">
    <label for="title">Title:</label>
    <input type="text" id="title" name="title" value="<%=log.getTitle()%>"><br>

    <label for="content">Content:</label>
    <textarea id="content" name="content"><%=log.getContent()%></textarea><br>

    <%if(log.getFile()!=null){%>
        <%if (log.getFile().getContentType().contains("image")){%>
            <img name="data" src="data:image/jpeg;base64,<%=java.util.Base64.getEncoder().encodeToString(log.getFile().getFileData())%>" alt="Image Description">
        <%}%>
        <%if(log.getFile().getContentType().contains("audio")){%>
            <audio controls>
                <source src="data:audio/mpeg;base64,<%=java.util.Base64.getEncoder().encodeToString(log.getFile().getFileData())%>" type="audio/mpeg">
                Your browser does not support the audio element.
            </audio>
        <%}%>
        <%if(log.getFile().getContentType().contains("video")){%>
            <video controls>
                <source src="data:video/mp4;base64,<%=java.util.Base64.getEncoder().encodeToString(log.getFile().getFileData())%>" type="video/mp4">
                Your browser does not support the video element.
            </video>
        <%}%>
    <%}%>
    <label for="fileInput" class="custom-file-upload">Change Media</label>
    <input type="file" id="fileInput" name="file" accept=".mp3, .jpg, .jpeg, .png, .mp4">
    <input name="submit" type="submit" value="Update">
    <input name="submit" type="submit" value="Delete">
</form>

</body>
</html>
