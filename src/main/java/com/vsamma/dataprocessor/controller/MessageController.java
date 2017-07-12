package com.vsamma.dataprocessor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.vsamma.dataprocessor.service.MessageService;
import com.vsamma.dataprocessor.storage.StorageService;

@Controller
public class MessageController {
	
	@Autowired
	MessageService msgService;
	
	@Autowired
	StorageService storageService;


    @MessageMapping("/import")
    @SendTo("/topic/import")
    public String importData(String fileId) throws Exception {
    	msgService.sendMessage("Opening file and starting import...");
    	Thread.sleep(500);

        //Insert file data to DB
    	storageService.storeContentsToDb(fileId);
    	
        msgService.sendMessage("Processing done and import completed!");
    	
        return new String(fileId);
    }
}