package com.freijer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;

import java.util.Iterator;
import java.util.Random;
import javax.swing.JButton;
import javax.xml.soap.Text;


public class DropWather implements Screen {


	private OrthographicCamera camera;
	private SpriteBatch spriteBatch;
	private Vector3 touchPos;

	private int zombieStateFlag = 0;
		private Texture[] zombie;
		private Rectangle zombie_rectangle;
			private Texture citzen;
			private Array<Rectangle> citzen_rectangle;

	private int game_score = 0; // очки игры
	private int brain_score = 0; //количество сьеденных жертв
	private int liveS_score = 1; // количество жизней. Уменьшается при соприкосновении с врагом и  и можно получить время от времени

	private int random_sec_1;
	private int random_sec_man_trident;


	private BitmapFont scoreFont;
	private BitmapFont liveFont;
	private BitmapFont brainFont;
		private Texture starScore;
		private Texture liveScore;
		private Texture brainScore;

	private Texture forestBack;
	private Texture sky;
	private float jumpHeigh;
	private float jumpSpeed = 0;
	private int gameStatFlag = 0;
	private Texture gameOver;

	private int passedCitzens = 0;
	private int zaq;
	private int eatFlag = 0;
	private int HP = 1;
	//---
	private Texture castle;
	private int castleNumber = 3;
	private float castleSpeed = 0.5f;
	private float castleX[] = new float[castleNumber];
	private float distancecastle;
	//---
	private Texture ground;
	private int groundNumber = 3;
	private float groundSpeed = 0.5f;
	private float groundX[] = new float[groundNumber];
	private float distanceground;
	//--
	private Texture ground2;
	private int groundNumber2 = 3;
	private float groundSpeed2 = 0.5f;
	private float groundX2[] = new float[groundNumber];
	private float distanceground2;
	//--
	private Texture tree;
	private int treeNumber = 3;
	private float treeSpeed = 0.5f;
	private float treeX[] = new float[treeNumber];
	private float distanceTree;
	//---
	private Texture godzilla;
	private int godzillaNumber = 3;
	private float godzillaSpeed = 0.5f;
	private float godzillaX[] = new float[godzillaNumber];
	private float distanceGodzilla;
	//--
	private Texture corax;
	private int coraxNumber = 3;
	private int coraxSpeed = 6;
	private float coraxX[] = new float[coraxNumber];
	private float coraxShift[] = new float[coraxNumber];
	private float distanceCorax;
	//--
	private Texture BackTrees;

	//--
	private Texture clouds;
	private int cloudsNumber = 4;
	private float cloudsSpeed = 0.5f;
	private float cloudsX[] = new float[cloudsNumber];
	private float distanceclouds;

	//---

	private Texture forestOver;
	private Random random;


	//--враги

	private Texture man_with_trident;
	private Array<Rectangle> man_with_trident_rectangle;

		private Texture boom;
	private Array<Rectangle> boom_rectangle;




