package kaleidot725.michetimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import kaleidot725.michetimer.domain.FilePersistence
import kaleidot725.michetimer.domain.Timer
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class FilePersistenceUnitTest {

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

        val file = FilePersistence(filename, Timer::class.java)
        file.save(expected)
        val actual = file.load()

        Assert.assertEquals(expected, actual)
    }
}