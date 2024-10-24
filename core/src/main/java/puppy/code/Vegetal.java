/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.*;

public class Vegetal extends Alimentos {
    private String nombre;
    public Vegetal(Texture textura, int velocidad, int puntos, int vida, String nombre) {
        super(textura, velocidad, vida);  // Usa el constructor de la clase abstracta Alimentos
        setNombre(nombre);
        setPuntos(puntos);  // Usamos el setter para asignar puntos por defecto a los vegetales
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
        return "Vegetal"; 
    }
    //
    // Metodos Pantallas o Interaccion con Jugador
    //
    public void reiniciar(){ // Lógica personalizada para reiniciar un alimento
      area.y = 480;   // Resetea la posición en pantalla
      setVida(80);    // Reinicia la vida del vegetal
      setPuntos(15);  // Reinicia el puntaje del vegetal
    }
    public void activarEfecto(Tarro tarro){ // Personalizar efectos aplicados a jugador
        //tarro.modificarVida(vida);  // Aplica el efecto de recuperar vida al tarro (jugador)
    }

    public void aplicarModificador(float factor){ // Personalizar modificcador aplicado a puntaje
       setPuntos((int) (puntos * factor));  // Modifica el puntaje según el factor dado 
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
