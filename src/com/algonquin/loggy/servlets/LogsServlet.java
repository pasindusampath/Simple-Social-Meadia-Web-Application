package com.algonquin.loggy.servlets;

import com.algonquin.loggy.beans.FileUpload;
import com.algonquin.loggy.beans.Log;
import com.algonquin.loggy.beans.TextLog;
import com.algonquin.loggy.dao.ApplicationDao;
import com.algonquin.loggy.inmemory.ApplicationInMemory;
import com.algonquin.loggy.services.ApplicationService;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;


/**
 * Servlet implementation class LogsServlet
 */
//@WebServlet(description = "Loggy Logs", urlPatterns = { "/LogsServlet" })
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10,      // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class LogsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    ApplicationService logs;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LogsServlet() {
        super();
        this.logs = new ApplicationDao();
//        this.logs = new ApplicationDao();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*String action = request.getParameter("action");
        if ("delete".equals(action)) {
            delete(request);
        }*/

        String title = "";
        String content = "";
        String id = request.getParameter("id");
        String uid = request.getParameter("uid");

        // Render response.
        PrintWriter writer = response.getWriter();
        String htmlResponse = printOutHead(writer);
        htmlResponse += printOutBodyForm(uid, title, content);
        // Read all logs, assign to local variable and sent to printOutBodyList
        //Map<UUID, Log> logs = this.logs.readLogs();
        List<Log> logsOfUser = this.logs.getLogsOfUser(uid);

        writer.write(htmlResponse);
        printOutBodyList(logsOfUser,writer);
        writer.write(printOutFoot());
        writer.flush();
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Log log = null;
        String uid = request.getParameter("uid");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String action = request.getParameter("action");
        Part filePart = null;
        FileUpload uploadedFile = null;
        try {
            filePart= request.getPart("file");
            if (filePart != null && filePart.getSize() > 0) {
                // Create a FileUpload object based on the file type
                uploadedFile= new FileUpload(filePart);
            }


        } catch (IOException | ServletException e) {
            System.out.println(e.getMessage()+" Error Handled Success on Logs Servlet");
        }


        if(action!=null){
            if(action.equals("Edit")||action.equals("Delete")){
                try {
                    System.out.println("String log id : "+id);
                    System.out.println("String User id : "+uid);
                    Log logOfUser = this.logs.getLogOfUser(uid, id);
                    System.out.println("String log id : "+logOfUser.getLogId());
                    System.out.println("String User id : "+logOfUser.getId());
                    request.setAttribute("log", logOfUser);
                    request.getRequestDispatcher("/edit_log.jsp").forward(request, response);
                } catch (ServletException | IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            if(action.equals("updateLog")){
                TextLog textLog = new TextLog(title, content, uid, uploadedFile, id);
                String submit = request.getParameter("submit");
                if(submit.equals("Update"))
                    this.logs.updateLog(textLog);
                if(submit.equals("Delete"))
                    this.logs.deleteLog(textLog);
                try {
                    doGet(request, response);
                } catch (ServletException | IOException e) {
                    System.out.println(e.getMessage()+"Error Handled Success on Logs Servlet");
                }
                return;

            }
            System.out.println(action);

        }
        if(!this.logs.isUserAvailable(uid)){
            this.logs.addUser(uid);
        }
        String type="";
        if(((type=request.getParameter("actionType")) != null && type.equals("login"))){


        }else {
            if (id == null || "".equals(id)) {
                // Initialize id and continue with the rendering.
                TextLog textLog = new TextLog(title, content, uid,uploadedFile,String.valueOf(this.logs.getNewLogId(uid)));
                this.logs.createLog(textLog);
                //System.out.println("Log Created"+uid);
            } else {
                // Read the record from memory.

            }
        }


        // Process GET for rendering the page with updates.
        try {
            doGet(request, response);
        } catch (ServletException | IOException e) {
            System.out.println(e.getMessage()+"Error Handled Success on Logs Servlet");
        }
    }

    private void delete(HttpServletRequest request) throws ServletException, IOException {
        String id = request.getParameter("id");
        if (id != null && !id.equals("null")) {
            // Remove the log.
            //this.logs.deleteLog(id);
        }
    }

    // This is the HTML code generated entirely from the Servlet.
    private String printOutHead(Writer writer) {
        try {
            writer.write("<!DOCTYPE html>\n" + "	<html lang=\"en\">\n" + "<head>\n");
            writer.write( "<title>Example</title>\n" );
            writer.write("<style>\n");
            writer.write("body { font-family: Arial, sans-serif; background-color: #f0f0f0; margin: 0; padding: 0; }");
            writer.write(".masthead { padding: 80px; background-color: #007bff; text-align: center; }");
            writer.write(".masthead h1 { font-size: 36px; margin-bottom: 20px; }");
            writer.write("form { max-width: 600px; margin: 0 auto; background-color: #fff; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.2); }");
            writer.write("label { display: block; font-weight: bold; margin-bottom: 5px; }");
            writer.write("input[type=\"text\"], input[type=\"file\"] { width: 97%; padding: 10px; margin-bottom: 15px; border: 1px solid #ccc; border-radius: 4px; }");
            writer.write("input[type=\"submit\"], input[type=\"button\"] { background-color: #007bff; color: #fff; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; }");
            writer.write("input[type=\"submit\"]:hover, input[type=\"button\"]:hover { background-color: #0056b3; }");
            writer.write(".custom-file-upload { display: inline-block; padding: 8px 20px; background-color: #007bff; color: #fff; border: none; border-radius: 4px; cursor: pointer; }");
            writer.write(".custom-file-upload:hover { background-color: #0056b3; }");
            writer.write(".log-container { border: 1px solid #ccc; padding: 10px; margin: 10px; }");
            writer.write(".log-container h2 { margin: 0; padding: 0; }");
            writer.write(".log-container img,.log-container video { display: block; width: 100%; height: auto; margin: 10px 0; }");
            writer.write(".log-container p { margin: 5px 0; }");
            writer.write(".log-container input[type=\"submit\"] { background-color: #007bff; color: #fff; padding: 8px 16px; margin-right: 5px; border: none; border-radius: 4px; cursor: pointer; }");
            writer.write(".log-container input[type=\"submit\"]:hover { background-color: #0056b3; }");
            writer.write("</style>\n");
            writer.write("</head>\n");
            writer.write("<body id=\"page-top\">\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";

    }

    private String printOutBodyForm(String id, String title, String content) {
        String out = "        <!-- Form Section-->\n"
                + "        <header class=\"masthead bg-primary text-white text-center\">\n"
                + "          <h1>Log ahead</h1>\n" + "        <form action=\"logs\" method=\"post\" enctype=\"multipart/form-data\">\n"
                + "          <input type=\"hidden\" name=\"uid\" value=\"" + id + "\">"
                + "          <label for=\"fname\">Title:</label><br>\n"
                + "          <input type=\"text\" id=\"title\" name=\"title\" value=\"" + title + "\"><br>\n"
                + "          <label for=\"lname\">Content:</label><br>\n"
                + "          <input type=\"text\" id=\"content\" name=\"content\" value=\"" + content + "\"><br><br>\n"
                + "          <input type=\"file\" id=\"fileInput\" name=\"file\" accept=\".mp3, .jpg, .jpeg, .png, .mp4\"><br>\n"
                + "          <input type=\"submit\" value=\"Submit\">\n"
                + "          <input type=\"button\" value=\"Cancel\" onclick=\"window.location='logs'\">\n"
                + "        </form>\n" + "        </header>\n";
        return out;
    }

    private String printOutBodyList(List<Log> list,PrintWriter writer) {
        // Body list top.
        writer.write("\n" + "<!-- Content Section-->\n" + "<!-- Loggy Items-->\n");
        writer.write("<div class=\"row\">\n");
        String out = ""
                + " ";
        // This is the actual List.
        for (Log logItem : list) {
            printOutBodyItem(logItem,writer);
        }
        /*writer.write("</tbody>\n");
        writer.write("</table>\n");*/
        writer.write("</div>\n");
        /*out += "</tbody>\n" + "</table>\n";
        // Body list bottom.
        out += "</div>\n";*/
        return out;
    }

    private String printOutBodyItem(Log log,PrintWriter writer) {
        String imagePath = "C:\\Users\\Pasindu Sampath\\Desktop\\loggy-lab-master\\resource\\test.jpg";
        writer.write("<div class=\"log-container\">");
        writer.write("<form action=\"/test/logs\" method=\"post\">");
        writer.write("<input type=\"hidden\" name=\"id\" value=\"" + log.getLogId() + "\"/>");
        writer.write("<input type=\"hidden\" name=\"uid\" value=\"" + log.getId() + "\"/>");
        writer.write("<div style=\"border: 1px solid #ccc; padding: 10px; width: 300px; margin: auto;\">");
        writer.write("<h2 style=\"margin: 0; padding: 0;\">"+log.getTitle()+"</h2>");
        if(log.getFile()!=null){
            if(log.getFile().getContentType().contains("image")){
                writer.write("<img src=\"data:image/jpeg;base64," + java.util.Base64.getEncoder().encodeToString(log.getFile().getFileData()) + "\" alt=\"Image Description\" style=\"width: 100%; height: auto;\">\n");
            }
            if(log.getFile().getContentType().contains("audio")){
                writer.write("<audio controls>\n");
                writer.write("  <source src=\"data:audio/mpeg;base64," + java.util.Base64.getEncoder().encodeToString(log.getFile().getFileData()) + "\" type=\"audio/mpeg\">\n");
                writer.write("  Your browser does not support the audio element.\n");
                writer.write("</audio>\n");
            }
            if(log.getFile().getContentType().contains("video")){
                writer.write("<video controls>\n");
                writer.write("  <source src=\"data:video/mp4;base64," + java.util.Base64.getEncoder().encodeToString(log.getFile().getFileData()) + "\" type=\"video/mp4\">\n");
                writer.write("  Your browser does not support the video element.\n");
                writer.write("</video>\n");
            }
        }
        writer.write("<p style=\"margin: 5px 0;\">"+log.getContent()+"</p>");
        writer.write("<input type=\"submit\" name=\"action\" value=\"Edit\">");
        writer.write("<input type=\"submit\" name=\"action\" value=\"Delete\">");
        writer.write("</div>\n");
        writer.write("</div>\n");
        writer.write("</form>\n");
        /*writer.write("<tr>\n");
        writer.write("<th scope=\"row\">" + id.substring(0, 8)+ "</th>\n");
        writer.write("<td>"+ title + "</td>\n" );
        writer.write("<td>"+content+ "</td>\n");
        writer.write("<td>" + "<a href=\"logs?id="+id+"\">Edit</a>\n"+"<a href=\"logs?id="+id+"&action=delete\">Delete</a>\n"+"</td>\n");
        writer.write("</tr>\n");*/
        /*String out = "<tr>\n"
                +"<th scope=\"row\">" + id.substring(0, 8)+ "</th>\n" +
                "<td>"+ title + "</td>\n" +
                "<td>"+content+ "</td>\n" +
                "<td>" + "<a href=\"logs?id="+id+"\">Edit</a>\n"+"<a href=\"logs?id="+id+"&action=delete\">Delete</a>\n"+"</td>\n"
                + "</tr>\n";*/

        return "";
    }

    private String printOutFoot() {
        String out = "    </body>\n" + "</html>\n";

        return out;
    }

}
