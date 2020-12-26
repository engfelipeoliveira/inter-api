package br.com.inter.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;

import br.com.inter.dto.Request;

public interface XlsService {
	
	HashMap<File, List<Row>> read() throws Exception;
	Request buildRequest(Row row) throws Exception;
	
}
