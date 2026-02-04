package dao;

import db.DBConnection;
import model.Exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExamDAO {

    // ================= ADD EXAM =================
    public boolean addExam(Exam exam) {

        String sql = "INSERT INTO exam (exam_name, duration, total_marks) VALUES (?, ?, ?)";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, exam.getExamName());
            ps.setInt(2, exam.getDuration());
            ps.setInt(3, exam.getTotalMarks());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= GET ALL EXAMS =================
    public List<Exam> getAllExams() {

        List<Exam> exams = new ArrayList<>();

        String sql = "SELECT * FROM exam";

        try {
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                Exam exam = new Exam(
                        rs.getInt("exam_id"),
                        rs.getString("exam_name"),
                        rs.getInt("duration"),
                        rs.getInt("total_marks")
                );
                exams.add(exam);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return exams;
    }

    public void updateTotalMarks(int examId, int totalMarks) {

    String sql = "UPDATE exam SET total_marks = ? WHERE exam_id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, totalMarks);
        ps.setInt(2, examId);
        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
    }
}

}

