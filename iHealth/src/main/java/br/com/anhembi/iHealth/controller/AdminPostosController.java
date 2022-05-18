package br.com.anhembi.iHealth.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

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

import br.com.anhembi.iHealth.modelo.Posto;
import br.com.anhembi.iHealth.modelo.Regiao;
import br.com.anhembi.iHealth.modelo.Remedio;
import br.com.anhembi.iHealth.modelo.User;
import br.com.anhembi.iHealth.repository.PostoRepository;
import br.com.anhembi.iHealth.repository.RegiaoRepository;
import br.com.anhembi.iHealth.repository.RemedioRepository;
import br.com.anhembi.iHealth.repository.UserRepository;

@Controller
@RequestMapping("admin")
public class AdminPostosController {

	@Autowired
	RegiaoRepository regiaoRepository;
	
	@Autowired
	RemedioRepository remedioRepository;
	
	@Autowired
	PostoRepository postoRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "postos")
	public String listaDePostos(Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Posto> postos = postoRepository.findAll(Sort.by("id").ascending());
		model.addAttribute("postos", postos);
		
		if(postos.isEmpty() || postos == null) {
			return "admin/posto/postosVazios";
		}
		
		return "admin/posto/postos";
	}
	
	@GetMapping(value = "postoForm")
	public String PostoForm(Posto posto, Model model, Principal principal) {
		List<Regiao> regioes = regiaoRepository.findAll();
		model.addAttribute("regioes", regioes);
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		
		return "admin/posto/postoForm";
	}
	
	@PostMapping(value = "cadastrarPosto",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String cadastrarPosto(Posto posto,  @RequestParam("imagemFile") MultipartFile file, @RequestParam("regiaoFile") String regiao, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		try {
			Regiao r = regiaoRepository.findByNome(regiao);
			posto.setRegiao(r);
			posto.setUrlImagemPosto(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		postoRepository.save(posto);
		return "redirect:/admin/postoSuccess";
	}
	
	@GetMapping(value = "postoSuccess")
	public String pedidoSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/posto/postoSuccess";
	}
	
	
	@GetMapping(value = "deletarPosto/{id}")
	public String postoDeletar(@PathVariable("id") Long id) {
		Optional<Posto> posto = postoRepository.findById(id);

		if (posto.isPresent()) {
			postoRepository.deleteById(id);
			return "redirect:/admin/postos";
		} else {
			return "admin/postos";
		}
	}
	
	@GetMapping(value = "editarPosto/{id}")
	public String postoFormEdit(@PathVariable("id") Long id, Model model, Principal principal) {

		Posto posto = postoRepository.findById(id).get();
		model.addAttribute("posto", posto);
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Regiao> regioes = regiaoRepository.findAll();
		
		model.addAttribute("regioes", regioes);

		return "admin/posto/postoEditForm.html";
	}
	
	@PostMapping(value = "atualizarPosto", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String recadastrarPosto(Posto posto,@RequestParam("imagemFile") MultipartFile file, @RequestParam("regiaoFile") String regiao) {
		try {
			Regiao r = regiaoRepository.findByNome(regiao);
			posto.setRegiao(r);
			posto.setUrlImagemPosto(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		postoRepository.save(posto);
		return "redirect:/admin/postos";
	}

	
	
	@GetMapping(value = "listaDeRemedios/{id}")
	public ModelAndView listaDeRemedios(@PathVariable(name = "id") Long id, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Posto posto = postoRepository.findById(id).get();
		ModelAndView mv = new ModelAndView("admin/posto/listaDeRemediosPosto");
		mv.addObject("posto", posto);
		
		List<Remedio> remediosNaoAssociados  = remedioRepository.findAll();
		remediosNaoAssociados.removeAll(posto.getRemedios());
		mv.addObject("remedios", remediosNaoAssociados);
		
		List<Remedio> postoRemedios = posto.getRemedios();
		mv.addObject("postoRemedios", postoRemedios);
		return mv;
	}
	
	@PostMapping( value = "associarRemedioPosto")
	public String associarRemedios(@ModelAttribute Remedio remedio, @RequestParam Long idPosto) {
		
		Posto posto = postoRepository.findById(idPosto).get();
		remedio  = remedioRepository.findById(remedio.getId()).get();
		
		posto.getRemedios().add(remedio);
		postoRepository.save(posto);
		
		return "redirect:/admin/listaDeRemedios/" + idPosto;
	}
	
	@GetMapping(value = "imagemPosto/{id}")
	@ResponseBody
	public byte[] exibirImagemd(@PathVariable("id") Long id) {
		Posto posto = this.postoRepository.getById(id);
		return posto.getUrlImagemPosto();
	}
}
