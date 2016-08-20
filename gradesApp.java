package course.homework;

import org.bson.Document;

import com.mongodb.Block;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import static com.mongodb.client.model.Sorts.*;
import static com.mongodb.client.model.Filters.*;

public class gradesApp {
	
	public static void main(String[] args) {
	
		 
		 MongoClient client =new MongoClient();
		 MongoDatabase db=client.getDatabase("students");  //create database object
		 MongoCollection<Document> coll=db.getCollection("grades");   //create collection object

		 //this returns all documents in the collection into the findIterable object//
		     FindIterable<Document> iterable1 =coll.find();    		     
		//this will sort the documents first in terms of student id and then score     
		     iterable1= iterable1.sort(ascending("student_id","score"));		  
		//this will return first document from the collection      
		     Document doc=iterable1.first();		     
		   
		 //this returns the student_id of the 1st document as String, which is converted into Integer    
		     Integer id= Integer.decode(doc.get("student_id").toString());
		    		     
		     
		   /*  
		   just go over the iterable collection and if student_id is same as student_id, then delete it and
		   increment the student id.....because the collection is sorted in ascending order...the first different
		   student id will contain lowest score...
		  */   
		         for(Document doc1 :iterable1)
		         {
		             	 if(doc1.get("student_id").equals(id))
		              {		            	
		        		  coll.deleteOne(doc1);//delete the particular document from the collection//
		            	  id++;
		              }	        	 
		         } 		       
		       
	System.out.println(coll.count());  //this must print 600 at the end	        
		       
	}

}
