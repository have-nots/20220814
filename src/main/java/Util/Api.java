package Util;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

public class Api {
	public void defaultname(){//全局配置
		//RestAssured.useRelaxedHTTPSValidation();
		//RestAssured.proxy("127.0.0.1",8080);//设置代理
		//RestAssured.baseURI="";
	}
	public RequestSpecification defaultReqSpec = given().log().all()
			.contentType(ContentType.JSON).when()
			.queryParam("access_token", Config.getInstance().access_token)
			;
	public ResponseSpecification defaultResSpec= RestAssured.expect()
			.contentType(ContentType.JSON)
			.statusCode(200)
			.time(Matchers.lessThan(5000L))
			.statusLine("HTTP/1.1 200 OK");
	//
    public RequestSpecification requestSpecification = new RequestSpecBuilder()
			.setContentType(ContentType.JSON)
			.addQueryParam("access_token",Config.getInstance().access_token)
			.build();

	public RequestSpecification filterSpec = given().filter((req,res,ctx)->{
		//对请求req和响应res做封装
		res.then().log().all();
		return ctx.next(req,res);});

    public ResponseSpecification responseSpecification= new ResponseSpecBuilder()
			.expectStatusCode(200)
			.expectContentType(ContentType.JSON)
			.expectResponseTime(Matchers.lessThan(5000L))
			.expectStatusLine("HTTP/1.1 200 OK").build();

}
