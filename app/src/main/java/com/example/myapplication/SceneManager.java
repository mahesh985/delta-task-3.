package com.example.myapplication;

import android.graphics.Canvas;
import android.text.method.Touch;
import android.view.MotionEvent;

import java.util.ArrayList;

public class SceneManager {
    public ArrayList<Scene> scenes = new ArrayList<>();
    private static int  ACTIVE_SCENE;
    public  SceneManager(){
        ACTIVE_SCENE =0;
        scenes.add(new Gameplayscene());
    }

    public  void recieveTouch(MotionEvent event){

    }


    public void update(){

    }
    public void draw(Canvas canvas ){
        scenes.get(ACTIVE_SCENE).draw(canvas);

    }

}
