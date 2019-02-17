package kaleidot725.michetimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kaleidot725.michetimer.model.repository.PersistenceFile
import kaleidot725.michetimer.model.entity.Timer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class PersistenceFileUnitTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val filename : String = "setting.json"

    @Test
    fun Scenario()
    {
        val expected = mutableListOf<Timer>()
        expected.add(Timer(1, "2", 3, "4"))
        expected.add(Timer(2, "3", 4, "5"))
        expected.add(Timer(3, "4", 5, "6"))

        val file = PersistenceFile(filename, Timer::class.java)
        file.save(expected)
        val actual = file.load()

        Assert.assertEquals(expected, actual)
    }
}