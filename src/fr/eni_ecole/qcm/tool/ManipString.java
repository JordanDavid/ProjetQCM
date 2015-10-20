/**
 * 20 oct. 2015
 * QCM V1.0
 * fr.eni_ecole.qcm.tool
 */
package fr.eni_ecole.qcm.tool;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
	
	/**
	 * Méthode en charge de l'encodage MD5 d'une chaine 
	 * 20 oct. 2015
	 * @param password chaine à encoder
	 * @return un MD5 de la chaine
	 */
	public static String encode(String password)
    {
        byte[] uniqueKey = password.getBytes();
        byte[] hash      = null;

        try
        {
            hash = MessageDigest.getInstance("MD5").digest(uniqueKey);
        }
        catch (NoSuchAlgorithmException e)
        {
            throw new Error("No MD5 support in this VM.");
        }

        StringBuilder hashString = new StringBuilder();
        for (int i = 0; i < hash.length; i++)
        {
            String hex = Integer.toHexString(hash[i]);
            if (hex.length() == 1)
            {
                hashString.append('0');
                hashString.append(hex.charAt(hex.length() - 1));
            }
            else
                hashString.append(hex.substring(hex.length() - 2));
        }
        return hashString.toString();
    }
	
}
