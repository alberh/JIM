package com.jim_project.interprete.parser;

import com.jim_project.interprete.Modelo;
import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.LlamadaAMacro;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.lmodel.LParser;
import com.jim_project.interprete.parser.loopmodel.LoopParser;
import com.jim_project.interprete.parser.previo.PrevioParser;
import com.jim_project.interprete.parser.whilemodel.WhileParser;
import com.jim_project.interprete.util.gestor.GestorAmbitos;
import java.util.Arrays;

/**
 * Clase encargada de mantener el flujo de ejecución mientras se interpreta el
 * código de un ámbito.
 *
 * @author Alberto García González
 */
public class ControladorEjecucion {

    private int _instruccionesEjecutadas;
    private final Ambito _ambito;
    private final Programa _programa;

    private final ArrayList<String> _lineas;
    private int _lineaActual;
    private boolean _salto;

    private StringBuilder _traza;

    /**
     * Constructor de clase.
     *
     * @param ambito Referencia al ámbito que contiene este controlador de
     * ejecución.
     * @param lineas Lista de cadenas con el código del ámbito.
     */
    public ControladorEjecucion(Ambito ambito, ArrayList<String> lineas) {
        _instruccionesEjecutadas = 0;
        _ambito = ambito;
        _programa = _ambito.programa();
        _lineas = lineas;
        _lineaActual = _lineas.size();
        _salto = false;
        _traza = new StringBuilder();
    }

    /**
     * Devuelve el número de instrucciones ejecutadas.
     *
     * @return El número de instrucciones ejecutadas.
     */
    public int instruccionesEjecutadas() {
        return _instruccionesEjecutadas;
    }

    /**
     * Añade {@code n} al contador de instrucciones ejecutadas.
     *
     * @param n El valor a sumar al contador. Si {@code n} es menor que 0, no se
     * realizará la suma.
     */
    public void sumarInstrucciones(int n) {
        if (n >= 0) {
            _instruccionesEjecutadas += n;
        }
    }

    /**
     * Devuelve el ámbito que contiene a este controlador de ejecución.
     *
     * @return Referencia al ámbito que contiene a este controlador de
     * ejecución.
     */
    public Ambito ambito() {
        return _ambito;
    }

    /**
     * Concatena la traza que mantiene este controlador de ejecución con la
     * traza de ámbitos hijos ya terminados.
     *
     * @param traza La traza a concatenar.
     */
    public void trazarAmbito(String traza) {
        _traza.append(traza);
    }

    /**
     * Devuelve la traza de este controlador de ejecución.
     *
     * @return La traza de este controlador de ejecución.
     */
    public String traza() {
        return _traza.toString();
    }

    /**
     * Pone en marcha el controlador de ejecución, asignando los parámetros a
     * las variables de entrada del ámbito e interpretando el código asociado.
     *
     * @param parametros Los parámetros de entrada del ámbito.
     */
    public void iniciar(String[] parametros) {
        Ambito ambitoRaiz = _programa.gestorAmbitos().ambitoRaiz();
        Ambito ambitoPadre = _programa.gestorAmbitos().ambitoPadre(_ambito.profundidad());
        String idMacro = "";
        int lineaLlamada;

        if (_ambito == ambitoRaiz) {
            lineaLlamada = numeroLineaActual();
        } else {
            if (ambitoPadre != ambitoRaiz) {
                idMacro = ambitoPadre.macroAsociada().id();
            }
            lineaLlamada = ambitoPadre.controladorEjecucion().numeroLineaActual();
        }

        previo();

        if (_programa.estadoOk()) {
            // Asignar variables de entrada
            if (parametros != null) {
                asignarVariablesEntrada(parametros);
            }

            // Lanzar
            if (_ambito.programa().verbose()) {
                if (_ambito == ambitoRaiz) {
                    System.out.println("Ejecutando el programa");
                } else {
                    for (int i = 0; i < _ambito.profundidad() - 1; ++i) {
                        System.out.print("   ");
                    }
                    /* El mensaje 'Ejecutando macro ...' se hace una vez creado y
                     * puesto en marcha el nuevo ámbito asociado a tal macro.
                     * De ahí que se haga la siguiente comprobación.
                     */
                    if (ambitoPadre != ambitoRaiz) {
                        System.out.print(idMacro + ", línea " + (lineaLlamada - 1));
                    } else {
                        System.out.print("Línea " + lineaLlamada);
                    }
                    System.out.println(": Ejecutando macro " + _ambito.macroAsociada().id());
                }
            }

            ejecutar(_programa.modelo().tipo());
        }
    }

