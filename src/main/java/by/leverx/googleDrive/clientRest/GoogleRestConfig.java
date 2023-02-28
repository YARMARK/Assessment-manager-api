package by.leverx.googleDrive.clientRest;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GoogleRestConfig {

  @Value("${drive.env.client-id}")
  private String CLIENT_ID;

  @Value("${drive.env.client-secret}")
  private String CLIENT_SECRET;

  @Bean
  public RestTemplate googleRestTemplate() {
    RestTemplate restTemplate = new RestTemplate();
    ClientHttpRequestInterceptor interceptor = new BasicAuthenticationInterceptor(CLIENT_ID,
        CLIENT_SECRET);
    List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
    interceptors.add(interceptor);
    restTemplate.setInterceptors(interceptors);
    return restTemplate;
  }

}
