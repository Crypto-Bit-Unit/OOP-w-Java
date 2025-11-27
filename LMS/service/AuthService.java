
package service;
// service/AuthService.java
import dao.UserDao;
import model.User; 
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Optional;

public class AuthService {
    private final UserDao userDao;

    public AuthService(UserDao userDao) { this.userDao = userDao; }

    public AuthService() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean register(String username, String password, String fullName, StringBuilder error) {
        if (username == null || username.isBlank()) { error.append("Username is required."); return false; }
        if (password == null || password.length() < 6) { error.append("Password must be at least 6 characters."); return false; }
        if (userDao.findByUsername(username).isPresent()) { error.append("Username already exists."); return false; }
        String hash = hash(password);
        userDao.save(new User(username, hash, fullName));
        return true;
    }

    public Optional<User> login(String username, String password) {
        Optional<User> found = userDao.findByUsername(username);
        if (found.isEmpty()) return Optional.empty();
        String hash = hash(password);
        return hash.equals(found.get().getHashedPassword()) ? found : Optional.empty();
    }

    private String hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] bytes = md.digest(input.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Hash failed", e);
        }
    }
}

