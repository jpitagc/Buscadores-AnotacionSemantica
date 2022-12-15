package src;

import es.uc3m.miaa.utils.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.util.Set;
import java.util.HashSet;
import java.util.Vector;

public class Annotator {
    
    public static void main(String[] args) throws Exception{

        String defaultClass = "<#webPage>";
        List<String> allLines = new ArrayList<>();
        GoogleSearch google = new GoogleSearch();

        boolean rs = GoogleSearch.startGoogle();
        if (!rs){throw new Exception("Failed to start gogole.");}

        if(args.length == 0 || args.length > 3){throw new Exception("Invalid number of arguments");}
        if(!args[0].contains(".txt")){throw new Exception("The format of file is not valid");}
        if(args.length > 1 && args[1].equalsIgnoreCase("-c")){
            defaultClass = args[2];
        }
        try {
			
            allLines = Files.readAllLines(Paths.get(args[0]));

		} catch (Exception e) {
			throw new Exception("Error reading file: " + e);
		}

        String outputFileName = args[0].replace(".txt","").concat(".ttl");
        MyGATE myGate = MyGATE.getInstance();
        try{
            File outputFile = new File(outputFileName);
        } catch(Exception e){ throw new Exception("Error creating file: " + e);}

         try{
            FileWriter outputFileWriter = new FileWriter(outputFileName);
            Set<String> allFoundLocations = new HashSet<String>();
            for (String webPage : allLines){

                System.out.println("Annotating: "+webPage);
                outputFileWriter.write("<"+webPage+"> rdf:type "+defaultClass+"\n");
                String fileContent = "";

               
                byte[] fileBytes = Files.readAllBytes(Paths.get(webPage.replace("file:", "./src/tests/")));
                fileContent= new String(fileBytes);
               

                List<Entity> entities = myGate.findEntities(fileContent);
                Set<String> specificFoundLOcations = new HashSet<String>();

                for(Entity thisEntity : entities){
                    if(thisEntity.getType() == "Location"){
                        String params = "&num=" + 1;
                        Vector<Instance> results = google.doWikipediaSearch(thisEntity.getText(),params);
                        if(results.size() > 0){
                            specificFoundLOcations.add(results.get(0).getId());
                            allFoundLocations.add(results.get(0).getId());
                        }
                        
                    }
                }
                for(String foundURL : specificFoundLOcations) {
                    outputFileWriter.write("<"+webPage+"> <#mentionsInstance> "+ "<" +foundURL+ ">."+"\n");
                }
            }
            for(String location: allFoundLocations){
                outputFileWriter.write("<"+location+"> a dcterms:Location.\n");
            }
            
            outputFileWriter.close();
         }catch(Exception e){ throw new Exception("Error writting to file: " + e);}
         rs = GoogleSearch.stopGoogle();
    }
}

