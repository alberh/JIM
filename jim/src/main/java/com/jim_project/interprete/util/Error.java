package com.jim_project.interprete.util;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.Programa.Estado;

// Meter dentro de programa y fuera estáticos
// programa().error().deBlahBlah();
@Deprecated
public class Error {

    private Programa _programa;
    
    public Error(Programa programa) {
        _programa = programa;
    }
    
    // Métodos útiles
    private void imprimir(String s) {
        if (_programa.estadoOk()) {
            System.err.println(s);
            _programa.estado(Estado.ERROR);
        }
    }

    private void imprimir(String s, int n) {
        if (_programa.ficheroEnProceso().equals("jim.tmp")) {
            imprimir("Línea " + n + ". " + s);
        } else {
            imprimir(_programa.ficheroEnProceso() + ":" + n + ". " + s);
        }
    }

    private int numeroLineaActual() {
        return 0; //_programa.numeroLineaActual();
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

    // GUI
    public static void alCargarProgramaGUI(String f) {
        //imprimir("Error 10: No se pudo abrir el fichero \"" + f + "\".");
    }

    public void alGuardarFichero(String f) {
        imprimir("Error 11: No se pudo guardar el fichero \"" + f + "\".");
    }

    public void alGuardarFicheroTemporal() {
        imprimir("Error 12: No se pudo guardar el fichero temporal.");
    }

    // Variable
    public void alObtenerIndiceDeVariable(String v) {
        imprimir("Error 27: No se pudo obtener el índice de la variable "
                + "\"" + v + "\".",
                numeroLineaActual());
    }
    
    public void deIdentificadorDeVariableVacio() {
        imprimir("Error 24: Nombre de variable vacío.");
    }
    
    public void deTipoDeVariableNoValido(String tipo) {
        // Para después del refactor
        imprimir("Error 26: Tipo de variable '" + tipo + "' no válido.",
                0 /*_programa.numeroLineaActual()*/);
    }

    // Bucle
    public void alCerrarBucle(int linea ){
        imprimir("Error 13: No se esperaba cierre de bucle en la línea "
                + linea + ".");
    }
    
    // Etiqueta
    public void deIdentificadorDeEtiquetaVacio() {
        imprimir("Error 25: Nombre de etiqueta vacío.");
    }
    
    public void alObtenerIndiceDeEtiqueta(String e) {
        imprimir("Error 28: No se pudo obtener el índice de la etiqueta "
                + "\"" + e + "\".",
                numeroLineaActual());
    }

    // Configuración
    public void alCargarConfiguracion(String f) {
        imprimir("Error 14: No se pudo cargar el fichero de configuración \""
                + f + "\".");
    }

    public void alCrearFicheroConfiguracion(String f) {
        imprimir("Error 15: No se pudo crear el fichero de configuración \""
                + f + "\".");
    }

    public void alGuardarConfiguracion() {
        imprimir("Error 16: No se pudo guardar la configuración.");
    }

    // Analizador léxico
    public void deCaracterNoReconocido(int n, String s) {
        imprimir("Error 17: Carácter '" + s + "' no reconocido.", n);
    }

    public void deCaracterNoReconocido(String s) {
        deCaracterNoReconocido(numeroLineaActual(), s);
    }

    public void deDefinicionInterior(int n) {
        imprimir("Error 18 en línea: Las definiciones de macros "
                + "no pueden ser anidadas.", 0);
    }

    public void deDefinicionInterior() {
        deDefinicionInterior(numeroLineaActual());
    }

    // Analizador sintáctico
    public void deTokenNoEsperado(String token, String descripcion) {
        deTokenNoEsperado(numeroLineaActual(), token, descripcion);
    }

    public void deTokenNoEsperado(int n, String token, String descripcion) {
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

    public void deTokenNoEsperado(String descripcion) {
        deTokenNoEsperado(numeroLineaActual(), descripcion);
    }

    public void deTokenNoEsperado(int n, String descripcion) {
        imprimir("Error 20: No se esperaba un símbolo. Descripción: "
                + descripcion,
                n);
    }

    public void deESEnAnalizadorLexico() {
        deESEnAnalizadorLexico(numeroLineaActual());
    }

    public void deESEnAnalizadorLexico(int n) {
        imprimir("Error 21: No se pudieron llevar a cabo operaciones de E/S en el analizador léxico.", n);
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
