package com.jim_project.interprete.util;

import com.jim_project.interprete.Programa;
import com.jim_project.interprete.Programa.Estado;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.util.gestor.GestorAmbitos;
import java.util.concurrent.RunnableFuture;

/**
 * Clase que ofrece una colección de métodos preparados para mostrar distintos
 * mensajes de error dependiendo del contexto.
 *
 * @author Alberto García González
 */
public class Error {

    private final Programa _programa;

    /**
     * Constructor de clase.
     *
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
        System.err.print(mensaje);
        Error.estadoErroneo(programa);
    }

    private static void estadoErroneo(Programa programa) {
        if (programa != null && programa.estadoOk()) {
            programa.estado(Estado.ERROR);
        }
    }

    private void imprimir(String mensaje, int numeroLinea) {
        if (_programa.estadoOk()) {
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
                }

                linea = gA.ambitoRaiz().controladorEjecucion().numeroLineaActual();
                for (int i = 0; i < profundidadBase - ambito.profundidad(); ++i) {
                    sb.append("   ");
                }
                sb.append("\\-Llamada desde línea ").append(linea).append(".");

                Error.imprimir(sb.toString(), _programa);
            }
        }
    }

    private int numeroLineaActual() {
        return _programa.gestorAmbitos().ambitoActual().controladorEjecucion().numeroLineaActual();
    }

    // Programa
    /**
     * Error al cargar el programa.
     *
     * @param fichero El nombre del fichero.
     */
    public void alCargarPrograma(String fichero) {
        imprimir("Error 0: No se pudo abrir el fichero \"" + fichero + "\".");
    }

    /**
     * Error al cargar macros.
     *
     * @param fichero El nombre del fichero.
     */
    public void alCargarMacros(String fichero) {
        imprimir("Error 1: No se pudo leer el fichero de macros \"" + fichero + "\".");
    }

    /**
     * Error al comprobar directorio.
     *
     * @param directorio El nombre del directorio.
     */
    public void alComprobarDirectorio(String directorio) {
        imprimir("Error 2: \"" + directorio + "\" no es un directorio.");
    }

    /**
     * Error al obtener la lista de ficheros de macros.
     *
     * @param directorio El nombre del directorio.
     */
    public void alObtenerListaFicherosMacros(String directorio) {
        imprimir("Error 3: No se pudo obtener la lista de ficheros de macros "
                + "del directorio \"" + directorio + "\".");
    }

    /**
     * Error al crear los directorios de macros.
     */
    public void alCrearDirectoriosMacros() {
        imprimir("Error 4: No se pudieron crear los directorios de macros.");
    }

    /**
     * Error al comprobar el acceso a un directorio.
     *
     * @param directorio El nombre del directorio.
     */
    public void alComprobarAccesoDirectorio(String directorio) {
        imprimir("Error 5: No se acceder al directorio \"" + directorio + "\".");
    }

    /**
     * Error de ejecución.
     */
    public void deEjecucion() {
        imprimir("Error 6: Ocurrió algún error en el módulo intérprete durante"
                + " la ejecución del programa.");
    }

    // Macro
    /**
     * Error de macro no definida.
     *
     * @param numeroLinea El número de línea del error.
     * @param id El identificador de la macro.
     */
    public void deMacroNoDefinida(int numeroLinea, String id) {
        imprimir("Error 7: La macro " + id + " no ha sido definida.",
                numeroLinea);
    }

    /**
     * Error en el número de parámetros en una llamada a macro.
     *
     * @param numeroLinea El número de línea del error.
     * @param id El identificador de la macro.
     * @param numVariablesEntrada El número de variables de entrada de la macro.
     * @param numParametros El número de parámetros con el que ha sido llamada
     * la macro.
     */
    public void enNumeroParametros(int numeroLinea, String id,
            int numVariablesEntrada, int numParametros) {

        imprimir("Error 8: La macro " + id + " requiere "
                + numVariablesEntrada + " parámetros de entrada y ha sido "
                + "llamada con " + numParametros + ".", numeroLinea);
    }

