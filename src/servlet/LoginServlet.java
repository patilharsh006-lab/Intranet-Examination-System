package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dao.UserDAO;
import model.users;

@WebServlet("/api/login")
public class LoginServlet extends HttpServlet {
    private Gson gson = new Gson();
    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
        PrintWriter out = response.getWriter();
        try {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) sb.append(line);
            JsonObject json = gson.fromJson(sb.toString(), JsonObject.class);
            String email = json.get("email").getAsString();
            String password = json.get("password").getAsString();
            users user = userDAO.login(email, password);
            if (user != null) {
                JsonObject resp = new JsonObject();
                resp.addProperty("success", true);
                resp.addProperty("userId", user.getUserId());
                resp.addProperty("name", user.getName());
                resp.addProperty("role", user.getRole());
                out.print(gson.toJson(resp));
            } else {
                response.setStatus(401);
                out.print("{\"success\":false,\"message\":\"Invalid email or password\"}");
            }
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
        }
        out.flush();
    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(200);
    }
}