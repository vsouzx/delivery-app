package br.com.anhembi.iHealth.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.anhembi.iHealth.dto.RequisicaoNovoComentario;
import br.com.anhembi.iHealth.modelo.Comentario;
import br.com.anhembi.iHealth.modelo.User;
import br.com.anhembi.iHealth.repository.ComentarioRepository;
import br.com.anhembi.iHealth.repository.UserRepository;

@Controller
@RequestMapping("comentario")
public class ComentarioController {

	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("homeComentarios")
	public String homeComentarios(RequisicaoNovoComentario requisicao, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Comentario> comentarios = comentarioRepository.findAll(Sort.by("id").descending());
		model.addAttribute("comentarios", comentarios);
		
		String url = "comentarios";
		 model.addAttribute("url", url);
		
		if(comentarios.isEmpty() || comentarios.size() == 0) {
			
			return "user/comentario/homeVaziaComentarios";
		}
		return "user/comentario/homeComentarios";
	}
	
	@GetMapping("listarTodos")
	public String listarTodosComentarios(RequisicaoNovoComentario requisicao, Model model, Principal principal) {
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Comentario> comentarios = comentarioRepository.findAll(Sort.by("id").descending());
		model.addAttribute("comentarios", comentarios);
		
		String url = "comentarios";
		 model.addAttribute("url", url);
		if(comentarios.isEmpty() || comentarios.size() == 0) {
			
			return "user/comentario/homeVaziaComentarios";
		}
		return "user/comentario/homeComentarios";
	}
	
	@GetMapping("listarPorUsuario")
	public String listarPorUsuario(RequisicaoNovoComentario requisicao, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		
		List<Comentario> comentarios = comentarioRepository.findAllByUser(user,Sort.by("id").descending());
		model.addAttribute("comentarios", comentarios);
		
		String url = "comentariosUsuario";
		 model.addAttribute("url", url);
		if(comentarios.isEmpty() || comentarios.size() == 0) {
			
			return "user/comentario/homeVaziaComentariosUsuario";
		}
		return "user/comentario/homeComentarios";
	}
	
	@GetMapping("excluir/{id}")
	public String excluirComentario(Comentario comentario, BindingResult result) {
		
		comentarioRepository.deleteById(comentario.getId());
		
		return "redirect:/comentario/homeComentarios";
	}
	
	@GetMapping("formComentario")
	public String formComentario(RequisicaoNovoComentario requisicao, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/comentario/comentarioForm";
	}
	
	@PostMapping("novo")
	public String novoComentario(@Valid RequisicaoNovoComentario requisicao, BindingResult result, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user); 
		
		if(result.hasErrors()){
			return "user/comentario/comentarioForm";
		}
		
		User usuario = userRepository.getUserByCpf(username);
		Comentario comentario = requisicao.toComentario();
		comentario.setUser(usuario);
		comentarioRepository.save(comentario);
		
		model.addAttribute("usuario", usuario);
		return "redirect:/comentario/comentarioSuccess";
	}
	
	@GetMapping(value = "comentarioSuccess")
	public String pedidoSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/comentario/comentarioSuccess";
	}
	
	
}