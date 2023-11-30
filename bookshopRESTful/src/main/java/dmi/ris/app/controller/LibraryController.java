package dmi.ris.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("library")
@Tag(name = "Servisi za rad sa eksternim sistemom biblioteke")

public class LibraryController {
	@Autowired
	RestTemplate restTemplate;
	
	@Operation(summary = "Servis koji vraca sve knjige u biblioteci")
	@GetMapping("books")
	public ResponseEntity<List> getBooksFromLibrary() {
		return restTemplate.getForEntity("http://localhost:8081/Biblioteka/rest/knjige/all", List.class);
	}

}
