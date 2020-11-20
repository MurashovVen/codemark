package codemark.testservice.service.impl;

import codemark.testservice.model.dto.UserDTO;
import codemark.testservice.model.dto.mapping.UserMapper;
import codemark.testservice.exception.NotFoundException;
import codemark.testservice.exception.UserAlreadyExistException;
import codemark.testservice.model.UserEntity;
import codemark.testservice.repository.UserRepository;
import codemark.testservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@EnableTransactionManagement
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public List<UserDTO> findAll() throws NotFoundException {
        List<UserEntity> users = this.userRepository.findAll();
        if (users.isEmpty()) {
            throw new NotFoundException("Users not founds");
        } else {
            return userMapper.toDTO(users);
        }
    }

    @Override
    @Transactional
    public UserDTO findById(String login) throws NotFoundException {

        return  userMapper.toDTO(userRepository.findById(login)
                .orElseThrow(() -> new NotFoundException(String.format("User with login: '%s' doesn't exist", login)))
        );
    }

    @Override
    @Transactional
    public void deleteById(String login) {
        log.info(String.format("Deleting user with login: '%s'", login));

        userRepository.deleteById(login);
    }

    @Override
    @Transactional
    public void save(UserDTO user) throws UserAlreadyExistException {
        if (userRepository.existsById(user.getLogin())) {
            throw new UserAlreadyExistException(String.format("User with login '%s' already exist", user.getLogin()));
        }

        log.info(String.format("Saving user with login: '%s'", user.getLogin()));

        UserEntity userEntity = userMapper.toEntity(user);
        userRepository.save(userEntity);
    }

    @Override
    @Transactional
    public void update(UserDTO user) throws NotFoundException {
        if(userRepository.existsById(user.getLogin())) {
            log.info(String.format("Updating user with login: '%s'", user.getLogin()));

            userRepository.deleteById(user.getLogin());

            UserEntity userEntity = userMapper.toEntity(user);
            userRepository.save(userEntity);

        } else {
            throw new NotFoundException(String.format("User with login '%s' doesn't exist", user.getLogin()));
        }
    }
}
