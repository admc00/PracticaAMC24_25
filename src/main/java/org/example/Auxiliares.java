package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Auxiliares {

    private Auxiliares() {
    }

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

    public static class Menu {

        private Menu() {
        }

        private static void comprobarEstrategiasFicheroTspAux(String nombre, ArrayList<Punto> puntos) {
            ArrayList<Punto> dataset = puntos == null ? Ficheros.leerFichero(nombre) : puntos;
            System.out.printf("%-25s %-30s %-30s %-20s %-20s %-20s%n", "Estrategia", "Punto1", "Punto2", "Distancia", "Calculadas", "Tiempo");

            Distancia bex = BusquedaExhaustiva.distanciaMinima(dataset);
            System.out.printf("%-25s %-30s %-30s %-20.4f %-20d %-20.1f%n", "Busqueda exhaustiva", bex.getX1().getID() + "(" + bex.getX1().getX() + ", " + bex.getX2().getY() + ")", bex.getX2().getID() + "(" + bex.getX2().getX() + ", " + bex.getX2().getY() + ")", bex.getDistancia(), 0, BusquedaExhaustiva.getTiempo());


            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);


            Distancia dyv = DivideYVenceras.distanciaMinima(datasetOrd, 0, dataset.size() - 1);
            System.out.printf("%-25s %-30s %-30s %-20.4f %-20d %-20.1f%n", "Divide y venceras", dyv.getX1().getID() + "(" + dyv.getX1().getX() + ", " + dyv.getX2().getY() + ")", dyv.getX2().getID() + "(" + dyv.getX2().getX() + ", " + dyv.getX2().getY() + ")", dyv.getDistancia(), 0, DivideYVenceras.getTiempo());

            Distancia dyvm = DivideYVencerasMejorado.distanciaMinima(datasetOrd, 0, dataset.size() - 1);
            System.out.printf("%-25s %-30s %-30s %-20.4f %-20d %-20.1f%n", "Divide y venceras mejorado", dyvm.getX1().getID() + "(" + dyvm.getX1().getX() + ", " + dyvm.getX2().getY() + ")", dyvm.getX2().getID() + "(" + dyvm.getX2().getX() + ", " + dyvm.getX2().getY() + ")", dyvm.getDistancia(), 0, DivideYVencerasMejorado.getTiempo());

            Distancia bexp = BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
            System.out.printf("%-25s %-30s %-30s %-20.4f %-20d %-20.1f%n", "Busqueda exhaustiva poda", bexp.getX1().getID() + "(" + bexp.getX1().getX() + ", " + bexp.getX2().getY() + ")", bexp.getX2().getID() + "(" + bexp.getX2().getX() + ", " + bexp.getX2().getY() + ")", bexp.getDistancia(), 0, BusquedaExhaustivaPoda.getTiempo());

        }



        public static void crearFicheroAleatorioTSP(Scanner escaner, boolean peorCaso) {
            System.out.println("Introduce el tamaño del dataset: ");
            int size = escaner.nextInt();
            Ficheros.crearArchivoTSP(size, peorCaso);
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

            if (!listaFicheros.contains(fichero)) {
                fichero = listaFicheros.get(0);
            }

            System.out.println(fichero);
            //System.out.printf("%s")
            comprobarEstrategiasFicheroTspAux(fichero, null);
        }

        //TODO:Renombrar a estudiar estrategia
        public static void estudiarUnaEstrategia(Scanner teclado, boolean peorCaso) {
            var continuar = true;
            var estrategia = -1;
            Enum opcion;


            do {
                System.out.println("Estrategia a estudiar:");
                System.out.println("1.Busqueda exhaustiva");
                System.out.println("2.Busqueda exhaustiva poda");
                System.out.println("3.Divide y venceras");
                System.out.println("4.Divide y venceras mejorado");
                System.out.println("0. Salir al menu principal");
                System.out.print("Selecciona una opción: ");

                estrategia = teclado.nextInt();


                opcion = Estrategias.getEstrategia(estrategia);

                switch (opcion) {
                    case Estrategias.NONE -> {
                        continuar = false;
                    }
                    case Estrategias.EXHAUSTIVO -> {
                        System.out.println("Busqueda exhaustiva");

                        System.out.println("Tiempos de ejecucion promedio");
                        System.out.printf("%-10s %20s%n", "Talla", "Tiempo");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            BusquedaExhaustiva.distanciaMinima(dataset);
                            System.out.printf("%-10d %20.1f%n", i, BusquedaExhaustiva.getTiempo());
                        }
                    }
                    case Estrategias.EXHAUSTIVO_PODA -> {
                        System.out.println("Busqueda exhaustiva poda");

                        System.out.println("Tiempos de ejecucion promedio");
                        System.out.printf("%-10s %20s%n", "Talla", "Tiempo");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);
                            BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                            System.out.printf("%-10d %20.1f%n", i, BusquedaExhaustivaPoda.getTiempo());
                        }
                    }
                    case Estrategias.DIVIDE_Y_VENCERAS -> {
                        System.out.println("Divide y venceras");

                        System.out.println("Tiempos de ejecucion promedio");
                        System.out.printf("%-10s %20s%n", "Talla", "Tiempo");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);
                            DivideYVenceras.distanciaMinima(datasetOrd, 0, datasetOrd.size() - 1);
                            System.out.printf("%-10d %20.1f%n", i, DivideYVenceras.getTiempo());
                        }
                    }
                    case Estrategias.DIVIDE_Y_VENCERAS_MEJORADO -> {
                        System.out.println("Divide y venceras mejorado");

                        System.out.println("Tiempos de ejecucion promedio");
                        System.out.printf("%-10s %20s%n", "Talla", "Tiempo");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);
                            DivideYVencerasMejorado.distanciaMinima(datasetOrd, 0, datasetOrd.size() - 1);
                            System.out.printf("%-10d %20.1f%n", i, DivideYVencerasMejorado.getTiempo());
                        }
                    }
                    default -> System.out.println("Número no valido.Elige un número de nuevo.");
                }
            } while (continuar);
        }

        public static void compararDosEstrategias(Scanner teclado, boolean peorCaso) throws Exception {
            var continuar = true;
            int estrategia1,estrategia2;
            Enum opcion;

            do {
                System.out.println("\n\n*** COMPARACION DE DOS ESTRATEGIAS ***\n\n");
                System.out.println("*** Selecciona las dos estrategias a comparar");
                System.out.println("1. Busqueda exhaustiva");
                System.out.println("2. Busqueda exhaustiva poda");
                System.out.println("3. Divide y venceras");
                System.out.println("4. Divide y venceras mejorado");
                System.out.println("0. Salir al menu principal");
                System.out.println("---------------------------------");

                System.out.print("Elige estrategia 1: ");
                estrategia1 = teclado.nextInt();
                System.out.print("\nElige estrategia 2: ");
                estrategia2 = teclado.nextInt();

                opcion = Estrategias.getPermutacionEstrategia(estrategia1, estrategia2);

                switch (opcion) {
                    case Estrategias.NONE -> {
                        continuar = false;
                    }
                    case Estrategias.EXvsEXPODA -> {
                        System.out.println("\n\n\t*** EXHAUSTIVO vs EXHAUSTIVO PODA ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "Exhaustivo", "ExhaustivoPoda", "Exhaustivo", "ExhaustivoPoda");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaExhaustivo = BusquedaExhaustiva.distanciaMinima(dataset);
                            Distancia distanciaExPoda = BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, BusquedaExhaustiva.getTiempo(), BusquedaExhaustivaPoda.getTiempo(), distanciaExhaustivo.getDistancia(), distanciaExPoda.getDistancia());
                        }
                    }
                    case Estrategias.EXvsDYV -> {
                        System.out.println("\n\n\t*** EXHAUSTIVO vs DIVIDE Y VENCERAS ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "Exhaustivo", "DivideVenceras", "Exhaustivo", "DivideVenceras");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaExhaustivo = BusquedaExhaustiva.distanciaMinima(dataset);
                            Distancia distanciaDyV = DivideYVenceras.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, BusquedaExhaustiva.getTiempo(), DivideYVenceras.getTiempo(), distanciaExhaustivo.getDistancia(), distanciaDyV.getDistancia());
                        }
                    }
                    case Estrategias.EXvsDYVM -> {
                        System.out.println("\n\n\t*** EXHAUSTIVO vs DIVIDE Y VENCERAS MEJORADO ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "Exhaustivo", "DyV_Mejorado", "Exhaustivo", "DyV_Mejorado");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaExhaustivo = BusquedaExhaustiva.distanciaMinima(dataset);
                            Distancia distanciaDyVMejorado = DivideYVencerasMejorado.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, BusquedaExhaustiva.getTiempo(), DivideYVencerasMejorado.getTiempo(), distanciaExhaustivo.getDistancia(), distanciaDyVMejorado.getDistancia());
                        }
                    }
                    case Estrategias.EXPODAvsEX -> {
                        System.out.println("\n\n\t*** EXHAUSTIVO PODA vs EXHAUSTIVO ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "ExhaustivoPoda", "Exhaustivo", "ExhaustivoPoda", "Exhaustivo");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaExPoda = BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                            Distancia distanciaExhaustivo = BusquedaExhaustiva.distanciaMinima(dataset);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, BusquedaExhaustivaPoda.getTiempo(), BusquedaExhaustiva.getTiempo(), distanciaExPoda.getDistancia(), distanciaExhaustivo.getDistancia());
                        }
                    }
                    case Estrategias.EXPODAvsDYV -> {
                        System.out.println("\n\n\t*** EXHAUSTIVO PODA vs DIVIDE Y VENCERAS ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "ExhaustivoPoda", "DivideVenceras", "ExhaustivoPoda", "DivideVenceras");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaExPoda = BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                            Distancia distanciaDyV = DivideYVenceras.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, BusquedaExhaustivaPoda.getTiempo(), DivideYVenceras.getTiempo(), distanciaExPoda.getDistancia(), distanciaDyV.getDistancia());
                        }
                    }
                    case Estrategias.EXPODAvsDYVM -> {
                        System.out.println("\n\n\t*** EXHAUSTIVO PODA vs DIVIDE Y VENCERAS MEJORADO ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "ExhaustivoPoda", "DyV_Mejorado", "ExhaustivoPoda", "DyV_Mejorado");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaExPoda = BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                            Distancia distanciaDyVMejorado = DivideYVencerasMejorado.distanciaMinima(datasetOrd,0,datasetOrd.size() - 1);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, BusquedaExhaustivaPoda.getTiempo(), DivideYVencerasMejorado.getTiempo(), distanciaExPoda.getDistancia(), distanciaDyVMejorado.getDistancia());
                        }
                    }
                    case Estrategias.DYVvsEX -> {
                        System.out.println("\n\n\t*** DIVIDE Y VENCERAS vs EXHAUSTIVO ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "DivideVenceras", "Exhaustivo", "DivideVenceras", "Exhaustivo");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaDyV = DivideYVenceras.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            Distancia distanciaExhaustivo = BusquedaExhaustiva.distanciaMinima(dataset);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, DivideYVenceras.getTiempo(), BusquedaExhaustiva.getTiempo(), distanciaDyV.getDistancia(), distanciaExhaustivo.getDistancia());
                        }
                    }
                    case Estrategias.DYVvsEXPODA -> {
                        System.out.println("\n\n\t*** DIVIDE Y VENCERAS vs EXHAUSTIVO PODA ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "DivideVenceras", "ExhaustivoPoda", "DivideVenceras", "ExhaustivoPoda");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaDyV = DivideYVenceras.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            Distancia distanciaExPoda = BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, DivideYVenceras.getTiempo(), BusquedaExhaustivaPoda.getTiempo(), distanciaDyV.getDistancia(), distanciaExPoda.getDistancia());
                        }
                    }
                    case Estrategias.DYVvsDYVM -> {
                        System.out.println("\n\n\t*** DIVIDE Y VENCERAS vs DIVIDE Y VENCERAS MEJORADO ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "DivideVenceras", "DyV_Mejorado", "DivideVenceras", "DyV_Mejorado");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaDyV = DivideYVenceras.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            Distancia distanciaDyVMejorado = DivideYVencerasMejorado.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, DivideYVenceras.getTiempo(), DivideYVencerasMejorado.getTiempo(), distanciaDyV.getDistancia(), distanciaDyVMejorado.getDistancia());
                        }
                    }
                    case Estrategias.DYVMvsEX -> {
                        System.out.println("\n\n\t*** DIVIDE Y VENCERAS MEJORADO vs EXHAUSTIVO ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "DyV_Mejorado", "Exhaustivo", "DyV_Mejorado", "Exhaustivo");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaDyVMejorado = DivideYVencerasMejorado.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            Distancia distanciaExhaustivo = BusquedaExhaustiva.distanciaMinima(dataset);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, DivideYVencerasMejorado.getTiempo(), BusquedaExhaustiva.getTiempo(), distanciaDyVMejorado.getDistancia(), distanciaExhaustivo.getDistancia());
                        }
                    }
                    case Estrategias.DYVMvsEXPODA -> {
                        System.out.println("\n\n\t*** DIVIDE Y VENCERAS MEJORADO vs EXHAUSTIVO PODA ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "DyV_Mejorado", "ExhaustivoPoda", "DyV_Mejorado", "ExhaustivoPoda");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaDyVMejorado = DivideYVencerasMejorado.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            Distancia distanciaExPoda = BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, DivideYVencerasMejorado.getTiempo(), BusquedaExhaustivaPoda.getTiempo(), distanciaDyVMejorado.getDistancia(), distanciaExPoda.getDistancia());
                        }
                    }
                    case Estrategias.DYVMvsDYV -> {
                        System.out.println("\n\n\t*** DIVIDE Y VENCERAS MEJORADO vs DIVIDE Y VENCERAS ***\n");

                        System.out.printf("%25s%n","Tiempos de ejecucion promedio");
                        System.out.printf("%35s %20s %20s %20s%n", "DyV_Mejorado", "DivideVenceras", "DyV_Mejorado", "DivideVenceras");
                        System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Distancia", "Distancia");
                        for (int i = 500; i <= 5000; i += 500) {
                            ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                            ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);

                            Distancia distanciaDyVMejorado = DivideYVencerasMejorado.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            Distancia distanciaDyV = DivideYVenceras.distanciaMinima(datasetOrd,0, datasetOrd.size() - 1);
                            System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, DivideYVencerasMejorado.getTiempo(), DivideYVenceras.getTiempo(), distanciaDyVMejorado.getDistancia(), distanciaDyV.getDistancia());
                        }
                    }
                    case Estrategias.IDENTICO -> {
                        System.out.println("*** NO SE PUEDE COMPARAR UNA ESTRATEGIA CONSIGO MISMA ***");
                    }
                    case Estrategias.ERROR -> {
                        System.out.println("Opcion seleccionada no valida.");
                    }
                    default -> throw new Exception();
                }
            } while (continuar);
        }

        public static void compararTodasEstrategias(boolean peorCaso) {
            System.out.println("Tiempos de ejecucion promedio");
            System.out.printf("%35s %20s %20s %20s%n", "Busqueda exhaustiva", "Busqueda exhaustiva poda", "Divide y venceras", "Divide y venceras mejorado" );
            System.out.printf("%5s %25s %20s %20s %20s%n", "Talla", "Tiempo", "Tiempo", "Tiempo", "Tiempo");
            for (int i = 500; i <= 5000; i += 500) {
                ArrayList<Punto> dataset = Ficheros.rellenarPuntos(i, peorCaso);
                BusquedaExhaustiva.distanciaMinima(dataset);
                ArrayList<Punto> datasetOrd = Auxiliares.quickSort(dataset, 0, dataset.size() - 1);
                DivideYVenceras.distanciaMinima(datasetOrd, 0, datasetOrd.size() - 1);
                DivideYVencerasMejorado.distanciaMinima(datasetOrd, 0, datasetOrd.size() - 1);
                BusquedaExhaustivaPoda.distanciaMinima(datasetOrd);
                System.out.printf("%-10d %20.1f %20.1f %20.1f %20.1f%n", i, BusquedaExhaustiva.getTiempo(), BusquedaExhaustivaPoda.getTiempo(), DivideYVenceras.getTiempo(), DivideYVencerasMejorado.getTiempo());
            }
        }

        public static void comprobarTodasEstrategias(Scanner teclado, boolean peorCaso) {
            System.out.println("Introduce la talla:");
            var talla = teclado.nextInt();

            var puntos = Ficheros.rellenarPuntos(talla, peorCaso);

            comprobarEstrategiasFicheroTspAux(null, puntos);
        }

        private enum Estrategias {
            EXHAUSTIVO,
            EXHAUSTIVO_PODA,
            DIVIDE_Y_VENCERAS,
            DIVIDE_Y_VENCERAS_MEJORADO,
            EXvsEXPODA,
            EXvsDYV,
            EXvsDYVM,
            EXPODAvsEX,
            EXPODAvsDYV,
            EXPODAvsDYVM,
            DYVvsEX,
            DYVvsEXPODA,
            DYVvsDYVM,
            DYVMvsEX,
            DYVMvsEXPODA,
            DYVMvsDYV,
            IDENTICO,
            NONE,
            ERROR;

            static Enum getEstrategia(int estrategiaRAW) {
                switch (estrategiaRAW) {
                    case 0:
                        return NONE;
                    case 1:
                        return EXHAUSTIVO;
                    case 2:
                        return EXHAUSTIVO_PODA;
                    case 3:
                        return DIVIDE_Y_VENCERAS;
                    case 4:
                        return DIVIDE_Y_VENCERAS_MEJORADO;
                    default:
                        return ERROR;
                }
            }

            static Enum getPermutacionEstrategia(int estrategiaUnoRAW, int estrategiaDosRAW) {
                switch (estrategiaUnoRAW) {
                    case 0 -> {
                        return NONE;
                    }
                    case 1 -> {
                        switch (estrategiaDosRAW) {
                            case 0: return NONE;
                            case 1: return IDENTICO;
                            case 2: return EXvsEXPODA;
                            case 3: return EXvsDYV;
                            case 4: return EXvsDYVM;
                            default: return ERROR;
                        }
                    }
                    case 2 -> {
                        switch (estrategiaDosRAW) {
                            case 0: return NONE;
                            case 1: return EXPODAvsEX;
                            case 2: return IDENTICO;
                            case 3: return EXPODAvsDYV;
                            case 4: return EXPODAvsDYVM;
                            default: return ERROR;
                        }
                    }
                    case 3 -> {
                        switch (estrategiaDosRAW) {
                            case 0: return NONE;
                            case 1: return DYVvsEX;
                            case 2: return DYVvsEXPODA;
                            case 3: return IDENTICO;
                            case 4: return DYVvsDYVM;
                            default: return ERROR;
                        }
                    }
                    case 4 -> {
                        switch (estrategiaDosRAW) {
                            case 0: return NONE;
                            case 1: return DYVMvsEX;
                            case 2: return DYVMvsEXPODA;
                            case 3: return DYVMvsDYV;
                            case 4: return IDENTICO;
                            default: return ERROR;
                        }
                    }
                    default -> {
                        return ERROR;
                    }
                }
            }
        }
    }

}
