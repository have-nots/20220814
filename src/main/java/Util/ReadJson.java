package Util;

import Department.User;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import java.util.HashMap;

public class ReadJson {
    String ramdon = String.valueOf(System.currentTimeMillis()).substring(5,10);

    String jsonString = JsonPath.parse(
            User.class.getClassLoader().getResourceAsStream("data/addmember.json"))
            .set("name","User"+ramdon)
            .set("userid",ramdon)
            .set("mobile","+86 138000"+ramdon)
            .set("email",ramdon+"@qq.com").jsonString();
    public DocumentContext bySimple(String file){
        return JsonPath.parse(
                User.class.getClassLoader().getResourceAsStream(file));
    }
    public String byHashMap(String file, HashMap<String,Object> map){
        DocumentContext documentContext = JsonPath.parse(
                User.class.getClassLoader().getResourceAsStream(file));
        map.entrySet().forEach(entry -> {
            documentContext.set(entry.getKey(),entry.getValue());
        });
        return documentContext.jsonString();
    }

}
