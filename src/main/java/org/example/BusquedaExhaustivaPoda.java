package org.example;

import java.util.ArrayList;

public class BusquedaExhaustivaPoda implements Algoritmo {

    private BusquedaExhaustivaPoda() {
    }

    public static Distancia distanciaMinima(ArrayList<Punto> puntos) {
        double distanciaMin;
        Distancia menorDistancia = new Distancia();
        Distancia distanciaActual;

        Punto punto1 = puntos.get(0);
        Punto punto2 = puntos.get(1);
        distanciaActual = new Distancia(punto1, punto2);
        distanciaMin = distanciaActual.getDistancia();


        for (int i = 0; i < puntos.size() - 1; i++) {
            Punto puntoBase = puntos.get(i);
            for (int j = i + 1; j < puntos.size(); j++) {
                Punto puntoActual = puntos.get(j);
                distanciaActual = new Distancia(puntoBase, puntoActual);

                //Comprobamos si la distancia minima tiene que ser actualizada
                if (distanciaActual.getDistancia() < distanciaMin) {
                    distanciaMin = distanciaActual.getDistancia();
                    menorDistancia = distanciaActual;

                }

                //Poda
                if (Math.abs(puntoBase.getX() - puntoActual.getX()) >= distanciaMin) {
                    break;
                }
            }
        }

        return menorDistancia;
    }
}
