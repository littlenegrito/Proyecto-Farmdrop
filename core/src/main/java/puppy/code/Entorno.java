/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Entorno {
    private Array<Accionable> elementos;
    private Array<Rectangle> elementosPos;
    
    private long lastDropTime;
    private Sound dropSound;
    private Music rainMusic;

     public Entorno(Sound dropSound, Music rainMusic) {
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
    private int determinarCantidadElementos() {
        return MathUtils.random(1, 10); // Crear entre 1 y 10 elementos aleatoriamente
    }
   private void crearElemento() {
       int cantidad = determinarCantidadElementos();
        for (int i = 0; i < cantidad; i++) { // Bucle para crear la cantidad determinada de elementos
            // Crear un nuevo rectángulo de posición para cada elemento
            Rectangle elementoPos = new Rectangle(MathUtils.random(0, 800 - 64), 480, 64, 64);
           
            Accionable nuevoElemento; // crear la interfaz para asignarle un tipo de elemento
            int tipoElemento = MathUtils.random(1, 10); // Determina el tipo de elemento basado en probabilidades
            
            // Generar aleatoriamente un elemento bueno o malo
            if (tipoElemento <= 4) { // 40% de probabilidad para fruta
                nuevoElemento = new Fruta(new Texture(Gdx.files.internal(seleccionarTexturaFruta())), 5, 5, 10, "Fruta");
            } else if (tipoElemento <= 7) { // 20% de probabilidad para vegetales
                nuevoElemento = new Chatarra(new Texture(Gdx.files.internal(seleccionarTexturaChatarra())), 5, 3, -5, "Chatarra");
            } else if (tipoElemento <= 9) { // 30% de probabilidad para chatarra
                nuevoElemento = new Vegetal(new Texture(Gdx.files.internal(seleccionarTexturaVegetal())), 5, 3, 10, "Vegetal");
            } else { // |0% de probabilidad para basura
                nuevoElemento = new Basura(new Texture(Gdx.files.internal(seleccionarTexturaBasura())), 5, 3, -10, "Basura");
            }
            // Añadir el nuevo elemento al arreglo
            elementos.add(nuevoElemento);
            elementosPos.add(elementoPos);
        }
        lastDropTime = TimeUtils.nanoTime();
    }
    public boolean actualizarMovimiento(Tarro tarro){
        // Generar nuevos elementos si ha pasado suficiente tiempo
       if (TimeUtils.nanoTime() - lastDropTime > 100000000) crearElemento();
       // Actualizar la posición de los elementos
       for (int i = 0; i < elementosPos.size; i++) {
           Accionable elemento = elementos.get(i);
           Rectangle elementoPos = elementosPos.get(i);
           elementoPos.y -= 300 * Gdx.graphics.getDeltaTime();

           elemento.moverElemento(Gdx.graphics.getDeltaTime()); // Llamada a moverElemento()

           // Verificar si el elemento ha caido al suelo
           if (elementoPos.y + 64 < 0) { // Si cae fuera de la pantalla
                elemento.desaparecer();
                elementos.removeIndex(i);
                elementosPos.removeIndex(i);
                i--;
                continue;
           }
           if (elemento.esRecogido(tarro.getArea())) { // Verificar si el elemento ha sido recogido
               elemento.activarEfecto(tarro);
               if (elemento instanceof Alimentos) {
                   dropSound.play();
                   tarro.sumarPuntos(10);
               }
               else if(elemento instanceof Peligros){
                   // logica a manejar
               }
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

            // Dibujar la textura correspondiente
            batch.draw(elemento.obtenerTextura(), elementoPos.x, elementoPos.y);
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
                return "apple.png"; // Asegúrate de que la ruta sea correcta
            case 2:
                return "banana.png";
            case 3:
                return "orange.png";
            case 4:
                return "cherry.png";
            case 5:
                return "strawberry.png";
            default:
                return "apple.png"; // Valor por defecto
        }
    }
    private String seleccionarTexturaVegetal() {
        int tipo = MathUtils.random(1, 4); // 1 a 5 para vegetales diferentes
        switch (tipo) {
            case 1:
                return "tomato.png"; // Asegúrate de que la ruta sea correcta
            case 2:
                return "pumpkin.png";
            case 3:
                return "broccoli.png";
            case 4:
                return "carrot.png";
            case 5:
                return "corn.png";
            default:
                return "tomato.png"; // Valor por defecto
        }
    }
    private String seleccionarTexturaBasura() {
        int tipoBasura = MathUtils.random(1, 5); // 1 a 5 para basuras diferentes
        switch (tipoBasura) {
            case 1:
                return "basura.png"; // Asegúrate de que la ruta sea correcta
            case 2:
                return "basura.png";
            case 3:
                return "basura.png";
            case 4:
                return "basura.png";
            case 5:
                return "basura.png";
            default:
                return "basura.png"; // Valor por defecto
        }
    }
    private String seleccionarTexturaChatarra() {
        int tipoBasura = MathUtils.random(1, 5); // 1 a 5 para chatarras diferentes
        switch (tipoBasura) {
            case 1:
                return "pizza.png"; // Asegúrate de que la ruta sea correcta
            case 2:
                return "pollo.png";
            case 3:
                return "milkshake.png";
            case 4:
                return "taco.png";
            case 5:
                return "burger.png";
            default:
                return "pizza.png"; // Valor por defecto
        }
    }
}

