// DoodleView.java
// Visualização principal do aplicativo My Paint
package com.deitel.doodlz;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.print.PrintHelper;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

// a tela principal que é pintada
public class DoodleView extends View
{
    // usado para determinar se o usuário moveu um dedo o suficiente para
    // desenhar novamente
    private static final float TOUCH_TOLERANCE = 10;

    private Bitmap bitmap; // área de desenho a exibir ou salvar
    private Canvas bitmapCanvas; // usado para desenhar no bitmap
    private final Paint paintScreen; // usado para desenhar o bitmap na tela
    private final Paint paintLine; // usado para desenhar linhas no bitmap

    // mapas dos objetos Path que estão sendo desenhados e os
    // objetos Point desses objetos Path
    private final Map<Integer, Path> pathMap = new HashMap<Integer, Path>();

    private final Map<Integer, Point> previousPointMap =
            new HashMap<Integer, Point>();

    // usado para ocultar/mostrar barras de sistema
    private GestureDetector singleTapDetector;

    public DoodleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
}
