package com.chatop.chatop.controllers;

import com.chatop.chatop.entity.Message;
import com.chatop.chatop.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@RestController
@SecurityRequirement(name="auth")
public class MessageController {

    private final MessageService messageService;

    @Autowired
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @Operation(
            tags = "Message ctrl",
            description = "send msg",
            responses = {
                    @ApiResponse(
                            description = "sucess",
                            responseCode = "200"
                    )
            }


    )

    @PostMapping("/messages")
    public ResponseEntity<HashMap> createMessage(@RequestBody Message message) {
        String Message;
        HttpStatus Status;
        boolean success = messageService.addMessage(message);
        if (success) {
            Message = "Message créé avec succès";
            Status = HttpStatus.OK;
        } else {
            Message = "Échec de la création du message";
            Status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        HashMap<String, String> map = new HashMap<>();
        map.put("message", Message);
        return new ResponseEntity<HashMap>(map, Status);
    }
}
