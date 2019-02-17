package kaleidot725.michetimer.domain

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
                "bellstar" -> return AlarmType.Bellstar
                "garagara" -> return AlarmType.Garagara
                "hiutiisi" -> return AlarmType.Hiutiisi
                "suzu" -> return AlarmType.Suzu
                "tetupaipu" -> return AlarmType.Tetupaipu
                else -> return AlarmType.Bellstar
            }
        }

        fun convertAlarmTypeToString(type : Int) : String {
            when(type) {
                AlarmType.Bellstar -> return "bellstar"
                AlarmType.Garagara -> return "garagara"
                AlarmType.Hiutiisi -> return "hiutiisi"
                AlarmType.Suzu -> return "suzu"
                AlarmType.Tetupaipu -> return "tetupaipu"
                else -> return "bellstar"
            }
        }

        fun convertAlarmTypeToResouceId(type : Int) : Int {
            when(type) {
                AlarmType.Bellstar -> return R.raw.bellstar
                AlarmType.Garagara -> return R.raw.garagara
                AlarmType.Hiutiisi -> return R.raw.hiutiisi
                AlarmType.Suzu -> return R.raw.suzu
                AlarmType.Tetupaipu -> return R.raw.tetupaipu
                else -> return R.raw.bellstar
            }
        }
    }
}



