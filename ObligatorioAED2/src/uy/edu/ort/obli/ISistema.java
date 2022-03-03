package uy.edu.ort.obli;

public interface ISistema {

	Retorno inicializarSistema (int maxPuntos);
	
	Retorno destruirSistema();

	Retorno registrarUsuario(String cedula, String nombre);
	
	Retorno buscarUsuario(String cedula);

	Retorno listarUsuarios();

	Retorno registrarEstacion(Double coordX, Double coordY, String
			nombre);
	
	Retorno registrarTramo(Double coordXi, Double coordYi, Double
			coordXf, Double coordYf, int metros, int minutos);
	
	Retorno evacuacionEmergencia(Double coordXi, Double coordYi, int
			metros);

	Retorno caminoMinimo(Double coordXi, Double coordYi, Double
			coordXf, Double coordYf);

	Retorno dibujarMapa();
}
