package br.com.anhembi.iHealth.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.anhembi.iHealth.modelo.Pedido;
import br.com.anhembi.iHealth.modelo.Remedio;
import br.com.anhembi.iHealth.modelo.Status;
import br.com.anhembi.iHealth.modelo.User;
import br.com.anhembi.iHealth.repository.PedidoRepository;
import br.com.anhembi.iHealth.repository.PostoRepository;
import br.com.anhembi.iHealth.repository.RegiaoRepository;
import br.com.anhembi.iHealth.repository.RemedioRepository;
import br.com.anhembi.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "admin")
public class AdminPedidoController {

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
	
	@PostMapping(value = "salvarPedido/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String salvarPedido(@RequestParam("status") Status status, @PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Pedido pedido = pedidoRepository.getById(id);
		pedido.setStatus(status);
		pedidoRepository.save(pedido);
		
		List<Pedido> pedidos = pedidoRepository.findAll(Sort.by("id").descending());
		String url = "todos";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		return "admin/pedido/listaPedidos";
	
	}
	
	@GetMapping(value = "pedidosAdmin")
	public String listaDePedidos(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAll(Sort.by("id").descending());
		String url = "todos";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		
		return "admin/pedido/listaPedidos";
	}
	
	@GetMapping(value = "pedidosAdmin/recusados")
	public String listaDePedidosRecusados(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByStatus(Status.RECUSADO,Sort.by("id").descending());
		String url = "recusados";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		
		if(pedidos.isEmpty() || pedidos == null) {
			return "admin/pedido/pedidosVazios";
		}
		
		return "admin/pedido/listaPedidos";
	}
	
	@GetMapping(value = "pedidosAdmin/aceitos")
	public String listaDePedidosAceitos(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByStatus(Status.ACEITO, Sort.by("id").descending());
		String url = "aceitos";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		

		if(pedidos.isEmpty() || pedidos == null) {
			return "admin/pedido/pedidosVazios";
		}
		
		return "admin/pedido/listaPedidos";
	}
	
	@GetMapping(value = "pedidosAdmin/entregues")
	public String listaDePedidosEntregues(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByStatus(Status.ENTREGUE, Sort.by("id").descending());
		String url = "entregues";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		

		if(pedidos.isEmpty() || pedidos == null) {
			return "admin/pedido/pedidosVazios";
		}
		
		return "admin/pedido/listaPedidos";
	}
	
	@GetMapping(value = "pedidosAdmin/analise")
	public String listaDePedidosAnalise(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByStatus(Status.ANALISE, Sort.by("id").descending());
		String url = "analise";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		

		if(pedidos.isEmpty() || pedidos == null) {
			return "admin/pedido/pedidosVazios";
		}
		
		return "admin/pedido/listaPedidos";
	}

	@GetMapping(value = "detalhePedido/{id}")
	public ModelAndView detalheRemedio(@PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Pedido pedido = pedidoRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("admin/pedido/situacaoPedido");
		mv.addObject("pedido", pedido);
		
		List<Remedio> remediosNaoAssociados  = remedioRepository.findAll();
		remediosNaoAssociados.removeAll(pedido.getRemedios());
		mv.addObject("remedios", remediosNaoAssociados);
		
		List<Remedio> pedidoRemedios = pedido.getRemedios();
		mv.addObject("pedidoRemedios", pedidoRemedios);
		return mv;
	}
	
	@GetMapping(value = "excluirPedido/{id}")
	public String deletarPedido(@PathVariable("id") Long id) {
		
		pedidoRepository.deleteById(id);
		return "redirect:/admin/pedidosAdmin";
	}
}



