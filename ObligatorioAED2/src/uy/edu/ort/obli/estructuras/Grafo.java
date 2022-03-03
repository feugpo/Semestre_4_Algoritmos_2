package uy.edu.ort.obli.estructuras;

import java.util.Iterator;

import uy.edu.ort.obli.Retorno;
import uy.edu.ort.obli.Retorno.Resultado;
import uy.edu.ort.obli.clases.Estacion;
import uy.edu.ort.obli.clases.Tramo;

public class Grafo {

	private int tope;
	private int cant;
	private Estacion[] vertices;
	private Lista<Tramo>[][] matAdy;   //PQ ILISTA Y NO LISTA?
	
	public Grafo(int tope, boolean esDirigido) {

//		this.cant = 0;
		this.tope = tope;

		this.vertices = new Estacion[tope];
		this.matAdy = (Lista<Tramo>[][])new Lista<?>[tope][tope];
		
		if(esDirigido) {
			for (int i = 0; i < tope; i++) {
				for (int j = 0; j < tope; j++) {
					this.matAdy[i][j] = new ListaOrd<Tramo>();
				}
			}			
		} else {
			for (int i = 0; i < tope; i++) {
				for (int j = i; j < tope; j++) {
					this.matAdy[i][j] = this.matAdy[j][i] = new ListaOrd<Tramo>();
				}
			}
		}
	}

	public boolean estaLleno() {
		return cant == tope;
	}

	private int buscarHueco() {
		for (int i = 0; i < tope; i++) {
			if (vertices[i] == null) {
				return i;
			}
		}
		return -1;
	}

	private int verticeAlAzar() {
		for (int i = 0; i < tope; i++) {
			if (vertices[i] != null) {
				return i;
			}
		}
		return -1;
	}

	public void insertarEstacion(Estacion estacion) {
		int pos = buscarHueco();
		vertices[pos] = estacion;
		cant++;
		}
	
	private int buscarPos(Estacion vert) {
		for (int i = 0; i < tope; i++) {
			if (vertices[i]!=null && vertices[i].equals(vert)) {
				return i;
			}
		}
		return -1;
	}
	
	public Estacion traerEstacion(Estacion vert) {
		for (int i = 0; i < tope; i++) {
			if (vertices[i].equals(vert)) {
				return vertices[i];
			}
		}
		return null;
	}

	public boolean existeVertice(Estacion vert) {
		return buscarPos(vert) != -1;
	}
	
	public boolean existeTramo(Estacion vOrigen, Estacion vDestino) {
		int posOrigen = buscarPos(vOrigen);
		int posDestino = buscarPos(vDestino);
		return !matAdy[posOrigen][posDestino].estaVacia(); // INVIERTO EL RESULTADO PARA QUE QUEDE MAS LEGIBLE
	}
	
	public Lista<Tramo> traerTramo(Estacion vOrigen, Estacion vDestino) {
		int posOrigen = buscarPos(vOrigen);
		int posDestino = buscarPos(vDestino);
		return matAdy[posOrigen][posDestino];
	}
	
	public void insertarTramo(Estacion vOrigen, Estacion vDestino, int metros, int minutos) {
		int posOrigen = buscarPos(vOrigen);
		int posDestino = buscarPos(vDestino);
		//creamos 2 tramos una en cada sentido
		Tramo nuevaUno = new Tramo();
		Tramo nuevaDos = new Tramo();
		nuevaUno.setOrigen(vOrigen);
		nuevaDos.setOrigen(vDestino);
		nuevaUno.setDestino(vDestino);
		nuevaDos.setDestino(vOrigen);
		nuevaUno.setMetros(metros);
		nuevaDos.setMetros(metros);
		nuevaUno.setMinutos(minutos);
		nuevaDos.setMinutos(minutos);
		String nombreUno = vOrigen.getNombre()+"_"+vDestino.getNombre();//+((matAdy[posOrigen][posDestino].largo/2)+1); 
		String nombreDos = vDestino.getNombre()+"_"+vOrigen.getNombre();//+((matAdy[posOrigen][posDestino].largo/2)+1); 
		nuevaUno.setNombre(nombreUno);
		nuevaDos.setNombre(nombreDos);
		matAdy[posOrigen][posDestino].insertarOrd(nuevaUno);
		matAdy[posOrigen][posDestino].insertarOrd(nuevaDos);
	}

