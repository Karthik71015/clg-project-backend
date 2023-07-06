import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet(name = "getEmployeeDetails", value = "/getEmployeeDetails")
public class getEmployeeDetails extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
               response.addHeader("ACCESS-CONTROL-ALLOW-ORIGIN","*");
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
                   String query = "select * from employees where employeeid = '"+id+"';";
                   ResultSet rs = stmt.executeQuery(query);
                   JSONArray employeedetailsArray = new JSONArray();
                   while(rs.next())
                   {
                       JSONObject employeedetails = new JSONObject();

                       String employeename=rs.getString(2);
                       String city = rs.getString(5);
                       String area = rs.getString(6);
                       String designation = rs.getString(7);
                       int available = rs.getInt(11);
                       int charges = rs.getInt(8);
                       employeedetails.put("employeename",employeename);
                       employeedetails.put("designation",designation);
                       employeedetails.put("charges",charges);
                       employeedetails.put("city",city);
                       employeedetails.put("area",area);
                       employeedetails.put("available",available);
                       employeedetailsArray.put(employeedetails);

                   }
                   obj.put("result",employeedetailsArray);


               }
               catch(Exception e)
               {
                   e.printStackTrace();
               }
               out.println(obj);
               System.out.println(obj);
    }
}
