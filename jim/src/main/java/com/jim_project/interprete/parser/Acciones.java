package com.jim_project.interprete.parser;

import com.jim_project.interprete.Modelo;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.LlamadaAMacro;
import com.jim_project.interprete.componente.Macro;

/**
 * Clase que provee métodos que realizan las operaciones comunes a todos los
 * modelos simulados.
 * @author Alberto García González
 */
public class Acciones {

    /**
     * Referencia al ámbito sobre cuyos componentes serán realizadas las acciones.
     */
    protected final Ambito _ambito;
    /**
     * Referencia a la última variable asignada.
     * Útil para distintos tipos de asignaciones y comprobaciones.
     */
    protected Variable _ultimaVariableAsignada;
    /**
     * Bandera que indica si se deben ignorar las comprobaciones que se harían
     * sobre una asignación directa.
     * Su uso es necesario para no realizar dichas comprobaciones cuando se asigna
     * un valor que ha sido fruto de una operación binaria, en cuyo caso ya habrá
     * pasado por las comprobaciones oportunas.
     */
    protected boolean _ignorarComprobacionAsignacion;

    /**
     * Constructor de clase.
     * @param ambito Referencia al ámbito sobre el que se debe operar.
     */
    public Acciones(Ambito ambito) {
        _ambito = ambito;
        _ultimaVariableAsignada = null;
        _ignorarComprobacionAsignacion = false;
    }

    /**
     * Devuelve el ámbito asociado.
     * @return El ámbito asociado.
     */
    public Ambito ambito() {
        return _ambito;
    }

    /**
     * Devuelve una referencia a la última variable asignada.
     * @return Una referencia a la última variable asignada.
     */
    public Variable ultimaVariableAsignada() {
        return _ultimaVariableAsignada;
    }

    /**
     * Cambia la variable asignada.
     * @param idVariable Referencia a la nueva variable asignada.
     */
    public void variableAsignada(Object idVariable) {
        _ultimaVariableAsignada = obtenerVariable(idVariable);
    }

    /**
     * Asigna a una variable un valor constante o el valor de otra variable.
     * @param lvalue El identificador de la variable que será asignada.
     * @param rvalue El valor a asignar o el identificador de la variable
     * a asignar.
     */
    public void asignacion(Object lvalue, Object rvalue) {
        if (!_ignorarComprobacionAsignacion) {
            comprobarAsignacion(rvalue);
        } else {
            _ignorarComprobacionAsignacion = false;
        }

        int valor = obtenerValor(rvalue);

        if (_ambito.programa().estadoOk()) {
            if (valor > -1) {
                obtenerVariable(lvalue).valor(valor);
            } else {
                //_ambito.programa().error().deMaximoEnteroSuperado();
            }
        }
    }

    /**
     * Si el uso de macros no está permitido, comprueba las limitaciones establecidas
     * por cada modelo en la asignación.
     * @param rvalue El valor a asignar o el identificador de la variable
     * a asignar.
     */
    public void comprobarAsignacion(Object rvalue) {
        // Como con la instrucción GOTO L, 
        if (!_ambito.programa().macrosPermitidas()) {
            com.jim_project.interprete.util.Error error = _ambito.programa().error();

            switch (_ambito.programa().modelo().tipo()) {
                case L:
                    // Comprobar que lo que se asigna no es ni un número,
                    // ni una variable distinta de la que está siendo asignada
                    if (esEntero(rvalue) || !(new Variable(rvalue.toString(), null).id().equals(_ultimaVariableAsignada.id()))) {
                        //error.deAsignacionNoPermitida();
                        error.deLlamadasAMacroNoPermitidas();
                    }
                    break;

                case LOOP:
                case WHILE:
                    // Comprobar que lo que se asigna sea una variable o el 0
                    if (!esCadena(rvalue) && obtenerValor(rvalue) != 0) {
                        //error.deAsignacionNoPermitida();
                        error.deLlamadasAMacroNoPermitidas();
                    }
                    break;

                default:
            }
        }
    }

