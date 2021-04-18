package com.freijer.game;

import android.content.Intent;
import android.os.Bundle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new ZombieProgres(), config);

//		startActivity(new Intent(AndroidLauncher.this, Start.class));
//		Intent intentResult = new Intent(this, Start.class);
//		startActivity(intentResult);
	}
}
