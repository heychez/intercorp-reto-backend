package com.reto.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reto.models.Cliente;
import com.reto.models.KpiClientesResponse;
import com.reto.services.ClienteService;

@RestController
@RequestMapping("/")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;

	@PostMapping("/creacliente")
	public Cliente creaCliente(@RequestBody Cliente cliente) {
		return this.clienteService.crear(cliente);
	}

	@GetMapping("/kpideclientes")
	public KpiClientesResponse kpiDeClientes() {
		KpiClientesResponse response = new KpiClientesResponse(clienteService.promedioEdades(),
				clienteService.desviacionEstandarEdades());

		return response;
	}

	@GetMapping("/listclientes")
	public List<Cliente> listClientes() {
		return this.clienteService.listarConFecFallecimiento();
	}

}
