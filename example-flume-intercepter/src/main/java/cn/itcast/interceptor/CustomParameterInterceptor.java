package cn.itcast.interceptor;

import com.google.common.base.Charsets;
import org.apache.flume.Context;
import org.apache.flume.Event;
import org.apache.flume.interceptor.Interceptor;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cn.itcast.interceptor.CustomParameterInterceptor.Constants.*;

/**
 * Created by itcast
 */
public class CustomParameterInterceptor implements Interceptor{


    /** The field_separator.指明每一行字段的分隔符 */
    private final String fields_separator;

    /** The indexs.通过分隔符分割后，指明需要那列的字段 下标*/
    private final String indexs;

    /** The indexs_separator. 多个下标的分隔符*/
    private final String indexs_separator;

    /** The encrypted_field_index. 需要加密的字段下标*/
    private final String encrypted_field_index;



    /**
     *3.通过CustomParameterInterceptor的构造器获取到配文件中的参数;
     */
    public CustomParameterInterceptor( String fields_separator,
                                       String indexs, String indexs_separator,String encrypted_field_index) {
        String f = fields_separator.trim();
        String i = indexs_separator.trim();
        this.indexs = indexs;
        this.encrypted_field_index=encrypted_field_index.trim();
        if (!f.equals("")) {
            f = UnicodeToString(f);
        }
        this.fields_separator =f;
        if (!i.equals("")) {
            i = UnicodeToString(i);
        }
        this.indexs_separator = i;
    }

	/*
	 *
	 * \t 制表符 ('\u0009')
	 *
	 */

    public static String UnicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");
        }
        return str;
    }

    /*
     * 4.该方法就是自定义拦截器最终具体功能实现的所在
     */
    public Event intercept(Event event) {
        if (event == null) {
            return null;
        }
        try {
            String line = new String(event.getBody(), Charsets.UTF_8);
            String[] fields_spilts = line.split(fields_separator);
            String[] indexs_split = indexs.split(indexs_separator);
            String newLine="";
            // 遍历需要保留下来的下标数组 0,1,3,5,6
            for (int i = 0; i < indexs_split.length; i++) {
                int parseInt = Integer.parseInt(indexs_split[i]);
                // 匹配到加密的字段,对加密字段进行加密
                if(!"".equals(encrypted_field_index)&&encrypted_field_index.equals(indexs_split[i])){
                    newLine+=StringUtils.GetMD5Code(fields_spilts[parseInt]);
                }else{
                    newLine+=fields_spilts[parseInt];
                }

                // 指定最终输出的字符串之间字段分隔符
                if(i!=indexs_split.length-1){
                    newLine+=fields_separator;
                }
            }
            // 把修改之后的数据添加到event body中
            event.setBody(newLine.getBytes(Charsets.UTF_8));
            return event;
        } catch (Exception e) {
            return event;
        }
    }

    /*
     * 3. event 批处理犯方法,最终需要调用intercept(event);
     */
    public List<Event> intercept(List<Event> events) {
        List<Event> out = new ArrayList<Event>();
        for (Event event : events) {
            Event outEvent = intercept(event);
            if (outEvent != null) {
                out.add(outEvent);
            }
        }
        return out;
    }

    /*
     * @see org.apache.flume.interceptor.Interceptor#initialize()
     */
    public void initialize() {


    }

    /*
     * @see org.apache.flume.interceptor.Interceptor#close()
     */
    public void close() {


    }



    public static class Builder implements Interceptor.Builder {

        /** The fields_separator.指明每一行字段的分隔符 */
        private  String fields_separator;

        /** The indexs.通过分隔符分割后，指明需要那列的字段 下标*/
        private  String indexs;

        /** The indexs_separator. 多个下标下标的分隔符*/
        private  String indexs_separator;

        /** The encrypted_field. 需要加密的字段下标*/
        private  String encrypted_field_index;

        /*
         * 1.通过flume context 上下文环境变量读取采集方案中配置的属性,如果没有配置,由默认值实现;
         */
        public void configure(Context context) {
            fields_separator = context.getString(FIELD_SEPARATOR, DEFAULT_FIELD_SEPARATOR);
            indexs = context.getString(INDEXS, DEFAULT_INDEXS);
            indexs_separator = context.getString(INDEXS_SEPARATOR, DEFAULT_INDEXS_SEPARATOR);
            encrypted_field_index= context.getString(ENCRYPTED_FIELD_INDEX, DEFAULT_ENCRYPTED_FIELD_INDEX);

        }

        /*
         * 2.通过build方法 把解析的参数传递给自定义拦截器的实例对象;
         */
        public Interceptor build() {

            return new CustomParameterInterceptor(fields_separator, indexs, indexs_separator,encrypted_field_index);
        }
    }




    /**
     * The Class Constants.
     *
     */
    public static class Constants {
        /** The Constant FIELD_SEPARATOR. */
        public static final String FIELD_SEPARATOR = "fields_separator";

        /** The Constant DEFAULT_FIELD_SEPARATOR. */
        public static final String DEFAULT_FIELD_SEPARATOR =" ";

        /** The Constant INDEXS. */
        public static final String INDEXS = "indexs";

        /** The Constant DEFAULT_INDEXS. */
        public static final String DEFAULT_INDEXS = "0";

        /** The Constant INDEXS_SEPARATOR. */
        public static final String INDEXS_SEPARATOR = "indexs_separator";

        /** The Constant DEFAULT_INDEXS_SEPARATOR. */
        public static final String DEFAULT_INDEXS_SEPARATOR = ",";

        /** The Constant ENCRYPTED_FIELD_INDEX. */
        public static final String ENCRYPTED_FIELD_INDEX = "encrypted_field_index";

        /** The Constant DEFAUL_TENCRYPTED_FIELD_INDEX. */
        public static final String DEFAULT_ENCRYPTED_FIELD_INDEX = "";

        /** The Constant PROCESSTIME. */
        public static final String PROCESSTIME = "processTime";
        /** The Constant PROCESSTIME. */
        public static final String DEFAULT_PROCESSTIME = "a";

    }



    /**
     * 字符串md5加密
     */
    public static class StringUtils {
        // 全局数组
        private final static String[] strDigits = { "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

        // 返回形式为数字跟字符串
        private static String byteToArrayString(byte bByte) {
            int iRet = bByte;
            // System.out.println("iRet="+iRet);
            if (iRet < 0) {
                iRet += 256;
            }
            int iD1 = iRet / 16;
            int iD2 = iRet % 16;
            return strDigits[iD1] + strDigits[iD2];
        }

        // 返回形式只为数字
        private static String byteToNum(byte bByte) {
            int iRet = bByte;
            System.out.println("iRet1=" + iRet);
            if (iRet < 0) {
                iRet += 256;
            }
            return String.valueOf(iRet);
        }

        // 转换字节数组为16进制字串
        private static String byteToString(byte[] bByte) {
            StringBuffer sBuffer = new StringBuffer();
            for (int i = 0; i < bByte.length; i++) {
                sBuffer.append(byteToArrayString(bByte[i]));
            }
            return sBuffer.toString();
        }

        public static String GetMD5Code(String strObj) {
            String resultString = null;
            try {
                resultString = new String(strObj);
                MessageDigest md = MessageDigest.getInstance("MD5");
                // md.digest() 该函数返回值为存放哈希值结果的byte数组
                resultString = byteToString(md.digest(strObj.getBytes()));
            } catch (NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
            return resultString;
        }
    }

}
