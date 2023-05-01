import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet(name = "reviewAndRating", value = "/reviewAndRating")
public class reviewAndRating extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        String employeeid = request.getParameter("eid");
        int eid = Integer.parseInt(employeeid);
        String customerid = request.getParameter("cid");
        int cid = Integer.parseInt(customerid);
        String review = request.getParameter("review");
        String ratings = request.getParameter("rating");
        int rating = Integer.parseInt(ratings);
        String dbName = "jdbc:postgresql://localhost:5432/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        try{
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, "1234");
            Statement stmt = conn.createStatement();
            String query = "select * from ratings where customerid=" + cid + " and employeeid = "+ eid +" ;";
            ResultSet rs=stmt.executeQuery(query);
            boolean check = rs.next();
            if(check)
            {
                obj.put("success","rating already exists");
            }
            else {
                PreparedStatement insertquery=conn.prepareStatement("insert into ratings(employeeid,customerid,ratings,review) values(?,?,?,?)");
                insertquery.setInt(1,eid);
                insertquery.setInt(2,cid);
                insertquery.setInt(3,rating);
                insertquery.setString(4,review);
                insertquery.executeUpdate();
                obj.put("success","rated successfully");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        out.println(obj);
    }
}
