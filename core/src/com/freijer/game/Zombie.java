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

import java.awt.Rectangle;
import java.util.Random;

import javax.xml.soap.Text;


//после сьедения 20 людей зомби мутирует, больше жизней

// движение облаков и второго ряда темных деревьев звдний фон
// Циивилы исчезают за зомби (после сьедения)
// тень под зомби всегда есть
// после сьедения над зомби появляются мозги и поднимаются в воздух на  пару сантиметров и исчезают



public class Zombie extends ApplicationAdapter {
	SpriteBatch batch;
	int zombieStateFlag = 0;
	Texture background;
	Texture forestBack;
	Texture sky;

	Texture[] zombie;
	float jumpHeigh;
	float jumpSpeed = 0;
	int gameStatFlag = 0;
	Texture manFire;
	Texture manWeapon;
	int mansNumber = 5;
	float manFireX[] = new float[mansNumber];
	float manWeaponX[] = new float[mansNumber];
	float distanceBetweenMans;
	float mansShift[] = new float[mansNumber];
	Random random;
	Random randomSpeed;
	int manSpeed = 4;
	Circle zombieCircle;
	Circle[] fireAngle;
	Circle[] weapAngle;
	int gameScore = 0;
	int passedIndex = 0;
	BitmapFont scoreFont;
	Texture gameOver;

	Texture citzen;
	int citzenNumber = 4;
	int citzenSpeed = 5;
	float citzenX[] = new float[citzenNumber];
	float distanceCitzens;
	float citzenShift[] = new float[citzenNumber];
	Circle[] citzAngle;

	int eatScore = 0;
	int passedCitzens = 0;
	int zaq;
	int eatFlag = 0;
	int HP = 1;
//-------
	Texture castle;
	int castleNumber = 2;
	int castleSpeed = 1;
	float castleX[] = new float[castleNumber];
	float distancecastle;
	//---
	Texture ground;
	int groundNumber = 3;
	float groundSpeed = 0.5f;
	float groundX[] = new float[groundNumber];
	float distanceground;
//--
	Texture ground2;
	int groundNumber2 = 3;
	float groundSpeed2 = 0.5f;
	float groundX2[] = new float[groundNumber];
	float distanceground2;
//--
	Texture tree;
	int treeNumber = 3;
	int treeSpeed = 1;
	float treeX[] = new float[treeNumber];
	float distanceTree;
	//---
	Texture godzilla;
	int godzillaNumber = 3;
	int godzillaSpeed = 1;
	float godzillaX[] = new float[godzillaNumber];
	float distanceGodzilla;
//--
	Texture corax;
	int coraxNumber = 3;
	int coraxSpeed = 6;
	float coraxX[] = new float[coraxNumber];
	float coraxShift[] = new float[coraxNumber];
	float distanceCorax;



//------



	//ShapeRenderer shapeRenderer;



	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("country1.png");
		forestBack = new Texture("ForestBack.png");
		castle = new Texture("castle.png");
		ground = new Texture("ground.png");
		ground2 = new Texture("ground.png");
		tree = new Texture("tree.png");
		sky = new Texture("sky.png");
		godzilla = new Texture("godzilla.png");
		corax = new Texture("corax.png");



		//shapeRenderer = new ShapeRenderer();
		zombieCircle = new Circle();
		fireAngle = new Circle[mansNumber];
		weapAngle = new Circle[mansNumber];
		citzAngle = new Circle[citzenNumber];
//		zombie = new Texture[2];
//		zombie[0] = new Texture("zombieDown.png");
//		zombie[1] = new Texture("zombieUp.png");

		zombie = new Texture[4];
		zombie[0] = new Texture("zombieDown.png");
		zombie[1] = new Texture("zombieUp.png");
		zombie[2] = new Texture("BigZombieB.png");
		zombie[3] = new Texture("BigZombieM.png");

