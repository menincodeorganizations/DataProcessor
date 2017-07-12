package com.vsamma.dataprocessor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
    @Autowired
    public SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String message) {
        messagingTemplate.convertAndSend("/topic/import", message);
    }
}