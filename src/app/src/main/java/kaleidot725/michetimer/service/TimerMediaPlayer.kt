package kaleidot725.michetimer.service

import android.content.Context
import android.media.MediaPlayer
import kaleidot725.michetimer.R

class TimerMediaPlayer(context : Context, name : String) : TimerMediaPlayerInterface {
    override val context = context
    override val name : String = name
    override val id : Int = when(name) {
        "chime"  -> { R.raw.chime  }
        "timeup" -> { R.raw.timeup }
        else     -> { R.raw.chime  }
    }

    private var player : MediaPlayer = MediaPlayer.create(context, id).apply { isLooping = true }

    override fun play() {
        if (player.isPlaying)
        {
            return
        }

        player.start()
    }

    override fun stop() {
        if (!player.isPlaying)
        {
            return
        }

        player.pause()
        player.seekTo(0)
    }

    override fun finalize() {
        player.release()
    }
}