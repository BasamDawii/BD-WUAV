package BE;


import java.util.UUID;

public class Salesperson implements Employee {
    public Salesperson(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    private UUID id;
    private String username;
    private String password;

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}