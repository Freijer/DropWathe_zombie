package com.freijer.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Random;

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

	int manSpeed = 4;
	int mansNumber = 5;

	float manFireX[] = new float[mansNumber];
	float manWeaponX[] = new float[mansNumber];

	float distanceBetweenMans;

	float mansShift[] = new float[mansNumber];
	Random random;



	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("country.png");
		zombie = new Texture[2];
		zombie[0] = new Texture("zombieDown.png");
		zombie[1] = new Texture("zombieUp.png");
		jumpHeigh = Gdx.graphics.getHeight()/9 - zombie[0].getHeight()/9;

		manFire = new Texture("mantor.png");
		manWeapon = new Texture("manweap.png");
		random = new Random();

//		manFireX = Gdx.graphics.getWidth()/ 2 - 140;
//		manWeaponX = Gdx.graphics.getWidth()/ 2 + 250;

		distanceBetweenMans = Gdx.graphics.getWidth(); //растояние между первым и вторям отрядом
		for(int i=0; i <mansNumber; i++){
			manFireX[i] = Gdx.graphics.getWidth()/ 2 - 140 + i * distanceBetweenMans - 50 ; //растояние между первым и вторям отрядом
			mansShift[i] = (random.nextFloat() - 0.5f) *Gdx.graphics.getHeight()/7 - 50;
		}
		for(int j=0; j <mansNumber; j++){
			manWeaponX[j] = Gdx.graphics.getWidth()/ 2 + 250 + j * distanceBetweenMans + 40; //растояние между первым и вторям отрядом
			mansShift[j] = (random.nextFloat() - 0.5f) *Gdx.graphics.getHeight()/9 - 30;
		}

//		for(int j=0; j <mansNumber; j++){
//			manWeaponX[j] = Gdx.graphics.getWidth()/ 2 + 250 + j * distanceBetweenMans;
//		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		if (Gdx.input.justTouched()){
			Gdx.app.log("tap", "Упал");
			gameStatFlag = 1;
		}
		if (gameStatFlag ==1){



			if (Gdx.input.justTouched()){
				 jumpSpeed = -30;
			}
			if (jumpHeigh >Gdx.graphics.getHeight()/9 || jumpSpeed < 0){
				jumpSpeed ++;
				jumpHeigh -=jumpSpeed;
				if (jumpHeigh > 1200){
					jumpSpeed = 40;
				}
			}
		} else {
			if (Gdx.input.justTouched()){
				Gdx.app.log("tap", "Упал");
				gameStatFlag = 1;
			}
		}
//		for(int i=0; i <mansNumber; i++) {
//			manFireX[i] -= manSpeed;
//			manWeaponX[i] -= manSpeed;
//			batch.draw(manFire, manFireX[i], Gdx.graphics.getHeight() / 9 + 30);
//			batch.draw(manWeapon, manWeaponX[i], Gdx.graphics.getHeight() / 9 - 70);
//		}
		for(int i=0; i <mansNumber; i++) {
			manFireX[i] -= manSpeed;
			batch.draw(manFire, manFireX[i], Gdx.graphics.getHeight() / 9 + mansShift[i]);
		}

		for(int j=0; j <mansNumber; j++) {
			manWeaponX[j] -= manSpeed;
			batch.draw(manWeapon, manWeaponX[j], Gdx.graphics.getHeight() / 10 + mansShift[j]);
		}

		if (zombieStateFlag ==0){
			zombieStateFlag = 1;
		} else {
			zombieStateFlag = 0;
		}



		batch.draw(zombie[zombieStateFlag], 100, jumpHeigh);



		batch.end();

	}
	

}
