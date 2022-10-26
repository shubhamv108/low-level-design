package code.shubham.authentication.controllers;

import code.shubham.authentication.service.AuthenticationService;
import code.shubham.authentication.utils.AccessTokenUtil;
import code.shubham.commons.util.ResponseUtils;
import code.shubham.models.authentication.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

    private final AuthenticationService service;
    private final AccessTokenUtil accessTokenUtil;

    @Autowired
    public AuthenticationController(final AuthenticationService service,
                                    final AccessTokenUtil accessTokenUtil) {
        this.service = service;
        this.accessTokenUtil = accessTokenUtil;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login.Request request) {
        Login.Response response = this.service.login(request);
        return ResponseUtils.getDataResponseEntity(HttpStatus.OK.value(), response);
    }

    @PostMapping
    public ResponseEntity<?> authenticate(HttpServletRequest request, HttpServletResponse response) {
        Integer userId = this.service.authenticate(request, response);
        return ResponseUtils.getResponseEntity(HttpStatus.OK.value(), null, null, "userId", String.valueOf(userId));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            @RequestHeader(value = "userId", required = true) Integer userId,
            HttpServletRequest request, HttpServletResponse response) {
        String accessToken;
        try {
            accessToken = this.accessTokenUtil.accessToken(request, response);
        } catch (IOException e) {
            return ResponseUtils.getResponseEntity(500);
        }
        if (this.service.logout(userId, accessToken))
            return ResponseUtils.getDataResponseEntity(HttpStatus.OK.value(), response);
        return ResponseUtils.getResponseEntity(500);
    }
}
