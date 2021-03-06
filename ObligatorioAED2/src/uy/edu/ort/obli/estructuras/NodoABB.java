package uy.edu.ort.obli.estructuras;

public class NodoABB<T> {
	private T dato;
	private NodoABB<T> izq, der;

	public NodoABB(T dato) {
		this.dato = dato;
		this.izq = this.der = null;
	}

	public NodoABB(T dato, NodoABB<T> izq, NodoABB<T> der) {
		this.dato = dato;
		this.izq = izq;
		this.der = der;
	}

	public T getDato() {
		return dato;
	}

	public void setDato(T dato) {
		this.dato = dato;
	}

	public NodoABB<T> getIzq() {
		return izq;
	}

	public void setIzq(NodoABB<T> izq) {
		this.izq = izq;
	}

	public NodoABB<T> getDer() {
		return der;
	}

	public void setDer(NodoABB<T> der) {
		this.der = der;
	}

	@Override
	public String toString() {
		return dato + "";
	}

}
