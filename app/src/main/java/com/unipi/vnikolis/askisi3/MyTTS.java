package com.unipi.vnikolis.askisi3;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Βαγγέλης on 20/1/2018.
 */

//κλάσση για να κανει speak οτι του δηλωσεις(φτιαχνουμε τα settings εδω) Text To Translate
public class MyTTS {

    private TextToSpeech tts;

    public MyTTS(Context context) //Constractor
    {
        //με την κλάσση TextToSpeech καλούμε την μέθοδο OnInitListener η οποία ειναι τυπου interface επιστρεφει δηλαδη interface
        TextToSpeech.OnInitListener initListener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //σε τι γλωσσα θελω να ξεκινησω
                if (status == TextToSpeech.SUCCESS)
                    tts.setLanguage(Locale.ROOT);  //root = για οτι γλώσσα είναι η συσκευη του κινητού

            }
        };
        tts = new TextToSpeech(context, initListener);
    }

    public void speak(String what2say){
        tts.speak(what2say, TextToSpeech.QUEUE_ADD,null);// δουλευει η μεθοδος
    }
}
