package kaleidot725.michetimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kaleidot725.michetimer.model.timer.TimerRunner
import kaleidot725.michetimer.model.timer.TimerRunnerInterface
import kaleidot725.michetimer.model.timer.TimerRunnerState
import org.junit.Assert
import org.junit.Test
import org.junit.Rule

class TimerRunnerUnitTest {

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val ms = 1000L
    private val expectedId : Int = 1
    private val expectedName : String = "Name"
    private val expectedSeconds : Long = 60L
    private val timer : TimerRunnerInterface = TimerRunner(expectedId, expectedName, expectedSeconds)

    @Test
    fun constructor() {
        Assert.assertEquals(expectedName, timer.name)
        Assert.assertEquals(TimerRunnerState.Init, timer.state.value)
        Assert.assertEquals(expectedSeconds, timer.seconds)
        Assert.assertEquals(expectedSeconds, timer.remainSeconds.value)
    }

    @Test
    fun RunとPause繰り返し() {
        timer.run()
        Assert.assertEquals(TimerRunnerState.Run, timer.state.value)
        Thread.sleep(ms)
        Thread.sleep(ms)

        Assert.assertTrue(timer.remainSeconds.value as Long <= 59L)
        Thread.sleep(ms)

        timer.pause()
        Assert.assertEquals(TimerRunnerState.Pause, timer.state.value)
        Assert.assertTrue(timer.remainSeconds.value as Long <= 58L)

        timer.run()
        Assert.assertEquals(TimerRunnerState.Run, timer.state.value)
        Thread.sleep(ms)

        Assert.assertTrue(timer.remainSeconds.value as Long <= 57L)
        Thread.sleep(ms)

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
        Assert.assertEquals(expectedSeconds, timer.remainSeconds.value)
    }

    @Test
    fun PauseからReset(){
        timer.run()
        Assert.assertEquals(TimerRunnerState.Run, timer.state.value)

        timer.pause()
        Assert.assertEquals(TimerRunnerState.Pause, timer.state.value)

        timer.reset()
        Assert.assertEquals(TimerRunnerState.Init, timer.state.value)
        Assert.assertEquals(expectedSeconds, timer.remainSeconds.value)
    }

    @Test
    fun TimeupからReset() {
        val shortTimer = TimerRunner(expectedId, expectedName, 1)
        shortTimer.run()
        Thread.sleep(ms)

        shortTimer.reset()
        Assert.assertEquals(TimerRunnerState.Init, timer.state.value)
        Assert.assertEquals(expectedSeconds, timer.remainSeconds.value)
    }

    @Test
    fun RunからTimeup() {
        val shortTimer = TimerRunner(expectedId, expectedName, 1)
        shortTimer.run()
        Thread.sleep(ms * 3)

        Assert.assertEquals(TimerRunnerState.Timeout, shortTimer.state.value)
        Assert.assertEquals(0L, shortTimer.remainSeconds.value)
    }
}
