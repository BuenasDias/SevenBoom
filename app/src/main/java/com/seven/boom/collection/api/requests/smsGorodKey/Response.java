package com.seven.boom.collection.api.requests.smsGorodKey;

import com.google.gson.annotations.SerializedName;

public class Response{

	@SerializedName("key")
	private String key;

	public String getKey(){
		return key;
	}
}