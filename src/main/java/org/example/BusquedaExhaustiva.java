package org.example;

import java.util.ArrayList;

public class BusquedaExhaustiva {

    private static double tiempo;
    private static int puntosRecorridos = 0;

    private BusquedaExhaustiva() {
    }

    public static Distancia distanciaMinima(ArrayList<Punto> puntos) {

        tiempo = 0;
        long startTime = System.nanoTime();


        Distancia distanciaMinima = new Distancia();
        double distanciaMinimaEncontrada = Double.MAX_VALUE;
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                Distancia distanciaMedida = new Distancia(puntos.get(i), puntos.get(j));
                puntosRecorridos++;
                if (distanciaMedida.getDistancia() < distanciaMinimaEncontrada) {
                    distanciaMinimaEncontrada = distanciaMedida.getDistancia();
                    distanciaMinima = distanciaMedida;
                }
            }
        }

        long endTime = System.nanoTime();
        tiempo = endTime - startTime;
        tiempo /= 1000000;

        return distanciaMinima;
    }

    public static double getTiempo() {
        return tiempo;
    }
    public static int getPuntosRecorridos(){
        return puntosRecorridos;
    }
}
