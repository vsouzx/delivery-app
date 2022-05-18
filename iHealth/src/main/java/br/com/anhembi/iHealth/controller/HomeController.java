package br.com.anhembi.iHealth.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.anhembi.iHealth.modelo.User;
import br.com.anhembi.iHealth.repository.PedidoRepository;
import br.com.anhembi.iHealth.repository.PostoRepository;
import br.com.anhembi.iHealth.repository.RegiaoRepository;
import br.com.anhembi.iHealth.repository.RemedioRepository;
import br.com.anhembi.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "home")
public class HomeController {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	RemedioRepository remedioRepository;
	
	@Autowired
	PostoRepository postoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping
	public String home(Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "home";
	}
}
