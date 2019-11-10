package com.reto.models;

public class KpiClientesResponse {

	private double promedioEdades;
	private double desviacionEstandarEdades;

	public KpiClientesResponse(double promedioEdades, double desviacionEstandarEdades) {
		this.promedioEdades = promedioEdades;
		this.desviacionEstandarEdades = desviacionEstandarEdades;
	}

	public double getDesviacionEstandarEdades() {
		return desviacionEstandarEdades;
	};

	public double getPromedioEdades() {
		return promedioEdades;
	}

	public void setDesviacionEstandarEdades(double desviacionEstandarEdades) {
		this.desviacionEstandarEdades = desviacionEstandarEdades;
	}

	public void setPromedioEdades(double promedioEdades) {
		this.promedioEdades = promedioEdades;
	}
}
