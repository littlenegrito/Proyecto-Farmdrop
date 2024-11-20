/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
public class VegetalFactory implements ElementoFactory {
    public Accionable crearElemento(TextureAtlas atlas, float velocidad, int puntaje, int vida) {
        // Selecciona la textura y ajusta los parámetros para cada tipo de vegetal
        int aleatorio = MathUtils.random(1, 100); // Genera un número entre 1 y 100
        String textura;
        float velocidadVegetal;
        int puntajeVegetal;
        int vidaVegetal;
        String nombreVegetal;

        if (aleatorio <= 25) { // Tomate - Bajo puntaje, moderada vida, velocidad media
                textura = "tomato";
                velocidadVegetal = velocidad * 1.0f;
                puntajeVegetal = puntaje + 1;
                vidaVegetal = vida + 2;
                nombreVegetal = "Tomate";
        }else if (aleatorio <= 45){ // Brócoli - Baja velocidad, alto puntaje, vida alta
                textura = "broccoli";
                velocidadVegetal = velocidad * 0.7f; // Más lento
                puntajeVegetal = puntaje + 3;
                vidaVegetal = vida + 5;
                nombreVegetal = "Brócoli";
        }else if (aleatorio <= 65){ // Calabaza - Velocidad baja, puntaje alto, vida muy alta
                textura = "pumpkin";
                velocidadVegetal = velocidad * 0.6f; // Lenta
                puntajeVegetal = puntaje + 4;
                vidaVegetal = vida + 7;
                nombreVegetal = "Calabaza";
        }else if (aleatorio <= 80){// Zanahoria - Velocidad alta, puntaje moderado, vida media
                textura = "carrot";
                velocidadVegetal = velocidad * 1.3f; // Rápida
                puntajeVegetal = puntaje + 2;
                vidaVegetal = vida + 3;
                nombreVegetal = "Zanahoria";
        }else if (aleatorio <= 95){ // Maíz - Muy rápido, bajo puntaje, vida moderada
                textura = "corn";
                velocidadVegetal = velocidad * 1.5f; // Muy rápido
                puntajeVegetal = puntaje + 1;
                vidaVegetal = vida + 2;
                nombreVegetal = "Maíz";
                // Versiones Doradas
        }else if (aleatorio <= 97){ // Tomate - Alto puntaje y vida
                textura = "golden_tomato";
                velocidadVegetal = velocidad * 2.5f; // Muy rápido
                puntajeVegetal = puntaje + 10;
                vidaVegetal = vida + 10;
                nombreVegetal = "TomateOro";
        }else{ //Broccoli - Alto puntaje y vida
                textura = "golden_tomato";
                velocidadVegetal = velocidad * 3f; // Muy rápido
                puntajeVegetal = puntaje + 15;
                vidaVegetal = vida * 2;
                nombreVegetal = "BroccoliOro";
        }


        // Crea el objeto Vegetal con los parámetros específicos
        return new Vegetal(atlas.findRegion(textura), velocidadVegetal, puntajeVegetal, vidaVegetal, nombreVegetal);
    }
    
}
