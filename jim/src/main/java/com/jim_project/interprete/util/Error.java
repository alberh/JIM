package com.jim_project.interprete.util;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.Programa.Estado;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.util.gestor.GestorAmbitos;

/**
 * Clase que ofrece una colección de métodos preparados para mostrar distintos
 * mensajes de error dependiendo del contexto.
 * @author Alberto García González
 */
public class Error {

    private final Programa _programa;

    /**
     * Constructor de clase.
     * @param programa Referencia al programa en ejecución.
     */
    public Error(Programa programa) {
        _programa = programa;
    }

    // Métodos útiles
    private static void imprimir(String mensaje) {
        imprimir(mensaje, null);
    }

    private static void imprimir(String mensaje, Programa programa) {
        System.err.println(mensaje);
        Error.estadoErroneo(programa);
    }

    private static void estadoErroneo(Programa programa) {
        if (programa != null && programa.estadoOk()) {
            programa.estado(Estado.ERROR);
        }
    }

    private void imprimir(String mensaje, int numeroLinea) {
        GestorAmbitos gA = _programa.gestorAmbitos();
        Ambito ambito = gA.ambitoActual();

        if (ambito == gA.ambitoRaiz()) {
            Error.imprimir("Línea " + numeroLinea + ". " + mensaje, _programa);
        } else {
            StringBuilder sb = new StringBuilder();

            sb.append("Error en macro ").append(ambito.macroAsociada().id())
                    .append(", línea ").append(numeroLinea)
                    .append(", definida en ").append(ambito.macroAsociada().definidaEn())
                    .append(":\n")
                    .append("   ").append(mensaje).append("\n");

            Ambito ambitoPadre = gA.ambitoPadre(ambito.profundidad());
            String idMacroPadre;
            String ficheroMacro;
            int profundidadBase = ambito.profundidad() + 1;
            int linea;
            while (ambitoPadre != gA.ambitoRaiz()) {
                //puntos(ambito.profundidad(), sb);
                idMacroPadre = ambitoPadre.macroAsociada().id();
                ficheroMacro = ambitoPadre.macroAsociada().definidaEn();
                linea = ambitoPadre.controladorEjecucion().numeroLineaActual();

                for (int i = 0; i < profundidadBase - ambito.profundidad(); ++i) {
                    sb.append("   ");
                }
                sb.append("\\-Llamada desde macro ").append(idMacroPadre)
                        .append(", línea ").append(linea)
                        .append(", definida en ").append(ficheroMacro)
                        .append(".\n");

                ambito = ambitoPadre;
                ambitoPadre = gA.ambitoPadre(ambito.profundidad());
                /*
                 puntos(ambito, sb);
                 sb.append("Error en " + ambito.macroAsociada().id() + ", línea "
                 + ambito.controladorEjecucion().numeroLineaActual());
                 */
            }

            linea = gA.ambitoRaiz().controladorEjecucion().numeroLineaActual();
            for (int i = 0; i < profundidadBase - ambito.profundidad(); ++i) {
                sb.append("   ");
            }
            sb.append("\\-Llamada desde línea ").append(linea).append(".");

            Error.imprimir(sb.toString(), _programa);
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
    public void deMacroNoDefinida(int numeroLinea, String id) {
        imprimir("Error 7: La macro " + id + " no ha sido definida.",
                numeroLinea);
    }

    public void enNumeroParametros(int numeroLinea, String id,
            int numVariablesEntrada, int numParametros) {

        imprimir("Error 8 : La macro " + id + " requiere "
                + numVariablesEntrada + " parámetros de entrada y ha sido "
                + "llamada con " + numParametros + ".", numeroLinea);
    }

    public void deRecursividadEnMacros(int n, String id) {
        imprimir("Error 9: No se ha podido expandir la macro \"" + id
                + "\" porque contiene llamadas recursivas.",
                n);
    }

    // Main
    public static void deModeloNoValido(String modelo) {
        Error.imprimir("Error 10: No se pudo instanciar el modelo \"" + modelo + "\".");
    }

    public static void deParametrosNoIndicados() {
        Error.imprimir("Error 11: Debe indicar al menos el modelo de computación "
                + "para iniciar el programa.");
    }

    public static void deParametrosNoIndicadosEnFichero(String fichero) {
        Error.imprimir("Error 12: No se han encontrado argumentos suficientes "
                + "en el fichero \"" + fichero + "\". "
                + "Debe indicar al menos el modelo de computación.");
    }

    public static void deModificadorNoValido(char modificador) {
        Error.imprimir("Error 13: Modificador " + modificador + " no válido.");
    }

    public static void deParametroNoValido(String param) {
        Error.imprimir("Error 14: Parámetro " + param + " no válido. "
                + "Todos los parámetros de entrada del programa deben ser "
                + "numéricos.");
    }

    public static void deFicheroNoExistente(String f) {
        Error.imprimir("Error 15: El fichero \"" + f + "\" no existe.");
    }

    // GUI
    public static void alCargarProgramaGUI(String f) {
        Error.imprimir("Error 16: No se pudo abrir el fichero \"" + f + "\".");
    }

    public static void alGuardarFichero(String f) {
        Error.imprimir("Error 17: No se pudo guardar el fichero \"" + f + "\".");
    }

    public static void alGuardarFicheroTemporal() {
        Error.imprimir("Error 18: No se pudo guardar el fichero temporal.");
    }

    public static void deDesbordamientoDePila() {
        Error.imprimir("Error 19: Desbordamiento de pila.");
    }

    public static void deTrabajadorInterrumpido() {
        Error.imprimir("Error 20: La ejecución finalizó de forma inesperada.");
    }

    // Variable
    public static void alObtenerIndiceDeVariable(String v) {
        imprimir("Error 21: No se pudo obtener el índice de la variable "
                + "\"" + v + "\"."); //, numeroLineaActual());
    }
    /*
     public static void deIdentificadorDeVariableVacio() {
     // imprimir("Error 24: Nombre de variable vacío.");
     }
     */

    public static void deTipoDeVariableNoValido(char tipo) {
        // Para después del refactor
        imprimir("Error 22: Tipo de variable '" + tipo + "' no válido.");
        // , numeroLineaActual());
    }

    // Bucle
    public void alCerrarBucle(int linea) {
        imprimir("Error 23: No se esperaba cierre de bucle en la línea "
                + linea + ".");
    }

    // Etiqueta
    public static void deIdentificadorDeEtiquetaVacio() {
        imprimir("Error 24: Nombre de etiqueta vacío.");
    }

    public void alObtenerIndiceDeEtiqueta(String e) {
        imprimir("Error 25: No se pudo obtener el índice de la etiqueta "
                + "\"" + e + "\".", numeroLineaActual());
    }

    // Configuración
    public static void alCargarConfiguracion(String f) {
        imprimir("Error 26: No se pudo cargar el fichero de configuración \""
                + f + "\".");
    }

    public static void alCrearFicheroConfiguracion(String f) {
        imprimir("Error 27: No se pudo crear el fichero de configuración \""
                + f + "\".");
    }

    public static void alGuardarConfiguracion() {
        imprimir("Error 28: No se pudo guardar la configuración.");
    }

    // Acciones
    public void deLlamadasAMacroNoPermitidas() {
        imprimir("Error 29: No se permite la ejecución de macros.",
                numeroLineaActual());
    }

    public void deMaximoEnteroSuperado() {
        imprimir("Error 30: Se ha superado el valor máximo almacenable en una "
                + "variable (" + Integer.MAX_VALUE + ").", numeroLineaActual());
    }

    // Analizador léxico
    public void deCaracterNoReconocido(int n, String s) {
        imprimir("Error 31: Carácter '" + s + "' no reconocido.", n);
    }

    public void deCaracterNoReconocido(String s) {
        deCaracterNoReconocido(numeroLineaActual(), s);
    }

    public void deDefinicionInterior(int numeroLinea) {
        imprimir("Error 32: Las definiciones de macros "
                + "no pueden ser anidadas.", numeroLinea);
    }

    // Analizador sintáctico
    public void deTokenNoEsperado(String token, String descripcion) {
        deTokenNoEsperado(numeroLineaActual(), token, descripcion);
    }

    public void deTokenNoEsperado(int numeroLinea, String token, String descripcion) {
        switch (token) {
            case "IDMACRO":
                imprimir("Error 33: Identificador no reconocido.",
                        numeroLinea);
                break;

            case "FLECHA":
                imprimir("Error 34: Asignación no esperada.",
                        numeroLinea);
                break;
            /*
             case "end-of-file":
             imprimir("Error 19: No se esperaba el final de fichero.", numeroLinea);
             break;
             
             case "')'":
             imprimir("Error 35: Cierre de llamada a macro no esperado.",
             numeroLinea);
             break;
             */
            default:
                imprimir("Error 19: Error sintáctico.",
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
        imprimir("Error 36: No se pudieron llevar a cabo operaciones de E/S en el analizador léxico.", numeroLinea);
    }

    // Operaciones
    /*
     public void deDivisionPorCero() {
     imprimir("Error 24: División por cero.", Programa.numeroLineaActual());
     }
     */
    // Operaciones del modo flexible
    /*
     public void deOperacionValorNoUnidad(char operador) {
     imprimir("Error 22: No se puede operar con un valor distinto de la unidad "
     + "sin activar el modo flexible.", numeroLineaActual());
     }

     public void deOperacionYAsignacionDiferente() {
     imprimir("Error 2X: No se puede operar con una variable distinta a la "
     + "variable destino de la asignación sin activar el modo flexible.",
     numeroLineaActual());
     }

     public void deOperadorNoPermitido() {
     imprimir("Error 3X: Operador no permitido sin activar el modo flexible.",
     numeroLineaActual());
     }

     public void deOperacionEntreVariables() {
     imprimir("Error 23: No se puede realizar una operación entre dos variables "
     + "sin activar el modo flexible.",
     numeroLineaActual());
     }

     public void deOperacionNoPermitida() {
     imprimir("Error 2R: Operación no permitida sin activar el modo flexible.",
     numeroLineaActual());
     }

     public void deAsignacionNoPermitida() {
     imprimir("Error 3R: Asignación no permitida sin activar el modo flexible.",
     numeroLineaActual());
     }
     */
}
