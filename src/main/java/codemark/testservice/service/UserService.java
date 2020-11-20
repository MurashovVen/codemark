package codemark.testservice.service;

import codemark.testservice.model.dto.UserDTO;
import codemark.testservice.exception.NotFoundException;
import codemark.testservice.exception.UserAlreadyExistException;

import java.util.List;

public interface UserService {
    List<UserDTO> findAll() throws NotFoundException;
    UserDTO findById(String login) throws NotFoundException;
    void deleteById(String login);
    void save(UserDTO user) throws UserAlreadyExistException;
    void update(UserDTO user) throws NotFoundException;
}
