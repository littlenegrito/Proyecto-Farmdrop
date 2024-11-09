/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.*;

public class Vegetal extends Alimentos {
    private String nombre;
    public Vegetal(TextureRegion textura, float velocidad, int puntos, int vida, String nombre) {
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
    public float obtenerVelocidad() {
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
        aplicarModificador(3); // Multiplicador de puntos
        System.out.println("Suma de puntos: " + obtenerPuntaje());
        tarro.sumarPuntos(obtenerPuntaje());
        int vidaActual = tarro.getVidas();
        int vidaARecuperar = obtenerVida(); // Vida que el vegetal intenta curar
        if(vida < 100){ // limitar puntos de vida
            int vidaFaltante = 100 - vidaActual; // Cantidad de vida para alcanzar el máximo de 100
            int vidaASumar = Math.min(vidaARecuperar, vidaFaltante); 
            System.out.println("Suma de vida: " + vidaASumar);
            tarro.sumarVida(vidaASumar); 
        }
    }

    public void aplicarModificador(float factor){ // Personalizar modificcador aplicado a puntaje
       setPuntos((int) (puntos * factor));  // Modifica el puntaje según el factor dado 
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
