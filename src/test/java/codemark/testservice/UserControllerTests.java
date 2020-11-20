package codemark.testservice;

import codemark.testservice.common.BaseControllerTests;
import codemark.testservice.controller.UserController;
import codemark.testservice.model.dto.RoleDTO;
import codemark.testservice.model.dto.UserDTO;
import codemark.testservice.model.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

class UserControllerTests extends BaseControllerTests {
    @Autowired
    private UserController userController;

    private final List<RoleDTO> defaultRolesDTO = new LinkedList<>();
    private UserDTO defaultUserDTO;


    @BeforeEach
    public void buildDTO(){
        defaultRolesDTO
                .add(RoleDTO.builder()
                        .id(11)
                        .name("admin")
                        .build());
        defaultRolesDTO
                .add(RoleDTO.builder()
                        .id(21)
                        .name("user")
                        .build());

        defaultUserDTO = UserDTO.builder()
                .username("test")
                .login("test")
                .password("1Sqwe")
                .roles(defaultRolesDTO)
                .build();
    }

    @Test
    void saveWithCorrectData_UserSaved() {
        //given
        UserDTO userDTO = defaultUserDTO;

        //run
        userController.save(userDTO);

        Optional<UserEntity> dbUser = userRepository.findById("test");

        //assert
        assert(dbUser.isPresent());
        assert(userDTO.equals(userMapper.toDTO(dbUser.get())));
    }

    @Test
    void saveWithNullLogin_UserNotSaved() {
        //given
        UserDTO userDTO = defaultUserDTO;
        userDTO.setLogin(null);

        //run
        userController.save(userDTO);
        Optional<UserEntity> dbUser = userRepository.findById("test");

        //assert
        assert (dbUser.isEmpty());
    }

    @Test
    void saveWithNullUsername_UserNotSaved() {
        //given
        UserDTO userDTO = defaultUserDTO;
        userDTO.setUsername(null);

        //run
        userController.save(userDTO);
        Optional<UserEntity> dbUser = userRepository.findById(userDTO.getLogin());

        //assert
        assert (dbUser.isEmpty());
    }

    @Test
    void saveWithNullPassword_UserNotSaved() {
        //given
        UserDTO userDTO = defaultUserDTO;
        userDTO.setPassword(null);

        //run
        userController.save(userDTO);
        Optional<UserEntity> dbUser = userRepository.findById(userDTO.getLogin());

        //assert
        assert (dbUser.isEmpty());
    }

    @Test
    void saveWithNoDownCaseSymbolPassword_UserNotSaved(){
        //given
        UserDTO userDTO = defaultUserDTO;
        userDTO.setPassword("123qwe");

        //run
        userController.save(userDTO);
        Optional<UserEntity> dbUser = userRepository.findById(userDTO.getLogin());

        //assert
        assert (dbUser.isEmpty());
    }

    @Test
    void saveWithNoDigitPassword_UserNotSaved(){
        //given
        UserDTO userDTO = defaultUserDTO;
        userDTO.setPassword("Qqwe");

        //run
        userController.save(userDTO);
        Optional<UserEntity> dbUser = userRepository.findById(userDTO.getLogin());

        //assert
        assert (dbUser.isEmpty());
    }

    @Test
    void saveWithTotalIncorrectPassword_UserNotSaved(){
        //given
        UserDTO userDTO = defaultUserDTO;
        userDTO.setPassword("asd");

        //run
        userController.save(userDTO);
        Optional<UserEntity> dbUser = userRepository.findById(userDTO.getLogin());

        //assert
        assert (dbUser.isEmpty());
    }

    @Test
    void deleteExistingUser_UserDeleted() {
        //given
        UserDTO userDTO = defaultUserDTO;

        //run
        userController.save(userDTO);
        Optional<UserEntity> dbSavedUser = userRepository.findById(userDTO.getLogin());
        userController.delete(userDTO.getLogin());
        Optional<UserEntity> dbDeletedUser = userRepository.findById(userDTO.getLogin());

        //assert
        assert (dbSavedUser.isPresent());
        assert (dbDeletedUser.isEmpty());
    }

    @Test
    void deleteNonExistingUser_UserNotDeleted() {
        //given
        UserDTO userDTO = defaultUserDTO;

        //run
        //userController.save(userDTO);
        userController.delete(userDTO.getLogin());
        Optional<UserEntity> dbUser = userRepository.findById(userDTO.getLogin());

        //assert
        assert (dbUser.isEmpty());
    }

    @Test
    void updateExistingUser_UserUpdated() {
        //given
        UserDTO oldUserDTO = defaultUserDTO;

        List<RoleDTO> newRolesDTO = new LinkedList<>();
        newRolesDTO
                .add(RoleDTO.builder()
                        .id(21)
                        .name("user")
                        .build());

        UserDTO newUserDTO = defaultUserDTO;
        newUserDTO.setRoles(newRolesDTO);

        //run
        userController.save(oldUserDTO);
        userController.update(newUserDTO);

        Optional<UserEntity> dbUser = userRepository.findById(newUserDTO.getLogin());

        //assert
        assert (dbUser.isPresent());
        assert (dbUser.get().equals(userMapper.toEntity(newUserDTO)));
    }

