package kaleidot725.michetimer.model.domain.alarm

import kaleidot725.michetimer.R

class AlarmType(val value: Int) {
    companion object {
        val Bellstar = 0
        val Garagara = 1
        val Hiutiisi = 2
        val Suzu = 3
        val Tetupaipu = 4

        fun convertStringToAlarmType(str : String) : Int {
            when(str) {
                "bellstar" -> return Bellstar
                "garagara" -> return Garagara
                "hiutiisi" -> return Hiutiisi
                "suzu" -> return Suzu
                "tetupaipu" -> return Tetupaipu
                else -> return Bellstar
            }
        }

        fun convertAlarmTypeToString(type : Int) : String {
            when(type) {
                Bellstar -> return "bellstar"
                Garagara -> return "garagara"
                Hiutiisi -> return "hiutiisi"
                Suzu -> return "suzu"
                Tetupaipu -> return "tetupaipu"
                else -> return "bellstar"
            }
        }

        fun convertAlarmTypeToResouceId(type : Int) : Int {
            when(type) {
                Bellstar -> return R.raw.bellstar
                Garagara -> return R.raw.garagara
                Hiutiisi -> return R.raw.hiutiisi
                Suzu -> return R.raw.suzu
                Tetupaipu -> return R.raw.tetupaipu
                else -> return R.raw.bellstar
            }
        }
    }
}



