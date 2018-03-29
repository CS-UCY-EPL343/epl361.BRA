package com.team3.bra;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

/**
 * Alternation of the AlertDialog.Builder. It is responsible to create a normal
 * AlertDialog with some extra getters and a dialogue Factory.
 *
 */
public class Dialogues extends AlertDialog.Builder {
    private Dialogues(@NonNull Context context) {
        super(context);
    }

    private View myView;
    private AlertDialog alertDialog;

    /**
     * Getter for the Dialogue view.
     *
     * @return the Dialogue view.
     */
    public View getView() {
        return myView;
    }

    /**
     * Function that dismisses the current dialogue.
     */
    public void dismiss() {
        alertDialog.dismiss();
    }

    /**
     * Dialogue Factory for creating a dialogue box from a specific activity and
     * context
     *
     * @param a
     *            the activity which the dialogue will be created from.
     * @param c
     *            the context which the dialogue will be created from.
     * @param id
     *            The id of the inflating dialogue.
     * @return a dialogue box from a specific activity and context.
     */
    public static Dialogues dialogueFactory(Activity a, Context c, int id) {
        Dialogues builder = new Dialogues(c);
        builder.myView = a.getLayoutInflater().inflate(id, null);
        builder.setView(builder.myView);
        builder.alertDialog = builder.create();
        builder.alertDialog.show();
        return builder;

    }
}
