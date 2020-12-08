package dmi.ris.app.model;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class Book extends RepresentationModel<Book>{
	
	private String id;
	private String title;
	private List<Author> authors;
	private String publisher;
	private double price;
	private boolean inStock;

}
