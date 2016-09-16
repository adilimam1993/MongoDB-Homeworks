package course;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

public class PhotosMongoDBFinal {

	
	 public static void main(String[] args)
	 {
	        MongoClient client=new MongoClient();
	        
	        MongoDatabase photos=client.getDatabase("photo");
	        
	        MongoCollection albums=photos.getCollection("albums");
	        FindIterable<Document> ite1=albums.find();
	        
	        
	        MongoCollection images=photos.getCollection("images");
	        FindIterable<Document> ite2=images.find();  //images
		           
	         
	        
	        
	        
	        
	        System.out.println("Albums count"+albums.count());	        
	        
	        System.out.println("Images count"+images.count());
	        
	        
	        
	        for(Document doc:ite2)  //
	        {
	        	String word=doc.get("_id").toString();
	        	Integer i=Integer.decode(word);
	        	
	        	
	        	//Trying to find if this image exists in the any of the albums
	              FindIterable<Document> find=albums.find(Filters.eq("images",i)); 
	             
	              if(find.first()==null)
	              {	            	  
	            	   images.deleteOne(doc);  
	              }
	              
	               
	        }
	        
	         System.out.println(images.count());
	     
	            FindIterable<Document> find2=images.find(Filters.eq("tags","sunrises"));
	             
	            int count=0;
	            for(Document doc:find2)
	            {
	            	count++; //Count the total images that have a tag called sunrises
	            }
		 
	            System.out.println(count);
	 }
	 
}
