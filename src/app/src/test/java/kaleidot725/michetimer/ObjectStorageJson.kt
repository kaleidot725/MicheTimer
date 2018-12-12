package kaleidot725.michetimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kaleidot725.michetimer.domain.TimerRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ObjectStorageJson {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var timer : TimerRunner
    @Before
    fun setup() {
        timer = TimerRunner(1, "Name", 60)
    }

    @Test
    fun constructor() {
    }

    @Test
    fun Save() {
    }
}
