package com.banking.controller;

import com.banking.dto.TransactionDTO;
import com.banking.exception.WrongTokenException;
import com.banking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("transfer")
public class TransferController {

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/get/{token}")
    @ResponseBody
    public List<TransactionDTO> getTransactionsByToken(@PathVariable("token") String token){
        try {
            return transactionService.getTransactionsByToken(token);
        } catch (WrongTokenException ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Token not found!!!", ex);
        }
    }
}