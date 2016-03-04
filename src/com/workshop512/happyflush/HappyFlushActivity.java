package com.workshop512.happyflush;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;



public class HappyFlushActivity extends Activity {
	
    public static final int FLUSH_SOUND1 = 1;
    public static final int FLUSH_SOUND2 = 2;
    public static final int FLUSH_SOUND3 = 3;
    private SoundPool soundPool;
    private HashMap<Integer, Integer> soundPoolMap;
	private Integer samplenum = 1;
	private Context mContext;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	mContext = this;    // since Activity extends Context
    	
        //get rid of titlebar
        requestWindowFeature(Window.FEATURE_NO_TITLE);  
        //get rid of notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
       
        initSounds();
        
        
        Button btn = (Button)this.findViewById(R.id.flush_button);
        
        //modify the color of the button
        //btn.getBackground().setColorFilter(new LightingColorFilter(0xFF9933, 0x0CC000)); 
        
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	
            /*
        		PackageManager pm = mContext.getPackageManager();
        		Intent appStartIntent = pm.getLaunchIntentForPackage("com.chartcross.gpstest");
        		Log.d("+++", "attempting to start appStartIntent: " + appStartIntent );
        		if (null != appStartIntent)
        		{
        			mContext.startActivity(appStartIntent);  //try to start
        		    //startActivityForResult(appStartIntent, 1);
        		}
        		else {
        			Toast.makeText(getApplicationContext(), "Install 'GPSTest App' to view internal GPS status." , Toast.LENGTH_SHORT ).show();
        		}            	
            */
        
            	//next two lines for setting button text
                //Button btn=(Button)v;
                //btn.setText("Flushing...");
                
               	playSound(samplenum);

				Log.d("++++","samplenum is "+ samplenum);

                
                //make a toast 
				String toastStr = "Please enjoy the flushing. Sample "+ samplenum.toString() + ".";
                Toast toast= Toast.makeText(getApplicationContext(), toastStr, Toast.LENGTH_LONG);
                //Toast.makeText(getApplicationContext(), getString(R.string.emailregisternotentered), Toast.LENGTH_SHORT).show();
                toast.setGravity(Gravity.BOTTOM, 0, 0);
                toast.show();
                
                //Increment the sample number for next time.
               	//samplenum = samplenum % 3; //R will be 0 thru 2
               	samplenum = (samplenum % 3) + 1; //samplenum will be 1 thru 3
        

            }
        });
        
    }
    
    @Override
    public void onAttachedToWindow() {   //this is to ensure smooth gradients on all devices
    	    super.onAttachedToWindow();
    	    Window window = getWindow();
    	    window.setFormat(PixelFormat.RGBA_8888);
    }
    
    
    
    
    private void initSounds() {
    	soundPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 100);
    	soundPoolMap = new HashMap<Integer, Integer>();
    	soundPoolMap.put(FLUSH_SOUND1, soundPool.load(this.getBaseContext(), R.raw.tflush1, 1));
    	soundPoolMap.put(FLUSH_SOUND2, soundPool.load(this.getBaseContext(), R.raw.tflush2, 2));
    	soundPoolMap.put(FLUSH_SOUND3, soundPool.load(this.getBaseContext(), R.raw.tflush3, 3));
    }


    public void playSound(int sound) {
            AudioManager mgr = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
            int streamVolume = mgr.getStreamVolume(AudioManager.STREAM_MUSIC);
            soundPool.play(soundPoolMap.get(sound), streamVolume, streamVolume, 1, 0, 1f);
    }
    
    /* Gets the length of the sound in ms, so you can wait
     * between playing sounds in a sequence
     */
    private long getSoundDuration(int rawId){
    	  MediaPlayer player = MediaPlayer.create(mContext, rawId);
    	  int duration = player.getDuration();
    	  return duration;
    	}
    
    public void update() {
    	if (isExploding()) {
    		playSound(FLUSH_SOUND1);
       }
    }

	private boolean isExploding() {
		return false;
	}
    
    	
}