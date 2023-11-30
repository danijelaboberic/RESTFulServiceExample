package dmi.ris.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import dmi.ris.app.model.Book;
import dmi.ris.app.service.BookshopManager;

@RestController
public class BookshopControler {
	
	BookshopManager manager = new BookshopManager();
	

	@GetMapping("/books")
	public List<Book> getAllBooks(){
		return manager.getBookshop();
	}
	@GetMapping("/books/{title}")
	public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable("title") String title){
		List books = manager.getBookByTitle(title);
		if ((books == null)||(books.isEmpty())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		}else {
			return ResponseEntity.ok(books);
		}
		
	}
	@PostMapping("/book")
	public ResponseEntity<String> saveBook(@RequestBody Book book){
		boolean ok = manager.addBook(book);
		ResponseEntity<String> response;
		if (ok) {
			 return ResponseEntity.ok("Uspešno je sačuvana knjiga");

		}else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
		
	}
	@DeleteMapping("/books/{id}")
	public ResponseEntity removeBook(@PathVariable("id") String id){
		boolean ok = manager.removeBook(id);
		if(ok) {
			return ResponseEntity.ok().build();
		}else{
			return ResponseEntity.noContent().build();
		}
	}
	@GetMapping("/hateoas/books")
	public CollectionModel<EntityModel<Book>> getAllBooksHateoas(){
		
		List<EntityModel<Book>> books = manager.getBookshop().stream().
				map(b-> EntityModel.of(b,
				linkTo(methodOn(BookshopControler.class).getOneHateoas(b.getId())).withRel("one"),
				linkTo(methodOn(BookshopControler.class).removeBook(b.getId())).withRel("remove"))).
				collect(Collectors.toList());
		
		return CollectionModel.of(books, linkTo(methodOn(BookshopControler.class).getAllBooksHateoas()).withSelfRel());	
	}
	
	@GetMapping("/hateoas/books/one/{id}")
	public EntityModel<Book> getOneHateoas(@PathVariable("id") String id){
		Book book = manager.getBookById(id);
		return EntityModel.of(book, linkTo(methodOn(BookshopControler.class).getAllBooksHateoas()).withRel("books"),
				linkTo(methodOn(BookshopControler.class).getOneHateoas(id)).withSelfRel());	
	}
}
