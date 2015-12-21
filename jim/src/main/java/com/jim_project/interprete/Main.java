package com.jim_project.interprete;

import com.jim_project.interprete.util.Configuracion;
import com.jim_project.interprete.util.Error;

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

            String fichero = args[0];
            String cadenaModelo = args[1];

            Modelo modelo = new Modelo(cadenaModelo);

            if (modelo.tipo() != null) {
                boolean modoFlexible = false;
                boolean macrosPermitidas = true;
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
        } else {
            System.out.println("Uso: jim fichero modelo [ex|extendido] [param1 [param2 [...]]]");
        }
    }

    public static void iniciar(
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

    public static void iniciarExpansionMacros(
            String fichero,
            Modelo modelo,
            boolean modoFlexible,
            boolean macrosPermitidas
    ) {

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
