package testcases;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.TestDataBuild;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class GoogleMap extends Base {
	
	public static String placeId;
	RequestSpecification req;
	ResponseSpecification resp;
	Response response;
	TestDataBuild tdb = new TestDataBuild();
	

	@Test(priority=1)
	public void addNewPlace() throws Exception {
		
		req = given().spec(reqSpecification()).body(tdb.addPlaceBuild());
		resp = req.then().spec(respSpecification());
		response = req.when().post(getAPIResource("addPlaceAPI"));
		placeId = getJsonPath(response,"place_id");
		
	}
	
	@Test(priority=2)
	public void updatePlace() throws Exception {
		
		req = given().spec(reqSpecification()).body(tdb.updatePlaceBuild(placeId));
		resp = req.then().spec(respSpecification());
		response = req.when().put(getAPIResource("updatePlaceAPI"));
		
		
	}
	
	@Test(priority=3)
	public void getPlace() throws Exception {
		
		req = given().spec(reqSpecification()).queryParam("place_id", placeId);
		resp = req.then().spec(respSpecification());
		response = req.when().get(getAPIResource("getPlaceAPI"));
		assertEquals(getJsonPath(response,"address"),"70 winter walk, USA");
		
	}

	@Test(priority=4)
	public void deletePlace() throws Exception {
		
		req = given().spec(reqSpecification()).body(tdb.deletePlaceBuild(placeId));
		resp = req.then().spec(respSpecification());
		response = req.when().post(getAPIResource("deletePlaceAPI"));
		assertEquals(getJsonPath(response,"status"),"OK");	
		
		
	}
	
	

}
