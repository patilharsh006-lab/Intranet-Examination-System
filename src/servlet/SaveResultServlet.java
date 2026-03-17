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
import dao.ExamResultDAO;
import model.ExamResult;

@WebServlet("/api/results/save")
public class SaveResultServlet extends HttpServlet {
    private Gson gson = new Gson();
    private ExamResultDAO resultDAO = new ExamResultDAO();

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
            ExamResult result = new ExamResult();
            result.setUserId(json.get("userId").getAsInt());
            result.setExamId(json.get("examId").getAsInt());
            result.setScore(json.get("score").getAsInt());
            result.setTotalMarks(json.get("totalMarks").getAsInt());
            result.setTimeTaken(json.get("timeTaken").getAsInt());
            boolean success = resultDAO.saveResult(result);
            if (success) {
                out.print("{\"success\":true}");
            } else {
                out.print("{\"success\":false}");
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