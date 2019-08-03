package singh.mahabir.bcs.bl;

import singh.mahabir.bcs.sl.model.Book;
import singh.mahabir.bcs.sl.model.UserRatings;

/**
 * It is service layer of 'Book Catalog Service'
 * 
 * @author Mahabir Singh
 *
 */
public interface IBookCatalogService {

	/**
	 * It return the user ratings of books
	 * 
	 * @param userId userId
	 * @return {@link UserRatings} UserRatings
	 */
	UserRatings getUserRatingDetails(String userId);

	/**
	 * It return the book information of requested bookId
	 * 
	 * @param bookId bookId
	 * @return {@link Book} Book
	 */
	Book getBookInformation(String bookId);

}
