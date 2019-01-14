package kaleidot725.michetimer.addtimer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kaleidot725.michetimer.R
import kaleidot725.michetimer.addTimerNavigator

class AddTimerActivity : AppCompatActivity(),  AddTimerNavigator  {

    companion object {
        val addMode : Int = 0
        val editMode : Int = 1

        fun create(context : Context, mode : Int, id : Int) : Intent {
            val intent = Intent(context, AddTimerActivity::class.java).apply {
                putExtra("mode", mode)
                putExtra("id", id)
            }

            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_timer)

        // FIXME シングルトンでの変数保持をやめる
        addTimerNavigator = this
        val mode = intent.getIntExtra("mode", AddTimerActivity.addMode)
        val id = intent.getIntExtra("id", -1)

        when(mode)
        {
            addMode -> setTitle("Add Timer")
            editMode -> setTitle("Edit Timer")
        }

        val transaction = supportFragmentManager.beginTransaction()
        val fragment = AddTimerFragment.create(this, mode, id)
        transaction.replace(R.id.container, fragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onComplete() {
        this.finish()
    }
}
