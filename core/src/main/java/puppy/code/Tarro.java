/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Tarro {
	   private Rectangle bucket;
	   private TextureRegion bucketImage;
	   private Sound sonidoHerido;
	   private int vidas = 100;
	   private int puntos = 0;
	   private int velx = 400;
	   private boolean herido = false;
	   private int tiempoHeridoMax=50;
	   private int tiempoHerido;
	   private Habilidad habilidadActual;
	   private float factorPuntaje = 1.0f;
           
           float scale = 0.15f;  // Cambia este valor para ajustar el tamaño
           
	   public Tarro(TextureRegion tex, Sound ss) {
                this.bucketImage = tex;
                if (this.bucketImage == null) {
                    System.err.println("Error: La textura del tarro no se ha cargado correctamente.");
                }
                this.sonidoHerido = ss;
            }
		

		public Tarro() {
			this.habilidadActual = HabilidadDash.obtenerInstancia();
			this.bucket = new Rectangle(0, 20, 64, 64); // Inicializa el área del tarro
		}

		public void usarHabilidad() {
			if (habilidadActual != null) {
				habilidadActual.usarHabilidad(this); // Usar la habilidad actual
			} else {
				System.out.println("No hay habilidad asignada.");
			}
		}
	   
		public int getVidas() {
			return vidas;
		}
		public void setFactorPuntaje(float factor) {
			this.factorPuntaje = factor; // Establece el nuevo factor de puntaje
		}
		public void resetFactorPuntaje() {
			this.factorPuntaje = 1.0f; // Restaura el factor de puntaje original
		}
		public int obtenerPuntosConFactor() {
			return (int) (puntos * factorPuntaje); // Retorna los puntos considerando el factor
		}
		public int getPuntos() {
			return puntos;
		}
		public Rectangle getArea() {
			return bucket;
		}
		public void sumarPuntos(int pp) {
			puntos+=pp;
		}
        public void sumarVida(int vida) {
			this.vidas+=vida;
		}
		public void establecerHabilidad(Habilidad habilidad) {
			this.habilidadActual = habilidad; // Cambiar la habilidad actual
		}
		
    	

    	public float getVelx() {
        	return velx;
    	}
		
	
	   public void crear() {
		     bucket = new Rectangle();
                    // Ajustar el ancho y la altura de acuerdo a la escala
                    bucket.width = bucketImage.getRegionWidth() * scale;
                    bucket.height = bucketImage.getRegionHeight() * scale;
                    bucket.x = 1600 / 2 - bucket.width / 2; // Centrar el tarro
                    bucket.y = 20; // Altura del tarro
		   
	   }
	   public void dañar(int daño) {
		  vidas-= daño;
		  herido = true;
		  tiempoHerido=tiempoHeridoMax;
		  sonidoHerido.play();
	   }
	   public void dibujar(SpriteBatch batch) {
                if (!herido) {
                    batch.draw(bucketImage, bucket.x, bucket.y, bucket.width, bucket.height);
                } else {
                    batch.draw(bucketImage, bucket.x, bucket.y + MathUtils.random(-5, 5), bucket.width, bucket.height);
                float scale = 0.2f;  // Cambia este valor para ajustar el tamaño
                float tarroWidth = bucketImage.getRegionWidth() * scale;
                float tarroHeight = bucketImage.getRegionHeight() * scale;

                if (!herido) {
                    batch.draw(bucketImage, bucket.x, bucket.y, tarroWidth, tarroHeight);
                } else {
                    batch.draw(bucketImage, bucket.x, bucket.y + MathUtils.random(-5, 5), tarroWidth, tarroHeight);
                    tiempoHerido--;
                    if (tiempoHerido <= 0) herido = false;
                }
            }
           }
	   public void actualizarMovimiento() { 
		   // movimiento desde mouse/touch
		   /*if(Gdx.input.isTouched()) {
			      Vector3 touchPos = new Vector3();
			      touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			      camera.unproject(touchPos);
			      bucket.x = touchPos.x - 64 / 2;
			}*/
		   //movimiento desde teclado
		   if(Gdx.input.isKeyPressed(Input.Keys.LEFT)) bucket.x -= velx * Gdx.graphics.getDeltaTime();
		   if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)) bucket.x += velx * Gdx.graphics.getDeltaTime();
		   // que no se salga de los bordes izq y der
		   if(bucket.x < 0) bucket.x = 0;
		   if(bucket.x > 1600 - bucket.width) bucket.x = 1600 - bucket.width;
	   }
	    

	public void destruir() {
		  
	   }
	
   public boolean estaHerido() {
	   return herido;
   }
	   
}