		manFire = new Texture("mantor.png");
		manWeapon = new Texture("manweap.png");
		citzen = new Texture("citzen.png");
		random = new Random();
		randomSpeed = new Random();
		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.CYAN);
		scoreFont.getData().setScale(10);
		gameOver = new Texture("gamover.png");
		distanceBetweenMans = Gdx.graphics.getWidth() + 500; //растояние между первым и вторям отрядом
		distanceCitzens = Gdx.graphics.getWidth() + 1500;
		distancecastle = Gdx.graphics.getWidth() + 600;
		distanceground = Gdx.graphics.getWidth() + 100;
		distanceground2 = Gdx.graphics.getWidth() + 100;
		distanceTree = Gdx.graphics.getWidth() + 2000;
		distanceGodzilla = Gdx.graphics.getWidth() + 1500;
		distanceCorax = Gdx.graphics.getWidth() + 900;

		InitGame();


	}

	public void InitGame() {
		jumpHeigh = Gdx.graphics.getHeight() / 9 - zombie[0].getHeight() / 9;
		for (int i = 0; i < mansNumber; i++) {
			manFireX[i] = 500 + Gdx.graphics.getWidth() + i * distanceBetweenMans; //растояние между первым и вторям отрядом
			//mansShift[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() / 7 - 50;
			fireAngle[i] = new Circle();
		}
		for (int j = 0; j < mansNumber; j++) {
			manWeaponX[j] = 1400 + Gdx.graphics.getWidth() + j * distanceBetweenMans; //растояние между первым и вторям отр
			//mansShift[j] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() / 9 - 10;
			weapAngle[j] = new Circle();
		}
		for (int c = 0; c < citzenNumber; c++) {
			citzenX[c] = 900 + Gdx.graphics.getWidth() + c * distanceCitzens; //растояние между первым и вторям отр
			//citzenShift[c] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() / 9 - 10;
			citzAngle[c] = new Circle();
		}
		for (int k = 0; k < castleNumber; k++) {
			castleX[k] = 700 + Gdx.graphics.getWidth() + k * distancecastle; //растояние между первым и вторям отр

		}
		for (int t = 0; t < groundNumber; t++) {
			groundX[t] = 1; //растояние между первым и вторям отр
		}
		for (int t2 = 0; t2 < groundNumber2; t2++) {
			groundX2[t2] = 1200; //растояние между первым и вторям отр
		}
		for (int tr= 0; tr < treeNumber; tr++) {
			treeX[tr] = 1500 ; //растояние между первым и вторям отр
		}
		for (int g= 0; g < godzillaNumber; g++) {
			godzillaX[g] = 1000 + Gdx.graphics.getWidth() + g * distanceGodzilla; //растояние между первым и вторям отр
		}
		for (int cr= 0; cr < coraxNumber; cr++) {
			coraxX[cr] = 1200 + Gdx.graphics.getWidth() + cr * distanceCorax; //растояние между первым и вторям отр
			coraxShift[cr] = (random.nextFloat() - 0.5f) * 900 - 50;
		}




	}

	@Override
	public void render() {
		batch.begin();
		//batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch.draw(sky, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		for (int g = 0; g < godzillaNumber; g++) { // годира
			if (godzillaX[g] < -godzilla.getWidth()) {
				godzillaX[g] = godzillaNumber * distanceGodzilla;
			} else {
				godzillaX[g] -= godzillaSpeed;
				//manFireX[i] -= manSpeed[i];
			}
			batch.draw(godzilla, godzillaX[g], 900);
			//fireAngle[i] = new Circle(manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i], 100);
		}

		batch.draw(forestBack, 0, -370, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//--------фон

		for (int k = 0; k < castleNumber; k++) {
			if (castleX[k] < -castle.getWidth()) {
				castleX[k] = castleNumber * distancecastle;
			} else {
				castleX[k] -= castleSpeed;
				//manFireX[i] -= manSpeed[i];
			}
			batch.draw(castle, castleX[k], Gdx.graphics.getHeight() / 10);
			//fireAngle[i] = new Circle(manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i], 100);
		}

		for (int t = 0; t < groundNumber; t++) {
			if (groundX[t] < -ground.getWidth()) {
				groundX[t] = groundNumber+ distanceground;
			} else {
				groundX[t] -= groundSpeed;
				//manFireX[i] -= manSpeed[i];
			}
			batch.draw(ground, groundX[t], 0);
			//fireAngle[i] = new Circle(manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i], 100);
		}

		for (int t2 = 0; t2 < groundNumber2; t2++) {
			if (groundX2[t2] < -ground2.getWidth()) {
				groundX2[t2] = groundNumber2+ distanceground2;
			} else {
				groundX2[t2] -= groundSpeed2;
				//manFireX[i] -= manSpeed[i];
			}
			batch.draw(ground2, groundX2[t2], 5);
			//fireAngle[i] = new Circle(manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i], 100);
		}

		batch.draw(zombie[zombieStateFlag], 150, jumpHeigh);

		for (int tr = 0; tr < treeNumber; tr++) {
			if (treeX[tr] < -tree.getWidth()) {
				treeX[tr] = groundNumber2+ distanceground2;
			} else {
				treeX[tr] -= treeSpeed;
				//manFireX[i] -= manSpeed[i];
			}
			batch.draw(tree, treeX[tr], Gdx.graphics.getHeight() / 12);
			//fireAngle[i] = new Circle(manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i], 100);
		}



//-----кончился фон
		if (gameStatFlag == 1) {
			if (eatFlag == 3) {
				if (Gdx.input.justTouched()) {
					eatScore = eatScore +1;
					Gdx.app.log("Пожраль!!!!", String.valueOf(eatScore));
					eatFlag = 0;
				}
			}
			if (manFireX[passedIndex] < Gdx.graphics.getWidth() / 30) {
				gameScore++;
				if (passedIndex < mansNumber - 1) {
					passedIndex++;
				} else {
					passedIndex = 0;
				}
			}
			if (manWeaponX[passedIndex] < Gdx.graphics.getWidth() / 30) {
				gameScore++;
				if (passedIndex < mansNumber - 1) {
					passedIndex++;
				} else {
					passedIndex = 0;
				}
			}
		}
		if (gameStatFlag == 1) {



			for (int i = 0; i < mansNumber; i++) {
				if (manFireX[i] < -manFire.getWidth()) {
					manFireX[i] = mansNumber * distanceBetweenMans;
				} else {
					manFireX[i] -= manSpeed;
					//manFireX[i] -= manSpeed[i];
				}
				batch.draw(manFire, manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i]);
				fireAngle[i] = new Circle(manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i], 100);
			}

			for (int j = 0; j < mansNumber; j++) {
				if (manWeaponX[j] < -manWeapon.getWidth()) {
					manWeaponX[j] = mansNumber * distanceBetweenMans;
				} else {
					manWeaponX[j] -= manSpeed;
					//manWeaponX[j] -= manSpeed[j];
				}
				batch.draw(manWeapon, manWeaponX[j], Gdx.graphics.getHeight() / 10 + mansShift[j]);
				weapAngle[j] = new Circle(manWeaponX[j], Gdx.graphics.getHeight() / 10 + mansShift[j], 100);
			}

			for (int c = 0; c < citzenNumber; c++) {
				if (citzenX[c] < -citzen.getWidth()) {
					citzenX[c] = citzenNumber * distanceCitzens;
				} else {
					citzenX[c] -= citzenSpeed;
				}
				batch.draw(citzen, citzenX[c], Gdx.graphics.getHeight() / 10 + citzenShift[c]);
				citzAngle[c] = new Circle(citzenX[c], Gdx.graphics.getHeight() / 10 + citzenX[c], 100);
			}


			for (int cr = 0; cr < coraxNumber; cr++) {
				if (coraxX[cr] < -corax.getWidth()) {
					coraxX[cr] = coraxNumber * distanceCorax;
				} else {
					coraxX[cr] -= coraxSpeed;
				}
				batch.draw(corax, 900-coraxX[cr], 900 + coraxShift[cr]);

			}



			if (Gdx.input.justTouched()) {
				jumpSpeed = -30;
			}
			if (jumpHeigh > Gdx.graphics.getHeight() / 9 || jumpSpeed < 0) {
				jumpSpeed++;
				jumpHeigh -= jumpSpeed;
				if (jumpHeigh > 1200) {
					jumpSpeed = 30;
				}
			}
		} else if (gameStatFlag == 0) {
			if (Gdx.input.justTouched()) {
				Gdx.app.log("tap", "Прыжок");
				gameStatFlag = 1;
			}


		} else if (gameStatFlag == 2) {
			batch.draw(gameOver, Gdx.graphics.getWidth() / 2 - gameOver.getWidth() / 2, Gdx.graphics.getHeight() / 2 - gameOver.getHeight() / 2);
			if (Gdx.input.justTouched()) {
				Gdx.app.log("tap", "ПРЫЖОК");
				gameStatFlag = 1;
				InitGame();
				gameScore = 0;
				passedIndex = 0;
				jumpSpeed = 0;
			}
		}

//////////
		if(eatScore <4){
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
//////////////

		//batch.draw(zombie[zombieStateFlag], 150, jumpHeigh);


		String ZombieHP = String.valueOf(HP);
		scoreFont.draw(batch, String.valueOf(eatScore), 100, 150);
		//scoreFont.draw(batch, "HP: " + ZombieHP, 220, 150);
		batch.end();

		zombieCircle.set(210, jumpHeigh + 105, 90);
		for (int i = 0; i < mansNumber; i++) {
			if (Intersector.overlaps(zombieCircle, fireAngle[i]) || Intersector.overlaps(zombieCircle, weapAngle[i])) {
				Gdx.app.log("Пересечено", "Попался");
				gameStatFlag = 2;
			}
		}
		for (int c = 0; c < citzenNumber; c++) {
			if (Intersector.overlaps(zombieCircle, citzAngle[c])) {
				eatFlag = 3;
			}
			//shapeRenderer.end();
		}



	}
}
