/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code.lwjgl3;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import java.io.File;

public class MyTexturePacker {
    public static void packTextures() {
        TexturePacker.Settings settings = new TexturePacker.Settings();
        settings.maxWidth = 2048;  // Aumenta el ancho máximo de la página
        settings.maxHeight = 2048; // Aumenta el alto máximo de la página
        settings.duplicatePadding = false; // Elimina el padding duplicado
        settings.paddingX = 0; // Ajusta padding en X
        settings.paddingY = 0; // Ajusta padding en Y
        settings.minWidth = 1;
        settings.minHeight = 1;


        String inputDir = "input";  // Carpeta relativa para texturas de entrada
        String outputDir = "output"; // Carpeta relativa para el atlas de salida
        String atlasName = "imagenesAtlas"; // Reemplaza con el nombre deseado para el atlas.
        
        System.out.println("Ruta de entrada: " + inputDir);
        System.out.println("Ruta de salida: " + outputDir);

         // Verificación para evitar el re-empaquetado.
        File inputFolder = new File(inputDir);
        File outputFolder = new File(outputDir);
        
        
        // Verificar las carpetas de assets
        if (!inputFolder.exists()) {
            System.out.println("La carpeta de entrada no existe: " + inputDir);
        }
        // Verifica si la carpeta de salida existe, y si no, la crea
        if (!outputFolder.exists()) {
            System.out.println("La carpeta de salida no existe. Creando: " + outputDir);
            outputFolder.mkdirs(); // Crea la carpeta de salida si no existe
        } else {
            // Limpia la carpeta de salida eliminando archivos existentes
            //for (File file : outputFolder.listFiles()) {
               // file.delete();
            //}
        }
        
        File atlasFile = new File(outputDir + "/" + atlasName + ".atlas");
        if (atlasFile.exists()) {
            System.out.println("El atlas "+ atlasName+ " ya existe. No es necesario empaquetar nuevamente.");
            return;
        }
        
        

        // Si el atlas no existe, procesa las texturas.
        System.out.println("Empaquetando texturas en un nuevo atlas...");
        TexturePacker.process(settings, inputDir, outputDir, atlasName);
        System.out.println("Empaquetado completo: Atlas creado en " + outputDir);
    }
}
