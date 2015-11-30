package com.jim_project.interprete.util;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.Programa.Estado;

public class Error {

    private Programa _programa;

    public Error(Programa programa) {
        _programa = programa;
    }

    // Métodos útiles
    private static void imprimir(String mensaje) {
        imprimir(mensaje, null);
    }
    
    private static void imprimir(String mensaje, Programa programa) {
        System.err.println(mensaje);
        Error.estadoErroneo(programa);;
    }
    
    private static void estadoErroneo(Programa programa) {
        if (programa != null && programa.estadoOk()) {
            programa.estado(Estado.ERROR);
        }
    }

    private void imprimir(String mensaje, int numeroLinea) {
        if (_programa.ficheroEnProceso().equals("jim.tmp")) {
            Error.imprimir("Línea " + numeroLinea + ". " + mensaje, _programa);
        } else {
            Error.imprimir(_programa.ficheroEnProceso() + ":" + numeroLinea + ". " + mensaje, _programa);
        }
    }

    private int numeroLineaActual() {
        return _programa.gestorAmbitos().ambitoActual().controladorEjecucion().numeroLineaActual();
    }

    // Programa
    public void alCargarPrograma(String f) {
        imprimir("Error 0: No se pudo abrir el fichero \"" + f + "\".");
    }

    public void alCargarMacros(String f) {
        imprimir("Error 1: No se pudo leer el fichero de macros \"" + f + "\".");
    }

    public void alComprobarDirectorio(String d) {
        imprimir("Error 2: \"" + d + "\" no es un directorio.");
    }

    public void alObtenerListaFicherosMacros(String d) {
        imprimir("Error 3: No se pudo obtener la lista de ficheros de macros "
                + "del directorio \"" + d + "\".");
    }

    public void alCrearDirectoriosMacros() {
        imprimir("Error 4: No se pudieron crear los directorios de macros.");
    }

    public void alComprobarAccesoDirectorio(String s) {
        imprimir("Error 5: No se acceder al directorio \"" + s + "\".");
    }

    public void deEjecucion() {
        imprimir("Error 6: Ocurrió algún error en el módulo intérprete durante"
                + " la ejecución del programa.");
    }

    // Macro
    public void deMacroNoDefinida(int n, String id) {
        imprimir("Error 7: La macro \"" + id
                + "\" no está definida en el modelo "
                + _programa.nombreModelo() + ".",
                n);
    }

    public void enNumeroParametros(int n, String id,
            int numVariablesEntrada, int numParametros) {
        
        imprimir("Error 8 : La macro \"" + id + "\" acepta un máximo de "
                + numVariablesEntrada + " parámetros y ha sido llamada con "
                + numParametros + ".",
                n);
    }

    public void deRecursividadEnMacros(int n, String id) {
        imprimir("Error 9: No se ha podido expandir la macro \"" + id
                + "\" porque contiene llamadas recursivas.",
                n);
    }
    
    // Main
    public static void deModeloNoValido(String cadena) {
        Error.imprimir("Error X5: No se pudo instanciar el modelo \"" + cadena + "\".");
    }

    // GUI
    public static void alCargarProgramaGUI(String f) {
        Error.imprimir("Error 10: No se pudo abrir el fichero \"" + f + "\".");
    }

    public static void alGuardarFichero(String f) {
        Error.imprimir("Error 11: No se pudo guardar el fichero \"" + f + "\".");
    }

    public static void alGuardarFicheroTemporal() {
        Error.imprimir("Error 12: No se pudo guardar el fichero temporal.");
    }

    // Variable
    public static void alObtenerIndiceDeVariable(String v) {
        imprimir("Error 27: No se pudo obtener el índice de la variable "
                + "\"" + v + "\"."); //, numeroLineaActual());
    }

    public static void deIdentificadorDeVariableVacio() {
        imprimir("Error 24: Nombre de variable vacío.");
    }

    public static void deTipoDeVariableNoValido(String tipo) {
        // Para después del refactor
        imprimir("Error 26: Tipo de variable '" + tipo + "' no válido.");
                // , numeroLineaActual());
    }

    // Bucle
    public void alCerrarBucle(int linea) {
        imprimir("Error 13: No se esperaba cierre de bucle en la línea "
                + linea + ".");
    }

    // Etiqueta
    public static void deIdentificadorDeEtiquetaVacio() {
        imprimir("Error 25: Nombre de etiqueta vacío.");
    }

    public void alObtenerIndiceDeEtiqueta(String e) {
        imprimir("Error 28: No se pudo obtener el índice de la etiqueta "
                + "\"" + e + "\".", numeroLineaActual());
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
    public void deCaracterNoReconocido(int n, String s) {
        imprimir("Error 17: Carácter '" + s + "' no reconocido.", n);
    }

    public void deCaracterNoReconocido(String s) {
        deCaracterNoReconocido(numeroLineaActual(), s);
    }

    public void deDefinicionInterior(int numeroLinea) {
        imprimir("Error 18 en línea: Las definiciones de macros "
                + "no pueden ser anidadas.", numeroLinea);
    }

    // Analizador sintáctico
    public void deTokenNoEsperado(String token, String descripcion) {
        deTokenNoEsperado(numeroLineaActual(), token, descripcion);
    }

    public void deTokenNoEsperado(int numeroLinea, String token, String descripcion) {
        switch (token) {
            case "IDMACRO":
                imprimir("Error 19: Definición o llamada a macro no esperada.",
                        numeroLinea);
                break;

            case "FLECHA":
                imprimir("Error 19: Asignación no esperada.", numeroLinea);
                break;

            case "end-of-file":
                imprimir("Error 19: No se esperaba el final de fichero.", numeroLinea);
                break;

            case "')'":
                imprimir("Error 19: Cierre de llamada a macro no esperado.", numeroLinea);
                break;

            default:
                imprimir("Error 19: No se esperaba el símbolo \"" + token
                        + "\"." /* + Descripción: " + descripcion */,
                        numeroLinea);
                break;
        }
    }

    public void deTokenNoEsperado(String descripcion) {
        deTokenNoEsperado(numeroLineaActual(), descripcion);
    }

    public void deTokenNoEsperado(int numeroLinea, String descripcion) {
        /*
        imprimir("Error 20: No se esperaba un símbolo. Descripción: "
                + descripcion, numeroLinea);
        */
        Error.estadoErroneo(_programa);
    }

    public void deESEnAnalizadorLexico() {
        deESEnAnalizadorLexico(numeroLineaActual());
    }

    public void deESEnAnalizadorLexico(int numeroLinea) {
        imprimir("Error 21: No se pudieron llevar a cabo operaciones de E/S en el analizador léxico.", numeroLinea);
    }

    // Operaciones
    /*
     public void deDivisionPorCero() {
     imprimir("Error 24: División por cero.", Programa.numeroLineaActual());
     }
     */
    // Operaciones extendidas
    public void deSumaValorNoUnidad() {
        imprimir("Error 22: No se puede sumar un valor distinto de la unidad "
                + "sin activar el modo extendido.", numeroLineaActual());
    }

    public void deOperacionEntreVariables(char operador) {
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
