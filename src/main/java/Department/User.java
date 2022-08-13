package Department;

import Util.Config;
import Util.ReadJson;
import com.alibaba.fastjson.JSONObject;
import com.jayway.jsonpath.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.path.json.JsonPath.from;
import static org.hamcrest.Matchers.equalTo;

public class User {
    public Response addmember() {
        String ramdon = String.valueOf(System.currentTimeMillis()).substring(5, 10);
        HashMap<String, Object> hashMap = new HashMap<String, Object>(){{
            put("name", "CHEN" + ramdon);
            put("userid", ramdon);
            put("mobile", "+86 138000" + ramdon);
            put("email", ramdon + "@qq.com");
        }};

        String jsonString = new ReadJson().byHashMap("data/addmember.json",hashMap);
        new ReadJson().bySimple("data/addmember.json")
                .set("name", "User" + ramdon)
                .set("userid", ramdon)
                .set("mobile", "+86 138000" + ramdon)
                .set("email", ramdon + "@qq.com").jsonString();
        from(jsonString).getString("userid");
        JSONObject parse = (JSONObject) JSONObject.parse(jsonString);
        Response add = add(jsonString);
        return add;
    }

    public Response add(String body) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/create";
        //https://qyapi.weixin.qq.com/cgi-bin/user/simplelist?access_token=ACCESS_TOKEN&department_id=DEPARTMENT_ID
        // isVailToken();
        return given()
                // given 设置测试预设（请求头、请求参数、请求体等等）
                .header("Content-Type", "application/json")
                .queryParam("access_token", Config.getInstance().access_token)
                .body(body)
                .when()
                // when 所要执行的请求动作
                .post(url)
                .then()
                // then 解析结果、断言
                .log().all()  // 打印全部响应信息（响应头、响应体、状态等等）
                .statusCode(200)
                .extract().response();

    }

    public Response list(int department_id) {
        String url = "https://qyapi.weixin.qq.com/cgi-bin/user/simplelist";
        // isVailToken();
        return given()
                // given 设置测试预设（请求头、请求参数、请求体等等）
                .header("Content-Type", "application/json")
                .queryParam("access_token", Config.getInstance().access_token)
                .queryParam("department_id", department_id)
                .when()
                // when 所要执行的请求动作
                .post(url)
                .then()
                // then 解析结果、断言
                .statusCode(200)
                .extract().response();

    }

}
