package com.vsamma.dataprocessor.controller;

import java.io.IOException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.vsamma.dataprocessor.storage.StorageFileNotFoundException;
import com.vsamma.dataprocessor.storage.StorageService;

@Controller
public class FileUploadController {
	
	private final StorageService storageService;
	
	@Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }
	
	@GetMapping("/")
    public String listUploadedFiles(Model model) throws IOException {
		
		model.addAttribute("name", "Lalala");

		//Seda pole vaja, kuna esialgsel laadimisel ei ole midagi näha 
		//ja ma samas ei taha ka reloadi teha, ehk juba lehe laadimisel seda fn-i uuesti välja kutsuda. 
//        model.addAttribute("files", storageService
//                .loadAll()
//                .map(path ->
//                        MvcUriComponentsBuilder
//                                .fromMethodName(FileUploadController.class, "serveFile", path.getFileName().toString())
//                                .build().toString())
//                .collect(Collectors.toList()));

        return "uploadForm";
    }
	
	@GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+file.getFilename()+"\"")
                .body(file);
    }
	
	@PostMapping("/import")
//    public String handleFileUpload(@RequestParam("file") MultipartFile file,
//                                   RedirectAttributes redirectAttributes) {
	public ResponseEntity<Resource> handleFileUpload(@RequestParam("file") MultipartFile file,
            RedirectAttributes redirectAttributes) {

        storageService.store(file);
        System.out.println(file);
        System.out.println(file.getOriginalFilename());
        Resource fileResponse = storageService.loadAsResource(file.getOriginalFilename());
        
        //Kas seda on vaja? see toimib aint redirect puhul ja ma ei taha siia redirecti.
//        redirectAttributes.addFlashAttribute("message",
//                "You successfully uploaded " + file.getOriginalFilename() + "!");
        
        //TODO - siia tahaks lisada faili lugemise ja händlimise.
        
        

//        return "redirect:/";
        return new ResponseEntity("Successfully uploaded - " +
        		file.getOriginalFilename(), new HttpHeaders(), HttpStatus.OK);
        
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
    
}
