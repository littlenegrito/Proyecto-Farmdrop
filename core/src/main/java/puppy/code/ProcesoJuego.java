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

public abstract class ProcesoJuego {
   protected Array<Accionable> elementos;
    protected Array<Rectangle> elementosPos;
    protected float escala = 0.09f; // factor de escala para las texturas
    protected long lastDropTime;
    protected Sound dropSound;
    protected Music rainMusic;
    protected TextureAtlas atlas;
    
    public ProcesoJuego(TextureAtlas atlas, Sound dropSound, Music rainMusic) {
        this.atlas = atlas;
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
    }
    // Métodos comunes
    public void crear() {
        elementos = new Array<>();
        elementosPos = new Array<>();
        crearElemento(seleccionarFactory());
        rainMusic.setLooping(true);
        rainMusic.play();
    }
    // El Template Method que define los pasos generales
    public final void actualizarJuego(Tarro tarro, int puntos, SpriteBatch batch, boolean[] mostrarIcon, long[] tiempoInicioIcon) {
        modificarJuego(seleccionarFactory()); // utilizar patron factory 
        // Actualización de la dificultad
        if (actualizarDificultad(puntos)) {
            mostrarIcon[0] = true;
            tiempoInicioIcon[0] = System.currentTimeMillis(); // Registrar el tiempo actual
        }
        // Actualizar el movimiento de los elementos (tarro y otros)
        actualizarMovimiento(tarro);
        // Dibujar todos los elementos en pantalla
        actualizarDibujo(batch);
    }
    protected abstract void actualizarDibujo(SpriteBatch batch);
    // Paso común para actualizar dificultad
    protected abstract boolean actualizarDificultad(int puntos);
    // Método abstracto para modificar elementos, dejado a las clases hijas
    protected abstract void modificarJuego(ElementoFactory factory);
    // Método abstracto para actualizar movimiento, delegado a las clases hijas
    protected abstract boolean actualizarMovimiento(Tarro tarro);
    //Método de creación de elementos
    protected abstract void crearElemento(ElementoFactory factory);
    public void destruir() {
        dropSound.dispose();
        rainMusic.dispose();
    }
    public void pausar() {
        rainMusic.stop();
    }
    public void continuar() {
        rainMusic.play();
    }
    protected abstract ElementoFactory seleccionarFactory();
}
