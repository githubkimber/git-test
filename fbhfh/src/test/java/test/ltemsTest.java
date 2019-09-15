package test;

import dao.ltemsDao;
import domain.ltems;
import impl.ltemsDaolmpl;
import org.junit.Test;

import java.util.List;

public class ltemsTest {

    @Test
    public void findAll() throws  Exception{

        ltemsDao itemsDao = new ltemsDaolmpl() ;
        List<ltems> list = itemsDao.findAll() ;
        for (ltems itmes : list){
            System.out.println(itmes.getUsername());
        }
    }
}