    @Test
    void updateNonExistingUser_UserNotUpdated() {
        //given
        UserDTO userDTO = defaultUserDTO;

        //run
        userController.update(userDTO);

        Optional<UserEntity> dbUser = userRepository.findById(userDTO.getLogin());

        //assert
        assert (dbUser.isEmpty());
    }

    @Test
    void updateExistingUserWithNullLogin_UserNotUpdated() {
        //given
        UserDTO oldUserDTO = defaultUserDTO;

        List<RoleDTO> newRolesDTO = new LinkedList<>();
        newRolesDTO
                .add(RoleDTO.builder()
                        .id(21)
                        .name("user")
                        .build());

        UserDTO newUserDTO = oldUserDTO.toBuilder()
                .login(null)
                .roles(newRolesDTO)
                .build();

        //run
        userController.save(oldUserDTO);
        userController.update(newUserDTO);
        Optional<UserEntity> dbUser = userRepository.findById("test");

        //assert
        assert (dbUser.isPresent());
        assert (oldUserDTO.equals(userMapper.toDTO(dbUser.get())));
    }

    @Test
    void updateExistingUserWithNullUsername_UserNotUpdated() {
        //given
        UserDTO oldUserDTO = defaultUserDTO;

        List<RoleDTO> newRolesDTO = new LinkedList<>();
        newRolesDTO
                .add(RoleDTO.builder()
                        .id(21)
                        .name("user")
                        .build());

        UserDTO newUserDTO = oldUserDTO.toBuilder()
                .username(null)
                .roles(newRolesDTO)
                .build();

        //run
        userController.save(oldUserDTO);
        userController.update(newUserDTO);
        Optional<UserEntity> dbUser = userRepository.findById(oldUserDTO.getLogin());

        //assert
        assert (dbUser.isPresent());
        assert (oldUserDTO.equals(userMapper.toDTO(dbUser.get())));
    }

    @Test
    void updateExistingUserWithNullPassword_UserNotUpdated() {
        //given
        UserDTO oldUserDTO = defaultUserDTO;

        List<RoleDTO> newRolesDTO = new LinkedList<>();
        newRolesDTO
                .add(RoleDTO.builder()
                        .id(21)
                        .name("user")
                        .build());

        UserDTO newUserDTO = oldUserDTO.toBuilder()
                .password(null)
                .roles(newRolesDTO)
                .build();

        //run
        userController.save(oldUserDTO);
        userController.update(newUserDTO);
        Optional<UserEntity> dbUser = userRepository.findById(oldUserDTO.getLogin());

        //assert
        assert (dbUser.isPresent());
        assert (oldUserDTO.equals(userMapper.toDTO(dbUser.get())));
    }

    @Test
    void updateExistingUserWithNoDownCaseSymbolPassword_UserNotUpdated(){
        //given
        UserDTO oldUserDTO = defaultUserDTO;

        List<RoleDTO> newRolesDTO = new LinkedList<>();
        newRolesDTO
                .add(RoleDTO.builder()
                        .id(21)
                        .name("user")
                        .build());

        UserDTO newUserDTO = oldUserDTO.toBuilder()
                .password("1qwe")
                .roles(newRolesDTO)
                .build();

        //run
        userController.save(oldUserDTO);
        userController.update(newUserDTO);
        Optional<UserEntity> dbUser = userRepository.findById(oldUserDTO.getLogin());

        //assert
        assert (dbUser.isPresent());
        assert (oldUserDTO.equals(userMapper.toDTO(dbUser.get())));
    }

    @Test
    void updateExistingUserWithNoDigitPassword_UserNotUpdated(){
        //given
        UserDTO oldUserDTO = defaultUserDTO;

        List<RoleDTO> newRolesDTO = new LinkedList<>();
        newRolesDTO
                .add(RoleDTO.builder()
                        .id(21)
                        .name("user")
                        .build());

        UserDTO newUserDTO = oldUserDTO.toBuilder()
                .password("Qqwe")
                .roles(newRolesDTO)
                .build();

        //run
        userController.save(oldUserDTO);
        userController.update(newUserDTO);
        Optional<UserEntity> dbUser = userRepository.findById(oldUserDTO.getLogin());

        //assert
        assert (dbUser.isPresent());
        assert (oldUserDTO.equals(userMapper.toDTO(dbUser.get())));
    }

    @Test
    void updateExistingUserWithTotalIncorrectPassword_UserNotUpdated(){
        //given
        UserDTO oldUserDTO = defaultUserDTO;

        List<RoleDTO> newRolesDTO = new LinkedList<>();
        newRolesDTO
                .add(RoleDTO.builder()
                        .id(21)
                        .name("user")
                        .build());

        UserDTO newUserDTO = oldUserDTO.toBuilder()
                .password("qwe")
                .roles(newRolesDTO)
                .build();

        //run
        userController.save(oldUserDTO);
        userController.update(newUserDTO);
        Optional<UserEntity> dbUser = userRepository.findById(oldUserDTO.getLogin());

        //assert
        assert (dbUser.isPresent());
        assert (oldUserDTO.equals(userMapper.toDTO(dbUser.get())));
    }
}
