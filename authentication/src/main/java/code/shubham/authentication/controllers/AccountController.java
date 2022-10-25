package code.shubham.authentication.controllers;

import code.shubham.authentication.dao.entities.AuthenticationAccount;
import code.shubham.authentication.dao.service.AccountService;
import code.shubham.authentication.service.AuthenticationService;
import code.shubham.commons.util.ResponseUtils;
import code.shubham.models.authentication.CreateAccount;
import code.shubham.models.authentication.UpdateAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
public class AccountController {

    private final AccountService service;

    private final AuthenticationService authenticationService;

    @Autowired
    public AccountController(final AccountService service,
                             final AuthenticationService authenticationService) {
        this.service = service;
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> create(
            @RequestHeader(value = "userId", required = false) Integer userId,
            @RequestBody CreateAccount.Request request) {
        String encodedPassword = this.authenticationService.getEncodedPassword(request.getPassword());
        AuthenticationAccount account = this.service.create(request, encodedPassword, userId);
        return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED,
                CreateAccount.Response.builder().username(account.getUsername()).build());
    }

    @PatchMapping("/{username}")
    public ResponseEntity<?> update(
            @PathVariable("username") String username,
            @RequestHeader(value = "userId") UpdateAccount.Request request) {
        AuthenticationAccount account = this.service.setUserId(username, request.getUserId());
        return ResponseUtils.getDataResponseEntity(HttpStatus.OK.value(), account);
    }
}
