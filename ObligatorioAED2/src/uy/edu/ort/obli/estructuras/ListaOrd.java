package uy.edu.ort.obli.estructuras;

public class ListaOrd<T extends Comparable<T>> extends Lista<T> {
	
	@Override
	public void insertarPpio(T dato) {
		throw new UnsupportedOperationException("No puede insertar al principio sobre una lista ordenada.");
	}
	
	@Override
	public void insertarFin(T dato) {
		throw new UnsupportedOperationException("No puede insertar al final sobre una lista ordenada.");
	}
	
	@Override
	public void insertarOrd(T dato) {
		if(inicio == null || dato.compareTo(inicio.getDato()) < 0) {
			super.insertarPpio(dato);
		} else {
			NodoLista<T> aux = inicio;
			while(aux.getSig() != null && dato.compareTo(aux.getSig().getDato()) >= 0)
			{
				aux = aux.getSig();
			}
			NodoLista<T> nuevo = new NodoLista<T>(dato, aux.getSig());
			aux.setSig(nuevo);		
			if(nuevo.getSig() == null) {
				fin = nuevo;
			}
			largo++;
		}
	}
	
	
}