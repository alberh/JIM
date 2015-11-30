package com.jim_project.interprete.componente;

import com.jim_project.interprete.util.Error;
import com.jim_project.interprete.util.gestor.GestorVariables;

public class Variable extends Componente {

    public enum Tipo {

        ENTRADA, LOCAL, SALIDA
    }

    private int _valor;
    private Tipo _tipo;
    private int _indice;
    
    public Variable(String id, GestorVariables gestorVariables) {
        this(id, 0, gestorVariables);
    }

    public Variable(String id, int valor, GestorVariables gestorVariables) {
        super(Variable.normalizarID(id), gestorVariables);

        _valor = valor;
        _tipo = Variable.obtenerTipo(_id);
        _indice = Variable.obtenerIndice(_id);
    }

    public Tipo tipo() {
        return _tipo;
    }

    public int indice() {
        return _indice;
    }

    public int valor() {
        return _valor;
    }

    public void valor(int nuevoValor) {
        _valor = Math.max(0, nuevoValor);
    }

    public void incremento() {
        _valor++;
    }

    public void decremento() {
        if (_valor > 0) {
            _valor--;
        }
    }

    @Override
    public String toString() {
        return "(" + _id + ", " + _valor + ")";
    }

    /* Métodos estáticos
     */
    public static String normalizarID(String id) {
        id = id.toUpperCase();
        int len = id.length();

        if (len > 0) {
            if (!id.contains("Y") && (len == 1 || (len == 2 && id.charAt(0) == 'V'))) {
                return id + "1";
            } else {
                return id;
            }
        } else {
            Error.deIdentificadorDeVariableVacio();
            return "";
        }
    }

    public static int obtenerIndice(String id) {
        id = Variable.normalizarID(id);
        
        if (id.equals("Y") || id.equals("VY")) {
            return 1;
        }

        if (id.length() > 1) {
            int indiceInicio;

            if (Character.isDigit(id.charAt(1))) {
                indiceInicio = 1;
            } else {
                indiceInicio = 2;
            }
            
            try {
                return Integer.parseInt(id.substring(indiceInicio));
            } catch (NumberFormatException ex) {
                Error.alObtenerIndiceDeVariable(id);
            }
        } else {
            return 1;
        }

        return 0;
    }

    public static Tipo obtenerTipo(String id) {
        /* Después del refactor? Porque al final hice que se pusieran las V
         * o las L según si estaba expandiendo o no...
         char charTipo = Variable.normalizarID(id).charAt(0);
         char charTipo;
         if (id.charAt(0) == 'V') {
         charTipo = Variable.normalizarID(id).charAt(1);
         } else {
         charTipo = Variable.normalizarID(id).charAt(0);
         }

         switch (charTipo) {
         case 'X':
         return Tipo.ENTRADA;

         case 'Y':
         return Tipo.SALIDA;

         case 'Z':
         return Tipo.LOCAL;

         default:
         Error.deTipoDeVariableNoValido(charTipo);
         return null;
         }
         */

        String tipo;
        if (id.charAt(0) == 'V') {
            tipo = Variable.normalizarID(id).substring(0, 2);
        } else {
            tipo = Variable.normalizarID(id).substring(0, 1);
        }

        switch (tipo) {
            case "X":
            case "VX":
                return Tipo.ENTRADA;

            case "Y":
            case "VY":
                return Tipo.SALIDA;

            case "Z":
            case "VZ":
                return Tipo.LOCAL;

            default:
                Error.deTipoDeVariableNoValido(tipo);
                return null;
        }
    }
}