	public DropWather() {
		touchPos = new Vector3();
			camera = new OrthographicCamera();
			camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			spriteBatch = new SpriteBatch();


		zombie = new Texture[4];
		zombie[0] = new Texture("zombieDown.png");
		zombie[1] = new Texture("zombieUp.png");
		zombie[2] = new Texture("BigZombieB.png");
		zombie[3] = new Texture("BigZombieM.png");

			boom = new Texture("boom_bang.png");
			man_with_trident = new Texture("man_with_trident.png");

			citzen = new Texture("citzen.png");


		forestBack = new Texture("ForestBack.png");
		castle = new Texture("castle.png");
		ground = new Texture("ground.png");
		ground2 = new Texture("ground.png");
		tree = new Texture("tree.png");
		sky = new Texture("skyNoCloud.png");
		clouds = new Texture("Clouds.png");
		godzilla = new Texture("godzilla.png");
		corax = new Texture("corax.png");
		BackTrees = new Texture("BackTrees.png");
		forestOver = new Texture("forestOver.png");
		gameOver = new Texture("gamover.png");

		starScore = new Texture("starScore.png");
		liveScore = new Texture("heartScore.png");
		brainScore = new Texture("brainScore.png");


		zombie_rectangle = new Rectangle();
			zombie_rectangle.x = Gdx.graphics.getWidth() / 5; //положение
			zombie_rectangle.y = Gdx.graphics.getHeight() / 9; //положение
				zombie_rectangle.width = zombie[zombieStateFlag].getWidth(); //размер прямоугольника
				zombie_rectangle.height = zombie[zombieStateFlag].getHeight(); //размер прямоугольника
					citzen_rectangle  = new Array<>();


					man_with_trident_rectangle = new Array<>();
					boom_rectangle = new Array<>();


			random_sec_1 = 1 + (int) (Math.random() * 4); // Генерация  числа

			Timer.schedule(new Timer.Task(){
							   @Override
							   public void run() {
								   spawnRaindrop();
							   }
						   }
					, 1        //    (delay)
					, 7 + random_sec_1    //    (seconds)
					, 5       //    (repeat)
			);

		random_sec_man_trident = 1 + (int) (Math.random() * 3); // Генерация  числа

			Timer.schedule(new Timer.Task(){
							   @Override
							   public void run() {
								   spawnMan_Trident();
							   }
						   }
					, 1        //    (delay)
					, 7 + random_sec_man_trident    //    (seconds)
					, 5       //    (repeat)
			);

//		Timer.schedule(new Timer.Task(){
//						   @Override
//						   public void run() {
//							   spawn_Boom();
//						   }
//					   }
//				, 1        //    (delay)
//				, 1    //    (seconds)
//		);



		scoreFont = new BitmapFont(); //отрисовка на экране очков
		scoreFont.setColor(Color.BLACK);
		scoreFont.getData().setScale(4);
			liveFont = new BitmapFont(); //отрисовка на экране жизней
			liveFont.setColor(Color.BLACK);
			liveFont.getData().setScale(4);
				brainFont = new BitmapFont(); //отрисовка на экране сьеденных мозгов
				brainFont.setColor(Color.BLACK);
				brainFont.getData().setScale(4);




		distanceground = Gdx.graphics.getWidth() + 100;
		distanceground2 = Gdx.graphics.getWidth() + 100;

		distanceCorax = Gdx.graphics.getWidth() + 1400;

		distancecastle = Gdx.graphics.getWidth() + 800;
		distanceTree = Gdx.graphics.getWidth() + 1200;
		distanceGodzilla = Gdx.graphics.getWidth() + 1800;

		distanceclouds = 320;

		random = new Random();

		InitGame();
	}

	private void spawnRaindrop(){
		Rectangle citzenDrop = new Rectangle();
			citzenDrop.x =  Gdx.graphics.getWidth() + 200;
			citzenDrop.y = Gdx.graphics.getHeight() / 9;
				citzenDrop.width = citzen.getWidth();
				citzenDrop.height = citzen.getHeight();
					citzen_rectangle.add(citzenDrop);
		citzenDrop.set(Gdx.graphics.getWidth() + 200, Gdx.graphics.getHeight() / 9, citzen.getWidth(), citzen.getHeight());
	} //создание случайных жертв

	private void spawnMan_Trident(){
		Rectangle man_Trident_Drop = new Rectangle();
		man_Trident_Drop.x =  Gdx.graphics.getWidth() + 350;
			man_Trident_Drop.y = Gdx.graphics.getHeight() / 9;
			man_Trident_Drop.width = man_with_trident.getWidth();
				man_Trident_Drop.height = man_with_trident.getHeight();
					man_with_trident_rectangle.add(man_Trident_Drop);
		man_Trident_Drop.set(Gdx.graphics.getWidth() + 150, Gdx.graphics.getHeight() / 9, man_with_trident.getWidth(), man_with_trident.getHeight());
	} //создание случайных врагов с трезубцем

	private void spawn_Boom(){
		Rectangle boom_Drop = new Rectangle();
//			boom_Drop.x =  Gdx.graphics.getWidth()/2;
//			boom_Drop.y = Gdx.graphics.getHeight()/2;
//		boom_Drop.width = boom.getWidth();
//		boom_Drop.height = boom.getHeight();
			boom_Drop.x = zombie[zombieStateFlag].getWidth();
			boom_Drop.y = zombie[zombieStateFlag].getHeight()+50;
				boom_Drop.width = 300;
				boom_Drop.height = 300;
			boom_rectangle.add(boom_Drop);
		boom_Drop.set(boom_Drop.x, boom_Drop.y, boom_Drop.width, boom_Drop.height);
	} //создание буум





