package com.reto.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reto.models.Cliente;
import com.reto.repositories.ClienteRepo;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepo clienteRepo;

	public Cliente crear(Cliente cliente) {
		this.clienteRepo.create(cliente);
		return cliente;
	}

	public ArrayList<Cliente> listar() {
		return this.clienteRepo.find();
	}

	public double promedioEdades() {
		ArrayList<Cliente> clientes = this.clienteRepo.find();
		if (clientes.size() == 0)
			return 0;

		double totalEdades = 0;

		for (int i = 0; i < clientes.size(); i++) {
			totalEdades += clientes.get(i).getEdad();
		}

		return totalEdades / (double) clientes.size();
	}

	public double desviacionEstandarEdades() {
		ArrayList<Cliente> clientes = this.clienteRepo.find();
		if (clientes.size() == 0)
			return 0;

		double promedio = this.promedioEdades();
		double total = 0;

		for (int i = 0; i < clientes.size(); i++) {
			total += Math.pow(clientes.get(i).getEdad() - promedio, 2);
		}

		return Math.sqrt(total / (double) clientes.size());
	}

	public ArrayList<Cliente> listarConFecFallecimiento() {
		ArrayList<Cliente> clientes = this.clienteRepo.find();
		if (clientes.size() == 0)
			return clientes;

		// ordenar clientes por edad descendiente
		clientes.sort(new Comparator<Cliente>() {
			public int compare(Cliente c1, Cliente c2) {
				if (c1.getEdad() > c2.getEdad())
					return -1;
				if (c1.getEdad() > c2.getEdad())
					return 1;
				return 0;
			}
		});

		// matriz de la tabla de esperanza de vida
		int maxEdad = clientes.get(0).getEdad();
		double[][] tablaDeVida = new double[maxEdad][4];
		for (int i = 0; i < maxEdad; i++) {
			for (int j = 0; i < 4; j++) {
				tablaDeVida[i][j] = 0;
			}
		}

		for (int i = 0; i < maxEdad; i++) {
			for (int j = 0; j < clientes.size(); j++) {
				Cliente cliente = clientes.get(j);
				if (i + 1 <= cliente.getEdad())
					tablaDeVida[i][0]++;
			}
		}

		// calculo de lx
		for (int i = 0; i < maxEdad; i++) {
			if (i == maxEdad - 1)
				tablaDeVida[i][1] = (tablaDeVida[i][0] + 0) / 2;
			else
				tablaDeVida[i][1] = (tablaDeVida[i][0] + tablaDeVida[i + 1][0]) / 2;
		}

		// calculo de las edades mas por vivir (tx, ex)
		for (int i = 0; i < maxEdad; i++) {
			for (int j = i; j < maxEdad; j++) {
				tablaDeVida[i][2] += tablaDeVida[j][1];
			}

			tablaDeVida[i][3] = tablaDeVida[i][2] / tablaDeVida[i][1];
		}

		// calculo de las fechas de fallecimiento
		for (int i = 0; i < clientes.size(); i++) {
			Cliente cliente = clientes.get(i);

			long edadTotal = (long) (cliente.getEdad() + tablaDeVida[cliente.getEdad() - 1][3]);

			LocalDate fechaFallecimiento = cliente.getFechaNacimiento().plusYears(edadTotal);

			cliente.setFechaFallecimiento(fechaFallecimiento);
		}

		return clientes;
	}

}
