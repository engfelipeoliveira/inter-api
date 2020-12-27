package br.com.inter.service;

import static br.com.inter.service.FileUtils.filterFile;
import static java.lang.Double.parseDouble;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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

	@Value("${xls.index.total}")
	private int INDEX_TOTAL;

	@Value("${xls.index.pagador.cnpjCpf}")
	private int INDEX_PAGADOR_CNPJ_CPF;

	@Value("${xls.index.pagador.nome}")
	private int INDEX_PAGADOR_NOME;

	@Value("${xls.index.pagador.email}")
	private int INDEX_PAGADOR_EMAIL;

	@Value("${xls.index.pagador.telefone}")
	private int INDEX_PAGADOR_TELEFONE;

	@Value("${xls.index.pagador.cep}")
	private int INDEX_PAGADOR_CEP;

	@Value("${xls.index.pagador.numero}")
	private int INDEX_PAGADOR_NUMERO;

	@Value("${xls.index.pagador.complemento}")
	private int INDEX_PAGADOR_COMPLEMENTO;

	@Value("${xls.index.pagador.bairro}")
	private int INDEX_PAGADOR_BAIRRO;

	@Value("${xls.index.pagador.cidade}")
	private int INDEX_PAGADOR_CIDADE;

	@Value("${xls.index.pagador.uf}")
	private int INDEX_PAGADOR_UF;

	@Value("${xls.index.pagador.endereco}")
	private int INDEX_PAGADOR_ENDERECO;

	@Value("${xls.index.pagador.ddd}")
	private int INDEX_PAGADOR_DDD;

	@Value("${xls.index.pagador.tipoPessoa}")
	private int INDEX_PAGADOR_TIPO_PESSOA;

	@Value("${xls.index.dataEmissao}")
	private int INDEX_DATA_EMISSAO;

	@Value("${xls.index.seuNumero}")
	private int INDEX_SEU_NUMERO;

	@Value("${xls.index.dataLimite}")
	private int INDEX_DATA_LIMITE;

	@Value("${xls.index.dataVencimento}")
	private int INDEX_DATA_VENCIMENTO;

	@Value("${xls.index.mensagem.linha1}")
	private int INDEX_MENSAGEM_LINHA_1;

	@Value("${xls.index.desconto1.codigoDesconto}")
	private int INDEX_DESCONTO1_CODIGO_DESCONTO;

	@Value("${xls.index.desconto1.taxa}")
	private int INDEX_DESCONTO1_TAXA;

	@Value("${xls.index.desconto1.valor}")
	private int INDEX_DESCONTO1_VALOR;

	@Value("${xls.index.desconto1.data}")
	private int INDEX_DESCONTO1_DATA;

	@Value("${xls.index.desconto2.codigoDesconto}")
	private int INDEX_DESCONTO2_CODIGO_DESCONTO;

	@Value("${xls.index.desconto2.taxa}")
	private int INDEX_DESCONTO2_TAXA;

	@Value("${xls.index.desconto2.valor}")
	private int INDEX_DESCONTO2_VALOR;

	@Value("${xls.index.desconto2.data}")
	private int INDEX_DESCONTO2_DATA;

	@Value("${xls.index.desconto3.codigoDesconto}")
	private int INDEX_DESCONTO3_CODIGO_DESCONTO;

	@Value("${xls.index.desconto3.taxa}")
	private int INDEX_DESCONTO3_TAXA;

	@Value("${xls.index.desconto3.valor}")
	private int INDEX_DESCONTO3_VALOR;

	@Value("${xls.index.desconto3.data}")
	private int INDEX_DESCONTO3_DATA;

	@Value("${xls.index.valorNominal}")
	private int INDEX_VALOR_NOMINAL;

	@Value("${xls.index.valorAbatimento}")
	private int INDEX_VALOR_ABATIMENTO;

	@Value("${xls.index.multa.codigoMulta}")
	private int INDEX_MULTA_CODIGO_MULTA;

	@Value("${xls.index.multa.taxa}")
	private int INDEX_MULTA_TAXA;

	@Value("${xls.index.multa.valor}")
	private int INDEX_MULTA_VALOR;

	@Value("${xls.index.multa.data}")
	private int INDEX_MULTA_DATA;

	@Value("${xls.index.mora.codigoMora}")
	private int INDEX_MORA_CODIGO_MORA;

	@Value("${xls.index.mora.taxa}")
	private int INDEX_MORA_TAXA;

	@Value("${xls.index.mora.valor}")
	private int INDEX_MORA_VALOR;

	@Value("${xls.index.mora.data}")
	private int INDEX_MORA_DATA;

	@Value("${xls.index.cnpjCPFBeneficiario}")
	private int INDEX_CNPJ_CPF_BENEFICIARIO;

	@Value("${xls.index.numDiasAgenda}")
	private int INDEX_NUM_DIAS;

	@Override
	public HashMap<File, List<Row>> read() throws Exception {
		List<Row> rows = new ArrayList<Row>();
		HashMap<File, List<Row>> mapFileRows = new HashMap<File, List<Row>>();

		FilenameFilter filter = filterFile("XLS", "XLSX");
		File[] files = new File(DIR_SOURCE).listFiles(filter);
		for (File file : files) {
			rows = readRows(file);
			mapFileRows.put(file, rows);
			// FileUtils.moveFile(DIR_TARGET, file);
		}
		return mapFileRows;
	}
	

	@Override
	public Request buildRequest(Row row) throws Exception {
		return Request.builder().pagador(buildPagador(row)).dataEmissao(toDate(row, INDEX_DATA_EMISSAO))
				.seuNumero(toString(row, INDEX_SEU_NUMERO)).dataLimite(toString(row, INDEX_DATA_LIMITE))
				.dataVencimento(toDate(row, INDEX_DATA_VENCIMENTO)).mensagem(buildMensagem(row))
				.desconto1(buildDesconto1(row)).desconto2(buildDesconto2(row)).desconto3(buildDesconto3(row))
				.valorNominal(toDouble(row, INDEX_VALOR_NOMINAL)).valorAbatimento(toDouble(row, INDEX_VALOR_ABATIMENTO))
				.multa(buildMulta(row)).mora(buildMora(row))
				.cnpjCPFBeneficiario(toString(row, INDEX_CNPJ_CPF_BENEFICIARIO))
				.numDiasAgenda(toString(row, INDEX_NUM_DIAS)).build();
	}

	private List<Row> readRows(File file) throws Exception {
		List<Row> rows = new ArrayList<Row>();

		FileInputStream fileInputStream = new FileInputStream(file);
		Workbook workbook = new XSSFWorkbook(fileInputStream);
		Sheet sheet = workbook.getSheetAt(0);
		Iterator<Row> rowIterator = sheet.iterator();
		rowIterator.next();

		while (rowIterator.hasNext()) {
			rows.add(rowIterator.next());
		}

		workbook.close();
		fileInputStream.close();

		return rows;
	}

	private String toString(Row row, int index) throws Exception {
		if (row.getCell(index) != null) {
			row.getCell(index).setCellType(STRING);
			return row.getCell(index).getStringCellValue();
		}

		return null;
	}

	private Double toDouble(Row row, int index) throws Exception {
		if (row.getCell(index) != null) {
			row.getCell(index).setCellType(STRING);
			return parseDouble(row.getCell(index).getStringCellValue().replace(",", "."));
		}

		return null;
	}

	private String toDate(Row row, int index) throws Exception {
		if (row.getCell(index) != null) {
			row.getCell(index).setCellType(NUMERIC);
			SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");
			return formatter.format(row.getCell(index).getDateCellValue());
		}

		return null;
	}

	private Pagador buildPagador(Row row) throws Exception {
		return Pagador.builder().cnpjCpf(toString(row, INDEX_PAGADOR_CNPJ_CPF)).nome(toString(row, INDEX_PAGADOR_NOME))
				.email(toString(row, INDEX_PAGADOR_EMAIL)).telefone(toString(row, INDEX_PAGADOR_TELEFONE))
				.cep(toString(row, INDEX_PAGADOR_CEP)).numero(toString(row, INDEX_PAGADOR_NUMERO))
				.complemento(toString(row, INDEX_PAGADOR_COMPLEMENTO)).bairro(toString(row, INDEX_PAGADOR_BAIRRO))
				.cidade(toString(row, INDEX_PAGADOR_CIDADE)).uf(toString(row, INDEX_PAGADOR_UF))
				.endereco(toString(row, INDEX_PAGADOR_ENDERECO)).ddd(toString(row, INDEX_PAGADOR_DDD))
				.tipoPessoa(toString(row, INDEX_PAGADOR_TIPO_PESSOA)).build();
	}

	private Mensagem buildMensagem(Row row) throws Exception {
		return Mensagem.builder().linha1(toString(row, INDEX_MENSAGEM_LINHA_1)).build();
	}

	private Desconto buildDesconto1(Row row) throws Exception {
		return Desconto.builder().codigoDesconto(toString(row, INDEX_DESCONTO1_CODIGO_DESCONTO))
				.taxa(toDouble(row, INDEX_DESCONTO1_TAXA)).valor(toDouble(row, INDEX_DESCONTO1_VALOR))
				.data(toDate(row, INDEX_DESCONTO1_DATA)).build();
	}

	private Desconto buildDesconto2(Row row) throws Exception {
		return Desconto.builder().codigoDesconto(toString(row, INDEX_DESCONTO2_CODIGO_DESCONTO))
				.taxa(toDouble(row, INDEX_DESCONTO2_TAXA)).valor(toDouble(row, INDEX_DESCONTO2_VALOR))
				.data(toDate(row, INDEX_DESCONTO2_DATA)).build();
	}

	private Desconto buildDesconto3(Row row) throws Exception {
		return Desconto.builder().codigoDesconto(toString(row, INDEX_DESCONTO3_CODIGO_DESCONTO))
				.taxa(toDouble(row, INDEX_DESCONTO3_TAXA)).valor(toDouble(row, INDEX_DESCONTO3_VALOR))
				.data(toDate(row, INDEX_DESCONTO3_DATA)).build();
	}

	private Multa buildMulta(Row row) throws Exception {
		return Multa.builder().codigoMulta(toString(row, INDEX_MULTA_CODIGO_MULTA))
				.taxa(toDouble(row, INDEX_MULTA_TAXA)).valor(toDouble(row, INDEX_MULTA_VALOR))
				.data(toDate(row, INDEX_MULTA_DATA)).build();
	}

	private Mora buildMora(Row row) throws Exception {
		return Mora.builder().codigoMora(toString(row, INDEX_MORA_CODIGO_MORA)).taxa(toDouble(row, INDEX_MORA_TAXA))
				.valor(toDouble(row, INDEX_MORA_VALOR)).data(toDate(row, INDEX_MORA_DATA)).build();
	}
}
