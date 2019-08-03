package singh.mahabir.bcs.sl.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@link CatalogResponse} CatalogResponse is a POJO class
 * 
 * @author Mahabir Singh
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CatalogResponse {

	private String name;
	private String desc;
	private String author;
	private LocalDate launchedDate;
	private String bookId;
	private int rating;
	private String comment;
		
}
