package src;


import es.uc3m.miaa.utils.*;

import java.util.Vector;



public class Annotator {

	public ArrayList<String> webpages = new Arraylist<String>();

    public static void main(String[] args) {

    	BufferedReader reader;
        System.out.println("Running annotator command...");
        try{
        	if (args.length()<1 || args[0]==null){
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
            System.out.println("Error reading file: "+e.printStackTrace());
        }
        String defaultClass= args.length()>1 && args[1]!=null?args[1]:"#webPage";
        MyGate myGate = MyGate.getInstance();
        File myFile = new File("test.ttl");
        myFile.createNewFile();
        FileWriter myWriter = new FileWriter(myFile);
    	ArrayList<String> types = new ArrayList<String>();
        for(int i=0; i < webpages.size(); i++ ) {
        	myWriter.write("?"+webpages[i]+" a "defaultClass+" .\n")
        	ArrayList<Entity> entities = myGate.findEntities(webpages[i]);
        	for(int i=0; i < entities.size(); i++ ) {
        		myWriter.write("?"+entities[i].text+" a <#"entities[i].type+"> .\n");
        		if(!types.contains(entities[i].type)) {
        			types.add(entities[i].type);
        		}
        	}
        }
        for(int i = 0; i < types.size();i++) {
        	myWriter.write("?"+types[i]+" rdfs:subPropertyOf <#Entity>+" ; <#named> \""+types[i]+"\" . \n")
        }
        
    }
    
}
