package ru.bmstu.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.bmstu.dtos.ErrorDto;
import ru.bmstu.dtos.JwtInDto;
import ru.bmstu.dtos.JwtOutDto;
import ru.bmstu.exception.CustomBadCredentialsException;
import ru.bmstu.service.UserServiceSec;
import ru.bmstu.utils.JwtTokenUtil;

@RestController
public class AuthController {
    private final UserServiceSec userServiceSec;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    public AuthController(UserServiceSec userServiceSec, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.userServiceSec = userServiceSec;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK",
                    content = @Content(schema = @Schema(implementation = JwtOutDto.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class)))
    })
    public JwtOutDto createAuthToken(@RequestBody JwtInDto authRequest){
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new CustomBadCredentialsException("Invalid username or password");
        }
        UserDetails userDetails = userServiceSec.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return new JwtOutDto(token);
    }
}
