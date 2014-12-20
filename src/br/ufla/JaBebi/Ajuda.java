package br.ufla.JaBebi;

import br.ufla.trabfinal.R;
import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Ajuda extends Activity implements SensorEventListener{
	private Button voltar;
	private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    MediaPlayer som;
    
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ajuda);
		voltar = (Button) findViewById(R.id.btnVOLTAR);
		mSensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        som = MediaPlayer.create(this, R.raw.cerveja);
        Toast.makeText(this, R.string.easterEgg, Toast.LENGTH_LONG).show();

		//Ao clicar no botão voltar
		voltar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Ajuda.this, Inicio.class);
				startActivity(i);
				finish();
			}
		});
	}
	
	@Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
     
    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
     
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
 
    @Override
    public void onSensorChanged(SensorEvent event) {
        Float x = event.values[0];
        //Float y = event.values[1];
        //Float z = event.values[2];
         
         /*
        Os valores ocilam de -10 a 10.
        Quanto maior o valor de X mais ele ta caindo para a esquerda - Positivo Esqueda 
        Quanto menor o valor de X mais ele ta caindo para a direita  - Negativo Direita
        Se o valor de  X for 0 então o celular ta em pé - Nem Direita Nem Esquerda
        Se o valor de Y for 0 então o cel ta "deitado"
         Se o valor de Y for negativo então ta de cabeça pra baixo, então quanto menor y mais ele ta inclinando pra ir pra baixo
        Se o valor de Z for 0 então o dispositivo esta reto na horizontal.
        Quanto maioro o valor de Z Mais ele esta inclinado para frente
        Quanto menor o valor de Z Mais ele esta inclinado para traz.
        */
         
        if(x > 5)  
        	som.start();
        if(x < -5)  
        	som.start();
    } 

}
