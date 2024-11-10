package org.example;

import java.util.ArrayList;

public class DivideYVenceras {

    private static double tiempo;
    private static int puntosRecorridos;

     DivideYVenceras() {
    }

    //ojo
    public static Distancia distanciaMinima(ArrayList<Punto> puntos, int izquierda, int derecha) {
        Distancia menorDistancia = null;

        tiempo = 0;


        long startTime = System.nanoTime();


        // Caso base: pocos puntos, calcular por exhaustiva
        if (derecha - izquierda <= 3) {
            return getDistanciaMinimaExhaustivoAcotado(puntos, izquierda, derecha, Double.MAX_VALUE);
        }

        int medio = (izquierda + derecha) / 2;
        Punto puntoMedio = puntos.get(medio);

        Distancia particionIzquierda = distanciaMinima(puntos, izquierda, medio);
        Distancia particionDerecha = distanciaMinima(puntos, medio, derecha);
        double distanciaMin;

        var distanciaIzquierda = particionIzquierda.getDistancia();
        var distanciaDerecha = particionDerecha.getDistancia();

        if (distanciaIzquierda <= distanciaDerecha) {
            distanciaMin = distanciaIzquierda;
            menorDistancia = particionIzquierda;
        } else {
            distanciaMin = distanciaDerecha;
            menorDistancia = particionDerecha;
        }

        // Crear una lista de puntos en la banda de distanciaMin
        ArrayList<Punto> puntosEnRango = new ArrayList<>();
        for (int i = izquierda; i <= derecha; i++) {
            if (Math.abs(puntos.get(i).getX() - puntoMedio.getX()) < distanciaMin) {
                puntosEnRango.add(puntos.get(i));
            }
        }

        // Búsqueda en la banda de distanciaMin
        //menorDistancia = getDistanciaMinimaExhaustivoAcotado(puntosEnRango,)
        for (int i = 0; i < puntosEnRango.size() - 1; i++) {
            for (int j = i + 1; j < puntosEnRango.size(); j++) {
                var distanciaMedida = new Distancia(puntosEnRango.get(i), puntosEnRango.get(j));
                puntosRecorridos++;
                if (distanciaMedida.getDistancia() < distanciaMin) {
                    distanciaMin = distanciaMedida.getDistancia();
                    menorDistancia = distanciaMedida;
                }
            }
        }

        long endTime = System.nanoTime();
        tiempo = endTime - startTime;
        tiempo /= 1000000;

        return menorDistancia;
    }

    public static double getTiempo() {
        return tiempo;
    }



    private static Distancia getDistanciaMinimaExhaustivoAcotado(ArrayList<Punto> puntos, int izquierda, int derecha, double distanciaMinimaInicio) {
        var distanciaMinima = new Distancia();
        var distanciaMinimaEncontrada = distanciaMinimaInicio;
        for (int i = izquierda; i <= derecha; i++) {
            for (int j = i + 1; j <= derecha; j++) {
                var distanciaMedida = new Distancia(puntos.get(i), puntos.get(j));
                puntosRecorridos++;
                if (distanciaMedida.getDistancia() < distanciaMinimaEncontrada) {
                    distanciaMinimaEncontrada = distanciaMedida.getDistancia();
                    distanciaMinima = distanciaMedida;
                }
            }
        }
        return distanciaMinima;
    }
    public static int getPuntosRecorridos() {
        return puntosRecorridos;
    }
}
