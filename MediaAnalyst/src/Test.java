import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import media.net.HttpRequest;
import media.net.HttpRequest.HttpResult;


public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		HttpRequest request = new HttpRequest();
		String url = "http://vsh.video.qq.com/getinfo";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("vids","m00139mfkmr"));
		params.add(new BasicNameValuePair("otype","json"));
		params.add(new BasicNameValuePair("defaultfmt","all"));
		try {
			HttpResult result = request.post(url, params);
			System.out.println(result.textResult);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
