package singh.mahabir.bcs.bl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;
import singh.mahabir.bcs.sl.model.Book;
import singh.mahabir.bcs.sl.model.UserRatings;

/**
 * Service Layer implementation
 * 
 * @author Mahabir Singh
 *
 */
@Service
@Slf4j
public class BookCatalogService implements IBookCatalogService {

	/*
	 * When we call the dependent of micro service or external end point
	 * 
	 * So 1st way to use this
	 * 
	 */
	@Autowired
	private RestTemplate restTemplate;
	
	
	@Override
	@HystrixCommand(fallbackMethod = "getUserRatingDetailsFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })
	public UserRatings getUserRatingDetails(String userId){
		log.info("request came at service layer for userId {}", userId);
		
	/**	 this way we can call rest Endpoints which returns List of Objects return
	 
		  restTemplate.exchange("http://localhost:9093/ratingsdata/users/"+userId,
		  HttpMethod.GET, null, new
		  ParameterizedTypeReference<List<Rating>>(){}).getBody();
		  */
		
		return restTemplate.getForObject("http://ratings-data-service/ratings/" + userId, UserRatings.class);
	}

	@Override
	@HystrixCommand(fallbackMethod = "getBookInformationFallback", commandProperties = {
			@HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
			@HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
			@HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })
	public Book getBookInformation(String bookId) {
		log.info("request came at service layer for bookId {}", bookId);
		// If we are using restTemplate as http client
		return restTemplate.getForObject("http://book-info-service/book/" + bookId, Book.class);
	}

	private UserRatings getUserRatingDetailsFallback(String userId) {
		return new UserRatings();
	}

	private Book getBookInformationFallback(String userId) {
		return new Book("", "", "", "", null);
	}
}
