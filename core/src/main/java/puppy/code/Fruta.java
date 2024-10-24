/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;
public class Fruta extends Alimentos {
    private String nombre;
    public Fruta(Texture textura, int velocidad, int puntos, int vida, String nombre) { 
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
    public int obtenerVelocidad() {
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
        // Lógica para aplicar el efecto específico de la Fruta en el jugador
        //tarro.modificarVida(vida);  // Ejemplo: aumentar la vida del jugador 
    }
    @Override
    public void aplicarModificador(float factor){ // Personalizar modificcador aplicado a puntaje
         puntos *= factor;  // Ejemplo: modifica el puntaje de la fruta basado en un multiplicador
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
