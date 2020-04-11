package com.okayjam.kotlin



class App(var height: Int, var width: Int) {
    fun getArea(a: Int, b: Int): Int = a * b
}



fun main () {

        val a = """JAM\sd1234546567658768!@@#$%%^^&***?><"""
        println(a)
        println("Hello World!")

        for ( b in 1..10 step 2) {
            println(b)
        }

        var map = HashMap<String, String>()
        map["jam"] = "Jam1111111";
        println(map["jam"])

        var list1=listOf(1,2,3);

    val filter = list1.filter { x -> x > 2 }
    println(list1)
    println(filter)

    var i={x:Int , y:Int -> x+y}
    println(i(1,2))

    println(getRoundArea(radius = 1F))


    var aa = 123
         println(aa)
    var num1= aa?.toInt()
    println(num1)

    try {
        println(1/aa!!.toInt())
    }catch (e:Exception) {
        e.printStackTrace()
        println(e)
        println(e.message)
    }



    var rect = App(5, 10) //构建Rect对象，不需要new
    println("矩形的宽${rect.width}高${rect.height}") //引用Rect类中成员变量
    println("矩形的面积是${rect.getArea(rect.width, rect.height)}") //引用Rect类中成员变量

    val result = method1(p2 = 2.5)
    println(result)

}


fun method1(p1:Int=10 , p2:Double):Double{
    return p1 * p2
}


//为变量PI赋予了默认值 Pi,这样，调用该方法时可以不再传递PI。但，如果我们想传入的值和默认值不一致时还是需要传入的
fun getRoundArea(PI:Float=3.1415926F , radius:Float):Float{
    return PI*radius*radius
}


