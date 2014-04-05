package com.proserus.stocks.bp.utils;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jfree.data.time.Year;
import org.joda.time.DateTime;
import org.joda.time.Days;

import com.proserus.stocks.bp.model.Filter;

public class DateUtil {

	private static final int NUMBER_OF_DAYS_PER_YEAR = 365;

	static public DateTime getCurrentDate(){
		return new DateTime();
	}
	
	static public DateTime getFilteredStartDate(DateTime date, Filter filter){
		if(filter.isDateFiltered()){
			DateTime start = new DateTime(filter.getYear().getStart());
			if(date.isBefore(start)){
				date = start;
			}
		}
		return date;
	}
	
	static public DateTime getFilteredEndDate(Filter filter){
		if(filter.isDateFiltered()){
			DateTime endOfYear = new DateTime(filter.getYear().getEnd());
			if(endOfYear.isBeforeNow()){
				return endOfYear;
			}
		}
		return getCurrentDate();
	}
	
	static public Year getFilteredYear(DateTime date, Filter filter){//FIXME Year JFree
		return getFilteredYear(new Year(date.getYear()),filter);
	}
	static public Year getFilteredYear(Year year, Filter filter){
		if(filter.isDateFiltered()){
			//TODO fix filter date
			if(year.getYear() < filter.getYear().getYear()){
				year = filter.getYear();
			}
		}
		return year;
	}
	
	static public Year getCurrentYear(){
		return new Year();
	}
	
	static public boolean isFilteredForYear(DateTime year, Filter filter){
		if(filter.isDateFiltered()){
			if(year.getYear() != filter.getYear().getYear()){
				return false;
			}
		}
		return true;
	}
	
	//TODO Better name...
	//It returns the date that should be used, considering to the current filter.
	static public Year getYearForUsablePrice(Filter filter){
		if(filter.isDateFiltered()){
			return (Year)filter.getYear();
		}else{
			return getCurrentYear();
		}
	}
	
	static public double getDaysBetween(DateTime from, DateTime to){
		return Days.daysBetween(from, to).getDays();
	}
	
	static public double getYearsBetween(DateTime from, DateTime to){
		double value = getDaysBetween(from, to);
		if(value==0){
			return 0d;
		}
		return value / NUMBER_OF_DAYS_PER_YEAR;
	}
	
	static public Date stringToToDate(String pattern, String date){
		Format formatter = new SimpleDateFormat(pattern);
		try {
	        return (Date)formatter.parseObject(date);
        } catch (ParseException e) {
        	//TODO not good..
	        return null;
        }
	}
}