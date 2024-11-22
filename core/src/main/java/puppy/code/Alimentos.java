/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
public abstract class Alimentos implements Accionable {
    protected int puntos;
    protected int vida;
    protected float velocidad;
    
    protected TextureRegion textura;
    protected Rectangle area;
    protected int efecto;
    
   public Alimentos(TextureRegion textura, float velocidad, int vida) {
        setVida(vida); // Encapsulado con setter
        setTextura(textura); // Encapsulado con setter
        setVelocidad(velocidad); // Encapsulado con setter
        this.area = new Rectangle(MathUtils.random(0, 1920 - textura.getRegionWidth()), 960, textura.getRegionWidth(), textura.getRegionHeight());
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
        System.out.println("Se recogio un alimento");
        return area.overlaps(tarroArea);
    }

    @Override
    public void desaparecer() {
        // Puedes implementar la lógica de eliminar o reciclar el elemento si es necesario.
    }
    @Override
    public TextureRegion obtenerTextura() { // Cambiado a TextureRegion
        return textura; // Devolver la textura de la fruta
    }
    public abstract float obtenerVelocidad(); 
    public abstract void activarEfecto(Tarro tarro);  // El método que define el efecto sobre el jugador
    //
    // Getters, abstractos
    //
    public abstract int obtenerVida();
    public abstract int obtenerPuntaje();
    public abstract String obtenerNombre();
    public abstract String obtenerTipo();
    public abstract void reiniciar(); // Restaura el estado del elemento a su condición inicial, útil para reciclar objetos en lugar de crear nuevos durante el juego.
    public abstract void aplicarModificador(float factor); // Ajusta temporalmente las propiedades del elemento, como aumentar su velocidad o efecto, en función de potenciadores o cambios en la dificultad.
    
    //
    // Metodos concretos
    //
    public boolean esBueno() {
        return true;
    }
    public void detenerMovimiento() { // Pausa el movimiento del elemento, útil en caso de que el juego se detenga o necesite congelar temporalmente la acción.
        this.velocidad = 0;// Implementar si necesitas pausar el movimiento
    }
    public boolean estaEnPantalla() {
        return area.y + area.height >= 0 && area.y <= 480;
    }
    //
    // Setters
    //
    public void setVida(int vida) {
        this.vida = vida;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public void setTextura(TextureRegion textura) {   
        this.textura = textura;
    }

    public void setVelocidad(float velocidad) {
        this.velocidad = velocidad;
    }
    public void setEfecto(int efecto){
        this.efecto = efecto;
    }
}
