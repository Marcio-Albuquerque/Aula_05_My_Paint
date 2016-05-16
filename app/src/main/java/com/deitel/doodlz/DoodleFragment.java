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


} //Final
