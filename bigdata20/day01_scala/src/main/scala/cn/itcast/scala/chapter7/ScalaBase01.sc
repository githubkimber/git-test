import scala.collection.mutable
import scala.collection.mutable.{ArrayBuffer, ListBuffer}

val array = new Array[Int](10)
println(array)
array(1) = 12
array(2) = 13
println("array(1):"+array(1))

// 通过伴生对象来创建数组
val array1 = Array(1,2,3,4,5)
println(array1(4))

// 可变长数组
val array3 = new ArrayBuffer[Int]()
array3.append(1,2,3,4,5)            // append : 往数组里面追加元素
println("array3.toString():"+array3.toString())
println("array3.mkString(","):"+array3.mkString(","))
println("array3:"+array3)

// 需求 : 定长数组转变长数组  toBuffer : mutable
val toBuffer : mutable.Buffer[Int] = array.toBuffer
toBuffer.append(20,30,40,50)

// 变长数组转换为定长数组  toArray : Array
val toArray : Array [Int] = array3.toArray
//  变定长后不能再随便添加元素


// 定义多维数组 : ofDim  3行4列
val dim = Array.ofDim[Double](3,4)
dim(1)(1) = 11.11
println(dim.mkString(","))


// 数组的遍历
val array5 = ArrayBuffer(1,2,3,4,5,6)
for (i <- 0 to 5){
  println("array5("+i+"):"+array5(i))
}
for (x<- array5){
  println(x)
}

// 数组的方法
val array6 = Array(1,2,3,4,5,6)
array6.max    // 求数组元素的最大值
array6.min    // 求数组元素的最小值
array6.sum    // 求数组元素的和
array6.sorted     // 对数组元素的排序


// tuple(元组)为一个容器,可以存放各种不同类型的数据,
// 注意: 元组一旦创建之后,就是不可变的,元组当中没有添加和删除这一说.
// 创建元组,直接使用小括号,小括号当中存放各种元素即可.
val tuple = ("helloworker",165,1.2 )
println("tuple:"+tuple)

// tuple (元组)的数据访问,从索引1开始访问: tuple._1
println(tuple._1)

// 元组的遍历 (需要用迭代器: productIterator)
for (x <- tuple.productIterator ){
  println("productIterator:"+x)
}

tuple.productIterator.foreach(x => println(x))


// 定义不可变的Map集合
val map1 = Map("hello" -> "zhangsan" , "age" -> 20 , "address" -> "eath")

// 定义可变Map集合
val map2 = scala.collection.mutable.Map("hello" ->"work" , "age" -> 28)

// 可变map添加元素
map2 += ("name" -> 50)
println("map2+50:"+map2)

// 可变map删除元素
map2 - ("name")
println("map2-name:"+map2)

// 更新元素: += 原本有这个键就更新覆盖,没有就加上;
//           + 原本有这个键就更新覆盖,没有就不加上;
//map2("hello") = "abc"
//println(map2)
map2 += ("hello" -> "xiaomao")
println("map2更新:"+map2)
map2 + ("phone" -> "beijing", "phone" -> "huawei")
println("map2+phone:"+map2)

// 添加key键
map2 += ("abc" -> "旺财")
println("map2+=key键:"+map2)

map2 + ("搞怪呀" -> "周星驰")
println("map2+key键:"+map2)

// 通过key来获取值
// get方法: 有这个key 就取值,没有这个key就返回 none
val key1 = map2.get("name")
println("key1:"+key1)
val key2 = map2.get("jhiu")
println("key2:"+key2)

// getOrElse 方法: 有这个key 就取值,
//                 没有这个key就返回后面的默认值
val key3 = map2.getOrElse("namekkol","傻瓜")
println("key3:"+key3)


// Map集合的遍历
// 获取key 和 value
for ((x,y)<- map2 ){
  println("x:"+x+"-----"+"y:"+y)
}

// 只获取key
for (x <- map2.keys){
  println("x:"+x)
}

// 只获取值
for (y <- map2.values){
  println("y:"+y)
}

// 获取key,value对
for (p <- map2){
  println(p)
}

// 将对偶数组转换成Map集合  toMap
val arrayMap = Array(("name" , "zhangsan") , ("age" , 28))
val map3 : Map[String, Any] = arrayMap.toMap
println("map3:"+map3)


// list 集合(列表)
val list1 = List("abc", 128, 5.0)
// 访问列表中的元素
println("list(0):"+list1(0))

// list 集合添加元素 :+  +:  新元素在哪边+就朝哪边.
val liste = List(1,2,3)
val list3 = liste :+ list1    // list1整体被当做元素右边加入集合
val list4 = liste +: list1    // liste整体被当做元素左边加入集合
println(":+:"+list3)
println("+::"+list4)
val list2 = list1 :+ 10
println("list2:"+list2)
val list5 = 10 +: list1
println("list2:"+list5)

// :: 这种创建方法最右边必须是个list集合来接受前面的元素
// (Nil是一个空集合)
val list6 = 1 :: 2 :: 3 :: list1
println("list1:"+list1)
val list7 = 1 :: 2 :: 3 :: Nil
println("Nil:"+Nil)
val list8 = list1 :: 1 :: 2 :: 3 :: Nil
println("list8:"+list8)
val list9 = 1 :: list1 :: 2 :: 3 :: Nil
println("list9:"+list9)     // list1 被当做元素加入了最右边集合

