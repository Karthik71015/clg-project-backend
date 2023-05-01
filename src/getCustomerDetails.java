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

@WebServlet(name = "getCustomerDetails", value = "/getCustomerDetails")
public class getCustomerDetails extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.addHeader("Access-Control-Allow-Origin","*");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        String dbName = "jdbc:postgresql://localhost:5432/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "1234";
        JSONObject obj = new JSONObject();
        String id = request.getParameter("id");
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt = con.createStatement();
            String query = "select * from customers where customerid = "+id+";";
            ResultSet rs = stmt.executeQuery(query);
            JSONArray customerDetailsArray = new JSONArray();
            while(rs.next())
            {
                JSONObject customerdetails = new JSONObject();

                String customername=rs.getString(2);
                String mobilenumber = rs.getString(3);
                String address = rs.getString(4);
                String city = rs.getString(5);
                String area = rs.getString(6);
                String mail = rs.getString(7);
                customerdetails.put("customername",customername);
                customerdetails.put("mobilenumber",mobilenumber);
                customerdetails.put("address",address);
                customerdetails.put("city",city);
                customerdetails.put("area",area);
                customerdetails.put("mail",mail);
                customerDetailsArray.put(customerdetails);

            }
            obj.put("result",customerDetailsArray);


        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        out.println(obj);
    }
}
