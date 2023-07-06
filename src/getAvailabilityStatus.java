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

@WebServlet(name = "getAvailabilityStatus", value = "/getAvailabilityStatus")
public class getAvailabilityStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String dbName = "jdbc:postgresql://localhost:5432/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "1234";
        String employeeid = request.getParameter("eid");
        int eid = Integer.parseInt(employeeid);
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt=con.createStatement();
            String query="select availabilitystatus from employees where employeeid ="+eid+";";
            ResultSet rs = stmt.executeQuery(query);
            Boolean check= rs.next();
            if(check)
            {
                obj.put("response","status obtained");
                int status = rs.getInt(1);
                obj.put("status",status);
            }
            else {
                obj.put("response","error");
            }


        }
        catch (Exception e)
        {
            obj.put("response","error");
            e.printStackTrace();
        }
        out.println(obj);
    }
}
