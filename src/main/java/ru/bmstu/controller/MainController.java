package ru.bmstu.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.dtos.*;
import ru.bmstu.entity.UserLocal;
import ru.bmstu.exception.CustomIllegalAccessException;
import ru.bmstu.mapper.UserMapper;
import ru.bmstu.service.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v2")
public class MainController {
    private static UserService userService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public MainController(UserService userService, BCryptPasswordEncoder passwordEncoder, UserMapper userMapper) {
        MainController.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    @GetMapping("/getStatus")
    public ResponseEntity<String> getAppStatus() {
        return ResponseEntity.status(HttpStatus.OK).body("App working mormally");
    }

    @GetMapping("/users/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SuccessDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<UserDto> getUserById(@PathVariable int id){
        UserLocal targetUserLocal = userService.getUsers().stream().filter(x -> x.getId() == id).findFirst().orElseThrow(() -> new NoSuchElementException("UserLocal with id=" + id + " not found"));
        return ResponseEntity.ok(userMapper.toDto(targetUserLocal));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PostMapping("/users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = SuccessDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<SuccessDto> createUser(@RequestBody CreateDto request,
                                        Authentication authentication) {
        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));

        if (!isTeacher && request.getRole().equals("Teacher")) {
            throw new CustomIllegalAccessException("Student cannot create Teacher");
        }
        userService.addUser(request.getFullName(), request.getRole(), passwordEncoder.encode(request.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDto("User created successfully"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PutMapping("/users/{id}/tokens")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SuccessDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<SuccessDto> updateUser(@PathVariable int id,
            @RequestBody UpdateDto request,
            Authentication authentication) {
        boolean isTeacher = authentication.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));

        if (!isTeacher && request.getAmount() > 0) {
            throw new CustomIllegalAccessException("Student cannot add tokens");
        }
        userService.updateUser(id, request.getAmount());
        return ResponseEntity.ok(new SuccessDto("Tokens updated successfully"));
    }

    @SecurityRequirement(name = "bearerAuth")
    @DeleteMapping("/users/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = SuccessDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    public ResponseEntity<SuccessDto> deleteStudent(@PathVariable int id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(new SuccessDto("User deleted successfully"));
    }

}
