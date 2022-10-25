package code.shubham.authentication.controllers;

import code.shubham.authentication.dao.entities.AuthenticationAccount;
import code.shubham.authentication.service.AuthenticationService;
import code.shubham.commons.util.ResponseUtils;
import code.shubham.models.authentication.CreateAccount;
import code.shubham.models.authentication.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AuthenticationController {

    private final AuthenticationService service;

    @Autowired
    public AuthenticationController(final AuthenticationService service) {
        this.service = service;
    }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestHeader("userId") Integer userId,
                                   @RequestBody Login.Request request) {
        Login.Response response = this.service.login(request);
        return ResponseUtils.getDataResponseEntity(HttpStatus.OK.value(), response);
    }
}
