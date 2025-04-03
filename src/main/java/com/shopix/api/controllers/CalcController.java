package com.shopix.api.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopix.api.dtos.CalcDTO;

@RestController
@RequestMapping("/api/calc")
public class CalcController {

	@PostMapping
	public String store(@RequestBody CalcDTO calc)
	{
		float resultado;
		switch (calc.op()) {
		case "soma":
			resultado = calc.arg1() + calc.arg2();
			break;
		case "sub":
			resultado = calc.arg1() - calc.arg2();
			break;
		case "mul":
			resultado = calc.arg1() * calc.arg2();
			break;
		case "div":
			resultado = calc.arg1() / calc.arg2();
			break;
		default:
			return "Paramêtro inválido";
		}
		return "Resultado " + resultado;
	}
}
