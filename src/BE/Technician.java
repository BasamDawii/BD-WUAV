package BE;

public class Technician implements Employee {
    int id;
    String username;
    String password;

    public Technician(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public int getId() {
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
    @Override
    public String toString() {
        return username;
    }
}