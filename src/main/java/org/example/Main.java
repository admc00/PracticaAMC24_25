package org.example;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        boolean continua = true, peorCaso = false;
        var opcion = -1;
        do {
            try {
                if (peorCaso) {
                    System.out.println("Peor caso:Activado");
                } else {
                    System.out.println("Peor caso:Desactivado");
                }

                System.out.println("Menú:");
                System.out.println("1.Crear fichero .tsp aleatorio");
                System.out.println("2.Comprobar todos los dataset");
                System.out.println("3.Estudiar 1 estrategia");
                System.out.println("4.Comparar 2 estrategias");
                System.out.println("5.Comparar todas las estrategias");
                System.out.println("6.Comprobar todas las estrategias");
                System.out.println("7.Activar/Desactivar peor caso");
                System.out.println("8.Comprobar estrategias de un fichero tsp");
                System.out.println("0. Salir");
                System.out.print("Selecciona una opción: ");

                opcion = teclado.nextInt();
                switch (opcion) {
                    case 0 -> continua = !continua;
                    case 1 -> Auxiliares.Menu.crearFicheroAleatorioTSP(teclado, peorCaso);
                    case 2 -> Auxiliares.Menu.comprobarTodosLosDataset();
                    case 3 -> Auxiliares.Menu.estudiarUnaEstrategia(teclado, peorCaso);
                    case 4 -> Auxiliares.Menu.compararDosEstrategias(teclado, peorCaso);
                    case 5 -> Auxiliares.Menu.compararTodasEstrategias(peorCaso);
                    case 6 -> Auxiliares.Menu.comprobarTodasEstrategias(teclado, peorCaso);
                    case 7 -> peorCaso = !peorCaso;
                    case 8 -> Auxiliares.Menu.comprobarEstrategiasFicheroTsp(teclado);
                    default -> System.out.println("Opcion no valida");
                }
            } catch (Exception e) { System.out.println("\n\n\n HA OCURRIDO UN ERROR INESPERADO \n\n\n");}
        } while (continua);
        teclado.close();
    }
}