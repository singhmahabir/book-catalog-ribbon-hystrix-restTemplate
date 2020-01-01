package singh.mahabir.bcs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Main class to start the book catalog service
 *
 * @author Mahabir Singh
 *
 */
@SpringBootApplication
/**
 * By having "spring-cloud-starter-netflix-eureka-client" on the classpath your
 * application will automatically register with the Eureka Server
 * without @EnableDiscoveryClient
 *
 * since 1.4.7.RELEASE
 *
 * Upto 1.3.6.RELEASE we required @EnableDiscoveryClient
 */
//@EnableDiscoveryClient
/**
 *
 * @EnableCircuitBreaker It is not required for fallback
 *
 */
@EnableCircuitBreaker
@EnableHystrixDashboard
public class BookCatalogServiceApplication {

    /**
     * To call a end point one way to use RestTemplate
     *
     * For that we need to create a bean and pass to SpringContaxt and we
     * can @Autowired when required
     *
     * @return RestTemplate
     */
    @Bean
    @LoadBalanced // It is required to distribute the request using ribbon
    public RestTemplate getRestTemplate() {
	// This way we can set the time out into Rest Template
	final HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
	clientHttpRequestFactory.setConnectTimeout(3000);
//	restTemplate.getInterceptors().add(new BasicAuthorizationInterceptor("user", "user"));
	return new RestTemplate(clientHttpRequestFactory);

    }

    public static void main(String[] args) {
	SpringApplication.run(BookCatalogServiceApplication.class, args);
    }

}
