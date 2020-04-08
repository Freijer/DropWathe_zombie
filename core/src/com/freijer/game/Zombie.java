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



//после сьедения 20 людей зомби мутирует, больше жизней
public class Zombie extends ApplicationAdapter {
	SpriteBatch batch;
	int zombieStateFlag = 0;
	Texture background;
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

	Texture castle;
	int castleNumber;
	float castleX[] = new float[castleNumber];



	//ShapeRenderer shapeRenderer;



	@Override
	public void create() {
		batch = new SpriteBatch();
		background = new Texture("country.png");
		castle = new Texture();

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
		InitGame();
	}

	public void InitGame() {
		jumpHeigh = Gdx.graphics.getHeight() / 9 - zombie[0].getHeight() / 9;
		for (int i = 0; i < mansNumber; i++) {
			manFireX[i] = 500 + Gdx.graphics.getWidth() + i * distanceBetweenMans; //растояние между первым и вторям отрядом
			mansShift[i] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() / 7 - 50;
			fireAngle[i] = new Circle();
		}
		for (int j = 0; j < mansNumber; j++) {
			manWeaponX[j] = 1400 + Gdx.graphics.getWidth() + j * distanceBetweenMans; //растояние между первым и вторям отр
			mansShift[j] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() / 9 - 10;
			weapAngle[j] = new Circle();
		}
		for (int c = 0; c < citzenNumber; c++) {
			citzenX[c] = 900 + Gdx.graphics.getWidth() + c * distanceCitzens; //растояние между первым и вторям отр
			citzenShift[c] = (random.nextFloat() - 0.5f) * Gdx.graphics.getHeight() / 9 - 10;
			citzAngle[c] = new Circle();
		}
	}

	@Override
	public void render() {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		if (gameStatFlag == 1) {
			if (eatFlag == 3) {
				if (Gdx.input.justTouched()) {
					eatScore = eatScore +1;
					Gdx.app.log("Пожраль!!!!", String.valueOf(eatScore));
					eatFlag = 0;
				}
			}
			if (manFireX[passedIndex] < Gdx.graphics.getWidth() / 2) {
				gameScore++;
				if (passedIndex < mansNumber - 1) {
					passedIndex++;
				} else {
					passedIndex = 0;
				}
			}
			if (manWeaponX[passedIndex] < Gdx.graphics.getWidth() / 2) {
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

		batch.draw(zombie[zombieStateFlag], 150, jumpHeigh);


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
