package org.example;

public class Distancia {

    private final Punto x1;
    private final Punto x2;
    private double distancia;

    public Distancia(Punto x1, Punto x2) {
        this.x1 = x1;
        this.x2 = x2;
        this.distancia();
    }

    public Distancia() {
        this.x1 = new Punto();
        this.x2 = new Punto();
    }

    public double getDistancia() {
        return this.distancia;
    }

    private void distancia() {
        double distanciaX = x1.getX() - x2.getX();
        double distanciaY = x1.getY() - x2.getY();
        distancia = Math.sqrt(Math.pow(distanciaX, 2) + Math.pow(distanciaY, 2));
    }

    public Punto getX1() {
        return x1;
    }

    public Punto getX2() {
        return x2;
    }
}
