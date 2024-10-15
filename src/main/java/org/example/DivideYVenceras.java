package org.example;

import java.util.ArrayList;

public class DivideYVenceras implements Algoritmo {

    private DivideYVenceras() {
    }

    //ojo
    public static Distancia distanciaMinima(ArrayList<Punto> p, int izquierda, int derecha) {
        Distancia menorDistancia = null;

        //this.puntos = p;

        //tiempo = 0;

        //long startTime = System.nanoTime();

        if (derecha - izquierda <= 3) {
            double minDistancia = Double.MAX_VALUE;
            // Caso base: pocos puntos, calcular por fuerza bruta
            for (int i = izquierda; i <= derecha; i++) {
                for (int j = i + 1; j <= derecha; j++) {
                    DistanciaPuntos actualLinea = new DistanciaPuntos(puntos.get(i), puntos.get(j));
                    double distancia = actualLinea.getDistanciaMin();
                    puntosRecorridos++;
                    if (distancia < minDistancia) {
                        minDistancia = distancia;
                        menorDistancia = actualLinea;
                    }
                }
            }
            return menorDistancia;
        }

        int medio = (izquierda + derecha) / 2;
        Punto puntoMedio = puntos.get(medio);

        DistanciaPuntos lineaIzquierda = DivideyVenceras(puntos, izquierda, medio);
        DistanciaPuntos lineaDerecha = DivideyVenceras(puntos, medio, derecha);
        double distanciaMin;

        if (lineaIzquierda.getDistanciaMin() <= lineaDerecha.getDistanciaMin()) {
            distanciaMin = lineaIzquierda.getDistanciaMin();
            menorDistancia = lineaIzquierda;
        } else {
            distanciaMin = lineaDerecha.getDistanciaMin();
            menorDistancia = lineaDerecha;
        }

        // Crear una lista de puntos en la banda de distanciaMin
        ArrayList<Punto> puntosEnRango = new ArrayList<>();
        for (int i = izquierda; i <= derecha; i++) {
            if (Math.abs(puntos.get(i).getX() - puntoMedio.getX()) < distanciaMin) {
                puntosEnRango.add(puntos.get(i));
            }
        }

        // Búsqueda en la banda de distanciaMin
        for (int i = 0; i < puntosEnRango.size() - 1; i++) {
            for (int j = i + 1; j < puntosEnRango.size(); j++) {
                DistanciaPuntos l = new DistanciaPuntos(puntosEnRango.get(i), puntosEnRango.get(j));
                puntosRecorridos++;
                if (l.getDistanciaMin() < distanciaMin) {
                    distanciaMin = l.getDistanciaMin();
                    menorDistancia = l;
                }
            }
        }

        long endTime = System.nanoTime();
        tiempo = endTime - startTime;
        tiempo /= 1000;

        return menorDistancia;
    }
}
