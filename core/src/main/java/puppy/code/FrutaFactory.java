/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
public class FrutaFactory implements ElementoFactory {
    @Override
     public Accionable crearElemento(TextureAtlas atlas, float velocidad, int puntaje, int efecto) {
        // Selecciona una textura y configura parámetros específicos para cada fruta
        int aleatorio = MathUtils.random(1, 100); // Genera un número entre 1 y 100
        String textura;
        float velocidadFruta;
        int puntajeFruta;
        String nombreFruta;
        
         if (aleatorio <= 25) { // Manzana - Baja velocidad, puntaje bajo
                textura = "apple";
                velocidadFruta = velocidad * 0.8f; 
                puntajeFruta = puntaje + 2; 
                nombreFruta = "Manzana";
         }else if (aleatorio <= 45){ // Banana - Velocidad media, puntaje moderado
                textura = "banana";
                velocidadFruta = velocidad;
                puntajeFruta = puntaje + 3;
                nombreFruta = "Banana";
         }else if (aleatorio <= 60){ // Naranja - Velocidad rápida, puntaje más alto
                textura = "orange";
                velocidadFruta = velocidad * 1.2f; 
                puntajeFruta = puntaje + 5;
                nombreFruta = "Naranja";
         }else if (aleatorio <= 70){ // Frutilla - Muy rápida, puntaje alto
                textura = "strawberry";
                velocidadFruta = velocidad * 1.4f; 
                puntajeFruta = puntaje + 7;
                nombreFruta = "Frutilla";
         }else if (aleatorio <= 75){ // Cereza - Rara y altamente valiosa
                textura = "cherry";
                velocidadFruta = velocidad * 1.5f; 
                puntajeFruta = puntaje + 10; 
                nombreFruta = "Cereza";
         }else if (aleatorio <= 80){ // Durazno - Lenta y Bajo valor
                textura = "peach";
                velocidadFruta = velocidad *0.8f; 
                puntajeFruta = (int) puntaje - (puntaje%2); 
                nombreFruta = "Durazno";
         }else if (aleatorio <= 90){ // Limon - Lenta y Bajo valor
                textura = "lemon";
                velocidadFruta = velocidad *0.75f; 
                puntajeFruta = (int) puntaje - (puntaje%2); 
                nombreFruta = "Limon";

         }else if (aleatorio <= 93){ // Manzana Dorada- Rara y altamente valiosa
                textura = "golden_apple";
                velocidadFruta = velocidad * 2.2f; // Muy rápida
                puntajeFruta = puntaje * 2; // Muy valiosa
                nombreFruta = "ManzanaOro";
         }else if (aleatorio <= 96){ // Cereza Dorada - Rara y altamente valiosa
                textura = "golden_cherry";
                velocidadFruta = velocidad * 2.5f; // Muy rápida
                puntajeFruta = puntaje + 20; // Muy valiosa
                nombreFruta = "CerezaOro";
         }else if (aleatorio <= 99){ // Frutilla Dorada - Rara y altamente valiosa
                textura = "golden_strawberry";
                velocidadFruta = velocidad * 2.3f; // Muy rápida
                puntajeFruta = puntaje + 25; // Muy valiosa
                nombreFruta = "FrutillaOro";
         }else { // Durazno Dorada - Rara y altamente valiosa
                textura = "golden_peach";
                velocidadFruta = velocidad * 2.3f; // Muy rápida
                puntajeFruta = puntaje * 3; // Muy valiosa
                nombreFruta = "DuraznoOro";
          }

        // Crea el objeto Fruta con los parámetros específicos
        return new Fruta(atlas.findRegion(textura), velocidadFruta, puntajeFruta, efecto, nombreFruta);
    }
}
