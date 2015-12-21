package com.jim_project.interprete;

import com.jim_project.interprete.util.Configuracion;
import com.jim_project.interprete.util.Error;
import java.util.Scanner;

/* Cambio futuro: para las condicionales, definir en gramáticas las expresiones lógicas y permitir != N, N es un natural, menor que,
 * mayor que, etc...

 Apuntes cita con Salguero:
 Preparar memoria, importancia a diagramas.
 Resaltado de código en el editor
 Mirar plataforma Rodin
 Mirar licencias jflex y byacc/j. Pensar en licencia para el TFG.
 Planificación: tiempos memoria, diseño, programación, etc
 */

/* Uso: jim fichero modelo [ex|extendido] [param1 [param2 [...]]]
 *             0      1          2          2/3     3/4
 */
public class Main {

    public static void main(String[] args) {
        Configuracion.cargar();

        switch (args.length) {
            case 0:
                bienvenida();
                break;

            case 1:
                if (args[0].equalsIgnoreCase("--help")) {
                    ayuda();
                } else {
                    cargarArgumentosDesdeFichero(args);
                }
                break;

            default:
                cargarConArgumentos(args);
        }
    }

    private static void cargarResto(String[] args) {
        Modelo modelo = new Modelo(argsFichero[2]);
        if (modelo.tipo() != null) {
            boolean mostrarTraza = false;
            boolean modoFlexible = false;
            boolean macrosPermitidas = true;
            boolean verbose = false;
            String[] parametros = null;
            boolean ok = true;

            if (nA > 3) {
                String arg = argsFichero[3];
                int siguienteIndice = 3;
                // Asignar modificadores
                if (!Character.isDigit(arg.charAt(0))) {
                    siguienteIndice = 4;

                    for (int i = 0; i < arg.length(); ++i) {
                        System.out.println("Cargando modificador " + arg.charAt(i));
                        switch (new String(arg.charAt(i))) {
                            case 't':
                                mostrarTraza = true;
                                break;
                            case 'f':
                                modoFlexible = true;
                                break;
                            case 'm':
                                macrosPermitidas = true;
                                break;
                            case 'v':
                                verbose = true;
                                break;
                            default:
                                Error.deModificadorNoValido(arg.charAt(i));
                                ok = false;
                        }
                    }
                }

                // Asignar parámetros entrada
                if (siguienteIndice < nA && ok) {
                    parametros = new String[nA - siguienteIndice];
                    int cont = 0;
                    for (int i = siguienteIndice; i < nA && ok; ++i) {
                        try {
                            Integer.parseInt(args[i]);
                            parametros[cont] = args[i];
                        } catch (Exception ex) {
                            Error.deParametroNoValido(args[i]);
                            ok = false;
                        }

                        cont++;
                    }
                }
            }

            if (ok) {
                iniciar(fichero, modelo, modoFlexible, macrosPermitidas, parametros);
            }
        }
    }

    private static String[] cargarArgumentosDesdeFichero(String[] args) {
        String fichero = args[0];

        // Búsqueda de parámetros
        Scanner sc = new Scanner(fichero);
        if (sc.hasNextLine()) {
            String linea = sc.nextLine();
            String[] argsFichero = linea.split(" ");

            int nA = argsFichero.length;
            if (nA > 2
                    && argsFichero[0].equals("#")
                    && argsFichero[1].equalsIgnoreCase("args:")) {

                // LLamada a cargarResto con la lista de parámetros
                // menos los dos primeros (#, args:)
                String[] nuevosArgs = new String[]
            } else {
                Error.deParametrosNoIndicados();
            }
        }
        sc.close();
    }

    private static void cargarConArgumentos(String[] args) {
        Configuracion.cargar();
        String fichero = args[0];
        Modelo modelo = new Modelo(args[1]);

        if (modelo.tipo() != null) {
            boolean mostrarTraza = false;
            boolean modoFlexible = false;
            boolean macrosPermitidas = true;
            boolean verbose = false;
            String[] parametros = null;
            boolean ok = true;

            if (args.length > 2) {
                int indiceParametros = 2;

                if (args[2].equals("ex") || args[2].equals("extendido")) {
                    modoFlexible = true;
                    indiceParametros = 3;
                }

                if (args.length > indiceParametros) {
                    parametros = new String[args.length - indiceParametros];

                    int cont = 0;
                    for (int i = indiceParametros; i < args.length && ok; ++i) {
                        try {
                            Integer.parseInt(args[i]);
                            parametros[cont] = args[i];
                        } catch (Exception ex) {
                            Error.deParametroNoValido(args[i]);
                            ok = false;
                        }

                        cont++;
                    }
                }
            }

            if (ok) {
                iniciar(fichero, modelo, modoFlexible, macrosPermitidas, parametros);
            }
        }
    }

    private static void iniciar(
            String fichero,
            Modelo modelo,
            boolean modoFlexible,
            boolean macrosPermitidas,
            String[] parametros) {

        Programa programa = new Programa(fichero,
                modelo,
                Programa.Objetivo.EJECUTAR,
                modoFlexible,
                macrosPermitidas);
        programa.iniciar(parametros);

        if (programa.estadoOk()) {
            System.out.println();
            System.out.println("Resultado: " + programa.resultado());
        }
    }

