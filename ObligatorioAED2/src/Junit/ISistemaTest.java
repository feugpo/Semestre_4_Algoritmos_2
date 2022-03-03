package Junit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import uy.edu.ort.obli.ISistema;
import uy.edu.ort.obli.Retorno;
import uy.edu.ort.obli.Retorno.Resultado;
import uy.edu.ort.obli.Sistema;


public class ISistemaTest {

	private ISistema sis;

	@Before
	public void setUp() throws Exception {
		sis = new Sistema();
	}

	@Test
	public void testInicializarSistema() {
		Retorno ret;

		ret = sis.inicializarSistema(0);
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.inicializarSistema(-1);
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.inicializarSistema(-10);
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.inicializarSistema(1);
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.inicializarSistema(10);
		assertEquals(Resultado.OK, ret.resultado);
	}

	@Test
	public void testDestruirSistema() {
		Retorno ret;

		ret = sis.destruirSistema();
		assertEquals(Resultado.OK, ret.resultado);
	}

	@Test
	public void testRegistrarUsuario() {
		Retorno ret;
		sis.inicializarSistema(3);

		ret = sis.registrarUsuario("1.111.1111", "Paolo Montero");
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.registrarUsuario("1.111.111--1", "Paolo Montero");
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.registrarUsuario("11111111", "Paolo Montero");
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.registrarUsuario("1-111-111-1", "Paolo Montero");
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.registrarUsuario("1.111.111-1", "Paolo Montero");
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarUsuario("2.222.222-2", "Marcelo Sosa");
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarUsuario("3.333.333-3", "Silvio Mendes");
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarUsuario("4.444.444-4", "Carlos Bueno");
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarUsuario("1.111.111-1", "Paolo Montero");
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.registrarUsuario("2.222.222-2", "Marcelo Sosa");
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.registrarUsuario("3.333.333-3", "Silvio Mendes");
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.registrarUsuario("4.444.444-4", "Carlos Bueno");
		assertEquals(Resultado.ERROR_2, ret.resultado);
	}

	@Test
	public void testBuscarUsuario() {
		Retorno ret;

		sis.inicializarSistema(3);
		sis.registrarUsuario("4.444.444-4", "Carlos Bueno");
		sis.registrarUsuario("2.222.222-2", "Marcelo Sosa");
		sis.registrarUsuario("6.666.666-6", "Ladislao Mazurkiewicz");
		sis.registrarUsuario("1.111.111-1", "Paolo Montero");
		sis.registrarUsuario("3.333.333-3", "Silvio Mendes");
		sis.registrarUsuario("5.555.555-5", "Santiago Garcia");
		sis.registrarUsuario("7.777.777-7", "Hailton Correa de Arruda");
		sis.registrarUsuario("8.888.888-8", "Jose Maria Listorti");
		sis.registrarUsuario("9.999.999-9", "Yayo Guridi");

		ret = sis.buscarUsuario("1.111.1111");
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.buscarUsuario("1.111.111--1");
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.buscarUsuario("11111111");
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.buscarUsuario("1-111-111-1");
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.buscarUsuario("1.111.111-2");
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.buscarUsuario("2.222.222-1");
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.buscarUsuario("0.000.000-0");
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.buscarUsuario("4.444.444-4");
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals("4.444.444-4;Carlos Bueno", ret.valorString);
		assertEquals(1, ret.valorEntero);

		ret = sis.buscarUsuario("2.222.222-2");
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals("2.222.222-2;Marcelo Sosa", ret.valorString);
		assertEquals(2, ret.valorEntero);

		ret = sis.buscarUsuario("5.555.555-5");
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals("5.555.555-5;Santiago Garcia", ret.valorString);
		assertEquals(3, ret.valorEntero);

		ret = sis.buscarUsuario("9.999.999-9");
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals("9.999.999-9;Yayo Guridi", ret.valorString);
		assertEquals(5, ret.valorEntero);
	}

	@Test
	public void testListarUsuarios() {
		Retorno ret;

		sis.inicializarSistema(3);
		sis.registrarUsuario("4.444.444-4", "Carlos Bueno");
		sis.registrarUsuario("2.222.222-2", "Marcelo Sosa");
		sis.registrarUsuario("6.666.666-6", "Ladislao Mazurkiewicz");
		sis.registrarUsuario("1.111.111-1", "Paolo Montero");
		sis.registrarUsuario("3.333.333-3", "Silvio Mendes");
		sis.registrarUsuario("5.555.555-5", "Santiago Garcia");
		sis.registrarUsuario("7.777.777-7", "Hailton Correa de Arruda");
		sis.registrarUsuario("8.888.888-8", "Jose Maria Listorti");
		sis.registrarUsuario("9.999.999-9", "Yayo Guridi");

		ret = sis.listarUsuarios();
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals(
				"1.111.111-1;Paolo Montero|2.222.222-2;Marcelo Sosa|3.333.333-3;Silvio Mendes|4.444.444-4;Carlos Bueno|5.555.555-5;Santiago Garcia|6.666.666-6;Ladislao Mazurkiewicz|7.777.777-7;Hailton Correa de Arruda|8.888.888-8;Jose Maria Listorti|9.999.999-9;Yayo Guridi",
				ret.valorString);
	}

