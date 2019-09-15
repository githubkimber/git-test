package cn.itcast.taxi

import com.esri.core.geometry.{Geometry, GeometryEngine}
import org.json4s.JsonAST.JObject
import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read , write}
/*
{"type": "FeatureCollection", "features": [

{"type": "Feature",
 "id": 0,
  "properties":
{"boroughCode": 5, "borough": "Staten Island", "@id": "http:\/\/nyc.pediacities.com\/Resource\/Borough\/Staten_Island"},
"geometry": {"type": "Polygon", "coordinates": [[
[- 74.050508064032471, 40.566422034160816], [- 74.049983525625748, 40.566395924928273],
[- 74.049316403620878, 40.565887747780437], [- 74.049236298420453, 40.565362736368101],
[- 74.050026201586434, 40.565318180621134], [- 74.050906017050892, 40.566094342130597],
[- 74.050679167486138, 40.566310845736403], [- 74.05107159803778, 40.566722493397798],
[- 74.050508064032471, 40.566422034160816] ]] } }

]}
*/
case class FeatureCollection(features : List[Feature])

case class Feature(properties : Map[String , String] , geometry : JObject){
  def getGeometry() : Geometry = {

    import org.json4s.jackson.JsonMethods._
    // JSON -> Obj
    val mapGeo = GeometryEngine.geoJsonToGeometry(compact(render(geometry)), 0, Geometry.Type.Unknown)
    mapGeo.getGeometry

  }
}

object FeatureExtraction {

  // 完成具体的JSON 解析工作
  def parseJson(json : String) : FeatureCollection = {
    // 1.倒入一个 formats 隐式转换
    implicit val formats = Serialization.formats(NoTypeHints)

    // 2.JSON -> Obj
    val featureCollection = read[FeatureCollection](json)

    featureCollection
  }
}