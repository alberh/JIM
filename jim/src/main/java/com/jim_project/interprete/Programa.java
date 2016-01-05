package com.jim_project.interprete;

import com.jim_project.interprete.Programa.Objetivo;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.util.Error;
import com.jim_project.interprete.util.Configuracion;
import java.io.File;
import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.jim_project.interprete.util.gestor.GestorAmbitos;
import com.jim_project.interprete.util.gestor.GestorMacros;
import java.util.concurrent.RunnableFuture;

public class Programa {

    /**
     * Define los distintos estados del programa.
     */
    public enum Estado {

        /**
         * El programa ha funcionado correctamente hasta el momento y está listo
         * para su ejecución.
         */
        OK,
        /**
         * Ha ocurrido algún error durante la ejecución.
         */
        ERROR,
        /**
         * Aún no se han especificado los argumentos de entrada del programa,
         * por lo que se encuentra en un estado de no-preparación.
         */
        NO_ARGS
    };

    /**
     * Define las etapas del programa.
     */
    /*
     public enum Etapa {

     /**
     * Etapa inicial, antes de la primera llamada al método {@link Programa#iniciar()}.
     *
     ESPERANDO, CARGANDO_FICHERO, COMPROBANDO_DIRECTORIO_MACROS,
     CARGANDO_MACROS, ANALIZANDO, EXPANDIENDO_MACROS, INTERPRETANDO, TERMINADO
     };
     */
    /**
     * Define los posibles objetivos del programa. {@link Programa}.
     */
    public enum Objetivo {

        /**
         * El objetivo del programa es interpretar el código.
         */
        INTERPRETAR,
        /**
         * El objetivo del programa es expandir el código.
         */
        EXPANDIR
    };

    private final Configuracion _configuracion;
    private ArgumentosPrograma _argumentos;
    private RunnableFuture<String> _worker;

    private String _ficheroEnProceso;
    private final Error _error;
    private Estado _estado;

    private final GestorAmbitos _gestorAmbitos;
    private final GestorMacros _gestorMacros;

    /**
     * Constructor de clase.
     *
     * @param configuracion Referencia a la {@link Configuracion} creada y
     * cargada en {@link JIM} y {@link JIMGui}.
     */
    public Programa(Configuracion configuracion) {
        _configuracion = configuracion;
        _argumentos = null;
        _ficheroEnProceso = null;
        _estado = Estado.NO_ARGS;
        _error = new Error(this);
        _gestorAmbitos = new GestorAmbitos(this);
        _gestorMacros = new GestorMacros(this);
    }

    /**
     * Devuelve los argumentos del programa.
     *
     * @return Los argumentos del programa.
     */
    public ArgumentosPrograma argumentos() {
        return _argumentos;
    }

    /**
     * Define nuevos argumentos de entrada para el programa.
     *
     * @param argumentos Los nuevos argumentos de entrada.
     */
    public void argumentos(ArgumentosPrograma argumentos) {
        _argumentos = argumentos;
        _ficheroEnProceso = argumentos.fichero;
        _estado = Estado.OK;
    }

    /**
     * Devuelve el {@link RunnableFuture} asociado, utilizado para detener la
     * interpretación o la expansión de macros cuando el programa ha sido
     * lanzado en segundo plano.
     *
     * @return
     */
    public RunnableFuture<String> worker() {
        return _worker;
    }

    /**
     * Asigna un nuevo {@link RunnableFuture}.
     *
     * @param worker El nuevo {@link RunnableFuture}.
     */
    public void worker(RunnableFuture<String> worker) {
        _worker = worker;
    }

    /**
     * Devuelve la configuración asociada.
     *
     * @return La configuración asociada.
     */
    public Configuracion configuracion() {
        return _configuracion;
    }

    /**
     * Devuelve la ruta del fichero en proceso.
     *
     * @return La ruta del fichero en proceso.
     */
    public String ficheroEnProceso() {
        return new File(_ficheroEnProceso).getPath();
    }

    /**
     * Asigna la ruta del fichero en proceso.
     *
     * @param ficheroEnProceso La ruta del fichero en proceso.
     */
    public void ficheroEnProceso(String ficheroEnProceso) {
        _ficheroEnProceso = ficheroEnProceso;
    }

    /**
     * Devuelve el estado del programa.
     *
     * @return El estado del programa.
     */
    public Estado estado() {
        return _estado;
    }

    /**
     * Cambia el estado del programa.
     *
     * @param estado El nuevo estado del programa.
     */
    public void estado(Estado estado) {
        _estado = estado;
    }

    /**
     * Comprueba si el estado del programa sigue siendo válido.
     *
     * @return {@code true}, si el estado es {@link Estado#OK}; {@code false} en
     * caso contrario.
     */
    public boolean estadoOk() {
        return _estado == Estado.OK;
    }

    /**
     * Devuelve el objetivo del programa.
     *
     * @return El objetivo del programa.
     */
    public Objetivo objetivo() {
        return _argumentos.objetivo;
    }