    // Main
    /**
     * Error de modelo no válido.
     *
     * @param modelo El identificador del modelo.
     */
    public static void deModeloNoValido(String modelo) {
        Error.imprimir("Error 9: No se pudo instanciar el modelo \"" + modelo + "\".");
    }

    /**
     * Error de parámetros no indicados en el fichero.
     *
     * @param fichero El nombre del fichero.
     */
    public static void deParametrosNoIndicadosEnFichero(String fichero) {
        Error.imprimir("Error 10: No se han encontrado argumentos suficientes "
                + "en el fichero \"" + fichero + "\". "
                + "Debe indicar al menos el modelo de computación.");
    }

    /**
     * Error de modificador no válido.
     *
     * @param modificador El carácter correspondiente al modificador.
     */
    public static void deModificadorNoValido(char modificador) {
        Error.imprimir("Error 11: Modificador " + modificador + " no válido.");
    }

    /**
     * Error de parámetro no válido.
     *
     * @param param El parámetro no válido.
     */
    public static void deParametroNoValido(String param) {
        Error.imprimir("Error 12: Parámetro " + param + " no válido. "
                + "Todos los parámetros de entrada del programa deben ser "
                + "numéricos.");
    }

    /**
     * Error de fichero no existente.
     *
     * @param fichero El nombre del fichero.
     */
    public static void deFicheroNoExistente(String fichero) {
        Error.imprimir("Error 13: El fichero \"" + fichero + "\" no existe.");
    }

    // GUI
    /**
     * Error al cargar el programa desde la GUI.
     *
     * @param fichero El nombre del fichero.
     */
    public static void alCargarProgramaGUI(String fichero) {
        Error.imprimir("Error 14: No se pudo abrir el fichero \"" + fichero + "\".");
    }

    /**
     * Error al guardar un fichero desde la GUI.
     *
     * @param fichero El nombre del fichero.
     */
    public static void alGuardarFichero(String fichero) {
        Error.imprimir("Error 15: No se pudo guardar el fichero \"" + fichero + "\".");
    }

    /**
     * Error al guardar el fichero temporal.
     */
    public static void alGuardarFicheroTemporal() {
        Error.imprimir("Error 16: No se pudo guardar el fichero temporal.");
    }

    /**
     * Error de desbordamiento de pila.
     */
    public static void deDesbordamientoDePila() {
        Error.imprimir("Error 17: Desbordamiento de pila.");
    }

    /**
     * Error de {@link RunnableFuture} interrumpido.
     */
    public static void deTrabajadorInterrumpido() {
        Error.imprimir("Error 18: La ejecución finalizó de forma inesperada.");
    }

    // Variable
    /**
     * Error al obtener el índice de una variable.
     *
     * @param variable El identificador de la variable.
     */
    public void alObtenerIndiceDeVariable(String variable) {
        imprimir("Error 19: No se pudo obtener el índice de la variable "
                + "\"" + variable + "\".",
                numeroLineaActual());
    }

    /**
     * Error de tipo de variable no válido.
     *
     * @param tipo El carácter identificativo del tipo de variable.
     */
    public void deTipoDeVariableNoValido(char tipo) {
        imprimir("Error 20: Tipo de variable '" + tipo + "' no válido.",
                numeroLineaActual());
    }

    // Bucle
    /**
     * Error al definir el cierre o final de un bucle.
     *
     * @param linea El número del cierre del bucle.
     */
    public void alDefinirCierreBucle(int linea) {
        imprimir("Error 21: No se esperaba cierre de bucle en la línea "
                + linea + ".");
    }

    // Etiqueta
    /**
     * Error de identificador de etiqueta vacío.
     */
    public static void deIdentificadorDeEtiquetaVacio() {
        imprimir("Error 22: Nombre de etiqueta vacío.");
    }

    /**
     * Error al obtener el índice de una etiqueta.
     *
     * @param etiqueta El identificador de la etiqueta.
     */
    public void alObtenerIndiceDeEtiqueta(String etiqueta) {
        imprimir("Error 23: No se pudo obtener el índice de la etiqueta "
                + "\"" + etiqueta + "\".",
                numeroLineaActual());
    }

