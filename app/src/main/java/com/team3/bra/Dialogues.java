package com.team3.bra;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;

public class Dialogues extends AlertDialog.Builder{
    private Dialogues(@NonNull Context context) {
        super(context);
    }
    private View myView;
    private AlertDialog alertDialog;

    public View getView(){
        return myView;
    }
    public void dismiss(){
        alertDialog.dismiss();
    }

    public static Dialogues dialogueFactory(Activity a, Context c, int id){
        Dialogues builder = new Dialogues(c);
        builder.myView = a.getLayoutInflater().inflate(id, null);
        builder.setView(builder.myView);
        builder.alertDialog = builder.create();
        builder.alertDialog.show();
        return builder;

    }
}
