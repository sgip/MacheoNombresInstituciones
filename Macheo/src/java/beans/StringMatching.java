/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author lilian
 */
public class StringMatching {
    
    // <editor-fold defaultstate="collapsed" desc="N-Grams">  
    public static double ngram(String s1, String s2, int n){
        //Generar los ngrams de la primera cadena
        List<String> ngramList1 = new ArrayList();
        for(int i=0; i<s1.length()-n; i++){
            String gram = s1.substring(i, i+n);
            if(!ngramList1.contains(gram)){
                ngramList1.add(gram);
            }
        }
        
        //Generar los ngrams de la segunda cadena
        List<String> ngramList2 = new ArrayList();
        for(int i=0; i<s2.length()-n; i++){
            String gram = s2.substring(i, i+n);
            if(!ngramList2.contains(gram)){
                ngramList2.add(gram);
            }
        }

        double cantCoincidencias = 0;
        double cantTotal = ngramList1.size() + ngramList2.size();
        for(int i=0; i<ngramList1.size(); i++){
            if(ngramList2.contains(ngramList1.get(i))){
                cantCoincidencias++;
            }
        }
        
        double coeficienteSimilaridad = cantCoincidencias/cantTotal;
        return coeficienteSimilaridad;
        
    }
    // </editor-fold>  
    
    // <editor-fold defaultstate="collapsed" desc="Distancia Damerau Levenshtein">
    public static int calcularDistanciaDamerauLevenshtein(String str1,String str2) {      
        int[][] distance = new int[str1.length() + 1][str2.length() + 1];        
 
        for (int i = 0; i <= str1.length(); i++)                                 
            distance[i][0] = i;                                                  
        for (int j = 1; j <= str2.length(); j++)                                 
            distance[0][j] = j;                                                  
 
        for (int i = 1; i <= str1.length(); i++)                                 
            for (int j = 1; j <= str2.length(); j++)                             
                distance[i][j] = minimum(                                        
                        distance[i - 1][j] + 1,                                  
                        distance[i][j - 1] + 1,                                  
                        distance[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1));
 
        return distance[str1.length()][str2.length()];                           
    }
    
    private static int minimum(int a, int b, int c) {                            
        return Math.min(Math.min(a, b), c);                                      
    }
    
    //http://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="LCS">
    public int longestSubstr(String first, String second) {
        if (first == null || second == null || first.length() == 0 || second.length() == 0) {
            return 0;
        }

        int maxLen = 0;
        int fl = first.length();
        int sl = second.length();
        int[][] table = new int[fl+1][sl+1];

        for(int s=0; s <= sl; s++)
          table[0][s] = 0;
        for(int f=0; f <= fl; f++)
          table[f][0] = 0;




        for (int i = 1; i <= fl; i++) {
            for (int j = 1; j <= sl; j++) {
                if (first.charAt(i-1) == second.charAt(j-1)) {
                    if (i == 1 || j == 1) {
                        table[i][j] = 1;
                    }
                    else {
                        table[i][j] = table[i - 1][j - 1] + 1;
                    }
                    if (table[i][j] > maxLen) {
                        maxLen = table[i][j];
                    }
                }
            }
        }
        return maxLen;
    }
    // </editor-fold">
    
    // <editor-fold defaultstate="collapsed" desc="jaccard">
    public double jaccard_coeffecient(String s1, String s2) {

        double j_coeffecient;
        ArrayList<String> j1 = new ArrayList<String>();
        ArrayList<String> j2 = new ArrayList<String>();
        HashSet<String> set1 = new HashSet<String>();
        HashSet<String> set2 = new HashSet<String>();
       
            s1="$"+s1+"$";
            s2="$"+s2+"$";
            int j=0;
            int i=3;
       
            while(i<=s1.length())
            {
                j1.add(s1.substring(j, i));
                    j++;
                    i++;
            }   
            j=0;
            i=3;
            while(i<=s2.length())
            {
                j2.add(s2.substring(j, i));
                    j++;
                    i++;
            }   

           
            Iterator<String> itr1 = j1.iterator();
            while (itr1.hasNext()) {
                  String element = itr1.next();
                  System.out.print(element + " ");
                }
                System.out.println();
                Iterator<String> itr2 = j2.iterator();
                while (itr2.hasNext()) {
                  String element = itr2.next();
                  System.out.print(element + " ");
                }
                System.out.println();
           
               
                set2.addAll(j2);
                set2.addAll(j1);
                set1.addAll(j1);
                set1.retainAll(j2);
               
                   
                System.out.println("Union="+set2.size());
                System.out.println("Intersection="+set1.size());
               
                j_coeffecient=((double)set1.size())/((double)set2.size());
                //System.out.println("Jaccard coeffecient="+j_coeffecient);
                return j_coeffecient;

    }
    // </editor-fold>
    
}
