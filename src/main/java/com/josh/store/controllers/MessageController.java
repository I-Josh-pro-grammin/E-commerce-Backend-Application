package com.josh.store.controllers;

import com.josh.store.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @RequestMapping("/hello")
    public Message hello() {
        return new Message("Hello world!");
    }
}
