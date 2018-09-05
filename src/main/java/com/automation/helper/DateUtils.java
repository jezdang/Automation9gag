package com.automation.helper;

import com.automation.utils.ExtendLog;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {

	/** XPF date format */
	public final static String XPF_DATE_FORMAT = "yyyy-MM-dd";
	public final static String DATE_FORMAT = "dd/MM/yyyy";
	public final static String PERFORMANCE_DATE_FORMAT = "dd/MM/yyyy KK:mm a";

	public static Date inDaysFromDate(final String date, final int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(convertStringToDate(date, DATE_FORMAT));

		calendar.add(Calendar.DATE, numberOfDays);
		return calendar.getTime();
	}

	public static Date inDaysFromDate(final Date date, final int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.add(Calendar.DATE, numberOfDays);
		return calendar.getTime();
	}

	public static Date inMonthsFromDate(final String date, final int numberOfMonths) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(convertStringToDate(date, DATE_FORMAT));

		calendar.add(Calendar.MONTH, numberOfMonths);
		return calendar.getTime();
	}

	public static Date inDaysFromToday(final int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		if (numberOfDays != 0) {
			calendar.add(Calendar.DATE, numberOfDays);
		}
		return calendar.getTime();
	}

	public static String inDaysFromTodayToString(final int numberOfDays) {
		return convertDateToString(inDaysFromToday(numberOfDays));
	}

	public static Date nextMonthFromToday() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	public static Date nextMonthFromDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return calendar.getTime();
	}

	public static String dateInNextMonthToString(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, 1);
		return convertDateToString(calendar.getTime(), DATE_FORMAT);
	}

	public static String getDateString(String day, int xMonthAfterThat, String seperateSign) {
		String monthText =
				convertDateToString(DateUtils.nextXMonthFromToday(xMonthAfterThat), "MM" + seperateSign + "YYYY");
		return String.format("%s%s%s", day, seperateSign, monthText);
	}

	public static Date nextXMonthFromToday(int xMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, xMonth);
		return calendar.getTime();
	}

	public static int getDayOfMonth() {
		Calendar c = Calendar.getInstance();
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static int getDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.DAY_OF_MONTH);
	}

	public static Date inOneWeek() {
		return inDaysFromToday(7);
	}

	public static Date inTwoWeeks() {
		return inDaysFromToday(14);
	}

	public static Date inThreeWeeks() {
		return inDaysFromToday(21);
	}

	public static Date inFourWeeks() {
		return inDaysFromToday(28);
	}

	public static String convertDateToString(Date date, String format) {
		SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(format);
		return mySimpleDateFormat.format(date);
	}

	public static String convertDateToString(Date date) {
		SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		return mySimpleDateFormat.format(date);
	}

	public static Date convertStringToDate(String dateStr, String format) {
		SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(format);
		try {
			return mySimpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	//
	// convert dd/MM/yyyy to dd.MM.yyyy
	public static String convertStringToNewDateFormat(String dateStr, String format) {
		SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			Date formatted = mySimpleDateFormat.parse(dateStr);
			mySimpleDateFormat = new SimpleDateFormat(format);
			return mySimpleDateFormat.format(formatted);
		} catch (ParseException e) {
			return null;
		}
	}

	// Debit function
	// https://jira.secutix.com/browse/TST-506
	public static int debitMonthMovement() {
		Date date1 = DateUtils.inDaysFromToday(15);
		Date date2 = convertStringToDate(getDateString("10", 1, "/"), DATE_FORMAT);

		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		return date1.before(date2) || sdf.format(date1).equals(sdf.format(date2)) ? 1 : 2;
	}

	public static List<String> calculateDebitDateList(int numberOfDate) {
		List<String> dateList = new ArrayList<String>();
		int monthMovement = debitMonthMovement();

		for (int i = 0; i < numberOfDate; i++) {
			dateList.add(getDateString("10", monthMovement + i, "/"));
		}

		ExtendLog.info(String.format("List debit date [%s]", Arrays.toString(dateList.toArray())));

		return dateList;
	}

	public static List<String> calculateDebitDateList(String firstDueDate, int numberOfDate) {
		String date = firstDueDate.substring(0, 2);
		List<String> dateList = new ArrayList<String>();
		int monthMovement = debitMonthMovement();

		for (int i = 0; i < numberOfDate; i++) {
			dateList.add(getDateString(date, monthMovement + i, "/"));
		}

		ExtendLog.info(String.format("List debit date [%s]", Arrays.toString(dateList.toArray())));

		return dateList;
	}

	public static Date firstDayOfNextMonthFromToday() {
		return new LocalDate().plusMonths(1).dayOfMonth().withMinimumValue().toDate();
	}

	public static Date firstDayOfYear() {
		return firstDayOfYear(0);
	}

	public static Date firstDayOfYear(int yearsFromCurrent) {
		return new LocalDate().withDayOfMonth(1).withMonthOfYear(1).plusYears(yearsFromCurrent).toDate();
	}

	public static Date today() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	@SuppressWarnings("boxing")
	public static Integer getMinutesFromMidnight() {
		Calendar calendar = Calendar.getInstance();
		return calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);
	}

	public static Date todayInSpecificMonth(Date date, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH, date.getDate());
		calendar.set(Calendar.MONTH, month);
		return calendar.getTime();
	}

	public static Date inDaysFromDate(final String date, final int numberOfDays, final String dateFormat) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(convertStringToDate(date, dateFormat));

		calendar.add(Calendar.DATE, numberOfDays);
		return calendar.getTime();
	}

	public static Date lastDayFromXMonth(int xMonth) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, xMonth);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(Calendar.DATE, -1);
		return calendar.getTime();
	}

	public static Date nextMonth(int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.MONTH, Calendar.YEAR, day);
		return calendar.getTime();
	}

	public static Date getDateString(String day, int xMonthAfterThat) {
		return convertStringToDate(getDateString(day, xMonthAfterThat, "/"), DATE_FORMAT);
	}

	public static Date convertStringToDate(String dateStr) {
		SimpleDateFormat mySimpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
		try {
			return mySimpleDateFormat.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getTodayString(String format) {
		return new SimpleDateFormat(format).format(Calendar.getInstance().getTime()).toString();
	}

	// Ex: format = "dd.MM.yyyy"
	public static String getDateStringFromToday(int numberOfDays, String format) {
		if (DateUtils.isAfterMidNight()) {
			numberOfDays -= 1;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, numberOfDays);
		return new SimpleDateFormat(format).format(calendar.getTime()).toString();
	}

	public static String getDateFromTodayToXMonth(int numberOfMonths, String format) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH, numberOfMonths);
		return new SimpleDateFormat(format).format(calendar.getTime()).toString();
	}

	public static String getYearAfter(int numberOfYear) {
		return String.valueOf(Calendar.getInstance().get(Calendar.YEAR) + numberOfYear);
	}

	/**
	 * After midnight is 00:00 - 04:00 Because test can run around 00:00. And we have to select a precise date on
	 * screen. We need to calculate the date correctly from current date.
	 */
	public static boolean isAfterMidNight() {
		int hourOfDay = new DateTime().getHourOfDay();
		return hourOfDay >= 0 && hourOfDay <= 4;
	}

}
