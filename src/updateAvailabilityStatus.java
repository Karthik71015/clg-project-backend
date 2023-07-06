import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

@WebServlet(name = "updateAvailabilityStatus", value = "/updateAvailabilityStatus")
public class updateAvailabilityStatus extends HttpServlet {
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
        String sts = request.getParameter("status");
        int invitestatus = Integer.parseInt(sts);
        int eid = Integer.parseInt(employeeid);
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt=con.createStatement();
            String query="update employees set availabilitystatus = "+invitestatus+" where employeeid ="+eid+";";
            stmt.executeUpdate(query);
            obj.put("response","updated");

        }
        catch (Exception e)
        {
            obj.put("response","not updated");
            e.printStackTrace();
        }
        out.println(obj);
    }
}
