

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Vector;

import es.uc3m.miaa.utils.Entity;
import es.uc3m.miaa.utils.MyGATE;



public class Annotator {

	public static ArrayList<String> webpages = new ArrayList<String>();

    public static void main(String[] args) {

    	BufferedReader reader;
        System.out.println("Running annotator command...");
        try{
        	if (args.length<1 || args[0]==null){
        		System.out.println("Missing file argument"); 
        		System.exit(-1);
        		}
        	reader = new BufferedReader(new FileReader(args[0]));
			String line = reader.readLine();

			while (line != null) {
				System.out.println(line);
				webpages.add(line);
				line = reader.readLine();
			}

			reader.close();
        } catch(Exception e){
            System.out.println("Error reading file: "+e);
        }
        String defaultClass= args.length>1 && args[1]!=null?args[1]:"#webPage";
        MyGATE myGate = MyGATE.getInstance();
        File myFile = new File(args[0].replace(".txt",".ttl"));
        myFile.createNewFile();
        FileWriter myWriter = new FileWriter(myFile);
    	ArrayList<String> types = new ArrayList<String>();
        for(int i=0; i < webpages.size(); i++ ) {
        	myWriter.write("<"+webpages.get(i)+"> a "+defaultClass+" .\n");
        	ArrayList<Entity> entities = myGate.findEntities(webpages.get(i));
        	for(int j=0; j < entities.size(); j++ ) {
        		myWriter.write("<"+entities.get(j).getText()+"> a "+entities.get(j).getType()+" .\n");
        		if(!types.contains(entities.get(j).getType())) {
        			types.add(entities.get(j).getType());
        		}
        	}
        }
        for(int i = 0; i < types.size();i++) {
        	myWriter.write("<"+types.get(i)+"> rdfs:subPropertyOf <#Entity>"+" ; <#named> \""+types.get(i)+"\" . \n");
        }
        
    }
    
}
