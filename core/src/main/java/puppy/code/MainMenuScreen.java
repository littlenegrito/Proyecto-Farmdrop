package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenuScreen implements Screen, InputProcessor {

	final GameLluviaMenu game;
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
        private Texture mainScreenTexture;
        private Texture playButtonTexture;
        private Texture tutorialButtonTexture;
        private Texture tutorialScreenTexture1; 
        private Texture tutorialScreenTexture2; 
        private boolean showTutorial; // Bandera para mostrar la imagen del tutorial
        private int tutorialStep; // Controla qué pantalla del tutorial se muestra

        
        // Coordenadas y dimensiones de los botones
        private float playButtonX, playButtonY, playButtonWidth, playButtonHeight;
        private float tutorialButtonX, tutorialButtonY, tutorialButtonWidth, tutorialButtonHeight;

	public MainMenuScreen(final GameLluviaMenu game) {
            this.game = game;
            this.batch = game.getBatch();
            this.font = game.getFont();
            camera = new OrthographicCamera();
            camera.setToOrtho(false, 1920, 1080); // Resolución de la imagen
            // Cargar la textura de la pantalla principal
             mainScreenTexture = new Texture(Gdx.files.internal("main screen.jpg"));
             playButtonTexture = new Texture(Gdx.files.internal("play button.png"));
             tutorialButtonTexture = new Texture(Gdx.files.internal("tutorial button.png"));
             tutorialScreenTexture1 = new Texture(Gdx.files.internal("tutorial screen 1.jpg")); 
             tutorialScreenTexture2 = new Texture(Gdx.files.internal("tutorial screen 2.jpg")); 

             
            // Configuración inicial
            showTutorial = false;
            tutorialStep = 0;
             
            // Posicionar botones en el centro de la pantalla
            playButtonWidth = 700;
            playButtonHeight = 400;
            playButtonX = (1920 - playButtonWidth) / 2 + 345;
            playButtonY = (1080 / 2) - 150;

            tutorialButtonWidth = playButtonWidth; // Igual tamaño que el botón de Play
            tutorialButtonHeight = playButtonHeight;
            tutorialButtonX = playButtonX; // Alineado horizontalmente con el botón de Play
            tutorialButtonY = playButtonY - (playButtonHeight - 105); // Distancia vertical relativo al otro boton
            
            Gdx.input.setInputProcessor(this); // Establecer el InputProcessor
	}

	@Override
	public void render(float delta) {
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		font.getData().setScale(2, 2);
                
                if (!showTutorial) {
                    // Dibujar el menú principal
                    batch.draw(mainScreenTexture, 0, 0, 1920, 1080);
                    batch.draw(playButtonTexture, playButtonX, playButtonY, playButtonWidth, playButtonHeight);
                    batch.draw(tutorialButtonTexture, tutorialButtonX, tutorialButtonY, tutorialButtonWidth, tutorialButtonHeight);
                } else {
                    // Mostrar la pantalla correspondiente del tutorial
                    if (tutorialStep == 1) {
                        batch.draw(tutorialScreenTexture1, 0, 0, 1920, 1080);
                    } else if (tutorialStep == 2) {
                        batch.draw(tutorialScreenTexture2, 0, 0, 1920, 1080);
                    }
                }

                batch.end();
        }

        @Override // detectar toques
        public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                float touchX = screenX;
                float touchY = 1080 - screenY; // Ajustar porque las coordenadas están invertidas

                if (!showTutorial) {
                    if (touchX >= playButtonX && touchX <= playButtonX + playButtonWidth &&
                        touchY >= playButtonY && touchY <= playButtonY + playButtonHeight) {
                        Gdx.input.setInputProcessor(null);  // Desactiva el InputProcessor
                        game.setScreen(new GameScreen(game));  // Cambia de pantalla
                        dispose();  // Libera recursos del menú
                    }
                    if (touchX >= tutorialButtonX && touchX <= tutorialButtonX + tutorialButtonWidth &&
                        touchY >= tutorialButtonY && touchY <= tutorialButtonY + tutorialButtonHeight) {
                        showTutorial = true;
                        tutorialStep = 1;
                    }
                } else {
                    if (tutorialStep == 1) {
                        tutorialStep = 2;
                    } else if (tutorialStep == 2) {
                        showTutorial = false;
                        tutorialStep = 0;
                    }
                }

            return true;
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

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
            camera.setToOrtho(false, 1920, 1080);
		
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
            dispose();  // Libera recursos del menú
		
	}
	@Override
        public void dispose() {
            // Liberar recursos
            if (mainScreenTexture != null) mainScreenTexture.dispose();
            if (playButtonTexture != null) playButtonTexture.dispose();
            if (tutorialButtonTexture != null) tutorialButtonTexture.dispose();
            if (tutorialScreenTexture1 != null) tutorialScreenTexture1.dispose();
            if (tutorialScreenTexture1 != null) tutorialScreenTexture2.dispose();
        }

}
