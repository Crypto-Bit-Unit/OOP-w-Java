
package model;

// model/User.java
public class User {
    
    private final String username;
    private final String hashedPassword; // naive hashing for demo
    private final String fullName;
    private boolean isAdmin;
    public boolean isAdmin() { return isAdmin; }
    public void setAdmin(boolean isAdmin) { this.isAdmin = isAdmin; }


    public User(String username, String hashedPassword, String fullName) {
        if (username == null || username.isBlank()) throw new IllegalArgumentException("Username required");
        if (hashedPassword == null || hashedPassword.isBlank()) throw new IllegalArgumentException("Password required");
        this.username = username.trim();
        this.hashedPassword = hashedPassword.trim();
        this.fullName = fullName == null ? "" : fullName.trim();
    }
    public String getUsername() { return username; }
    public String getHashedPassword() { return hashedPassword; }
    public String getFullName() { return fullName; }

    public Object getRole() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated: nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}