	@Test
	public void testRegistrarEstacion() {
		Retorno ret;

		sis.inicializarSistema(3);
		ret = sis.registrarUsuario("4.444.444-4", "Carlos Bueno");

		ret = sis.registrarEstacion(1.0, 1.0, "Campeon del Siglo");
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarEstacion(2.0, 2.0, "Cancha Barrial");
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarEstacion(1.0, 1.0, "Estadio similar al Campeon del Siglo");
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.registrarEstacion(2.0, 2.0, "Estadio similar al Campeon del Siglo");
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.registrarEstacion(3.0, 3.0, "Abraham Paladino");
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarEstacion(4.0, 4.0, "Parque Viera");
		assertEquals(Resultado.ERROR_1, ret.resultado);
	}

	@Test
	public void testRegistrarTramo() {
		Retorno ret;

		sis.inicializarSistema(3);
		ret = sis.registrarUsuario("4.444.444-4", "Carlos Bueno");

		sis.registrarEstacion(1.0, 1.0, "Campeon del Siglo");
		sis.registrarEstacion(2.0, 2.0, "Cancha Barrial");
		sis.registrarEstacion(3.0, 3.0, "Abraham Paladino");

		ret = sis.registrarTramo(1.0, 1.0, 2.0, 2.0, -1, 10);
		assertEquals(Resultado.ERROR_1, ret.resultado);

		ret = sis.registrarTramo(1.0, 1.0, 2.0, 2.0, 10, -1);
		assertEquals(Resultado.ERROR_2, ret.resultado);

		ret = sis.registrarTramo(1.0, 1.0, 4.0, 4.0, 10, 10);
		assertEquals(Resultado.ERROR_3, ret.resultado);

		ret = sis.registrarTramo(4.0, 4.0, 1.0, 1.0, 10, 10);
		assertEquals(Resultado.ERROR_3, ret.resultado);

		ret = sis.registrarTramo(4.0, 4.0, 5.0, 5.0, 10, 10);
		assertEquals(Resultado.ERROR_3, ret.resultado);

		ret = sis.registrarTramo(1.0, 1.0, 2.0, 2.0, 10, 10);
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarTramo(1.0, 1.0, 3.0, 3.0, 10, 10);
		assertEquals(Resultado.OK, ret.resultado);

		ret = sis.registrarTramo(2.0, 2.0, 3.0, 3.0, 10, 10);
		assertEquals(Resultado.OK, ret.resultado);

	}

	private int countOccurrences(String pattern, String text) {
		Matcher m = Pattern.compile(pattern).matcher(text);
		int count = 0, pos = 0;
		while (m.find(pos)) {
			count++;
			pos = m.start() + 1;
		}
		return count;
	}

	@Test
	public void testEvacuacionEmergencia() {
		Retorno ret;

		creacionMapa();

		ret = sis.evacuacionEmergencia(1.0, 1.0, 5);
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals(1, countOccurrences("[|]", ret.valorString)); // Si hay 1 pipe, hay 2 estaciones.
		assertTrue(ret.valorString.contains("Cancha Barrial"));
		assertTrue(ret.valorString.contains("Parque Viera"));

		ret = sis.evacuacionEmergencia(1.0, 1.0, 14);
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals(5, countOccurrences("[|]", ret.valorString)); // Si hay 5 pipe, hay 6 estaciones.
		assertTrue(ret.valorString.contains("Cancha Barrial"));
		assertTrue(ret.valorString.contains("Abraham Paladino"));
		assertTrue(ret.valorString.contains("Parque Viera"));
		assertTrue(ret.valorString.contains("Estadio Charrua"));
		assertTrue(ret.valorString.contains("Parque Capurro"));
		assertTrue(ret.valorString.contains("Belvedere"));

		ret = sis.evacuacionEmergencia(1.0, 1.0, 1891);
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals(9, countOccurrences("[|]", ret.valorString)); // Si hay 9 pipe, hay 10 estaciones.
		assertTrue(ret.valorString.contains("Cancha Barrial"));
		assertTrue(ret.valorString.contains("Abraham Paladino"));
		assertTrue(ret.valorString.contains("Parque Viera"));
		assertTrue(ret.valorString.contains("Estadio Charrua"));
		assertTrue(ret.valorString.contains("Parque Capurro"));
		assertTrue(ret.valorString.contains("Parque Saroldi"));
		assertTrue(ret.valorString.contains("Obdulio Varela"));
		assertTrue(ret.valorString.contains("Belvedere"));
		assertTrue(ret.valorString.contains("Troccoli"));
		assertTrue(ret.valorString.contains("Alberto Supicci"));

	}

