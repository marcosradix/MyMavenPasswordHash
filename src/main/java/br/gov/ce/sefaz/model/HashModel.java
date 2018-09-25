package br.gov.ce.sefaz.model;

import org.hibernate.validator.constraints.NotEmpty;

public class HashModel {

	@NotEmpty(message="O campo é obrigatório")
	private String senhaPrincipal;
	private String senhaMaster;
	private String senhaServer;
	
	public String getSenhaPrincipal() {
		return senhaPrincipal;
	}
	public void setSenhaPrincipal(String senhaPrincipal) {
		this.senhaPrincipal = senhaPrincipal;
	}
	public String getSenhaMaster() {
		return senhaMaster;
	}
	public void setSenhaMaster(String senhaMaster) {
		this.senhaMaster = senhaMaster;
	}
	public String getSenhaServer() {
		return senhaServer;
	}
	public void setSenhaServer(String senhaServer) {
		this.senhaServer = senhaServer;
	}
	@Override
	public String toString() {
		return "HashModel [senhaPrincipal=" + senhaPrincipal + ", senhaMaster=" + senhaMaster + ", senhaServer="
				+ senhaServer + "]";
	}
	
}
