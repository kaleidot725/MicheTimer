package kaleidot725.michetimer.model.domain.alarm

import android.content.Context
import android.media.SoundPool
import android.media.AudioAttributes


class AlarmPlayerDefault(context : Context, type : Int, isRepeating : Boolean) : AlarmPlayer {

    override val context = context
    override val type : Int = type

    override var isPlaying : Boolean = false
    override var isRepeating : Boolean = isRepeating

    private val resource : Int = AlarmType.convertAlarmTypeToResouceId(type)
    private val pool : SoundPool
    private val soundId : Int
    private var streamId : Int = 0

    init {
        val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).setContentType(AudioAttributes.CONTENT_TYPE_SPEECH).build()
        pool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(1).build()
        soundId = pool.load(context, resource, 1)
    }

    override fun play() {
        if (isPlaying)
        {
            return
        }

        streamId = pool.play(soundId, 1.0f, 1.0f, 1, if(isRepeating) -1 else 0, 1.0f)
        isPlaying = true
    }

    override fun stop() {
        if (!isPlaying)
        {
            return
        }

        pool.stop(streamId)
        isPlaying = false
    }

    override fun dispose() {
        pool.release()
    }
}