package org.example;

import java.util.ArrayList;

public class Algoritmos {
  /* public static double distancia2(Punto p1, Punto p2) {
        double x = p1.getX() - p2.getX();
        double y = p1.getY() - p2.getY();
        return Math.sqrt(x * x + y * y);
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }

    public static Distancia exhaustivo(ArrayList<Punto> puntos, int izq,int der){
        Distancia d = new Distancia();
        d.setDistancia(distancia2(puntos.get(izq), puntos.get(der);
        for(int i = izq; i < der; i++){
            for(int j = i+1; j < der; j++){
                double dist = distancia2(puntos.get(i), puntos.get(j));
                if(dist < d.getDistancia()){
                    d.setDistancia(dist);
                    d.setP1(puntos.get(i));
                    d.setP2(puntos.get(j));
                }
            }
        }
        return d;
    }*/

    public static Distancia BusquedaExhaustiva(ArrayList<Punto> puntos){
        Distancia d = new Distancia();
        double distanciaMin = Double.MAX_VALUE;
        for (int i = 0; i < puntos.size(); i++){
            for (int j = i+1; j < puntos.size(); j++){
                Distancia dp = new Distancia(puntos.get(i), puntos.get(j));
                if (dp.getDistancia() < distanciaMin){
                    distanciaMin = dp.getDistancia();
                    d = dp;
                }
            }
        }
        return d;
    }

    public static Distancia BusquedaExhaustivaPoda(ArrayList<Punto> puntos){
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

    //ojo
    public static Distancia DivideyVenceras(ArrayList<Punto> p, int izquierda, int derecha) {
        Distancia menorDistancia = null;

        this.puntos = p;

        tiempo = 0;

        long startTime = System.nanoTime();

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