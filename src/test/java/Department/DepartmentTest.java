package Department;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.form;
import static io.restassured.RestAssured.useRelaxedHTTPSValidation;
import static java.lang.Thread.sleep;
import static org.hamcrest.Matchers.equalTo;


class DepartmentTest {

    @BeforeAll
    static void beforeAll() {
        new Department().isVailToken();

    }

    @ParameterizedTest
    @ValueSource(ints = {1})
    void add(int parentid) {

        new Department().add(new Department().randomDepar(parentid)).then().body("errmsg",equalTo("created"));
    }
    @Test
    void s1() {
        String jsonString = JsonPath.parse(User.class.getClassLoader().getResourceAsStream("data/1.json")).jsonString();
        System.out.println(jsonString);
        System.out.println(JsonPath.parse(this.getClass().getResource("/").getPath()));
        System.out.println( this.getClass().getResource("/").getPath());
        System.out.println( this.getClass().getResource("/").getPath());

    }
    @Test
    void delete() {

        JSONObject jsonObject1 = new Department().randomDepar(1);
        new Department().add(jsonObject1).then();
        new Department().delete(jsonObject1.getInteger("id")).then().body("errmsg",equalTo("deleted"));
    }
    void deleteAll(HashSet<Integer>hs,HashSet<Integer>ids){
        Iterator it1 = ids.iterator();
        while(it1.hasNext()){
            new Department().delete(Integer.valueOf((Integer) it1.next()));

        }
        Iterator it2 = hs.iterator();

        while(it2.hasNext()){
            new Department().delete(Integer.valueOf((Integer) it2.next()));

        }
    }
    @Test
    void list() {
        Response list = new Department().list();
        list.then().body("errcode",equalTo(0));
        String str1 = list.asString();//转json
        JSONObject parse = (JSONObject) JSONObject.parse(str1);
        JSONArray jsonArray = parse.getJSONArray("department_id");
      //  jsonArray.stream().forEach(obg  ->  (JSONObject));
        HashSet<Integer> hs = new HashSet<Integer>();//创建HashSet对象
        HashSet<Integer> ids = new HashSet<Integer>();//创建HashSet对象

        for(Object str : jsonArray){
            JSONObject jsonObject= (JSONObject)str;
            hs.add(jsonObject.getInteger("parentid"));
            ids.add(jsonObject.getInteger("id"));

        }
        System.out.println("123:"+hs.toString());
        System.out.println("ids:"+ids.toString());

        ids.removeAll(hs);
        System.out.println("--ids:"+ids.toString());
        hs.remove(1);
        hs.remove(0);
        ids.remove(1);

        deleteAll(hs,ids);
        //winners -> winners.winnerId >= 30 && winners.winnerId < 6
     //   new Department().list().then().log().all().body(
               // "department_id.find{department_id -> department_id.id==1}.order",equalTo(100000000));
       // new Department().list().then().log().all().body("department_id.find{it.id >1 && it.id < 1000}.order",equalTo(11));
       // new Department().list().then().log().all().body("department_id.find{it.id==1}.order",equalTo(100000000));
    }
}
