package uy.edu.ort.obli.estructuras;


public class Cola<T> {
	private ILista<T> lista;

	public Cola() {
		this.lista = new Lista<T>();
	}
	
	public boolean estaVacia() {
		return lista.estaVacia();
	}
	
	public int largo() {
		return lista.largo();
	}
	
	public void encolar(T dato) {
		lista.insertarFin(dato);
	}
	
	public T desencolar() {
		T ret = lista.obtenerPpio();
		lista.borrarPpio();
		return ret;
	}
	
}
