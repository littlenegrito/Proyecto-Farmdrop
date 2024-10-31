/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;
public class Fruta extends Alimentos {
    private String nombre;
    public Fruta(TextureRegion textura, float velocidad, int puntos, int vida, String nombre) { 
        super(textura, velocidad, vida); // Llama al constructor de la clase abstracta
        setNombre(nombre);            // Asigna el nombre a la instancia
        setPuntos(puntos);         // Asigna los puntos de la instancia
    }
    //
    // Getters
    //
     @Override
    public int obtenerVida() {
        return vida;
    }
    @Override
    public int obtenerPuntaje() {
        return puntos;
    }
    @Override
    public String obtenerNombre() {
        return nombre;
    }
    @Override
    public float obtenerVelocidad() {
        return velocidad;
    }
    @Override
    public String obtenerTipo() {
        return "Fruta"; 
    }
    //
    // Metodos Pantallas o Interaccion con Jugador
    //
    @Override
    public void reiniciar(){ // Lógica personalizada para reiniciar un alimento
        area.y = 480;
        vida = 100;
        puntos = 10;
    }
    @Override
    public void activarEfecto(Tarro tarro){ // Personalizar efectos aplicados a jugador
        System.out.println("Suma de puntos: " + obtenerPuntaje());
        tarro.sumarPuntos(obtenerPuntaje());
    }
    @Override
    public void aplicarModificador(float factor){ // Personalizar modificcador aplicado a puntaje
        setPuntos((int) (puntos * factor));  // Modifica el puntaje según el factor dado 
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
