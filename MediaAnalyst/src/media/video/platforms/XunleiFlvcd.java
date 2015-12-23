package media.video.platforms;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;

import media.entity.Video;
import media.flvcd.Flvcd;

import org.htmlparser.NodeFilter;
import org.htmlparser.filters.LinkStringFilter;
import org.htmlparser.util.ParserException;

public class XunleiFlvcd extends Flvcd {
	
	protected String[] formats = { "", "normal", "high", "super" };
	protected String[] types = { "flv", "mp4", "hd2", "hd4" };

	@Override
	public Video getVideo(String playPageLink) throws ParserException,
			IOException, ParseException {
		// TODO Auto-generated method stub
		if(!playPageLink.contains("vod")){
			return null;
		}
		XunleiVideo video = new XunleiVideo(playPageLink);
		NodeFilter filter = new LinkStringFilter("cdn_transfer");
		for (int i = 0; i < video.getTypeformats().size(); ++i) {
			HashMap<Integer, String> urls = getVideoLinks(playPageLink,
					video.getTypeformats().get(i), filter);
			video.setDownloadUrls(types[i], urls);
		}
		return (Video)video;
	}

}
