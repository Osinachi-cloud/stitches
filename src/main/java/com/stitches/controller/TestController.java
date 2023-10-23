package com.stitches.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/test")
@Slf4j
public class TestController {

    @GetMapping
    public ResponseEntity<String> testing(){
        log.info("I entered");
        return new ResponseEntity<>("Yehhh!!!", HttpStatus.OK);
    }
}
