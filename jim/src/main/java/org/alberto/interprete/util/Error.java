package org.alberto.interprete.util;

import org.alberto.interprete.Programa;
import org.alberto.interprete.Programa.Estado;

public class Error {

    private static void imprimir(String s) {
        if (Programa.estadoOk()) {
            System.err.println(s);
            Programa.estado(Estado.ERROR);
        }
    }

    private static void imprimir(String s, int n) {
        if (Programa.ficheroEnProceso().equals("jim.tmp")) {
            imprimir("Línea " + n + ". " + s);
        } else {
            imprimir(Programa.ficheroEnProceso() + ":" + n + ". " + s);
        }
    }

    private static int numeroLineaActual() {
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

    public static void alObtenerListaFicherosMacros(String d) {
        imprimir("Error 3: No se pudo obtener la lista de ficheros de macros "
                + "del directorio \"" + d + "\".");
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
    public static void deMacroNoDefinida(int n, String id) {
        imprimir("Error 7: La macro \"" + id
                + "\" no está definida en el modelo "
                + Programa.nombreModelo() + ".",
                n);
    }

    public static void enNumeroParametros(int n, String id,
            int numVariablesEntrada, int numParametros) {
        imprimir("Error 8 : La macro \"" + id + "\" acepta un máximo de "
                + numVariablesEntrada + " parámetros y ha sido llamada con "
                + numParametros + ".",
                n);
    }

    public static void deRecursividadEnMacros(int n, String id) {
        imprimir("Error 9: No se ha podido expandir la macro \"" + id
                + "\" porque contiene llamadas recursivas.",
                n);
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

    // Variable
    public static void alObtenerIndiceDeVariable(String v) {
        imprimir("Error 27: No se pudo obtener el índice de la variable "
                + "\"" + v + "\".",
                numeroLineaActual());
    }
    
    public static void deIdentificadorDeVariableVacio() {
        imprimir("Error 24: Nombre de variable vacío.");
    }
    
    public static void deTipoDeVariableNoValido(String tipo) {
        // Para después del refactor
        imprimir("Error 26: Tipo de variable '" + tipo + "' no válido.",
                Programa.numeroLineaActual());
    }

    // Bucle
    public static void alCerrarBucle(int linea ){
        imprimir("Error 13: No se esperaba cierre de bucle en la línea "
                + linea + ".");
    }
    
    // Etiqueta
    public static void deIdentificadorDeEtiquetaVacio() {
        imprimir("Error 25: Nombre de etiqueta vacío.");
    }
    
    public static void alObtenerIndiceDeEtiqueta(String e) {
        imprimir("Error 28: No se pudo obtener el índice de la etiqueta "
                + "\"" + e + "\".",
                numeroLineaActual());
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
    public static void deCaracterNoReconocido(int n, String s) {
        imprimir("Error 17: Carácter '" + s + "' no reconocido.", n);
    }

    public static void deCaracterNoReconocido(String s) {
        deCaracterNoReconocido(numeroLineaActual(), s);
    }

    public static void deDefinicionInterior(int n) {
        imprimir("Error 18 en línea: Las definiciones de macros "
                + "no pueden ser anidadas.", 0);
    }

    public static void deDefinicionInterior() {
        deDefinicionInterior(numeroLineaActual());
    }

    // Analizador sintáctico
    public static void deTokenNoEsperado(String token, String descripcion) {
        deTokenNoEsperado(numeroLineaActual(), token, descripcion);
    }

    public static void deTokenNoEsperado(int n, String token, String descripcion) {
        switch (token) {
            case "IDMACRO":
                imprimir("Error 19: Definición o llamada a macro no esperada.",
                        n);
                break;

            case "FLECHA":
                imprimir("Error 19: Asignación no esperada.", n);
                break;

            case "end-of-file":
                imprimir("Error 19: Encontrado final de fichero.", n);
                break;

            case "')'":
                imprimir("Error 19: Cierre de llamada a macro no esperado.", n);
                break;

            default:
                imprimir("Error 19: No se esperaba el símbolo \"" + token
                        + "\"." /* + Descripción: " + descripcion */,
                        n);
                break;
        }
    }

    public static void deTokenNoEsperado(String descripcion) {
        deTokenNoEsperado(numeroLineaActual(), descripcion);
    }

    public static void deTokenNoEsperado(int n, String descripcion) {
        imprimir("Error 20: No se esperaba un símbolo. Descripción: "
                + descripcion,
                n);
    }

    public static void deESEnAnalizadorLexico() {
        deESEnAnalizadorLexico(numeroLineaActual());
    }

    public static void deESEnAnalizadorLexico(int n) {
        imprimir("Error 21: No se pudieron llevar a cabo operaciones de E/S en el analizador léxico.", n);
    }
    
    // Operaciones
    /*
    public static void deDivisionPorCero() {
        imprimir("Error 24: División por cero.", Programa.numeroLineaActual());
    }
    */
    
    // Operaciones extendidas
    public static void deSumaValorNoUnidad() {
        imprimir("Error 22: No se puede sumar un valor distinto de la unidad "
                + "sin activar el modo extendido.", numeroLineaActual());
    }

    public static void deOperacionEntreVariables(char operador) {
        String operacion;
        switch (operador) {
            case '+':
                operacion = "la suma";
                break;

            case '-':
                operacion = "la resta";
                break;

            case '*':
                operacion = "el producto";
                break;

            case '/':
                operacion = "la división";
                break;

            case '%':
                operacion = "el módulo";
                break;

            default:
                operacion = "";
        }

        imprimir("Error 23: No se puede realizar " + operacion + " entre dos "
                + "variables sin activar el modo extendido.",
                numeroLineaActual());
    }
}
