/**
 * Copyright 2019. All rights reserved.
 */

package singh.mahabir.bcs.configuration;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

/**
 * This class is required when we are not using eureka server
 *
 * we have two way to configure the client like below
 *
 * But in latest version of spring.cloud like this even this configuration is
 * not required
 *
 * @author Mahabir Singh
 *
 */
@Configuration
//@RibbonClient(name = "ratings-data-service")
@RibbonClients(value = { @RibbonClient(name = "book-info-service"), @RibbonClient(name = "ratings-data-service") })
public class RibbonClientConfiguration {

}