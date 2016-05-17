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

    // o construtor de DoodleView o inicializa
    public DoodleView(Context context, AttributeSet attrs)
    {
        super(context, attrs); // passa o contexto para o construtor de View
        paintScreen = new Paint(); // usado para exibir bitmap na tela

        // ajusta as configurações de exibição iniciais da linha pintura
        paintLine = new Paint();
        paintLine.setAntiAlias(true); // suaviza as bordas da linha desenhada
        paintLine.setColor(Color.BLACK); //a cor padrão é preto
        paintLine.setStyle(Paint.Style.STROKE); // linha cheia
        paintLine.setStrokeWidth(5); // configura a lugura de linha padrão
        paintLine.setStrokeCap(Paint.Cap.ROUND);// extremidades da linha arredondadas

        // GestureDetector para toques rápidos
        singleTapDetector =
                new GestureDetector(getContext(), singleTapListener);
    }

    // O método onSizeChanged cria Bitmap e Canvas após exibir o aplicativo
    @Override
    public void onSizeChanged(int w, int h, int oldW, int olhH)
    {
        bitmap = Bitmap.createBitmap(getWidth(),getHeight(),
                Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE); // apaga o Bitmap com branco
    }

    // limpa o desenho
    public void clear()
    {
        pathMap.clear(); // remove todos os caminhos
        previousPointMap.clear(); // remove todos os pontos anteriores
        bitmap.eraseColor(Color.WHITE); // apaga o bitmap
        invalidate(); // atualiza a tela
    }

    // configura a cor da linha pintada
    public void setDrawingColor(int color)
    {
        paintLine.setColor(color);
    }

    // retorna a cor da linha pintada
    public int getDrawingColor()
    {
        return paintLine.getColor();
    }

    // configura a lagura da linha pintada
    public void setLineWidth(int width)
    {
        paintLine.setStrokeWidth(width);
    }

    // retorna a largura da linha pintada
    public int getLineWidth()
    {
        return (int) paintLine.getStrokeWidth();
    }

    // Chamada sempre que essa View é desenhada
    @Override
    // chama todas as vezes que a View é desenhada
    protected void onDraw(Canvas canvas) {
        // desenha a tela de fundo
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);
        // para cada path sendo desenhado
        for (Integer key : pathMap.keySet())
            canvas.drawPath(pathMap.get(key), paintLine); // desenha linha
    }

    // oculta as baras de ação
    public void hideSystemBars()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
        setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                        View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_FULLSCREEN |
                        View.SYSTEM_UI_FLAG_IMMERSIVE);
    }

    // mostra as barras de sistema e a barra de ação
    public void showSystemBars(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }

    // cria SimpleOnGestureListener para eventos de toque rápido
    private SimpleOnGestureListener singleTapListener =
            new SimpleOnGestureListener(){
              @Override
                public boolean onSingleTapUp(MotionEvent e){
                  if ((getSystemUiVisibility() &
                          View.SYSTEM_UI_FLAG_HIDE_NAVIGATION) == 0)
                        hideSystemBars();
                  else
                      showSystemBars();
                  return true;
              }
            };

    // trata evento de toque
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // obtém o tipo de evento e o identificador do ponteiro que causou o evento
        // se um evento de toque rápido ocorreu em dispositivo KitKat ou mais recente
        if (singleTapDetector.onTouchEvent(event))
            return true;

        int action = event.getActionMasked(); // tipo de evento
        int actionIndex = event.getActionIndex(); // ponteiro (isto é, o dedo)

        // determina se o toque começou, terminou ou está ocrrendo
        if (action == MotionEvent.ACTION_DOWN|| action == MotionEvent.ACTION_POINTER_DOWN) {
            touchStarted(event.getX(actionIndex),
                    event.getY(actionIndex), event.getPointerId(actionIndex));
        }
        else if (action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_POINTER_UP)
        {
            touchEnded(event.getPointerId(actionIndex));
        }
        else
        {
            touchMoved(event);
        }
        invalidate(); // redesenhar
        return true;
    } //fim do metodo onTouchhEvent


}
