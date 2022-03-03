package uy.edu.ort.obli;



import java.util.regex.Pattern;

import uy.edu.ort.obli.Retorno.Resultado;
import uy.edu.ort.obli.clases.Estacion;
import uy.edu.ort.obli.clases.Usuario;
import uy.edu.ort.obli.estructuras.ABB;
import uy.edu.ort.obli.estructuras.Grafo;

public class Sistema implements ISistema{
	
	//CREO LISTAS PARA LLAMAR DE LOS METODOS O ESTRUCTURA ARBOL GRAFO ETC? 
	ABB<Usuario> usuarios;
	Grafo tramos;
	//Pattern cedulaValida;
	
	
	
	

	public ABB<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(ABB<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
	
	public Sistema(){
        
    }

	@Override
	public Retorno inicializarSistema(int maxPuntos) {
		Retorno ret = new Retorno(Retorno.Resultado.OK);
		boolean dirigido = false;
		if(maxPuntos > 0) {
			//this.cedulaValida = Pattern.compile("^[0-9]([.][0-9]{3}){2}-[0-9]$");
			this.usuarios = new ABB<Usuario>();
			this.tramos = new Grafo(maxPuntos, dirigido);
		}else {
			ret.resultado = Retorno.Resultado.ERROR_1;
		}
		return ret;
	}

	@Override
	public Retorno destruirSistema() {
		Retorno ret = new Retorno(Retorno.Resultado.NO_IMPLEMENTADA);
		this.usuarios=null;
		this.tramos=null;
		//crear metodos q vacien las estructuras? Si son listas seria vaciar listas
		ret.resultado = Retorno.Resultado.OK;
		return ret;
	}

	@Override
	public Retorno registrarUsuario(String cedula, String nombre) {
		Retorno ret = new Retorno(Resultado.NO_IMPLEMENTADA);
		Usuario usuario = new Usuario(cedula, nombre);
		boolean esValida = Pattern.matches("^[0-9]([.][0-9]{3}){2}-[0-9]$", cedula); //validacion usando expresion regular ingresada
		boolean pertenece = usuarios.pertenece(usuario); // validar si existe usuario // Usamos el metodo pertenece que ya existe > se podria usar el buscar usuario
		if(!esValida) {
			ret.resultado = Retorno.Resultado.ERROR_1;
			return ret;
		}else if(pertenece) {
			ret.resultado = Retorno.Resultado.ERROR_2;
			return ret;
		}else{
			ret.resultado = Retorno.Resultado.OK;
			usuarios.insertarCheto(usuario);
		}
		return ret;
	}

	@Override
	public Retorno buscarUsuario(String cedula) {
		Retorno ret = new Retorno(Resultado.NO_IMPLEMENTADA); 
		boolean esValida = Pattern.matches("^[0-9]([.][0-9]{3}){2}-[0-9]$", cedula);
		if(!esValida) {
			ret.resultado = Retorno.Resultado.ERROR_1;
			return ret;
		}
		Usuario usuarioBuscado = new Usuario(cedula, "");
		Retorno busqueda = usuarios.buscarDato(usuarioBuscado);
		//FALTA CONTADOR DE DATOS RECORRIDOS TAMBIEN // PUEDO USAR UNA LISTA DE STRINGS PARA PASAR LOS DATOS? O ES OVERKILL?
		if(busqueda == null) {
			ret.resultado = Retorno.Resultado.ERROR_2;
		}else {
			ret.valorString = busqueda.valorString;
			ret.valorEntero = busqueda.valorEntero;
			ret.resultado = Retorno.Resultado.OK;
		}
		return ret;
	}

	@Override
	public Retorno listarUsuarios() {
		Retorno ret = new Retorno(Resultado.OK); 
		ret.valorString = usuarios.listarAscendente();
		return ret;
	}

	@Override
	public Retorno registrarEstacion(Double coordX, Double coordY, String nombre) {
		Retorno ret = new Retorno(Resultado.NO_IMPLEMENTADA);
		Estacion nueva = new Estacion(coordX, coordY, nombre);
		if(tramos.estaLleno()) {
			ret.resultado = Retorno.Resultado.ERROR_1;
		}else if(!tramos.existeVertice(nueva)) {
			ret.resultado = Retorno.Resultado.OK;
			tramos.insertarEstacion(nueva);
		}else{
			ret.resultado = Retorno.Resultado.ERROR_2;
		}
		return ret;
	}

	@Override
	public Retorno registrarTramo(Double coordXi, Double coordYi, Double coordXf, Double coordYf, int metros,
			int minutos) {
		Retorno ret = new Retorno(Resultado.NO_IMPLEMENTADA);
		Estacion origen = tramos.traerEstacion(new Estacion(coordXi, coordYi, ""));
		Estacion destino = tramos.traerEstacion(new Estacion(coordXf, coordYf, ""));
		if(metros <= 0) {
			ret.resultado = Retorno.Resultado.ERROR_1;
		}else if(minutos <= 0) {
			ret.resultado = Retorno.Resultado.ERROR_2;
		}else if(origen == null || destino == null) {
			ret.resultado = Retorno.Resultado.ERROR_3;
		}else {
			ret.resultado = Retorno.Resultado.OK;
			tramos.insertarTramo(origen, destino, metros, minutos);
		}
		return ret;
	}

	@Override
	public Retorno evacuacionEmergencia(Double coordXi, Double coordYi, int metros) {
		Retorno ret = new Retorno(Resultado.NO_IMPLEMENTADA);
		Estacion puntoEvacuacion = tramos.traerEstacion(new Estacion(coordXi, coordYi, ""));
		if(puntoEvacuacion != null) {
			ret.resultado = Retorno.Resultado.OK;
			ret = tramos.dijkstraUnico(puntoEvacuacion, new Estacion(0d, 0d, ""), metros);
		}else {
			ret.resultado = Retorno.Resultado.ERROR_1;
		}		
		return ret;
	}

	@Override
	public Retorno caminoMinimo(Double coordXi, Double coordYi, Double coordXf, Double coordYf) {
		Retorno ret = new Retorno(Resultado.NO_IMPLEMENTADA);
		Estacion origen = tramos.traerEstacion(new Estacion(coordXi, coordYi, ""));
		Estacion destino = tramos.traerEstacion(new Estacion(coordXf, coordYf, ""));
		if(origen != null && destino != null) {
			ret.resultado = Retorno.Resultado.OK;
			ret = tramos.dijkstraUnico(origen, destino, 0);
		}else {
			ret.resultado = Retorno.Resultado.ERROR_1;
		}
		return ret;
	}

	@Override
	public Retorno dibujarMapa() {
		Retorno ret = new Retorno(Resultado.NO_IMPLEMENTADA);
		ret.valorString = tramos.generarURL();
		System.out.println(ret.valorString);
		ret.resultado = Retorno.Resultado.OK;
		return ret;
	}
}
