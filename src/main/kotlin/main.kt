import java.util.*

fun main(args: Array<String>) {
    val scan = Scanner(System.`in`)
    val arrays = Array(6) {Array(6) {0} }
    for (i in 0 until 6) {
        arrays[i] = scan.nextLine().split(" ").map {it.trim().toInt()}.toTypedArray()
    }
    val result = hourGlassSum(arrays)
    println(result)
}

fun hourGlassSum(arrays: Array<Array<Int>>): Int {

    TODO("NYI")
}
