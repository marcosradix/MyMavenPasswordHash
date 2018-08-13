package br.gov.ce.sefaz.controller;

import org.sonatype.plexus.components.cipher.DefaultPlexusCipher;
import org.sonatype.plexus.components.cipher.PlexusCipherException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.sefaz.model.HashModel;

@RestController
@RequestMapping
public class Controller {
	
	@GetMapping(path="/my-maven-password-hash")
	public String goHome(){
		return "index";
}
	
	
	@PostMapping("/my-maven-password-hash")
	public HashModel init(@RequestBody HashModel hashModel ){
		HashModel hashsGerados = null;
		try {
			hashsGerados = gerarSenhaHash(hashModel.getSenhaPrincipal());
		} catch (PlexusCipherException e) {
			e.printStackTrace();
		}
		return hashsGerados;
	}
	
	public HashModel gerarSenhaHash(String senhaPrincipal) throws PlexusCipherException{
		DefaultPlexusCipher cipher = new DefaultPlexusCipher();
		HashModel hashModel = new HashModel();
		String masterPasswd = cipher.encryptAndDecorate( senhaPrincipal, "settings.security" );
		String serverPasswd =  cipher.encryptAndDecorate( senhaPrincipal, masterPasswd );
		
		hashModel.setSenhaMaster(masterPasswd);
		hashModel.setSenhaServer(serverPasswd);
		return hashModel;
	}
}



