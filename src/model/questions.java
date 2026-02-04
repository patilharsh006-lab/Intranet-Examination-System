package model;

public class questions {

    private int questionId;
    private int examId;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private char correctOption;
    private int marks;

    // Constructor
    public questions(int examId, String question,
                     String optionA, String optionB,
                     String optionC, String optionD,
                     char correctOption, int marks) {

        this.examId = examId;
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctOption = correctOption;
        this.marks = marks;
    }

    // Getters
    public int getExamId() {
        return examId;
    }

    public String getQuestion() {
        return question;
    }

    public String getOptionA() {
        return optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public char getCorrectOption() {
        return correctOption;
    }

    public int getMarks() {
        return marks;
    }
}
