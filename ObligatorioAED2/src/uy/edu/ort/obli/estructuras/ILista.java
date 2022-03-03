package uy.edu.ort.obli.estructuras;

public interface ILista<T> extends Iterable<T> {
	
	public void insertarPpio(T dato);
	public void insertarFin(T dato);
	public void insertarOrd(T dato);
	public void borrarPpio();
	public void borrarFin();
	public T obtenerPpio();
	public T obtenerFin();
	public int largo();
	public boolean estaVacia();
	public boolean estaLlena();
	public boolean existe(T dato);
	public T recuperar(T dato);
	public void borrar(T dato);
	
}