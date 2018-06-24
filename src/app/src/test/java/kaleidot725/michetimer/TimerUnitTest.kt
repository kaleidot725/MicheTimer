package kaleidot725.michetimer

import android.arch.core.executor.testing.InstantTaskExecutorRule
import kaleidot725.michetimer.Models.Timer
import kaleidot725.michetimer.Models.TimerState
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

    lateinit var timer : Timer
    @Before
    fun setup() {
        timer = Timer("Name", 60)
    }

    @Test
    fun constructor() {
        Assert.assertEquals("Name", timer.name)
        Assert.assertEquals(TimerState.Init, timer.state)
        Assert.assertEquals(60L, timer.seconds)
        Assert.assertEquals(60L, timer.remainSeconds)
    }

    @Test
    fun fromRunToPause() {
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state)
        Thread.sleep(1100)

        timer.update()
        Assert.assertTrue(timer.remainSeconds as Long <= 59L)
        Thread.sleep(1100)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state)
        Assert.assertTrue(timer.remainSeconds as Long <= 58L)

        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state)
        Thread.sleep(1100)

        timer.update()
        Assert.assertTrue(timer.remainSeconds as Long <= 57L)
        Thread.sleep(1100)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state)
        Assert.assertTrue(timer.remainSeconds as Long <= 56L)
    }

    @Test
    fun fromRunToInit() {
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state)

        timer.reset()
        Assert.assertEquals(TimerState.Init, timer.state)
        Assert.assertEquals(60L, timer.remainSeconds)
    }

    @Test
    fun fromPauseToInit(){
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state)

        timer.reset()
        Assert.assertEquals(TimerState.Init, timer.state)
        Assert.assertEquals(60L, timer.remainSeconds)
    }

    @Test
    fun fromTimeupToInit() {
        val timeupTimer = Timer("Name", 1)
        timeupTimer.run()
        Thread.sleep(2000)

        timeupTimer.update()
        Assert.assertEquals(TimerState.Timeup, timeupTimer.state)
        Assert.assertEquals(0L, timeupTimer.remainSeconds)

        timeupTimer.reset()
        Assert.assertEquals(TimerState.Init, timer.state)
        Assert.assertEquals(60L, timer.remainSeconds)
    }

    @Test
    fun fromRunToTimeup() {
        val timeupTimer = Timer("Name", 1)
        timeupTimer.run()
        Thread.sleep(2000)

        timeupTimer.update()
        Assert.assertEquals(TimerState.Timeup, timeupTimer.state)
        Assert.assertEquals(0L, timeupTimer.remainSeconds)
    }
}
