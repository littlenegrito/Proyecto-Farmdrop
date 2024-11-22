/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

	public class GameLluviaMenu extends Game {

		private SpriteBatch batch;
                private BitmapFont font;
                private TextureAtlas atlas; // Carga el atlas aquí
                private int higherScore;

		public void create() {
			batch = new SpriteBatch();
                        font = new BitmapFont(); // Usamos la fuente predeterminada de LibGDX
                        atlas = new TextureAtlas(Gdx.files.internal("output/imagenesAtlas.atlas")); // acceder a las texturas generadas
                        Gdx.graphics.setWindowedMode(1920, 1080); // Tamaño base

                        this.setScreen(new MainMenuScreen(this));
		}

		public void render() {
			super.render(); // important!
		}

		public void dispose() {
			batch.dispose();
			font.dispose();
                        atlas.dispose();
		}

		public SpriteBatch getBatch() {
			return batch;
		}

		public BitmapFont getFont() {
			return font;
		}
                
                public TextureAtlas getAtlas() { 
                    return atlas;
                }

		public int getHigherScore() {
			return higherScore;
		}

		public void setHigherScore(int higherScore) {
			this.higherScore = higherScore;
		}
		

	}