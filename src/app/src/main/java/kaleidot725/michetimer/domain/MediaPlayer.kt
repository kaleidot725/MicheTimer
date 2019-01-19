package kaleidot725.michetimer.domain

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.rtp.AudioStream
import android.provider.MediaStore
import kaleidot725.michetimer.R

class MediaPlayer(context : Context, name : String) : MediaPlayerInterface {
    override val context = context
    override val name : String = name
    override val id : Int = when(name) {
        "chime"  -> { R.raw.chime  }
        "timeup" -> { R.raw.timeup }
        else     -> { R.raw.chime  }
    }

    private var player : MediaPlayer = MediaPlayer.create(context, id).apply {
        isLooping = true
    }

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