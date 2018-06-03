package kaleidot725.michetimer

import kaleidot725.michetimer.Models.Timer
import kaleidot725.michetimer.Models.TimerState
import org.junit.Assert
import org.junit.Test

import org.junit.Before

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TimerUnitTest {

    lateinit var timer : Timer
    @Before
    fun setup() {
        timer = Timer("Name", 60)
    }

    @Test
    fun constructor() {
        Assert.assertEquals("Name", timer.name)
        Assert.assertEquals(TimerState.Init, timer.state)
        Assert.assertEquals(60, timer.remainSeconds)
    }

    @Test
    fun fromRunToPause() {
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state)

        Thread.sleep(1100)
        Assert.assertTrue(timer.remainSeconds <= 59)
        Thread.sleep(1100)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state)
        Assert.assertTrue(timer.remainSeconds <= 58)

        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state)

        Thread.sleep(1100)
        Assert.assertTrue(timer.remainSeconds <= 57)
        Thread.sleep(1100)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state)
        Assert.assertTrue(timer.remainSeconds <= 56)
    }

    @Test
    fun fromRunToInit() {
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state)

        timer.reset()
        Assert.assertEquals(TimerState.Init, timer.state)
        Assert.assertEquals(60, timer.remainSeconds)
    }

    @Test
    fun fromPauseToInit(){
        timer.run()
        Assert.assertEquals(TimerState.Run, timer.state)

        timer.pause()
        Assert.assertEquals(TimerState.Pause, timer.state)

        timer.reset()
        Assert.assertEquals(TimerState.Init, timer.state)
        Assert.assertEquals(60, timer.remainSeconds)
    }

    @Test
    fun fromTimeupToInit() {
        val timeupTimer = Timer("Name", 1)
        timeupTimer.run()

        Thread.sleep(2000)

        Assert.assertEquals(TimerState.Timeup, timeupTimer.state)
        Assert.assertEquals(0, timeupTimer.remainSeconds)

        timeupTimer.reset()
        Assert.assertEquals(TimerState.Init, timer.state)
        Assert.assertEquals(60, timer.remainSeconds)
    }

    @Test
    fun fromRunToTimeup() {
        val timeupTimer = Timer("Name", 1)
        timeupTimer.run()

        Thread.sleep(2000)

        Assert.assertEquals(TimerState.Timeup, timeupTimer.state)
        Assert.assertEquals(0, timeupTimer.remainSeconds)
    }
}
