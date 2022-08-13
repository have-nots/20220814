package Department;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.util.HashMap;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;

class UserTest {

    @Test
    void addmember() {
        new User().addmember().then().body("errcode",equalTo(0));
        new User().addmember().then().body("errcode",equalTo(0));
        new User().addmember().then().body("errcode",equalTo(0));

    }

    @BeforeAll
    static void beforeAll() {
        new Department().isVailToken();

    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data/test.csv")
    void calcInsurance(int age,int score,int money) {
        System.out.println(age+score+money);
    }
    void add() {
    }

    @Test
    void list() {

    }

    @Test
    void testList() {
        //new User().list(1).then().body("userlist.find{it.name='name'}.userid",equalTo("ChenNian"));
        List<HashMap<String,Object>> list = new User().list(1).then().log().all().extract().path("userlist");
        System.out.println(JSON.toJSONString(list.get(0)));//{"name":"unlearn","department":[1],"userid":"ChenNian"}
        System.out.println(list.get(0).toString());//{name=unlearn, department=[1], userid=ChenNian}
        list.forEach(e-> {
            String str = JSON.toJSONString(e);
            JSONObject parse = (JSONObject) JSONObject.parse(str);
            System.out.println(parse.getString("name"));
        });
    }
}
