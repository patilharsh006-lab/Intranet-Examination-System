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

// Maps to frontend: apiGet('/api/results?userId=1')
// Loads all past results for a student
@WebServlet("/api/results")
public class GetResultsServlet extends HttpServlet {

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
            String userIdParam = request.getParameter("userId");

            if (userIdParam == null || userIdParam.isEmpty()) {
                response.setStatus(400);
                out.print("{\"error\":\"userId parameter is required\"}");
                return;
            }

            int userId = Integer.parseInt(userIdParam);

            // Call your existing DAO
            List<ExamResult> results = resultDAO.getResultsByUserId(userId);

            // Gson will automatically convert Timestamp to a readable string
            out.print(gson.toJson(results));

        } catch (NumberFormatException e) {
            response.setStatus(400);
            out.print("{\"error\":\"Invalid userId\"}");
        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"error\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }

        out.flush();
    }
}
