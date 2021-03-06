package com.jim_project.interprete.gestor;

import java.util.ArrayList;
import java.util.HashMap;
import com.jim_project.interprete.Modelo;
import com.jim_project.interprete.Programa;
import com.jim_project.interprete.componente.Ambito;
import com.jim_project.interprete.componente.Etiqueta;
import com.jim_project.interprete.componente.LlamadaAMacro;
import com.jim_project.interprete.componente.Macro;
import com.jim_project.interprete.componente.Variable;
import com.jim_project.interprete.parser.analizadormacros.MacrosParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NotDirectoryException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Clase encargada de gestionar las macros cargadas en el programa.
 *
 * @author Alberto García González
 */
public class GestorMacros extends GestorComponentes {

    private final HashMap<String, Macro> _macros;

    /**
     * Constructor de clase.
     *
     * @param programa Referencia al programa en ejecución.
     */
    public GestorMacros(Programa programa) {
        super(programa);
        _macros = new HashMap<>();
    }

    /**
     * Crea una nueva macro y la añade al gestor.
     *
     * @param id El identificador de la macro.
     * @return Una referencia a la macro recién creada.
     */
    public Macro nuevaMacro(String id) {
        Macro macro = new Macro(id, this);
        _macros.put(macro.id(), macro);

        return macro;
    }

    /**
     * Busca una macro por su identificador.
     *
     * @param id El identificador de la macro.
     * @return Una referencia a la macro buscada, o {@code null} en caso de no
     * ser encontrada en el gestor.
     */
    public Macro obtenerMacro(String id) {
        return _macros.get(id.toUpperCase());
    }

