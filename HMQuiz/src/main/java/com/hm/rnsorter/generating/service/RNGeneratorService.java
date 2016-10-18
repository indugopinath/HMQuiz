/**
 * 
 */
package com.hm.rnsorter.generating.service;

import java.text.DecimalFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hm.rnsorter.persist.service.IRNPersistService;

/**
 * @author InduGopinath
 *The file is used to generate a new Random number
 */
@Qualifier(value="RNGeneratorService")
@Service
public class RNGeneratorService implements IRNGeneratorService {
	
	@Autowired
	private IRNPersistService persistService;


	/** To generate a new Random number
	 * @see com.hm.rnsorter.Iservice.IRNSortService#generateRandomNumber()
	 */
	@Override
	public Double generateRandomNumber()
	{
		double newRandomnumber=0;
		newRandomnumber=(int)((Math.random() * 9000000)+1000000);
    	DecimalFormat format=new DecimalFormat("###.###");
       	format.format(newRandomnumber);
//       	System.out.println("The random number generated is"+newRandomnumber);
       	
       	if(newRandomnumber!=0)
       	{
       		persistService.persistNewRN(newRandomnumber);
       	}
       	else
       	{
       		System.out.println("Attention!!!The new RN is not saved. Please check");
       	}
       	
		return newRandomnumber;
	}
	
		
}
