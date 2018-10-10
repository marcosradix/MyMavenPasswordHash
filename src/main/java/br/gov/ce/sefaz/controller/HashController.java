package br.gov.ce.sefaz.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.sonatype.plexus.components.cipher.DefaultPlexusCipher;
import org.sonatype.plexus.components.cipher.PlexusCipherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.gov.ce.sefaz.model.HashModel;
import br.gov.ce.sefaz.util.Producer;

@Controller
@RequestMapping("/mymavenpasswordhash")
public class HashController {
	
	
	private Logger logger = Logger.getLogger(HashController.class);
	@Autowired
	private Producer producer;
	private long now = 0;
	
	@GetMapping
	public ModelAndView goHome(HashModel hashModel){
		now = System.currentTimeMillis();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("dataInicioView", producer.produceLogarData(now).getDataFormatada());
		modelAndView.setViewName("index");
		return modelAndView;
	}
	
	@RequestMapping("/mymavenpasswordhash")
	  public void handleRequest() {
	      throw new RuntimeException("Erro inesperado.");
	  }
	
	@PostMapping(value="/mavenhash")
	public ModelAndView gerarHash(@Valid HashModel hashModel,BindingResult result , ModelAndView model ){
		HashModel hashsGerados = null;
		
		try {
			hashsGerados = gerarSenhaHash(hashModel.getSenhaPrincipal());
			logger.info("valor nulo : " + hashsGerados.getSenhaPrincipal());
			if(result.hasErrors()){
				result.rejectValue("senhaPrincipal", "error", "Como você vai gerar o hash se não informou nada.");
			}else{
				model.addObject("hashModel", hashsGerados).addObject("success", "Hash construido com sucesso");
			}
			
			
		} catch (PlexusCipherException e) {
			e.printStackTrace();
		}
		model.setViewName("index");
		return model;
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



