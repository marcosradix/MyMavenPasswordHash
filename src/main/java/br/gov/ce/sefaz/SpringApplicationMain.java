package br.gov.ce.sefaz;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class SpringApplicationMain  extends SpringBootServletInitializer  {//extender para usar wildfly

	private static final Logger log = LoggerFactory.getLogger(SpringApplicationMain.class.getName());


	public static void main(String[] args) {
		long now = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm ss");    
		String format = sdf.format(now);
		log.info("Inicio : {}",format);
		SpringApplication.run(SpringApplicationMain.class, args);
	}

	@Override // sobreescrever para usar wildfly
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}
}
