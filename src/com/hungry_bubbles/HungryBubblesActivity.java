package com.hungry_bubbles;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class HungryBubblesActivity extends Activity {
	private AppManager appManager;
	private GameView gameView;	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);  
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.game_screen);
        
        appManager = (AppManager) getApplication();
        gameView = new GameView(this, appManager.getStartingPlayer());
        LinearLayout layout = (LinearLayout) findViewById(R.id.main_layout);  
        layout.addView(gameView);        
    }
}