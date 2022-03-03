/**
 * 
 */
package uy.edu.ort.obli;

import uy.edu.ort.obli.clases.Estacion;
import uy.edu.ort.obli.clases.Usuario;
import uy.edu.ort.obli.estructuras.ABB;

/**
 * @author Fernando
 *
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Sistema s = new Sistema();
		s.inicializarSistema(5);
		s.registrarUsuario("1.235.641-2", "Maximiliano");
		s.registrarUsuario("2.786.365-3", "Lucia");
		//Estacion prueba = new Estacion(200d, 100d, "estacion primera");
		s.registrarEstacion(200d, 100d, "estacion primera");
		s.registrarEstacion(250d, 80d, "estacion segunda");
		s.registrarEstacion(250d, 80d, "estacion tercera");
		s.registrarEstacion(150d, 60d, "estacion Central");
		s.registrarEstacion(220d, 70d, "estacion cuarta");
		s.registrarEstacion(235d, 60d, "estacion quinta");
		s.registrarEstacion(122d, 60d, "estacion sexta");
		
		s.registrarTramo(200d, 100d, 250d, 80d, 20, 30);
		s.registrarTramo(250d, 80d, 150d, 60d, 20, 30);
		s.registrarTramo(150d, 60d, 235d, 60d, 20, 30);
		System.out.println("existe tramo: " + s.tramos.existeTramo(new Estacion(200d, 100d, "estacion primera"), new Estacion(250d, 80d, "estacion segunda")));
		System.out.println("Camino mas corto: " + s.caminoMinimo(200d, 100d, 235d, 60d));
		//s.registrarUsuario("1.987.632-5", "Mati");
		//s.registrarUsuario("3.567.489-3", "Fernando");
		//System.out.println("lista de usuarios: " + s.listarUsuarios().valorString);
		//System.out.println("usuario buscado: " + s.buscarUsuario("1.235.641-2").valorString);
		//System.out.println("expresion regular: " + s.cedulaValida);
		  
		 
	}

}
