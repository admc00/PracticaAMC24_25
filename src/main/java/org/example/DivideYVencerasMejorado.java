package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DivideYVencerasMejorado {

    private static double tiempo;

    private DivideYVencerasMejorado() {
    }

    public static Distancia distanciaMinima(ArrayList<Punto> puntos, int izquierda, int derecha) {
        Distancia menorDistancia = new Distancia();

        tiempo = 0;

        long startTime = System.nanoTime();

        if (derecha - izquierda <= 3) {
            double Mindistancia = Double.POSITIVE_INFINITY;
            // Caso base: Fuerza bruta para un número pequeño de puntos
            for (int i = izquierda; i < derecha - 1; i++) {
                for (int j = i + 1; j < derecha; j++) {
                    Distancia actualLinea = new Distancia(puntos.get(i), puntos.get(j));
                    double distancia = actualLinea.getDistancia();
                    if (distancia < Mindistancia) {
                        Mindistancia = distancia;
                        menorDistancia = actualLinea;
                    }
                }
            }

            return menorDistancia;
        }

        // Divide los puntos en dos mitades
        int medio = (izquierda + derecha) / 2;
        Punto puntoMedio = puntos.get(medio);

        Distancia lineaIzquierda = distanciaMinima(puntos, izquierda, medio);
        Distancia lineaDerecha = distanciaMinima(puntos, medio, derecha);

        double distanciaMin;
        // Elegir la línea más corta de las dos
        if (lineaIzquierda.getDistancia() <= lineaDerecha.getDistancia()) {
            distanciaMin = lineaIzquierda.getDistancia();
            menorDistancia = lineaIzquierda;
        } else {
            distanciaMin = lineaDerecha.getDistancia();
            menorDistancia = lineaDerecha;
        }

        // Filtrar los puntos en la franja por coordenada x
        List<Punto> puntosEnRango = new ArrayList<>();
        for (int i = izquierda; i < derecha; i++) {
            if (Math.abs(puntos.get(i).getX() - puntoMedio.getX()) < distanciaMin) {
                puntosEnRango.add(puntos.get(i));
            }
        }

        // Ordenar los puntos en la franja por coordenada y
        Collections.sort(puntosEnRango, (p1, p2) -> Double.compare(p1.getY(), p2.getY()));

        // Búsqueda de pares cercanos en la franja
        for (int i = 0; i < puntosEnRango.size() - 1; i++) {
            for (int j = i + 1; j < puntosEnRango.size() && j - i < 12; j++) {
                Distancia linea = new Distancia(puntosEnRango.get(i), puntosEnRango.get(j));
                double distancia = linea.getDistancia();
                if (distancia < distanciaMin) {
                    distanciaMin = distancia;
                    menorDistancia = linea;
                }
            }
        }

        long endTime = System.nanoTime();
        tiempo = endTime - startTime;
        tiempo /= 1000;

        return menorDistancia;
    }

    public static double getTiempo() {
        return tiempo;
    }
}
