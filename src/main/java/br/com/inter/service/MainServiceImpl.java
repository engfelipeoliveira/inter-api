package br.com.inter.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.io.IOException;
import java.util.List;

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
		
		List<Request> requests = xlsService.read();
		requests.stream().forEach(request -> {
			try {
				this.restService.post(URL_POST_INVOICE, request);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
