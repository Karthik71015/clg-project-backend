import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.sql.*;
@WebServlet(name = "Login", value = "/Login")
public class Login extends HttpServlet
{
     public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException,ServletException
     {
         response.setContentType("text/html");
         response.addHeader("Access-Control-Allow-Origin","*");
         PrintWriter out = response.getWriter();
         String mail = request.getParameter("mail");
         String password = request.getParameter("pass");
         System.out.println(mail);
         System.out.println(password);


     }
}
