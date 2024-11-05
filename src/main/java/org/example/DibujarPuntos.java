package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DibujarPuntos extends JPanel {

    private  ArrayList<Punto> puntos;

    public DibujarPuntos() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.RED);
        for (Punto punto : puntos) {
            int x = (int) punto.getX();
            int y = (int) punto.getY();
            g.fillOval(x, y, 5, 5);
        }
    }

    public void setPuntos(ArrayList<Punto> puntos) {
        this.puntos = puntos;
    }

}
