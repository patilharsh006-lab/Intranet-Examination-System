package model;

public class users {

    private int userId;     // ✅ ADD THIS
    private String name;
    private String email;
    private String password;
    private String role;

    // ✅ Constructor with userId
    public users(int userId, String name, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ✅ Getter for userId
    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
}

