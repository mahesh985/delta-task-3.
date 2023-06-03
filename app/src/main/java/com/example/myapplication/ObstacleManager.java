package com.example.myapplication;

import static com.example.myapplication.Constants.*;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class ObstacleManager {
    private ArrayList<Obatacle> obstacles;
    private int playerGap;

    private int obstacleGap;

    private int obstacleHeight;
    private  int color ;

    private  long stratTime;
    private long initTime;
    private int score=0;

    public ObstacleManager(int playerGap, int obstacleGap, int i, int black){
        this.playerGap = playerGap;
        this.obstacleGap= obstacleGap;
        this.obstacleHeight = obstacleHeight;
        this.color= color;

        stratTime=initTime = System.currentTimeMillis();

        obstacles = new ArrayList<>();



        populateObstacles ();
    }

    public boolean playerCollide(RectPlayer player){
        for(Obatacle ob : obstacles){
            if(ob.playerCollide(player))
                return true;
        }
        return false;
    }
    private void populateObstacles() {
        int currY = -5* SCREEN_HEIGHT/4;
        while(currY <0){
            int xStart =(int)(Math.random()*(SCREEN_WIDTH-playerGap));
           obstacles.add(new Obatacle(obstacleHeight,color,xStart,currY,playerGap));
           currY+=obstacleHeight + obstacleGap;

        }

    }

    public void update(){
        int elapsedTime = (int) (System.currentTimeMillis() - stratTime);
        stratTime  = (int) System.currentTimeMillis();
        float speed =(float)(Math.sqrt(1+(stratTime-initTime)/1000.0))*SCREEN_HEIGHT/10000.0f;
        for(Obatacle ob : obstacles){
            ob.incrementY(speed*elapsedTime);
        }

        if(obstacles.get(obstacles.size()-1).getRectangle().top>= SCREEN_HEIGHT){
            int xStart =(int)(Math.random()*(SCREEN_WIDTH-playerGap));
            obstacles.add(0,new Obatacle(obstacleHeight,color,xStart,obstacles.get(0).getRectangle().top + obstacleHeight + obstacleGap,playerGap ));
            obstacles.remove(obstacles.size()-1);
            score++;
        }


    }
    public void draw(Canvas canvas){

        for(Obatacle ob : obstacles)
            ob.draw(canvas);
        Paint paint =new Paint();
        paint.setTextSize(100);
        paint.setColor(Color.MAGENTA);
        canvas.drawText(""+score,50,50+paint.descent()-paint.ascent(),paint);
    }
}
