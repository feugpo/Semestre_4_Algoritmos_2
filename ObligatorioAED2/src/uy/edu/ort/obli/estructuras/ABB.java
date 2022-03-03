package uy.edu.ort.obli.estructuras;

import uy.edu.ort.obli.Entero;
import uy.edu.ort.obli.Retorno;
import uy.edu.ort.obli.Retorno.Resultado;

public class ABB<T extends Comparable<T>> {

	private NodoABB<T> raiz;

	public ABB() {
//		this.raiz = null;
	}

	public ABB(NodoABB<T> raiz) {
		this.raiz = raiz;
	}

	public NodoABB<T> getRaiz() {
		return raiz;
	}

	public int cantNodos() {
		return cantNodosRec(raiz);
	}

	private int cantNodosRec(NodoABB<T> nodo) {
		if (nodo == null) {
			return 0;
		} else {
			int nodosIzq = cantNodosRec(nodo.getIzq());
			int nodosDer = cantNodosRec(nodo.getDer());
			return 1 + nodosIzq + nodosDer;
		}
	}

	public int altura() {
		return alturaRec(raiz);
	}

	private int alturaRec(NodoABB<T> nodo) {
		if (nodo == null) {
			return 0;
		} else {
			int alturaIzq = alturaRec(nodo.getIzq());
			int alturaDer = alturaRec(nodo.getDer());
			return 1 + Math.max(alturaIzq, alturaDer);
		}
	}

	public boolean esBalanceado() {
		return esBalanceadoRec(raiz);
	}

	private boolean esBalanceadoRec(NodoABB<T> nodo) {
		if (nodo == null) {
			return true;
		} else {
			return Math.abs(alturaRec(nodo.getIzq()) - alturaRec(nodo.getDer())) <= 1 && esBalanceadoRec(nodo.getIzq())
					&& esBalanceadoRec(nodo.getDer());
		}
	}

	public boolean pertenece(T dato) {
		return perteneceRec(raiz, dato);
	}

	private boolean perteneceRec(NodoABB<T> nodo, T dato) {
		if (nodo == null) {
			return false;
		} else if (dato.equals(nodo.getDato())) {
			return true;
		} else if (dato.compareTo(nodo.getDato()) < 0) {
			return perteneceRec(nodo.getIzq(), dato);
		} else {
			return perteneceRec(nodo.getDer(), dato);
		}
	}
	
	// -----------------------------------------------------------------------
	
	
	public Retorno buscarDato(T dato) {
		Retorno ret = new Retorno(Resultado.NO_IMPLEMENTADA); // LO USAMOS COMO CAPSULA // EN ESTOS CASOS ESPECIFICOS
		ret.valorString = "";
		ret.valorEntero = 0;
		return buscarDatoRec(raiz, dato, ret);
	}
	private Retorno buscarDatoRec(NodoABB<T> nodo, T dato, Retorno ret) {
		if (nodo == null) {
			return null;
		} else if (dato.equals(nodo.getDato())) {
			ret.valorEntero++;
			ret.valorString += nodo.getDato().toString();
			return ret;
		} else if (dato.compareTo(nodo.getDato()) < 0) {
			ret.valorEntero++;
			return buscarDatoRec(nodo.getIzq(), dato, ret);
		} else {
			ret.valorEntero++;
			return buscarDatoRec(nodo.getDer(), dato, ret);
		}
	}
	
