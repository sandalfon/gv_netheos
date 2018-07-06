package org.sandalfon.netheos.test;


import static org.assertj.core.api.Assertions.*;

import java.nio.charset.Charset;
import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.sandalfon.netheos.Netheos;
import org.sandalfon.netheos.entity.Account;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
/**
 * Security and content tests 
 *  /!\ the netheos applciation must run behind 
 * 
 * @author Gilles VIEIRA
 *
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Netheos.class)
public class AccountControllerTests {


	@Test
	public void getUsernameTest() {
		RestTemplate restTemplate = new RestTemplate();   
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString("netheos:adminNetheos".getBytes(Charset.forName("UTF-8"))));
		HttpEntity<String> myRequest = new HttpEntity<>( headers);

		ResponseEntity<Account> response = restTemplate.exchange(
				"http://localhost:8080/netheos/account/1",
				HttpMethod.GET,
				myRequest,
				Account.class);

		Account returnedAccount = response.getBody();

		assertThat(returnedAccount.getUsername()).isEqualTo("netheos");

	}

	@Test
	public void getError403Test() {
		RestTemplate restTemplate = new RestTemplate();   
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.getUrlEncoder().encodeToString("netheosUser:userNetheos".getBytes(Charset.forName("UTF-8"))));
		HttpEntity<String> myRequest = new HttpEntity<>( headers);
		int code =200;
		try{	
			restTemplate.exchange(
					"http://localhost:8080/netheos/account/1",
					HttpMethod.GET,
					myRequest,
					Account.class).getStatusCode();
		}catch(HttpClientErrorException e) {
			code = e.getRawStatusCode();
		}finally {
			assertThat(code).isEqualTo(403);
		}
	}


}