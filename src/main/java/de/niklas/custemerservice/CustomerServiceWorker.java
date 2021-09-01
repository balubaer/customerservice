package de.niklas.custemerservice;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.niklas.custemerservice.model.Auftraege;
import de.niklas.custemerservice.model.Kunde;
import de.niklas.custemerservice.repository.AuftraegeRepository;
import de.niklas.custemerservice.repository.KundenRepository;

public class CustomerServiceWorker {

	private static final Logger log = LoggerFactory.getLogger(CustomerServiceWorker.class);

	public static final String[] HEADERS = { "Firma", "Strasse", "Strassenzusatz", "Ort", "Land", "PLZ", "Vorname", "Nachname", "Kunden-ID" };

	private AuftraegeRepository auftraegeRepo;
	private KundenRepository kundenRepo;

	private LocalDateTime startDateTime;

	private HashMap<String, ArrayList<Kunde>> kundenProLander;

	public CustomerServiceWorker() {
		super();
		this.kundenProLander = new HashMap<String, ArrayList<Kunde>>();
	}

	public void setAuftraegeRepo(AuftraegeRepository auftraegeRepo) {
		this.auftraegeRepo = auftraegeRepo;
	}

	public void setKundenRepo(KundenRepository kundenRepo) {
		this.kundenRepo = kundenRepo;
	}

	public void setStartDateTime(LocalDateTime startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	private void addKundeToLand(Kunde kunde, String land) {
		ArrayList<Kunde> findKunden  = kundenProLander.get(land);
		if (findKunden != null) {
			findKunden.add(kunde);
		} else {
			findKunden = new ArrayList<Kunde>();
			findKunden.add(kunde);
			kundenProLander.put(land, findKunden);
		}
	}
	
	private void makeCSVProLand(String dateString, String land, ArrayList<Kunde> kunden) {
		String filename = land + dateString + ".csv";

		BufferedWriter writer;
		try {
			writer = Files.newBufferedWriter(Paths.get(filename));
			CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(HEADERS));
			
			for (Kunde kunde : kunden) {
				csvPrinter.printRecord(kunde.getFirmenname(), kunde.getStrasse(), kunde.getStrassenzusatz(), kunde.getOrt(), kunde.getLand(), kunde.getPlz(), kunde.getVorname(), kunde.getNachname(), kunde.getKundenid());

				
			}
			csvPrinter.flush();
			csvPrinter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void makeCSV(String dateString) {
		Set<String> laenderKeys = kundenProLander.keySet();
		for (String key : laenderKeys) {
			makeCSVProLand(dateString, key, kundenProLander.get(key));
		}
	}
	
	private void findKunden() {
		LocalDateTime endDateTime = startDateTime.plusMonths(1L);

		Iterable<Auftraege> auftraege = auftraegeRepo.findAll();
		List<Auftraege> sortedList = StreamSupport.stream(auftraege.spliterator(), false).collect(Collectors.toList());

		Collections.sort(sortedList);
	    log.info("### startDateTime :: " + startDateTime.toString() + " endDateTime :: " + endDateTime.toString());

		for (Auftraege auftrag : sortedList) {
			LocalDateTime auftragLastChange = auftrag.getLastchangeTimeStamp();
			if (auftragLastChange.isAfter(this.startDateTime) && auftragLastChange.isBefore(endDateTime)) {
				String kundenID = auftrag.getKundeid();

				Optional<Kunde> okunde = kundenRepo.findById(Long.parseLong(kundenID, 10));
				Kunde kunde = okunde.get();

				if (kunde != null) {
					addKundeToLand(kunde, kunde.getLand());
					log.info("Find Kunde: " + kunde.getNachname() + " " + kunde.getFirmenname() + " " + kunde.getLand());
				}
			}
		}
		 DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		makeCSV(startDateTime.format(formatter));
	}

	public void work() {
	    log.info("in work Current time is :: " + Calendar.getInstance().getTime());
	    findKunden();


	}

}