    // Configuración
    /**
     * Error al cargar la configuración.
     *
     * @param fichero El nombre del fichero de configuración.
     */
    public static void alCargarConfiguracion(String fichero) {
        imprimir("Error 24: No se pudo cargar el fichero de configuración \""
                + fichero + "\".");
    }

    /**
     * Error al crear el fichero de configuración.
     *
     * @param fichero El nombre del fichero de configuración.
     */
    public static void alCrearFicheroConfiguracion(String fichero) {
        imprimir("Error 25: No se pudo crear el fichero de configuración \""
                + fichero + "\".");
    }

    /**
     * Error al guardar la configuración.
     */
    public static void alGuardarConfiguracion() {
        imprimir("Error 26: No se pudo guardar la configuración.");
    }

    // Acciones
    /**
     * Error de llamadas a macro no permitidas.
     */
    public void deLlamadasAMacroNoPermitidas() {
        imprimir("Error 27: No se permite la ejecución de macros.",
                numeroLineaActual());
    }

    /**
     * Error de valor máximo de un entero superado.
     */
    public void deMaximoEnteroSuperado() {
        imprimir("Error 28: Se ha superado el valor máximo almacenable en una "
                + "variable (" + Integer.MAX_VALUE + ").", numeroLineaActual());
    }

    // Analizador léxico
    /**
     * Error de carácter no reconocido.
     *
     * @param linea El número de línea del error.
     * @param caracter El carácter no reconocido.
     */
    public void deCaracterNoReconocido(int linea, String caracter) {
        imprimir("Error 29: Carácter '" + caracter + "' no reconocido.", linea);
    }

    /**
     * Realiza una llamada a
     * {@link Error#deCaracterNoReconocido(int, java.lang.String)} con el número
     * de línea actual y usnado {@code caracter} como segundo argumento.
     *
     * @param caracter El carácter no reconocido.
     * @see Error#deCaracterNoReconocido(int, java.lang.String)
     */
    public void deCaracterNoReconocido(String caracter) {
        deCaracterNoReconocido(numeroLineaActual(), caracter);
    }

    /**
     * Error de definición de macro interior.
     *
     * @param linea El número de línea del error.
     */
    public void deDefinicionInterior(int linea) {
        imprimir("Error 30: Las definiciones de macros "
                + "no pueden ser anidadas.", linea);
    }

    // Analizador sintáctico
    /**
     * Realiza una llamada a
     * {@link Error#deTokenNoEsperado(int, java.lang.String, java.lang.String)}
     * con el número de línea actual, {@code token} y {@code descripcion}.
     *
     * @param token El tóken leído.
     * @param descripcion La descripción del error.
     */
    public void deTokenNoEsperado(String token, String descripcion) {
        deTokenNoEsperado(numeroLineaActual(), token, descripcion);
    }

    /**
     * Error de tóken no esperado.
     *
     * @param linea El número de línea del error.
     * @param token El tóken leído.
     * @param descripcion La descripción del error.
     */
    public void deTokenNoEsperado(int linea, String token, String descripcion) {
        switch (token) {
            case "IDMACRO":
                imprimir("Error 31: Identificador no reconocido.",
                        linea);
                break;

            case "FLECHA":
                imprimir("Error 32: Asignación no esperada.",
                        linea);
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
                imprimir("Error 33: Error sintáctico.",
                        linea);
                break;
        }
    }

    /**
     * Realiza una llamada a {@link Error#deESEnAnalizadorLexico(int)} con el
     * número de línea actual.
     */
    public void deESEnAnalizadorLexico() {
        deESEnAnalizadorLexico(numeroLineaActual());
    }

    /**
     * Error de entrada/salida en el analizador léxico.
     *
     * @param numeroLinea El número de línea del error.
     */
    public void deESEnAnalizadorLexico(int numeroLinea) {
        imprimir("Error 34: No se pudieron llevar a cabo operaciones de E/S en el analizador léxico.", numeroLinea);
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
