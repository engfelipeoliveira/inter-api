package br.com.inter.service;

import static org.slf4j.LoggerFactory.getLogger;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.inter.dto.Desconto;
import br.com.inter.dto.Mensagem;
import br.com.inter.dto.Mora;
import br.com.inter.dto.Multa;
import br.com.inter.dto.Pagador;
import br.com.inter.dto.Request;

@Service
public class MainServiceImpl implements MainService {

	@Value("${inter.api.url.post.invoice}")
	private String URL_POST_INVOICE;
	
	private static final Logger LOG = getLogger(MainServiceImpl.class);

	private final RestService restService;
	
	MainServiceImpl(RestService restService) {
		this.restService = restService;
	}
	
	@Override
	public void execute() throws Exception {
		
		Request request = Request.builder()
				.pagador(Pagador.builder()
						.cnpjCpf("26103754097")
						.nome("luis felipe")
						.email("luisfelipemileo@gmail.com")
						.telefone("988763663")
						.cep("31327130")
						.numero("201")
						.complemento("casa")
						.bairro("centro")
						.cidade("SAO PAULO")
						.uf("SP")
						.endereco("RUA TESTE")
						.ddd("12")
						.tipoPessoa("FISICA")
						.build())
				.dataEmissao("2020-08-25")
				.seuNumero("1234567810")
				.dataLimite("SESSENTA")
				.dataVencimento("2020-08-28")
				.mensagem(Mensagem.builder()
						.linha1("TEXTO 1")
						.build())
				.desconto1(Desconto.builder()
						.codigoDesconto("NAOTEMDESCONTO")
						.taxa(new BigDecimal(0))
						.valor(new BigDecimal(0))
						.data("")
						.build())
				.desconto2(Desconto.builder()
						.codigoDesconto("NAOTEMDESCONTO")
						.taxa(new BigDecimal(0))
						.valor(new BigDecimal(0))
						.data("")
						.build())
				.desconto3(Desconto.builder()
						.codigoDesconto("NAOTEMDESCONTO")
						.taxa(new BigDecimal(0))
						.valor(new BigDecimal(0))
						.data("")
						.build())
				.valorNominal(new BigDecimal(100))
				.valorAbatimento(new BigDecimal(0))
				.multa(Multa.builder()
						.codigoMulta("PERCENTUAL")
						.taxa(new BigDecimal(5))
						.valor(new BigDecimal(0))
						.data("2020-08-31")
						.build())
				.mora(Mora.builder()
						.codigoMora("TAXAMENSAL")
						.taxa(new BigDecimal(1))
						.valor(new BigDecimal(0))
						.data("2020-08-31")
						.build())
				.cnpjCPFBeneficiario("23130935000198")
				.numDiasAgenda("SESSENTA")
				.build();
		this.restService.post(URL_POST_INVOICE, request);
	}
}
