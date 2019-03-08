package com.dinfo.bdms.utils;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by melman on 27/09/16.
 */
public class OKManager {

	private OkHttpClient okClient;

	public OKManager() {
		okClient = new OkHttpClient().newBuilder()
				.followRedirects(false)
				.followSslRedirects(false)
				.build();
	}

	public void post(String url, RequestBody body, Object tag, Callback callback) {
		Request request = new Request.Builder().url(url).post(body).tag(tag).build();
		Call call = okClient.newCall(request);
		call.enqueue(callback);
	}

	public void get(String url, Object tag, Callback callback) {
		Request request = new Request.Builder().tag(tag).url(url).get().build();
		Call call = okClient.newCall(request);
		call.enqueue(callback);
	}

	public Response syncGet(String url, String cookie) throws IOException {
		Request request = null;
		if(cookie != null && cookie.length() != 0) {
			request = new Request.Builder()
					.addHeader("Cookie", cookie)
					.url(url).get().build();
		} else {
			request = new Request.Builder()
					.url(url).get().build();
		}
		Response response = okClient.newCall(request).execute();
		if(response.code() == 302) {
			response = syncGet(response.header("Location"), cookie);
		}
		return response;
	}

	public Response syncPost(String url, RequestBody body) throws IOException {
		Request request = new Request.Builder().url(url).post(body).build();
		return okClient.newCall(request).execute();
	}

	public Response syncPost(String url, String cookie, Map<String, String> paramMap) throws IOException {
		FormBody.Builder bodyBuilder = new FormBody.Builder();
		if(paramMap != null && paramMap.size() != 0) {
			for(String key : paramMap.keySet()) {
				bodyBuilder.add(key, paramMap.get(key));
			}
		}
		RequestBody body = bodyBuilder.build();

		Request request = null;
		if(cookie != null && cookie.length() != 0) {
			request = new Request.Builder()
					.addHeader("Cookie", cookie)
					.url(url).post(body).build();
		} else {
			request = new Request.Builder()
					.url(url).post(body).build();
		}

		return okClient.newCall(request).execute();
	}
}
