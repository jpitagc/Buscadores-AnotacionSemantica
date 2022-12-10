package src;


import es.uc3m.miaa.utils.*;

import java.util.Vector;



public class Search {



    public static void main(String[] args) {
        System.out.println("Running search command...");
        GoogleSearch google = new GoogleSearch();

        boolean rs = google.startGoogle();
        if (!rs){System.out.println("Not started"); System.exit(-1);;}

        Vector<Instance> results = new Vector<Instance>();
        try{
            String params = "&num=" + args[1];
            results = google.doWikipediaSearch(args[0],params);
        } catch(Exception e){
            System.out.println("Excepción");
        }

      
        
        System.out.println("Obtained results are: ");

        for (int i = 0; i < results.size(); i++) {
            Instance indResult = results.get(i);
            System.out.println(i + "- Url -> " + indResult.getId() +"\n Title -> "+ indResult.getLabel()+"\n Snippet -> "+ indResult.getDescription());
          }
        rs = google.stopGoogle();
        
    }
    
}
