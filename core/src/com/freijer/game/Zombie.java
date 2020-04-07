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
	float jumpSpeed =0;
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
	//int manSpeed[] = new int[mansNumber]; // переменная скорость

	Circle zombieCircle;

	Circle[] fireAngle;
	Circle[] weapAngle;
	int gameScore = 0;
	int passedIndex = 0;

	BitmapFont scoreFont;
	Texture gameOver;
	//ShapeRenderer shapeRenderer;



	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("country.png");
		//shapeRenderer = new ShapeRenderer();

		zombieCircle = new Circle();
		fireAngle = new Circle[mansNumber];
		weapAngle = new Circle[mansNumber];

		zombie = new Texture[2];
		zombie[0] = new Texture("zombieDown.png");
		zombie[1] = new Texture("zombieUp.png");


		manFire = new Texture("mantor.png");
		manWeapon = new Texture("manweap.png");


		random = new Random();
		randomSpeed = new Random();
		scoreFont = new BitmapFont();
		scoreFont.setColor(Color.CYAN);
		scoreFont.getData().setScale(10);

		gameOver = new Texture("gamover.png");

		distanceBetweenMans = Gdx.graphics.getWidth() + 500; //растояние между первым и вторям отрядом
		InitGame();

	}

	public void InitGame(){
		jumpHeigh = Gdx.graphics.getHeight()/9 - zombie[0].getHeight()/9;
		for(int i=0; i <mansNumber; i++){
			manFireX[i] = 500 + Gdx.graphics.getWidth()  + i * distanceBetweenMans ; //растояние между первым и вторям отрядом
			//manFireX[i] = Gdx.graphics.getWidth()/ 4  + i * distanceBetweenMans ; //растояние между первым и вторям отрядом
			mansShift[i] = (random.nextFloat() - 0.5f) *Gdx.graphics.getHeight()/7 - 50;
			fireAngle[i] = new Circle();
		}
		for(int j=0; j <mansNumber; j++){
			manWeaponX[j] = 1400 + Gdx.graphics.getWidth() + j * distanceBetweenMans ; //растояние между первым и вторям отр
			//manWeaponX[j] = Gdx.graphics.getWidth()/ 10  + j * distanceBetweenMans ; //растояние между первым и вторям отрядом
			mansShift[j] = (random.nextFloat() - 0.5f) *Gdx.graphics.getHeight()/9 - 10;
			weapAngle[j] = new Circle();
		}
	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if ( gameStatFlag == 1){
			Gdx.app.log("Очки",  String.valueOf(gameScore));
			if(manFireX[passedIndex] < Gdx.graphics.getWidth()/2){
				gameScore ++;
				if (passedIndex < mansNumber - 1){
					passedIndex++;
				} else {
					passedIndex = 0;
				}
			}
			if(manWeaponX[passedIndex] < Gdx.graphics.getWidth()/2){
				gameScore ++;
				if (passedIndex < mansNumber - 1){
					passedIndex++;
				} else {
					passedIndex = 0;
				}
			}

		}
		if (gameStatFlag ==1){

			for(int i=0; i <mansNumber; i++) {
				if (manFireX[i]< -manFire.getWidth() ){
					manFireX[i] = mansNumber * distanceBetweenMans;
				} else {
					manFireX[i] -= manSpeed;
					//manFireX[i] -= manSpeed[i];

				}
				batch.draw(manFire, manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i]);
//			int x = (int)manFireX[i];
//			int y = (int) ((int)Gdx.graphics.getHeight() / 9 + mansShift[i]);
//			fireRectangle[i] = new Rectangle(x, y, manFire.getWidth(), manFire.getHeight());
				fireAngle[i] = new Circle(manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i], 100);
			}

			for(int j=0; j <mansNumber; j++) {
				if (manWeaponX[j] < -manWeapon.getWidth()){
					manWeaponX[j] = mansNumber * distanceBetweenMans;
				} else {
					manWeaponX[j] -= manSpeed;
					//manWeaponX[j] -= manSpeed[j];
				}
				batch.draw(manWeapon, manWeaponX[j], Gdx.graphics.getHeight() / 10 + mansShift[j]);
//			int x = (int) manWeaponX[j];
//			int y = (int) ((int)Gdx.graphics.getHeight() / 10 + mansShift[j]);
//			fireRectangle[j] = new Rectangle(x, y, manFire.getWidth(), manFire.getHeight());
				weapAngle[j] = new Circle(manWeaponX[j], Gdx.graphics.getHeight() / 10 + mansShift[j], 100);
			}



			if (Gdx.input.justTouched()){
				 jumpSpeed = -30;
			}
			if (jumpHeigh >Gdx.graphics.getHeight()/9 || jumpSpeed < 0){
				jumpSpeed ++;
				jumpHeigh -=jumpSpeed;
				if (jumpHeigh > 1200){
					jumpSpeed = 30;
				}
			}
		} else if (gameStatFlag ==0){
			if (Gdx.input.justTouched()){
				Gdx.app.log("tap", "Прыжок");
				gameStatFlag = 1;
			}
		} else if (gameStatFlag ==2){
			batch.draw(gameOver, Gdx.graphics.getWidth()/2 - gameOver.getWidth() /2, Gdx.graphics.getHeight()/2 - gameOver.getHeight()/2);
			if (Gdx.input.justTouched()){
				Gdx.app.log("tap", "Прыжок");
				gameStatFlag = 1;
				InitGame();
				gameScore = 0;
				passedIndex = 0;
				jumpSpeed = 0;
			}
		}
