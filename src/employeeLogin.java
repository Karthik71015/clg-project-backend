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

@WebServlet(name = "employeeLogin", value = "/employeeLogin")
public class employeeLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.addHeader("Access-Control-Allow-Origin","*");
        PrintWriter out=response.getWriter();
        String dbName = "jdbc:postgresql://localhost:5432/postgres";
        String dbDriver = "org.postgresql.Driver";
        String userName = "postgres";
        String password = "1234";
        String mail=request.getParameter("mail");
        String pass=request.getParameter("pass");
        System.out.println(mail);
        System.out.println(password);
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt=con.createStatement();
            String sql="SELECT * FROM employees where email='"+mail+"'and "+"password='"+pass+"'";
            ResultSet rs = stmt.executeQuery(sql);
            Boolean ifExist=rs.next();
            if(ifExist) {
                obj.put("success","login successfully");
                String query = "select employeeid from employees where email='"+mail+"';";
                rs=stmt.executeQuery(query);
                rs.next();
                int id = rs.getInt(1);
                obj.put("employeeid",id);

            }
            else{
                obj.put("success","login failed incorrect username or password");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        out.println(obj);
    }
}
