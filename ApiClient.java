package com.example.demo.api;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sample")
public class SampleApiController {

    // GET endpoint: /api/v1/sample/greeting?name=World
    @GetMapping("/greeting")
    public String getGreeting(@RequestParam(defaultValue = "World") String name) {
        return "Hello, " + name + "!";
    }

    // POST endpoint: /api/v1/sample/message
    @PostMapping("/message")
    public MessageResponse postMessage(@RequestBody MessageRequest request) {
        return new MessageResponse("Received: " + request.getMessage());
    }

    // Simple DTOs for request/response
    public static class MessageRequest {
        private String message;
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    public static class MessageResponse {
        private String reply;
        public MessageResponse(String reply) { this.reply = reply; }
        public String getReply() { return reply; }
        public void setReply(String reply) { this.reply = reply; }
    }
}
