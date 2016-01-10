package com.jim_project.interprete.util.gestor;

import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.LlamadaAMacro;
import com.jim_project.interprete.componente.Variable;
import java.util.ArrayList;

/**
 * Clase encargada de gestionar las llamadas a macro de un ámbito.
 *
 * @author Alberto García González
 */
public class GestorLlamadasAMacro extends GestorComponentes {

    private final ArrayList<LlamadaAMacro> _llamadasAMacros;
    private LlamadaAMacro _ultimaLlamadaAMacro;

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito que contiene este gestor.
     */
    public GestorLlamadasAMacro(Ambito ambito) {
        super(ambito);

        _llamadasAMacros = new ArrayList<>();
        _ultimaLlamadaAMacro = null;
    }

    /**
     * Define una nueva llamada a macro, la marca como macro siendo definida y
     * la añade al gestor.
     *
     * @param ultimaVariableAsignada La última variable sobre la que ha sido
     * asignado algún valor.
     * @param ultimaEtiquetaEncontrada La última etiqueta encontrada en el código.
     * @param idMacro El identificador de la macro objetivo de la llamada.
     */
    public void definirLlamadaAMacro(
            Variable ultimaVariableAsignada,
            Etiqueta ultimaEtiquetaEncontrada,
            Object idMacro) {
        
        int linea = _ambito.controladorEjecucion().numeroLineaActual();
        String idEtiqueta;
        if (ultimaEtiquetaEncontrada != null) {
            idEtiqueta = ultimaEtiquetaEncontrada.id();
        } else {
            idEtiqueta = null;
        }

        _ultimaLlamadaAMacro = new LlamadaAMacro(
                linea,
                ultimaVariableAsignada.id(),
                idEtiqueta,
                idMacro.toString()
        );
        _llamadasAMacros.add(_ultimaLlamadaAMacro);
    }

    /**
     * Especifica las variables de entrada de la macro siendo definida.
     *
     * @param parametro Los parámetros de entrada de la macro.
     */
    public void definirVariableEntradaMacro(Object parametro) {
        _ultimaLlamadaAMacro.parametros().add(parametro.toString());
    }

    /**
     * Busca una llamada a macro según el identificador de la llamada y el
     * número de línea del controlador de ejecución que esté procesando la
     * llamada.
     *
     * @param idMacro El identificador de la macro cuya llamada se quiere
     * recuperar.
     * @return Una referencia a la llamada a macro buscada, si existe;
     * {@code null} si no existe.
     */
    public LlamadaAMacro obtenerLlamadaAMacro(String idMacro) {
        for (LlamadaAMacro llamada : _llamadasAMacros) {
            if (llamada.id().equalsIgnoreCase(idMacro)
                    && llamada.linea()
                    == _programa.gestorAmbitos().ambitoActual()
                    .controladorEjecucion().numeroLineaActual()) {

                return llamada;
            }
        }

        return null;
    }

    /**
     * Devuelve la lista de llamadas a macro.
     *
     * @return La lista de llamadas a macro.
     */
    public ArrayList<LlamadaAMacro> llamadasAMacro() {
        return _llamadasAMacros;
    }

    /**
     * Elimina todas las llamadas a macro almacenadas en el gestor.
     */
    @Override
    public void limpiar() {
        _llamadasAMacros.clear();
    }

    /**
     * Devuelve el número de llamadas a macro almacenadas en el gestor.
     *
     * @return El número de llamadas a macro almacenadas en el gestor.
     */
    @Override
    public int count() {
        return _llamadasAMacros.size();
    }

    /**
     * Comprueba si el gestor está vacío.
     *
     * @return {@code true}, si el getor está vacío; {@code false}, si contiene
     * alguna llamada a macro.
     */
    @Override
    public boolean vacio() {
        return _llamadasAMacros.isEmpty();
    }

}
