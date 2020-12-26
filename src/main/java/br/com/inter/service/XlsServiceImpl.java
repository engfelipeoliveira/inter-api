package br.com.inter.service;

import static org.apache.poi.ss.usermodel.CellType.STRING;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.inter.dto.Desconto;
import br.com.inter.dto.Mensagem;
import br.com.inter.dto.Mora;
import br.com.inter.dto.Multa;
import br.com.inter.dto.Pagador;
import br.com.inter.dto.Request;

@Service
public class XlsServiceImpl implements XlsService {

	@Value("${dir.source.xls.files}")
	private String DIR_SOURCE;

	@Value("${dir.target.xls.files}")
	private String DIR_TARGET;

	@Override
	public List<Request> read() {
		List<Request> requests = new ArrayList<Request>();

		FilenameFilter filter = FileUtils.filterFile("XLS", "XLSX");
		File[] files = new File(DIR_SOURCE).listFiles(filter);
		for (File file : files) {
			try {
				requests = readRequests(file);
				FileUtils.moveFile(DIR_TARGET, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return requests;
	}

	private List<Request> readRequests(File file) throws IOException {
		List<Request> requests = new ArrayList<Request>();

		FileInputStream fileInputStream = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();
		
		while (rowIterator.hasNext()) {
			requests.add(this.buildRequest(rowIterator.next()));
		}

		workbook.close();
		fileInputStream.close();

		return requests;
	}
	
	private String getString(Row row, int index) {
		return row.getCell(index) != null ? row.getCell(index).getStringCellValue() : null;
	}
	
	private BigDecimal getBigDecimal(Row row, int index) {
		return row.getCell(index) != null ? new BigDecimal(row.getCell(index).getStringCellValue()) : null;
	}
	
	private Pagador buildPagador(Row row) {
		return Pagador.builder()
				.cnpjCpf(getString(row, 0))
				.nome(getString(row, 1))
				.email(getString(row, 2))
				.telefone(getString(row, 3))
				.cep(getString(row, 4))
				.numero(getString(row, 5))
				.complemento(getString(row, 6))
				.bairro(getString(row, 7))
				.cidade(getString(row, 8))
				.uf(getString(row, 9))
				.endereco(getString(row, 10))
				.ddd(getString(row, 11))
				.tipoPessoa(getString(row, 12))
				.build();
	}
	
	private Mensagem buildMensagem(Row row) {
		return Mensagem.builder()
				.linha1(getString(row, 17))
				.build();
	}
	
	private Desconto buildDesconto1(Row row) {
		return Desconto.builder()
				.codigoDesconto(getString(row, 18))
				.taxa(getBigDecimal(row, 19))
				.valor(getBigDecimal(row, 20))
				.data(getString(row, 21))
				.build();
	}
	
	private Desconto buildDesconto2(Row row) {
		return Desconto.builder()
				.codigoDesconto(getString(row, 22))
				.taxa(getBigDecimal(row, 23))
				.valor(getBigDecimal(row, 24))
				.data(getString(row, 25))
				.build();
	}
	
	private Desconto buildDesconto3(Row row) {
		return Desconto.builder()
				.codigoDesconto(getString(row, 26))
				.taxa(getBigDecimal(row, 27))
				.valor(getBigDecimal(row, 28))
				.data(getString(row, 29))
				.build();
	}
	
	private Multa buildMulta(Row row) {
		return Multa.builder()
				.codigoMulta(getString(row, 30))
				.taxa(getBigDecimal(row, 31))
				.valor(getBigDecimal(row, 32))
				.data(getString(row, 33))
				.build();
	}

	private Mora buildMora(Row row) {
		return Mora.builder()
				.codigoMora(getString(row, 34))
				.taxa(getBigDecimal(row, 35))
				.valor(getBigDecimal(row, 36))
				.data(getString(row, 37))
				.build();
	}
	
	private Request buildRequest(Row row) {
		for(int i=0; i<=39; i++) {
			if(row.getCell(i) != null) {
				row.getCell(i).setCellType(STRING);	
			}
		}
		
		return Request.builder()
				.pagador(buildPagador(row))
				.dataEmissao(getString(row, 13))
				.seuNumero(getString(row, 14))
				.dataLimite(getString(row, 15))
				.dataVencimento(getString(row, 16))
				.mensagem(buildMensagem(row))
				.desconto1(buildDesconto1(row))
				.desconto2(buildDesconto2(row))
				.desconto3(buildDesconto3(row))
				.valorNominal(new BigDecimal(100))
				.valorAbatimento(new BigDecimal(0))
				.multa(buildMulta(row))
				.mora(buildMora(row))
				.cnpjCPFBeneficiario(getString(row, 38))
				.numDiasAgenda(getString(row, 39))
				.build();
	}

}
