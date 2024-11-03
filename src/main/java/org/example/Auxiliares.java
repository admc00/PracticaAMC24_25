package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Auxiliares {

    private Auxiliares(){}

    public static ArrayList<Punto> quickSort(ArrayList<Punto> puntos, int low, int high) {

        if (low < high) {
            int pi = partition(puntos, low, high);

            quickSort(puntos, low, pi - 1);
            quickSort(puntos, pi + 1, high);
        }
        return puntos;
    }

    public static int partition(ArrayList<Punto> puntos, int low, int high) {
        Punto pivot = puntos.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (compare(puntos.get(j), pivot) <= 0) {
                i++;
                swap(puntos, i, j);
            }
        }

        swap(puntos, i + 1, high);
        return i + 1;
    }

    public static double compare(Punto p1, Punto p2) {
        // Comparar los puntos según algún criterio, por ejemplo, comparar las coordenadas x o y.
        // Devolver un número negativo si p1 es menor que p2,
        // devolver 0 si p1 es igual a p2,
        // devolver un número positivo si p1 es mayor que p2.

        // Ejemplo de comparación por coordenada x:
        return Double.compare(p1.getX(), p2.getX());
    }

    public static void swap(ArrayList<Punto> puntos, int i, int j) {
        Punto temp = puntos.get(i);
        puntos.set(i, puntos.get(j));
        puntos.set(j, temp);
    }
    public class Menu {

        private Menu(){};

        public static void crearFicheroAleatorioTSP(Scanner escaner, boolean peorCaso) {
            System.out.println("Introduce el tamaño del dataset: ");
            int size = escaner.nextInt();
            Ficheros.crearArchivoTSP(size,peorCaso);
        }

        public static void comprobarTodosLosDataset() {
            System.out.println("Comprobando todos los dataset");

            ArrayList<String> nomCarpetas = Ficheros.leerNombreCarpetas();
            for (String nombre : nomCarpetas) {
                System.out.println(nombre);
                System.out.println("Estrategia         Punto1 Punto2 distancia calculadas tiempo");
                comprobarEstrategiasFicheroTspAux(nombre, null);
            }

        }

        public static void comprobarEstrategiasFicheroTsp(Scanner escaner) {
            System.out.println("nombre/ruta del fichero:");
            var fichero = escaner.next();
            var listaFicheros = Ficheros.leerNombreCarpetas();

            if(!listaFicheros.contains(fichero)){fichero = listaFicheros.get(0).toString();}

            System.out.println(fichero);
            //System.out.printf("%s")
            comprobarEstrategiasFicheroTspAux(fichero,null);
        }

        //TODO:Hacer privado
        private static void comprobarEstrategiasFicheroTspAux(String nombre, ArrayList puntos) {
            ArrayList<Punto> dataset = puntos == null ? Ficheros.leerFichero(nombre) : puntos;
            System.out.printf("%-25s %-30s %-30s %-20s %-20s %-20s%n","Estrategia","Punto1","Punto2","Distancia","Calculadas","Tiempo");

            Distancia bex = BusquedaExhaustiva.distanciaMinima(dataset);
            System.out.printf("%-25s %-30s %-30s %-20.4f %-20d %-20.1f%n","Busqueda exhaustiva", bex.getX1().getID()+"("+bex.getX1().getX() + ", " + bex.getX2().getY()+")", bex.getX2().getID()+"("+bex.getX2().getX() + ", " + bex.getX2().getY()+")",bex.getDistancia(),0,BusquedaExhaustiva.getTiempo());


            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size()-1);


            Distancia dyv = DivideYVenceras.distanciaMinima(datasetOrd, 0, dataset.size()-1);
            System.out.printf("%-25s %-30s %-30s %-20.4f %-20d %-20.1f%n","Divide y venceras", dyv.getX1().getID()+"("+dyv.getX1().getX() + ", " + dyv.getX2().getY()+")", dyv.getX2().getID()+"("+dyv.getX2().getX() + ", " + dyv.getX2().getY()+")",dyv.getDistancia(),0,DivideYVenceras.getTiempo());

            Distancia dyvm = DivideYVencerasMejorado.distanciaMinima(datasetOrd, 0, dataset.size()-1);
            System.out.printf("%-25s %-30s %-30s %-20.4f %-20d %-20.1f%n","Divide y venceras mejorado", dyvm.getX1().getID()+"("+dyvm.getX1().getX() + ", " + dyvm.getX2().getY()+")", dyvm.getX2().getID()+"("+dyvm.getX2().getX() + ", " + dyvm.getX2().getY()+")",dyvm.getDistancia(),0,DivideYVencerasMejorado.getTiempo());

            Distancia bexp = BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
            System.out.printf("%-25s %-30s %-30s %-20.4f %-20d %-20.1f%n","Busqueda exhaustiva poda", bexp.getX1().getID()+"("+bexp.getX1().getX() + ", " + bexp.getX2().getY()+")", bexp.getX2().getID()+"("+bexp.getX2().getX() + ", " + bexp.getX2().getY()+")",bexp.getDistancia(),0,BusquedaExhaustivaPoda.getTiempo());

        }

        public static void comprobarUnaEstrategia(boolean peorCaso) {
            boolean salir = false;
            Scanner teclado = new Scanner(System.in);
            while(!salir){
                int opcion = -1;
                System.out.println("Estrategia a estudiar:");
                System.out.println("1.Busqueda exhaustiva");
                System.out.println("2.Divide y venceras");
                System.out.println("3.Divide y venceras mejorado");
                System.out.println("4.Busqueda exhaustiva poda");
                System.out.println("0. Salir al menu principal");
                System.out.print("Selecciona una opción: ");

                opcion = teclado.nextInt();
                switch (opcion){
                    case 0:
                        salir = true;
                        break;
                    case 1:
                        System.out.println("Busqueda exhaustiva");

                        System.out.println("Tiempos de ejecucion promedio");
                        System.out.printf("%-10s %20s%n", "Talla", "Tiempo");
                        for (int i = 500; i <= 5000; i+=500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            BusquedaExhaustiva.distanciaMinima(dataset);
                            System.out.printf("%-10d %20.1f%n",i,DivideYVenceras.getTiempo());
                        }
                        break;
                    case 2:
                        System.out.println("Divide y venceras");

                        System.out.println("Tiempos de ejecucion promedio");
                        System.out.printf("%-10s %20s%n", "Talla", "Tiempo");
                        for (int i = 500; i <= 5000; i+=500){
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size()-1);
                            DivideYVenceras.distanciaMinima(datasetOrd, 0, datasetOrd.size()-1);
                            System.out.printf("%-10d %20.1f%n",i,DivideYVenceras.getTiempo());
                        }
                        break;
                    case 3:
                        System.out.println("Divide y venceras mejorado");

                        System.out.println("Tiempos de ejecucion promedio");
                        System.out.printf("%-10s %20s%n", "Talla", "Tiempo");
                        for (int i = 500; i <= 5000; i+=500){
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size()-1);
                            DivideYVencerasMejorado.distanciaMinima(datasetOrd, 0, datasetOrd.size()-1);
                            System.out.printf("%-10d %20.1f%n",i,DivideYVenceras.getTiempo());
                        }
                        break;
                    case 4:
                        System.out.println("Busqueda exhaustiva poda");

                        System.out.println("Tiempos de ejecucion promedio");
                        System.out.printf("%-10s %20s%n", "Talla", "Tiempo");
                        for (int i = 500; i <= 5000; i+=500){
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size()-1);
                            BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                            System.out.printf("%-10d %20.1f%n",i,DivideYVenceras.getTiempo());
                        }
                        break;

                }
            }

        }

        public static void compararTodasEstrategias(boolean peorCaso){
            System.out.println("Tiempos de ejecucion promedio");
            System.out.printf("%35s %20s %20s %20s%n","Busqueda exhaustiva", "Divide y venceras", "Divide y venceras mejorado", "Busqueda exhaustiva poda");
            System.out.printf("%5s %25s %20s %20s %20s%n","Talla","Tiempo","Tiempo","Tiempo","Tiempo");
            for (int i = 500; i <= 5000; i+=500){
                ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                BusquedaExhaustiva.distanciaMinima(dataset);
                ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size()-1);
                DivideYVenceras.distanciaMinima(datasetOrd, 0, datasetOrd.size()-1);
                DivideYVencerasMejorado.distanciaMinima(datasetOrd, 0, datasetOrd.size()-1);
                BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n",i,BusquedaExhaustiva.getTiempo(),DivideYVenceras.getTiempo(),DivideYVencerasMejorado.getTiempo(),BusquedaExhaustivaPoda.getTiempo());
            }
        }

        public static void comprobarTodasEstrategias(Scanner teclado, boolean peorCaso){
            System.out.println("Introduce la talla:");
            var talla = teclado.nextInt();

            var puntos = Ficheros.rellenarPuntos(talla,peorCaso);

            comprobarEstrategiasFicheroTspAux(null, puntos);
        }
    }

}