//		for(int i=0; i <mansNumber; i++) {
//			manFireX[i] -= manSpeed;
//			manWeaponX[i] -= manSpeed;
//			batch.draw(manFire, manFireX[i], Gdx.graphics.getHeight() / 9 + 30);
//			batch.draw(manWeapon, manWeaponX[i], Gdx.graphics.getHeight() / 9 - 70);
//		}

		/*
		for(int i=0; i <mansNumber; i++) {
			if (manFireX[i]< -manFire.getWidth() ){
				manFireX[i] = mansNumber * distanceBetweenMans;
			} else {
				manFireX[i] -= manSpeed;
				//manFireX[i] -= manSpeed[i];

			}
			batch.draw(manFire, manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i]);
//			int x = (int)manFireX[i];
//			int y = (int) ((int)Gdx.graphics.getHeight() / 9 + mansShift[i]);
//			fireRectangle[i] = new Rectangle(x, y, manFire.getWidth(), manFire.getHeight());
			fireAngle[i] = new Circle(manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i], 100);
		}

		for(int j=0; j <mansNumber; j++) {
			if (manWeaponX[j] < -manWeapon.getWidth()){
				manWeaponX[j] = mansNumber * distanceBetweenMans;
			} else {
				manWeaponX[j] -= manSpeed;
				//manWeaponX[j] -= manSpeed[j];
			}
			batch.draw(manWeapon, manWeaponX[j], Gdx.graphics.getHeight() / 10 + mansShift[j]);
//			int x = (int) manWeaponX[j];
//			int y = (int) ((int)Gdx.graphics.getHeight() / 10 + mansShift[j]);
//			fireRectangle[j] = new Rectangle(x, y, manFire.getWidth(), manFire.getHeight());
			weapAngle[j] = new Circle(manWeaponX[j], Gdx.graphics.getHeight() / 10 + mansShift[j], 100);
		}
*/

		if (zombieStateFlag ==0){
			zombieStateFlag = 1;
		} else {
			zombieStateFlag = 0;
		}



		batch.draw(zombie[zombieStateFlag], 150, jumpHeigh);

		scoreFont.draw(batch, String.valueOf(gameScore), 100, 150);
		batch.end();

		//zombieCircle.set(100, jumpHeigh, zombie[zombieStateFlag].getWidth()/2);
		zombieCircle.set(210, jumpHeigh+105, 90);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.CYAN);
		//shapeRenderer.circle(zombieCircle.x, zombieCircle.y, zombieCircle.radius);

		for(int i=0; i <mansNumber; i++) {

			//shapeRenderer.circle(manFireX[i] + 60, Gdx.graphics.getHeight() / 9 + mansShift[i] + 105, 80);
			//shapeRenderer.circle(manWeaponX[i] + 60, Gdx.graphics.getHeight() / 10 + mansShift[i] + 105, 80);
			//shapeRenderer.setColor(Color.GREEN);

			if(Intersector.overlaps(zombieCircle, fireAngle[i]) || Intersector.overlaps(zombieCircle, weapAngle[i])){
				Gdx.app.log("Пересечено", "Попался");
				gameStatFlag =2;
				//
			}


		}


		//shapeRenderer.end();


	}
	

}
