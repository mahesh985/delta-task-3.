package com.example.myapplication;

import static android.graphics.Color.BLACK;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.camera2.params.BlackLevelPattern;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;



public class Gamepanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private Rect r =new Rect();
    private RectPlayer player;
    private Point playerpoint;

    private ObstacleManager obstacleManager;
    private boolean movingPlayer=false;
    private boolean gameOver=false;

    private long gameOverTime;
    public Gamepanel(Context context) {
        super(context);

        getHolder().addCallback(this);

        player = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255,0,0));
        playerpoint =new Point(Constants.SCREEN_WIDTH/2, 3*Constants.SCREEN_HEIGHT/4);
        player.update(playerpoint);

        obstacleManager =new ObstacleManager(200,350,75, BLACK);

        thread = new MainThread(getHolder(),this);

        setFocusable(true);

    }

    public void reset(){
        playerpoint=new Point(Constants.SCREEN_WIDTH/2,3*Constants.SCREEN_HEIGHT/4);
        player.update(playerpoint);
        obstacleManager =new ObstacleManager(200,350,75, BLACK);
        movingPlayer=false;

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height ){

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }




    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        while (true) {
            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event ) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!gameOver && player.getRectangle().contains((int) event.getX(),(int)event.getY()))
                    movingPlayer=true;
                if(gameOver && System.currentTimeMillis()-gameOverTime >=2000){
                    reset();
                    gameOver=false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!gameOver && movingPlayer)
                     playerpoint.set((int) event.getX(),(int) event.getY());
                break;
            case MotionEvent.ACTION_UP:
                movingPlayer=false;
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }
    public  void update(){
        if(!gameOver) {
            player.update(playerpoint);
            obstacleManager.update();
            if (obstacleManager.playerCollide(player)) {
                gameOver=true;
                gameOverTime=System.currentTimeMillis();
            }
        }
    }
  @Override
  public void draw(Canvas canvas ){
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);

        if(gameOver){
            Paint paint=new Paint();
            paint.setTextSize(100);
            paint.setColor(Color.MAGENTA);
            drawCenterText(canvas, paint,"Game Over");
        }
  }
    private void drawCenterText(Canvas canvas, Paint paint, String text) {

        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(Color.rgb(255, 255, 255));

        canvas.getClipBounds(r);

        int cHeight = r.height();

        int cWidth = r.width();

        paint.getTextBounds(text, 0, text.length(), r);

        float x = cWidth / 2f -r.width() / 2f- r.left;
        float y = cHeight / 2f + r.height() / 2f- r.bottom;

        canvas.drawText(text, x, y, paint);
    }
}