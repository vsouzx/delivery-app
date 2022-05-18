package br.com.anhembi.iHealth.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.anhembi.iHealth.modelo.Regiao;
import br.com.anhembi.iHealth.modelo.Role;
import br.com.anhembi.iHealth.modelo.User;
import br.com.anhembi.iHealth.repository.PedidoRepository;
import br.com.anhembi.iHealth.repository.RegiaoRepository;
import br.com.anhembi.iHealth.repository.RoleRepository;
import br.com.anhembi.iHealth.repository.UserRepository;

@Controller
@RequestMapping("perfil")
public class PerfilController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	@GetMapping("detalhes")
	public String usersDetalhes(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Regiao> regioes = regiaoRepository.findAll();
		model.addAttribute("regioes", regioes);
		
		return "user/detalhes/detalhes";
	}
	
	@GetMapping("editar")
	public String userEditar(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Regiao> regioes = regiaoRepository.findAll();
		model.addAttribute("regioes", regioes);
		
		return "user/detalhes/editar";
	}
	
	@GetMapping(value = "editarSenha")
	public String senhaEditar(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Regiao> regioes = regiaoRepository.findAll();
		model.addAttribute("regioes", regioes);
		
		return "user/detalhes/editarSenha";
	}
	
	@PostMapping(value = "atualizar",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String atualizar(User user, Model model, @RequestParam("regiaoField") String regiao,  Principal principal) {
	
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User userLogado = userRepository.findByCpf(username);
		
		Collection<Role> roles = userLogado.getRoles();
		user.setRoles(roles);
		
		user.setPassword(userLogado.getPassword());
			
		Regiao r = regiaoRepository.findByNome(regiao);
		user.setRegiao(r);
		
		user.setFoto(userLogado.getFoto());
			
		userRepository.save(user);
		return "user/detalhes/editarSuccess.html";
	}
	
	@PostMapping("atualizarSenha")
	public String atualizarSenha(User user, Model model, @RequestParam("senhaField") String senha, Principal principal) {
	
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User userLogado = userRepository.findByCpf(username);
		
		String senhaNaoCriptografada = senha;
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String password = senhaNaoCriptografada;
		String encodedPassword = encoder.encode(password);
		userLogado.setPassword(encodedPassword);
			
		userRepository.save(userLogado);
		return "user/detalhes/editarSuccess.html";
	}
	
	@GetMapping(value = "excluir/{id}")
	public String deletarPedido(@PathVariable("id") Long id) {
		
		User user = userRepository.findById(id).get();
		
		user.setRoles(null);

		userRepository.deleteById(id);
		return "redirect:/admin/usuarios";
	}
	
	@GetMapping(value = "imagem/{id}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("id") Long id) {
		User user = this.userRepository.getById(id);
		return user.getFoto();
	}
	
	@GetMapping(value = "editarFoto")
	public String editarFoto(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/detalhes/editarFoto";
	}
	
	@PostMapping("atualizarFoto")
	public String atualizarFoto(User user, Model model, @RequestParam("fotoFile") MultipartFile foto, Principal principal) throws IOException {
	
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User userLogado = userRepository.findByCpf(username);
		
		userLogado.setFoto(foto.getBytes());
			
		userRepository.save(userLogado);
		return "user/detalhes/editarSuccess.html";
	}
}