	public String listarAscendente() {
		NodoABB<T> minimo = elMinimo(); //Lo agrego aca para minimizar los llamados a el minimo
		String listaUsuarios = listarAscendenteRec(raiz, minimo);
		return listaUsuarios;
	}
	private String listarAscendenteRec(NodoABB<T> nodo, NodoABB<T> minimo) {
		String ret = "";
		if (nodo != null) {
			ret += listarAscendenteRec(nodo.getIzq(), minimo);
			if(nodo != minimo) {
				ret += "|";
			}
			ret += nodo.getDato().toString(); 
			ret += listarAscendenteRec(nodo.getDer(), minimo);
		}
		return ret;
	}
	//METODO PARA APOYAR AL LISTADO
	public NodoABB<T> elMinimo() {
		return elMinimoRec(raiz);
	}
	private NodoABB<T> elMinimoRec(NodoABB<T> nodo) {
		if(nodo.getIzq() != null)return elMinimoRec(nodo.getIzq());
		return nodo;
	}
	
	
	// -----------------------------------------------------------------------
	
	
	public void listarDescendente() {
		listarDescendenteRec(raiz);
	}

	private void listarDescendenteRec(NodoABB<T> nodo) {
		if (nodo != null) {
			listarDescendenteRec(nodo.getDer());
			System.out.println(nodo);
			listarDescendenteRec(nodo.getIzq());
		}
	}

	// Pre: !esVacio()
	public void borrarMin() {
		if (raiz.getIzq() == null) {
			raiz = raiz.getDer();
		} else {
			borrarMinRec(raiz);
		}
	}

	private void borrarMinRec(NodoABB<T> nodo) {
		if (nodo.getIzq().getIzq() == null) {
			nodo.setIzq(nodo.getIzq().getDer());
		} else {
			borrarMinRec(nodo.getIzq());
		}
	}

	public Capsula<T> borrarMinCheto(Capsula<T> capsula) {
		raiz = borrarMinChetoRec(raiz, capsula);
		return capsula;
	}

	private NodoABB<T> borrarMinChetoRec(NodoABB<T> nodo, Capsula<T> num) {
		if (nodo.getIzq() == null) {
			num.dato = nodo.getDato();
			return nodo.getDer();
		} else {
			nodo.setIzq(borrarMinChetoRec(nodo.getIzq(), num));
			return nodo;
		}
	}

	public void borrarMaxCheto() {
		raiz = borrarMaxChetoRec(raiz);
	}

	private NodoABB<T> borrarMaxChetoRec(NodoABB<T> nodo) {
		if (nodo.getDer() == null) {
			return nodo.getIzq();
		} else {
			nodo.setDer(borrarMaxChetoRec(nodo.getDer()));
			return nodo;
		}
	}

	public void insertar(T dato) {
		if (raiz == null) {
			raiz = new NodoABB<T>(dato);
		} else {
			insertarRec(raiz, dato);
		}
	}

	private void insertarRec(NodoABB<T> nodo, T dato) {
		if (dato.compareTo(nodo.getDato()) < 0) {
			if (nodo.getIzq() == null) {
				nodo.setIzq(new NodoABB<T>(dato));
			} else {
				insertarRec(nodo.getIzq(), dato);
			}
		} else if (dato.compareTo(nodo.getDato()) > 0) {
			if (nodo.getDer() == null) {
				nodo.setDer(new NodoABB<T>(dato));
			} else {
				insertarRec(nodo.getDer(), dato);
			}
		}
	}

	public void insertarCheto(T dato) {
		raiz = insertarChetoRec(raiz, dato);
	}

	private NodoABB<T> insertarChetoRec(NodoABB<T> nodo, T dato) {
		if (nodo == null) {
			return new NodoABB<T>(dato);
		} else {
			if (dato.compareTo(nodo.getDato()) < 0) {
				nodo.setIzq(insertarChetoRec(nodo.getIzq(), dato));
			} else if (dato.compareTo(nodo.getDato()) > 0) {
				nodo.setDer(insertarChetoRec(nodo.getDer(), dato));
			}
			return nodo;
		}
	}

