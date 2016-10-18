
package com.hm.rnsorter.sorting.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hm.rnsorter.model.RandomNumber;
import com.hm.rnsorter.persist.service.IRNPersistService;

/**
 * @author InduGopinath
 * 
 * The class serves the purpose of Random Number sorting 
 * and exposes the methods in IRNSortServicxe
 */
@Qualifier(value="RNService")
@Service
public class RNSortService implements IRNSortService  {
    
	@Autowired
	private IRNPersistService RNPersistService;
	
		
	/** To sort the array of numbers
	 * @param unsortedList
	 * @return RandomNumber
	 */
	public RandomNumber sortList(double[] unsortedList)
	{
		if(unsortedList!=null && unsortedList.length>0)
	{
		RandomNumber rnObj=new RandomNumber();
		if(unsortedList!=null && unsortedList.length==1)
		{
			rnObj.setSortDuration(0);
			rnObj.setSortedList(unsortedList);
			rnObj.setSwapNumber(0);
		}
		else
		{
			
		long startTime=System.nanoTime();
		
		//Sorting algorithm
		int totalLenght =  unsortedList.length;
		
		  int shifts = 0;
		  for (int i = 1; i < totalLenght; i++) {
		    double toMove = unsortedList[i];
		    int j = i;
		    while (j > 0 && toMove < unsortedList[j - 1]) {
		    	unsortedList[j] = unsortedList[j - 1];
		      --j;
		      ++shifts;
		    }
		    unsortedList[j] = toMove;
		  }
		
		long endTime=System.nanoTime();
		long duration=endTime-startTime;
		
		rnObj.setSortDuration(duration);
		rnObj.setSwapNumber(shifts);
		rnObj.setSortedList(unsortedList);
		}
		
		//Delete the old file with an unsorted entry
		RNPersistService.deletecontent();
		
		//Persist the sorted list
		RNPersistService.persistsortedList(unsortedList);
		
		List<RandomNumber>rnList= new ArrayList<>();
		rnList.add(rnObj);
		//To perisit into the history file
		RNPersistService.persistsortedHistory(rnList);
		
		return rnObj;
		}
	else 
	{
		return null;
	}
}
		

	
	
}
