package com.HelpManager.holders;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class YearWeeksHolder {
   private Hashtable<Integer, Boolean> weeks;
   
   public YearWeeksHolder()
   {
	   weeks = new Hashtable<Integer, Boolean>();
   }
   
   public void setIndex(int weekNumber, boolean isWeekScheduled)
   {
	   weeks.put(weekNumber, isWeekScheduled);
   }
   
   public Map<Integer, Boolean> getIndex(int key)
   {
	   Map<Integer, Boolean> aWeek = new HashMap<Integer, Boolean>();
	   if(weeks.containsKey(key))
	  {
		aWeek.put(key, weeks.get(key));
	  }
	   
	   return aWeek;
   }
   
   public boolean getKeyValue(int key)
   {
	   return weeks.get(key);
   }
   
   public void removeIndex(int key)
   {
	   if(weeks.containsKey(key))
		   weeks.remove(key);
   }
   
   public int getKey(int key)
   {
	   if(weeks.containsKey(key))
	   return key;
	   else
		   return -1;
   }
   public int size()
   {
	   return weeks.size();
   }
}