    /**
     * Expande las llamadas a macro del ámbito asociado y devuelve una cadena
     * con el código resultante tras la expansión.
     *
     * @return El código resultante tras la expansión.
     */
    public String expandir() {
        if (_ambito.programa().verbose()) {
            System.out.println("Analizando el programa");
        }
        previo();

        String expansion = null;
        ArrayList<LlamadaAMacro> llamadas
                = new ArrayList(_ambito.gestorLlamadasAMacro().llamadasAMacro());

        if (_ambito.programa().verbose()) {
            if (!llamadas.isEmpty()) {
                System.out.println("Expandiendo macros");
            } else {
                System.out.println("No hay llamadas a macros");
            }
        }

        if (!llamadas.isEmpty()) {
            ArrayList<String> lineas = new ArrayList(_lineas);
            int desplazamiento = 0;

            for (int i = 0; i < llamadas.size() && _programa.estadoOk(); ++i) {
                LlamadaAMacro llamada = llamadas.get(i);

                if (_ambito.programa().verbose()
                        && (_programa.worker() == null || !_programa.worker().isCancelled())) {
                    System.out.println("   Expandiendo llamada a macro " + llamada.id() + " en línea " + llamada.linea());
                }

                //llamada.linea(llamada.linea() + desplazamiento);
                String resultadoExpansion = _programa.gestorMacros().expandir(llamada);

                if (resultadoExpansion != null) {
                    ArrayList<String> lineasExpansion = new ArrayList<>(
                            Arrays.asList(resultadoExpansion.split("[\n\r]+"))
                    );

                    lineas.remove(llamada.linea() + desplazamiento - 1);
                    lineas.addAll(llamada.linea() + desplazamiento - 1, lineasExpansion);
                    desplazamiento += lineasExpansion.size() - 1;
                }

                if (_programa.worker() != null && _programa.worker().isCancelled()) {
                    _programa.estado(Programa.Estado.ERROR);
                }
            }

            if (_programa.estadoOk()) {
                if (_programa.verbose()) {
                    System.out.println("Expansión de macros finalizada.");
                }
                StringBuilder sb = new StringBuilder();
                lineas.forEach(
                        linea -> sb.append(linea).append("\n")
                );
                expansion = sb.toString();
            }
        }

        return expansion;
    }

    private void previo() {
        if (_programa.estadoOk()) {
            ejecutar(Modelo.Tipo.PREVIO);
        }
    }

    private Parser obtenerParser(Modelo.Tipo tipo, String lineaReader) {
        BufferedReader reader = new BufferedReader(new StringReader(lineaReader));

        switch (tipo) {
            case PREVIO:
                return new PrevioParser(reader, this);

            case L:
                return new LParser(reader, this);

            case LOOP:
                return new LoopParser(reader, this);

            case WHILE:
                return new WhileParser(reader, this);

            default:
                return null;
        }
    }

    private void ejecutar(Modelo.Tipo tipoParser) {
        _lineaActual = 0;
        _salto = false;
        _instruccionesEjecutadas = 0;

        if (_programa.argumentos().traza) {
            _traza = new StringBuilder("[");
        }

        if (numeroLineas() > 0) {
            String linea = lineaSiguiente();
            Parser parser = obtenerParser(tipoParser, linea);
            AnalizadorLexico lex = parser.analizadorLexico();

            do {
                // Límite de tamaño a la traza
                //if (_traza.length() < 10000) {
                if (_programa.argumentos().traza) {
                    if (_instruccionesEjecutadas > 0) {
                        _traza.append(",")
                                .append(System.getProperty("line.separator"));
                    }
                    _traza.append(_ambito.estadoMemoria());
                }
                //}

                if (!linea.trim().isEmpty()) {
                    ++_instruccionesEjecutadas;
                }

                try {
                    lex.yyclose();
                } catch (Exception ex) {
                    _ambito.programa().error().deEjecucion();
                }
                lex.yyreset(new BufferedReader(new StringReader(linea)));

                parser.parse();

                if (!_salto) {
                    linea = lineaSiguiente();
                } else {
                    linea = lineaActual();
                    _salto = false;
                }

                if (_programa.worker() != null && _programa.worker().isCancelled()) {
                    terminar();
                }
            } while (!finalizado() && _programa.estadoOk());
        }

        if (_programa.argumentos().traza) {
            if (_instruccionesEjecutadas > 0) {
                _traza.append(",")
                        .append(System.getProperty("line.separator"));
            }
            _traza.append(_ambito.estadoMemoria());
            _traza.append("]");
        }
    }

