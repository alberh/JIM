package com.jim_project.interprete.parser;

import com.jim_project.interprete.Modelo;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.LlamadaAMacro;
import com.jim_project.interprete.componente.Macro;

public class Acciones {

    protected Ambito _ambito;
    protected Variable _ultimaVariableAsignada;
    protected boolean _ignorarComprobacionAsignacion;

    public Acciones(Ambito ambito) {
        _ambito = ambito;
        _ultimaVariableAsignada = null;
        _ignorarComprobacionAsignacion = false;
    }

    public Ambito ambito() {
        return _ambito;
    }

    public Variable ultimaVariableAsignada() {
        return _ultimaVariableAsignada;
    }

    public void variableAsignada(Object idVariable) {
        _ultimaVariableAsignada = obtenerVariable(idVariable);
    }

    public void asignacion(Object lvalue, Object rvalue) {
        if (!_ignorarComprobacionAsignacion) {
            comprobarAsignacion(rvalue);
        } else {
            _ignorarComprobacionAsignacion = false;
        }

        int valor = obtenerValor(rvalue);

        if (_ambito.programa().estadoOk() && valor > -1) {
            obtenerVariable(lvalue).valor(valor);
        }
    }

    public void comprobarAsignacion(Object o) {
        // Como con la instrucción GOTO L, 
        if (!_ambito.programa().macrosPermitidas()) {
            com.jim_project.interprete.util.Error error = _ambito.programa().error();

            switch (_ambito.programa().modelo().tipo()) {
                case L:
                    // Comprobar que lo que se asigna no es ni un número,
                    // ni una variable distinta de la que está siendo asignada
                    if (esEntero(o) || !(new Variable(o.toString(), null).id().equals(_ultimaVariableAsignada.id()))) {
                        //error.deAsignacionNoPermitida();
                        error.deLlamadasAMacroNoPermitidas();
                    }
                    break;

                case LOOP:
                case WHILE:
                    // Comprobar que lo que se asigna sea una variable o el 0
                    if (!esCadena(o) && obtenerValor(o) != 0) {
                        //error.deAsignacionNoPermitida();
                        error.deLlamadasAMacroNoPermitidas();
                    }
                    break;

                default:
            }
        }
    }

    public void incremento(Object lvalue) {
        if (!_ambito.programa().macrosPermitidas()) {
            //_ambito.programa().error().deOperacionNoPermitida();
            //error.deLlamadasAMacroNoPermitidas();
            _ambito.programa().error().deLlamadasAMacroNoPermitidas();
        }

        obtenerVariable(lvalue).incremento();
    }

    public void decremento(Object lvalue) {
        if (!_ambito.programa().macrosPermitidas()
                && _ambito.programa().modelo().tipo() == Modelo.Tipo.L) {
            //_ambito.programa().error().deOperacionNoPermitida();
            //error.deLlamadasAMacroNoPermitidas();
            _ambito.programa().error().deLlamadasAMacroNoPermitidas();
        }

        obtenerVariable(lvalue).decremento();
    }

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

    public void llamadaAMacro(Object idMacro) {
        if (!_ambito.programa().macrosPermitidas()) {
            _ambito.programa().error().deLlamadasAMacroNoPermitidas();
        }

        _ignorarComprobacionAsignacion = true;
        Macro macro = _ambito.programa().gestorMacros().obtenerMacro(idMacro.toString());
        int valor = 0;

        if (macro != null) {
            LlamadaAMacro llamada = _ambito.gestorLlamadasAMacro().obtenerLlamadaAMacro(idMacro.toString());

            int nP = llamada.parametros().size();
            int nV = macro.variablesEntrada().size();

            if (nP != nV) {
                int n = _ambito.controladorEjecucion().numeroLineaActual();
                _ambito.programa().error().enNumeroParametros(n, idMacro.toString(), nV, nP);
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
                    .replaceAll("[\\[\\]]", "").replace(")(", "\n")
                    + "\nFin traza de llamada a macro " + macro.id();
            _ambito.controladorEjecucion().trazarAmbito(traza);

            if (nuevoAmbito.programa().estadoOk()) {
                valor = nuevoAmbito.resultado();
            }

            _ambito.programa().gestorAmbitos().eliminarUltimoAmbito();
        }

        _ultimaVariableAsignada.valor(valor);
    }

    protected Variable obtenerVariable(Object id) {
        // tratamiento de errores
        return _ambito.variables().obtenerVariable(id.toString());
    }

    protected int obtenerValor(Object o) {
        int valor = 0;

        if (esEntero(o)) {
            valor = (Integer) o;
        } else {
            try {
                valor = _ambito.variables().obtenerVariable((String) o).valor();
            } catch (Exception ex) {
                // Pequeño parche para que no asigne valor 0 tras una llamada
                // a macro.
                valor = -1;
            }
        }

        return valor;
    }

    protected static boolean esEntero(Object o) {
        return o instanceof Integer;
    }

    protected static boolean esCadena(Object o) {
        return o instanceof String;
    }
}
