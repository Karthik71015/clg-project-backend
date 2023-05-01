import org.json.JSONArray;
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

@WebServlet(name = "getEmployeeRating", value = "/getEmployeeRating")
public class getEmployeeRating extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String dbName = "jdbc:postgresql://localhost:5432/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "1234";
        String employeeid=request.getParameter("eid");
        int eid = Integer.parseInt(employeeid);
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt=con.createStatement();
            String query="select c.customerid,c.customername,r.review,r.ratings from customers c inner join ratings r on c.customerid = r.customerid where r.employeeid="+eid+";";
            ResultSet rs = stmt.executeQuery(query);
            JSONArray customerdetailsArray = new JSONArray();
            Boolean ifExist=rs.next();
            if(ifExist) {
                obj.put("response","results found");
                rs = stmt.executeQuery(query);
                while(rs.next())
                {
                    JSONObject customerdetails = new JSONObject();
                    int customerid=rs.getInt(1);
                    String customername=rs.getString(2);
                    String review  = rs.getString(3);
                    int rating = rs.getInt(4);
                    customerdetails.put("customerid",customerid);
                    customerdetails.put("customername",customername);
                    customerdetails.put("review",review);
                    customerdetails.put("rating",rating);
                    customerdetailsArray.put(customerdetails);

                }
                obj.put("result",customerdetailsArray);
            }
            else{
                obj.put("response","no results found");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        out.println(obj);
    }
}
