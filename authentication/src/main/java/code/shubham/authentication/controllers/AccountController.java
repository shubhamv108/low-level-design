package code.shubham.authentication.controllers;

import code.shubham.authentication.dao.entities.UserAccount;
import code.shubham.authentication.dao.service.AccountService;
import code.shubham.authentication.service.AuthenticationService;
import code.shubham.commons.util.ResponseUtils;
import code.shubham.models.authentication.CreateAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
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
    public ResponseEntity<?> create(@RequestBody CreateAccount.Request request) {
        String encodedPassword = this.authenticationService.getEncodedPassword(request.getPassword());
        UserAccount account = this.service.create(request, encodedPassword);
        return ResponseUtils.getDataResponseEntity(HttpStatus.CREATED,
                CreateAccount.Response.builder().username(account.getUsername()).build());
    }
}
