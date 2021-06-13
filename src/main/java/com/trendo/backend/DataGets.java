
package com.trendo.backend;

//spring imports
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
//unirest imports
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

@Entity
public class DataGets {

	private @Id @GeneratedValue Long id;
	private String ethPrice;
	private String dogePrice;
	private String neoPrice;
	private String linkPrice;
	private String eosPrice;
	// currencies identifiers
	public static String uuidEth = "e991ba77-d384-48ff-b0a4-40e95ef6b7d6";
	public static String uuidDOGE = "7bb2339e-b6eb-408c-836f-2894c8751c6d";
	public static String uuidNEO = "ccc44498-322c-4b6e-9977-cb1eba92f463";
	public static String uuidLINK = "bf5421f5-59ea-4e90-914b-da89e384df37";
	public static String uuidEOS = "ab8ac321-082b-4231-a31c-4c938cfceab7";

	private DataGets() {
	}

	// calling prices
	public DataGets(String ethPrice, String dogePrice, String neoPrice, String linkPrice, String eosPrice)
			throws Exception {
		this.ethPrice = getPrice(ethPrice);
		this.dogePrice = getPrice(dogePrice);
		this.neoPrice = getPrice(neoPrice);
		this.linkPrice = getPrice(linkPrice);
		this.eosPrice = getPrice(eosPrice);
	}

	// Getters
	public Long getId() {
		return id;
	}

	public String getEthPrice() {
		return ethPrice;
	}

	public String getDogePrice() {
		return dogePrice;
	}

	public String getNeoPrice() {
		return neoPrice;
	}

	public String getLinkPrice() {
		return linkPrice;
	}

	public String getEosPrice() {
		return eosPrice;
	}

	// Creating response
	public static HttpResponse<JsonNode> createResponse(String identifier) throws Exception {

		// General Variables for api
		String apiKey = "5dcc767e21msh6eb86612ad393aep192d68jsn24088a077eae";
		String apiHost = "bravenewcoin.p.rapidapi.com";
		String host = "https://bravenewcoin.p.rapidapi.com/market-cap?assetId=";
		// This one changes each 24h @getToken
		String token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik5EVXhNRGhHT0VReE56STVOelJCTTBJM1FrUTVOa0l4TWtRd1FrSTJSalJFTVRaR1F6QTBOZyJ9.eyJpc3MiOiJodHRwczovL2F1dGguYnJhdmVuZXdjb2luLmNvbS8iLCJzdWIiOiJvQ2RRb1pvSTk2RVJFOUhZM3NRN0ptYkFDZkJmNTVSWUBjbGllbnRzIiwiYXVkIjoiaHR0cHM6Ly9hcGkuYnJhdmVuZXdjb2luLmNvbSIsImlhdCI6MTYyMzUyODExOCwiZXhwIjoxNjIzNjE0NTE4LCJhenAiOiJvQ2RRb1pvSTk2RVJFOUhZM3NRN0ptYkFDZkJmNTVSWSIsInNjb3BlIjoicmVhZDppbmRleC10aWNrZXIgcmVhZDpyYW5raW5nIHJlYWQ6bXdhIHJlYWQ6Z3dhIHJlYWQ6YWdncmVnYXRlcyByZWFkOm1hcmtldCByZWFkOmFzc2V0IHJlYWQ6b2hsY3YgcmVhZDptYXJrZXQtY2FwIiwiZ3R5IjoiY2xpZW50LWNyZWRlbnRpYWxzIn0.OMA_YhY1xkuZih7AQifsY0H4vCG7YW_pESNhBY9vQT07SQV0yazf2yt07A_NCFqdl9azMjbC2W9iO8AEMl1_4OFNqDXvhpDxY7nzScbrUkL_Urh49IMwQcf46as4E8jKr4EzAIrj_fIqV83lEXGxR7wQLWHcqTF_Vm1tgLMoo5MSYqdaYgnb4F-7O5q4fjBB_yjMcTbP5lOR6bJ4FQiXzRyB4QcknUV0SOXbveLGpX330WFGT4u5qkQ2VywjZT-O3EAmFvoKd5GixCsxNjicEklQc2Yi4yABWvzrCr03Bn6VFyUeAui9IrEfBGMKJcqLBo2t1mcRJzJlIa18He0x_g";

		// Requesting response
		HttpResponse<JsonNode> response = Unirest.get(host + identifier).header("authorization", "Bearer " + token)
				.header("x-rapidapi-key", apiKey).header("x-rapidapi-host", apiHost).asJson();
		// returnign response
		return response;
	}

	// Getting currency price
	public static String getPrice(String identifier) throws Exception {
		// creating response
		HttpResponse<JsonNode> response = createResponse(identifier);
		// Price as string
		String price = response.getBody().getArray().getJSONObject(0).optJSONArray("content").getJSONObject(0)
				.optString("price");
		// Returning rounded price as a string
		double conversion = Double.parseDouble(price);
		double rounded = Math.round(conversion * 100.0) / 100.0;
		String roundedPrice = String.valueOf(rounded);
		// System.out.println(roundedPrice);
		return roundedPrice;

	}

}
