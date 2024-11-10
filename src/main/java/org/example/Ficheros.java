package org.example;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ficheros {

    private Ficheros() {
    }

    public static ArrayList<Punto> leerFichero(String texto) {
        FileReader fr = null;
        BufferedReader br;

        var p = new ArrayList<Punto>();

        try {
            String localDir = System.getProperty("user.dir");
            var archivo = new File(localDir + File.separator + "dataset" + File.separator + texto + File.separator + texto);

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            //String linea;

            var lineas = br.lines().skip(6).toList();

            for (var linea : lineas) {
                if (linea.contains("EOF")) {
                    break;
                }
                String[] parte = linea.split(" ");
                if (parte.length == 3) {
                    p.add(new Punto(Double.parseDouble(parte[1]), Double.parseDouble(parte[2]), Integer.parseInt(parte[0])));
                }
            }

            /*while ((linea = br.readLine()) != null) {

                if (linea.contains("EOF")) {
                    break;
                }
                String[] parte = linea.split(" ");
                if (parte.length == 3) {

                    p.add(new Punto(Double.parseDouble(parte[1]), Double.parseDouble(parte[2]), Integer.parseInt(parte[0])));
                }
            }*/
        } catch (Exception ex) {
            Logger.getLogger(Ficheros.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return p;
    }

    public static ArrayList<Punto> rellenarPuntos(int n, boolean mismax) {
        ArrayList<Punto> p = new ArrayList<>();

        Random rand = new Random();
        rand.setSeed(System.nanoTime());

        int num, den;
        double x, y, aux1;

        if (mismax) { //PEOR CASO
            for (int i = 0; i < n; i++) {
                aux1 = rand.nextInt(1000) + 7; //7 y 1007
                y = aux1 / ((double) i + 1 + i * 0.100); //aux2; //+(i/3.0);
                num = rand.nextInt(3);
                y += ((i % 500) - num * (rand.nextInt(100)));
                x = 1;
                Punto pn = new Punto(x, y, i + 1);
                p.add(pn);
            }
        } else { //CASO MEDIO
            for (int i = 0; i < n; i++) {
                num = rand.nextInt(4000) + 1; //genera un número aleatorio entre 1 y 4000
                den = rand.nextInt(11) + 7; //genera un aleatorio entre 7 y 17
                x = num / ((double) den + 0.37); //division con decimales
                y = (rand.nextInt(4000) + 1) / ((double) (rand.nextInt(11) + 7) + 0.37);
                Punto pn = new Punto(x, y, i + 1);
                p.add(pn);
            }

        }
        return p;
    }

    public static void crearArchivoTSP(ArrayList<Punto> dataSet, String estrategia, Integer size, Boolean peorCaso) {
        File dir, file;
        ArrayList<Punto> puntos;

        if (estrategia == null) {
            dir = new File(System.getProperty("user.dir") + File.separator + "dataset" + File.separator + "dataset" + size + ".tsp");
            dir.mkdirs();

            file = new File(dir.getPath() + File.separator + "dataset" +  size + ".tsp");

            puntos = rellenarPuntos(size, peorCaso);
        }else {
            dir = new File(System.getProperty("user.dir") + File.separator + "dataset" + File.separator + estrategia + ".tsp");
            dir.mkdirs();

            file = new File(dir.getPath() + File.separator + estrategia + ".tsp");

            puntos = dataSet;
            size = puntos.size();
        }


        String filePath = file.toString();
        Random r = new Random();
        r.setSeed(System.nanoTime());
        //DecimalFormat decimalFormat = new DecimalFormat("#.##########");

        try {
            //ArrayList<Punto> puntos = estrategia == null ? rellenarPuntos(size, peorCaso) : dataSet;

            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            writer.write("NAME: dataset" + estrategia != null ? estrategia : size + ".tsp");
            writer.newLine();
            writer.write("TYPE: TSP");
            writer.newLine();
            writer.write("COMMENT: " + size + " locations");
            writer.newLine();
            writer.write("DIMENSION: " + size);
            writer.newLine();
            writer.write("EDGE_WEIGHT_TYPE: EUC_2D");
            writer.newLine();
            writer.write("NODE_COORD_SECTION");
            writer.newLine();
            for (int i = 0; i < size; i++) {
                writer.write(i + 1 + " " + puntos.get(i).getX() + " " + puntos.get(i).getY());
                writer.newLine();
            }
            //Forzar escritura en el archivo
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static ArrayList<String> leerNombreCarpetas() {
        String carpetaRuta = System.getProperty("user.dir") + File.separator + "dataset";
        ArrayList<String> nombresCarpetas = new ArrayList<>();

        // Crear un objeto File que apunte a la carpeta
        File carpeta = new File(carpetaRuta);

        // Obtener la lista de archivos y carpetas en la carpeta
        File[] listaArchivos = carpeta.listFiles();

        // Verificar que la carpeta no esté vacía
        if (listaArchivos != null) {

            // Iterar sobre los archivos y carpetas
            for (File archivo : listaArchivos) {
                if (archivo.isDirectory()) {
                    nombresCarpetas.add(archivo.getName());
                }
            }
        } else {
            System.out.println("La carpeta está vacía o no existe.");
        }

        return nombresCarpetas;
    }


}
