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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.anhembi.iHealth.modelo.Remedio;
import br.com.anhembi.iHealth.modelo.User;
import br.com.anhembi.iHealth.repository.RemedioRepository;
import br.com.anhembi.iHealth.repository.UserRepository;

@Controller
@RequestMapping(value = "admin")
public class AdminMedicamentoController {

	@Autowired
	RemedioRepository remedioRepository;

	@Autowired
	UserRepository userRepository;
	
	@GetMapping(value = "medicamentos")
	public String medicamentos(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		List<Remedio> remedios = remedioRepository.findAll(Sort.by("id").descending());
		model.addAttribute("remedios", remedios);

		if (remedios == null || remedios.isEmpty()) {
			return "admin/medicamento/medicamentosvazios";
		} else {
			model.addAttribute("remedios", remedios);
			return "admin/medicamento/medicamentos";
		}

	}

	@GetMapping(value = "cadastroMedicamentoForm")
	public String medicamentoForm(Remedio remedio, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/medicamento/medicamentoForm";
	}

	@PostMapping(value = "cadastrarMedicamento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String cadastrarMedicamento(Remedio remedio, @RequestParam("imagemFile") MultipartFile file, Model model, Principal principal) {
		
		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		try {
				remedio.setImagem(file.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
			remedioRepository.save(remedio);
			return "redirect:/admin/medicamentoSuccess";
	}
	
	@GetMapping(value = "medicamentoSuccess")
	public String pedidoSuccess(Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		return "admin/medicamento/medicamentoSuccess";
	}
	
	@GetMapping(value = "imagem/{id}")
	@ResponseBody
	public byte[] exibirImagem(@PathVariable("id") Long id) {
		Remedio remedio = this.remedioRepository.getById(id);
		return remedio.getImagem();
	}
	
	@GetMapping(value = "deletarMedicamento/{id}")
	public String medicamentoDeletar(@PathVariable("id") Long id) {
		Optional<Remedio> remedio = remedioRepository.findById(id);

		if (remedio.isPresent()) {
			remedioRepository.deleteById(id);
			return "redirect:/admin/medicamentos";
		} else {
			return "admin/medicamentos";
		}
	}

	@GetMapping(value = "editarMedicamento/{id}")
	public String medicamentoFormEdit(@PathVariable("id") Long id, Model model, Principal principal) {

		String username = principal.getName();
		model.addAttribute("username", username);
		
		User user = userRepository.findByCpf(username);
		model.addAttribute("user", user);
		
		Remedio medicamento = remedioRepository.findById(id).get();

		model.addAttribute("medicamento", medicamento);

		return "admin/medicamento/medicamentoFormEdit.html";
	}

	@PostMapping(value = "atualizarMedicamento", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String recadastrar(Remedio remedio, @RequestParam("imagemFile") MultipartFile file) {
		try {
			remedio.setImagem(file.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		remedioRepository.save(remedio);
		return "redirect:/admin/medicamentos";

	}
}
