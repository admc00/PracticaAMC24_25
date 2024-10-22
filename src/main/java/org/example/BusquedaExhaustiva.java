package org.example;

import java.util.ArrayList;

public class BusquedaExhaustiva {

    private BusquedaExhaustiva() {
    }

    public static Distancia distanciaMinima(ArrayList<Punto> puntos) {
        Distancia distanciaMinima = new Distancia();
        double distanciaMinimaEncontrada = Double.MAX_VALUE;
        for (int i = 0; i < puntos.size(); i++) {
            for (int j = i + 1; j < puntos.size(); j++) {
                Distancia distanciaMedida = new Distancia(puntos.get(i), puntos.get(j));
                if (distanciaMedida.getDistancia() < distanciaMinimaEncontrada) {
                    distanciaMinimaEncontrada = distanciaMedida.getDistancia();
                    distanciaMinima = distanciaMedida;
                }
            }
        }
        return distanciaMinima;
    }
}
