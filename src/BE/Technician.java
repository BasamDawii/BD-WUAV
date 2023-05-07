package BE;

import java.util.UUID;

public class Technician implements Employee {
    private UUID id;
    private String username;
    private String password;

    public Technician(UUID id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
