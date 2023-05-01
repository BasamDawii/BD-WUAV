package BE;

public class Salesperson {
    int id;
    String username;
    String password;
    public Salesperson(int id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return username;
    }
    public void setName(String name) {
        this.username = name;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}