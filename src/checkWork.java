import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "checkWork", value = "/checkWork")
public class checkWork extends HttpServlet {
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
            Statement stmt=con.createStatement();
            String query = "select * from Taskinfo where employeeid="+eid+" and  customerid="+cid+";";
            ResultSet rs = stmt.executeQuery(query);
            Boolean ifexist = rs.next();
            if(ifexist)
            {
                obj.put("success","task exist");
                query = "select status from Taskinfo where employeeid ="+eid+" and customerid ="+cid+";";
                rs = stmt.executeQuery(query);
                rs.next();
                int status = rs.getInt(1);
                obj.put("status",status);
            }
            else {
                obj.put("success","task not exist");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        out.println(obj);
    }
}