	public void InitGame()
	{
		jumpHeigh = Gdx.graphics.getHeight() / 9 - zombie[zombieStateFlag].getHeight() / 9;

		for (int k = 0; k < castleNumber; k++) {
			castleX[k] = 800 + Gdx.graphics.getWidth() + k * distancecastle; //растояние между первым и вторям отр
		}
		for (int t = 0; t < groundNumber; t++) {
			groundX[t] = 1; //растояние между первым и вторям отр
		}
		for (int t2 = 0; t2 < groundNumber2; t2++) {
			groundX2[t2] = 1200; //растояние между первым и вторям отр
		}
		for (int tr = 0; tr < treeNumber; tr++) {
			treeX[tr] = 950; //растояние между первым и вторям отр
		}
		for (int g = 0; g < godzillaNumber; g++) {
			godzillaX[g] = 100 + Gdx.graphics.getWidth() + g * distanceGodzilla; //растояние между первым и вторям отр
		}
		for (int cr = 0; cr < coraxNumber; cr++) {
			coraxX[cr] = 1200 + Gdx.graphics.getWidth() + cr * distanceCorax; //растояние между первым и вторям отр
			coraxShift[cr] = (random.nextFloat() - 0.5f) * 900 - 50;
		}
		for (int cl = 0; cl < cloudsNumber; cl++) {
			cloudsX[cl] = 100; //растояние между первым и вторям отр
		}
	}



