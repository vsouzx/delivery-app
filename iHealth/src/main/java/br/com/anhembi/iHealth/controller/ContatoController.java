package br.com.anhembi.iHealth.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.Principal;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import br.com.anhembi.iHealth.modelo.User;
import br.com.anhembi.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "contato")
public class ContatoController {
	
	@Autowired
	private JavaMailSender mailSender;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping
	public String emailForm(Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/contato/contatoForm";
	}
	
	@PostMapping(value = "enviarEmail")
	public String submitContact(HttpServletRequest request, 
			@RequestParam("anexo") MultipartFile multipartFile, Principal principal, Model model) throws MessagingException, UnsupportedEncodingException {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		String nome = request.getParameter("nomeUser");
		String email = request.getParameter("emailUser");
		String subject = request.getParameter("subjectUser");
		String conteudo = request.getParameter("userMsg");
		
//		SimpleMailMessage mensagem = new SimpleMailMessage();
		MimeMessage mensagem = mailSender.createMimeMessage();
		
		MimeMessageHelper helper = new MimeMessageHelper(mensagem, true);
		
		String mailSubject = nome + " enviou uma mensagem";
		String mailContent = "<p><b>Nome: </b>" + nome + "</p>";
		mailContent += "<p><b>E-mail: </b>" + email + "</p>";
		mailContent += "<p><b>Assunto: </b>" + subject + "</p>";
		mailContent += "<p><b>Mensagem: </b>" + conteudo + "</p>";
		
		helper.setFrom("vtsoliveira2001@gmail.com", "iHealth Contact");
		helper.setTo("vtsoliveira2001@gmail.com");
		helper.setSubject(mailSubject);
		helper.setText(mailContent, true);
		
		if(!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
			
			InputStreamSource source = new InputStreamSource() {

				@Override
				public InputStream getInputStream() throws IOException {
					// TODO Auto-generated method stub
					return multipartFile.getInputStream();
				}
				
			};
			helper.addAttachment(fileName, source);
		}
		mailSender.send(mensagem);
		
		return "user/contato/mensagem";
	}
}
