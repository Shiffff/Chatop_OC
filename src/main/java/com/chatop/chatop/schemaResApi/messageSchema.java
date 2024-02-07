package com.chatop.chatop.schemaResApi;

import io.swagger.v3.oas.annotations.media.Schema;

public class messageSchema {

    @Schema(description = "Sucess", example = "Message créé avec succès")
    private String message;

    public messageSchema(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }


}
