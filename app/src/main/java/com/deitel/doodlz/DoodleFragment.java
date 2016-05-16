// DoodleFragment.java
// Fragmento no qual o componete DoodView é exibido
package com.deitel.doodlz;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class DoodleFragment extends Fragment
{
    private DoodleView doodleView; //trata eventos de toque e desenho
    private float acceleration;
    private float currentAcceleration;
    private float lastAcceleration;
    private boolean dialogOnScreen =  false;

    // valor para determinar se o usuario chacoalhou o dispositivo para apagar
    private static final int ACCELERATION_THRESHOLD = 100000;

    // Chamado quando a view do fragmento precisa ser criada
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        View view =
                inflater.inflate(R.layout.fragment_doodle, container, false);
        setHasOptionsMenu(true); //este fragmento tem itens de menu a exibir

        // obtém referência para o componete DoodleView
        doodleView = (DoodleView)view.findViewById(R.id.doodleView);

        // inicializa valores de aceleração
        acceleration = 0.00f;
        currentAcceleration = SensorManager.GRAVITY_EARTH;
        lastAcceleration = SensorManager.GRAVITY_EARTH;
        return view;
    }

    // começa a detectar eventos de sensor
    @Override
    public void onStart()
    {
        super.onStart();
        enableAccelerometerListening(); // detecta chacoalho
    }

    // Habilita a detecção de eventos
    public void enableAccelerometerListening()
    {
        //obtém o componente SensorManager
        SensorManager sensorManager =
                (SensorManager)getActivity().getSystemService(
                        Context.SENSOR_SERVICE);

        // registra a detecção de eventos de acelerômetro
        sensorManager.registerListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);;
    }

    // para de detectar eventos de sensor
    @Override
    public void onPause()
    {
        super.onPause();
        disableAccelerometerListening(); // para de detectar chacoalho
    }

    // desabilita a detecção de eventos de acelerômetro
    public void disableAccelerometerListening()
    {
        //obtém o componente SensorManager
        SensorManager sensorManager =
                (SensorManager)getActivity().getSystemService(
                        Context.SENSOR_SERVICE);

        //para de detectar eventos de acelerômetro
        sensorManager.unregisterListener(sensorEventListener,
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    // rotina de tratamento para eventos de acelerômetro
    private SensorEventListener sensorEventListener =
            new SensorEventListener()
            {
                // usa acelerômetro para determeninar se o usuário chacoalhou o dispositivo
                @Override
                public void onSensorChanged(SensorEvent event)
                {
                    // garante que outras caixas de diálogo não sejam exibidas
                    if (!dialogOnScreen)
                    {
                        // obtém valores x, y e z para componente SensorEvent
                        float x = event.values[0];
                        float y = event.values[1];
                        float z = event.values[2];

                        // salva valor de aceleração anterior
                        lastAcceleration = currentAcceleration;

                        // calcula a aceleração atual
                        currentAcceleration = x*x + y*y + z*z;

                        // calcula a mudança na aceleração
                        acceleration = currentAcceleration *
                                (currentAcceleration - lastAcceleration);

                        // se a aceleração está acima de determinado limite
                        if (acceleration > ACCELERATION_THRESHOLD)
                            confirmErase();
                    }
                }// fim do método onSensorChanged

                // método obrigatório da interface SensorEventListener
                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy)
                {
                }
            }; //fim da classe interna anônima

} //Final
