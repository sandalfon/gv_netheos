package org.sandalfon.netheos.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;
import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sandalfon.netheos.Netheos;
import org.sandalfon.netheos.entity.Faq;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


/**
 * Security and content tests for the use case 2
 *  /!\ the netheos applciation must run behind 
 * 
 * @author Gilles VIEIRA
 *
 */


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Netheos.class)
public class Usecase2Test {

	private String adminUser ="netheos";
	private String adminPwd="adminNetheos";
	private String userUser="netheosUser";
	private String userPwd="userNetheos";
	private String norRoleUser="tata";
	private String norolePwd="tata";


	
	@Test
	public void getUsecase2AdminAccesTest() {
		RestTemplate restTemplate = new RestTemplate();   
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((adminUser+":"+adminPwd).getBytes(Charset.forName("UTF-8"))));

		HttpEntity<String> myRequest = new HttpEntity<>( headers);

		ResponseEntity<Faq[]> response = restTemplate.exchange(
				"http://localhost:8080/netheos/faq",
				HttpMethod.GET,
				myRequest,
				Faq[].class);

		Faq[] returnedFaq = response.getBody();

		assertThat(returnedFaq.length).isEqualTo(5);

	}

	
	
	@Test
	public void usecase2AdminAcces403Test() {
		usecase2AdminAcces403Login(userUser,userPwd,403);
		usecase2AdminAcces403Login(norRoleUser,norolePwd,403);
	}
	
	private void usecase2AdminAcces403Login(String user, String pwd, int codeRef) {
		RestTemplate restTemplate = new RestTemplate();   
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString((user+":"+pwd).getBytes(Charset.forName("UTF-8"))));

		HttpEntity<String> myRequest = new HttpEntity<>( headers);


		int code =200;
		try{	
			restTemplate.exchange(
					"http://localhost:8080/netheos/faq",
					HttpMethod.GET,
					myRequest,
					Faq[].class).getStatusCode();
		}catch(HttpClientErrorException e) {
			code = e.getRawStatusCode();
		}finally {
			assertThat(code).isEqualTo(codeRef);
		}
	}
	
	
	

}
