package kaleidot725.michetimer

import android.arch.core.executor.testing.InstantTaskExecutorRule
import kaleidot725.michetimer.domain.TimerRunner
import kaleidot725.michetimer.domain.TimerRunnerState
import org.junit.Assert
import org.junit.Test
import org.junit.Before
import org.junit.Rule

class TimerRunnerUnitTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var timer : TimerRunner
    @Before
    fun setup() {
        timer = TimerRunner(1, "Name", 60)
    }

    @Test
    fun constructor() {
        Assert.assertEquals("Name", timer.name)
        Assert.assertEquals(TimerRunnerState.Init, timer.state.value)
        Assert.assertEquals(60L, timer.seconds)
        Assert.assertEquals(60L, timer.remainSeconds.value)
    }

    @Test
    fun RunとPause繰り返し() {
        timer.run()
        Assert.assertEquals(TimerRunnerState.Run, timer.state.value)
        Thread.sleep(1100)

        Assert.assertTrue(timer.remainSeconds.value as Long <= 59L)
        Thread.sleep(1100)

        timer.pause()
        Assert.assertEquals(TimerRunnerState.Pause, timer.state.value)
        Assert.assertTrue(timer.remainSeconds.value as Long <= 58L)

        timer.run()
        Assert.assertEquals(TimerRunnerState.Run, timer.state.value)
        Thread.sleep(1100)

        Assert.assertTrue(timer.remainSeconds.value as Long <= 57L)
        Thread.sleep(1100)

        timer.pause()
        Assert.assertEquals(TimerRunnerState.Pause, timer.state.value)
        Assert.assertTrue(timer.remainSeconds.value as Long <= 56L)
    }

    @Test
    fun RunからReset() {
        timer.run()
        Assert.assertEquals(TimerRunnerState.Run, timer.state.value)

        timer.reset()
        Assert.assertEquals(TimerRunnerState.Init, timer.state.value)
        Assert.assertEquals(60L, timer.remainSeconds.value)
    }

    @Test
    fun PauseからReset(){
        timer.run()
        Assert.assertEquals(TimerRunnerState.Run, timer.state.value)

        timer.pause()
        Assert.assertEquals(TimerRunnerState.Pause, timer.state.value)

        timer.reset()
        Assert.assertEquals(TimerRunnerState.Init, timer.state.value)
        Assert.assertEquals(60L, timer.remainSeconds.value)
    }

    @Test
    fun TimeupからReset() {
        val timeupTimer = TimerRunner(1, "Name", 1)
        timeupTimer.run()
        Thread.sleep(2000)

        timeupTimer.reset()
        Assert.assertEquals(TimerRunnerState.Init, timer.state.value)
        Assert.assertEquals(60L, timer.remainSeconds.value)
    }

    @Test
    fun RunからTimeup() {
        val timeupTimer = TimerRunner(1, "Name", 1)
        timeupTimer.run()
        Thread.sleep(3000)

        Assert.assertEquals(TimerRunnerState.Timeout, timeupTimer.state.value)
        Assert.assertEquals(0L, timeupTimer.remainSeconds.value)
    }
}
