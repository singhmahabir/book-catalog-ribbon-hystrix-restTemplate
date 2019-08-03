package singh.mahabir.bcs.sl.model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@link UserRatings} UserRatings is a response class
 * 
 * Returning a class is always better than a array because in future we can
 * change the response without any problem and rest client to accept a array is
 * a bulky job
 * 
 * @author Mahabir Singh
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserRatings {

	private List<UserRating> rating = new ArrayList<>();
}
