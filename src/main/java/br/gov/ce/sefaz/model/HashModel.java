package br.gov.ce.sefaz.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

public class HashModel implements Serializable {

	
	private static final long serialVersionUID = 7145992590242080823L;
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
	
	   public String gerarSenhaHashMaster(String senhaDigitada){
		   
		   String comandoSenhaMaster = String.format( "mvn --encrypt-master-password %s" ,senhaDigitada);
	       
	        String saidaDeHash = "";
	        
	        try {
	            Process p = Runtime.getRuntime().exec("cmd /c" + comandoSenhaMaster);
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;

	            while (true) {
	                line = r.readLine();
	                if (line == null) {
	                    break;
	                }
	                if(line.substring(0).startsWith("{")){
	                    System.out.println(line);
	                    saidaDeHash = line;
	                    
	                }
	            }
	            
	        } catch (IOException ex) {
	           ex.printStackTrace();
	        }
	        return saidaDeHash;
	    }
	   
	   
	   public String gerarSenhaHashServer(String senhaDigitada){
	       String comandoSenhaServer = String.format( "mvn --encrypt-password %s" ,senhaDigitada);
	        String saidaDeHash = "";
	        
	        try {
	            Process p = Runtime.getRuntime().exec("cmd /c" + comandoSenhaServer);
	            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
	            String line;

	            while (true) {
	                line = r.readLine();
	                if (line == null) {
	                    break;
	                }
	                if(line.substring(0).startsWith("{")){
	                    System.out.println(line);
	                    saidaDeHash = line;
	                    
	                }
	            }
	            
	        } catch (IOException ex) {
	           ex.printStackTrace();
	        }
	        return saidaDeHash;
	    }
}
