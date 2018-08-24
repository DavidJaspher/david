package Conn.DBDemo;

import Conn.DBConnection.DBConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class textbox extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        DBConnect con = new DBConnect();
        String output = "";
        boolean flag=true;
        int i = 0;
        try (PrintWriter out = response.getWriter()) {
            try {
                con.getConnection();
                if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("loaddata")) {
                    output = "<table>";
                    con.read("SELECT s.id,s.DATA FROM test.sample s WHERE STATUS=1");
                    while (con.rs.next()) {
                        flag=false;
                        output += "<tr><td><input type='text' name='data_" + con.rs.getString("id") + "' id='data_" + con.rs.getString("id") + "' value='" + con.rs.getString("data") + "' onchange=\"update(" + con.rs.getString("id") + ");\"/></td>";
                        for (i = 1; i < 10; i++) {
                            if (con.rs.next()) {
                                output += "<td><input type='text' name='data_" + con.rs.getString("id") + "' id='data_" + con.rs.getString("id") + "' value='" + con.rs.getString("data") + "' onchange=\"update(" + con.rs.getString("id") + ");\"/></td>";
                            }
                            if (con.rs.isLast() || con.rs.isAfterLast()) {
                                break;
                            }
                        }
                        if (con.rs.isLast() || con.rs.isAfterLast()) {
                            if (i < 9) {
                                output += "<td><input type='text' name='data_new' id='data_new' value='' onchange=\"add();\"/></td>";
                            } else {
                                output += "<tr><td><input type='text' name='data_new' id='data_new' value='' onchange=\"add();\"/></td></tr>";

                            }
                        }
                        output += "</tr>";
                    }
                    
                    if (flag) {
                            con.insert("INSERT INTO test.sample (id, DATA, STATUS)VALUES  (null, 'First Data', '1');");
                            output+="refresh this page";
                        }
                    
                    out.print(output);
                }
                if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("adddata")) {
                    con.insert("INSERT INTO test.sample (id, DATA, STATUS)VALUES  (null, '" + request.getParameter("addval") + "', '1');");
                }
                if (request.getParameter("option") != null && request.getParameter("option").equalsIgnoreCase("updatedata")) {
                    if (request.getParameter("updateval").equalsIgnoreCase("")) {
                        con.delete("UPDATE  test.sample SET  STATUS = '0' WHERE id = '" + request.getParameter("updateid") + "';");
                    } else {
                        con.update("UPDATE  test.sample SET DATA = '" + request.getParameter("updateval") + "' WHERE id = '" + request.getParameter("updateid") + "';");
                    }
                }
            } catch (Exception e) {
                out.print(e);
            } finally {
                try {
                    con.closeConnection();
                } catch (SQLException ex) {
                    Logger.getLogger(textbox.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
