package io.chandranath.chatterjee.discoveryclient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class DiscoveryClientApplication {

	@Autowired
	RestTemplateBuilder builder;

	@Autowired
	EurekaClient client;

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryClientApplication.class, args);
	}

	@RequestMapping("/client")
	public String clientHandler() {
		RestTemplate template = builder.build();
		InstanceInfo info = client.getNextServerFromEureka("discovery-service", false);
		ResponseEntity<String> response = template.exchange(info.getHomePageUrl()+"/service", HttpMethod.GET, null, String.class);
		return response.getBody();
	}

}
