package br.gov.ce.sefaz.util;

import org.springframework.stereotype.Component;

@Component
public class Producer{
    
    public LogarData produceLogarData(final Long dataMiliSeconds) {
    	LogarData logarData = new LogarData(dataMiliSeconds);
    	return logarData;
    }

}
