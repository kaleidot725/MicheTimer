package kaleidot725.michetimer.main

enum class MainFilter(val raw: Int)  {
    None(0),
    NameAsc(1),
    NameDesc(2),
    SecondsAsc(3),
    SecondsDesc(4),
    Search(5),
}