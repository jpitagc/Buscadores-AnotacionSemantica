package src;


import es.uc3m.miaa.utils.*;

import java.util.Vector;



public class Search {



    public static void main(String[] args) throws Exception {
        int total_results = Integer.parseInt(args[1]);
        if (total_results < 1 || total_results > 100){
            throw new Exception("Number of results has to be between 1 and 100.");
        }
        System.out.println("Running search command...");
        GoogleSearch google = new GoogleSearch();

        boolean rs = GoogleSearch.startGoogle();
        if (!rs){System.out.println("Not started"); System.exit(-1);;}

        Vector<Instance> results = new Vector<Instance>();
        
        int pending_results = Integer.parseInt(args[1]);

        for(int start = 1; start<= total_results; start+=10){
            if (pending_results / 10 >= 1){
                try{
                    String params = "&num=" + 10 + "&start=" + start;
                    results.addAll(google.doWebSearch(args[0],params));
                } catch(Exception e){
                    System.out.println("Excepción:" + e);
                }
                pending_results -= 10;
            }else{
                try{
                    String params = "&num=" + pending_results + "&start=" + start;
                    results.addAll(google.doWebSearch(args[0],params));
                } catch(Exception e){
                    System.out.println("Excepción:" + e);
                }
                
            }
            
        }
        
        
        System.out.println("Obtained results are: ");

        for (int i = 0; i < results.size(); i++) {
            Instance indResult = results.get(i);
            System.out.println((i+1) + "- Url -> " + indResult.getId() +"\n Title -> "+ indResult.getLabel()+"\n Snippet -> "+ indResult.getDescription());
          }
        rs = GoogleSearch.stopGoogle();
        
    }
    
}
