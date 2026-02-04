package model;

import java.sql.Timestamp;

public class ExamResult {

    private int resultId;
    private int userId;
    private int examId;
    private int score;
    private int totalMarks;
    private int timeTaken;
    private Timestamp examDate;

    public ExamResult() {}

    public ExamResult(int userId, int examId, int score, int totalMarks, int timeTaken) {
        this.userId = userId;
        this.examId = examId;
        this.score = score;
        this.totalMarks = totalMarks;
        this.timeTaken = timeTaken;
    }

    // getters & setters
    public int getResultId() { return resultId; }
    public void setResultId(int resultId) { this.resultId = resultId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getExamId() { return examId; }
    public void setExamId(int examId) { this.examId = examId; }

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotalMarks() { return totalMarks; }
    public void setTotalMarks(int totalMarks) { this.totalMarks = totalMarks; }

    public int getTimeTaken() { return timeTaken; }
    public void setTimeTaken(int timeTaken) { this.timeTaken = timeTaken; }

    public Timestamp getExamDate() { return examDate; }
    public void setExamDate(Timestamp examDate) { this.examDate = examDate; }
}
