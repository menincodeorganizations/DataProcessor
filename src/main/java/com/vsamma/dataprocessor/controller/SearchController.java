package com.vsamma.dataprocessor.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.spring4.view.AjaxThymeleafViewResolver;

import com.vsamma.dataprocessor.dto.PersonDTO;
import com.vsamma.dataprocessor.model.AjaxResponseBody;
import com.vsamma.dataprocessor.model.Person;
import com.vsamma.dataprocessor.model.SearchQuery;
import com.vsamma.dataprocessor.repository.PersonRepository;
import com.vsamma.dataprocessor.storage.StorageService;

@Controller
public class SearchController {
	
	//Some service is needed
//    UserService userService;
//
//    @Autowired
//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
	
	@Autowired
	StorageService storageService;

	@PostMapping("/search")
	@ResponseBody
	public ResponseEntity<?> getSearchResultsViaAjax(
			@Valid @RequestBody SearchQuery query, Errors errors) {
		
		AjaxResponseBody result = new AjaxResponseBody();
		
        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            String err = (errors.getAllErrors()
                        .stream().map(x -> x.getDefaultMessage())
                        .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(err);
        }
        
//        List<Person> srcResult = personRepository.findByNameContaining(query.getQuery());
//        List<PersonDTO> users = userService.findByUserNameOrEmail(search.getUsername());
//        if (users.isEmpty()) {
//            result.setMsg("no user found!");
//        } else {
//            result.setMsg("success");
//        }
//        result.setResult(users);
        
        List<PersonDTO> srcResult = storageService.findByNameContaining(query.getQuery());
        result.setResults(srcResult);

        return ResponseEntity.ok(result);
	}

}
