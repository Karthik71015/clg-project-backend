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

@WebServlet(name = "getInvitationStatus", value = "/getInvitationStatus")
public class getInvitationStatus extends HttpServlet {
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
        String employeeid=request.getParameter("eid");
        int eid = Integer.parseInt(employeeid);
        int cid = Integer.parseInt(customerid);
        JSONObject obj = new JSONObject();
        try{
            Class.forName(dbDriver);
            Connection con = DriverManager.getConnection(dbName,userName,password);
            Statement stmt=con.createStatement();
            String query="select status from Taskinfo where customerid= "+cid+" and employeeid="+eid+" ;";
            ResultSet rs = stmt.executeQuery(query);
            Boolean ifExist=rs.next();
            if(ifExist) {
                obj.put("response","result found");
                int status = rs.getInt(1);
                obj.put("status",status);

            }
            else{
                obj.put("response","no result found");
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        out.println(obj);
    }
}
