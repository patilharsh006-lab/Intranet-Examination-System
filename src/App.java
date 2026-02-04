import db.DBConnection;
import dao.UserDAO;
import dao.ExamDAO;
import dao.ExamResultDAO;
import dao.questionsDAO;

import model.users;
import model.Exam;
import model.ExamResult;
import model.questions;


import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {

        // 1️⃣ Check DB connection
        if (DBConnection.getConnection() == null) {
            System.out.println("❌ Database Connection Failed");
            return;
        }
        System.out.println("✅ Database Connected Successfully");

        Scanner sc = new Scanner(System.in);
        UserDAO userDAO = new UserDAO();

        // 2️⃣ LOGIN
        System.out.println("\n➡ User Login");
        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        users loggedUser = userDAO.login(email, password);

        if (loggedUser == null) {
            System.out.println("❌ Invalid credentials");
            return;
        }

        System.out.println("✔ Login successful");
        System.out.println("Welcome, " + loggedUser.getName());

        // 3️⃣ ROLE CHECK
        if ("ADMIN".equalsIgnoreCase(loggedUser.getRole())) {
            showAdminDashboard(sc);
        } else {
            showStudentDashboard(sc, loggedUser);
        }
    }

    // ================= ADMIN DASHBOARD =================
    private static void showAdminDashboard(Scanner sc) {

        ExamDAO examDAO = new ExamDAO();
        questionsDAO questionDAO = new questionsDAO();

        while (true) {
            System.out.println("\n===== ADMIN DASHBOARD =====");
            System.out.println("1. View Exams");
            System.out.println("2. Add Exam");
            System.out.println("3. Add Questions");
            System.out.println("4. Logout");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {

                case 1:
                       List<Exam> exams = examDAO.getAllExams();

    System.out.println("\n====================");
    System.out.println("📘 AVAILABLE EXAMS");
    System.out.println("====================");

    if (exams.isEmpty()) {
        System.out.println("❌ No exams found.");
    } else {
        for (Exam e : exams) {
            System.out.println(
                "ID: " + e.getExamId() +
                " | Name: " + e.getExamName() +
                " | Duration: " + e.getDuration() + " mins" +
                " | Marks: " + e.getTotalMarks()
            );
        }
    }

    System.out.println("\nPress ENTER to return to menu...");
    sc.nextLine();   // ⭐ THIS LINE IS MANDATORY

    break;

                case 2:
                    System.out.print("Enter exam title: ");
                    String title = sc.nextLine();

                    Exam exam = new Exam(1, title, 60, 100);

                    examDAO.addExam(exam);
                    System.out.println("✔ Exam added");
                    break;

                case 3:
                    
    System.out.print("Enter Exam ID: ");
    int examId = sc.nextInt();
    sc.nextLine();

    while (true) {

        System.out.print("Question: ");
        String q = sc.nextLine();

        System.out.print("Option A: ");
        String a = sc.nextLine();

        System.out.print("Option B: ");
        String b = sc.nextLine();

        System.out.print("Option C: ");
        String c = sc.nextLine();

        System.out.print("Option D: ");
        String d = sc.nextLine();

        System.out.print("Correct Option (A/B/C/D): ");
        char correct = sc.next().toUpperCase().charAt(0);

        System.out.print("Marks: ");
        int marks = sc.nextInt();
        sc.nextLine();

        questions question = new questions(
                examId, q, a, b, c, d, correct, marks
        );

        boolean added = questionDAO.addQuestion(question);

if (added) {
    int totalMarks = questionDAO.getTotalMarksByExamId(examId);
    examDAO.updateTotalMarks(examId, totalMarks);

    System.out.println("✔ Question added");
    System.out.println("📊 Updated Total Marks: " + totalMarks);
} else {
    System.out.println("❌ Failed to add question");
}

        System.out.print("Add another question? (y/n): ");
        char ch = sc.next().toLowerCase().charAt(0);
        sc.nextLine();

        if (ch != 'y') {
            break;
        }
    }
    break;

                

                case 4:
                    System.out.println("Logged out");
                    return;

                default:
                    System.out.println("❌ Invalid choice");
            }
        }
    }

    // ================= STUDENT DASHBOARD =================
    private static void showStudentDashboard(Scanner sc, users user) {

    ExamDAO examDAO = new ExamDAO();
    questionsDAO questionDAO = new questionsDAO();
    ExamResultDAO resultDAO = new ExamResultDAO();

    while (true) {
        System.out.println("\n===== STUDENT DASHBOARD =====");
        System.out.println("1. Start Exam");
        System.out.println("2. View Result History");
        System.out.println("3. Logout");
        System.out.print("Enter choice: ");

        int choice = sc.nextInt();
        sc.nextLine();

        switch (choice) {

            case 1:
                // ================= START EXAM =================
                List<Exam> exams = examDAO.getAllExams();

                if (exams.isEmpty()) {
                    System.out.println("❌ No exams available.");
                    break;
                }

                System.out.println("\n📘 Available Exams:");
                for (Exam e : exams) {
                    System.out.println(e.getExamId() + " - " + e.getExamName());
                }

                System.out.print("\nEnter Exam ID to start: ");
                int examId = sc.nextInt();
                sc.nextLine();

                Exam selectedExam = null;
                for (Exam e : exams) {
                    if (e.getExamId() == examId) {
                        selectedExam = e;
                        break;
                    }
                }

                if (selectedExam == null) {
                    System.out.println("❌ Invalid Exam ID");
                    break;
                }

                int durationMinutes = selectedExam.getDuration();
                long startTime = System.currentTimeMillis();
                long endTime = startTime + (durationMinutes * 60 * 1000);

                List<questions> questions =
                        questionDAO.getquestionsByExamId(examId);

                if (questions.isEmpty()) {
                    System.out.println("❌ This exam has no questions.");
                    break;
                }

                int score = 0;

                for (questions q : questions) {

                    if (System.currentTimeMillis() > endTime) {
                        System.out.println("\n⏰ Time's up! Exam auto-submitted.");
                        break;
                    }

                    System.out.println("\n" + q.getQuestion());
                    System.out.println("A. " + q.getOptionA());
                    System.out.println("B. " + q.getOptionB());
                    System.out.println("C. " + q.getOptionC());
                    System.out.println("D. " + q.getOptionD());

                    System.out.print("Your answer: ");
                    char ans = sc.next().toUpperCase().charAt(0);

                    if (ans == q.getCorrectOption()) {
                        score += q.getMarks();
                    }
                }

                long actualEndTime = System.currentTimeMillis();
                int timeTakenMinutes =
                        (int) ((actualEndTime - startTime) / (1000 * 60));

                ExamResult result = new ExamResult(
                        user.getUserId(),
                        examId,
                        score,
                        selectedExam.getTotalMarks(),
                        timeTakenMinutes
                );

                boolean saved = resultDAO.saveResult(result);

                System.out.println("\n🎯 Your Score: " + score +
                        " / " + selectedExam.getTotalMarks());

                if (saved) {
                    System.out.println("✅ Exam result saved");
                } else {
                    System.out.println("❌ Failed to save result");
                }

                break;

            case 2:
                // ================= VIEW RESULT HISTORY =================
                viewResultHistory(user);
                break;

            case 3:
                System.out.println("👋 Logged out");
                return;

            default:
                System.out.println("❌ Invalid choice");
        }
    }
}


// ================= STEP 4: SAVE EXAM RESULT =================






    
    private static void viewResultHistory(users loggedInUser) {

    ExamResultDAO resultDAO = new ExamResultDAO();
    List<ExamResult> results =
            resultDAO.getResultsByUserId(loggedInUser.getUserId());

    if (results.isEmpty()) {
        System.out.println("📭 No exam results found.");
        return;
    }

    System.out.println("\n📊 Your Exam Results");
    System.out.println("------------------------------------------------------------");
    System.out.printf("%-8s %-8s %-8s %-12s %-20s%n",
            "ExamID", "Score", "Total", "Time(min)", "Date");
    System.out.println("------------------------------------------------------------");

    for (ExamResult r : results) {
        System.out.printf("%-8d %-8d %-8d %-12d %-20s%n",
                r.getExamId(),
                r.getScore(),
                r.getTotalMarks(),
                r.getTimeTaken(),
                r.getExamDate().toLocalDateTime().toLocalDate()
        );
    }

    System.out.println("------------------------------------------------------------");
}



    
}


