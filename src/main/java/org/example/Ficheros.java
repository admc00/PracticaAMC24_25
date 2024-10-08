package org.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Ficheros {
    File archivo;
    FileReader fr;
    BufferedReader br;
    ArrayList<Punto> p;

    public Ficheros(){
        archivo = null;
        fr = null;
        br = null;
        p = new ArrayList<>();
    }

    public ArrayList<Punto> LeerFichero(String texto)
    {
        try {
            String localDir = System.getProperty("user.dir");
            System.out.println(localDir);
            archivo = new File(localDir + File.separator + "dataset" + File.separator + texto + ".tsp");
            //archivo = new File("..\\..\\..\\..\\..\\dataset\\" + texto + ".tsp");
            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            String linea;
            while ((linea = br.readLine()) != null) {

                if(linea.contains("EOF")){
                    break;
                }
                String[] parte = linea.split(" ");
                if(parte.length == 3){

                    p.add(new Punto(Float.parseFloat(parte[1]),Float.parseFloat(parte[2]),Integer.parseInt(parte[0])));
                }
                //System.out.println(linea);
            }
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



}
