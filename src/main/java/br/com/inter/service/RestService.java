package br.com.inter.service;

import org.apache.http.client.methods.CloseableHttpResponse;

public interface RestService {
	
	CloseableHttpResponse post(String url, Object request) throws Exception;
	
}
