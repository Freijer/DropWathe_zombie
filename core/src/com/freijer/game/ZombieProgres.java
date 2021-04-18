package com.freijer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;
import javax.swing.JButton;
import javax.xml.soap.Text;


//Жертвы: гражданин + 2-3 добавить
// пускать их не 2-3 в масиве, а 1
// после пеересечения - менять картинки на "кости" - т.е.е труп.
// с началом появления нгового врага его картинка рисуется заново



//после сьедения 20 людей зомби мутирует, больше жизней
// движение облаков и второго ряда темных деревьев звдний фон
// Циивилы исчезают за зомби (после сьедения)
// тень под зомби всегда есть
// после сьедения над зомби появляются мозги и поднимаются в воздух на  пару сантиметров и исчезают

//что бы убрать отрисовку фигукр - закоментить все shaprender

public class ZombieProgres extends ApplicationAdapter {
	private SpriteBatch batch;
	private ShapeRenderer shapeRenderer;

	private Texture background;
	private Texture forestBack;
	private Texture forestFront;
		private Texture [] zombie_little;
		private int zombiewStateFlag = 0;

		private float jumpHigh; //высота прыжка
		private float jumpSpeed = 0; //скорость
		private int gameStatFlag = 0; // начальное состояние игы зомби
	  	private int gameScore = 0; // очки игры
		private int passIndIndex_man = 0;



	private Texture man_with_trident;
		private int speed_man_with_trident = 4;
			private int numbers_man_with_trident = 5;
			private float man_with_trident_X[] = new float[numbers_man_with_trident]; //координата X
			private float distanceBetween_man_with_trident;

	Circle zombieCircle;
	private Rectangle [] rectangle_man_with_trident;


	@Override
	public void create() {
		batch = new SpriteBatch(); //картинки
			shapeRenderer = new ShapeRenderer();//фигуры

		zombieCircle = new Circle();
			rectangle_man_with_trident = new Rectangle[numbers_man_with_trident];

		jumpHigh = Gdx.graphics.getHeight()/7;

		background = new Texture("background.png");
			forestBack = new Texture("ForestBack.png");
			forestFront = new Texture("ForestFront.png");
		zombie_little = new Texture[2];
		zombie_little[0] = new Texture("zombieUp.png");
		zombie_little[1] = new Texture("zombieDown.png");
			man_with_trident = new Texture("man_with_trident.png");
				distanceBetween_man_with_trident = Gdx.graphics.getWidth() -100; //расстояние между парой - пол экрана или Gdx.graphics.getWidth() + 500
					for (int i = 0; i < numbers_man_with_trident; i++){//заполнение массива  врагом_1 с расстоянием между врагами в цикле между появлением каждого
						man_with_trident_X[i] = Gdx.graphics.getWidth() + 500 + i *  distanceBetween_man_with_trident;
						rectangle_man_with_trident[i] = new Rectangle(); //оборачиваем каждого человека с вилами в прямоугольник для границ
					}


	}


	@Override
	public void render() {
		batch.begin();
			batch.draw(forestBack, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			batch.draw(forestFront, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				batch.draw(background, 0, -100, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		//-прыжки
		if (Gdx.input.justTouched()){
			gameStatFlag = 1;
		}
		if (gameStatFlag ==1) {
			Gdx.app.log("GameScore", String.valueOf(gameScore));
			//--подсчсчет очков за мужиков с вилами, которые прошли за экран, с которыми не столкнулся
			if (man_with_trident_X[passIndIndex_man] < Gdx.graphics.getWidth()){ //Если мужик с вилой прошел весь экран, очки увеличиваем на 1
				gameScore++;
				if (passIndIndex_man < numbers_man_with_trident - 1){ //труб у нас пять, проверяем, если их осталось на 1 меньше, переходим к следующей
					passIndIndex_man++;
				} else {
					passIndIndex_man = 0;
				} //если мы прошли все трубы, возвращаемся к 0 к самой первой трубе
			}
			//----
				if (Gdx.input.justTouched()) {
					jumpSpeed = -30;//скорость падения при прыжке
				}
				if(jumpHigh > Gdx.graphics.getHeight() / 7 || jumpSpeed < 0){
					jumpSpeed++; //---падение скорость
					jumpHigh -= jumpSpeed; //---падение скорость
					if (jumpHigh > (zombie_little[zombiewStateFlag].getHeight())*7) {
						jumpSpeed = 40;
					}
				}
			} else {
				if (Gdx.input.justTouched()){
					gameStatFlag = 1;
				}
			}
			//
			for (int i = 0; i < numbers_man_with_trident; i++){
				if(man_with_trident_X[i] < - man_with_trident.getWidth() ){
					man_with_trident_X[i] = numbers_man_with_trident * distanceBetween_man_with_trident;
				} //когда заканчивается 5 человеков, они перезапускаются
					else {
					man_with_trident_X[i] -= speed_man_with_trident; // движение первого врага
				}// если не зашел за край человек, - отнимаем координаты и двигаеми за левый край
						batch.draw(man_with_trident, man_with_trident_X[i], Gdx.graphics.getHeight() / 7); //отрисовка врага_1
							rectangle_man_with_trident[i] = new Rectangle(Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/7, man_with_trident.getWidth(), man_with_trident.getHeight());
	}
//-- анимация зомби - проверяем. Если флаг = 0, то значение присваеваем 1 и наборот, тем самым при вызове отрисовки zombie_little gjcnjzyyj ,eltn vtyznmcz rfhnbyrf
		if (zombiewStateFlag == 0){
			zombiewStateFlag =1;
		} else {
			zombiewStateFlag = 0;
		}
//--
		batch.draw(zombie_little[zombiewStateFlag],  Gdx.graphics.getWidth()/6, jumpHigh);
			batch.end();
				zombieCircle.set(Gdx.graphics.getWidth()/6+55, jumpHigh+zombie_little[zombiewStateFlag].getHeight()/2, zombie_little[zombiewStateFlag].getWidth()/2+10); //размер активной зоны поражения и поендания зомбаком
				shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
				shapeRenderer.setColor(Color.CYAN);
				shapeRenderer.circle(zombieCircle.x, zombieCircle.y, zombieCircle.radius);
					for (int i = 0; i < numbers_man_with_trident; i++) {
						shapeRenderer.rect( man_with_trident_X[i], Gdx.graphics.getHeight() / 7, man_with_trident.getWidth(), man_with_trident.getHeight());
						if (Intersector.overlaps(zombieCircle, rectangle_man_with_trident[i])){ //для пересечения со вторым врагом указываем через ||
							Gdx.app.log("intersect_1", "Попался!");
						}
					} //отрисовка прямоугшьников на врраге 1
				shapeRenderer.end();
	}
}
