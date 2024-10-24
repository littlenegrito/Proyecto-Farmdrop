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
    private Tarro jugador;

     public Entorno(Tarro jugador, Sound dropSound, Music rainMusic) {
        this.jugador = jugador;
        this.dropSound = dropSound;
        this.rainMusic = rainMusic;
        elementos = new Array<>();
        elementosPos = new Array<>();
    }
    public void crear() {
        elementos.clear();
        elementosPos.clear();
        crearElemento();
    }
    private int determinarCantidadElementos() {
        return MathUtils.random(1, 10); // Crear entre 1 y 30 elementos aleatoriamente
    }
   private void crearElemento() {
        Rectangle elementoPos = new Rectangle();
        Accionable nuevoElemento;
        int cantidad = determinarCantidadElementos();
        /*
         // Listas de texturas para diferentes tipos de elementos
        String[] frutas = {"naranja.png", "manzana.png", "banana.png", "cereza.png", "frutilla.png"};
        String[] vegetales = {"zanahoria.png", "brocoli.png", "tomate.png", "calabaza.png", "maiz.png"};
        String[] basuras = {"basura.png", "basura2.png", "basura3.png", "basura4.png", "basura5.png"};
        String[] chatarra = {"pizza.png", "sushi.png", "hamburguesa.png", "milkshake.png", "taco.png"};
        */
        // Generar aleatoriamente un elemento bueno o malo
        for (int i = 0; i < cantidad; i++) { // Bucle para crear la cantidad determinada de elementos
            int tipoElemento = MathUtils.random(1, 10); // Determina el tipo de elemento basado en probabilidades

            if (tipoElemento <= 4) { // 40% de probabilidad para fruta
                String texturaFruta = seleccionarTexturaFruta(); // Llama a la función para obtener la textura de la fruta
                nuevoElemento = new Fruta(new Texture(Gdx.files.internal(texturaFruta)), 5, 5, 10, "Fruta");
            } else if (tipoElemento <= 7) { // 20% de probabilidad para vegetales
                String texturaChatarra = seleccionarTexturaChatarra(); // Llama a la función para obtener la textura de la chatarra
                nuevoElemento = new Chatarra(new Texture(Gdx.files.internal(texturaChatarra)), 5, 3, -5, "Chatarra");
            } else if (tipoElemento <= 9) { // 30% de probabilidad para chatarra
                String texturaVegetal = seleccionarTexturaVegetal(); // Llama a la función para obtener la textura del vegetal
                nuevoElemento = new Vegetal(new Texture(Gdx.files.internal(texturaVegetal)), 5, 3, 10, "Vegetal");
            } else { // |0% de probabilidad para basura
                String texturaBasura = seleccionarTexturaBasura(); // Llama a la función para obtener la textura de la basura
                nuevoElemento = new Basura(new Texture(Gdx.files.internal(texturaBasura)), 5, 3, -10, "Basura");
            }

            // Establecer la posición del elemento
            elementoPos.x = MathUtils.random(0, 800 - 64);
            elementoPos.y = 480; // Se genera desde la parte superior de la pantalla
            elementoPos.width = 64;
            elementoPos.height = 64;

            elementos.add(nuevoElemento);
            elementosPos.add(elementoPos);
        }
        lastDropTime = TimeUtils.nanoTime();
    }
    public boolean actualizarMovimiento(Tarro tarro){
        // Generar nuevos elementos si ha pasado suficiente tiempo
       if (TimeUtils.nanoTime() - lastDropTime > 1000000000) crearElemento();
       // Actualizar la posición de los elementos
       for (int i = 0; i < elementosPos.size; i++) {
           Rectangle elementoPos = elementosPos.get(i);
           Accionable elemento = elementos.get(i);

           elemento.moverElemento(Gdx.graphics.getDeltaTime());

           // Verificar si el elemento ha sido recogido
           if (elemento.esRecogido(tarro.getArea())) {
               elemento.activarEfecto(tarro);
               if (elemento instanceof Alimentos) {
                   dropSound.play();
               }
               // Remover el elemento tras la colisión
               elementosPos.removeIndex(i);
               elementos.removeIndex(i);
           } else if (elementoPos.y + 64 < 0) { // Si colisiona con el suelo
               elemento.desaparecer(); // Llama al método desaparecer
               elementosPos.removeIndex(i);
               elementos.removeIndex(i);
           } else {
               elementoPos.y -= 300 * Gdx.graphics.getDeltaTime(); // Mantén la caída
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
                return "basura2.png";
            case 3:
                return "basura3.png";
            case 4:
                return "basura4.png";
            case 5:
                return "basura5.png";
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
                return "sushi.png";
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

