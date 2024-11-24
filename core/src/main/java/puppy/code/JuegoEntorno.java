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
public class JuegoEntorno extends ProcesoJuego { // clase concreta que define todos los parametros y valores
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
    private long incrementoTiempoGeneracion = 100_000_000;
    private long limiteTiempoGeneracion = 100_000_000;
    
     // Umbrales de puntuación para aumentar dificultad
    private int umbralPuntos = 250; // Cada ciertos puntos aumenta dificultad
    private int puntosAlcanzados = 0;
    
    // Parámetros de generación dinámica
    private long tiempoGeneracionBase = 800_000_000; // Tiempo inicial en nanosegundos (0.8s)
    private long tiempoGeneracionActual = tiempoGeneracionBase; // Tiempo variable entre elementos
    
    public JuegoEntorno(TextureAtlas atlas, Sound dropSound, Music rainMusic) {
        super(atlas, dropSound, rainMusic);
    }
    @Override
    protected void modificarJuego(ElementoFactory factory) {
        // Implementación específica para modificar el juego según la lógica de dificultad
        if (TimeUtils.nanoTime() - lastDropTime > tiempoGeneracionActual) {
            crearElemento(factory);
        }
    }
    @Override
    protected void crearElemento(ElementoFactory factory) {
            float anchoOriginal = atlas.findRegion("apple").getRegionWidth() * escala;
            float altoOriginal = atlas.findRegion("apple").getRegionHeight() * escala;

            // Generar la posición de los elementos asegurándose de que no salgan de la pantalla
            Rectangle elementoPos = new Rectangle(
                MathUtils.random(0, 1920 - anchoOriginal), // Asegúrate de que el ancho esté escalado
                960, // Mantener la posición vertical
                anchoOriginal, // Ancho escalado
                altoOriginal // Alto escalado
            );
            System.out.println("Creando elemento en posición: " + elementoPos);
           
            Accionable nuevoElemento = factory.crearElemento(atlas, obtenerVelocidadBase(factory), obtenerPuntajeBase(factory), obtenerEfectoBase(factory)); // crear la interfaz para asignarle un tipo de elemento
            // Añadir el nuevo elemento al arreglo
            if (nuevoElemento != null){
                elementos.add(nuevoElemento);
                elementosPos.add(elementoPos);
            }else System.out.println("Error al crear objeto");
            
            
            lastDropTime = TimeUtils.nanoTime();
    }
    @Override
    protected boolean actualizarDificultad(int puntos) {
        int nuevosUmbrales = puntos / umbralPuntos;
        if (nuevosUmbrales > puntosAlcanzados / umbralPuntos) {
            // Aumentar dificultad
            velocidadBase += incrementoVelocidad;
            if (danioBaseChatarra < 25) danioBaseChatarra += incrementoDaño;
            if (curacionBaseVegetal < 20) curacionBaseVegetal = Math.max(curacionBaseVegetal - decrementoCuracion, 1);
            puntajeBaseFruta += incrementoPuntajeFruta;
            puntajeBaseVegetal += incrementoPuntajeVegetal;
            
            // Reducir tiempo de generación para mayor frecuencia
            tiempoGeneracionActual = Math.max(tiempoGeneracionActual - incrementoTiempoGeneracion, limiteTiempoGeneracion); 
            
            puntosAlcanzados = puntos;
            System.out.println("Dificultad aumentada! Velocidad Base: " + velocidadBase + ", Daño Chatarra: " + danioBaseChatarra + ", Curación Vegetal: " + curacionBaseVegetal + ", Puntaje Fruta: " + puntajeBaseFruta + ", Puntaje Vegetal: " + puntajeBaseVegetal);
            return true;
        }
        return false;
    }
    @Override
    protected boolean actualizarMovimiento(Tarro tarro) {
        // Actualizar posiciones de los elementos, usar lógica específica de la clase hija
        for (int i = 0; i < elementosPos.size; i++) {
            Accionable elemento = elementos.get(i);
            Rectangle elementoPos = elementosPos.get(i);
            float velocidad = elemento.obtenerVelocidad();
            elementoPos.y -= velocidad * Gdx.graphics.getDeltaTime();
            elemento.moverElemento(Gdx.graphics.getDeltaTime());

            if (elementoPos.y + elementoPos.height < 0) {
                elemento.desaparecer();
                elementos.removeIndex(i);
                elementosPos.removeIndex(i);
                i--;
                continue;
            }

            if (elementoPos.overlaps(tarro.getArea())) {
                elemento.activarEfecto(tarro);
                dropSound.play();
                elementos.removeIndex(i);
                elementosPos.removeIndex(i);
                i--;
            }
        }
        return tarro.getVidas() > 0;
    } 
    @Override
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
    @Override
    protected ElementoFactory seleccionarFactory() {
        int puntosEscalados = Math.min(puntosAlcanzados / umbralPuntos, 10); // Rango para limitar el cambio gradual de 0 a 10

        // Probabilidades iniciales y límites de cambio
        int probInicialFruta = 50;
        int probInicialVegetal = 35;
        int probInicialChatarra = 10;
        int probInicialBasura = 5;

        int probLimiteFruta = 40;
        int probLimiteVegetal = 30;
        int probLimiteChatarra = 20;
        int probLimiteBasura = 10;

        // Calcular la reducción proporcional de frutas y vegetales y el aumento de peligros
        int probFruta = probInicialFruta - ((probInicialFruta - probLimiteFruta) * puntosEscalados / 10);
        int probVegetal = probInicialVegetal - ((probInicialVegetal - probLimiteVegetal) * puntosEscalados / 10);
        int probChatarra = probInicialChatarra + ((probLimiteChatarra - probInicialChatarra) * puntosEscalados / 10);
        //int probBasura = probInicialBasura + ((probLimiteBasura - probInicialBasura) * puntosEscalados / 10);

        int tipoElemento = MathUtils.random(1, 100);

        if (tipoElemento <= probFruta) {
            return new FrutaFactory();
        } else if (tipoElemento <= probFruta + probVegetal) {
            return new VegetalFactory();
        } else if (tipoElemento <= probFruta + probVegetal + probChatarra) {
            return new ChatarraFactory();
        } else {
            return new BasuraFactory();
        }
    }
    private float obtenerVelocidadBase(ElementoFactory factory) {
        if (factory instanceof VegetalFactory) return (float) (velocidadBase * 1.5);
        if (factory instanceof BasuraFactory) return (float) (velocidadBase * 0.9);
        return velocidadBase;
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
}
