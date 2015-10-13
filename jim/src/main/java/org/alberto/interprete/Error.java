package org.alberto.interprete;

import org.alberto.interprete.Programa.Estado;

public class Error {

    private static void imprimir(String s) {
        System.err.println(s);
        Programa.estado(Estado.ERROR);
    }

    // Programa
    public static void alCargarPrograma(String f) {
        imprimir("Error 0: El fichero \"" + f + "\" no existe.");
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

    // Errores de analizador léxico
    // Errores de analizador sintáctico
    // Errores de analizador semántico / acciones
    // Errores previo
    // Errores expansión macros
    // Errores ejecución
}
