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

@WebServlet(name = "getAllEmployeeDetails", value = "/getAllEmployeeDetails")
public class getAllEmployeeDetails extends HttpServlet {
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
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt=con.createStatement();
            String sql="SELECT * FROM employees ;";
            ResultSet rs = stmt.executeQuery(sql);
            JSONArray employeedetailsArray = new JSONArray();
            Boolean ifExist=rs.next();
            if(ifExist) {
                obj.put("response","results found");
                rs = stmt.executeQuery(sql);
                while(rs.next())
                {
                    JSONObject employeedetails = new JSONObject();
                    int id = rs.getInt(1);
                    String employeename=rs.getString(2);
                    String city = rs.getString(5);
                    String area = rs.getString(6);
                    String designation = rs.getString(7);
                    int charges = rs.getInt(8);
                    int available = rs.getInt(11);
                    String query = "select avg(ratings) from ratings where employeeid = "+id+";";
                    Statement stmt1 = con.createStatement();
                    ResultSet rs1 = stmt1.executeQuery(query);
                    rs1.next();
                    int ratings = rs1.getInt(1);
                    employeedetails.put("id",id);
                    employeedetails.put("employeename",employeename);
                    employeedetails.put("designation",designation);
                    employeedetails.put("charges",charges);
                    employeedetails.put("city",city);
                    employeedetails.put("area",area);
                    employeedetails.put("available",available);
                    employeedetails.put("rating",ratings);
                    rs1.close();
                    employeedetailsArray.put(employeedetails);

                }
                obj.put("result",employeedetailsArray);
            }
            else{
                obj.put("response","no results found");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        out.println(obj);
    }
}
