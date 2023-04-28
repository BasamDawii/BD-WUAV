package BLL;

import BE.User;
import DAL.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public User login(String email, String password) {
        return userRepository.login(email, password);
    }
}
