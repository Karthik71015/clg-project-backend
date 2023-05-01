import org.json.JSONArray;
import org.json.JSONObject;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@WebServlet(name = "updateInvitationStatus", value = "/updateInvitationStatus")
public class updateInvitationStatus extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String dbName = "jdbc:postgresql://localhost:5432/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "1234";
        String customerid=request.getParameter("cid");
        String employeeid = request.getParameter("eid");
        String sts = request.getParameter("status");
        int invitestatus = Integer.parseInt(sts);
        int eid = Integer.parseInt(employeeid);
        int cid = Integer.parseInt(customerid);
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt=con.createStatement();
            String query="update Taskinfo set status = "+invitestatus+" where customerid="+cid+" and employeeid ="+eid+";";
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
