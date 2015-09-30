package pt.thingpink.utils;

import android.content.Context;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TPDateUtils {

	/**
	 * Get the time ago string representation for a given date.
	 * 
	 * @param ctx
	 *            The context.
	 * @param _pubDate
	 *            The date to parse.
	 * @return The time ago string for a given date.
	 */

	public static String getTimepassed(Context ctx, Date _pubDate) {

		String time = "";
		long timePassed = 0;
		timePassed = _pubDate.getTime();

		timePassed = (System.currentTimeMillis()) - timePassed;

		timePassed /= 1000;

		int minutes = ((int) timePassed / 60);
		int hours = ((int) minutes / 60);
		int days = (int) (hours / 24);
		int week = (int) (days / 7);
		int month = (int) (days / 30);
		int year = (int) (days / 365);
		if (year > 0 && year < 2)
			time = year + " " + ctx.getString(R.string.time_passed_year);
		else if (year > 0)
			time = year + " " + ctx.getString(R.string.time_passed_years);
		else if (month > 0 && month < 2)
			time = month + " " + ctx.getString(R.string.time_passed_month);
		else if (month > 0)
			time = month + " " + ctx.getString(R.string.time_passed_months);
		else if (week > 0 && week < 2)
			time = week + " " + ctx.getString(R.string.time_passed_week);
		else if (week > 0)
			time = week + " " + ctx.getString(R.string.time_passed_weeks);
		else if (days > 0 && days < 2)
			time = days + " " + ctx.getString(R.string.time_passed_day);
		else if (days > 0)
			time = days + " " + ctx.getString(R.string.time_passed_days);
		else if (hours > 0 && hours < 2)
			time = hours + " " + ctx.getString(R.string.time_passed_hour);
		else if (hours > 0)
			time = hours + " " + ctx.getString(R.string.time_passed_hours);
		else if (minutes > 0 && minutes < 2)
			time = minutes + " " + ctx.getString(R.string.time_passed_minute);
		else if (minutes > 0)
			time = minutes + " " + ctx.getString(R.string.time_passed_minutes);
		else if (timePassed < 2 && timePassed > 0)
			time = timePassed + " "
					+ ctx.getString(R.string.time_passed_second);
		else if (timePassed < 0)
			time = ctx.getString(R.string.just_now);
		else
			time = timePassed + " "
					+ ctx.getString(R.string.time_passed_seconds);

		return time;
	}

	public static int getAge(int year, int month, int day) {

		Calendar dob = Calendar.getInstance();
		Calendar today = Calendar.getInstance();

		dob.set(year, month, day);

		int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

		if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
			age--;
		}

		return age;
	}

	public static Date stringToDate(String aDate, String aFormat, TimeZone timezone) {

        if (aDate == null)
            return null;

        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat, Locale.getDefault());
        Date stringDate = simpledateformat.parse(aDate, pos);

        long currentTime = System.currentTimeMillis();
        int currentOffset = TimeZone.getDefault().getOffset(currentTime);
        //int serverOffset = TimeZone.getTimeZone("Europe/Lisbon").getOffset(currentTime);
        int serverOffset = timezone.getOffset(currentTime);
        int minutesDifference = (currentOffset - serverOffset) / (1000 * 60);

        Calendar cal = Calendar.getInstance();
        cal.setTime(stringDate);
        cal.add(Calendar.MINUTE, minutesDifference);

        return cal.getTime();

    }

    public static Date stringToDate(String aDate, String aFormat) {

        if (aDate == null)
            return null;

        /*ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat, Locale.getDefault());
        Date stringDate = simpledateformat.parse(aDate, pos);


        return stringDate;*/

		return stringToDate(aDate, aFormat, TimeZone.getTimeZone("GMT"));
	}

	public static String dateToString(Date aDate, String format) {

		if (aDate == null)
			return null;

		DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
		return df.format(aDate);
	}

    public static String dateToString(Date aDate, String format, TimeZone timezone) {

        if (aDate == null)
            return null;

        DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        df.setTimeZone(timezone);
        return df.format(aDate);
    }

	public static Calendar dateToCalendar(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}

	public static String getDateTwitter(String time) {
		DateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.US);

		int daysDifference = getDaysDifferenceTwitter(time);

		if (daysDifference <= 0) {

			long timePassed = 0;

			try {
				Date _pubDate = df.parse(time);
				timePassed = _pubDate.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}

			timePassed = (System.currentTimeMillis()) - timePassed;
			timePassed /= 1000;

			int minutes = ((int) timePassed / 60);
			int hours = ((int) minutes / 60);

			if (hours > 0)
				time = hours + "h";
			else if (minutes > 0)
				time = minutes + "m";
			else
				time = timePassed + "s";

			return time;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("d MMM", Locale.getDefault());
			try {
				Date _pubDate = df.parse(time);
				return sdf.format(_pubDate);

			} catch (ParseException e) {
				e.printStackTrace();
			}
			return time;

		}
	}

	public static String getInstameoDate(String time) {
		long milisec = Long.parseLong(time) * 1000;

		long daysDifference = getDaysDifferenceInstameo(milisec);

		if (daysDifference <= 0) {

			long timePassed = 0;

			Date _pubDate = new Date(milisec);
			timePassed = _pubDate.getTime();
			timePassed = (System.currentTimeMillis()) - timePassed;
			timePassed /= 1000;

			int minutes = ((int) timePassed / 60);
			int hours = ((int) minutes / 60);

			if (hours > 0)
				time = hours + "h";
			else if (minutes > 0)
				time = minutes + "m";
			else
				time = timePassed + "s";

			return time;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("d MMM");
			Date _pubDate = new Date(milisec);
			return sdf.format(_pubDate);

		}
	}

	@SuppressWarnings("deprecation")
	public static long getDaysDifferenceInstameo(Long date) {
		Date _pubDate = new Date(date);

		int timePassed;
		if (_pubDate.getMonth() != (new Date()).getMonth()) {
			timePassed = (int) (((new Date()).getTime() - date) / 86400000);
		} else
			timePassed = (new Date().getDate()) - _pubDate.getDate();

		return timePassed;

	}

	@SuppressWarnings("deprecation")
	public static int getDaysDifference(String date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

		int timePassed = 0;
		Date _pubDate = null;
		try {
			_pubDate = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}

		timePassed = _pubDate.getDate();
		if (_pubDate.getMonth() != (new Date()).getMonth()) {
			timePassed = (int) (((new Date()).getTime() - _pubDate.getTime()) / 86400000);
		} else
			timePassed = (new Date().getDate()) - timePassed;

		return timePassed;

	}

	public static int getDaysDifferenceTwitter(String date) {
		DateFormat df = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy", Locale.US);

		int timePassed = 0;
		Date _pubDate = null;
		try {
			_pubDate = df.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
			return -1;
		}

		timePassed = _pubDate.getDate();
		if (_pubDate.getMonth() != (new Date()).getMonth()) {
			timePassed = (int) (((new Date()).getTime() - _pubDate.getTime()) / 86400000);
		} else
			timePassed = (new Date().getDate()) - timePassed;

		return timePassed;

	}

	public static String formateDateFromstring(String inputFormat, String outputFormat, String inputDate) {

		Date parsed = null;
		String outputDate = "";

		SimpleDateFormat df_input = new SimpleDateFormat(inputFormat, java.util.Locale.getDefault());
		SimpleDateFormat df_output = new SimpleDateFormat(outputFormat, java.util.Locale.getDefault());

		try {
			parsed = df_input.parse(inputDate);
			outputDate = df_output.format(parsed);

		} catch (ParseException e) {

		}

		return outputDate;

	}
}
