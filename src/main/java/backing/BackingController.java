package backing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BackingController {

    Logger logger = LoggerFactory.getLogger(BackingController.class);

    @Value("#{environment.BACKING_SERVICE_ENDPOINT}")
    private String endpoint;

    @Value("#{environment.BACKING_SERVICE_ENDPOINT_FALLBACK}")
    private String endpoint_fallback;

    private boolean useFallback = false;

    @GetMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public String getCurrency() {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = useFallback ? endpoint_fallback : endpoint;
        useFallback = !useFallback;
        if (resourceUrl == null) {
            return "Backing service is not configured correctly.";
        }
        logger.info("Effective backing service endpoint is {}", resourceUrl);
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl, String.class);
        return response.getBody();
    }
}
