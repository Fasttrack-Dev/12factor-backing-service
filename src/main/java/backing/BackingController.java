package backing;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class BackingController {

    @GetMapping("/currency")
    @ResponseStatus(HttpStatus.OK)
    public String getCurrency() {

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl
                = "https://api.frankfurter.app/latest";
        ResponseEntity<String> response
                = restTemplate.getForEntity(resourceUrl , String.class);
        return response.getBody();
    }
}