    /**
     * Realiza un incremento unitario en el valor de una variable.
     * @param lvalue El identificador de la variable.
     */
    public void incremento(Object lvalue) {
        if (!_ambito.programa().macrosPermitidas()) {
            //_ambito.programa().error().deOperacionNoPermitida();
            //error.deLlamadasAMacroNoPermitidas();
            _ambito.programa().error().deLlamadasAMacroNoPermitidas();
        }

        obtenerVariable(lvalue).incremento();
    }

    /**
     * Realiza un decremento unitario en el valor de una variable.
     * @param lvalue El identificador de la variable.
     */
    public void decremento(Object lvalue) {
        if (!_ambito.programa().macrosPermitidas()
                && _ambito.programa().modelo().tipo() == Modelo.Tipo.L) {
            //_ambito.programa().error().deOperacionNoPermitida();
            //error.deLlamadasAMacroNoPermitidas();
            _ambito.programa().error().deLlamadasAMacroNoPermitidas();
        }

        obtenerVariable(lvalue).decremento();
    }

    /**
     * Realiza una operación binaria.
     * @param operador Indica el tipo de operación: '+', '-', '*', '/' y '%'.
     * @param op1 El primer operando.
     * @param op2 El segundo operando.
     * @return El valor resultante de la operación.
     */
    public int operacionBinaria(char operador, Object op1, Object op2) {
        int v1 = obtenerValor(op1);
        int v2 = obtenerValor(op2);
        int resultado = 0;
        _ignorarComprobacionAsignacion = true;

        comprobarOperacionBinaria(operador, op1, op2, v1, v2);

        if (_ambito.programa().estadoOk()) {
            switch (operador) {
                case '+':
                    resultado = v1 + v2;
                    if (resultado < v1 || resultado < v2) {
                        _ambito.programa().error().deMaximoEnteroSuperado();
                    }
                    break;

                case '-':
                    int dif = v1 - v2;
                    resultado = dif > 0 ? dif : 0;
                    break;

                case '*':
                    resultado = v1 * v2;
                    break;

                case '/':
                    if (v2 != 0) {
                        resultado = v1 / v2;
                    } else {
                        resultado = v1;
                    }
                    break;

                case '%':
                    if (v2 != 0) {
                        resultado = v1 % v2;
                    } else {
                        resultado = 0;
                    }
                    break;

                default:
                // informar de error
            }
        }

        return resultado;
    }

