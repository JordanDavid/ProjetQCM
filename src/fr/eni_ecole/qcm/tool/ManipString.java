/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.tool
 */
package fr.eni_ecole.qcm.tool;

import java.util.Arrays;

/**
 * 
 * @author d1410gerardm
 * 20 oct. 2015
 */
public class ManipString {

	/** A partir de la chaine de caractère passé en paramètre,
     *  Une nouvelle chaine de caractères est créée ayant chaque première lettre de chaque mot passée
     *  En majuscule et le reste passé en minuscule
     *  @param String chaine Chaine à modifier
     *  @param Char[] separateur Liste des séparateurs
     *  @return String Chaine modifiée
     */ 
	public static String firstInUpper(String chaine, char[] separateur){
        Boolean maj = true;
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < chaine.length(); i++) {
            if(maj){
                str.append(Character.toUpperCase(chaine.charAt(i)));
            } else {
                str.append(Character.toLowerCase(chaine.charAt(i)));
            }

            maj = Arrays.asList(separateur).indexOf(chaine.charAt(i)) != -1;
            
        }
        return str.toString();
	}
}
