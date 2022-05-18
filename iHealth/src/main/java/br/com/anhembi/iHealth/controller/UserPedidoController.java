package br.com.anhembi.iHealth.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
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
@RequestMapping(value = "user")
public class UserPedidoController {

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
	
	@PostMapping(value = "gerarPedido")
	public String cadastrarPedido(Pedido pedido,Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		pedido.setUser(user);
		pedidoRepository.save(pedido);
		return "user/pedido/gerarPedido";
	
	}
	
	@GetMapping(value = "pedido/{id}")
	public ModelAndView listaDeRemedios(@PathVariable(name = "id") Long id, Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Pedido pedido = pedidoRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("user/pedido/preencherPedido");
		mv.addObject("pedido", pedido);
		
		List<Remedio> remediosNaoAssociados  = remedioRepository.findAll();
		remediosNaoAssociados.removeAll(pedido.getRemedios());
		mv.addObject("remedios", remediosNaoAssociados);
		
		List<Remedio> pedidoRemedios = pedido.getRemedios();
		mv.addObject("pedidoRemedios", pedidoRemedios);
		return mv;
	}
	
	@PostMapping("/associarRemedioPedido")
	public String associarRemedio(@ModelAttribute Remedio remedio, @RequestParam Long idPedido, Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Pedido pedido = pedidoRepository.findById(idPedido).get();
		remedio  = remedioRepository.findById(remedio.getId()).get();
		
		pedido.getRemedios().add(remedio);
		pedidoRepository.save(pedido);
		
		return "redirect:/user/pedido/" + idPedido;
	}
	
	@PostMapping(value = "salvarPedido/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String salvarPedido(@RequestParam("anexoReceita") MultipartFile file, @PathVariable(name = "id") Long id, Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Pedido pedido = pedidoRepository.getById(id);
		try {
			pedido.setReceitaMedica(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		pedidoRepository.save(pedido);
		return "redirect:/user/pedidoSuccess";
	
	}
	
	@GetMapping(value = "pedidoSuccess")
	public String pedidoSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "user/pedido/pedidoSuccess";
	}
	
	@GetMapping(value = "pedidos")
	public String listaDePedidos(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByUser(user, Sort.by("id").descending());
		
		String url = "todos";
		model.addAttribute("url", url);
		
		model.addAttribute("pedidos", pedidos);
		
		if(pedidos == null || pedidos.size() == 0) {
			return "user/pedido/pedidosVazios";
		} else {
			return "user/pedido/listaPedidos";
		}
	}
	
	@GetMapping(value = "pedidos/recusados")
	public String listaDePedidosRecusados(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByUserAndStatus(user, Status.RECUSADO, Sort.by("id").descending());
		String url = "recusados";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		
		if(pedidos == null || pedidos.size() == 0) {
			return "user/pedido/pedidosVazios";
		} else {
			return "user/pedido/listaPedidos";
		}
	}
	
	@GetMapping(value = "pedidos/aceitos")
	public String listaDePedidosAceitos(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByUserAndStatus(user, Status.ACEITO, Sort.by("id").descending());
		String url = "aceitos";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		
		if(pedidos == null || pedidos.size() == 0) {
			return "user/pedido/pedidosVazios";
		} else {
			return "user/pedido/listaPedidos";
		}
	}
	
	@GetMapping(value = "pedidos/entregues")
	public String listaDePedidosEntregues(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByUserAndStatus(user, Status.ENTREGUE, Sort.by("id").descending());
		String url = "entregues";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		
		if(pedidos == null || pedidos.size() == 0) {
			return "user/pedido/pedidosVazios";
		} else {
			return "user/pedido/listaPedidos";
		}
	}
	
	@GetMapping(value = "pedidos/analise")
	public String listaDePedidosAnalise(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Pedido> pedidos = pedidoRepository.findAllByUserAndStatus(user, Status.ANALISE, Sort.by("id").descending());
		String url = "analise";
		model.addAttribute("url", url);
		model.addAttribute("pedidos", pedidos);
		
		if(pedidos == null || pedidos.size() == 0) {
			return "user/pedido/pedidosVazios";
		} else {
			return "user/pedido/listaPedidos";
		}
	}
	
	@GetMapping(value = "excluirPedido/{id}")
	public String deletarPedido(@PathVariable("id") Long id) {
		
		pedidoRepository.deleteById(id);
		return "redirect:/user/pedidos";
	}
	

	@GetMapping(value = "cancelarPedido/{id}")
	public String cancelarPedido(@PathVariable("id") Long id) {
		
		pedidoRepository.deleteById(id);
		return "redirect:/home";
	}
	
	@GetMapping(value = "detalhePedido/{id}")
	public ModelAndView detalheRemedio(@PathVariable(name = "id") Long id, Principal principal, Model model) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Pedido pedido = pedidoRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("user/pedido/pedidoDetalhes");
		mv.addObject("pedido", pedido);
		
		List<Remedio> remediosNaoAssociados  = remedioRepository.findAll();
		remediosNaoAssociados.removeAll(pedido.getRemedios());
		mv.addObject("remedios", remediosNaoAssociados);
		
		List<Remedio> pedidoRemedios = pedido.getRemedios();
		mv.addObject("pedidoRemedios", pedidoRemedios);
		return mv;
	}
	
	@GetMapping(value = "imagem/{id}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("id") Long id) {
		Pedido pedido = this.pedidoRepository.getById(id);
		return pedido.getReceitaMedica();
	}
	

	@GetMapping(value = "/remedio/imagem/{id}")
	@ResponseBody
	public byte[] exibirImagemRemedio(@PathVariable("id") Long id) {
		Remedio remedio = this.remedioRepository.getById(id);
		return remedio.getImagem();
	}
	
}


