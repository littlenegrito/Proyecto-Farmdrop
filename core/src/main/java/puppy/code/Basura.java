/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;

public class Basura extends Peligros {
    private String nombre; // Nombre de la basura

    public Basura(Texture textura, int velocidad, int daño, int efecto, String nombre) {
        super(textura, velocidad, daño, efecto);
        setNombre(nombre);
    }

    @Override
    public int obtenerDaño() {
        return daño; // Devuelve el daño que la basura causa al jugador
    }
    @Override
    public String obtenerNombre() {
        return nombre;
    }
    @Override
    public int obtenerVelocidad() {
        return velocidad;
    }
    @Override
    public String obtenerTipo() {
        return "Basura"; 
    }


    @Override
    public void activarEfecto(Tarro tarro) {
        // Implementar cómo la basura afecta al jugador. Por ejemplo:
        //tarro.modificarVida(daño); // Supongamos que el Tarro tiene un método para perder vida
    }
    @Override
    public void reiniciar(){ // Lógica personalizada para reiniciar un alimento
        area.y = 480;
        daño = -100;
    }
    @Override
    public void aplicarModificador(float factor) {
        // Ajustar la velocidad o daño según el factor
        this.velocidad *= factor;
        this.daño *= factor;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
