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
import dao.questionsDAO;
import model.questions;

@WebServlet("/api/questions/add")
public class AddQuestionServlet extends HttpServlet {
    private Gson gson = new Gson();
    private questionsDAO qDAO = new questionsDAO();

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

            int examId       = json.get("examId").getAsInt();
            String question  = json.get("question").getAsString();
            String optionA   = json.get("optionA").getAsString();
            String optionB   = json.get("optionB").getAsString();
            String optionC   = json.get("optionC").getAsString();
            String optionD   = json.get("optionD").getAsString();
            char correct     = json.get("correctOption").getAsString().charAt(0);
            int marks        = json.get("marks").getAsInt();

            // Using constructor directly (no setters needed)
            questions q = new questions(examId, question, optionA, optionB, optionC, optionD, correct, marks);

            boolean success = qDAO.addQuestion(q);
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
