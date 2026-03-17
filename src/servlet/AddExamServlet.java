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

import dao.ExamDAO;
import model.Exam;

// Maps to frontend: apiPost('/api/exams/add', { examName, duration, totalMarks })
@WebServlet("/api/exams/add")
public class AddExamServlet extends HttpServlet {

    private Gson gson = new Gson();
    private ExamDAO examDAO = new ExamDAO();

    // doPost() runs when the frontend sends a POST request with exam data
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
            // Step 1: Read the JSON body sent from the frontend
            // The frontend sends: { "examName": "DS Exam", "duration": 90, "totalMarks": 100 }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = request.getReader().readLine()) != null) {
                sb.append(line);
            }

            // Step 2: Parse the JSON string into a Java object using Gson
            JsonObject json = gson.fromJson(sb.toString(), JsonObject.class);

            String examName  = json.get("examName").getAsString();
            int duration     = json.get("duration").getAsInt();
            int totalMarks   = json.get("totalMarks").getAsInt();

            // Step 3: Create an Exam model object
            Exam exam = new Exam();
            exam.setExamName(examName);
            exam.setDuration(duration);
            exam.setTotalMarks(totalMarks);

            // Step 4: Call your existing DAO to save it to database
            boolean success = examDAO.addExam(exam);

            // Step 5: Send back success/failure JSON
            if (success) {
                out.print("{\"success\":true,\"message\":\"Exam created successfully\"}");
            } else {
                response.setStatus(500);
                out.print("{\"success\":false,\"message\":\"Failed to create exam\"}");
            }

        } catch (Exception e) {
            response.setStatus(500);
            out.print("{\"success\":false,\"message\":\"" + e.getMessage() + "\"}");
            e.printStackTrace();
        }

        out.flush();
    }

    // Handle browser preflight OPTIONS request (needed for CORS)
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        res.setHeader("Access-Control-Allow-Origin", "*");
        res.setHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
        res.setHeader("Access-Control-Allow-Headers", "Content-Type");
        res.setStatus(200);
    }
}
