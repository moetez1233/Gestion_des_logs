package com.example.demo.controller;

import net.logstash.logback.encoder.org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("api")
public class testExceptions {
    public static final Logger logger= LogManager.getLogger(testExceptions.class);

    @GetMapping
    public ResponseEntity<String> getName(){
        String name = null;
        if(StringUtils.equals(name,null)){
            logger.error("name is null");
            throw new NullPointerException("name is null");
        }
        return new ResponseEntity<>(name, HttpStatus.OK);
    }
}
