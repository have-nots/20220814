package Department;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
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

public class DingDingMessage {

    @Test
    void fun() {
        RestAssured.useRelaxedHTTPSValidation();
        Long timestamp = System.currentTimeMillis();
        String secret = "SEC4d257ef112d2f2c7c268ab4a37d8ae12e454b78bd069c17d6560e7d79dfc0787";

        String stringToSign = timestamp + "\n" + secret;
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] signData = new byte[0];
        try {
            signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String sign = null;
        try {
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(sign);
        String jsonData = "{\"msgtype\":\"text\",\"text\":\"{'content':'TEST我就是我, 是不一样的烟火'}\"}";
        given().log().all()

                // given 设置测试预设（请求头、请求参数、请求体等等）
                .header("Content-Type", "application/json")
                .body(jsonData).contentType(ContentType.JSON)
                .queryParam("timestamp",timestamp)
                .queryParam("sign",sign)//&sign="+sign+"timestamp="+timestamp
                .when()
                // when 所要执行的请求动作
                .post("https://oapi.dingtalk.com/robot/send?access_token=8cf313a202bae78cdb07e7703fa60e9bf6fbd4cd04d77eb4e898a99507d0fa10")
                .then()
                // then 解析结果、断言
                .log().all()  // 打印全部响应信息（响应头、响应体、状态等等）
                .statusCode(200)
                .body("errcode",equalTo(0))
                .body("errmsg",equalTo("ok"))
                .body("errmsg",hasItems("ok"));
    }
}
