package kaleidot725.michetimer

import android.arch.core.executor.testing.InstantTaskExecutorRule
import kaleidot725.michetimer.models.TimerRunner
import kaleidot725.michetimer.models.TimerState
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.Rule


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TimerUnitTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var timer : TimerRunner
    @Before
    fun setup() {
        timer = TimerRunner("Name", 60)
    }

    @Test
    fun constructor() {
        Assert.assertEquals("Name", timer.name)
        Assert.assertEquals(TimerState.Init, timer.state.value)
        Assert.assertEquals(60L, timer.seconds)
        Assert.assertEquals(60L, timer.remainSeconds.value)
    }

    @Test
    fun fromRunToPause() {
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state.value)
        Thread.sleep(1100)

        Assert.assertTrue(timer.remainSeconds.value as Long <= 59L)
        Thread.sleep(1100)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state.value)
        Assert.assertTrue(timer.remainSeconds.value as Long <= 58L)

        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state.value)
        Thread.sleep(1100)

        Assert.assertTrue(timer.remainSeconds.value as Long <= 57L)
        Thread.sleep(1100)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state.value)
        Assert.assertTrue(timer.remainSeconds.value as Long <= 56L)
    }

    @Test
    fun fromRunToInit() {
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state.value)

        timer.reset()
        Assert.assertEquals(TimerState.Init, timer.state.value)
        Assert.assertEquals(60L, timer.remainSeconds.value)
    }

    @Test
    fun fromPauseToInit(){
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state.value)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state.value)

        timer.reset()
        Assert.assertEquals(TimerState.Init, timer.state.value)
        Assert.assertEquals(60L, timer.remainSeconds.value)
    }

    @Test
    fun fromTimeupToInit() {
        val timeupTimer = TimerRunner("Name", 1)
        timeupTimer.run()
        Thread.sleep(2000)

        Assert.assertEquals(TimerState.Timeout, timeupTimer.state.value)
        Assert.assertEquals(0L, timeupTimer.remainSeconds.value)

        timeupTimer.reset()
        Assert.assertEquals(TimerState.Init, timer.state.value)
        Assert.assertEquals(60L, timer.remainSeconds.value)
    }

    @Test
    fun fromRunToTimeup() {
        val timeupTimer = TimerRunner("Name", 1)
        timeupTimer.run()
        Thread.sleep(2000)

        Assert.assertEquals(TimerState.Timeout, timeupTimer.state.value)
        Assert.assertEquals(0L, timeupTimer.remainSeconds.value)
    }
}
