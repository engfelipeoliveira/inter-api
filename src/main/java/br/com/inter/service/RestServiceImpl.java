package br.com.inter.service;

import static org.apache.http.impl.client.HttpClients.createDefault;
import static org.slf4j.LoggerFactory.getLogger;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class RestServiceImpl implements RestService {
	
	private static final Logger LOG = getLogger(RestServiceImpl.class);
	
	@Override
	public CloseableHttpResponse post(String url, Object request) throws Exception {
		CloseableHttpClient httpClient = createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			Gson gson = new Gson();
			String json = gson.toJson(request);
			LOG.info(json);

			StringEntity entity = new StringEntity(json);
			httpPost.setEntity(entity);
			httpPost.setHeader("content-type", "application/json");
			httpPost.setHeader("accept", "application/json");
			httpPost.setHeader("x-inter-conta-corrente", "14054310");
			response = httpClient.execute(httpPost);
		} finally {
			httpClient.close();
			response.close();
		}

		return response;
	}

}
