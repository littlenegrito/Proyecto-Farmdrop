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
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        settings.duplicatePadding = true;      // Evita problemas de bordes en las texturas
        settings.minWidth = 1;                 // Ancho mínimo de texturas a empaquetar
        settings.minHeight = 1;                // Alto mínimo de texturas a empaquetar


        String inputDir = "C:\\Users\\Acer\\Downloads\\Proyecto-FarmDrop\\assets\\input";// Reemplaza con la ruta a tus texturas de entrada.
        String outputDir = "C:\\Users\\Acer\\Downloads\\Proyecto-FarmDrop\\assets\\output"; // Reemplaza con la ruta donde quieres que se guarde el atlas.
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
