/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
public interface Accionable {
    public boolean esRecogido(Rectangle tarroArea); //Verifica si el elemento ha sido recogido por el jugador al detectar la colisión con el área del tarro.
    public void moverElemento(float deltaTime);//Controla el movimiento del elemento desde que cae hasta que desaparece o es recogido.
    public void desaparecer();//Para cuando un elemento toca el suelo y no fue recogido, asegurando que sea eliminado o desaparezca.
    public void dibujar(SpriteBatch batch);// Método para dibujar el elemento en pantalla, usando su textura correspondiente.
    void activarEfecto(Tarro tarro); // Método para aplicar el efecto al tarro
    Texture obtenerTextura(); // Método para obtener la textura a dibujar
}
