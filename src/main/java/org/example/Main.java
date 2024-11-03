package org.example;

import java.util.ArrayList;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        boolean continua = true, peorCaso = false;

         do {
            Scanner teclado = new Scanner(System.in);
            if (peorCaso) {
                System.out.println("Peor caso:Activado");
            } else {
                System.out.println("Peor caso:Desactivado");
            }

            System.out.println("Menú:");
            System.out.println("1.Crear fichero .tsp aleatorio");
            System.out.println("2.Comprobar todos los dataset");
            System.out.println("3.Comprobar 1 estrategia");
            System.out.println("4.Comparar todas las estrategias");
            System.out.println("5.Comparar 2 estrategias");
            System.out.println("6.Comprobar todas las estrategias");
            System.out.println("7.Activar/Desactivar peor caso");
            System.out.println("8.Comprobar estrategias de un fichero tsp");
            System.out.println("0. Salir");
            System.out.print("Selecciona una opción: ");


            int opcion = teclado.nextInt();
            switch (opcion){
                case 0:
                    continua = !continua;
                    break;
                case 1:
                    Auxiliares.Menu.crearFicheroAleatorioTSP(teclado,peorCaso);
                    break;
                case 2:
                    Auxiliares.Menu.comprobarTodosLosDataset();
                    break;
                case 3:
                    Auxiliares.Menu.comprobarUnaEstrategia(peorCaso);
                    break;
                case 4:
                    Auxiliares.Menu.compararTodasEstrategias(peorCaso);
                    break;
                case 6:
                    Auxiliares.Menu.comprobarTodasEstrategias(teclado,peorCaso);
                    break;




                case 7:
                    peorCaso = !peorCaso;
                    break;

                case  8:
                    Auxiliares.Menu.comprobarEstrategiasFicheroTsp(teclado);
                    break;

            }
        } while (continua);
    }
}