/**
 * Package pour les différentes classes d'utilitaires.
 */
package fr.eni_ecole.qcm.tool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Classe en charge de manipuler les dates mais qui permet aussi de convertir 
 * les objets SQL Date en Util.Java et inversement. 
 * @author ds
 * @since 1.0
 */
public class ManipDate {
	
	/**
	 * Vérification que la date est bien formaté suivant ce format: dd/MM/yyyy
	 * @param laDate la date à vérifier
	 * @return true si c'est bien formaté, false sinon.
	 */
	private static boolean verifDate(String laDate){
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		boolean bValue= true;
		java.util.Date dateTmp = null;

		try{
			if (laDate == null) bValue=false;
			else {
				dateTmp = sdf.parse(laDate);
				if(dateTmp!=null && !laDate.equals(sdf.format(dateTmp)))
					bValue= false;
			}
		}catch (ParseException e){
			bValue= false;
		}
		
		return bValue;
	}
	
	/**
	 * Permet de calculer la différence entre 2 dates.
	 * @param dateDebut date de début.
	 * @param dateFin date de fin.
	 * @return un entier
	 */
	public static int datesDiff(Date dateDebut, Date dateFin) {
		long diffCalul = 0;
		if (dateDebut != null && dateFin != null && dateFin.getTime() >= dateDebut.getTime()){
			diffCalul = dateFin.getTime() - dateDebut.getTime();
			diffCalul /= 31536000000L; //1000*60*60*24*365;
		}
		return (int)diffCalul;
	}

	/**
	 * Converti un champ string date en util.date.
	 * @param laDate le champ texte date à convertir
	 * @return la date de type java.util.Date
	 * @throws Exception Exception.
	 */
	public static java.util.Date stringVersUtil(String laDate) throws Exception{
		java.util.Date date=null;
		if (laDate != null && verifDate(laDate)){
			try{
				date = new SimpleDateFormat("dd/MM/yyyy").parse(laDate);
			}catch (Exception e){
				throw new Exception("Date non valide !");
			}
		}
		return date;
	}
	
	/**
	 * Retourne la date formaté dans une chaine de caractère au format long.
	 * @param laDate à formater
	 * @return une date au format String long.
	 */
	public static String dateAuFormatLong(java.util.Date laDate){
		DateFormat f = DateFormat.getDateInstance(DateFormat.FULL,Locale.getDefault());
		return f.format(laDate);
	}
	/**
	 * Retourne la date formaté dans une chaine de caractère au format cours.
	 * @param laDate à formater
	 * @return une date au format String cours.
	 */
	public static String dateAuFormatCours(java.util.Date laDate){
		DateFormat f = DateFormat.getDateInstance(DateFormat.MEDIUM,Locale.getDefault());
		return f.format(laDate);
	}

	/**
	 * Converti une jave.Util.Date vers une date au format SQL
	 * @param laDate date à convertir
	 * @return uneDate.
	 */
	public static java.sql.Date dateUtilVersSQL(java.util.Date laDate){
		if (laDate != null)
			return new java.sql.Date(laDate.getTime());
		else
			return null;
	}
	
	/**
	 * Converti une jave.sql.Date vers une date au format util.Date
	 * @param laDate date à convertir
	 * @return uneDate.
	 */
	public static java.util.Date dateSQLVersUtil(java.sql.Date laDate){
		if (laDate != null)
			return new java.util.Date(laDate.getTime());
		else
			return null;
	}

	/**
	 * Méthode en charge de convertir une date util.Date au format voulu 
	 * 26 oct. 2015
	 * @param laDate La date à convertir
	 * @param format Format choisi
	 * @return
	 * @throws ParseException 
	 */
	public static java.util.Date dateWithFormat(java.sql.Date laDate, String format) throws ParseException{
		Date date = null;
		if (laDate != null){
			DateFormat df = new SimpleDateFormat(format);
			date = df.parse(laDate.toString());
		}
			return date;
	}
}