    /**
     * Expande la llamada a macro indicada y devuelve el código resultante de su
     * expansión.
     *
     * @param llamadaAMacro Referencia a la llamada a macro que será expandida.
     * @return Una cadena con el código de la macro expandido, listo para ser
     * insertado en el código desde el que ha sido llamada.
     */
    public String expandir(LlamadaAMacro llamadaAMacro) {
        String separador = System.getProperty("line.separator");
        String idMacro = llamadaAMacro.id();
        ArrayList<String> parametrosEntrada = llamadaAMacro.parametros();
        int numeroLinea = llamadaAMacro.linea();// + desplazamiento;

        String idVariableSalida = llamadaAMacro.idVariableSalida().toUpperCase();
        String idEtiqueta = llamadaAMacro.idEtiqueta();
        if (programa().modelo().tipo() == Modelo.Tipo.L
                && idEtiqueta != null) {
            idEtiqueta = "[" + idEtiqueta + "]\t";
        } else {
            idEtiqueta = "";
        }

        Macro macro = obtenerMacro(idMacro);

        if (macro == null) {
            _programa.error().deMacroNoDefinida(numeroLinea, idMacro);
            return null;
        }
        String ficheroMacro = macro.definidaEn();

        // Añadir comprobación del número de parámetros (nP)
        //  - Permitir llamadas con 0 a Nv parámetros, siendo Nv
        //    el número de variables de entrada que se utilizan
        //    en la macro. Si nP > Nv, mostrar error.
        int nP = parametrosEntrada.size();
        int nV = macro.variablesEntrada().size();

        if (nP != nV) {
            _programa.error().enNumeroParametros(numeroLinea, idMacro, nV, nP);
            return null;
        }

        // Comprobamos que no hay llamadas recursivas directas ni indirectas
        // en la macro a expandir
        /*
         if (hayRecursividadEnMacros(macro)) {
         _programa.error().deRecursividadEnMacros(numeroLinea, id);
         return null;
         }
         */
        String expansion = new String(macro.cuerpo());

        ArrayList<String> variablesEntrada = macro.variablesEntrada();
        variablesEntrada.sort(null);

        ArrayList<String> variablesLocales = macro.variablesLocales();
        Ambito ambitoRaiz = _programa.gestorAmbitos().ambitoRaiz();

        ArrayList<String> etiquetas = macro.etiquetas();
        ArrayList<String> etiquetasSalto = macro.etiquetasSalto();

        HashMap<String, String> reemplazosEntrada = new HashMap<>();
        HashMap<String, String> reemplazosLocales = new HashMap<>();
        HashMap<String, String> reemplazosEtiquetas = null;
        String variableSalidaLocal = null;

        Variable vAuxiliar;
        for (int i = 0; i < variablesEntrada.size(); ++i) {
            String vAntigua = variablesEntrada.get(i).toUpperCase();
            vAuxiliar = ambitoRaiz.gestorVariables().nuevaVariable(Variable.Tipo.LOCAL);
            String vNueva = vAuxiliar.tokenTipo() + "_" + vAuxiliar.indice();

            reemplazosEntrada.put(vAntigua, vNueva);
        }

        for (int i = 0; i < variablesLocales.size(); ++i) {
            String vAntigua = variablesLocales.get(i);
            vAuxiliar = ambitoRaiz.gestorVariables().nuevaVariable(Variable.Tipo.LOCAL);
            String vNueva = vAuxiliar.tokenTipo() + "_" + vAuxiliar.indice();

            reemplazosLocales.put(vAntigua, vNueva);
        }

        if (programa().modelo().tipo() == Modelo.Tipo.L) {
            /* Se tienen en cuenta sólo las etiquetas que indican un objetivo de salto
             */
            reemplazosEtiquetas = new HashMap<>();
            for (String etiqueta : etiquetas) {
                Etiqueta eAuxiliar = ambitoRaiz.gestorEtiquetas().nuevaEtiqueta();
                String nuevaEtiqueta = eAuxiliar.grupo() + "_" + eAuxiliar.indice();
                reemplazosEtiquetas.put(etiqueta, nuevaEtiqueta);
            }
        }

        vAuxiliar = ambitoRaiz.gestorVariables().nuevaVariable(Variable.Tipo.LOCAL);
        variableSalidaLocal = "_" + vAuxiliar.tokenTipo() + "_" + vAuxiliar.indice() + "_";

        // Insertar y reemplazar
        String asignaciones = idVariableSalida + " <- 0" + separador;
        
        String vAntigua;
        String vNueva;
        for (int i = 0; i < variablesEntrada.size(); ++i) {
            vAntigua = variablesEntrada.get(i);
            vNueva = reemplazosEntrada.get(vAntigua);

            asignaciones += vNueva + " <- "
                    + parametrosEntrada.get(i).toUpperCase() + separador;
        }

        expansion = expansion.replace("Y", variableSalidaLocal);

        for (int i = variablesLocales.size() - 1; i >= 0; --i) {
            vAntigua = variablesLocales.get(i);
            vNueva = reemplazosLocales.get(vAntigua);

            expansion = expansion.replace(vAntigua, vNueva);
        }

        for (int i = variablesEntrada.size() - 1; i >= 0; --i) {
            vAntigua = variablesEntrada.get(i);
            vNueva = reemplazosEntrada.get(vAntigua);

            expansion = expansion.replace(vAntigua, vNueva);
        }

        if (reemplazosEtiquetas != null) {
            /* Reempaza las etiquetas que indican un objetivo de salto
             */
            for (String eAntigua : etiquetas) {
                String eNueva = reemplazosEtiquetas.get(eAntigua);
                expansion = expansion.replace(eAntigua, eNueva);
            }

            /* Reemplaza todas las etiquetas que son objetivo de un salto
             */
            Etiqueta eAuxiliar = ambitoRaiz.gestorEtiquetas().nuevaEtiqueta();
            String etiquetaSalida = eAuxiliar.grupo() + "_" + eAuxiliar.indice();
            for (String eAntigua : etiquetasSalto) {
                if (reemplazosEtiquetas.containsKey(eAntigua)) {
                    String eNueva = reemplazosEtiquetas.get(eAntigua);
                    expansion = expansion.replace(eAntigua, eNueva);
                } else {
                    expansion = expansion.replace(eAntigua, etiquetaSalida);
                }
            }
            /* Añadimos una última línea con la etiqueta designada como etiqueta
             * de salida de la macro y la asignación a la variable de salida
             * indicada por el usuario
             */
            expansion = expansion + separador + "[" + etiquetaSalida + "] "
                    + idVariableSalida + " <- " + variableSalidaLocal;
        } else {
            /* Añadimos una última línea con la asignación a la variable de
             * salida indicada por el usuario
             */
            expansion = expansion + idVariableSalida + " <- " + variableSalidaLocal;
        }

        expansion = "# Expansión de " + idMacro + " (" + ficheroMacro + ")"
                + separador + idEtiqueta + asignaciones
                + expansion;
        expansion = expansion.replace("_", "");
        return expansion + "\n# Fin expansión de " + idMacro + separador;
    }

