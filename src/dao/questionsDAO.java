package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBConnection;
import model.questions;

public class questionsDAO {

    // ================= ADD QUESTION (ADMIN) =================
    public boolean addQuestion(questions q) {

       String sql = "INSERT INTO questions (exam_id, question, option_a, option_b, option_c, option_d, correct_option, marks) VALUES (?,?,?,?,?,?,?,?)";


        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, q.getExamId());
            ps.setString(2, q.getQuestion());
            ps.setString(3, q.getOptionA());
            ps.setString(4, q.getOptionB());
            ps.setString(5, q.getOptionC());
            ps.setString(6, q.getOptionD());
            ps.setString(7, String.valueOf(q.getCorrectOption()));
            ps.setInt(8, q.getMarks());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    // ================= GET QUESTIONS BY EXAM (STUDENT) =================
    public List<questions> getquestionsByExamId(int examId) {

        List<questions> list = new ArrayList<>();

        String sql = "SELECT * FROM questions WHERE exam_id = ?";

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, examId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                questions q = new questions(
                        rs.getInt("exam_id"),
                        rs.getString("question"),
                        rs.getString("option_a"),
                        rs.getString("option_b"),
                        rs.getString("option_c"),
                        rs.getString("option_d"),
                        rs.getString("correct_option").charAt(0),
                        rs.getInt("marks")
                );
                list.add(q);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public int getTotalMarksByExamId(int examId) {

    String sql = "SELECT SUM(marks) FROM questions WHERE exam_id = ?";
    int totalMarks = 0;

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, examId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            totalMarks = rs.getInt(1);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return totalMarks;
}

}
