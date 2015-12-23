package media.flvcd;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import media.config.Configuration;
import media.entity.Audio;
import media.net.HttpRequest;
import media.net.HttpRequest.HttpResult;
import media.net.HttpUtils;

public class AudioFlvcd {
	private final String SONG_LINK_URL = Configuration.get("song_link_url");

	public Audio getAudio(String playPageLink) throws Exception
			 {
		String songId = null;
		if (playPageLink != null) {
			songId = playPageLink.substring(playPageLink
					.indexOf("music.baidu.com/song/")
					+ "music.baidu.com/song/".length());
		}
		return getAudioFromId(songId);
	}

	private Audio getAudioFromId(String songId) throws ParseException,
			IOException {
//		HttpRequest request = new HttpRequest();
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("songIds", songId));
//		params.add(new BasicNameValuePair("hq", "1"));
//		HttpResult result = request.post(SONG_LINK_URL, params);
//		Audio audio = Audio.getAudio(result.textResult);
		String url = SONG_LINK_URL + "?songIds=" + songId + "&hq=1";
		String textResult = HttpUtils.doGet(url, null, "UTF-8", true);
		Audio audio = Audio.getAudio(textResult);
		return audio;
	}

}
