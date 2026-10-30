package cross.cross_scripts.ave_58;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cross.cross_scripts.BaseHandler;
import cross.cross_scripts.MySql;

public class AttachmentSizeXYearReader extends BaseHandler{

	private static final Logger logger = LogManager.getLogger(AttachmentSizeXYearReader.class);

	public AttachmentSizeXYearReader(MySql mysql) {
		super(mysql, null);
	}

	@Override
	public void run() throws Exception {
		logger.warn("run " + this.getClass().getName());
		Map<Integer, BigDecimal> attachmentSizes = new HashMap<>();
		for(int i = 2016; i<2025;i++)
			attachmentSizes.put(i, BigDecimal.valueOf(0));
		
		String query = "select pe.data_evento, a.dimensione, pe.id_pratica_evento from pratiche_eventi pe inner join pratiche_eventi_allegati pea on pe.id_pratica_evento = pea.id_pratica_evento inner join allegati a on pea.id_allegato = a.id "
				+ "order by pe.data_evento asc";
		
		consumeSelect(query, set -> {
			LocalDate date = null;
			BigDecimal dim = null;
			Integer id = null;
			try {
				date = set.getDate(1).toLocalDate();
				dim = set.getBigDecimal(2);
				id = set.getInt(3);
//				logger.error("id evento: " + id);
				attachmentSizes.put(date.getYear(), dim.add(attachmentSizes.get(date.getYear())));
			} catch (Exception e) {
				new RuntimeException(e);
			}
		});
		for(Entry<Integer, BigDecimal> entry : attachmentSizes.entrySet()) {
			BigDecimal v = entry.getValue().divide(BigDecimal.valueOf(1024)).divide(BigDecimal.valueOf(1024));
			System.out.println("year: " + entry.getKey() + "\tsize: " + v + " KB");
		}
		logger.warn("END " + this.getClass().getName());
	}

}
