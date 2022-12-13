package src;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.File;
import java.io.FileWriter;

public class Annotador {
    
    public static void main(String[] args) throws Exception{

        String defaultClass = "urn:uc3m.es:miaa#webPage";

        if(args.length == 0 || args.length > 3){throw new Exception("Invalid number of arguments");}
        if(!args[0].contains(".txt")){throw new Exception("The format of file is not valid");}
        if(args.length > 1 && args[1].equalsIgnoreCase("-c")){
            defaultClass = args[2];
        }
        try {
			List<String> allLines = Files.readAllLines(Paths.get(args[0]));

			for (String line : allLines) {
				System.out.println(line);
			}

		} catch (Exception e) {
			throw new Exception("Error reading file: " + e);
		}

        String outputFileName = args[0].replace(".txt","").concat(".ttl");
        System.out.println(outputFileName);
        try{
            File outputFile = new File(outputFileName);
        } catch(Exception e){ throw new Exception("Error creating file: " + e);}
         try{
            FileWriter outputFileWriter = new FileWriter(outputFileName);
            outputFileWriter.write("Fuciona");
            outputFileWriter.close();
         }catch(Exception e){ throw new Exception("Error writting to file: " + e);}
    }
}
