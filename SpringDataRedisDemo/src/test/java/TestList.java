import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring/applicationContext-redis.xml")
public class TestList {

    @Autowired
    private RedisTemplate redisTemplate;

    /*
    右压栈: 后添加的元素排在后边
     */
    @Test
    public void testSetValue1(){
        redisTemplate.boundListOps("namelist1").rightPush("刘备");
        redisTemplate.boundListOps("namelist1").rightPush("关羽");
        redisTemplate.boundListOps("namelist1").rightPush("张飞");
    }

    /*
    显示压栈的值
     */
    @Test
    public void testGetvalue1(){
        List list = redisTemplate.boundListOps("namelist1").range(0,10) ;
        System.out.println(list);
    }

    /*
    左压栈: 后添加的元素排在前边
     */
    @Test
    public void testSetValue2(){
        redisTemplate.boundListOps("namelist2").leftPush("刘备");
        redisTemplate.boundListOps("namelist2").leftPush("关羽");
        redisTemplate.boundListOps("namelist2").leftPush("张飞");
    }

    /*
    显示压栈的值
     */
    @Test
    public void testGetvalue2(){
        List list = redisTemplate.boundListOps("namelist2").range(0,10) ;
        System.out.println(list);
    }

    /*
    按索引位置获取某个元素; index:第几个元素;
     */
    @Test
    public void searchByIndex(){
        String str = (String) redisTemplate.boundListOps("namelist2").index(1);
        System.out.println(str);
    }

    /*
    删除元素; index:被删除元素个数;
     */
    @Test
    public void removeValue(){
        redisTemplate.boundListOps("namelist1").remove(1,"张飞") ;
    }
}