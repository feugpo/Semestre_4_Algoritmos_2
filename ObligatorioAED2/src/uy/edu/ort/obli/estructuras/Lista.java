package uy.edu.ort.obli.estructuras;
import java.util.Iterator;

public class Lista<T> implements ILista<T> {

	protected NodoLista<T> inicio;
	protected NodoLista<T> fin;
	protected int largo;
	protected int tope;

	public Lista() {
//		this.inicio = null;
//		this.fin = null;
//		this.largo = 0;
		this.tope = -1;
	}
	
	public Lista(int tope) {
//		this.inicio = null;
//		this.fin = null;
//		this.largo = 0;
		this.tope = tope;
	}
	
	@Override
	public void insertarPpio(T dato) {
		NodoLista<T> nuevo = new NodoLista<T>(dato, inicio);
		inicio = nuevo;
		if(fin == null) {
			fin = nuevo;
		}
		largo++;
	}
	
	@Override
	public void insertarFin(T dato) {
		if(inicio == null) {
			insertarPpio(dato);
		} else {
			NodoLista<T> nuevo = new NodoLista<T>(dato);
			fin.setSig(nuevo);
			fin = nuevo;
			largo++;			
		}
	}
	
	@Override
	public void borrarPpio() {
		inicio = inicio.getSig();
		largo--;
		if(inicio == null) {
			fin = null;
		}
	}
	
	@Override
	public void borrarFin() {
		if(largo == 1) {
			borrarPpio();
		} else {
			NodoLista<T> aux = inicio;
			while(aux.getSig().getSig() != null) {
				aux = aux.getSig();
			}
			aux.setSig(null);
			fin = aux;
			largo--;
		}
	}
	
	@Override
	public int largo() {
		return largo;
	}
	
	@Override
	public boolean estaVacia() {
		return inicio == null;
	}

	@Override
	public boolean estaLlena() {
		return largo == tope;
	}

	@Override
	public boolean existe(T dato) {
		NodoLista<T> aux = inicio;
		while(aux != null) {
			if(dato.equals(aux.getDato()))
				return true;
			else
				aux = aux.getSig();
		}
		return false;
	}

	@Override
	public T recuperar(T dato) {
		NodoLista<T> aux = inicio;
		while(aux != null) {
			if(dato.equals(aux.getDato()))
				return aux.getDato();
			else
				aux = aux.getSig();
		}
		return null;
	}

	@Override
	public void borrar(T dato) {
		if(dato.equals(inicio.getDato())) {
			borrarPpio();
		} else if(dato.equals(fin.getDato())) {
			borrarFin();
		} else {
			NodoLista<T> aux = inicio;
			while(!dato.equals(aux.getSig().getDato())) {
				aux = aux.getSig();
			}
			aux.setSig(aux.getSig().getSig());
			largo--;
		}
	}

	@Override
	public void insertarOrd(T dato) {
		throw new UnsupportedOperationException("No puede insertar ordenadamente sobre una lista no ordenada.");
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			
			private NodoLista<T> aux = inicio;
			
			@Override
			public boolean hasNext() {
				return aux != null;
			}

			@Override
			public T next() {
				T ret = aux.getDato();
				aux = aux.getSig();
				return ret;
			}
		};
	}

	@Override
	public T obtenerPpio() {
		return inicio.getDato();
	}

	@Override
	public T obtenerFin() {
		return fin.getDato();
	}
	
}