    private static void iniciarExpansionMacros(
            String fichero,
            Modelo modelo,
            boolean modoFlexible,
            boolean macrosPermitidas) {

        Programa programa = new Programa(fichero,
                modelo,
                Programa.Objetivo.EJECUTAR,
                modoFlexible,
                macrosPermitidas);

        programa.iniciarExpansionMacros();

        if (programa.estadoOk()) {
            System.out.println();
            System.out.println("Programa tras la expansión");
            System.out.println();
            System.out.println(programa);
        }
    }

    private static void bienvenida() {
        System.out.println("JIM - Intérprete de Modelos");
        System.out.println("Versión " + Configuracion.version());
        System.out.println("Para ver la ayuda: java Jim --help");
    }

    private static void ayuda() {
        System.out.println("Uso:");
        System.out.println("java Jim fichero");
        System.out.println("java Jim fichero modelo [t|f|m|v] [param1 [param2 [...]]]");
        System.out.println();
        System.out.println("t: Muestra la traza del programa.");
        System.out.println("f: Activa el modo flexible.");
        System.out.println("m: Activa la ejecución de macros.");
        System.out.println("v: Hace la salida del programa más detallada.");
        System.out.println();
        System.out.println("Ejemplos:");
        System.out.println("\tjava Jim p1 l");
        System.out.println("\tjava Jim p2 loop 1 2");
        System.out.println("\tjava Jim p3 while tm 2 3 5");
        System.out.println();
        System.out.println("Para ejecutar el programa indicando sólo el "
                + "fichero, debe especificar los argumentos de entrada en la "
                + "primera línea del mismo mediante la siguiente notación:");
        System.out.println("\t# args: modelo [t|f|m|v] [param1 [param2 [...]]]");
        System.out.println("Si indica los argumentos en la llamada al programa, "
                + "no se tendrán en cuenta los del fichero.");
    }

    /*
     public static void pruebaExpansion(int[] parametros) {
     String programa = "ejemplos/entradaExp.txt";
     Programa.cargar(programa, Programa.Modelos.L);

     Programa.iniciarExpansionMacros();

        
     System.out.println("====================");
     System.out.println("Estado de la memoria");
     System.out.println("====================");
     // Programa.imprimirComponentes();
        

     System.out.println("====================");

     Programa.imprimirPrograma();
     System.out.println("====================");

     // Macro.pintar();
     }

     public static void pruebasL(int[] parametros) {
     String programa = "ejemplos/entradaL.txt";
     Programa.cargar(programa, Programa.Modelos.L);

     restoPrueba(parametros);
     }

     public static void pruebasLoop(int[] parametros) {
     String programa = "ejemplos/entradaLoop.txt";
     Programa.cargar(programa, Programa.Modelos.LOOP);

     restoPrueba(parametros);
     }

     public static void pruebasWhile(int[] parametros) {
     String programa = "ejemplos/entradaWhile.txt";
     Programa.cargar(programa, Programa.Modelos.WHILE);

     restoPrueba(parametros);
     }

     private static void restoPrueba(int[] parametros) {
     Programa.iniciar(parametros);

     System.out.println("====================");
     System.out.println("Estado de la memoria");
     System.out.println("====================");

     // Programa.imprimirComponentes();
     System.out.println("====================");

     System.out.println("Resultado: " + Programa.resultado());

     // Programa.imprimirPrograma();
     }

     /*
     public static void pruebasVariables() {

     Variable.set("x33");
     Variable.set("x1", 7);
     Variable.set("z7");
     Variable.set("z5", 9);
     Variable.set("Y", 5);

     Variable v = Variable.get("x1");
     System.out.println(v);
     v = Variable.get("z5");
     System.out.println(v);
     v = Variable.get("y");
     System.out.println(v);
     System.out.println();

     v = Variable.get(EVariable.ENTRADA);
     System.out.println(v);
     v = Variable.get(EVariable.LOCAL);
     System.out.println(v);
     v = Variable.get(EVariable.SALIDA);
     System.out.println(v);
     System.out.println();

     Variable.clear();
     v = Variable.get(EVariable.ENTRADA);
     v.valor(77);
     System.out.println(v.id() + ", " + v.valor());
     v = Variable.get(EVariable.LOCAL);
     System.out.println(v);
     v = Variable.get(EVariable.SALIDA);
     System.out.println(v);
     System.out.println();

     Variable.pintar();
     System.out.println();
     }

     public static void pruebasEtiquetas() {

     Etiqueta.set("l3", 3);
     Etiqueta.set("l0", 55);

     Etiqueta e = Etiqueta.get("l3");
     System.out.println(e);
     e = Etiqueta.get("l0");
     System.out.println(e.id() + ", " + e.linea());
     System.out.println();

     Etiqueta.pintar();
     System.out.println();
     }

     // RE-HACER LAS DOS SIGUIENTES
     public static void pruebaPrevioParser(String[] args) {

     System.out.println("Ejecutando pruebaPrevioParser");
     System.out.println("=================");

     pruebaPrevioParser analizador = new pruebaPrevioParser(AbrirLector(args)) ;
     analizador.parse();
     }

     public static void pruebaspruebaPrevioParser() {

     String[] args = { "entradapruebaPrevioParser.txt" };

     pruebaPrevioParser(args);
     Programa.imprimirEstado();
     }
     */
}
