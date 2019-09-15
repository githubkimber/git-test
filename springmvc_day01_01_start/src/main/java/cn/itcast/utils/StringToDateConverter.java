package cn.itcast.utils;



import org.springframework.core.convert.converter.Converter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
把字符串转换成日期
 */
public class StringToDateConverter implements Converter<String,Date> {

    /*

     */
    @Override
    public Date convert(String source) {

        // 如果数据为空
        if (source == null){
            throw new RuntimeException("请您传入数据") ;      // RuntimeException:运行时异常
        }
        DateFormat df = new SimpleDateFormat("yyyy,mm,dd") ;
        try {
            // 把字符串转换日期
            return df.parse(source);
        }catch(Exception e){
            throw new RuntimeException("数据类型转错误") ;
        }
    }
}
