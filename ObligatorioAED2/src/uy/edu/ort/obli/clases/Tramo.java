package uy.edu.ort.obli.clases;

public class Tramo implements Comparable<Tramo> {
	
	

	private Estacion origen;
	private Estacion destino;
	private int metros;
	private int minutos;
	private String nombre;

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Tramo() {
//		this.costo = 0;
//		this.existe = false;
	}
	public Tramo(Estacion origen, Estacion destino, int metros, int minutos) {
		this.origen = origen;
		this.destino = destino;
		this.metros = metros;
		this.minutos = minutos;
	}
	public Tramo(Estacion origen, Estacion destino) {
		this.origen = origen;
		this.destino = destino;
	}
	public Estacion getOrigen() {
		return origen;
	}

	public void setOrigen(Estacion origen) {
		this.origen = origen;
	}

	public Estacion getDestino() {
		return destino;
	}

	public void setDestino(Estacion destino) {
		this.destino = destino;
	}

	public int getMetros() {
		return metros;
	}


	public void setMetros(int metros) {
		this.metros = metros;
	}


	public int getMinutos() {
		return minutos;
	}


	public void setMinutos(int minutos) {
		this.minutos = minutos;
	}

	@Override
	public int compareTo(Tramo o) {
		return this.metros - o.metros;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((destino == null) ? 0 : destino.hashCode());
		result = prime * result + metros;
		result = prime * result + minutos;
		result = prime * result + ((origen == null) ? 0 : origen.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tramo other = (Tramo) obj;
		if (destino == null) {
			if (other.destino != null)
				return false;
		} else if (!destino.equals(other.destino))
			return false;
		if (metros != other.metros)
			return false;
		if (minutos != other.minutos)
			return false;
		if (origen == null) {
			if (other.origen != null)
				return false;
		} else if (!origen.equals(other.origen))
			return false;
		return true;
	}
	@Override
	public String toString() {
		//return "Tramo [origen=" + origen + ", destino=" + destino + ", metros=" + metros + ", minutos=" + minutos + "]";
		return origen.getCoordX()+";"+origen.getCoordY()+";"+destino.getCoordX()+";"+destino.getCoordY()+";"+nombre;
				
	}

}
