// MainActivity.java
// Configura o layout de Main Activity
package com.deitel.doodlz;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;

public class MainActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //determina o tamanho da tela
        int screenSize =
           getResources().getConfiguration().screenLayout &
           Configuration.SCREENLAYOUT_SIZE_MASK;

        // usa paisagem para tablets extragrandes; caso contrario, usa retrato
        if (screenSize == Configuration.SCREENLAYOUT_SIZE_XLARGE)
            setRequestedOrientation(
               ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(
               ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

    }
}