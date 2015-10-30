package org.alberto.interprete;

import org.alberto.interprete.util.Configuracion;

/* Cambio futuro: para las condicionales, definir en gramáticas las expresiones lógicas y permitir != N, N es un natural, menor que,
 * mayor que, etc...

 Apuntes cita con Salguero:
 Preparar memoria, importancia a diagramas.
 Resaltado de código en el editor
 Mirar plataforma Rodin
 Mirar licencias jflex y byacc/j. Pensar en licencia para el TFG.
 Planificación: tiempos memoria, diseño, programación, etc
 */

/* Uso: jim modelo fichero [ex|extendido] [param1 [param2 [...]]]
 *            0       1          2          2/3     3/4
 */
public class Main {

    public static void main(String[] args) {
        bienvenida();

        if (args.length >= 2) {
            Configuracion.cargar();

            String cadenaModelo = args[0];
            String fichero = args[1];

            Programa.Modelos modelo = obtenerModelo(cadenaModelo);
            Programa.ModoInstrucciones modoInstrucciones
                    = Programa.ModoInstrucciones.NORMAL;

            if (modelo != null) {
                int[] parametros = null;

                if (args.length > 2) {
                    int indiceParametros = 2;

                    if (args[2].equals("ex") || args[2].equals("extendido")) {
                        modoInstrucciones = Programa.ModoInstrucciones.EXTENDIDO;
                        indiceParametros = 3;
                    }

                    if (args.length > indiceParametros) {
                        parametros = new int[args.length - indiceParametros];

                        int cont = 0;
                        for (int i = indiceParametros; i < args.length; ++i) {
                            try {
                                parametros[cont] = Integer.parseInt(args[i]);
                            } catch (Exception ex) {
                                parametros[cont] = 0;
                            }

                            cont++;
                        }
                    }
                }

                iniciar(modelo, modoInstrucciones, fichero, parametros);
            } else {
                // Añadir soporte para expansión de macros en terminal

                // Error.deModeloNoValido(cadenaModelo);
                System.err.println("Error x: modelo no válido.");
            }
        } else {
            System.out.println("Uso: jim modelo fichero [param1 [param2 [...]]]");
        }
    }

    public static void iniciar(Programa.Modelos modelo,
            Programa.ModoInstrucciones modo,
            String fichero,
            int[] parametros) {
        
        Programa.cargar(fichero, modelo, modo,
                Programa.Etapa.EXPANDIENDO_MACROS/*EJECUTANDO*/);
        Programa.iniciar(parametros);

        if (Programa.estadoOk()) {
            System.out.println();
            System.out.println("Resultado: " + Programa.resultado());
        }
    }

    public static void iniciarExpansionMacros(Programa.Modelos modelo,
            Programa.ModoInstrucciones modo,
            String fichero) {
        Programa.cargar(fichero, modelo, modo,
                Programa.Etapa.EXPANDIENDO_MACROS);
        Programa.iniciarExpansionMacros();

        if (Programa.estadoOk()) {
            System.out.println();
            System.out.println("Programa tras la expansión");
            System.out.println();
            System.out.println(Programa.obtenerPrograma());
        }
    }

    private static Programa.Modelos obtenerModelo(String modelo) {
        switch (modelo.toUpperCase()) {
            case "L":
                return Programa.Modelos.L;

            case "LOOP":
                return Programa.Modelos.LOOP;

            case "WHILE":
                return Programa.Modelos.WHILE;

            default:
                return null;
        }
    }

    public static void bienvenida() {
        System.out.println("JIM - Intérprete de Modelos");
        System.out.println("Intérprete de modelos de computación L, LOOP y WHILE");
        System.out.println("Versión " + Configuracion.version());
        System.out.println();
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
