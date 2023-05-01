import org.json.JSONArray;
import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

@WebServlet(name = "createWork", value = "/createWork")
public class createWork extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                response.setContentType("application/json");
                response.setHeader("Access-Control-Allow-Origin", "*");
                PrintWriter out = response.getWriter();
                String employeeid = request.getParameter("eid");
                String customerid = request.getParameter("cid");
                int eid = Integer.parseInt(employeeid);
                int cid = Integer.parseInt(customerid);
                String dbName = "jdbc:postgresql://localhost:5432/postgres";
                String dbDriver = "org.postgresql.Driver";
                String userName = "postgres";
                String password = "1234";
                JSONObject obj = new JSONObject();
                try{
                    Class.forName(dbDriver);
                    Connection con = DriverManager.getConnection(dbName,userName,password);
                    PreparedStatement insertquery = con.prepareStatement("insert into taskinfo(employeeid,customerid,status) values(?,?,?);");
                    insertquery.setInt(1,eid);
                    insertquery.setInt(2,cid);
                    insertquery.setInt(3,0);
                    insertquery.executeUpdate();
                    obj.put("success","task assigned");

                }
                catch(Exception e)
                {
                   e.printStackTrace();
                   obj.put("success","not assigned");
                }
                out.println(obj);
    }
}
