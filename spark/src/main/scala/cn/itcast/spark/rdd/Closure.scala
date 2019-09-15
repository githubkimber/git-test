package cn.itcast.spark.rdd

import org.junit.Test

class Closure {

/*

*/
@Test
def test() : Unit = {

val f : Int => Double = closure()
val result = f(5)
println(result)
}

def closure() : Int => Double = {

val factor = 3.14
val areaFunction = (r : Int) => math.pow(r , 2) * factor
areaFunction
}

}
