package fetchPackage;

import static io.restassured.RestAssured.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import payTMPojoSerialization.*;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import payTMPojoDeSerialization.ParentOfResults;
import payTMPojoDeSerialization.Result;

public class ClassToFetchDetails {

	//public static String xsrf = "3672fd7e-02db-4281-940e-81f96f7840a5";
	//public static String Cookie = "_ga=GA1.3.2050952619.1697098668; signalSDKVisitorId=d45d84a0-68d7-11ee-8f30-75336e1c52e7; ext_name=ojplmecpdpgccookcobabopnaifgidhf; _gcl_au=1.1.289037908.1697098672; _hjSessionUser_2137993=eyJpZCI6IjU5NDNkY2M4LTEyZTAtNWJmNC05ZjNiLTRjMzBhYmM0YmIxMyIsImNyZWF0ZWQiOjE2OTcwOTg3MDc2MjYsImV4aXN0aW5nIjp0cnVlfQ==; _gid=GA1.2.2143907395.1700462617; _gid=GA1.3.2143907395.1700462617; _hjIncludedInSessionSample_2137993=0; _hjSession_2137993=eyJpZCI6ImZjNTJkNTcwLTY1MTktNGNkNi05ZDJiLWU1ZDJmNWZhNDQ5OCIsImNyZWF0ZWQiOjE3MDA1NDIwODUxOTYsImluU2FtcGxlIjpmYWxzZSwic2Vzc2lvbml6ZXJCZXRhRW5hYmxlZCI6dHJ1ZX0=; _hjAbsoluteSessionInProgress=1; SESSION=c5d0c816-ed5e-41cc-ab19-0a7516d48735; XSRF-TOKEN=3672fd7e-02db-4281-940e-81f96f7840a5; UMP_SESSION=c5d0c816-ed5e-41cc-ab19-0a7516d48735; _gat=1; _dc_gtm_UA-48995472-3=1; _ga_LSKTYTR270=GS1.1.1700542085.17.1.1700542232.0.0.0; _ga=GA1.2.2050952619.1697098668; _gat_UA-48995472-3=1; _ga_Z4F7L25N51=GS1.2.1700542085.13.1.1700542233.59.0.0; _ga_Z4F7L25N51=GS1.3.1700542085.13.1.1700542233.59.0.0; _ga_NF9V3YTCLK=GS1.2.1700542086.14.1.1700542264.9.0.0";
	public static String URL = "https://dashboard.paytm.com";
	public static String ResourcePath = "/api/v1/subscription/payment/list";
	public static Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
	public static PrintStream pr;
	public static int keys = 0;

	@BeforeTest
	public static void runfetchexcel() {
		FetchDetailsFromExcel.fetchsubscriptionidfromexcel();
	}
	//

	@Test(dataProvider = "fetchSubsctiptionId", dataProviderClass = FetchDetailsFromExcel.class)
	public static void fetchingPaymentDetails(String SubID) throws IOException {
		String xsrf = Utility.fetchFromPropertiesFile("xsrf"); 
		String Cookie = Utility.fetchFromPropertiesFile("Cookie");
		
		
		pr = new PrintStream(new FileOutputStream("Logging.text"), true);

		ParentOfRequest pfr = new ParentOfRequest();

		

		ArrayList<String> statue = new ArrayList<String>();
		statue.add("INIT");
		statue.add("ACTIVE");
		statue.add("INACTIVE");
		statue.add("CLOSED");
		statue.add("FAIL"); // */*
		pfr.setStatue(statue);
		pfr.setFromDate("2022/06/15");
		pfr.setSubscriptionId(SubID);

		SessionFilter sf = new SessionFilter();
		RequestSpecification BaseReq = new RequestSpecBuilder().addFilter(RequestLoggingFilter.logRequestTo(pr))
				.addFilter(ResponseLoggingFilter.logResponseTo(pr)).setBaseUri(URL).setContentType(ContentType.JSON)
				// .addHeader("Content-Length","111")
				.addHeader("Host", "dashboard.paytm.com").addHeader("authority", "dashboard.paytm.com")
				.addHeader("cookie", Cookie).addHeader("origin", "https://dashboard.paytm.com")
				.addHeader("referer", "https://dashboard.paytm.com/next/subscriptions").addHeader("x-xsrf-token", xsrf)
				.addFilter(sf).build();

		ParentOfResults parentOfResults = given().spec(BaseReq).urlEncodingEnabled(false).body(pfr).when()
				.post(ResourcePath).then().assertThat().statusCode(200).extract().as(ParentOfResults.class);

		List<Result> resultdata = parentOfResults.getResults().getResult();
		int size = resultdata.size();

		for (int a = 0; a < size; a++) {

			Result result = resultdata.get(a);

			data.put(keys, new Object[] { result.getSubsId(), result.getOrderId(), result.getTxnAmount(),result.getTimeStamp(),
					result.getStatus() });

			System.out.println("Data for iteration " + keys + ": " + Arrays.toString(data.get(keys)));
			keys++;

		}

		// return data;
	}

	@AfterTest
	public void saveinexcel() throws IOException {

		SaveDetailInExcel.saveDetailsExcel();

	}

}