	@Override
	public void render (float delta) {


		gameStatFlag = 1;
		Gdx.gl.glClearColor(1, 1, 1, 1);// Очищаем экран
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		spriteBatch.setProjectionMatrix(camera.combined);


		spriteBatch.begin();
		if (gameStatFlag == 1) {

			spriteBatch.draw(sky, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

			for (int cl = 0; cl < cloudsNumber; cl++) { // годира
				if (cloudsX[cl] < -clouds.getWidth()) {
					cloudsX[cl] = cloudsNumber * distanceclouds;
				} else {
					cloudsX[cl] -= cloudsSpeed; }
				spriteBatch.draw(clouds, cloudsX[cl] + 50, 50, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			}

			for (int g = 0; g < godzillaNumber; g++) { // годира
				if (godzillaX[g] < -godzilla.getWidth())
				{
					godzillaX[g] = godzillaNumber * distanceGodzilla;
				} else
				{
					godzillaX[g] -= godzillaSpeed;
				}
				spriteBatch.draw(godzilla, godzillaX[g], 900);
			}

			spriteBatch.draw(forestBack, 0, -370, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			spriteBatch.draw(BackTrees, 0, -40, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//--------фон
			for (int k = 0; k < castleNumber; k++) {
				if (castleX[k] < -castle.getWidth())
				{
					castleX[k] = castleNumber * distancecastle;
				} else
				{
					castleX[k] -= castleSpeed;
				}
				spriteBatch.draw(castle, castleX[k], Gdx.graphics.getHeight() / 10);
			}
			for (int t = 0; t < groundNumber; t++) {
				if (groundX[t] < -ground.getWidth()) {
					groundX[t] = groundNumber + distanceground;
				} else {
					groundX[t] -= groundSpeed;
				}
				spriteBatch.draw(ground, groundX[t], 0);
			}
			for (int t2 = 0; t2 < groundNumber2; t2++) {
				if (groundX2[t2] < -ground2.getWidth()) {
					groundX2[t2] = groundNumber2 + distanceground2;
				} else {
					groundX2[t2] -= groundSpeed2;
				}
				spriteBatch.draw(ground2, groundX2[t2], 5);
			}


			for (int tr = 0; tr < treeNumber; tr++) {
				if (treeX[tr] < -tree.getWidth())
				{
					treeX[tr] = groundNumber2 + distanceground2;
				} else
				{
					treeX[tr] -= treeSpeed;
				}
				spriteBatch.draw(tree, treeX[tr], Gdx.graphics.getHeight() / 12);
			}

			for (int cr = 0; cr < coraxNumber; cr++) {
				if (coraxX[cr] < -corax.getWidth())
				{
					coraxX[cr] = coraxNumber * distanceCorax;
				} else
				{
					coraxX[cr] -= coraxSpeed;
				}
				spriteBatch.draw(corax, 900 - coraxX[cr], 900 + coraxShift[cr]);
			}





			spriteBatch.draw(zombie[zombieStateFlag], Gdx.graphics.getWidth() / 5, zombie_rectangle.y);

			for (Rectangle citzendrop: citzen_rectangle){
				spriteBatch.draw(citzen, citzendrop.x, citzendrop.y);
			}

			for (Rectangle man_tridentdrop: man_with_trident_rectangle){
				spriteBatch.draw(man_with_trident, man_tridentdrop.x, man_tridentdrop.y);
			}

						for (Rectangle boomDrop: boom_rectangle){
		spriteBatch.draw(boom, boomDrop.x + zombie[zombieStateFlag].getWidth()/2, boomDrop.y - zombie[zombieStateFlag].getHeight()/2, 400, 400);
						}

			if (Gdx.input.justTouched()) {
				jumpSpeed = -30;
			}
			if (jumpHeigh > Gdx.graphics.getHeight() / 9 || jumpSpeed < 0) {
				jumpSpeed++;
				jumpHeigh -= jumpSpeed;
				zombie_rectangle.y = jumpHeigh;
				if (jumpHeigh > 1200)
				{
					jumpSpeed = 30;
				}
			}
		} else if (gameStatFlag == 0) {
			if (Gdx.input.justTouched()) {
				Gdx.app.log("tap", "Прыжок");
				gameStatFlag = 1;
			}
		} else if (gameStatFlag == 2) {
			spriteBatch.draw(forestOver, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				spriteBatch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			if (Gdx.input.justTouched())
			{
				Gdx.app.log("tap", "Гамовер");
				gameStatFlag = 1;
				eatFlag = 0;
				InitGame();
				game_score = 0;
				brain_score = 0;
				jumpSpeed = 0;
			}
		}
		spriteBatch.draw(starScore, starScore.getWidth()/4, liveScore.getHeight()/3-starScore.getHeight()/5); // отрисовка звезды-очков
			spriteBatch.draw(liveScore, starScore.getWidth()+40, liveScore.getHeight()/3-starScore.getHeight()/5); //отрисовка жизней-очков
			spriteBatch.draw(brainScore, starScore.getWidth()*2+40, liveScore.getHeight()/3-starScore.getHeight()/5); //отрисовка мозгов-отчков
		scoreFont.draw(spriteBatch, String.valueOf(game_score), 85, 120); //проритсовка на экране очков
			liveFont.draw(spriteBatch, String.valueOf(liveS_score), 225, 120); //проритсовка на экране жизней
				brainFont.draw(spriteBatch, String.valueOf(brain_score), 360, 120); //проритсовка на экране мозгов сьеденных жертв

		if(brain_score <4){
			if (zombieStateFlag == 0) {
				zombieStateFlag = 1;
			} else {
				zombieStateFlag = 0;
			}
		} else{
			if (zombieStateFlag == 2) {
				zombieStateFlag = 3;
			} else {
				zombieStateFlag = 2;
			}
		}


		Iterator<Rectangle> iterBoom = boom_rectangle.iterator();
		while (iterBoom.hasNext()) {
			Rectangle boomdrop = iterBoom.next();
			boomdrop.x -= 250 * Gdx.graphics.getDeltaTime(); //скорость падения
			if (boomdrop.x + 64 < 0) iterBoom.remove(); //если капля ниже нижнего края - удаляем её
		}

		Iterator<Rectangle> iter = citzen_rectangle.iterator();
		while (iter.hasNext()) {
			Rectangle raindrop = iter.next();
			raindrop.x -= 200 * Gdx.graphics.getDeltaTime(); //скорость падения
			if (raindrop.x + 64 < 0) iter.remove(); //если капля ниже нижнего края - удаляем её
			if (raindrop.overlaps(zombie_rectangle)) { //отслеживание наложения
				brain_score++;
				iter.remove();
			}
		}

		Iterator<Rectangle> iter_man_Trident = man_with_trident_rectangle.iterator();
		while (iter_man_Trident.hasNext()) {
			Rectangle manTriden_drop = iter_man_Trident.next();
			manTriden_drop.x -= 250 * Gdx.graphics.getDeltaTime(); //скорость падения
			if (manTriden_drop.x + 64 < 0) {
				game_score++;
				iter_man_Trident.remove(); //если капля ниже нижнего края - удаляем её
			}
			if (manTriden_drop.overlaps(zombie_rectangle)) { //отслеживание наложения
				spawn_Boom();
				liveS_score--;
				iter_man_Trident.remove();
			}
		}





		spriteBatch.end();
	}




	@Override
	public void show() {

	}
	@Override
	public void resize(int width, int height) {

	}
	@Override
	public void pause() {

	}
	@Override
	public void resume() {

	}
	@Override
	public void hide() {

	}
	@Override
	public void dispose() {

	}

}
