package Util;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.core.IsEqual.equalTo;

public class AccessToken {

        public String getToken (){
            String url = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
            return given().log().all()
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

    }



}
