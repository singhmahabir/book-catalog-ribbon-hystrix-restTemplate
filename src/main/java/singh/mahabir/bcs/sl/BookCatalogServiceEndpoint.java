package singh.mahabir.bcs.sl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import singh.mahabir.bcs.bl.IBookCatalogService;
import singh.mahabir.bcs.sl.model.Book;
import singh.mahabir.bcs.sl.model.CatalogResponse;
import singh.mahabir.bcs.sl.model.CatalogResponses;
import singh.mahabir.bcs.sl.model.UserRatings;

/**
 * Implementation of 'book-catalog-service'
 *
 * @author Mahabir Singh
 */

@RestController
@Slf4j
public class BookCatalogServiceEndpoint implements IBookCatalogServiceEndpoint {

    @Autowired
    private IBookCatalogService bookService;

    @Override
    @GetMapping(path = "/catalog/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CatalogResponses> getBooksDetails(@PathVariable String userId) {
	log.info("request came for userId {}", userId);

	// using userId, will call rating data service and get bookId's with rating and
	// comment

	final UserRatings ratings = bookService.getUserRatingDetails(userId);

	// for each book id, call book info service and get details

	final List<CatalogResponse> response = ratings.getRating().stream().map(rating -> {
	    final Book book = bookService.getBookInformation(rating.getBookId());
	    return new CatalogResponse(book.getName(), book.getDesc(), book.getAuthor(), book.getLaunchedDate(),
		    rating.getBookId(), rating.getRating(), rating.getComment());
	}).collect(Collectors.toList());

	return new ResponseEntity<>(new CatalogResponses(userId, response), HttpStatus.OK);
    }
}
