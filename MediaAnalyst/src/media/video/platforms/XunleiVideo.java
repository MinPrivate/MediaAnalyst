package media.video.platforms;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import media.entity.Video;
import media.entity.VideoSegment;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

public class XunleiVideo  extends Video{
	
	protected String[] formats = { "", "normal", "high", "super" };
	protected String[] types = { "flv", "mp4", "hd2", "hd4" };
	
	private HashMap<String, Long> videolength;
	private HashMap<String, Long> videoSizes;
	private HashMap<Integer, String> typeformats;
	private int index = 0;

	public XunleiVideo(String playPageLink) throws ParserException, IOException{
		//取得var G_SUBMOVIE_DATA数据块
		URL url = new URL(playPageLink);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		Parser htmlParser = new Parser(connection);
		NodeFilter filter = new TagNameFilter("script");
		NodeList nodes = htmlParser.extractAllNodesThatMatch(filter);
		Pattern pattern = Pattern.compile("var G_SUBMOVIE_DATA = (.+?);");
		String vinfo = null;
		if (nodes != null) {
			for (int i = 0; i < nodes.size(); i++) {
				Node node = nodes.elementAt(i);
				String script = node.toPlainTextString();
				Matcher matcher = pattern.matcher(script);
				if (matcher.find()) {
					vinfo = matcher.group(1);
					break;
				}
			}
		}
		
		HashMap<String, List<VideoSegment>> segs = new HashMap<String, List<VideoSegment>>();
		typeformats = new HashMap<Integer, String>();
		String vid = null;
		//解析信息
		if(vinfo != null){
			vid = vinfo.substring(vinfo.indexOf("submovieid:")+"submovieid:".length(),vinfo.indexOf("multilang")-1);
			String length_rs = vinfo.substring(vinfo.indexOf("length_r:[")+"length_r:[".length(),vinfo.indexOf("size")-2);
			String size_s = vinfo.substring(vinfo.indexOf("size:[")+"size:[".length(), vinfo.indexOf("status")-2);
			String lengths[] = length_rs.split(",");
			String sizes[] = size_s.split(",");
			videolength = new HashMap<String, Long>();
			videoSizes = new HashMap<String, Long>();
			
			for(int i = 0; i < lengths.length; i++){
				long l = Long.parseLong(lengths[i]);
				if(l == 0){
					continue;
				}
				long s = Long.parseLong(sizes[i]);
			
				videolength.put(types[i], l);
				videoSizes.put(types[i], s);
				typeformats.put(index++, formats[i]);
				List<VideoSegment> value = new ArrayList<VideoSegment>();
				VideoSegment v = new VideoSegment(types[i], 0, s, l, null,
						null);
				v.setSegs(1);
				value.add(v);
				
				segs.put(types[i], value);
			}
		}
		
		super.setVideoid(vid);
		super.setSegs(segs);
	}
	
	public HashMap<String, Long> getVideolength(){
		return this.videolength;
	}
	public HashMap<String, Long> getVideoSizes(){
		return this.videoSizes;
	}
	public HashMap<Integer, String> getTypeformats(){
		return this.typeformats;
	}
}
