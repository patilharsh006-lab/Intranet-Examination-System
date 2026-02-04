package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ExamResult;
import db.DBConnection;

public class ExamResultDAO {

    public boolean saveResult(ExamResult result) {

        String sql = "INSERT INTO exam_results " +
                     "(user_id, exam_id, score, total_marks, time_taken) " +
                     "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, result.getUserId());
            ps.setInt(2, result.getExamId());
            ps.setInt(3, result.getScore());
            ps.setInt(4, result.getTotalMarks());
            ps.setInt(5, result.getTimeTaken());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<ExamResult> getResultsByUserId(int userId) {

    List<ExamResult> results = new ArrayList<>();

    String sql = "SELECT * FROM exam_results WHERE user_id = ? ORDER BY exam_date DESC";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            ExamResult r = new ExamResult();

            r.setResultId(rs.getInt("result_id"));
            r.setUserId(rs.getInt("user_id"));
            r.setExamId(rs.getInt("exam_id"));
            r.setScore(rs.getInt("score"));
            r.setTotalMarks(rs.getInt("total_marks"));
            r.setTimeTaken(rs.getInt("time_taken"));
            r.setExamDate(rs.getTimestamp("exam_date"));

            results.add(r);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return results;
}

}

