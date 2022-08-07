package com.rockgustavo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class HomeController {
	ModelAndView mv = new ModelAndView();

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@GetMapping("/")
	public ModelAndView index2() {
		mv.setViewName("home/index");
		return mv;
	}

	@GetMapping("/logar")
	public ModelAndView index() {
		mv.setViewName("home/index");
		return mv;
	}

	@GetMapping("/pessoafisica")
	public ModelAndView pessoafisica() {
		mv.setViewName("home/pf");
		return mv;
	}

	@GetMapping("/pessoajuridica")
	public ModelAndView pessoajuridica() {
		mv.setViewName("home/pj");
		return mv;
	}

	@GetMapping("/voltar")
	public ModelAndView voltar() {
		mv.setViewName("home/home");
		return mv;
	}
	
	@GetMapping("/sair")
	public ModelAndView sair() {
		mv.setViewName("home/index");
		return mv;
	}

	@PostMapping("/logar")
	public ModelAndView create(String login, String password) {
		System.out.println("LOGIN: " + login + " - SENHA: " + passwordEncoder.encode(password));
		boolean autSenha = new BCryptPasswordEncoder().matches("123", passwordEncoder.encode(password));
		if ("admin".equals(login) && autSenha) {
			mv.setViewName("home/home");
		} else {
			mv.setViewName("home/index");
		}

		return mv;
	}

	@PostMapping("/salvarpf")
	public ModelAndView salvarpf(String nome, Double rendaAnual, Double gastosComSaude) {
		System.out
				.println("NOME: " + nome + " - RENDA ANUAL: " + rendaAnual + " - GASTOS COM SAUDE: " + gastosComSaude);

		mv.setViewName("home/home");
		return mv;
	}

	@PostMapping("/salvarpj")
	public ModelAndView salvarpj(String nome, Double rendaAnual, Integer numFuncionarios) {
		System.out.println(
				"NOME: " + nome + " - RENDA ANUAL: " + rendaAnual + " - Nº de funcionários: " + numFuncionarios);

		mv.setViewName("home/home");
		return mv;
	}

}