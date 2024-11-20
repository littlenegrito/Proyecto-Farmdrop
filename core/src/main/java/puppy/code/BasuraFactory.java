/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
public class BasuraFactory implements ElementoFactory{
    // Selecciona la textura y ajusta los parámetros específicos para cada tipo de chatarra
     @Override
    public Accionable crearElemento(TextureAtlas atlas, float velocidad, int dano, int efecto) {
            int aleatorio = MathUtils.random(1, 100); // Genera un número entre 1 y 100

            String textura;
            float velocidadChatarra;
            int danoChatarra;
            int efectoChatarra;
            String nombreChatarra;
            if (aleatorio <= 50){ // Basura - velocidad baja
                textura = "trash";
                velocidadChatarra = velocidad * 0.8f;
                danoChatarra = dano;
                efectoChatarra = efecto +3;
                nombreChatarra = "Basura";
            }else if (aleatorio <= 80){ // Lata - velocidad media
                textura = "can";
                velocidadChatarra = velocidad * 1.2f;
                danoChatarra = dano;
                efectoChatarra = efecto - 1;
                nombreChatarra = "Lata";
            }else{ // Papel - velocidad alto
                textura = "paper";
                velocidadChatarra = velocidad * 1.8f;
                danoChatarra = dano;
                efectoChatarra = efecto - 3;
                nombreChatarra = "Papel";
            }

        // Crea el objeto Chatarra con los parámetros específicos
        return new Basura(atlas.findRegion(textura), velocidadChatarra, danoChatarra, efectoChatarra, nombreChatarra);
    }
}
