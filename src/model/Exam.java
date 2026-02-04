package model;

public class Exam {

    private int examId;
    private String examName;
    private int duration; // in minutes
    private int totalMarks;

    public Exam() {
    }

    public Exam(int examId, String examName, int duration, int totalMarks) {
        this.examId = examId;
        this.examName = examName;   
        this.duration = duration;
        this.totalMarks = totalMarks;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(int totalMarks) {
        this.totalMarks = totalMarks;
    }
}