    /**
     * Comprueba el directorio de macros comunes, los directorios de macros de
     * cada modelo especificados en el fichero de configuración y carga en
     * memoria todas las macros comunes y del modelo a simular.
     */
    public void cargarMacros() {
        comprobarDirectoriosMacros();
        if (_programa.argumentos().verbose) {
            System.out.println("Cargando macros");
        }

        if (_programa.estadoOk()) {
            procesarFicherosMacros(_programa.configuracion().rutaMacros());
        }

        if (_programa.estadoOk()) {
            procesarFicherosMacros(_programa.argumentos().modelo.ruta());
        }
    }

    private void procesarFicherosMacros(String rutaMacros) {
        try {
            ArrayList<Path> rutas = new ArrayList<>();
            DirectoryStream<Path> directoryStream
                    = Files.newDirectoryStream(Paths.get(rutaMacros));

            for (Path path : directoryStream) {
                if (path.toFile().isFile()) {
                    rutas.add(path);
                }
            }
            directoryStream.close();

            for (Path p : rutas) {
                if (_programa.estadoOk()) {
                    if (_programa.argumentos().verbose) {
                        System.out.println("   " + p.toAbsolutePath());
                    }

                    String fichero = p.getFileName().toString();
                    String rutaFichero = rutaMacros + "/" + fichero;
                    _programa.ficheroEnProceso(rutaFichero);
                    try {
                        MacrosParser macrosParser
                                = new MacrosParser(new FileReader(rutaFichero), _programa);
                        macrosParser.parse();
                    } catch (FileNotFoundException ex) {
                        _programa.error().alCargarMacros(rutaFichero);
                    }
                }
            }
        } catch (NotDirectoryException ex) {
            _programa.error().alComprobarDirectorio(rutaMacros);
        } catch (IOException ex) {
            _programa.error().alObtenerListaFicherosMacros(rutaMacros);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void comprobarDirectoriosMacros() {
        if (_programa.argumentos().verbose) {
            System.out.println("Comprobando directorios de macros");
        }
        /* Esquema por defecto:
         * macros/
         * ...l/
         * ...loop/
         * ...while/
         */
        comprobarDirectorio(new File(_programa.configuracion().rutaMacros()));
        comprobarDirectorio(new File(_programa.configuracion().rutaMacrosL()));
        comprobarDirectorio(new File(_programa.configuracion().rutaMacrosLoop()));
        comprobarDirectorio(new File(_programa.configuracion().rutaMacrosWhile()));
    }

    private void comprobarDirectorio(File directorio) {
        if (_programa.argumentos().verbose) {
            System.out.println("   " + directorio.getAbsolutePath());
        }

        if (!directorio.exists()) {
            boolean creados = directorio.mkdirs();

            if (!creados) {
                _programa.error().alCrearDirectoriosMacros();
            }
        } else {
            // es un directorio
            if (!directorio.isDirectory()) {
                _programa.error().alComprobarDirectorio(directorio.getAbsolutePath());
            }

            if (!directorio.canRead()) {
                _programa.error().alComprobarAccesoDirectorio(directorio.getAbsolutePath());
            }
        }
    }

    /**
     * Elimina todas las macros almacenadas en el gestor.
     */
    @Override
    public void limpiar() {
        _macros.clear();
    }

    /**
     * Devuelve el número de macros almacenado en el gestor.
     *
     * @return El número de macros almacenado en el gestor.
     */
    @Override
    public int count() {
        return _macros.size();
    }

    /**
     * Comprueba si el gestor está vacío.
     *
     * @return {@code true}, si el gestor está vacío; {@code false}, si contiene
     * alguna macro.
     */
    @Override
    public boolean vacio() {
        return _macros.isEmpty();
    }
}
