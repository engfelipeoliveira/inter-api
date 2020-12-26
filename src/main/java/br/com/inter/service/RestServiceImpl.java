package br.com.inter.service;

import static java.lang.String.format;
import static org.apache.http.impl.client.HttpClients.createDefault;
import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class RestServiceImpl implements RestService {
	
	private static final Logger LOG = getLogger(RestServiceImpl.class);
	
	@Override
	public CloseableHttpResponse post(String url, Object request) throws IOException {
		CloseableHttpClient httpClient = createDefault();
		CloseableHttpResponse response = null;
		try {
			HttpPost httpPost = new HttpPost(url);
			Gson gson = new Gson();
			String json = gson.toJson(request);
			LOG.info(format("Executando API %s com conteudo %s", url, json));

			StringEntity entity = new StringEntity(json);
			httpPost.setEntity(entity);
			httpPost.setHeader("content-type", "application/json");
			httpPost.setHeader("accept", "application/json");
			httpPost.setHeader("x-inter-conta-corrente", "14054310");
			response = httpClient.execute(httpPost);

			if (isHttpStatusOK(response)) {
				LOG.info("Integrado com sucesso");
			} else {
				LOG.error("Erro ao executar integracao");
				LOG.error("Codigo do erro : " + response.getStatusLine().getStatusCode());
				LOG.error(EntityUtils.toString(response.getEntity()));
			}
		} catch  (Exception e) {
			LOG.error("Erro ao executar integracao", e);
		} finally {
			httpClient.close();
		}

		return response;
	}
	
	private boolean isHttpStatusOK(CloseableHttpResponse response) {
		return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
	}
	

}
