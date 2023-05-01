import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet(name = "RemoveFromWorkList", value = "/RemoveFromWorkList")
public class RemoveFromWorkList extends HttpServlet {
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
            PreparedStatement insertquery = con.prepareStatement("delete from taskinfo where customerid = ? and employeeid = ? ;");
            insertquery.setInt(1,cid);
            insertquery.setInt(2,eid);
            insertquery.executeUpdate();
            obj.put("success","task removed");

        }
        catch(Exception e)
        {
            e.printStackTrace();
            obj.put("success","not removed");
        }
        out.println(obj);
    }
}
