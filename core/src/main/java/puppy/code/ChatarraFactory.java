/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
public class ChatarraFactory implements ElementoFactory{
    @Override
    public Accionable crearElemento(TextureAtlas atlas, float velocidad, int dano, int efecto) {
        // Selecciona la textura y ajusta los parámetros específicos para cada tipo de chatarra
        
        int aleatorio = MathUtils.random(1, 100); // Genera un número entre 1 y 100
        
        String textura;
        float velocidadChatarra;
        int danoChatarra;
        int efectoChatarra;
        String nombreChatarra;

       
            if (aleatorio <= 30){ // Pizza - velocidad alta, daño alto, efecto moderado
                textura = "pizza";
                velocidadChatarra = velocidad * 0.8f;
                danoChatarra = dano + 5;
                efectoChatarra = efecto - 3;
                nombreChatarra = "Pizza";
            }else if (aleatorio <= 50){ // Donut - velocidad media, daño bajo, efecto bajo
                textura = "donut";
                velocidadChatarra = velocidad * 1.5f;
                danoChatarra = dano + 2;
                efectoChatarra = efecto - 1;
                nombreChatarra = "Dona";
            }else if (aleatorio <= 65){ // Torta - velocidad media-alta, daño muy alto, efecto bajo
                textura = "cake";
                velocidadChatarra = velocidad * 1.2f;
                danoChatarra = dano + 10;
                efectoChatarra = efecto - 2;
                nombreChatarra = "Torta";
            }else if (aleatorio <= 85){// Helado - velocidad muy alta, daño moderado, efecto alto
                textura = "ice_cream";
                velocidadChatarra = velocidad * 1.75f;
                danoChatarra = dano + 5;
                efectoChatarra = efecto - 5;
                nombreChatarra = "Helado";
            }else{ // Taco - velocidad baja, daño alto, efecto moderado
                textura = "taco";
                velocidadChatarra = velocidad * 0.9f;
                danoChatarra = dano * 2;
                efectoChatarra = efecto - 3;
                nombreChatarra = "Taco";
            }

        // Crea el objeto Chatarra con los parámetros específicos
        return new Chatarra(atlas.findRegion(textura), velocidadChatarra, danoChatarra, efectoChatarra, nombreChatarra);
    }
}
