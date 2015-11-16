package com.jim_project.interprete.parser;

import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.util.Error;

public class Acciones {

    protected Ambito _ambito;

    public Acciones(Ambito ambito) {
        _ambito = ambito;
    }

    public Ambito ambito() {
        return _ambito;
    }

    public void asignacion(Object lvalue, Object rvalue) {
        Variable variable = obtenerVariable(lvalue);
        int valor = obtenerValor(rvalue);

        variable.valor(valor);
    }

    public void incremento(Object lvalue) {
        obtenerVariable(lvalue).incremento();
    }

    public void decremento(Object lvalue) {
        obtenerVariable(lvalue).decremento();
    }

    public int operacion(char operador, Object op1, Object op2) {
        int v1, v2;

        v1 = obtenerValor(op1);
        v2 = obtenerValor(op2);

        /*
         ===========
         Modelo L =
         ---------------------
         Instrucciones básicas
         ---------------------
         V <- V + 1
         V <- V - 1
         V <- V
         IF V != 0 GOTO L
         -------------------
         Instrucciones extra
         -------------------
         V++
         V--
         V <- {N, V'}
         V <- {N, V'} {+,-,*,/,%} {N', V''}
         V <- MACRO(arg0, arg1, ..., argn)
         GOTO E
         Asignación, suma, resta, producto, división y módulo de variables y números
         ==============
         Modelo Loop =
         ---------------------
         Instrucciones básicas
         ---------------------
         V <- 0
         V <- V + 1
         V <- V'
         LOOP V
         END
         -------------------
         Instrucciones extra
         -------------------
         V++
         V <- {N, V'}
         V <- {N, V'} {+, *} {N', V''}
         V <- MACRO(arg0, arg1, ..., argn)
         Asignación, suma y producto de variables y números
         ===============
         Modelo While =
         ---------------------
         Instrucciones básicas
         ---------------------
         V <- 0
         V <- V + 1
         V <- V'
         V--
         WHILE V != 0
         END
         -------------------
         Instrucciones extra
         -------------------
         V++
         V <- {N, V'}
         V <- {N, V'} {+,-,*,/,%} {N', V''}
         V <- MACRO(arg0, arg1, ..., argn)
         Asignación, suma, resta, producto y división de variables y números
         */
        if (!_ambito.programa().modoFlexible()) {
            // Comprobaciones comunes
            if (operador == '+') {
                // Todos los modelos comparten la operación V <- V + 1
                // Número distinto de 1
                if (esEntero(op2) && v2 != 1) {
                    Error.deSumaValorNoUnidad();
                }
            }

            // Operación entre variables
            if (esCadena(op2)) {
                Error.deOperacionEntreVariables(operador);
            }
        }

        switch (operador) {
            case '+':
                return v1 + v2;

            case '-':
                int dif = v1 - v2;
                return dif >= 0 ? dif : 0;

            case '*':
                return v1 * v2;

            case '/':
                if (v2 == 0) {
                    return 0;
                }
                return v1 / v2;

            case '%':
                if (v2 == 0) {
                    return 0;
                }
                return v1 % v2;

            default:
                // informar de error
                return 0;
        }
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
                // Si el analizador previo se ha pasado debidamente, no debería
                // llegarse a este punto.
                valor = 0;
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
