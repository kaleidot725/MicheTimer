package kaleidot725.michetimer

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.databinding.ObservableList
import kaleidot725.michetimer.domain.*
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TimerRepositoryUnitTest {

    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private val persistence : Persistence<Timer> = FilePercistenceDummy()

    @Test
    fun AddとRemove()
    {
        val repository : Repository<Timer> = TimerRepository(persistence)
        val one = Timer(1, "2", 3, "4")
        val two = Timer(2, "3", 4, "5")
        val thr = Timer(3, "4", 5, "6")

        repository.add(one)
        repository.add(two)
        repository.add(thr)
        Assert.assertEquals(3, repository.count())

        repository.remove(one)
        repository.remove(two)
        repository.remove(thr)
        Assert.assertEquals(0, repository.count())
    }

    @Test(expected = IllegalArgumentException::class)
    fun Add_IDあり()
    {
        val repository : Repository<Timer> = TimerRepository(persistence)
        val org = Timer(1, "2", 3, "4")
        val same = Timer(1, "3", 4, "5")
        repository.add(org)
        repository.add(same)
    }

    @Test(expected = IllegalArgumentException::class)
    fun Remove_IDなし()
    {
        val repository : Repository<Timer> = TimerRepository(persistence)
        val org = Timer(1, "2", 3, "4")
        repository.remove(org)
    }

    @Test
    fun find()
    {
        val repository : Repository<Timer> = TimerRepository(persistence)
        val one = Timer(1, "2", 3, "4")
        val two = Timer(2, "3", 4, "5")
        val thr = Timer(3, "4", 5, "6")
        val allList = listOf<Timer>(one, two, thr)
        val emptyList = listOf<Timer>()

        repository.add(one)
        repository.add(two)
        repository.add(thr)
        Assert.assertEquals( one, repository.findById(one.id))
        Assert.assertEquals( two, repository.findById(two.id))
        Assert.assertEquals( thr, repository.findById(thr.id))
        Assert.assertEquals( allList, repository.findAll())

        repository.remove(one)
        repository.remove(two)
        repository.remove(thr)
        Assert.assertEquals( null, repository.findById(one.id))
        Assert.assertEquals( null, repository.findById(two.id))
        Assert.assertEquals( null, repository.findById(thr.id))
        Assert.assertEquals(emptyList, repository.findAll())
    }

    @Test
    fun find_IDなし() {
        val repository : Repository<Timer> = TimerRepository(persistence)
        val item = repository.findById(1)
        Assert.assertEquals(null, item)
    }

    @Test
    fun callback()
    {
        val repository : Repository<Timer> = TimerRepository(persistence)
        val spy = ObservableCallbackSpy()
        repository.addOnListChangedCallback(spy)

        val one = Timer(1, "2", 3, "4")
        val two = Timer(2, "3", 4, "5")
        val thr = Timer(3, "4", 5, "6")

        repository.add(one)
        repository.add(two)
        repository.add(thr)

        Assert.assertEquals(3, spy.insertedCount)
        Assert.assertEquals(0, spy.removedCount)
        Assert.assertEquals(3, spy.changedCount)
        Assert.assertEquals( 0, spy.rangeMovedCount)
        Assert.assertEquals(3, spy.rangeChangedCount)

        repository.remove(one)
        repository.remove(two)
        repository.remove(thr)

        Assert.assertEquals(3, spy.insertedCount)
        Assert.assertEquals(3, spy.removedCount)
        Assert.assertEquals(6, spy.changedCount)
        Assert.assertEquals( 0, spy.rangeMovedCount)
        Assert.assertEquals(6, spy.rangeChangedCount)
    }

    class ObservableCallbackSpy : ObservableList.OnListChangedCallback<ObservableList<Timer>>() {
        var insertedCount = 0
        var removedCount = 0
        var changedCount = 0
        var rangeMovedCount = 0
        var rangeChangedCount = 0

        override fun onItemRangeInserted(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
            insertedCount++
        }

        override fun onItemRangeRemoved(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
            removedCount++
        }

        override fun onChanged(sender: ObservableList<Timer>?) {
            changedCount++
        }

        override fun onItemRangeMoved(sender: ObservableList<Timer>?, fromPosition: Int, toPosition: Int, itemCount: Int) {
            rangeMovedCount++
        }

        override fun onItemRangeChanged(sender: ObservableList<Timer>?, positionStart: Int, itemCount: Int) {
            rangeChangedCount++
        }
    }

    class FilePercistenceDummy : Persistence<Timer> {
        override fun load(): List<Timer> {
            return mutableListOf()
        }

        override fun save(list: List<Timer>) {
        }
    }
}
