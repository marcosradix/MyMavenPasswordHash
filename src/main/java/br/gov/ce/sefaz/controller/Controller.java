package br.gov.ce.sefaz.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.gov.ce.sefaz.model.HashModel;

@RestController
public class Controller {
	
	@GetMapping("/my-maven-password-hash")
	public String seuNome(){
		
		return "Bem-Vindo";
	}
	
	
	@PostMapping("/my-maven-password-hash")
	public HashModel init(@RequestBody HashModel hashModel ){
		String senhaMaster = hashModel.gerarSenhaHashMaster(hashModel.getSenhaPrincipal());
		String senhaServer = hashModel.gerarSenhaHashServer(hashModel.getSenhaPrincipal());
		hashModel.setSenhaPrincipal("");
		hashModel.setSenhaMaster(senhaMaster);
		hashModel.setSenhaServer(senhaServer);
		return hashModel;
	}
}
