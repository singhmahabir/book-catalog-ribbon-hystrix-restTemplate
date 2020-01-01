package singh.mahabir.bcs.bl;

import java.nio.charset.Charset;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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

    @Value("${client.common.username}")
    private String password;

    @Value("${client.common.password}")
    private String username;

    @Override
    @HystrixCommand(fallbackMethod = "getUserRatingDetailsFallback", commandProperties = {
	    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000"),
	    @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
	    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "5"),
	    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "50"),
	    @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000") })
    public UserRatings getUserRatingDetails(String userId) {
	log.info("request came at service layer for userId {}", userId);

	/**
	 * this way we can call rest Endpoints which returns List of Objects return
	 *
	 * restTemplate.exchange("http://localhost:9093/ratingsdata/users/"+userId,
	 * HttpMethod.GET, null, new
	 * ParameterizedTypeReference<List<Rating>>(){}).getBody();
	 */
	final ResponseEntity<UserRatings> exchange = restTemplate.exchange(
		"http://ratings-data-service/ratings/" + userId, HttpMethod.GET,
		new HttpEntity<>(createHeaders(username, password)), UserRatings.class);
	if (exchange != null) {
	    return exchange.getBody();
	} else {
	    return null;
	}
	// return restTemplate.getForObject("http://ratings-data-service/ratings/" +
	// userId, UserRatings.class);
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
	final ResponseEntity<Book> exchange = restTemplate.exchange("http://book-info-service/book/" + bookId,
		HttpMethod.GET, new HttpEntity<>(createHeaders(username, password)), Book.class);
	if (exchange != null) {
	    return exchange.getBody();
	} else {
	    return getBookInformationFallback(bookId);
	}
	// return restTemplate.getForObject("http://book-info-service/book/" + bookId,
	// Book.class);
    }

    private HttpHeaders createHeaders(String username, String password) {
	return new HttpHeaders() {
	    private static final long serialVersionUID = 1606079435886489118L;

	    {
		final String auth = username + ":" + password;
		final byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII")));
		final String authHeader = "Basic " + new String(encodedAuth);
		set("Authorization", authHeader);
	    }
	};
    }

    private UserRatings getUserRatingDetailsFallback(String userId) {
	log.info("No Rating found for userId: {}", userId);
	return new UserRatings();
    }

    private Book getBookInformationFallback(String bookId) {
	return new Book(bookId, "", "", "", null);
    }
}
