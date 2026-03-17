package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import dao.questionsDAO;
import model.questions;

@WebServlet("/api/questions")
public class GetQuestionsServlet extends HttpServlet {
    private Gson gson = new Gson();
    private questionsDAO qDAO = new questionsDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");
        PrintWriter out = response.getWriter();
        try {
            String examIdParam = request.getParameter("examId");
            if (examIdParam == null || examIdParam.isEmpty()) {
                response.setStatus(400);
                out.print("{\"error\":\"examId is required\"}");
                return;
            }
            int examId = Integer.parseInt(examIdParam);
            List<questions> list = qDAO.getquestionsByExamId(examId);
            out.print(gson.toJson(list));
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
        }
        out.flush();
    }
}
