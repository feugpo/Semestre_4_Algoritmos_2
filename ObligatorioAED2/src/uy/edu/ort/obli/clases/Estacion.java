package uy.edu.ort.obli.clases;

public class Estacion {
	private String nombre;
	private Double coordX;
	private Double coordY;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Double getCoordX() {
		return coordX;
	}
	public void setCoordX(Double coordX) {
		this.coordX = coordX;
	}
	public Double getCoordY() {
		return coordY;
	}
	public void setCoordY(Double coordY) {
		this.coordY = coordY;
	}
	public Estacion(Double coordX, Double coordY, String nombre) {
		this.nombre = nombre;
		this.coordX = coordX;
		this.coordY = coordY;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coordX == null) ? 0 : coordX.hashCode());
		result = prime * result + ((coordY == null) ? 0 : coordY.hashCode());
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
		Estacion other = (Estacion) obj;
		if (coordX == null) {
			if (other.coordX != null)
				return false;
		} else if (!coordX.equals(other.coordX))
			return false;
		if (coordY == null) {
			if (other.coordY != null)
				return false;
		} else if (!coordY.equals(other.coordY))
			return false;
		return true;
	}
	@Override
	public String toString() {
		//return "Estacion [nombre=" + nombre + ", coordX=" + coordX + ", coordY=" + coordY + "]";
		return coordX+";"+coordY+";"+nombre;
	}
	
}
