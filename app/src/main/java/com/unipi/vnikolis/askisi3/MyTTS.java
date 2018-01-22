package com.unipi.vnikolis.askisi3;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by Βαγγέλης on 20/1/2018.
 */

public class MyTTS {

    private TextToSpeech tts;

    public MyTTS(Context context) //Constractor
    {
        TextToSpeech.OnInitListener initListener = new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                //σε τι γλωσσα θελω να ξεκινησω
                if (status == TextToSpeech.SUCCESS)
                    tts.setLanguage(Locale.ROOT);

            }
        };
        tts = new TextToSpeech(context, initListener);
    }

    public void speak(String what2say){
        tts.speak(what2say, TextToSpeech.QUEUE_ADD,null);// δουλευει η μεθοδος
    }
}
