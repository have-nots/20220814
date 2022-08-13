package Util;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class Config {
    public String access_token = "MOZ1N67jIcdaqDbSEuW9E1uZw-fQDWtDsINa2WR97kWX8TYTCqmugGka489A8TeHrb2HQbXzIMKum31F3Aa5vNWEIsNTTDEDRLZPUUkSAr_hNAhfxX0UKzoeJ7MjDcPcBRRU7j9hnNenGO1chAzVT2LpJXE6RprlHqV366SdF4J58zQfGmREcbxywzN7W_id9iMKlTtZED76oFxH2v0EWA";


    public String corpid = "wwef3b2cbcc809f1b1";
    public String corpsecret="LhIVT-anC2FcjfZV6omLak6M-u0Z1VZbXSJYd9S0F08";
    public static Config config;
    public static Config getInstance(){
        if(config==null){
            config = new Config();
        }
        return config;
    }
    public static void load(String path){
        //从文件中读配置文件
    }
    public Config getToken (){

        String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        this.access_token = given().log().all()
                // given 设置测试预设（请求头、请求参数、请求体等等）
                .header("Content-Type", "application/json")
                .queryParam("corpid", Config.getInstance().corpid)
                .queryParam("corpsecret", Config.getInstance().corpsecret)
                .when()
                // when 所要执行的请求动作
                .post(url)
                .then()
                // then 解析结果、断言
                .log().all()  // 打印全部响应信息（响应头、响应体、状态等等）
                .statusCode(200).body("errcode",equalTo(0))
                .extract().path("access_token");
        return this;
    }
}
