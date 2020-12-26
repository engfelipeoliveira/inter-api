package br.com.inter.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.inter.dto.Request;

@Service
public class MainServiceImpl implements MainService {

	@Value("${inter.api.url.post.invoice}")
	private String URL_POST_INVOICE;

	private static final Logger LOG = getLogger(MainServiceImpl.class);

	private final RestService restService;
	private final XlsService xlsService;

	MainServiceImpl(RestService restService, XlsService xlsService) {
		this.restService = restService;
		this.xlsService = xlsService;
	}

	@Override
	public void execute() throws Exception {

		HashMap<File, List<Row>> mapFileRows = xlsService.read();

		mapFileRows.entrySet().stream().forEach(entry -> {
			entry.getValue().stream().forEach(row -> {
				try {
					Request request = this.xlsService.buildRequest(row);
					try {
						CloseableHttpResponse response = this.restService.post(URL_POST_INVOICE, request);

						if (!isHttpStatusOK(response)) {
							LOG.error("API retornou um erro");
							LOG.error("API - Codigo do erro : " + response.getStatusLine().getStatusCode());
							LOG.error("API - Detalhe do erro : " + EntityUtils.toString(response.getEntity()));
							LOG.error("Arquivo : " + entry.getKey().getName());
							LOG.error("Linha : " + row.getRowNum());
						}

					} catch (Exception e) {
						LOG.error("Erro generico ao executar API", e);
						LOG.error("Arquivo : " + entry.getKey().getName());
						LOG.error("Linha : " + row.getRowNum());
					}
				} catch (Exception e) {
					LOG.error("Erro ao ler linha do arquivo xls", e);
					LOG.error("Arquivo : " + entry.getKey().getName());
					LOG.error("Linha : " + row.getRowNum());
				}
			});
		});

	}

	private boolean isHttpStatusOK(CloseableHttpResponse response) {
		return response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300;
	}
}
