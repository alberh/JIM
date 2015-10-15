package org.alberto.interprete;

import org.alberto.interprete.Programa.Estado;

public class Error {

    private static void imprimir(String s) {
        System.err.println(s);
        Programa.estado(Estado.ERROR);
    }

    private static int linea(int error) {
        return Programa.numeroLineaActual();
    }

    // Programa
    public static void alCargarPrograma(String f) {
        imprimir("Error 0: No se pudo abrir el fichero \"" + f + "\".");
    }

    public static void alCargarMacros(String f) {
        imprimir("Error 1: No se pudo leer el fichero de macros \"" + f + "\".");
    }

    public static void alComprobarDirectorio(String d) {
        imprimir("Error 2: \"" + d + "\" no es un directorio.");
    }

    public static void alObtenerListaFicherosMacros() {
        imprimir("Error 3: No se pudo obtener la lista de ficheros de macros.");
    }

    public static void alCrearDirectoriosMacros() {
        imprimir("Error 4: No se pudieron crear los directorios de macros.");
    }

    public static void alComprobarAccesoDirectorio(String s) {
        imprimir("Error 5: No se acceder al directorio \"" + s + "\".");
    }

    public static void deEjecucion() {
        imprimir("Error 6: Ocurrió algún error en el módulo intérprete durante"
                + " la ejecución del programa.");
    }

    // Macro
    public static void deMacroNoDefinida(String id) {
        imprimir("Error 7: La macro \"" + id + "\" no está definida "
                + "en el modelo " + Programa.nombreModelo() + ".");
    }

    public static void enNumeroParametros(String id, int numVariablesEntrada,
            int numParametros) {
        imprimir("Error 8: La macro \"" + id + "\" acepta un máximo de "
                + numVariablesEntrada + " parámetros y ha sido llamada con "
                + numParametros + ".");
    }

    public static void deRecursividadEnMacros(String id) {
        imprimir("Error 9: No se ha podido expandir la macro \"" + id
                + "\" porque contiene llamadas recursivas.");
    }

    // GUI
    public static void alCargarProgramaGUI(String f) {
        imprimir("Error 10: No se pudo abrir el fichero \"" + f + "\".");
    }

    public static void alGuardarFichero(String f) {
        imprimir("Error 11: No se pudo guardar el fichero \"" + f + "\".");
    }

    public static void alGuardarFicheroTemporal() {
        imprimir("Error 12: No se pudo guardar el fichero temporal.");
    }

    // Bucle
    public static void alCerrarBucle(int l) {
        imprimir("Error 13: No se esperaba cierre de bucle en la línea " + l + ".");
    }

    // Configuración
    public static void alCargarConfiguracion(String f) {
        imprimir("Error 14: No se pudo cargar el fichero de configuración \""
                + f + "\".");
    }

    public static void alCrearFicheroConfiguracion(String f) {
        imprimir("Error 15: No se pudo crear el fichero de configuración \""
                + f + "\".");
    }
    
    public static void alGuardarConfiguracion() {
        imprimir("Error 16: No se pudo guardar la configuración.");
    }

    // Analizador léxico
    public static void deCaracterNoReconocido(String s) {
        imprimir("Error 17: Carácter '" + s + "' no reconocido.");
    }

    public static void deDefinicionInterior() {
        imprimir("Error 18: No se puede definir una macro dentro de otra definición de macro.");
    }

    // Analizador sintáctico
    public static void deTokenNoEsperado(String nombre, String descripcion) {
        imprimir("Error 19: No se esperaba el símbolo " + nombre + ". Descripción: " + descripcion);
    }

    public static void deTokenNoEsperado(String descripcion) {
        imprimir("Error 20: No se esperaba un símbolo. Descripción: " + descripcion);
    }

    public static void deESenAnalizadorLexico() {
        imprimir("Error 21: No se pudieron llevar a cabo operaciones de E/S en el analizador léxico.");
    }
}
