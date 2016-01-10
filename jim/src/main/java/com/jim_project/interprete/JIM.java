package com.jim_project.interprete;

import com.jim_project.interprete.util.Configuracion;
import com.jim_project.interprete.util.Error;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Clase principal de la aplicación en su versión para consola.
 *
 * @author Alberto García González
 */
public class JIM {

    private static Programa _programa;
    private static Configuracion _configuracion;

    /**
     * Método de entrada de la aplicación.
     *
     * @param args Los argumentos del programa.
     */
    public static void main(String[] args) {
        _configuracion = new Configuracion().cargar();
        _programa = new Programa(_configuracion);

        // Para pruebas
        // args = new String[]{"asd.txt"};

        /* java Jim fichero modelo [t|T|m|v|e] [param1 [param2 [...]]]
         *             0      1        2       2/3     3/4
         */
        switch (args.length) {
            case 0:
                bienvenida();
                break;

            case 1:
                if (args[0].equalsIgnoreCase("--help")) {
                    ayuda();
                } else {
                    try {
                        String[] argsFichero = obtenerArgumentosDeFichero(args[0]);
                        prepararYLanzar(argsFichero);
                    } catch (FileNotFoundException ex) {
                        Error.deFicheroNoExistente(args[0]);
                    } catch (Exception ex) {
                        Error.deParametrosNoIndicadosEnFichero(args[0]);
                    }
                }
                break;

            default:
                prepararYLanzar(args);
        }
    }

    private static void prepararYLanzar(String[] args) {
        int numArgs = args.length;
        ArgumentosPrograma argsPrograma = new ArgumentosPrograma();
        argsPrograma.fichero = args[0];
        argsPrograma.modelo = new Modelo(args[1], _configuracion);
        argsPrograma.objetivo = Programa.Objetivo.INTERPRETAR;

        if (argsPrograma.modelo.tipo() != null) {
            boolean ok = true;

            if (numArgs > 2) {
                int siguienteIndice = 2;
                String cadenaMods = args[siguienteIndice];

                // Asignar modificadores
                if (!Character.isDigit(cadenaMods.charAt(0))) {
                    siguienteIndice = 3;

                    for (int i = 0; i < cadenaMods.length(); ++i) {
                        switch (cadenaMods.charAt(i)) {
                            case 't':
                                argsPrograma.traza = true;
                                break;

                            case 'T':
                                argsPrograma.traza = true;
                                argsPrograma.trazarMacros = true;
                                break;
                            /*
                             case 'f':
                             argsPrograma.modoFlexible = true;
                             break;
                             */
                            case 'm':
                                argsPrograma.macrosPermitidas = true;
                                break;
                            case 'v':
                                argsPrograma.verbose = true;
                                break;
                            case 'e':
                                argsPrograma.objetivo = Programa.Objetivo.EXPANDIR;
                                break;
                            default:
                                Error.deModificadorNoValido(cadenaMods.charAt(i));
                                ok = false;
                        }
                    }
                }

                // Asignar parámetros entrada
                if (siguienteIndice < numArgs && ok) {
                    argsPrograma.parametros = new String[numArgs - siguienteIndice];
                    int cont = 0;
                    for (int i = siguienteIndice; i < numArgs && ok; ++i) {
                        try {
                            Integer.parseInt(args[i]);
                            argsPrograma.parametros[cont] = args[i];
                        } catch (Exception ex) {
                            Error.deParametroNoValido(args[i]);
                            ok = false;
                        }

                        cont++;
                    }
                }
            }

            if (ok) {
                if (argsPrograma.objetivo == Programa.Objetivo.INTERPRETAR) {
                    iniciar(argsPrograma);
                } else {
                    iniciarExpansionMacros(argsPrograma);
                }
            }
        }
    }

    private static String[] obtenerArgumentosDeFichero(String nombreFichero)
            throws Exception {

        String[] nuevosArgs = null;
        File fichero = new File(nombreFichero);

        if (!fichero.exists()) {
            throw new FileNotFoundException();
        }

        try (Scanner sc = new Scanner(fichero)) {
            if (sc.hasNextLine()) {
                String linea = sc.nextLine();
                String[] argsFichero = linea.trim().split("[\t ]+");

                int numArgsFichero = argsFichero.length;
                if (numArgsFichero > 2
                        && argsFichero[0].equals("#")
                        && argsFichero[1].equalsIgnoreCase("args:")) {

                    // tamaño = todo - 2 (#, args:) + 1 (nombre del fichero)
                    nuevosArgs = new String[numArgsFichero - 1];
                    nuevosArgs[0] = nombreFichero;
                    for (int i = 0; i < nuevosArgs.length - 1; ++i) {
                        nuevosArgs[i + 1] = argsFichero[i + 2];
                    }
                } else {
                    throw new Exception();
                }
            }
        }

        return nuevosArgs;
    }

    private static void iniciar(ArgumentosPrograma argumentos) {
        _programa.argumentos(argumentos);
        try {
            _programa.iniciar();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        if (_programa.estadoOk()) {
            if (!argumentos.traza) {
                if (_programa.verbose()) {
                    System.out.print("Resultado: ");
                }
                System.out.println(_programa.resultado());
            } else {
                if (_programa.verbose()) {
                    System.out.println("Traza:");
                }
                System.out.println(_programa.traza());
            }
        }
    }

    private static void iniciarExpansionMacros(ArgumentosPrograma argumentos) {
        _programa.argumentos(argumentos);

        try {
            String expansion = _programa.iniciar();
            if (_programa.estadoOk()) {
                if (_programa.verbose()) {
                    System.out.println("Programa tras la expansión:");
                }
                System.out.println(expansion);
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void bienvenida() {
        System.out.println("JIM - Intérprete de Modelos");
        System.out.println("Versión " + _configuracion.version());
        System.out.println("Para ver la ayuda, utilice el argumento --help");
    }

    private static void ayuda() {
        System.out.println("Uso:");
        System.out.println("java Jim fichero");
        //System.out.println("java Jim fichero modelo [t|f|m|v|e] [param1 [param2 [...]]]");
        System.out.println("java Jim fichero modelo [t|T|m|v|e] [param1 [param2 [...]]]");
        System.out.println();
        System.out.println("t: Muestra la traza del programa.");
        System.out.println("T: Muestra la traza del programa, incluyendo las llamadas a macro.");
        //System.out.println("f: Activa el modo flexible.");
        System.out.println("m: Activa la ejecución de macros.");
        System.out.println("v: Hace la salida del programa más detallada.");
        System.out.println("e: Expande las macros del programa y muestra el código "
                + "resultante en pantalla. Se ignorarán la mayoría de modificadores y "
                + "los parámetros de entrada.");
        System.out.println();
        System.out.println("Ejemplos:");
        System.out.println("\tjava JIM p1");
        System.out.println("\tjava JIM p2 loop 1 2");
        System.out.println("\tjava JIM p3 while tm 2 3 5");
        System.out.println();
        System.out.println("Para ejecutar el programa indicando sólo el "
                + "fichero, debe especificar los argumentos de entrada en la "
                + "primera línea del mismo mediante la siguiente notación:");
        //System.out.println("\t# args: modelo [t|f|m|v] [param1 [param2 [...]]]");
        System.out.println("\t# args: modelo [t|T|m|v|e] [param1 [param2 [...]]]");
        System.out.println("Si indica los argumentos en la llamada al programa, "
                + "no se tendrán en cuenta los del fichero.");
    }
}