    /**
     * Si el uso de macros no está permitido, realiza las comprobaciones oportunas
     * para restringir las operaciones binarias a lo permitido por cada modelo.
     * @param operador Indica el tipo de operación: '+', '-', '*', '/' y '%'.
     * @param op1 El primer operando.
     * @param op2 El segundo operando.
     * @param valor1 El valor del primero operando.
     * @param valor2 El valor del segundo operando.
     */
    private void comprobarOperacionBinaria(
            char operador,
            Object op1,
            Object op2,
            int valor1,
            int valor2) {

        if (!_ambito.programa().macrosPermitidas()) {
            boolean hayError = false;
            Modelo modelo = _ambito.programa().modelo();
            com.jim_project.interprete.util.Error error = _ambito.programa().error();
            /*
             ===========
             Modelo L =
             ---------------------
             Instrucciones básicas
             ---------------------
             [X] V <- V + 1
             [X] V <- V - 1
             [X] V <- V
             [ ] IF V != 0 GOTO L
             -------------------
             Instrucciones extra
             -------------------
             [X] V++
             [X] V--
             [X] V <- {N, V'}
             [X] V <- {N, V'} {+,-,*,/,%} {N', V''}
             [X] V <- MACRO(arg0, arg1, ..., argn)
             [X] GOTO E
             Asignación, suma, resta, producto, división y módulo de variables y números
             */

            if (modelo.tipo() == Modelo.Tipo.L) {
                // V <- V + 1 y V <- V - 1
                if (operador == '+' || operador == '-') {
                    // Comprobar que el primer operando es una variable
                    if (esCadena(op1)) {
                        Variable v1 = new Variable(op1.toString(), null);
                        // Comprobar que se trata de la misma variable que va a ser asignada
                        if (v1.id().equals(_ultimaVariableAsignada.id())) {
                            // Comprobar que el segundo operador es un entero
                            if (esEntero(op2)) {
                                // Comprobar que no se sume un valor distinto de 1
                                if (valor2 != 1) {
                                    //error.deOperacionValorNoUnidad(operador);
                                    hayError = true;
                                }
                            } else {
                                //error.deOperacionEntreVariables();
                                hayError = true;
                            }
                        } else {
                            //error.deOperacionYAsignacionDiferente();
                            hayError = true;
                        }
                    } else {
                        //error.deOperacionNoPermitida();
                        hayError = true;
                    }
                } else {
                    //error.deOperadorNoPermitido();
                    hayError = true;
                }
            } /*
             ==============
             Modelo Loop =
             ---------------------
             Instrucciones básicas
             ---------------------
             [X] V <- 0
             [X] V <- V + 1
             [X] V <- V'
             [ ] LOOP V
             [ ] END
             -------------------
             Instrucciones extra
             -------------------
             [X] V++
             [X] V <- {N, V'}
             [X] V <- {N, V'} {+, *} {N', V''}
             [X] V <- MACRO(arg0, arg1, ..., argn)
             Asignación, suma y producto de variables y números
             */ else if (modelo.tipo() == Modelo.Tipo.LOOP) {
                // V <- V + 1
                if (operador == '+') {
                    // Comprobar que el primer operando es una variable
                    if (esCadena(op1)) {
                        Variable v1 = new Variable(op1.toString(), null);
                        // Comprobar que se trata de la misma variable que va a ser asignada
                        if (v1.id().equals(_ultimaVariableAsignada.id())) {
                            // Comprobar que el segundo operador es un entero
                            if (esEntero(op2)) {
                                // Comprobar que no se sume un valor distinto de 1
                                if (valor2 != 1) {
                                    //error.deOperacionValorNoUnidad(operador);
                                    hayError = true;
                                }
                            } else {
                                //error.deOperacionEntreVariables();
                                hayError = true;
                            }
                        } else {
                            //error.deOperacionYAsignacionDiferente();
                            hayError = true;
                        }
                    } else {
                        //error.deOperacionNoPermitida();
                        hayError = true;
                    }
                } else {
                    //error.deOperadorNoPermitido();
                    hayError = true;
                }
            } /*
             ===============
             Modelo While =
             ---------------------
             Instrucciones básicas
             ---------------------
             [X] V <- 0
             [X] V <- V + 1
             [X] V <- V'
             [X] V--
             [ ] WHILE V != 0
             [ ] END
             -------------------
             Instrucciones extra
             -------------------
             [X] V++
             [X] V <- {N, V'}
             [X] V <- {N, V'} {+,-,*,/,%} {N', V''}
             [X] V <- MACRO(arg0, arg1, ..., argn)
             Asignación, suma, resta, producto y división de variables y números
             */ else { //if (modelo.tipo() == Modelo.Tipo.WHILE) {
                // V <- V + 1
                if (operador == '+') {
                    // Comprobar que el primer operando es una variable
                    if (esCadena(op1)) {
                        Variable v1 = new Variable(op1.toString(), null);
                        // Comprobar que se trata de la misma variable que va a ser asignada
                        if (v1.id().equals(_ultimaVariableAsignada.id())) {
                            // Comprobar que el segundo operador es un entero
                            if (esEntero(op2)) {
                                // Comprobar que no se sume un valor distinto de 1
                                if (valor2 != 1) {
                                    //error.deOperacionValorNoUnidad(operador);
                                    hayError = true;
                                }
                            } else {
                                //error.deOperacionEntreVariables();
                                hayError = true;
                            }
                        } else {
                            //error.deOperacionYAsignacionDiferente();
                            hayError = true;
                        }
                    } else {
                        //error.deOperacionNoPermitida();
                        hayError = true;
                    }
                } else {
                    //error.deOperadorNoPermitido();
                    hayError = true;
                }
            }

            if (hayError) {
                error.deLlamadasAMacroNoPermitidas();
            }
        }
    }

