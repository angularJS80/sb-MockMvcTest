package cho.me.springwebtest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class SpringwebtestApplicationTests {



	@Autowired
	TestRestTemplate testRestTemplate;

	@LocalServerPort
	int localServerPort;

	@TestConfiguration // Configuration 으로 변경시 테스트가 메인이 됨으로 실제 어플리케이션에서 사용중인 빈을 주입 받을 수 없다. 주입받으려면 컴포넌트 스캔 해야함.
	static class TestConfig{
		@Bean
		public String getTestBean(){
			return "Its TestBean";
		}

	}

	@Autowired
	MockMvc mockMvc;

	@Autowired
	TestConfig testConfig;

	@Test
	public void contextLoads() throws Exception{

		System.out.println("localServerPort : "+localServerPort);
		assertThat(mockMvc).isNotNull();
		mockMvc.perform(get("/"))
				.andExpect(status().isOk())
				.andExpect(content().string("Hello World"))
				.andDo(print());

		String body = testRestTemplate.getForObject("/",String.class);
		assertThat(body).isEqualTo("Hello World");





	}

}
