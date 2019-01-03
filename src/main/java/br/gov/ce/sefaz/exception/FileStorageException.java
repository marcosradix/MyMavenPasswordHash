package br.gov.ce.sefaz.exception;

public class FileStorageException extends  RuntimeException {
	
	 /**
	 * 
	 */
	private static final long serialVersionUID = 8481456336622137576L;

	public FileStorageException(String message) {
	        super(message);
	    }

	    public FileStorageException(String message, Throwable cause) {
	        super(message, cause);
	    }

}
