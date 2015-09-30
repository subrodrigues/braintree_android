package pt.thingpink.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TPTextUtils {

	public static String getYoutubeVideoId(String youtubeUrl) {

		String video_id = "";
		youtubeUrl = youtubeUrl.trim();
		if (youtubeUrl != null && youtubeUrl.length() > 0
				&& youtubeUrl.startsWith("http")) {

			String expression = "^.*((youtu.be"
					+ "\\/)"
					+ "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
			CharSequence input = youtubeUrl;
			Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher(input);
			if (matcher.matches()) {
				String groupIndex1 = matcher.group(7);
				if (groupIndex1 != null && groupIndex1.length() == 11)
					video_id = groupIndex1;
			}
		}
		return video_id;
	}
}
