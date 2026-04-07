package hospital.system.dto;

public class LoginResponse {

    private final String token;
    private final Long id;
    private final String username;
    private final String role;

    public LoginResponse(String token, Long id, String username, String role) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.role = role;
    }

    public String getToken() { return token; }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getRole() { return role; }
}