package singh.mahabir.bcs.sl;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import singh.mahabir.bcs.sl.model.CatalogResponses;

/**
 * Contains endpoint to expose 'user-catalog-service'
 * 
 * @author Mahabir Singh
 *
 */
public interface IBookCatalogServiceEndpoint {
	
	/**
	 * It returns the list of users books rating details
	 * 
	 * @param userId userId
	 * @return {@link CatalogResponses} CatalogResponses
	 */
	@GetMapping(path="/catalog/{userId}" ,produces= MediaType.APPLICATION_JSON_VALUE )
	public ResponseEntity<CatalogResponses> getBooksDetails(@PathVariable String userId);
}
