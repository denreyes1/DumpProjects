import jdk.nashorn.internal.objects.NativeFunction.apply

fun main(args: Array<String>) {
//    tryLetFun()
//    tryRunFun()
//    tryApplyFun()
//    tryAlsoFun()
    tryWithFun()
}

fun tryLetFun() {
    val lumaLength = "luma".let{
        it.length
    }.let{
        "luma"
    }

    println("LET > Luma Length = $lumaLength")

    var kumaVar : String? = null
    val kumaLength = kumaVar?.let{
        it.length
    }

    println("LET > Kuma Length = $kumaLength")
}

fun tryRunFun() {
    val lumaLength = "luma".run{
        length
    }.run {
        "luma"
    }


    val testLength2 = run {
        "lumos".length
    }

    println("RUN > Luma length = $lumaLength")
}
data class Box(
    var color: String? = "",
    var size: String? = "",
    var contents: Int = 0
) {
    override fun toString(): String {
        return "Color = $color, Size = $size, Contents = $contents"
    }
}

fun tryApplyFun(){
    val box = Box("Red", "L", 5).apply {
        color = "Blue"
        size = "M"
        contents = 10
    }

    println(box.toString())
}

fun tryAlsoFun() {
    val box = Box("Red", "L", 5)
        .also{
            println(it.toString())
        }.apply {
            color = "Blue"
            size = "M"
            contents = 10
        }.also {
            it.color = "Purple"
            it.size = "Small"
            it.color = "Midnight"
        }
    println(box.toString())
}

fun tryWithFun() {
    val box = Box("Red", "L", 5)

    with(box) {
        println(box)
    }
}