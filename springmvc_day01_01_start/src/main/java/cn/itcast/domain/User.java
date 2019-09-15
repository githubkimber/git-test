package cn.itcast.domain;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable{

    private String uanme ;
    private Integer age ;

    private Date date;

    @Override
    public String toString() {
        return "User{" +
                "uanme='" + uanme + '\'' +
                ", age=" + age +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUanme() {
        return uanme;
    }

    public void setUanme(String uanme) {
        this.uanme = uanme;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
