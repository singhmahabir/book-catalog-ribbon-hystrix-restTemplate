package singh.mahabir.bcs.sl.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@link Book} is a POJO class
 * 
 * @author Mahabir Singh
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	private String bookId;
	private String name;
	private String desc;
	private String author;
	private LocalDate launchedDate;
}