// 可变长list集合 listBuffer
val list10 = new ListBuffer[String]
println("list10:"+list10)
list10.append("hello")
list10.append("abc")
list10.append("doudou")
println(list10)
for (x <- list10){
  println(x)
}


// Set集合不能重复(java语言中也是) 自带去重复功能
// SortedSet 排序Set集合(排序方式: 红黑树)
val set1 = Set("1","1",2,2)
println("set1:"+set1)
println(set1.mkString(","))

// 导用可变长集合的包后下面的Set集合都是可变长Set集合
import scala.collection.mutable.Set
val set2 = Set(1,2,3)

// 添加元素: += 原本有这个键就更新覆盖,没有就加上;
//           + 原本有这个键就更新覆盖,没有就不加上;
set2.add(4)
set2 += 5
println("+= 5 :"+set2)
println("+= 5 :"+set2.mkString)
set2 + 6
println("+ 6 :"+set2)
println("+ 6 :"+set2.mkString(","))

// 删除元素
set2 - 5              //  - 没用
println("-: "+set2)
set2 -= 3             //  -= 可删除存在的元素
println("-= : "+set2)
set2 -= 8             //  -= 删除不存在的元素,也不报错
println("-= : "+set2)
set2.remove(4)            //  remove 可删除存在的元素
println("remove : "+set2)
set2.remove(7)            //  remove 删除不存在的元素,也不报错
println("remove : "+set2)

// set集合当中遍历元素
for (x <- set2){
  println(x)
}
println(set2.mkString(","))


// 集合中的元素与函数的映射
val listFunc = List("name" ,"age" , "zhangsan", "lisi")
// List集合中的map()方法: 可自定义函数 来改变集合(还是list集合)
val li3 = listFunc.map(x => x + "world")
println(li3)
for(x <- li3){
  println(x)
}

// toUpperCase() 方法: 转换成大写字母
val li4 = listFunc.map(x => x.toUpperCase())
println("=> : "+li4)
// 如果变量x在=>右边只是用了一次,那么可以使用_代替x
val li5 = listFunc.map(_.toUpperCase())
println("_ :"+li5)

// flatten 方法: 压平, 打散, 分解list集合中的字符串元素转换成
// 字符元素(其他集合也可用如: Set)
val listFunc2 = List("address" , "phone")
val li6 : List [Char] = listFunc2.flatten
println(li6.mkString(","))

val set3 = Set("abc" , "hug")
val set30 : Set [Char] = set3.flatten
println(set30)

// flatMap 方法: flatten方法与Map方法的结合,
// 先执行自定义函数再将函数结果压平, 打散, 分解后得到集合;
val li7 = listFunc2.flatMap(x => x + "world")     // 等同于下面
println("li7:"+li7)
val li8 = listFunc2.flatMap(_ +"world")
println("li8"+li8)


// Queue 队列是一个先进先出的结构
// 定义一个可变长的队列  mutable
val queue1 = new mutable.Queue[Int]()
println(queue1)

// 队列添加元素 +=   ++=
queue1 += 8
println("1 : "+queue1)
// 添加集合中的元素  (+=添加不了其他集合,只能添加元素)
queue1 ++= List(1,2,3)
println("list : "+queue1)

// 队列删除(出队(顺序:先进先出))元素  dequeue()
val queue2 : Int = queue1.dequeue()
println("dequeue:"+queue2)
println("dequeue:"+queue1)

// 队列入队操作元素  enqueue
queue1.enqueue(4,5,6,7,8)
println("enqueue:"+queue1)

// 获取队列第一个与最后一个元素  head   last
val queue3 = queue1.head
println("head:"+queue3)
val queue4 = queue1.last
println("last:"+queue4)

// 队列的遍历
for (x<-queue1){
  println("x:"+x)
}

// 折叠化简与扫描操作      reduceList() 在括号内自定义函数
// reduceLeft((x , y) => x )  reduceList(): 从左边开始聚合数据

val ls1 = List(1,2,3,4,5)
ls1.reduceLeft(_+_)           // 等同于下面
ls1.reduceLeft((x, y) => x+y)
ls1.reduceLeft((x, y) => x-y)

// fold() 方法 类似于reduceleft()方法  在括号内自定义函数
val fo1 = List(1,9,2,8)
val fo2 = fo1.fold(10)((x,y) => x+y)
println(fo2)        // fold方法小括号里的10是写入的初始值,

// 拉链操作
val zipList1 = List("name" , "age" , "sex")
val zipList2 = List("zhangsan", 28)
val zip1 = zipList1 zip zipList2
// 等同于 zipList1.zip(zipList2)
println("zip1:"+zip1)         // 得到的list名为zip1集合的
println("zipList1:"+zipList1)     // 元素类型是元组类型
println("zipList2:"+zipList2)

// 拉链操作很适合转成Map集合 toMap 方法
val zipMap1 : Map[String , Any ] = zip1.toMap
println("zipMap1:"+zipMap1)


// 迭代器遍历集合
val list11 = List(1,2,"zhangsan")
val s1 : Iterator[Any] = list11.iterator
while(s1.hasNext){
  println("s1:"+s1.next())
}

for (x <-list11){
  println("x:"+x)
}

list11.productIterator.foreach(y => println("y:"+y))