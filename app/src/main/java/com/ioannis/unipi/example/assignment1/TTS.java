package com.ioannis.unipi.example.assignment1;

import android.content.Context;
import android.speech.tts.TextToSpeech;

public class TTS extends TextToSpeech {

    public TTS(Context context, OnInitListener listener) {
        super(context, listener);
    }

    public void speak (String text) {
        this.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    public void stopSpeak () {
        this.stop();
    }
    public void shutdownTTS() {
        this.stop();
        this.shutdown();
        super.stop();
    }

}