    /**
     * Devuelve el número de línea que está siendo interpretada.
     *
     * @return El número de línea que está siendo ejecutada.
     */
    public int numeroLineaActual() {
        return _lineaActual;
    }

    /**
     * Cambia el número de línea a interpretar.
     *
     * @param n El nuevo número de línea.
     */
    public void numeroLineaActual(int n) {
        if (lineaValida(n)) {
            _lineaActual = n;
        } else {
            terminar();
        }
    }

    /**
     * Devuelve la línea que está siendo ejecutada.
     *
     * @return La línea que está siendo ejecutada, o {@code null} si la
     * ejecución ha finalizado.
     */
    public String lineaActual() {
        return finalizado() ? null : _lineas.get(_lineaActual - 1);
    }

    /**
     * Avanza a la línea siguiente y la devuelve.
     *
     * @return La línea siguiente.
     */
    public String lineaSiguiente() {
        numeroLineaActual(_lineaActual + 1);
        return lineaActual();
    }

    /**
     * Devuelve la n-ésima línea del código.
     *
     * @param n El número de linea a obtener.
     * @return La línea del código.
     */
    public String linea(int n) {
        if (lineaValida(n)) {
            return _lineas.get(n);
        } else {
            return null;
        }
    }

    /**
     * Comprueba si un número de línea es válido. Un número de línea es válido
     * si es mayor o igual a 1 y menor que el número de líneas + 1.
     *
     * @param numeroLinea El número de línea a comprobar.
     * @return {@code true}, si el número es válido; {@code false}, en caso
     * contrario.
     */
    public boolean lineaValida(int numeroLinea) {
        return numeroLinea >= 1 && numeroLinea <= numeroLineas();
    }

    /**
     * Devuelve el número de líneas del código.
     *
     * @return El número de líneas del código.
     */
    public int numeroLineas() {
        return _lineas.size();
    }

    /**
     * Devuelve las líneas del código.
     *
     * @return Las líneas del código.
     */
    public ArrayList<String> lineas() {
        return _lineas;
    }

    /**
     * Detiene la ejecución del intérprete.
     */
    public void terminar() {
        _lineaActual = numeroLineas() + 1;
    }

    /**
     * Comprueba si el programa ha finalizado. Se considera que el programa ha
     * finalizado cuando el número de línea actual es mayor que el número de
     * líneas.
     *
     * @return {@code true}, si el programa ha finalizado; {@code false}, en
     * caso contrario.
     */
    public boolean finalizado() {
        return numeroLineaActual() <= 0 || numeroLineaActual() > numeroLineas();
    }

    /**
     * Cambia el número de línea actual al especificado e indica que se ha
     * ejecutado una instrucción de salto.
     *
     * @param linea El número de línea al que saltar.
     */
    public void salto(int linea) {
        numeroLineaActual(linea);
        _salto = true;
    }

    private void asignarVariablesEntrada(String[] parametros) {
        if (_ambito == _ambito.programa().gestorAmbitos().ambitoRaiz()) {
            // En el caso del ámbito raíz no es necesario envolver la llamada a
            // parseInt en un bloque try-catch.
            for (int i = 0; i < parametros.length; ++i) {
                int valor = Integer.parseInt(parametros[i]);
                _ambito.gestorVariables().nuevaVariable("X" + (i + 1), valor);
            }
        } else {
            GestorAmbitos gestor = (GestorAmbitos) _ambito.gestor();
            Ambito ambitoPadre = gestor.ambitoPadre(_ambito.profundidad());

            for (int i = 0; i < parametros.length; ++i) {
                int valor = 0;
                try {
                    valor = Integer.parseInt(parametros[i]);
                } catch (NumberFormatException ex) {
                    Variable v = ambitoPadre.gestorVariables().obtenerVariable(parametros[i]);
                    if (v != null) {
                        valor = v.valor();
                    }
                }
                _ambito.gestorVariables().nuevaVariable("X" + (i + 1), valor);
            }
        }
    }
}
