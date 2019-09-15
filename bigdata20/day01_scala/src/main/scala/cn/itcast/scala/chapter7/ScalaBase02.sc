// 方法中使用函数作为参数, 这样的方法叫高阶函数
def m7(x : Int ) = (y : String ) => y
println("m7(50):"+m7(50))

def m8(x : Int ) = {"当方法体只有一句执行语句,外面大括号可以省略"}
println("m8:"+m8(2))