	public Retorno dijkstraUnico(Estacion origen, Estacion destino, int radio) {
		Retorno ret = new Retorno(Resultado.OK); // LA USAMOS COMO CAPSULA PARA RETORNAR //DE ULTIMA LO CAMBIAMOS POR INT COMUN
		ret.valorString = "";
		ret.valorEntero = 0;
		// Declaro los vectores necesarios
		int[] dist = new int[tope];
		Estacion[] ant = new Estacion[tope]; //CON ESTE VECTOR ARMAR STRING CAMINO RECORRIDO
		boolean[] vis = new boolean[tope];
		// Inicializo los vectores
		for (int i = 0; i < tope; dist[i] = Integer.MAX_VALUE, ant[i] = new Estacion(0d, 0d, ""), i++);		
		int posOrigen = buscarPos(origen);	
		int posDestino = buscarPos(destino);
		// Declaro la distancia al origen como 0
		dist[posOrigen] = 0;		
		for (int k = 0; k < cant; k++) {
			// Busco la distancia más corta no visitada
			int pos = vertNoVisDeMenorDistancia(dist, vis);
			// Visito al vértice
			vis[pos] = true;
//			if(pos == posDestino) {
//				break;
//			}
			// Recorro los adyacentes buscando los vértices no visitados que deba actualizar, 
			// 		o sea, que a través de mí tienen una menor distancia al origen.
			for (int j = 0; j < tope; j++) {
				if(!vis[j] && !matAdy[pos][j].estaVacia()) {
					int distCantidata = dist[pos] + matAdy[pos][j].obtenerPpio().getMetros();
					if(distCantidata < dist[j]) {
						//COMPARAR CON RADIO Y CONCATENAR NOMBRE ESTACION
						dist[j] = distCantidata;
						ant[j] = vertices[pos];
					}
				}
			}
		}
		if(posDestino == -1) {  
			for(int l = 1; l < tope; l++){
				if(dist[l] <= radio) {
					if(l!=1) {
						ret.valorString += "|" + vertices[l].toString();
					}else ret.valorString += vertices[l].toString();	
				}
			}
		}else {
			Estacion destinoTramo = vertices[posDestino];
			Estacion origenTramo = ant[buscarPos(destinoTramo)];
			while(origenTramo.getNombre() != "") {
				Lista<Tramo> tramosArista = traerTramo(origenTramo, destinoTramo); // tengo la lista de tramos, preciso el q tenga el origen y destino en el orden deseado
				//Tramo aux = tramosArista.obtenerPpio(); //necesito la referencia de la distancia y tiempo para el Equals, sino siempre da null //=> necesito una mejor forma 
				//Tramo tramoBuscado = new Tramo(origenTramo, destinoTramo, aux.getMetros(), aux.getMinutos());
				//tramoBuscado = tramosArista.recuperar(tramoBuscado);
				Tramo tramoBuscado = new Tramo(origenTramo, destinoTramo);
				int min = Integer.MAX_VALUE;
				int contador = 0;
				for(Tramo t : tramosArista) {
					if(t.getOrigen().equals(origenTramo) && t.getDestino().equals(destinoTramo) && t.getMetros() < min){
						min = t.getMetros();
						tramoBuscado = t;
						contador ++;
					}
				}
				if(destinoTramo != destino) {
					ret.valorString = tramoBuscado.toString()+"_"+contador + "|" + ret.valorString;
					//ret.valorString = tramoBuscado.toString() + "|" + ret.valorString;  //para solucionar la barrita al final
				}else ret.valorString = tramoBuscado.toString()+"_"+contador;
				destinoTramo = origenTramo;  //  el que era ant ahora es el destino del tramo anterior
				origenTramo = ant[buscarPos(destinoTramo)];
			}
		}
		if(posDestino != -1)ret.valorEntero = dist[posDestino];
		return ret;
	}

	private int vertNoVisDeMenorDistancia(int[] dist, boolean[] vis) {
		int posMin = -1, min = Integer.MAX_VALUE;
		for (int i = 0; i < tope; i++) {
			if(!vis[i] && dist[i] < min) {
				 min = dist[i];
				 posMin = i;
			}
		}
		return posMin;
	}

	public String generarURL(){
		String ret = "http://maps.googleapis.com/maps/api/staticmap?center=Montevideo,Uruguay&zoom=10&size=1200x600&maptype=roadmap";
		String[] colores = new String[20];
		colores[0] = "green";
		colores[1] = "blue";
		colores[2] = "red";
		colores[3] = "orange";
		colores[4] = "cyan";
		colores[5] = "yellow";
		int contador = 0;
		for(int i=0; i<cant ;i++) {
			for (int j = 0; j < cant; j++) {
				if(!matAdy[i][j].estaVacia()) {
					Tramo tramo = matAdy[i][j].obtenerPpio();
					ret += "&markers=color:"+colores[contador]+"%7Clabel:"+tramo.getOrigen().getNombre()+"%7C"+tramo.getOrigen().getCoordX()+","+tramo.getOrigen().getCoordY();
					ret += "&markers=color:"+colores[contador]+"%7Clabel:"+tramo.getDestino().getNombre()+"%7C"+tramo.getDestino().getCoordX()+","+tramo.getDestino().getCoordY();
				}
			}
			contador ++;
		}
		ret+="&sensor=false&key=AIzaSyCZNdROFj6oM2GfFKu4uzh5VoqOYkz6VHQ";
		return ret;
	}
	
	
	

