package dmi.ris.app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import dmi.ris.app.model.Author;
import dmi.ris.app.model.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookshopManager {
	
	private List<Book> bookshop = new ArrayList<Book>();
	public BookshopManager() {
		Author a1 = new Author();
		a1.setFirstName("Craig");
		a1.setLastName("Walls");
		a1.setBiography("Craig Walls is a software developer at Pivotal and is the author of Spring in Action. He's a zealous promoter of the Spring Framework, speaking frequently at local user groups and conferences. When he's not writing about Spring or slinging code, Craig spends as much time as he can with his wife and two daughters.");
		Book b1 = new Book();
		b1.setId("1");
		b1.setTitle("Spring Boot in Action");
		b1.setPrice(35.00);
		b1.setPublisher("Manning Publications");
		List<Author> authorList = new ArrayList();
		authorList.add(a1);
		b1.setAuthors(authorList);
		
		Book b2 = new Book();
		b2.setId("2");
		b2.setTitle("Spring in Action");
		b2.setAuthors(authorList);
		b2.setPublisher("Manning Publications");
		b2.setPrice(25);
		bookshop.add(b1);
		bookshop.add(b2);
				
		
	}
	public boolean addBook(Book book) {
		bookshop.add(book);
		return true;
	}
	
	public List<Book> getBookByTitle(String title){
		return bookshop.stream().filter(b -> b.getTitle().contains(title)).collect(Collectors.toList());
	}
	public Book getBookById(String id){
		return bookshop.stream().filter(b -> b.getId().equals(id)).findFirst().get();
	}
	
	public boolean removeBook(String id){
		Optional<Book> forRemove = bookshop.stream().filter(b->b.getId().equalsIgnoreCase(id)).findFirst();
		if(forRemove.isPresent()) {
			return bookshop.remove(forRemove);
		}else {
			return false;
		}
		
	}
}
