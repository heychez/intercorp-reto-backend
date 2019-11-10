package com.reto.repositories;

import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.reto.models.Cliente;

@Repository
public class ClienteRepo {

	private ArrayList<Cliente> clientes = new ArrayList<Cliente>();

	public Cliente create(Cliente cliente) {
		this.clientes.add(cliente);
		return cliente;
	}

	public ArrayList<Cliente> find() {
		return this.clientes;
	}

}
