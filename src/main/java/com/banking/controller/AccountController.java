package com.banking.controller;

import com.banking.dto.AccountDTO;
import com.banking.exception.BalanceException;
import com.banking.exception.WrongTokenException;
import com.banking.model.Account;
import com.banking.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/get/{token}")
    @ResponseBody
    public List<AccountDTO> getAccountsByToken(@PathVariable("token") String token){
        try {
            return accountService.getAccountsByToken(token);
        } catch (WrongTokenException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found!!!", ex);
        }
    }

    @PostMapping(path = "/create/{token}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AccountDTO createAccountByToken(@PathVariable("token") String token, @RequestBody Account account) {
        try{
            return accountService.createAccountByToken(token, account);
        } catch (WrongTokenException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found!!!", ex);
        } catch (BalanceException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Wrong balance!", ex);
        }
    }
}
