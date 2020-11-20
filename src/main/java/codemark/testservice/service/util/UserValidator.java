package codemark.testservice.service.util;

import codemark.testservice.model.dto.UserDTO;

import java.util.LinkedList;
import java.util.List;

public class UserValidator {

    /**
     *
     * @return List<String> of errors
     */
    public static List<String> validate(UserDTO user) {
        LinkedList<String> errors = new LinkedList<>();

        if (user.getLogin() == null) {
            errors.add("login must be not null");
        }
        if (user.getUsername() == null) {
            errors.add("username must be not null");
        }
        if (user.getPassword() == null) {
            errors.add("password must be not null");
        } else if (!isValid(user.getPassword())) {
            errors.add("password is not valid");
        }

        return errors;
    }

    private static boolean isValid(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[A-Z]).{0,}");
    }
}
