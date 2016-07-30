package LCG.Examples.FullText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import LCG.DB.API.LunarDB;
import LCG.DB.API.LunarTable;
import LCG.RecordTable.StoreUtile.Record32KBytes; 

public class queryGrammaFullTextEngine {

	public static void main(String[] args) throws Exception {
		
	String db_root = "/home/feiben/DBTest/RTSeventhDB";
	
	LunarDB l_db = LunarDB.getInstance();
	String table = "profile"; 
	String column = "comment"; 
		 
	l_db.openDB(db_root);  

	long start_time = System.nanoTime(); 
			
	/*
	 * the query follows the grammar of:
	 * column againt ("keyword1 keyword2 + keywords3 ")
	 * 
	 * where "against" is the reserved keyword, 
	 * and space, comma(,), plus(+) stand for binary relationship of 
	 * keywords of OR,OR,AND.
	 */
	String query = column + " against(\"大家 + 共同\")";
	int latest = 200;
	ArrayList<Record32KBytes> recs  = l_db.getTable(table).queryFullText(query,latest);
			

	long end_time = System.nanoTime();

	double duration_ms  = (end_time - start_time) / 1000000.0 ;
		 
	for(int i=0 ;i<recs.size() ;i++)
	{
		System.out.println(recs.get(i).getID() + ": "+recs.get(i).recData());
	}
			
	System.out.println("full text search has records: " + recs.size());
			 
	System.out.println("Fetching records costs "+ duration_ms + " (ms)"); 
 
	if(l_db!=null)
	{ 
		l_db.save();
		l_db.closeDB(); 
	}
	}
}
