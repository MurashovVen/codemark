package codemark.testservice.controller;

import codemark.testservice.model.dto.UserDTO;
import codemark.testservice.exception.NotFoundException;
import codemark.testservice.exception.UserAlreadyExistException;
import codemark.testservice.model.ApiResponse;
import codemark.testservice.service.UserService;
import codemark.testservice.service.util.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> getAll(){
        try {
            return ResponseEntity.ok(userService.findAll());

        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> get(@RequestParam String login){
        try {
            return ResponseEntity.ok(userService.findById(login));

        } catch (NotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @DeleteMapping("/user")
    public ResponseEntity<ApiResponse> delete(@RequestParam String login){
        try {
            userService.deleteById(login);

            return ResponseEntity
                    .status(HttpStatus.ACCEPTED)
                    .body(new ApiResponse(true));

        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse(false, Collections.singletonList(String.format("user with login '%s' doesn't exist", login))));
        }
    }

    @PostMapping("/user")
    public ResponseEntity<ApiResponse> save(@RequestBody UserDTO userDTO) {
        List<String> errors = UserValidator.validate(userDTO);

        if(errors.isEmpty()) {
            try {
                userService.save(userDTO);

            } catch (UserAlreadyExistException e) {
                return ResponseEntity
                        .badRequest()
                        .body(new ApiResponse(false, Collections.singletonList(e.getMessage())));
            }

            return ResponseEntity.ok(new ApiResponse(true));

        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, errors));
        }
    }

    @PutMapping("/user")
    public ResponseEntity<ApiResponse> update(@RequestBody UserDTO userDTO) {
        List<String> errors = UserValidator.validate(userDTO);

        if(errors.isEmpty()) {
            try {
                userService.update(userDTO);

            } catch (NotFoundException e) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse(false, Collections.singletonList(e.getMessage())));
            }

            return ResponseEntity.ok(new ApiResponse(true));

        } else {
            return ResponseEntity
                    .badRequest()
                    .body(new ApiResponse(false, errors));
        }
    }
}