	public void borrar(T dato) {
		if (dato.equals(raiz.getDato())) {
			if (raiz.getIzq() == null && raiz.getDer() == null) {
				raiz = null; // Caso FÁCIL! :-)
			} else if (raiz.getIzq() == null) {
				raiz = raiz.getDer(); // Caso MEDIANO... :-|
			} else if (raiz.getDer() == null) {
				raiz = raiz.getIzq(); // Caso MEDIANO... :-|
			} else {
				Capsula<T> capsula = new Capsula<T>();
				raiz.setDer(borrarMinChetoRec(raiz.getDer(), capsula));
				raiz.setDato(capsula.dato);
			}
		} else {
			borrarRec(dato, raiz);
		}
	}

	private void borrarRec(T dato, NodoABB<T> nodo) {
		if (dato.compareTo(nodo.getDato()) < 0) {
			if (dato.equals(nodo.getIzq().getDato())) {
				if (nodo.getIzq().getIzq() == null && nodo.getIzq().getDer() == null) {
					nodo.setIzq(null); // Caso FÁCIL! :-)
				} else if (nodo.getIzq().getIzq() == null) {
					nodo.setIzq(nodo.getIzq().getDer()); // Caso MEDIANO... :-|
				} else if (nodo.getIzq().getDer() == null) {
					nodo.setIzq(nodo.getIzq().getIzq()); // Caso MEDIANO... :-|
				} else {
					Capsula<T> capsula = new Capsula<T>();
					nodo.getIzq().setDer(borrarMinChetoRec(nodo.getIzq().getDer(), capsula));
					nodo.getIzq().setDato(capsula.dato);
				}
			} else {
				borrarRec(dato, nodo.getIzq());
			}
		} else if (dato.compareTo(nodo.getDato()) > 0) {
			if (dato.equals(nodo.getDer().getDato())) {
				if (nodo.getDer().getIzq() == null && nodo.getDer().getDer() == null) {
					nodo.setDer(null); // Caso FÁCIL! :-)
				} else if (nodo.getDer().getIzq() == null) {
					nodo.setDer(nodo.getDer().getDer()); // Caso MEDIANO... :-|
				} else if (nodo.getDer().getDer() == null) {
					nodo.setDer(nodo.getDer().getIzq()); // Caso MEDIANO... :-|
				} else {
					Capsula<T> capsula = new Capsula<T>();
					nodo.getDer().setDer(borrarMinChetoRec(nodo.getDer().getDer(), capsula));
					nodo.getDer().setDato(capsula.dato);
				}
			} else {
				borrarRec(dato, nodo.getDer());
			}
		}
	}

	public void borrarCheto(T dato) {
		raiz = borrarChetoRec(dato, raiz);
	}

	private NodoABB<T> borrarChetoRec(T dato, NodoABB<T> nodo) {
		if (dato.equals(nodo.getDato())) {
			if (nodo.getIzq() == null && nodo.getDer() == null) {
				nodo = null; // Caso FÁCIL! :-)
			} else if (nodo.getIzq() == null) {
				nodo = nodo.getDer(); // Caso MEDIANO... :-|
			} else if (nodo.getDer() == null) {
				nodo = nodo.getIzq(); // Caso MEDIANO... :-|
			} else {
				Capsula<T> capsula = new Capsula<T>();
				nodo.setDer(borrarMinChetoRec(nodo.getDer(), capsula));
				nodo.setDato(capsula.dato);
			}
		} else if(dato.compareTo(nodo.getDato()) < 0){
			nodo.setIzq(borrarChetoRec(dato, nodo.getIzq()));
		} else if(dato.compareTo(nodo.getDato()) > 0){ // Innecesario este if, siempre es true (tricotomía de la matemática...)
			nodo.setDer(borrarChetoRec(dato, nodo.getDer()));
		}
		return nodo;
	}
	
	/*
	public void listarAscendente() {
		listarAscendenteRec(raiz);
	}

	private void listarAscendenteRec(NodoABB<T> nodo) {
		if (nodo != null) {
			listarAscendenteRec(nodo.getIzq());
			System.out.println(nodo);
			listarAscendenteRec(nodo.getDer());
		}
	}
	*/
}
