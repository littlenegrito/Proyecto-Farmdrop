package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia {
	private Array<Rectangle> rainDropsPos;
	private Array<Integer> rainDropsType;
    private long lastDropTime;
    private Texture gotaBuena;
    private Texture gotaMala;
    private Sound dropSound;
    private Music rainMusic;
	   
	public Lluvia(Texture gotaBuena, Texture gotaMala, Sound ss, Music mm) {
		rainMusic = mm;
		dropSound = ss;
		this.gotaBuena = gotaBuena;
		this.gotaMala = gotaMala;
	}
	
	public void crear() {
		rainDropsPos = new Array<Rectangle>();
		rainDropsType = new Array<Integer>();
		crearGotaDeLluvia();
	      // start the playback of the background music immediately
	      rainMusic.setLooping(true);
	      rainMusic.play();
	}
	
	private void crearGotaDeLluvia() {
	      Rectangle raindrop = new Rectangle();
	      raindrop.x = MathUtils.random(0, 800-64);
	      raindrop.y = 480;
	      raindrop.width = 64;
	      raindrop.height = 64;
	      rainDropsPos.add(raindrop);
	      // ver el tipo de gota
	      if (MathUtils.random(1,10)<5)	    	  
	         rainDropsType.add(1);
	      else 
	    	 rainDropsType.add(2);
	      lastDropTime = TimeUtils.nanoTime();
	   }
	
   public boolean actualizarMovimiento(Tarro tarro) {
        // Generar nuevos elementos si ha pasado suficiente tiempo
        if (TimeUtils.nanoTime() - lastDropTime > 1000000000) crearElemento();

        // Actualizar la posición de los elementos
        for (int i = 0; i < elementosPos.size; i++) {
            Rectangle elementoPos = elementosPos.get(i);
            Accionable elemento = elementos.get(i);

            elementoPos.y -= 300 * Gdx.graphics.getDeltaTime();
            elemento.moverElemento(Gdx.graphics.getDeltaTime());

            // Eliminar el elemento si cae al suelo
            if (elementoPos.y + 64 < 0) {
                elementosPos.removeIndex(i);
                elementos.removeIndex(i);
                continue;
            }

            // Si el elemento colisiona con el tarro
            if (elementoPos.overlaps(tarro.getArea())) {
                elemento.activarEfecto(tarro); // Aplicar el efecto en el tarro

                // Sonido de recolección si es un alimento
                if (elemento instanceof Alimentos) {
                    dropSound.play();
                }

                // Remover el elemento tras la colisión
                elementosPos.removeIndex(i);
                elementos.removeIndex(i);
            }
        }

        // Verificar si el jugador ha perdido
        return tarro.getVidas() > 0;
    }
   
   public void actualizarDibujoLluvia(SpriteBatch batch) { 
	   
	  for (int i=0; i < rainDropsPos.size; i++ ) {
		  Rectangle raindrop = rainDropsPos.get(i);
		  if(rainDropsType.get(i)==1) // gota dañina
	         batch.draw(gotaMala, raindrop.x, raindrop.y); 
		  else
			 batch.draw(gotaBuena, raindrop.x, raindrop.y); 
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
   
}
