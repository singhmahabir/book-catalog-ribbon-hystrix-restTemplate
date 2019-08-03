package singh.mahabir.bcs.sl.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A POJO class of {@link UserRatings}
 * 
 * @author Mahabir Singh
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRating {

	private String bookId;
	private int rating;
	private String comment;
}