	// Pre: existeVertice(vert)
	/*
	public void borrarVertice(String vert) {
		int pos = buscarPos(vert);
		vertices[pos] = null;
		for (int i = 0; i < tope; i++) {
			Lista<Tramo> celdaMatriz = matAdy[pos][i];
			Tramo buscado = new Tramo();
			celdaMatriz.recuperar(null)
			matAdy[pos][i].setExiste(false);
			//Listaenlamatriz.eltramo.laestacion
			matAdy[i][pos].setExiste(false);
		}
		cant--;
	}
	*/
  // Pre: existeVertice(vOrigen) && existeVertice(vDestino)
	

	//____________________________________________________________________________________________________________________

	
	
	/*
	
	
	
	// Pre: existeVertice(vOrigen) && existeVertice(vDestino) &&
	// existeTramo(vOrigen, vDestino)
	public void borrarTramo(Estacion vOrigen, Estacion vDestino, int metros, int minutos) {
		int posOrigen = buscarPos(vOrigen);
		int posDestino = buscarPos(vDestino);
		Tramo buscado = new Tramo(vOrigen, vDestino, metros, minutos);
		boolean existe = matAdy[posOrigen][posDestino].existe(buscado);
		if(existe) {
			matAdy[posOrigen][posDestino].borrar(buscado);
		}
	}
	
	public void dfs() {
		int pos = verticeAlAzar();
		boolean[] vis = new boolean[tope];
		dfsRec(pos, vis);
	}

	private void dfsRec(int pos, boolean[] vis) {
		System.out.println(vertices[pos]);
		vis[pos] = true;
		for (int j = 0; j < tope; j++) {
			if(matAdy[pos][j].isExiste() && !vis[j]) {
				dfsRec(j, vis);
			}
		}
	}
	
	public void bfs() {
		int origen = verticeAlAzar();
		boolean[] vis = new boolean[tope];
		Cola<Integer> cola = new Cola<Integer>();
		cola.encolar(origen);
		vis[origen] = true;
		while(!cola.estaVacia()) {
			int pos = cola.desencolar();
			System.out.println(vertices[pos]);
			for (int j = 0; j < tope; j++) {
				if(matAdy[pos][j].isExiste() && !vis[j]) {
					cola.encolar(j);
					vis[j] = true;
				}
			}
		}
	}
	
	public int dijkstra(String origen, String destino) {
		// Declaro los vectores necesarios
		int[] dist = new int[tope];
		String[] ant = new String[tope];
		boolean[] vis = new boolean[tope];
		
		// Inicializo los vectores
		for (int i = 0; i < tope; dist[i] = Integer.MAX_VALUE, ant[i] = "-", i++);
		
		int posOrigen = buscarPos(origen);
		int posDestino = buscarPos(destino);
		
		// Declaro la distancia al origen como 0
		dist[posOrigen] = 0;
		
		for (int k = 0; k < cant; k++) {
			// Busco la distancia más corta no visitada
			int pos = vertNoVisDeMenorDistancia(dist, vis);

			// Visito al vértice
			vis[pos] = true;
			
//			if(pos == posDestino) {
//				break;
//			}
			
			// Recorro los adyacentes buscando los vértices no visitados que deba actualizar, 
			// 		o sea, que a través de mí tienen una menor distancia al origen.
			
			for (int j = 0; j < tope; j++) {
				if(!vis[j] && matAdy[pos][j].isExiste()) {
					int distCantidata = dist[pos] + matAdy[pos][j].getMetros();
					if(distCantidata < dist[j]) {
						dist[j] = distCantidata;
						ant[j] = vertices[pos];
					}
				}
			}
		}
		return dist[posDestino];		
	}

	private int vertNoVisDeMenorDistancia(int[] dist, boolean[] vis) {
		int posMin = -1, min = Integer.MAX_VALUE;
		for (int i = 0; i < tope; i++) {
			if(!vis[i] && dist[i] < min) {
				 min = dist[i];
				 posMin = i;
			}
		}
		return posMin;
	}
	
	
	*/
	
	// Pre: !estaLleno()
		/*
		public void insertarVertice(String vert) {
			int pos = buscarHueco();
			vertices[pos] = vert;
			cant++;
		}
		*/
	
	/*
	private int buscarPos(String vert) {
		for (int i = 0; i < tope; i++) {
			if (vertices[i].equals(vert)) {
				return i;
			}
		}
		return -1;
	}
	*/
	
	
	/*
	 public boolean existeVertice(String vert) {
		return buscarPos(vert) != -1;
	}
	*/
	

	// Pre: existeVertice(vOrigen) && existeVertice(vDestino) &&
	// !existeTramo(vOrigen, vDestino)
	/*
	public void insertarTramo(String vOrigen, String vDestino, int metros, int minutos) {
		int posOrigen = buscarPos(vOrigen);
		int posDestino = buscarPos(vDestino);
		
		matAdy[posOrigen][posDestino].setExiste(true);
		matAdy[posOrigen][posDestino].setMetros(metros);
		matAdy[posOrigen][posDestino].setMinutos(minutos);
	}
	*/

}
