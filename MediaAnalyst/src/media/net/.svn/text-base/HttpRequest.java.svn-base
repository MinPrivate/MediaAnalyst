package media.net;

import java.io.IOException;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class HttpRequest {

	public HttpResult get(String url) throws ClientProtocolException,
			IOException {
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse response = httpClient.execute(httpGet);
		int code = response.getStatusLine().getStatusCode();
		String encoding = parseCharset(response);
		String result = EntityUtils.toString(response.getEntity(), encoding);
		return new HttpResult(code, result);
	}

	public HttpResult get(String url, List<NameValuePair> params)
			throws ClientProtocolException, IOException {
		url += "?";
		for (NameValuePair param : params) {
			url += param.getName() + "=" + param.getValue() + "&";
		}
		url = url.substring(0, url.length() - 1);
		return get(url);
	}

	@SuppressWarnings("deprecation")
	public HttpResult post(String url, List<NameValuePair> params)
			throws IOException {
		HttpPost httpPost = new HttpPost(url);
		HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
		httpPost.setEntity(entity);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResp = httpClient.execute(httpPost);
		int statusCode = httpResp.getStatusLine().getStatusCode();
		String encoding = parseCharset(httpResp);
		String result = EntityUtils.toString(httpResp.getEntity(), encoding);
		return new HttpResult(statusCode, result);
	}

	private String parseCharset(HttpResponse httpResp) {
		String charset = "UTF-8";
		Header[] headers = httpResp.getAllHeaders();
		for (Header header : headers) {
			if (header.getName().equalsIgnoreCase("Content-Encoding")) {
				charset = header.getValue();
				break;
			}
		}
		return charset;
	}

	public class HttpResult {
		public int code;
		public String textResult;

		public HttpResult(int code, String result) {
			this.code = code;
			this.textResult = result;
		}
	}
}
