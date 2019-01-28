package kaleidot725.michetimer.addtimer

class AddTimerMode(value : Int) {
    companion object {
        val add : Int = 0
        val edit : Int = 1
    }

    var value : Int = value
    val valueString : String
        get() = when(value) {
            add  -> "Add Timer"
            edit -> "Edit Timer"
            else -> "Unkown Timer"
        }
}