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

	
	public static String URL = "https://dashboard.paytm.com";
	public static String ResourcePath = "/api/v1/subscription/payment/list";
	public static Map<Integer, Object[]> data = new TreeMap<Integer, Object[]>();
	public static PrintStream pr;
	public static int keys = 0;

	@BeforeTest
	public static void runfetchexcel() throws IOException {
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
