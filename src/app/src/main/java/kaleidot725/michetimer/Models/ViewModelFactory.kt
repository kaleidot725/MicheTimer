package kaleidot725.michetimer.Models

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.speech.tts.TextToSpeech
import kaleidot725.michetimer.MicheTimer.MicheTimerViewModel

class ViewModelFactory() : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass == MicheTimerViewModel::class.java)
            return MicheTimerViewModel() as T

        throw IllegalArgumentException("Unknown ViewModel class : ${modelClass.name}")
    }
}
