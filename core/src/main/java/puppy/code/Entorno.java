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
    private float escala = 0.1f; // Factor de escala para los elementos
    
    private long lastDropTime;
    private Sound dropSound;
    private Music rainMusic;
    private TextureAtlas atlas; 

     public Entorno(TextureAtlas atlas, Sound dropSound, Music rainMusic) {
        this.atlas = atlas;
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
    }
    public void crear() {
        elementos = new Array<>();
        elementosPos = new Array<>();
        crearElemento();
        rainMusic.setLooping(true);
	rainMusic.play();
    }

   private void crearElemento() {
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
           
            Accionable nuevoElemento; // crear la interfaz para asignarle un tipo de elemento
            int tipoElemento = MathUtils.random(1, 10); // Determina el tipo de elemento basado en probabilidades
            
            // Generar aleatoriamente un elemento bueno o malo
            if (tipoElemento <= 4) { // 40% de probabilidad para fruta
                 nuevoElemento = new Fruta(atlas.findRegion(seleccionarTexturaFruta()), 200, 5, 5, "Fruta");
            } else if (tipoElemento <= 7) { // 20% de probabilidad para vegetales
                nuevoElemento = new Chatarra(atlas.findRegion(seleccionarTexturaChatarra()), 175, 5, 1, "Chatarra");
            } else if (tipoElemento <= 9) { // 30% de probabilidad para chatarra
                nuevoElemento = new Vegetal(atlas.findRegion(seleccionarTexturaVegetal()), 300, 3, 10, "Vegetal");
            } else { // |0% de probabilidad para basura
                nuevoElemento = new Basura(atlas.findRegion(seleccionarTexturaBasura()), 150, 10, 2, "Basura");
            }
            // Añadir el nuevo elemento al arreglo
            elementos.add(nuevoElemento);
            elementosPos.add(elementoPos);
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
               // Remover el elemento tras la colisión
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
           if (textura != null) {
                batch.draw(textura, elementoPos.x, elementoPos.y, anchoOriginal * escala, altoOriginal * escala);
            }
           else{
               System.out.println("No se encontro textura");
           }
        }    
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
    
    private String seleccionarTexturaFruta() {
        int tipoFruta = MathUtils.random(1, 5); // 1 a 5 para frutas diferentes
        switch (tipoFruta) {
            case 1:
                return "apple"; // Usa el nombre del atlas, sin la extensión
            case 2:
                return "banana";
            case 3:
                return "orange";
            case 4:
                return "strawberry";
            case 5:
                return "cherry";
            default:
                return "apple"; // Valor por defecto
        }
    }
    private String seleccionarTexturaVegetal() {
        int tipoVegetal = MathUtils.random(1, 5); // 1 a 5 para vegetales diferentes
        switch (tipoVegetal) {
            case 1:
                return "tomato"; 
            case 2:
                return "broccoli";
            case 3:
                return "pumpkin";
            case 4:
                return "carrot";
            case 5:
                return "corn";
            default:
                return "tomato"; // Valor por defecto
        }
    }
    private String seleccionarTexturaBasura() {
        int tipoBasura = MathUtils.random(1, 5); // 1 a 5 para basuras diferentes
        switch (tipoBasura) {
            case 1:
                return "trash"; // Asegúrate de que la ruta sea correcta
            case 2:
                return "trash";
            case 3:
                return "trash";
            case 4:
                return "trash";
            case 5:
                return "trash";
            default:
                return "trash"; // Valor por defecto
        }
    }
    private String seleccionarTexturaChatarra() {
    int tipoChatarra = MathUtils.random(1, 5); // 1 a 5 para chatarras diferentes
        switch (tipoChatarra) {
            case 1:
                return "pizza"; 
            case 2:
                return "pollo";
            case 3:
                return "burger";
            case 4:
                return "milkshake";
            case 5:
                return "taco";
            default:
                return "pizza"; // Valor por defecto
        }
    }
}

