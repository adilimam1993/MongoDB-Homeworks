package course;

import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class GradeNewSchemaApp {

	
	
	/*
	 *    I like to dedicate this solution to my bathroom. Over the years, early shower in the morning has been my place to ponder about important
	 *    life decisions and future planning.
	 */
	
	public static void main(String[] args) {
	
	

		MongoClient client = new MongoClient();
	   
	     MongoDatabase db=client.getDatabase("school");
	     
	     MongoCollection collection= db.getCollection("students");
	     
	     FindIterable<Document> find= collection.find();
	    
	     
	     /*
	      *  Unfortunately since MongoDb doesn't provide access to array of embedded document fields directly therefore I have  
	      *  to use string manipulation to compute the lowest homework for each document. 
	      *  I have converted each JSON-Document into string, then found index for string "homework, score=", caught the values of both homeworks,      
	      *  then eliminated the lower one using update and pull function.
	      */
	     
	     
	//Iterating through each document in the collection     
	     for(Document doc: find)
	     {
	       String str=doc.toString();	        
	       int index1=str.indexOf("homework, score=");
		   int indexClosingBracket= str.indexOf('}',index1+16);
		       
	       double hw1=Double.parseDouble(str.substring(index1+16, indexClosingBracket));
	       str = str.replaceFirst("homework, score=","");
  
           int index2=str.indexOf("homework, score=");
	      
	      indexClosingBracket= str.indexOf('}',index2+16);
	      
	      
	      double hw2=Double.parseDouble(str.substring(index2+16, indexClosingBracket));
	            
		   
	         if(hw1<hw2)
	          {
	        	  BasicDBObject update = new BasicDBObject("scores", new BasicDBObject("score", hw1));
	        	  collection.updateOne(doc, new BasicDBObject("$pull", update));
	      	      	        	
	          }
	         else
	         {
	        	 
	        	 BasicDBObject update = new BasicDBObject("scores", new BasicDBObject("score", hw2));
		      	 collection.updateOne(doc, new BasicDBObject("$pull", update));
	    	      
	         }
		      

		  
	     }
		
	      
	      
	      
	}

}