	@Test
	public void testCaminoMinimo() {
		Retorno ret;

		creacionMapa();

		ret = sis.caminoMinimo(1.0, 1.0, 7.0, 7.0);
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals(23, ret.valorEntero);
		assertEquals("1.0;1.0;2.0;2.0;Campeon del Siglo_Cancha Barrial_1|"
				+ "2.0;2.0;3.0;3.0;Cancha Barrial_Abraham Paladino_1|"
				+ "3.0;3.0;6.0;6.0;Abraham Paladino_Parque Capurro_1|"
				+ "6.0;6.0;5.0;5.0;Parque Capurro_Estadio Charrua_1|"
				+ "5.0;5.0;8.0;8.0;Estadio Charrua_Obdulio Varela_1|"
				+ "8.0;8.0;7.0;7.0;Obdulio Varela_Parque Saroldi_1", ret.valorString);

	}

	@Test
	public void testCaminoMinimoComplejo() {
		Retorno ret;

		creacionMapa();
		sis.registrarTramo(1.0, 1.0, 2.0, 2.0, 4, 4);
		sis.registrarTramo(1.0, 1.0, 2.0, 2.0, 6, 6);
		sis.registrarTramo(9.0, 9.0, 8.0, 8.0, 7, 7);
		sis.registrarTramo(9.0, 9.0, 8.0, 8.0, 3, 3);

		ret = sis.caminoMinimo(1.0, 1.0, 7.0, 7.0);
		assertEquals(Resultado.OK, ret.resultado);
		assertEquals(21, ret.valorEntero);
		assertEquals("1.0;1.0;2.0;2.0;Campeon del Siglo_Cancha Barrial_1|"
				+ "2.0;2.0;3.0;3.0;Cancha Barrial_Abraham Paladino_1|"
				+ "3.0;3.0;6.0;6.0;Abraham Paladino_Parque Capurro_1|" + "6.0;6.0;9.0;9.0;Parque Capurro_Belvedere_1|"
				+ "9.0;9.0;8.0;8.0;Belvedere_Obdulio Varela_1|" + "8.0;8.0;7.0;7.0;Obdulio Varela_Parque Saroldi_1",
				ret.valorString);

	}

	private void creacionMapa() {
		sis.inicializarSistema(11);
		sis.registrarUsuario("4.444.444-4", "Carlos Bueno");

		sis.registrarEstacion(1.0, 1.0, "Campeon del Siglo");
		sis.registrarEstacion(2.0, 2.0, "Cancha Barrial");
		sis.registrarEstacion(3.0, 3.0, "Abraham Paladino");
		sis.registrarEstacion(4.0, 4.0, "Parque Viera");
		sis.registrarEstacion(5.0, 5.0, "Estadio Charrua");
		sis.registrarEstacion(6.0, 6.0, "Parque Capurro");
		sis.registrarEstacion(7.0, 7.0, "Parque Saroldi");
		sis.registrarEstacion(8.0, 8.0, "Obdulio Varela");
		sis.registrarEstacion(9.0, 9.0, "Belvedere");
		sis.registrarEstacion(10.0, 10.0, "Troccoli");
		sis.registrarEstacion(11.0, 11.0, "Alberto Supicci");

		sis.registrarTramo(1.0, 1.0, 2.0, 2.0, 5, 5);
		sis.registrarTramo(1.0, 1.0, 4.0, 4.0, 3, 3);
		sis.registrarTramo(2.0, 2.0, 3.0, 3.0, 1, 1);
		sis.registrarTramo(2.0, 2.0, 3.0, 3.0, 1, 1);
		sis.registrarTramo(3.0, 3.0, 6.0, 6.0, 2, 2);
		sis.registrarTramo(3.0, 3.0, 11.0, 11.0, 19, 19);
		sis.registrarTramo(4.0, 4.0, 5.0, 5.0, 14, 14);
		sis.registrarTramo(5.0, 5.0, 6.0, 6.0, 6, 6);
		sis.registrarTramo(5.0, 5.0, 8.0, 8.0, 1, 1);
		sis.registrarTramo(6.0, 6.0, 9.0, 9.0, 3, 3);
		sis.registrarTramo(7.0, 7.0, 8.0, 8.0, 8, 8);
		sis.registrarTramo(8.0, 8.0, 9.0, 9.0, 5, 5);
		sis.registrarTramo(9.0, 9.0, 10.0, 10.0, 4, 4);
	}

	@Test
	public void testDibujarMapa() {
		Retorno ret;
		
		sis.inicializarSistema(11);
		sis.registrarUsuario("4.444.444-4", "Carlos Bueno");

		sis.registrarEstacion(-34.7968596, -56.067273, "Campeon del Siglo");
		sis.registrarEstacion(-34.8844797, -56.1587727, "Cancha Barrial");
		sis.registrarEstacion(-34.8913514, -56.1865854, "Palacio Legislativo");

		sis.registrarTramo(-34.7968596, -56.067273, -34.8844797, -56.1587727, 1, 1);
		sis.registrarTramo(-34.8844797, -56.1587727, -34.8913514, -56.1865854, 1, 1);
		sis.registrarTramo(-34.8913514, -56.1865854, -34.7968596, -56.067273, 1, 1);

		ret = sis.dibujarMapa();
		assertEquals(Resultado.OK, ret.resultado);
		
	}

}
