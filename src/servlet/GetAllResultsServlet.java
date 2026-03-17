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
import dao.ExamResultDAO;
import model.ExamResult;

@WebServlet("/api/results/all")
public class GetAllResultsServlet extends HttpServlet {

    private Gson gson = new Gson();
    private ExamResultDAO resultDAO = new ExamResultDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        PrintWriter out = response.getWriter();

        try {
            List<ExamResult> results = resultDAO.getAllResults();
            out.print(gson.toJson(results));
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }

        out.flush();
    }
}
