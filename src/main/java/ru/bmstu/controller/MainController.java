package ru.bmstu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.dto.CreateRequest;
import ru.bmstu.dto.ErrorResponse;
import ru.bmstu.dto.SuccessResponse;
import ru.bmstu.dto.UpdateRequest;
import ru.bmstu.entity.User;
import ru.bmstu.service.UserService;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("api/v1")
public class MainController {
    private static UserService userService;

    @Autowired
    public MainController(UserService userService) {
        MainController.userService = userService;
    }

    @GetMapping("/getStatus")
    public ResponseEntity<String> getAppStatus() {
        return ResponseEntity.status(HttpStatus.OK).body("App working mormally");
    }

    @GetMapping("/users/{id}")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = SuccessResponse.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
//            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
//    })
    public ResponseEntity<?> getStudentById(@PathVariable int id){
        try {
            User targetUser = userService.getUsers().stream().filter(x -> x.getID() == id).findFirst().orElseThrow(() -> new NoSuchElementException("User with id=" + id + " not found"));
            return ResponseEntity.ok(targetUser);
        }  catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("User not found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal Server Error", e.getMessage()));
        }
    }

    @PostMapping("/users")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "User-Credentials", value = "Full name",
//                    required = true, paramType = "header", dataType = "string")
//    })
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = SuccessResponse.class),
//            @ApiResponse(code = 201, message = "Created", response = SuccessResponse.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
//            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
//    })
    public ResponseEntity<?> createUser(@RequestHeader("User-Credentials") String credentials,
                                        @RequestBody CreateRequest request) {
        try {
            userService.addUser(request.getFullName(), request.getRole());
            return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse("User created successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Bad Request", e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal Server Error", e.getMessage()));
        }
    }

    @PutMapping("/users/{id}/tokens")
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "User-Credentials", value = "Full name",
//                    required = true, paramType = "header", dataType = "string")
//    })
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = SuccessResponse.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
//            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
//            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
//    })
    public ResponseEntity<?> updateUser(
            @RequestHeader("User-Credentials") String credentials, @PathVariable int id,
            @RequestBody UpdateRequest request) {
        try {
            userService.updateUser(id, request.getAmount());
            return ResponseEntity.ok(new SuccessResponse("Tokens updated successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Bad Request", e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal Server Error", e.getMessage()));
        }
    }

    @DeleteMapping("/users/{id}")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "OK", response = SuccessResponse.class),
//            @ApiResponse(code = 400, message = "Bad Request", response = ErrorResponse.class),
//            @ApiResponse(code = 401, message = "Unauthorized", response = ErrorResponse.class),
//            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
//            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class),
//            @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorResponse.class)
//    })
    public ResponseEntity<?> deleteStudent(@RequestHeader("User-Credentials") String credentials,
                                           @PathVariable int id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new SuccessResponse("Student deleted successfully"));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Bad Request", e.getMessage()));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponse("Forbidden", e.getMessage()));
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("Not Found", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Internal Server Error", e.getMessage()));
        }
    }

}
