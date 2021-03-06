package br.gov.ce.sefaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.support.SpringBootServletInitializer;

import br.gov.ce.sefaz.util.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})
public class SpringApplicationMain  extends SpringBootServletInitializer {//extender para usar wildfly


	public static void main(String[] args) {
		SpringApplication.run(SpringApplicationMain.class, args);
	}

	@Override // sobreescrever para usar wildfly
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return super.configure(builder);
	}
	

}
