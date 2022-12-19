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
import java.net.URL;
import java.util.regex.Pattern;

public class Annotator {
    
    public static void main(String[] args) throws Exception{

        String defaultClass = "<#webPage>";
        List<String> allUrls = new ArrayList<>();
        GoogleSearch google = new GoogleSearch();

        boolean rs = GoogleSearch.startGoogle();
        if (!rs){throw new Exception("Failed to start gogole.");}

        if(args.length == 0 || args.length > 3){throw new Exception("Invalid number of arguments. Expected allway one file and [-C <clase>] if desired");}
        if(!args[0].contains(".txt")){throw new Exception("The format of file is not valid. Input must be .txt");}
        if(args.length > 1 && args[1].equalsIgnoreCase("-c")){
            defaultClass = args[2];
        }else if(args.length > 1) {throw new Exception(" Optional argument is [-C <clase>] to annotate to a class diferent from #webPage");}
        try {
			
            allUrls = Files.readAllLines(Paths.get(args[0]));

		} catch (Exception e) {
			throw new Exception("Error reading file: " + e);
		}

        String outputFileName = args[0].replace(".txt","").concat(".ttl");

        MyGATE myGate = MyGATE.getInstance();

        try{
           new File(outputFileName);
        } catch(Exception e){ throw new Exception("Error creating file: " + e);}

         try{
            FileWriter outputFileWriter = new FileWriter(outputFileName);
            Set<String> allFoundLocations = new HashSet<String>();

            List<String> schemaLines = Files.readAllLines(Paths.get("./src/annotationSchema.ttl"));
            for (String line : schemaLines) {
                 if (Pattern.compile("^@").matcher(line).find()){
                    outputFileWriter.write(line + "\n");
                 };
            }
           
            for (String webPage : allUrls){

                System.out.println("Annotating: "+webPage);
                outputFileWriter.write("<"+webPage+"> rdf:type "+defaultClass+". \n");
                
                URL webPageUrl = new URL(webPage);

                List<Entity> entities = myGate.findEntities(webPageUrl);
                Set<String> namedLocations = new HashSet<String>();
                Set<String> specificFoundLocations = new HashSet<String>();

                for(Entity thisEntity : entities){
                    if(thisEntity.getType() == "Location"){
                        namedLocations.add(thisEntity.getText());
                    }
                }

                for(String location : namedLocations){
                    String params = "&num=" + 1 + "&hl=en";
                    Vector<Instance> results = google.doWikipediaSearch(location,params);
                    if(results.size() > 0){
                        specificFoundLocations.add(results.get(0).getId());
                        allFoundLocations.add(results.get(0).getId());
                    }
                        
                }
                

                for(String foundURL : specificFoundLocations) {
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

