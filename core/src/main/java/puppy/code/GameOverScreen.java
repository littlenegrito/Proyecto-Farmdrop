package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameOverScreen implements Screen, InputProcessor {
	private final GameLluviaMenu game;
	private SpriteBatch batch;	   
	private BitmapFont font;
	private OrthographicCamera camera;
        private Texture exitButtonTexture;
        private Texture gameOverScreenTexture; 
        
        private float exitButtonX, exitButtonY, exitButtonWidth, exitButtonHeight;
         
	public GameOverScreen(final GameLluviaMenu game) {
		this.game = game;
                this.batch = game.getBatch();
                this.font = game.getFont();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 1920, 1080);
                
                gameOverScreenTexture = new Texture(Gdx.files.internal("game over screen.jpg"));
                exitButtonTexture = new Texture(Gdx.files.internal("quit button.png"));
                
                // Posicionar botones en el centro de la pantalla
                exitButtonWidth = 600;
                exitButtonHeight = 300;
                exitButtonX = (1920 - exitButtonWidth) + 120;
                exitButtonY = (exitButtonHeight) - 290;
                 Gdx.input.setInputProcessor(this); // Activar InputProcessor
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
                
                batch.draw(gameOverScreenTexture, 0, 0, 1920, 1080);
                batch.draw(exitButtonTexture, exitButtonX, exitButtonY, exitButtonWidth, exitButtonHeight);
		batch.end();

	}
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                float touchX = screenX;
                float touchY = 1080 - screenY; // Ajustar porque las coordenadas están invertidas

                if (touchX >= exitButtonX && touchX <= exitButtonX + exitButtonWidth &&
                        touchY >= exitButtonY && touchY <= exitButtonY + exitButtonHeight) {
                        Gdx.app.exit();  // Cerrar el juego
    
                }else{
                    Gdx.input.setInputProcessor(null);  // Desactiva el InputProcessor
                    game.setScreen(new GameScreen(game));
                    dispose();  // Liberar recursos del menú
                }
            return true;
       }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
            Gdx.input.setInputProcessor(null);  // Desactiva el InputProcessor cuando se oculta
            dispose();  // Libera recursos del menu	
	}

	@Override
	public void dispose() {
            gameOverScreenTexture.dispose();
            exitButtonTexture.dispose();
		
	}
        // Métodos no utilizados de InputProcessor
        @Override public boolean keyDown(int keycode) { return false; }
        @Override public boolean keyUp(int keycode) { return false; }
        @Override public boolean keyTyped(char character) { return false; }
        @Override public boolean touchDown(int screenX, int screenY, int pointer, int button) { return false; }
        @Override public boolean touchDragged(int screenX, int screenY, int pointer) { return false; }
        @Override public boolean mouseMoved(int screenX, int screenY) { return false; }
        @Override public boolean scrolled(float amountX, float amountY) { return false; }
        @Override public boolean touchCancelled(int screenX, int screenY, int pointer, int button) { return false; }

}
