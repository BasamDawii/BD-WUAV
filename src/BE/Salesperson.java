package BE;

public class Salesperson extends User {
    public Salesperson(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName, email, password, "Salesperson");
    }
}
