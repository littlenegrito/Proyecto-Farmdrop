/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
public interface ElementoFactory {
    Accionable crearElemento(TextureAtlas atlas, float velocidad, int puntaje, int efecto);
}
