import org.json.JSONObject;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@WebServlet(name = "sendMessage", value = "/sendMessage")
public class sendMessage extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        JSONObject obj = new JSONObject();
        try {
            // Construct data
            String apiKey = "apikey=" + "NjI3MDU4Njg3OTQyNzI2YTZiNzk0Zjc4NjMzMjQzNjg=";
            String message = "&message=" + "hello world";
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + "916383101570";

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();
            obj.put("success","message sent");
            System.out.println(stringBuffer.toString());
        } catch (Exception e) {
            System.out.println("Error SMS " + e);
            e.printStackTrace();
            obj.put("success","message not sent");
        }
        out.println(obj);
    }
}