    /**
     * Crea un nuevo ámbito y lo lanza a ejecución.
     * @param idMacro El identificador de la macro asociada al nuevo ámbito.
     */
    public void llamadaAMacro(Object idMacro) {
        if (!_ambito.programa().macrosPermitidas()) {
            _ambito.programa().error().deLlamadasAMacroNoPermitidas();
        }

        _ignorarComprobacionAsignacion = true;
        Macro macro = _ambito.programa().gestorMacros().obtenerMacro(idMacro.toString());
        int valor = 0;
        int numeroLinea = _ambito.controladorEjecucion().numeroLineaActual();

        if (macro != null) {
            LlamadaAMacro llamada = _ambito.gestorLlamadasAMacro().obtenerLlamadaAMacro(idMacro.toString());

            int nP = llamada.parametros().size();
            int nV = macro.variablesEntrada().size();

            if (nP != nV) {
                _ambito.programa().error().enNumeroParametros(numeroLinea,
                        idMacro.toString().toUpperCase(), nV, nP);
                return;
            }
            String[] parametros = new String[nP];

            for (int i = 0; i < nP; ++i) {
                parametros[i] = llamada.parametros().get(i);
            }

            int profundidad = _ambito.profundidad() + 1;
            Ambito nuevoAmbito = _ambito.programa().gestorAmbitos().nuevoAmbito(parametros, macro, profundidad);
            nuevoAmbito.iniciar();
            String traza = ",\nTraza de llamada a macro " + macro.id() + "\n"
                    + nuevoAmbito.controladorEjecucion().traza()
                    .replaceAll("[\\[\\]]", "").replace(")(", "),\n(")
                    + ",\nFin traza de llamada a macro " + macro.id();
            _ambito.controladorEjecucion().trazarAmbito(traza);

            if (nuevoAmbito.programa().estadoOk()) {
                valor = nuevoAmbito.resultado();
                _ambito.controladorEjecucion().sumarInstrucciones(
                        nuevoAmbito.controladorEjecucion().instruccionesEjecutadas()
                );
            }

            _ambito.programa().gestorAmbitos().eliminarUltimoAmbito();
        } else {
            _ambito.programa().error().deMacroNoDefinida(numeroLinea, idMacro.toString().toUpperCase());
        }

        _ultimaVariableAsignada.valor(valor);
    }

    
    /**
     * Devuelve una variable del gestor de variables del ámbito donde se
     * ejecutan las acciones de esta clase.
     * @param id El identificador de la variable.
     * @return La variable con identificador {@code id}, si existe; {@code null} si no existe.
     */
    protected Variable obtenerVariable(Object id) {
        return _ambito.gestorVariables().obtenerVariable(id.toString());
    }

    /**
     * Obtiene el valor de un objeto, suponiendo que dicho objeto sea un {@link Integer}
     * o un {@link String} representando el identificador de una variable.
     * @param o El objeto cuyo valor se desea recuperar.
     * @return El valor de {@code o}.
     */
    protected int obtenerValor(Object o) {
        int valor = 0;

        if (esEntero(o)) {
            valor = (Integer) o;
        } else {
            try {
                valor = _ambito.gestorVariables().obtenerVariable((String) o).valor();
            } catch (Exception ex) {
                // Pequeño parche para que no asigne valor 0 tras una llamada
                // a macro.
                valor = -1;
            }
        }

        return valor;
    }

    /**
     * Comprueba si un objeto es de la subclase {@link Integer}.
     * @param obj El objeto a comprobar.
     * @return {@code true}, si {@code obj} es un {@link Integer}; {@code false}, si no lo es.
     */
    protected static boolean esEntero(Object obj) {
        return obj instanceof Integer;
    }

    /**
     * Comprueba si un objeto es de la subclase {@link String}.
     * @param obj El objeto a comprobar.
     * @return {@code true}, si {@code obj} es un {@link String}; {@code false}, si no lo es.
     */
    protected static boolean esCadena(Object obj) {
        return obj instanceof String;
    }
}
