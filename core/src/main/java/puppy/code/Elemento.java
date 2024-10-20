/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
public interface Elemento {
    
    void activarEfecto(Tarro tarro); // El método que define el efecto sobre el jugador
    
    // GETTERS
    
    int obtenerVida(); // Retorna el valor de vida que se debe modificar al jugador
    int obtenerPuntaje(); // Retorna el puntaje que otorga el elemento
    int obtenerVelocidad(); // Retorna la velocidad con la que el elemento cae, permitiendo diferenciar entre elementos que caen rápido o lento.
    int obtenerTipo();// Para identificar rápidamente el tipo de elemento (fruta, basura, etc.) sin tener que usar instancias de clases específicas.
    String obtenerNombre(); // Para identificar elemento especifico.
    
    boolean esBueno(); // Identifica cual de las clases abstractas corresponde
    boolean esRecogido(Rectangle tarroArea); //Verifica si el elemento ha sido recogido por el jugador al detectar la colisión con el área del tarro.
    boolean esDestructible(); // Indica si un elemento puede ser destruido por otros medios, como disparos u obstáculos antes de ser recogido.
    
    // Metodos Pantalla
    
    boolean estaEnPantalla(); // Verifica si el elemento sigue en la pantalla o si ha salido del área visible, para determinar cuándo eliminarlo o reciclarlo.
    void moverElemento(float deltaTime);//Controla el movimiento del elemento desde que cae hasta que desaparece o es recogido.
    void desaparecer();//Para cuando un elemento toca el suelo y no fue recogido, asegurando que sea eliminado o desaparezca.
    void dibujar(SpriteBatch batch);// Método para dibujar el elemento en pantalla, usando su textura correspondiente.
    void reiniciar(); // Restaura el estado del elemento a su condición inicial, útil para reciclar objetos en lugar de crear nuevos durante el juego.
    
    void aplicarModificador(float factor); // Ajusta temporalmente las propiedades del elemento, como aumentar su velocidad o efecto, en función de potenciadores o cambios en la dificultad.
    void detenerMovimiento(); // Pausa el movimiento del elemento, útil en caso de que el juego se detenga o necesite congelar temporalmente la acción.
    
}
