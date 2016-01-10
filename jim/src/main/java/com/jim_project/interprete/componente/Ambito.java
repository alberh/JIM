package com.jim_project.interprete.componente;

import com.jim_project.interprete.parser.ControladorEjecucion;
import java.util.ArrayList;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.gestor.GestorAmbitos;
import com.jim_project.interprete.gestor.GestorEtiquetas;
import com.jim_project.interprete.gestor.GestorBucles;
import com.jim_project.interprete.gestor.GestorLlamadasAMacro;
import com.jim_project.interprete.gestor.GestorVariables;
import java.util.Arrays;

/**
 * Clase que abstrae el concepto de ámbito de ejecución. Mantiene información
 * sobre las variables, bucles, etiquetas y llamadas a macros que se dan en el
 * ámbito que representa y se encarga de su gestión y puesta en ejecución.
 *
 * @author Alberto García González
 */
public class Ambito extends Componente {

    private Programa _programa;
    private ControladorEjecucion _controladorEjecucion;

    private GestorVariables _gestorVariables;
    private GestorBucles _gestorBucles;
    private GestorEtiquetas _gestorEtiquetas;
    private GestorLlamadasAMacro _gestorLlamadasAMacro;

    private String[] _parametrosEntrada;
    private Macro _macroAsociada;
    private int _profundidad;

    /**
     * Constructor de clase.
     *
     * @param programa Referencia al programa que contiene el
     * {@link GestorAmbitos} al que pertenece este ámbito.
     * @param parametrosEntrada Los parámetros de entrada del ámbito.
     * @param macroAsociada La macro asociada al ámbito.
     * @param profundidad La profundidad del ámbito.
     */
    public Ambito(Programa programa,
            String[] parametrosEntrada,
            Macro macroAsociada,
            int profundidad) {

        this(programa, parametrosEntrada, profundidad);

        ArrayList<String> lineas = new ArrayList<>(
                Arrays.asList(macroAsociada.cuerpo().split("[\n\r]+"))
        );

        _controladorEjecucion = new ControladorEjecucion(this, lineas);
        _macroAsociada = macroAsociada;
    }

    /**
     * Constructor de clase.
     *
     * @param programa Referencia al programa que contiene el
     * {@link GestorAmbitos} al que pertenece este ámbito.
     * @param parametrosEntrada Los parámetros de entrada del ámbito.
     * @param lineas Las líneas del programa a ejecutar.
     */
    public Ambito(Programa programa,
            String[] parametrosEntrada,
            ArrayList<String> lineas) {

        this(programa, parametrosEntrada, 0);

        _controladorEjecucion = new ControladorEjecucion(this, lineas);
        _macroAsociada = null;
    }

    private Ambito(Programa programa,
            String[] parametrosEntrada,
            int profundidad) {

        super(programa.ficheroEnProceso(), programa.gestorAmbitos());

        _programa = programa;
        _parametrosEntrada = parametrosEntrada;
        _profundidad = profundidad;

        _gestorVariables = new GestorVariables(this);
        _gestorBucles = new GestorBucles(this);
        _gestorEtiquetas = new GestorEtiquetas(this);
        _gestorLlamadasAMacro = new GestorLlamadasAMacro(this);
    }

    /**
     * Devuelve una referencia al controlador de ejecución asociado al ámbito.
     *
     * @return El {@link ControladorEjecucion} del ámbito.
     */
    public ControladorEjecucion controladorEjecucion() {
        return _controladorEjecucion;
    }

    /**
     * Devuelve una referencia al gestor de variables del ámbito.
     *
     * @return El {@link GestorVariables} del ámbito.
     */
    public GestorVariables gestorVariables() {
        return _gestorVariables;
    }

    /**
     * Devuelve una referencia al gestor de bucles del ámbito.
     *
     * @return El {@link GestorBucles} del ámbito.
     */
    public GestorBucles gestorBucles() {
        return _gestorBucles;
    }

    /**
     * Devuelve una referencia al gestor de etiquetas del ámbito.
     *
     * @return El {@link GestorEtiquetas} del ámbito.
     */
    public GestorEtiquetas gestorEtiquetas() {
        return _gestorEtiquetas;
    }

    /**
     * Devuelve una referencia al gestor de llamadas a macro del ámbito.
     *
     * @return El {@link GestorLlamadasAMacro} del ámbito.
     */
    public GestorLlamadasAMacro gestorLlamadasAMacro() {
        return _gestorLlamadasAMacro;
    }

