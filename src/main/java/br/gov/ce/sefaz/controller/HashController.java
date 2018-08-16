package br.gov.ce.sefaz.controller;

import org.sonatype.plexus.components.cipher.DefaultPlexusCipher;
import org.sonatype.plexus.components.cipher.PlexusCipherException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.gov.ce.sefaz.model.HashModel;

@Controller
@RequestMapping("/mymavenpasswordhash")
public class HashController {
	
	@GetMapping
	public String goHome(HashModel hashModel){
		return "index";
	}
	
	@PostMapping(value="/mavenhash")
	public String gerarHash(@ModelAttribute HashModel hashModel, ModelMap model ){
		HashModel hashsGerados = null;
		try {
			hashsGerados = gerarSenhaHash(hashModel.getSenhaPrincipal());
			
			model.addAttribute("hashModel", hashsGerados).addAttribute("success", "Hash construido com sucesso");
		} catch (PlexusCipherException e) {
			e.printStackTrace();
		}
		hashsGerados = new HashModel();
		hashModel = new HashModel();
		return "index";
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



