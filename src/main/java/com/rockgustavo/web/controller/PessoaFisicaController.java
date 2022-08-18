package com.rockgustavo.web.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.rockgustavo.model.entities.Fisica;
import com.rockgustavo.model.service.PessoaFisicaService;

@RestController
@RequestMapping(value = "/pf")
public class PessoaFisicaController {

	@Autowired
	private PessoaFisicaService service;
	
	@GetMapping("/cad")
	public ModelAndView pessoafisica(Fisica fisica) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("pessoaFisica/cadastro");
		return mv;
	}
	
	@PostMapping("/salvar")
	public ModelAndView salvar(Fisica fisica, RedirectAttributes attr) {
		if(service.insert(fisica) != null) {
			attr.addFlashAttribute("success", "Cadastro efetuado com sucesso!");
		}else {
			attr.addFlashAttribute("fail", "Ocorreu um erro! cadastro não efetuado!");
		}
		ModelAndView mv = new ModelAndView("redirect:/pf/cad");
		return mv;
	}
	
	@GetMapping("/listar")
	public ModelAndView pessoafisicaListar() {
		ModelAndView mv = new ModelAndView();
		List<Fisica> list = service.findAll();
		mv.addObject("listaPf", list);
		mv.setViewName("pessoaFisica/lista");
		return mv;
	}
	
	@GetMapping(value = "/editar/{id}")
	public ModelAndView preEditar(@PathVariable Integer id, RedirectAttributes attr) {
		attr.addFlashAttribute("info", "Modo de edição!");
		Fisica pessoa = service.findById(id);
		ModelAndView mv = new ModelAndView("redirect:/pf/cad");
		mv.addObject(pessoa);
		return mv;
	}
	
	@PostMapping(value = "/editar")
	public ModelAndView editar(Fisica fisica, RedirectAttributes attr) {
		if(service.update(fisica) != null) {
			attr.addFlashAttribute("success", "Cadastro atualizado com sucesso!");
		}else {
			attr.addFlashAttribute("fail", "Ocorreu um erro! cadastro não atualizado!");
		}
		return new ModelAndView("redirect:/pf/listar");
	}
	
	@GetMapping(value = "/delete/{id}")
	public ModelAndView deletar(@PathVariable Integer id, RedirectAttributes attr) {
		service.delete(id);
		attr.addFlashAttribute("success", "Exclusão realizada com sucesso!");
		return new ModelAndView("redirect:/pf/listar");
	}
	
	@ModelAttribute("pessoasF")
	public List<Fisica> listarPf() {
		List<Fisica> list = service.findAll();
		return list;
	}
	
	
	

	@GetMapping
	public ResponseEntity<List<Fisica>> findAll() {
		List<Fisica> list = service.findAll();
		return ResponseEntity.ok().body(list);

	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Fisica> findById(@PathVariable Integer id) {
		Fisica pessoa = service.findById(id);
		return ResponseEntity.ok().body(pessoa);

	}
	
	@GetMapping(value = "/impostos")
	public ResponseEntity<List<String>> findAllTax() {
		List<String> list = service.findAll().stream().map(pf -> pf.toString()).collect(Collectors.toList());
		return ResponseEntity.ok().body(list);

	}
	
	@GetMapping(value = "/imposto/{id}")
	public ResponseEntity<List<String>> findTax(@PathVariable Integer id) {
		List<String> pessoa = new ArrayList<>();
		pessoa.add(service.findById(id).toString());
		return ResponseEntity.ok().body(pessoa);

	}
	
	@PostMapping
	public ResponseEntity<Fisica> insert(@RequestBody Fisica obj) {
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).body(obj);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Fisica> update(@PathVariable Integer id, @RequestBody Fisica obj) {
		obj = service.update(id, obj);
		return ResponseEntity.ok().body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Fisica> delete(@PathVariable Integer id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