    /**
     * Devuelve una referencia al programa al que pertenece el gestor de ámbitos
     * que contiene este ámbito.
     *
     * @return El {@link Programa} cuyo {@link GestorAmbitos} contiene este
     * ámbito.
     */
    public Programa programa() {
        return _programa;
    }

    /**
     * Devuelve los parámetros de entrada del ámbito.
     *
     * @return Los parámetros de entrada del ámbito.
     */
    public String[] parametrosEntrada() {
        return _parametrosEntrada;
    }

    /**
     * Devuelve la macro asociada al ámbito.
     *
     * @return La macro asociada al ámbito.
     */
    public Macro macroAsociada() {
        return _macroAsociada;
    }

    /**
     * Devuelve la profundad del ámbito.
     *
     * @return La profundidad del ámbito.
     */
    public int profundidad() {
        return _profundidad;
    }

    /**
     * Método bandera que sirve para comprobar si el ámbito tiene una macro
     * asociada. Un ámbito tiene macro asociada si ha sido creado para
     * representar el espacio de ejecución de una macro. En el caso del ámbito
     * raíz, que ejecuta el código del programa, el ámbito no tiene macro
     * asociada.
     *
     * @return true, si el ámbito tiene macro asociada; false, en caso
     * contrario.
     */
    public boolean tieneMacroAsociada() {
        return _macroAsociada != null;
    }

    /**
     * Pone en marcha la ejecución del ámbito a través de su
     * {@link ControladorEjecucion}.
     */
    public void iniciar() {
        _controladorEjecucion.iniciar(_parametrosEntrada);
    }

    /**
     * Expande las llamadas a macro del código o macro asociada al ámbito,
     * haciendo uso de su {@link ControladorEjecucion}.
     *
     * @return El resultante tras la expansión de macros.
     */
    public String expandir() {
        return _controladorEjecucion.expandir();
    }

    /**
     * Devuelve una cadena que representa el estado de la memoria del ámbito. La
     * cadena de salida tiene la siguiente forma:<br>
     * (K, &lt;X<sub>1</sub> = V<sub>1</sub>, ..., X<sub>N</sub> = V<sub>N</sub>,
     * Z<sub>1</sub> = V<sub>N+1</sub>, ..., Z<sub>M</sub> = V<sub>N+M</sub>, Y
     * = V<sub>Y</sub>&gt;),<br>
     * donde K el siguiente número de línea a ejecutar y cada una de las
     * asignaciones de la serie representan el valor de cada variable de
     * entrada, local y de salida en el momento en el que se realiza la
     * consulta.
     *
     * @return Una cadena que representa el estado de la memoria del ámbito.
     */
    public String estadoMemoria() {
        boolean comaAlFinal = false;
        StringBuilder sb = new StringBuilder();
        int linea = _controladorEjecucion.numeroLineaActual();
        if (linea == 0) {
            linea = 1;
        }

        sb.append("(").append(linea).append(", <");

        ArrayList<Variable> variables = _gestorVariables.variablesEntrada();
        if (variables.size() > 0) {
            comaAlFinal = true;
            concatenarVariables(variables, sb);
        }

        if (comaAlFinal) {
            sb.append(", ");
            comaAlFinal = false;
        }

        variables = _gestorVariables.variablesLocales();
        if (variables.size() > 0) {
            comaAlFinal = true;
            concatenarVariables(variables, sb);
        }

        if (comaAlFinal) {
            sb.append(", ");
        }
        concatenarVariable(_gestorVariables.variableSalida(), sb);

        sb.append(">)");

        return sb.toString();
    }

    private void concatenarVariables(ArrayList<Variable> variables, StringBuilder sb) {
        concatenarVariable(variables.get(0), sb);

        for (int i = 1; i < variables.size(); ++i) {
            sb.append(", ");
            concatenarVariable(variables.get(i), sb);
        }
    }

    private void concatenarVariable(Variable variable, StringBuilder sb) {
        sb.append(variable.id()).append(" = ").append(variable.valor());
    }

    /**
     * Devuelve el resultado almacenado en la variable de salida del ámbito.
     *
     * @return El resultado del ámbito.
     */
    public int resultado() {
        return _gestorVariables.variableSalida().valor();
    }

    /**
     * Limpia todos los gestores del ámbito, dejándolo listo para ser
     * reutilizado.
     */
    public void limpiar() {
        _gestorVariables.limpiar();
        _gestorBucles.limpiar();
        _gestorEtiquetas.limpiar();
        _gestorLlamadasAMacro.limpiar();
    }
}
