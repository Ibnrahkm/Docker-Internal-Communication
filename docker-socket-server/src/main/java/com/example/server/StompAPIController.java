package com.example.server;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

@RestController
public class StompAPIController {

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) {
        return ("Hello, " + message + "!");
    }
}
