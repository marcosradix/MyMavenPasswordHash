package br.gov.ce.sefaz.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.sonatype.plexus.components.cipher.DefaultPlexusCipher;
import org.sonatype.plexus.components.cipher.PlexusCipherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.gov.ce.sefaz.model.HashModel;
import br.gov.ce.sefaz.model.UploadFileResponse;
import br.gov.ce.sefaz.util.FileStorageService;
import br.gov.ce.sefaz.util.Producer;

@Controller
@RequestMapping("/mymavenpasswordhash")
public class HashController {
	
	
	private Logger logger = Logger.getLogger(HashController.class);
	@Autowired
	private Producer producer;
	private long now = 0;
	
	@Autowired
	private FileStorageService fileStorageService;
	
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
				logger.info("Hash gerado com sucesso!");
				model.addObject("hashModel", hashsGerados).addObject("success", "Hash construido com sucesso");
			}
			
			
		} catch (PlexusCipherException e) {
			e.printStackTrace();
		}
		model.setViewName("index");
		return model;
	}
	
	@GetMapping("/downloadFile/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        // Load file as Resource
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        // Try to determine file's content type
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    
    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        String fileName = fileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();

        return new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
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



