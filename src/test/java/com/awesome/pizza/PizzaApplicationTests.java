package com.awesome.pizza;

import com.awesome.pizza.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, properties = {
		"aws.region=eu-central-1",
		"spring.autoconfigure.exclude=org.springframework.cloud.function.serverless.web.ServerlessAutoConfiguration"
})
class PizzaApplicationTests {

	@MockBean
	OrderRepository orderRepository;

	@Test
	void contextLoads() {
	}
}
