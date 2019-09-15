package cn.itcast.scala.chapter17.sparkmsg.util

//用于保存worker的信息
class WorkerInfo(val workerId:String,val memory:Int,val cores:Int) {

    //定义一个变量。变量用于存放worker的上一次心跳时间
    var lastHeartHeatTime:Long=_

    override def toString: String = {
        s"workerId:$workerId memory:$memory cores:$cores"
    }

}