    /**
     * Cambia el objetivo del programa.
     *
     * @param objetivo El nuevo objetivo del programa.
     */
    public void objetivo(Objetivo objetivo) {
        _argumentos.objetivo = objetivo;
    }
    /*
     public boolean modoFlexible() {
     return _argumentos.modoFlexible;
     }
     */

    /**
     * Bandera que indica si se permite la ejecución de macros.
     *
     * @return {@code true}, si las macros están permitidas; {@code false}, si
     * no lo están.
     */
    public boolean macrosPermitidas() {
        return _argumentos.macrosPermitidas;
    }

    /**
     * Bandera que indica si la salida detallada está activada.
     *
     * @return {@code true}, si la salida detallada está activada;
     * {@code false}, si no lo está.
     */
    public boolean verbose() {
        return _argumentos.verbose;
    }

    /**
     * Devuelve la instancia de {@link Error} asociada.
     *
     * @return La instancia de {@link Error} asociada.
     */
    public Error error() {
        return _error;
    }

    /**
     * Devuelve el modelo a interpretar.
     *
     * @return El modelo a interpretar.
     */
    public Modelo modelo() {
        return _argumentos.modelo;
    }

    /**
     * Devuelve el nombre del modelo a interpretar.
     *
     * @return El nombre del modelo a interpretar.
     */
    public String nombreModelo() {
        String modelo = _argumentos.modelo.tipo().toString();
        return modelo.charAt(0) + modelo.substring(1).toLowerCase();
    }

    /**
     * Devuelve el gestor de ámbitos asociado.
     *
     * @return El gestor de ámbitos asociado.
     */
    public GestorAmbitos gestorAmbitos() {
        return _gestorAmbitos;
    }

    /**
     * Devuelve el gestor de macros asociado.
     *
     * @return El gestor de macros asociado.
     */
    public GestorMacros gestorMacros() {
        return _gestorMacros;
    }

    /**
     * Pone en marcha el programa, analizando las macros comunes y del modelo,
     * analizando el programa, creando el ámbito raíz e iniciando la
     * interpretación del programa.
     *
     * @return Si el objetivo del programa es interpretar el código, devuelve
     * una cadena vacía.<br/>
     * Si el objetivo es expandir las llamadas a macros, devuelve el código
     * resultado de la expansión.
     * @throws Exception Cuando se llama a este método sin haber especificado
     * los argumentos del programa.
     */
    public String iniciar() throws Exception {
        if (estadoOk()) {
            limpiar();
            _gestorMacros.cargarMacros();
            ficheroEnProceso(_argumentos.fichero);

            if (estadoOk()) {
                ArrayList<String> lineas = new ArrayList<>();

                try (Scanner scanner = new Scanner(new File(_argumentos.fichero))) {
                    while (scanner.hasNextLine()) {
                        lineas.add(scanner.nextLine());
                    }
                } catch (FileNotFoundException ex) {
                    _error.alCargarPrograma(_argumentos.fichero);
                }

                Ambito ambito = _gestorAmbitos.nuevoAmbito(_argumentos.parametros, lineas);

                if (_argumentos.objetivo == Objetivo.INTERPRETAR) {
                    ambito.iniciar();
                } else {
                    return ambito.expandir();
                }
            }
        } else if (_estado == Estado.NO_ARGS) {
            throw new Exception("No se han especificado los argumentos del programa.");
        }

        return "";
    }

    /**
     * Devuelve el valor de la variable de salida del ámbito raíz del programa.
     *
     * @return El valor del resultado de la variable de salida principal.
     */
    public int resultado() {
        return _gestorAmbitos.ambitoRaiz().resultado();
    }

    /**
     * Devuelve la traza del programa.
     *
     * @return La traza del programa.
     */
    public String traza() {
        return _gestorAmbitos.ambitoRaiz().controladorEjecucion().traza();
    }

    /**
     * Limpia el gestor de ámbitos y el de macros, preparando el programa para
     * una nueva ejecución.
     */
    private void limpiar() {
        _gestorAmbitos.limpiar();
        _gestorMacros.limpiar();
    }

    /**
     * Devuelve una cadena con una representaciéon del programa, que contiene
     * todas las líneas del código a interpretar.
     *
     * @return Una cadena con la representación del programa.
     */
    @Override
    public String toString() {
        ArrayList<String> lineas = _gestorAmbitos.ambitoRaiz().controladorEjecucion().lineas();
        StringBuilder sb = new StringBuilder();

        lineas.forEach(
                linea -> sb.append(linea).append("\n")
        );

        return sb.toString();
    }

    /**
     * Devuelve una cadena con una representación más detallada del programa que
     * la devuelta por {@link Programa#toString()}, incluyendo además números de
     * línea y un recuento de líneas final.
     *
     * @return Una cadena con la representación detallada del programa.
     */
    public String toStringDetallado() {
        ArrayList<String> lineas = _gestorAmbitos.ambitoRaiz().controladorEjecucion().lineas();
        int numeroLineas = _gestorAmbitos.ambitoRaiz().controladorEjecucion().numeroLineas();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < lineas.size(); ++i) {
            sb.append(i + 1).append(": ").append(lineas.get(i)).append("\n");
        }
        sb.append(numeroLineas).append(" líneas.").append("\n");

        return sb.toString();
    }
}
