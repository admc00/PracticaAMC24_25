package org.example;

import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //Algoritmos prueba = new Algoritmos();
        Distancia d = new Distancia();
        Ficheros f = new Ficheros();
        ArrayList<Punto> puntos = f.LeerFichero("berlin52");
        for (Punto punto : puntos) {
            System.out.println(punto.getX() + " " + punto.getY() + " " + punto.getID());
        }
        d = Algoritmos.BusquedaExhaustiva(puntos);
        System.out.println("La distancia minima entre los puntos: " + d.getDistancia());
    }
}