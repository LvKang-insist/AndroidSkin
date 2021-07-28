package com.lvkang.example

/**
 * @name Test
 * @package com.lvkang.example
 * @author 345 QQ:1831712732
 * @time 2020/12/20 00:55
 * @description
 */

// 1000,560
fun test(a: Int, b: Int): Int {
    var a1 = a
    var b1 = b
    while (b1 != 0) {
        val temp = a1 % b1
        a1 = b1
        b1 = temp
    }
    return b1
}


fun test2(a: Int, b: Int): Int {
    return if (b != 0) {
        test2(b, a % b)
    } else {
        a
    }
}

fun main() {
    println(test2(1000, 560))
}

fun test1(a: Int, b: Int): Int {
    var a1 = a
    var b1 = b
    if (a < b) {
        val t = a1
        a1 = b1
        b1 = t
    }
    while (b1 != 0) {
        val yu = a1 % b1
        a1 = b1
        b1 = yu
    }
    return a1
}

sealed class Test2 {
    val test1: Test2? = null
    abstract val test2: Test2
}