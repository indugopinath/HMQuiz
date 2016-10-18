package com.hm.rnsorter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hm.rnsorter.generating.service.IRNGeneratorService;
import com.hm.rnsorter.model.RandomNumber;
import com.hm.rnsorter.persist.service.IRNPersistService;
import com.hm.rnsorter.sorting.service.IRNSortService;


/** The class serves as the main generator to random numbers
 * access the services to Generate/sort/persist/retrieve the 
 * 
 * @author InduGopinath
 */
@RestController
public class RNOperationController {
	
	
	/**
	 * The service to Generate Random numbers
	 */
	@Autowired
	private IRNGeneratorService RNGeneratorService;
	
	
	/**
	 * The service to Persist/Retrieve Random numbers
	 */
	@Autowired
	private IRNPersistService RNPersistService;
	
   
    /** The controller method to generate a new Random number
     * @return
     */
    @RequestMapping(value="/getNewRandomNumber",method = RequestMethod.GET)
    public ResponseEntity<Double> getRandomnumber(){
   	double newRandomnumber1=RNGeneratorService.generateRandomNumber();
   	return new ResponseEntity<Double>(newRandomnumber1, HttpStatus.OK);
   	 
    }
    
    
    /** The controller method to retrieve all the random numbers generated
     * @return ResponseEntity
     */
    @RequestMapping(value="/loadAllRandomNumbers",method = RequestMethod.GET)
    public ResponseEntity<RandomNumber> getAllRandomNumbers(){
    	RandomNumber RNList=null;
    	RNList=RNPersistService.getAllRandomNumbers();
     	return new ResponseEntity<RandomNumber>(RNList, HttpStatus.OK);
    }
    
   
    
    /** To get the Random number history
     * @return ResponseEntity
     */
    @RequestMapping(value="/sortingHistory",method = RequestMethod.GET)
    public ResponseEntity<List<RandomNumber>> getRandomNumberHistory(){
    	List<RandomNumber> RNList=null;
    	RNList=RNPersistService.getAllRandomNumbersHistory();
     	return new ResponseEntity<List<RandomNumber>>(RNList, HttpStatus.OK);
    }
}
