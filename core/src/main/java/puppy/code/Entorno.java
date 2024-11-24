/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Entorno {
    private ProcesoJuego procesoJuego;

     public Entorno(TextureAtlas atlas, Sound dropSound, Music rainMusic) {
        // Aquí se instancian las clases concretas (de la clase abstracta)
        this.procesoJuego = new JuegoEntorno(atlas, dropSound, rainMusic);
    }
     public void crear() {
        procesoJuego.crear();
    }

    public void actualizarJuego(Tarro tarro, int puntos, SpriteBatch batch, boolean[] mostrarIcon, long[] tiempoInicioIcon) {
        procesoJuego.actualizarJuego(tarro, puntos, batch, mostrarIcon, tiempoInicioIcon);
    }
    
    public void actualizarDibujo(SpriteBatch batch) {
        procesoJuego.actualizarDibujo(batch);
    }
    public boolean actualizarMovimiento(Tarro tarro) {
        return procesoJuego.actualizarMovimiento(tarro);
    }
    public boolean actualizarDificultad(int puntos) {
        return procesoJuego.actualizarDificultad(puntos);
    }

    public void destruir() {
        procesoJuego.destruir();
    }

    public void pausar() {
        procesoJuego.pausar();
    }

    public void continuar() {
        procesoJuego.continuar();
    }
   
    
}

