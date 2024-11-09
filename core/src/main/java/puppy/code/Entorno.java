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
    private Array<Accionable> elementos;
    private Array<Rectangle> elementosPos;
    private float escala = 0.1f; // Factor de escala para la textura de los elementos 
    
    private long lastDropTime;
    private long lastUpdateDifficultyTime; // Medida para controlar dificultad
    private Sound dropSound;
    private Music rainMusic;
    private TextureAtlas atlas;
    
    // Variables de dificultad iniciales
    private float velocidadBase = 175.0f;
    private int puntajeBaseFruta = 3;
    private int puntajeBaseVegetal = 5;
    private int danioBaseChatarra = 10;
    private int curacionBaseVegetal = 2;
    
     // Factores de incremento
    private float incrementoVelocidad = 10f; // Incremento por cada umbral
    private int incrementoDaño = 1;
    private int decrementoCuracion = 1;
    private int incrementoPuntajeFruta = 2;
    private int incrementoPuntajeVegetal = 1;
    
     // Umbrales de puntuación para aumentar dificultad
    private int umbralPuntos = 50; // Cada 100 puntos aumenta dificultad
    private int puntosAlcanzados = 0;

     public Entorno(TextureAtlas atlas, Sound dropSound, Music rainMusic) {
        this.atlas = atlas;
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
        this.lastUpdateDifficultyTime = TimeUtils.nanoTime();
    }
    public void crear() {
        elementos = new Array<>();
        elementosPos = new Array<>();
        crearElemento();
        rainMusic.setLooping(true);
	rainMusic.play();
    }
    
public void actualizarDificultad(int puntos) {
        // Determina cuántos umbrales se han alcanzado
        int nuevosUmbrales = puntos / umbralPuntos;
        if (nuevosUmbrales > puntosAlcanzados / umbralPuntos) {
            // Aumentar dificultad
            velocidadBase += incrementoVelocidad;
            if(danioBaseChatarra<25) danioBaseChatarra += incrementoDaño;
            if(curacionBaseVegetal<10) curacionBaseVegetal = Math.max(curacionBaseVegetal - decrementoCuracion, 1); // Evita que sea 0
            puntajeBaseFruta += incrementoPuntajeFruta;
            puntajeBaseVegetal += incrementoPuntajeVegetal;
            
            puntosAlcanzados = puntos; // Actualizar puntos alcanzados
            System.out.println("Dificultad aumentada! Velocidad: " + velocidadBase + ", Daño Chatarra: " + danioBaseChatarra + ", Curación Vegetal: " + curacionBaseVegetal + ", Puntaje Fruta: " + puntajeBaseFruta + ", Puntaje Vegetal: " + puntajeBaseVegetal);
        }
        else System.out.println("No se cruza el umbral");
    }
    
   private void crearElemento() {
            ElementoFactory factory = seleccionarFactory(); // utilizar patron factory
            float anchoOriginal = atlas.findRegion("pizza").getRegionWidth() * escala;
            float altoOriginal = atlas.findRegion("pizza").getRegionHeight() * escala;

            // Generar la posición de los elementos asegurándose de que no salgan de la pantalla
            Rectangle elementoPos = new Rectangle(
                MathUtils.random(0, 1600 - anchoOriginal), // Asegúrate de que el ancho esté escalado
                960, // Mantener la posición vertical
                anchoOriginal, // Ancho escalado
                altoOriginal // Alto escalado
            );
            System.out.println("Creando elemento en posición: " + elementoPos);
           
            Accionable nuevoElemento = factory.crearElemento(atlas, velocidadBase, obtenerPuntajeBase(factory), obtenerEfectoBase(factory)); // crear la interfaz para asignarle un tipo de elemento
            // Añadir el nuevo elemento al arreglo
            if (nuevoElemento != null){
                elementos.add(nuevoElemento);
                elementosPos.add(elementoPos);
            }else System.out.println("Error al crear objeto");
            
            
            lastDropTime = TimeUtils.nanoTime();
    }
    public boolean actualizarMovimiento(Tarro tarro){
        // Generar nuevos elementos si ha pasado suficiente tiempo
       if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearElemento();
       // Actualizar la posición de los elementos
       for (int i = 0; i < elementosPos.size; i++) {
           Accionable elemento = elementos.get(i);
           Rectangle elementoPos = elementosPos.get(i);
           float velocidad = elemento.obtenerVelocidad();
           elementoPos.y -= velocidad * Gdx.graphics.getDeltaTime(); // Aplicar la velocidad del elemento

           elemento.moverElemento(Gdx.graphics.getDeltaTime()); // Llamada a moverElemento()
           // Verificar si el elemento ha caido al suelo
           if (elementoPos.y + elementoPos.height < 0) { // Si cae fuera de la pantalla
                elemento.desaparecer();
                elementos.removeIndex(i);
                elementosPos.removeIndex(i);
                i--;
                continue;
           }
           if (elementoPos.overlaps(tarro.getArea())) { // Verificar si el elemento ha sido recogido
               System.out.println("Aplicar efecto");
               elemento.activarEfecto(tarro);
               dropSound.play();
               elementos.removeIndex(i);
               elementosPos.removeIndex(i);
               i--;
           }         
        }
        return tarro.getVidas() > 0;
    }

    public void actualizarDibujo(SpriteBatch batch) {
        // Dibujar los elementos según su tipo
        for (int i = 0; i < elementosPos.size; i++) {
            Rectangle elementoPos = elementosPos.get(i);
            Accionable elemento = elementos.get(i);
            
            // Obtener el tamaño original de la textura
            TextureRegion textura = elemento.obtenerTextura();
            float anchoOriginal = textura.getRegionWidth();
            float altoOriginal = textura.getRegionHeight();

            // Dibujar la textura correspondiente
           if (textura != null) batch.draw(textura, elementoPos.x, elementoPos.y, anchoOriginal * escala, altoOriginal * escala);
           else System.out.println("No se encontro textura");
        }    
    }
    
    private ElementoFactory seleccionarFactory() { // Distribuir la generación de elementos
        int tipoElemento = MathUtils.random(1, 10);
        if (tipoElemento <= 4) return new FrutaFactory();
        else if (tipoElemento <= 7) return new VegetalFactory();
        else if (tipoElemento <= 9) return new ChatarraFactory();
        else return new BasuraFactory();
    }

    private int obtenerPuntajeBase(ElementoFactory factory) {
        if (factory instanceof FrutaFactory) return puntajeBaseFruta;
        if (factory instanceof VegetalFactory) return puntajeBaseVegetal;
        if (factory instanceof ChatarraFactory) return danioBaseChatarra;
        if (factory instanceof BasuraFactory) return danioBaseChatarra;
        return 0;
    }
    
    private int obtenerEfectoBase(ElementoFactory factory) {
        if (factory instanceof VegetalFactory) return curacionBaseVegetal;
        if (factory instanceof ChatarraFactory) return 1; // efecto de prueba
        if (factory instanceof BasuraFactory) return 2; // efecto de prueba
        return 0;
    }
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
}

