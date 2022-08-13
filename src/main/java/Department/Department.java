package Department;
import Util.*;
import com.alibaba.fastjson.JSONObject;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
public class Department extends Api{

    public JSONObject randomDepar(int parentid){
        int length = String.valueOf(System.currentTimeMillis()).length();
        String  random = (String.valueOf(System.currentTimeMillis()).substring(length-4,length-1));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",parentid+random);
        jsonObject.put("name_en","N"+random);
        jsonObject.put("parentid",parentid);
        jsonObject.put("order",random);
        jsonObject.put("id",random);

        return jsonObject;
    }


    public Response add( JSONObject body){
        String url="https://qyapi.weixin.qq.com/cgi-bin/department/create";
        return given().spec(defaultReqSpec)
                // given 设置测试预设（请求头、请求参数、请求体等等）
                .body(body.toJSONString())
                // when 所要执行的请求动作
                .post(url)
                .then().spec(defaultResSpec)
                //.body("errmsg",equalTo("created"))
                //.body("id",equalTo(body.getIntValue("id")))
                .extract().response();

    }
    public Response delete(int id){
      String url="https://qyapi.weixin.qq.com/cgi-bin/department/delete";

        return given().spec(defaultReqSpec)
                // given 设置测试预设（请求头、请求参数、请求体等等）
                .header("Content-Type", "application/json")
                .queryParam("access_token", Config.getInstance().access_token)
                .queryParam("id", id)
                .when()
                // when 所要执行的请求动作
                .get(url)
                // then 解析结果、断言
                .then().spec(defaultResSpec).extract().response();

    }

    public Response list(){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/department/simplelist";

        return given().spec(defaultReqSpec)
                // when 所要执行的请求动作
                .get(url)
                .then().spec(defaultResSpec)
                // then 解析结果、断言
                .extract().response();

    }
    public void isVailToken(){
        String url = "https://qyapi.weixin.qq.com/cgi-bin/department/simplelist";
        int flag = given().spec(defaultReqSpec)
                // given 设置测试预设（请求头、请求参数、请求体等等）
                .queryParam("id", 1)
                .when()
                // when 所要执行的请求动作
                .post(url)
                .then().spec(defaultResSpec)
                .extract().path("errcode");

        if(flag != 0) {
            Config token = Config.getInstance().getToken();
        }
    }
}
