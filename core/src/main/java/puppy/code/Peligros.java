/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;

public abstract class Peligros implements Accionable {
     protected int daño;
     protected int velocidad;
     protected Texture textura;
     protected Rectangle area;
     protected int efecto;

    public Peligros(Texture textura, int velocidad, int daño, int efecto) {
        setDaño(daño); // Encapsulado con setter
        setTextura(textura);   // Encapsulado con setter
        setVelocidad(velocidad);  // Encapsulado con setter
        setEfecto(efecto); // Encapsulado con setter
        this.area = new Rectangle(MathUtils.random(0, 800 - 64), 480, 64, 64);
    }
    //
    // Metodos interfaces
    //
    @Override
    public void moverElemento(float deltaTime) {
        area.y -= velocidad * deltaTime;
    }
    
    @Override
    public void dibujar(SpriteBatch batch) {
        batch.draw(textura, area.x, area.y);
    }
    
    @Override
    public boolean esRecogido(Rectangle tarroArea) {
        return area.overlaps(tarroArea);
    }

    @Override
    public void desaparecer() {
        // Puedes implementar la lógica de eliminar o reciclar el elemento si es necesario.
    }
    @Override
    public Texture obtenerTextura() {
        return textura; // Devolver la textura de la fruta
    }
    public abstract void activarEfecto(Tarro tarro);  // El método que define el efecto sobre el jugador
    
    //
    // Getters, abstractos
    //
    public boolean esBueno() {
        return false;
    }
    public abstract int obtenerDaño();
    public abstract String obtenerNombre();
    public abstract int obtenerVelocidad(); 
    public abstract String obtenerTipo();
    
    public abstract void reiniciar(); // Restaura el estado del elemento a su condición inicial, útil para reciclar objetos en lugar de crear nuevos durante el juego.
    public abstract void aplicarModificador(float factor); // Ajusta temporalmente las propiedades del elemento, como aumentar su velocidad o efecto, en función de potenciadores o cambios en la dificultad.
    //
    // Metodos Pantallas o Interaccion con Jugador
    //

    public void detenerMovimiento() { // Pausa el movimiento del elemento, útil en caso de que el juego se detenga o necesite congelar temporalmente la acción.
        // Implementar si necesitas pausar el movimiento
    }
    public boolean estaEnPantalla() {
        return area.y + 64 >= 0;
    }
    //
    // Setters
    //
    public void setDaño(int daño) {
        this.daño = daño;
    }
    public void setTextura(Texture textura) {
        this.textura = textura;
    }

    public void setVelocidad(int velocidad) {
        this.velocidad = velocidad;
    }
    public void setEfecto(int efecto){
        this.efecto = efecto;
    }
    
}