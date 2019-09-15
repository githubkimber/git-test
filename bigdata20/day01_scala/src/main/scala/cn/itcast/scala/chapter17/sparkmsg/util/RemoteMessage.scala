package cn.itcast.scala.chapter17.sparkmsg.util

trait RemoteMessage extends Serializable{

}

//worker向master发送注册信息，由于不在同一进程中，需要实现序列化
case class RegisterMessage(val workerId:String,val memory:Int,val cores:Int) extends RemoteMessage
//master向worker发送信息，由于不在同一进程中，需要实现序列化
case class RegisteredMessage(val message:String) extends RemoteMessage
//worker向worker发送心跳信息，由于在同一进程中，不需要实现序列化
case object HeartBeat
//worker向master发送心跳，由于不在同一进程中，需要实现序列化
case class SendHeartBeat(val workerId:String) extends RemoteMessage
//master 向自己发送信息
case object CheckOutTime