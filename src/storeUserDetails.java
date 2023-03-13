import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.PrintWriter;
import org.json.JSONObject;
import java.sql.*;
import java.io.IOException;

@WebServlet(name = "storeUserDetails", value = "/storeUserDetails")
public class storeUserDetails extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        String name=request.getParameter("name");
        String mobilenumber = request.getParameter("mobilenumber");
        String address = request.getParameter("address");
        String area = request.getParameter("area");
        String city = request.getParameter("city");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String dbName = "jdbc:postgresql://localhost:5432/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        System.out.println(name + "," + mobilenumber + "," + address + "," + area + "," + city + "," + email + "," + password );
        try{
            Class.forName(dbDriver);
            Connection conn = DriverManager.getConnection(dbName, userName, "1234");
            Statement stmt = conn.createStatement();
            String query = "select * from customers where email='" + email + "';";
            ResultSet rs=stmt.executeQuery(query);
            boolean check = rs.next();
            if(check)
            {
                obj.put("success","user already exists");
            }
            else {
                PreparedStatement insertquery=conn.prepareStatement("insert into customers(customername,mobilenumber,address,city,area,email,password) values(?,?,?,?,?,?,?)");
                insertquery.setString(1,name);
                insertquery.setString(2,mobilenumber);
                insertquery.setString(3,address);
                insertquery.setString(4,city);
                insertquery.setString(5,area);
                insertquery.setString(6,email);
                insertquery.setString(7,password);
                insertquery.executeUpdate();
                obj.put("success","successfully registered");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        out.println(obj);
    }
}
