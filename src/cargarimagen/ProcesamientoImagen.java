package cargarimagen;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Luis
 */
public class ProcesamientoImagen {

    //Imagen actual que se ha cargado
    private BufferedImage imageActual;
    private int[][] matrizImagen;

    //Método que devuelve una imagen abierta desde archivo
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
        int fila = this.imageActual.getWidth();
        int colum = this.imageActual.getHeight();
        
        System.out.println("Tamaño en pixeles : " + fila + "px - " + colum +" px" );
        matrizImagen = new int[fila][colum];
        //Recorremos la imagen píxel a píxel
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < colum; j++) {
                //Almacenamos el color del píxel
               
                colorAux = new Color(this.imageActual.getRGB(i, j));
                
                //int srcPixel = this.imageActual.getRGB(i, j);
              
                
                //Calculamos la media de los tres canales (rojo, verde, azul)
                mediaPixel = (int) ((colorAux.getRed() + colorAux.getGreen() + colorAux.getBlue()) / 3);
                
                if(mediaPixel>128){
                matrizImagen[i][j] = 0;
                }else{
                 matrizImagen[i][j] = 1;
                }
    
                colorSRGB = (mediaPixel << 16) | (mediaPixel << 8) | mediaPixel;
                //Asignamos el nuevo valor al BufferedImage
                imageActual.setRGB(i, j, colorSRGB);
            }
            System.out.print("\n");
        }
        //Retornamos la imagen
        return imageActual;
    }

    public void mostrarBinariodeImagen() {
        int fila = imageActual.getWidth();
        int colum = imageActual.getHeight();

        matrizImagen = new int[fila][colum];
        for (int i = 0; i < fila; i++) {
            for (int j = 0; j < colum; j++) {
                int srcPixel = imageActual.getRGB(i, j);
                 
            }
        }
    }

    public String mostrarMatriz() {
        int fila = imageActual.getWidth();
        int colum = imageActual.getHeight();
        String cadenamatriz = "";
        for (int i = 0; i < colum; i++) {
            for (int j = 0; j < fila; j++) {
               cadenamatriz =  cadenamatriz + "," +matrizImagen[j][i];
            }
            cadenamatriz = cadenamatriz + "\n";
        }
        return cadenamatriz;
    }
}
