package cargarimagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Luis
 */
public class ProcesamientoImagen {

    private BufferedImage imageActual;
    static int[][] VE;
    static ArrayList<String> VP = new ArrayList<String>();
    //  static int[][] E1 = {{-1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, -1, 1, 1, -1, -1, -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, 1, 1, -1, -1, -1, -1, -1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1}};
    // static int[][] E2 = {{-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, -1, -1, 1, 1, -1, -1, -1, 1, 1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, 1, 1, 1, -1, -1, -1, -1, -1, -1, 1, 1, -1, -1, -1, -1, -1, -1, -1, 1, 1, 1, -1, -1, -1, 1, -1, -1, -1, 1, 1, 1, 1, 1, 1, 1, 1, -1}};
    int[][] W;
    static ArrayList listPat = new ArrayList();
    static ArrayList listTra = new ArrayList();
    static ArrayList listET = new ArrayList();
    static ArrayList listW = new ArrayList();

    //Retorna un objeto BufferedImagen
    public BufferedImage abrirImagen() {
        //Creamos la variable que será devuelta (la creamos como null)
        BufferedImage bmp = null;
        //Creamos un nuevo cuadro de diálogo para seleccionar imagen
        JFileChooser selector = new JFileChooser();
        //Le damos un título
        selector.setDialogTitle("Seleccione una imagen");
        //Filtramos los tipos de archivos
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("JPG & GIF & BMP", "jpg", "gif", "bmp");
        selector.setFileFilter(filtroImagen);
        //Abrimos el cuadro de diálog
        int flag = selector.showOpenDialog(null);
        //Comprobamos que pulse en aceptar
        if (flag == JFileChooser.APPROVE_OPTION) {
            try {
                //Devuelve el fichero seleccionado
                File imagenSeleccionada = selector.getSelectedFile();
                //Asignamos a la variable bmp la imagen leida
                bmp = ImageIO.read(imagenSeleccionada);
            } catch (Exception e) {
            }

        }
        //Asignamos la imagen cargada a la propiedad imageActual
        imageActual = bmp;
        //Retornamos el valor
        return bmp;
    }

    public BufferedImage escalaGrises() {
        //Variables que almacenarán los píxeles
        int mediaPixel, colorSRGB;
        Color colorAux;
        int y = this.imageActual.getHeight();
        int x = this.imageActual.getWidth();
        VE = new int[1][x * y];
        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < y; i++) {
            for (int j = 0; j < x; j++) {
                //Almacenamos el color del píxel
                colorAux = new Color(this.imageActual.getRGB(i, j));
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                //BinariodeImagen
                if (mediaPixel > 128) {
                    VE[0][j * 10 + i] = -1;
                } else {
                    VE[0][j * 10 + i] = 1;
                }
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                imageActual.setRGB(i, j, colorSRGB);
            }
        }
        //Retornamos la imagen
        return imageActual;
    }

    public static String mostrarMatriz(int[][] M) {
        String cadenamatriz = "";
        for (int i = 0; i < M.length; i++) {
            for (int j = 0; j < M[i].length; j++) {
                cadenamatriz = cadenamatriz + M[i][j] + ",";
            }
            cadenamatriz = cadenamatriz + "\n";
        }
        return cadenamatriz;
    }

    public static int[][] generarT(int[][] E) {
        int[][] TE1 = new int[100][E.length];
        for (int i = 0; i < E.length; i++) {
            for (int j = 0; j < 100; j++) {
                TE1[j][i] = E[i][j];
            }
        }
        return TE1;
    }

    public static int[][] generarET(int[][] E, int[][] ET) {
        int[][] MTE = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                MTE[i][j] = E[0][j] * ET[i][0];
            }
        }
        return MTE;
    }

    public static int[][] generarI(int x, int y) {
        int[][] MI = new int[x][y];
        for (int i = 0; i < MI.length; i++) {
            for (int j = 0; j < MI[i].length; j++) {
                if (i == j) {
                    MI[i][j] = 1;
                } else {
                    MI[i][j] = 0;
                }
            }
        }
        return MI;
    }

    public static int[][] generarW(int[][] ETX, int[][] I) {
        int[][] W = new int[100][100];
        for (int i = 0; i < W.length; i++) {
            for (int j = 0; j < W[i].length; j++) {
                W[i][j] = ETX[i][j] - I[i][j];
            }
        }
        return W;
    }

    public static int[][] sumaW(ArrayList<int[][]> LW) {
        int[][] W = new int[100][100];
        for (int i = 0; i < W.length; i++) {
            for (int j = 0; j < W[i].length; j++) {
                for (int k = 0; k < LW.size(); k++) {
                    W[i][j] = W[i][j] + LW.get(k)[i][j];
                }
            }
        }
        return W;
    }

    public static int[][] multWVE(int[][] W, int[][] VE, int ite) {
        System.out.println(ite + "  Iteración");
        int[][] WVE = new int[1][100];
        int acum;
        for (int i = 0; i < W.length; i++) {
            acum = 0;
            for (int j = 0; j < 100; j++) {
                acum = acum + VE[0][j] * W[i][j];
            }
            //Función de activación
            if (acum > 0) {
                WVE[0][i] = 1;
            } 
//            else if (acum == 0) {
//                WVE[0][i] = VE[0][i];
            else {
                WVE[0][i] = -1;
            }
        }
        if (comparar(WVE, VE)) {
            System.out.println("# de patrones :" + listPat.size() + "# de representaciones :" + VP.size());
            System.out.println("Vector de la última iteración : " + mostrarMatriz(WVE) + "\n");
            boolean ok = false;
            for (int i = 0; i < listPat.size(); i++) {
                int[][] pat = (int[][]) listPat.get(i);
                if (comparar(WVE, pat)) {
                    JOptionPane.showMessageDialog(null, "Patron : [" + VP.get(i) + "]  Reconocido");
                    ok = true;
                }
            }
            if (ok) {
                return WVE;
            } 
        } else {
            multWVE(W, WVE, (ite + 1));
        }
        return WVE;
    }

    public static boolean comparar(int[][] WVE, int[][] VE) {
        boolean ok = true;
        for (int i = 0; i < WVE.length; i++) {
            for (int j = 0; j < WVE[i].length; j++) {
                if (WVE[i][j] != VE[i][j]) {
                    ok = false;
                    break;
                }
            }
        }
        return ok;
    }

}
