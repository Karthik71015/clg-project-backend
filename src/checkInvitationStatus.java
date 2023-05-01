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

@WebServlet(name = "checkInvitationStatus", value = "/checkInvitationStatus")
public class checkInvitationStatus extends HttpServlet {
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
        int cid = Integer.parseInt(customerid);
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt=con.createStatement();
            String query="select e.employeeid,e.employeename,e.city,t.status from employees e inner join Taskinfo t on e.employeeid = t.employeeid where t.customerid="+cid+";";
            ResultSet rs = stmt.executeQuery(query);
            JSONArray employeedetailsArray = new JSONArray();
            Boolean ifExist=rs.next();
            if(ifExist) {
                obj.put("response","results found");
                rs = stmt.executeQuery(query);
                while(rs.next())
                {
                    JSONObject employeedetails = new JSONObject();
                    int employeeid=rs.getInt(1);
                    String employeename=rs.getString(2);
                    String city  = rs.getString(3);
                    int status = rs.getInt(4);
                    employeedetails.put("employeeid",employeeid);
                    employeedetails.put("employeename",employeename);
                    employeedetails.put("city",city);
                    employeedetails.put("status",status);
                    employeedetailsArray.put(employeedetails);

                }
                obj.put("result",employeedetailsArray);
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
