package br.gov.ce.sefaz.util;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogarData {

	
	private static final Logger log = LoggerFactory.getLogger(LogarData.class.getName());
	private Long dataMiliSeconds;
	private String dataFormatada;

	public LogarData(Long dataMili) {
		this.dataMiliSeconds = dataMili;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");    
		String format = sdf.format(this.dataMiliSeconds);
		log.info("Inicio : {}",format);
		this.dataFormatada = format;
	}

	public String getDataFormatada() {
		return dataFormatada;
	}

	public void setDataFormatada(String dataFormatada) {
		this.dataFormatada = dataFormatada;
	}





	
}
