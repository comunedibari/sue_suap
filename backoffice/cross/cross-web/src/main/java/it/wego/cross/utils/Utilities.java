package it.wego.cross.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.codehaus.jackson.map.ObjectMapper;

import it.wego.cross.constants.AnaTipiEvento;
import it.wego.cross.dto.dozer.PraticaDTO;
import it.wego.cross.dto.dozer.PraticaEventoDTO;

public class Utilities {

	public static java.sql.Date getTodaySQLDate(){
		java.util.Calendar cal = Calendar.getInstance();
		java.util.Date utilDate = new java.util.Date(); // your util date
		cal.setTime(utilDate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		java.sql.Date sqlDate = new java.sql.Date(cal.getTime().getTime()); // your sql date
		return sqlDate;
	}

	public static java.sql.Date dateToSQLDate(Date date){
		java.sql.Date sqlDate = new java.sql.Date(date.getTime());
		return sqlDate;
	}

	public static Date getIeri() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}


	public static Date getDataByNumeroGiorniIndietro(int num) {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -num);
		return cal.getTime();
	}


	public static String objectToStringJson(Object obj) throws Exception{
		String response = "";
		try {

			ObjectMapper mapperObj = new ObjectMapper();
			response =  mapperObj.writeValueAsString(obj);

		} catch (Exception e) {
			throw e;
		}

		return response;
	}


	public static String dateToString(Date date) {
		String result = "";
		if (date != null) {
			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			String format = formatter.format(date);
			result = format;
		}
		return result;
	}


	public static Date parseDate(String date) throws Exception {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return (Date) formatter.parse(date);
	}

	public static Date parseDateNoEscape(String date) throws Exception {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		return (Date) formatter.parse(date);
	}


	public static Date parseDateHHMMSS(String hour) throws Exception {
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		return (Date) formatter.parse(hour);
	}


	public static Date addDays(Date date, int days){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, days); 
		return cal.getTime();
	}

	public static Timestamp dateToTimestamp(Date date){
		Timestamp dataTimeStamp = new Timestamp(date.getTime());
		return dataTimeStamp;
	} 


	public static Date getToTimeDayMidNight(){
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		now.set(Calendar.HOUR_OF_DAY, 0);
		return now.getTime();
	}

	public static Boolean dayIsEqualToToDay(Date date){
		Boolean response = false;
		String dateToString = dateToString(date);
		String dateToStringOggi = dateToString(getToTimeDayMidNight());
		if(dateToStringOggi.equals(dateToString)){
			response = true;
		}
		return response;
	}

	public static Boolean dayIsBeforeToToDay(Date date){
		Boolean response = false;
		Calendar c = Calendar.getInstance();

		// set the calendar to start of today
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		// and get that as a Date
		Date today = c.getTime();

		// user-specified date which you are testing
		// let's say the components come from a form or something
		int year = 2011;
		int month = 5;
		int dayOfMonth = 20;

		// reuse the calendar to set user specified date
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

		// test your condition
		if (date.before(today)) {
			response = true;
		} else {
			response = false;
		}
		return response;
	}


	public static String timeStampToString(Timestamp date) {
		String result = "";
		if (date != null) {
			DateFormat formatter = new SimpleDateFormat("HH:mm");
			String format = formatter.format(date);
			result = format;
		}
		return result;
	}

	public static String formatDDMMYYYtoYYYYMMDDHHMMSS(String dataOraChiamata) {
		String[] split = dataOraChiamata.split("-");
		String giorno = "";
		String anno = "";
		String mese = "";
		for (int i = 0; i < split.length; i++) {
			giorno = split[0];
			mese = split[1];
			anno = split[2];
		}
		String nuovaData = anno+"-"+mese+"-"+giorno;
		nuovaData = nuovaData+" 00:00:00";
		return nuovaData;
	}


	public static Timestamp convertDDMMYYYYHHMMSStiTimestamp(String dataOraChiamata) throws Exception {
		dataOraChiamata = dataOraChiamata+" 00:00:00";
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		Date dataD =  (Date) formatter.parse(dataOraChiamata);
		Timestamp timestamp = new Timestamp(dataD.getTime());
		return timestamp;
	}

	public static List<PraticaDTO> sortPaperworksByRIC(List<PraticaDTO> list){
		if(list!= null){
			Collections.sort(list, new Comparator<PraticaDTO>() {
			    @Override
			    public int compare(PraticaDTO lhs, PraticaDTO rhs) {
			        // -1 - less than, 1 - greater than, 0 - equal, all inversed for descending
			    	Date d1 = lhs.getDataRicezione();
			    	Date d2 = rhs.getDataRicezione();
			        return -1*d1.compareTo(d2);
			    }
			});
		}
		return list;
		
	}
	
	public static List<PraticaDTO> getAllOfLinkedPaperworks(PraticaDTO pratica){
		List<PraticaDTO> allOfLinkedPaperwork = new ArrayList<>();
		ArrayList<Node<PraticaDTO>> queue = new ArrayList<>();

		Map<Integer, Node<PraticaDTO>> allNodes = new HashMap<>();
		allNodes.put(pratica.getIdPratica(), new Node<>(pratica));

		Set<Integer> alreadyProcessed = new HashSet<>();
		alreadyProcessed.add(pratica.getIdPratica());
		pratica.setLinkedToList(null);
		
		//first search: search of all nodes
		if(pratica.getPraticheAssociate() != null){
			for(PraticaDTO temp:pratica.getPraticheAssociate()) {
				temp.setLinkedToList(null);
				queue.add(queue.size(),new Node<>(pratica, temp));
				allNodes.put(temp.getIdPratica(), new Node<>(pratica, temp));
			}
		}

		while(!queue.isEmpty()) {
			Node<PraticaDTO> n = queue.get(0);
			queue.remove(0);
			if(!alreadyProcessed.contains(n.getCurrent().getIdPratica())) {
				alreadyProcessed.add(n.getCurrent().getIdPratica());
				
				for(PraticaDTO linkedPaperwork: n.getCurrent().getPraticheAssociate()) {
					linkedPaperwork.setLinkedToList(null);
					if(!alreadyProcessed.contains(linkedPaperwork.getIdPratica())) 
						allNodes.put(linkedPaperwork.getIdPratica(),new Node<>(n.getCurrent(), linkedPaperwork));
					
				}
			}
		}
		alreadyProcessed.clear();
		alreadyProcessed.add(pratica.getIdPratica());
		//second search: set of all parents node
		if(pratica.getPraticheAssociate() != null){
			for(PraticaDTO temp:pratica.getPraticheAssociate()) {
				queue.add(queue.size(),new Node<>(pratica, temp));
			}
		}
		
		while(!queue.isEmpty()) {
			Node<PraticaDTO> n = queue.get(0);
			queue.remove(0);
			System.out.println(n.getCurrent());
			if(!alreadyProcessed.contains(n.getCurrent().getIdPratica())) {
				alreadyProcessed.add(n.getCurrent().getIdPratica());

				for(PraticaDTO parent: n.getParents()) {
					if(!n.getCurrent().getLinkedToList().contains(parent))
						n.getCurrent().getLinkedToList().add(parent);
				}
				
				allOfLinkedPaperwork.add(allOfLinkedPaperwork.size(), n.getCurrent());
				for(PraticaDTO linkedPaperwork: n.getCurrent().getPraticheAssociate()) {
					
					if(!alreadyProcessed.contains(linkedPaperwork.getIdPratica())) {
						queue.add(queue.size(), new Node<>(n.getCurrent(), linkedPaperwork));
					} 
					for(Entry<Integer, Node<PraticaDTO>> tempQueue: allNodes.entrySet()) {
						if(tempQueue.getValue().getCurrent().getIdPratica().equals(linkedPaperwork.getIdPratica())) {
							tempQueue.getValue().getCurrent().getLinkedToList().add(n.getCurrent());
						}
					}
					
				}
			}
		}


		return allOfLinkedPaperwork;
	}
}
