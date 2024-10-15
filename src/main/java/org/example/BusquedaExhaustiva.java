package org.example;

import java.util.ArrayList;

public class BusquedaExhaustiva implements Algoritmo {

    private BusquedaExhaustiva() {
    }

    public static Distancia distanciaMinima(ArrayList<Punto> puntos) {
        Distancia d = new Distancia();
        double distanciaMin = Double.MAX_VALUE;
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                Distancia dp = new Distancia(puntos.get(i), puntos.get(j));
                if (dp.getDistancia() < distanciaMin) {
                    distanciaMin = dp.getDistancia();
                    d = dp;
                }
            }
        }
        return d;
    }
}
