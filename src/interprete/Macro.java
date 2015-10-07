package interprete;

import java.util.Hashtable;
import java.util.ArrayList;

public class Macro {

    private static Hashtable<String, Macro> _macros = new Hashtable<>();
    private static String _errorEnExpansion = null;

    private String _id;
    private String _cuerpo;
    private ArrayList<String> _entrada = new ArrayList<>();
    private ArrayList<String> _locales = new ArrayList<>();

    private ArrayList<String> _etiquetas = new ArrayList<>();
    private ArrayList<String> _etiquetasSalto = new ArrayList<>();
    
    private ArrayList<String> _llamadasAMacros = new ArrayList<>();

    public static Macro set(String id) {

        Macro macro = new Macro(id);
        _macros.put(id, macro);

        return macro;
    }

    public static Macro get(String id) {

        return _macros.get(id);
    }

    public static void clear() {

        _macros.clear();
        _errorEnExpansion = null;
    }

    public static String expandir(String vSalida, String idMacro, ArrayList<String> parametros) {

        Macro macro = Macro.get(idMacro);

        if (macro == null) {

            // gestión de errores
            _errorEnExpansion = "La macro " + idMacro + " no está definida.";
            return null;
        }

        // Añadir comprobación del número de parámetros (nP)
        //  - Permitir llamadas con 0 a Nv parámetros, siendo Nv
        //    el número de variables de entrada que se utilizan
        //    en la macro. Si nP > Nv, mostrar error.
        int nP = parametros.size();
        int nV = macro.variablesEntrada().size();

        if (nP > nV) {
            _errorEnExpansion = "La macro " + idMacro + " acepta un máximo de "
                    + nV + " parámetros y ha sido llamado con " + nP + ".";

            return null;
        }

        vSalida = vSalida.toUpperCase();

        String expansion = new String(macro.cuerpo());
        String asignaciones = vSalida + " <- 0\n";

        ArrayList<String> vEntrada = macro.variablesEntrada();
        vEntrada.sort(null);

        ArrayList<String> vLocales = macro.variablesLocales();

        for (int i = 0; i < vEntrada.size(); ++i) {

            String variable = vEntrada.get(i);
            String nuevaVariable = Variable.get(Variable.EVariable.LOCAL).id();

            expansion = expansion.replace(variable, nuevaVariable);

            if (i < parametros.size()) {

                asignaciones += nuevaVariable + " <- " + parametros.get(i).toUpperCase() + "\n";
            }
        }

        for (String variable : vLocales) {

            String nuevaVariable = Variable.get(Variable.EVariable.LOCAL).id();

            expansion = expansion.replace(variable, nuevaVariable);
        }

        /* Se obtiene una nueva variable local y se reemplaza todas las referencias a la variable de salida Y
         * por esta nueva variable
         */
        String variableSalidaLocal = Variable.get(Variable.EVariable.LOCAL).id();
        expansion = "#Expansión de macro " + idMacro + "\n"
                + asignaciones + expansion.replace("VY", variableSalidaLocal);

        if (Programa.modelo() == Programa.Modelos.L) {

            ArrayList<String> etiquetas = macro.etiquetas();
            ArrayList<String> etiquetasSalto = macro.etiquetasSalto();

            /* Reempaza las etiquetas que marcan un objetivo de salto
             */
            Hashtable<String, String> etiquetasReemplazadas = new Hashtable<>();
            for (String etiqueta : etiquetas) {

                // registrar el número de línea de la etiqueta desplazado según el número de línea
                // de la llamada a la macro + el número de asignaciones añadidas al código expandido
                String nuevaEtiqueta = Etiqueta.get().id();
                etiquetasReemplazadas.put(etiqueta, nuevaEtiqueta);

                expansion = expansion.replace(etiqueta, nuevaEtiqueta);
            }

            /* Reemplaza todas las etiquetas que son objetivo de un salto
             */
            String etiquetaSalida = Etiqueta.get().id();
            for (String etiqueta : etiquetasSalto) {

                if (etiquetasReemplazadas.contains(etiqueta)) {

                    expansion = expansion.replace(etiqueta, etiquetasReemplazadas.get(etiqueta));
                } else {

                    expansion = expansion.replace(etiqueta, etiquetaSalida);
                }
            }

            /* Añadimos una última línea con la etiqueta designada como etiqueta de salida de la macro
             * y la asignación a la variable de salida indicada por el usuario
             */
            expansion = expansion + "\n[" + etiquetaSalida + "] " + vSalida + " <- " + variableSalidaLocal;
        } else {

            /* Añadimos una última línea con la asignación a la variable de salida indicada por el usuario
             */
            expansion = expansion + vSalida + " <- " + variableSalidaLocal;
        }

        return expansion + "\n#Fin expansión de macro " + idMacro + "\n";
    }

    public Macro(String id) {

        this._id = id;
    }

    public String id() {

        return _id;
    }

    public String cuerpo() {

        return _cuerpo;
    }

    public void cuerpo(String cuerpo) {

        this._cuerpo = cuerpo;
    }

    public ArrayList<String> variablesEntrada() {

        return _entrada;
    }

    public ArrayList<String> variablesLocales() {

        return _locales;
    }

    public ArrayList<String> etiquetas() {

        return _etiquetas;
    }

    public ArrayList<String> etiquetasSalto() {

        return _etiquetasSalto;
    }

    public void nuevaVariable(String id) {

        char tipo = id.charAt(1);

        switch (tipo) {
            case 'X':
                if (!_entrada.contains(id)) {

                    _entrada.add(id);
                }
                break;

            case 'Z':
                if (!_locales.contains(id)) {

                    _locales.add(id);
                }
                break;

            case 'Y':
                break;

            default:
                System.err.println("Error: Tipo de variable '" + id + "' desconocido.");
        }
    }

    public void nuevaEtiqueta(String id) {

        if (!_etiquetas.contains(id)) {

            _etiquetas.add(id);
        }
    }

    public void nuevaEtiquetaSalto(String id) {

        if (!_etiquetasSalto.contains(id)) {

            _etiquetasSalto.add(id);
        }
    }

    public void nuevaLlamadaAMacro(String id) {

        _llamadasAMacros.add(id);
    }

    public static String errorEnExpansion() {
        return _errorEnExpansion;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("ID: ").append(_id).append("\n");
        sb.append("Cuerpo: ").append(_cuerpo).append("\n");

        sb.append("Variables de entrada: ");
        for (int i = 0; i < _entrada.size(); ++i) {
            sb.append(_entrada.get(i)).append(" ");
        }
        sb.append("\n");

        sb.append("Variables locales: ");
        for (int i = 0; i < _locales.size(); ++i) {
            sb.append(_locales.get(i)).append(" ");
        }
        sb.append("\n");

        sb.append("Etiquetas: ");
        for (int i = 0; i < _etiquetas.size(); ++i) {
            sb.append(_etiquetas.get(i)).append(" ");
        }
        sb.append("\n");
        
        sb.append("Llamadas a macros: ");
        for (int i = 0; i < _llamadasAMacros.size(); ++i) {
            sb.append(_llamadasAMacros.get(i)).append(" ");
        }
        sb.append("\n");

        return sb.toString();
    }

    public static void pintar() {

        System.out.println("Macros");
        _macros.forEach(
                (k, v) -> System.out.println(v)
        );
        System.out.println();
    }

}
