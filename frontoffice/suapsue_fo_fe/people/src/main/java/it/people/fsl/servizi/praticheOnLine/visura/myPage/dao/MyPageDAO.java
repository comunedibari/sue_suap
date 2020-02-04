package it.people.fsl.servizi.praticheOnLine.visura.myPage.dao;

import it.gruppoinit.commons.Utilities;
import it.people.City;
import it.people.IProcess;
import it.people.core.PeopleContext;
import it.people.core.PplUser;
import it.people.core.PplUserData;
import it.people.core.ProcessManager;
import it.people.core.persistence.PersistenceManager;
import it.people.core.persistence.PersistenceManager.Mode;
import it.people.core.persistence.PersistenceManagerFactory;
import it.people.core.persistence.converters.sql.StatusConverter;
import it.people.core.persistence.exception.peopleException;
import it.people.db.DBConnector;
import it.people.fsl.servizi.oggetticondivisi.PersonaFisica;
import it.people.fsl.servizi.oggetticondivisi.Titolare;
import it.people.fsl.servizi.oggetticondivisi.personagiuridica.PersonaGiuridica;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.model.ProcessData;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.ActivityBean;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.DatiEstesiMyPage;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.DeleganteMyPage;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.IndirizzoMyPage;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.InfoBoBean;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBean;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.oggetti.PraticaBeanExtended;
import it.people.fsl.servizi.praticheOnLine.visura.myPage.util.AccreditamentiUtils;
import it.people.process.AbstractPplProcess;
import it.people.process.GenericProcess;
import it.people.process.common.entity.Attachment;
import it.people.process.data.AbstractData;
import it.people.process.data.PplPersistentData;
import it.people.process.workflow.IWorkFlow;
import it.people.process.workflow.IWorkFlowFactory;
import it.people.process.workflow.WorkFlowFactory;
import it.people.propertymgr.ApplicationProperty;
import it.people.propertymgr.PropertyFormatException;
import it.people.util.DateFormatter;
import it.people.util.Device;
import it.people.util.MessageBundleHelper;
import it.people.util.PeopleProperties;
import it.people.util.status.PaymentStatusEnum;
import it.people.wrappers.IRequestWrapper;
import it.wego.cross.webservices.cxf.interoperability.TipologiaEventoIntegrazione;
import it.wego.datiestesiMyPage.DatiEstesiMyPageDocument;
//import it.wego.datiestesiMyPage.DatiEstesiMyPageDocument.DatiEstesiMyPage;
import it.wego.datiestesiMyPage.DatiEstesiMyPageDocument.DatiEstesiMyPage.Deleganti;
import it.wego.datiestesiMyPage.DatiEstesiMyPageDocument.DatiEstesiMyPage.Deleganti.Delegante;
import it.wego.datiestesiMyPage.DatiEstesiMyPageDocument.DatiEstesiMyPage.Indirizzi;
import it.wego.datiestesiMyPage.DatiEstesiMyPageDocument.DatiEstesiMyPage.Indirizzi.Indirizzo;
import it.wego.datiestesiMyPage.DatiEstesiMyPageDocument.Factory;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.ojb.broker.query.Criteria;
import org.apache.ojb.broker.query.QueryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyPageDAO {
	private static Logger logger = LoggerFactory.getLogger(MyPageDAO.class.getName());
	// static Category cat = Category.getRoot();
	private Connection fedbConnection = null;
	private Connection legacyDbConnection = null;
	private String fedbDatabaseName = "";
	private String legacyDbDatabaseName = "";

	public void open() {
		try {
			setLegacyDbConnection(DBConnector.getInstance().connect("LegacyDB"));

			setFedbConnection(DBConnector.getInstance().connect("FEDB"));
			if (getLegacyDbConnection() != null) {
				setLegacyDbDatabaseName(getLegacyDbConnection().getCatalog());
			}
			if (getFedbConnection() != null) {
				setFedbDatabaseName(getFedbConnection().getCatalog());
			}
		} catch (Exception e) {
			logger.error("MyPageDAO (open connection) ");
			logger.error(e.getMessage());
		}
	}
	
	
	public void saveAllegatoInterop(AbstractPplProcess process, IRequestWrapper request) {
		ProcessData dataForm = (ProcessData) process.getData();
		PreparedStatement ps = null;
		try {
			open();
			String sqlSave = "INSERT INTO "+ getLegacyDbDatabaseName() +"allegati_interop "
					+ "(identificativo_pratica, file_name, file, descrizione_allegato, stato_invio) "
					+ "	VALUES "
					+ "( ?, ?, ?, ?, ?)";
			ps = getLegacyDbConnection().prepareStatement(sqlSave);
			if(dataForm.getDettaglioPratica() != null && dataForm.getDettaglioPratica().getProcessDataID() != null 
					&& dataForm.getDettaglioPratica().getProcessDataID().trim().length() != 0)
				ps.setString(1, dataForm.getDettaglioPratica().getProcessDataID());
			
			Attachment allegato = (Attachment) dataForm.getAllegati().get(0);
			if(allegato != null) {
				ps.setString(2, allegato.getName());
				ps.setString(3, allegato.getData());
				ps.setString(4, allegato.getDescrizione());
				ps.setBoolean(5, false);
			}
			
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}
	}
	
	

	public int filesFailedSendNumber(AbstractPplProcess process, IRequestWrapper request) {
		int result = -1;
		Vector buffer = new Vector();
		ProcessData dataForm = (ProcessData) process.getData();
		ResultSet rs = null;
		PreparedStatement ps = null;
		try {
			PeopleContext pplContext = PeopleContext.create(request.getUnwrappedRequest());

			String communeId = pplContext.getCommune().getKey();
			open();
			String userID = pplContext.getUser().getUserID().toLowerCase();
			ps = getLegacyDbConnection()
					.prepareStatement("SELECT sp.oid FROM submitted_process AS sp WHERE sp.send_error = 1 "
							+ "AND sp.USER_ID = '" + userID + "' " + "AND sp.commune_id = '" + communeId + "'");
			for (rs = ps.executeQuery(); rs.next(); buffer.add(Long.valueOf(rs.getLong(1)))) {
			}
			dataForm.setFailedSendOids(buffer);
			if (!buffer.isEmpty()) {
				result = buffer.size();
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		try {
			rs.close();
		} catch (Exception exception1) {
		}
		try {
			ps.close();
		} catch (Exception exception2) {
		}
		try {
			closeConnections();
		} catch (Exception exception3) {
		}
		try {
			rs.close();
		} catch (Exception exception4) {
		}
		try {
			ps.close();
		} catch (Exception exception5) {
		}
		try {
			closeConnections();
		} catch (Exception exception6) {
		}
		try {
			rs.close();
		} catch (Exception exception7) {
		}
		try {
			ps.close();
		} catch (Exception exception8) {
		}
		try {
			closeConnections();
		} catch (Exception exception9) {
		}
		return result;
	}

	public ArrayList getListaPraticheInCompilazione(AbstractPplProcess process, IRequestWrapper request) {
		Integer offset = Integer.valueOf(0);
		if ((request.getParameter("pager.offset") != null) && (!"".equals(request.getParameter("pager.offset")))) {
			offset = Integer.valueOf(Integer.parseInt(request.getParameter("pager.offset")));
		}
		ResultSet rs = null;
		ProcessData dataForm = (ProcessData) process.getData();
		PreparedStatement ps = null;
		ArrayList listaPratiche = new ArrayList();
		StatusConverter statusConverter = new StatusConverter();
		try {
			PeopleContext pplContext = PeopleContext.create(request.getUnwrappedRequest());

			String communeId = pplContext.getCommune().getKey();
			open();
			String userID = pplContext.getUser().getUserID();

			Vector<String> categoryAssociationAccreditations = new Vector();
			if (AccreditamentiUtils.hasCategoryAssociationProfile(request.getUnwrappedRequest(),
					pplContext.getUser().getUserData().getCodiceFiscale(), communeId)) {
				categoryAssociationAccreditations = AccreditamentiUtils.getCategoriesAssociationCodes(
						request.getUnwrappedRequest(), pplContext.getUser().getUserData().getCodiceFiscale(),
						communeId);
			}
			String sqlCount = "SELECT count(*) numeroPratiche FROM " + getLegacyDbDatabaseName() + "pending_process "
					+ " LEFT OUTER JOIN " + getLegacyDbDatabaseName() + "detail_process ON " + getLegacyDbDatabaseName()
					+ "pending_process.process_data_id=" + getLegacyDbDatabaseName() + "detail_process.process_data_id "
					+ " LEFT OUTER JOIN " + getFedbDatabaseName() + "process_status ON " + getLegacyDbDatabaseName()
					+ "pending_process.process_data_id=" + getFedbDatabaseName() + "process_status.peopleid "
					+ " LEFT JOIN " + getLegacyDbDatabaseName() + "submitted_process ON " + getLegacyDbDatabaseName()
					+ "pending_process.oid=" + getLegacyDbDatabaseName() + "submitted_process.editable_process_id"
					+ " WHERE " + getLegacyDbDatabaseName() + "pending_process.user_id"
					+ getUserIds(userID, categoryAssociationAccreditations) + " AND " + getLegacyDbDatabaseName()
					+ "submitted_process.oid is null  AND " + getLegacyDbDatabaseName()
					+ "pending_process.oid NOT IN (SELECT " + getLegacyDbDatabaseName() + "pending_process.oid FROM "
					+ getLegacyDbDatabaseName() + "pending_process WHERE " + getLegacyDbDatabaseName()
					+ "pending_process.sent = '1') AND " + getLegacyDbDatabaseName() + "pending_process.commune_id='"
					+ communeId + "' ";
			if ((dataForm.getCodicePratica() != null) && (!dataForm.getCodicePratica().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + " and " + getLegacyDbDatabaseName() + "pending_process.process_data_id= ? ";
			}
			if ((dataForm.getDataCreazioneDa() != null) && (!dataForm.getDataCreazioneDa().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + "and " + getLegacyDbDatabaseName() + "pending_process.creation_time>=? ";
			}
			if ((dataForm.getDataCreazioneA() != null) && (!dataForm.getDataCreazioneA().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + "and " + getLegacyDbDatabaseName() + "pending_process.creation_time<=? ";
			}
			if ((dataForm.getDataUltimaModificaDa() != null)
					&& (!dataForm.getDataUltimaModificaDa().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + "and " + getLegacyDbDatabaseName() + "pending_process.last_modified_time>=? ";
			}
			if ((dataForm.getDataUltimaModificaA() != null)
					&& (!dataForm.getDataUltimaModificaA().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + "and " + getLegacyDbDatabaseName() + "pending_process.last_modified_time<=? ";
			}
			if ((dataForm.getOggetto() != null) && (!dataForm.getOggetto().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + "and " + getLegacyDbDatabaseName() + "detail_process.oggetto LIKE ?  ";
			}
			if ((dataForm.isPendingPayments()) && (!dataForm.isFailedPayments())) {
				sqlCount = sqlCount + "and " + getFedbDatabaseName() + "process_status.status="
						+ PaymentStatusEnum.paymentPending.getCode() + " ";
			}
			if ((dataForm.isFailedPayments()) && (!dataForm.isPendingPayments())) {
				sqlCount = sqlCount + "and " + getFedbDatabaseName() + "process_status.status="
						+ PaymentStatusEnum.paymentAborted.getCode() + " ";
			}
			if ((dataForm.isFailedPayments()) && (dataForm.isPendingPayments())) {
				sqlCount = sqlCount + "and (" + getFedbDatabaseName() + "process_status.status="
						+ PaymentStatusEnum.paymentPending.getCode() + " or " + getFedbDatabaseName()
						+ "process_status.status=" + PaymentStatusEnum.paymentAborted.getCode() + ") ";
			}
			ps = getLegacyDbConnection().prepareStatement(sqlCount);
			int iCount = 1;
			if ((dataForm.getCodicePratica() != null) && (!dataForm.getCodicePratica().equalsIgnoreCase(""))) {
				ps.setString(iCount, dataForm.getCodicePratica());
				iCount++;
			}
			if ((dataForm.getDataCreazioneDa() != null) && (!dataForm.getDataCreazioneDa().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataCreazioneDa());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(iCount, dataString);
				iCount++;
			}
			if ((dataForm.getDataCreazioneA() != null) && (!dataForm.getDataCreazioneA().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataCreazioneA());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(iCount, dataString + " 23:59:59");
				iCount++;
			}
			if ((dataForm.getDataUltimaModificaDa() != null)
					&& (!dataForm.getDataUltimaModificaDa().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataUltimaModificaDa());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(iCount, dataString);
				iCount++;
			}
			if ((dataForm.getDataUltimaModificaA() != null)
					&& (!dataForm.getDataUltimaModificaA().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataUltimaModificaA());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(iCount, dataString + " 23:59:59");
				iCount++;
			}
			if ((dataForm.getOggetto() != null) && (!dataForm.getOggetto().equalsIgnoreCase(""))) {
				ps.setString(iCount, "%" + dataForm.getOggetto() + "%");
				iCount++;
			}
			rs = ps.executeQuery();
			int numPratiche = 0;
			while (rs.next()) {
				numPratiche = rs.getInt("numeroPratiche");
			}
			dataForm.setNumPratiche(String.valueOf(numPratiche));

			String sql = "SELECT " + getLegacyDbDatabaseName() + "pending_process.oid, " + getLegacyDbDatabaseName()
					+ "pending_process.STATUS, " + getLegacyDbDatabaseName() + "pending_process.process_name, "
					+ getLegacyDbDatabaseName() + "pending_process.process_data_id as process_data, "
					+ getLegacyDbDatabaseName() + "pending_process.commune_id, " + getLegacyDbDatabaseName()
					+ "pending_process.content_name, " + getLegacyDbDatabaseName() + "pending_process.user_id, "
					+ getLegacyDbDatabaseName() + "pending_process.creation_time, " + getLegacyDbDatabaseName()
					+ "detail_process.oggetto, " + getFedbDatabaseName() + "process_status.status as paymentStatus "
					+ " FROM " + getLegacyDbDatabaseName() + "pending_process " + " LEFT OUTER JOIN "
					+ getLegacyDbDatabaseName() + "detail_process ON " + getLegacyDbDatabaseName()
					+ "pending_process.process_data_id=" + getLegacyDbDatabaseName() + "detail_process.process_data_id "
					+ " LEFT OUTER JOIN " + getFedbDatabaseName() + "process_status ON " + getLegacyDbDatabaseName()
					+ "pending_process.process_data_id=" + getFedbDatabaseName() + "process_status.peopleid "
					+ " LEFT JOIN " + getLegacyDbDatabaseName() + "submitted_process ON " + getLegacyDbDatabaseName()
					+ "pending_process.oid=" + getLegacyDbDatabaseName() + "submitted_process.editable_process_id"
					+ " WHERE " + getLegacyDbDatabaseName() + "pending_process.user_id"
					+ getUserIds(userID, categoryAssociationAccreditations) + " AND " + getLegacyDbDatabaseName()
					+ "submitted_process.oid is null  AND " + getLegacyDbDatabaseName()
					+ "pending_process.oid NOT IN (SELECT " + getLegacyDbDatabaseName() + "pending_process.oid FROM "
					+ getLegacyDbDatabaseName() + "pending_process WHERE " + getLegacyDbDatabaseName()
					+ "pending_process.sent = '1') AND " + getLegacyDbDatabaseName() + "pending_process.commune_id='"
					+ communeId + "' ";
			if ((dataForm.getCodicePratica() != null) && (!dataForm.getCodicePratica().equalsIgnoreCase(""))) {
				sql = sql + " and " + getLegacyDbDatabaseName() + "pending_process.process_data_id= ? ";
			}
			if ((dataForm.getDataCreazioneDa() != null) && (!dataForm.getDataCreazioneDa().equalsIgnoreCase(""))) {
				sql = sql + "and " + getLegacyDbDatabaseName() + "pending_process.creation_time>=? ";
			}
			if ((dataForm.getDataCreazioneA() != null) && (!dataForm.getDataCreazioneA().equalsIgnoreCase(""))) {
				sql = sql + "and " + getLegacyDbDatabaseName() + "pending_process.creation_time<=? ";
			}
			if ((dataForm.getDataUltimaModificaDa() != null)
					&& (!dataForm.getDataUltimaModificaDa().equalsIgnoreCase(""))) {
				sql = sql + "and " + getLegacyDbDatabaseName() + "pending_process.last_modified_time>=? ";
			}
			if ((dataForm.getDataUltimaModificaA() != null)
					&& (!dataForm.getDataUltimaModificaA().equalsIgnoreCase(""))) {
				sql = sql + "and " + getLegacyDbDatabaseName() + "pending_process.last_modified_time<=? ";
			}
			if ((dataForm.getOggetto() != null) && (!dataForm.getOggetto().equalsIgnoreCase(""))) {
				sql = sql + "and " + getLegacyDbDatabaseName() + "detail_process.oggetto LIKE ?  ";
			}
			if ((dataForm.isPendingPayments()) && (!dataForm.isFailedPayments())) {
				sql = sql + "and " + getFedbDatabaseName() + "process_status.status="
						+ PaymentStatusEnum.paymentPending.getCode() + " ";
			}
			if ((dataForm.isFailedPayments()) && (!dataForm.isPendingPayments())) {
				sql = sql + "and " + getFedbDatabaseName() + "process_status.status="
						+ PaymentStatusEnum.paymentAborted.getCode() + " ";
			}
			if ((dataForm.isFailedPayments()) && (dataForm.isPendingPayments())) {
				sql = sql + "and (" + getFedbDatabaseName() + "process_status.status="
						+ PaymentStatusEnum.paymentPending.getCode() + " or " + getFedbDatabaseName()
						+ "process_status.status=" + PaymentStatusEnum.paymentAborted.getCode() + ") ";
			}
			sql = sql + " ORDER BY " + getLegacyDbDatabaseName() + "pending_process.last_modified_time "
					+ dataForm.getOrdinamentoPraticheInCompilazione();

			sql = sql + " LIMIT " + dataForm.getNumPratichePerPag() + " OFFSET " + offset;

			ps = getLegacyDbConnection().prepareStatement(sql);
			int i = 1;
			if ((dataForm.getCodicePratica() != null) && (!dataForm.getCodicePratica().equalsIgnoreCase(""))) {
				ps.setString(i, dataForm.getCodicePratica());
				i++;
			}
			if ((dataForm.getDataCreazioneDa() != null) && (!dataForm.getDataCreazioneDa().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataCreazioneDa());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(i, dataString);
				i++;
			}
			if ((dataForm.getDataCreazioneA() != null) && (!dataForm.getDataCreazioneA().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataCreazioneA());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(i, dataString + " 23:59:59");
				i++;
			}
			if ((dataForm.getDataUltimaModificaDa() != null)
					&& (!dataForm.getDataUltimaModificaDa().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataUltimaModificaDa());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(i, dataString);
				i++;
			}
			if ((dataForm.getDataUltimaModificaA() != null)
					&& (!dataForm.getDataUltimaModificaA().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataUltimaModificaA());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(i, dataString + " 23:59:59");
				i++;
			}
			if ((dataForm.getOggetto() != null) && (!dataForm.getOggetto().equalsIgnoreCase(""))) {
				ps.setString(i, "%" + dataForm.getOggetto() + "%");
				i++;
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				PraticaBean ppd = new PraticaBean();
				ppd.setProcessDataID(rs.getString("process_data"));
				ppd.setContentName(rs.getString("content_name"));
				ppd.setUserID(rs.getString("user_id"));

				Timestamp tmp = rs.getTimestamp("creation_time");
				String s = DateFormatter.format(tmp);
				ppd.setCreation_time(s);

				ppd.setProcessName(rs.getString("process_name"));
				ppd.setStatus((String) statusConverter.sqlToJava(new Integer(rs.getInt("status"))));

				ppd.setOid(String.valueOf(rs.getLong("oid")));
				String oggetto = rs.getString("oggetto");
				if (oggetto == null) {
					ppd.setOggetto("");
				} else {
					ppd.setOggetto(oggetto);
				}
				String paymentStatus = rs.getString("paymentStatus");
				if (paymentStatus == null) {
					ppd.setPaymentStatus("");
				} else {
					int paymentStatusCode = Integer.parseInt(paymentStatus);
					switch (paymentStatusCode) {
					case 10:
						ppd.setPaymentStatus(MessageBundleHelper.message(
								PaymentStatusEnum.paymentPending.getDescriptionKey(), null, process.getProcessName(),
								process.getCommune().getKey(), process.getContext().getLocale()));

						break;
					case 11:
						ppd.setPaymentStatus("");
						break;
					case 12:
						ppd.setPaymentStatus(MessageBundleHelper.message(
								PaymentStatusEnum.paymentAborted.getDescriptionKey(), null, process.getProcessName(),
								process.getCommune().getKey(), process.getContext().getLocale()));

						break;
					default:
						ppd.setPaymentStatus("");
					}
				}
				datiEstesiListMyPage(process, request, rs.getString("process_name"), ps, ppd,
						Long.valueOf(rs.getLong("oid")));

				listaPratiche.add(ppd);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}
		return listaPratiche;
	}

	private Date createDate(String dataString) {
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Date d = null;
		try {
			d = df.parse(dataString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d;
	}

	public ArrayList getListaPraticheInviate(AbstractPplProcess process, IRequestWrapper request) {
		Integer offset = Integer.valueOf(0);
		if ((request.getParameter("pager.offset") != null) && (!"".equals(request.getParameter("pager.offset")))) {
			offset = Integer.valueOf(Integer.parseInt(request.getParameter("pager.offset")));
		}
		ProcessData dataForm = (ProcessData) process.getData();
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ArrayList listaPratiche = new ArrayList();
		try {
			PeopleContext pplContext = PeopleContext.create(request.getUnwrappedRequest());

			String communeId = pplContext.getCommune().getKey();
			open();
			String userID = pplContext.getUser().getUserID();

			String sqlCount = "SELECT count(*) numeroPratiche FROM submitted_process AS A  LEFT OUTER JOIN detail_process AS C ON A.PEOPLE_PROTOCOL_ID=C.process_data_id INNER JOIN pending_process AS B ON A.editable_process_id=B.oid INNER JOIN submitted_process_history AS D on A.oid = D.sbmt_process_id WHERE A.user_id='"
					+ userID + "' AND D.status_id='1'  AND A.commune_id='" + communeId + "' ";
			if ((dataForm.getCodicePratica() != null) && (!dataForm.getCodicePratica().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + " and A.people_protocol_id= ?";
			}
			if ((dataForm.getDataInvioDa() != null) && (!dataForm.getDataInvioDa().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + "and A.submitted_time>=? ";
			}
			if ((dataForm.getDataInvioA() != null) && (!dataForm.getDataInvioA().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + "and A.submitted_time<=? ";
			}
			if ((dataForm.getOggetto() != null) && (!dataForm.getOggetto().equalsIgnoreCase(""))) {
				sqlCount = sqlCount + "and oggetto LIKE ?  ";
			}
			ps = getLegacyDbConnection().prepareStatement(sqlCount);
			int iCount = 1;
			if ((dataForm.getCodicePratica() != null) && (!dataForm.getCodicePratica().equalsIgnoreCase(""))) {
				ps.setString(iCount, dataForm.getCodicePratica());
				iCount++;
			}
			if ((dataForm.getDataInvioDa() != null) && (!dataForm.getDataInvioDa().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataInvioDa());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(iCount, dataString);
				iCount++;
			}
			if ((dataForm.getDataInvioA() != null) && (!dataForm.getDataInvioA().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataInvioA());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(iCount, dataString + " 23:59:59");
				iCount++;
			}
			if ((dataForm.getOggetto() != null) && (!dataForm.getOggetto().equalsIgnoreCase(""))) {
				ps.setString(iCount, "%" + dataForm.getOggetto() + "%");
				iCount++;
			}
			rs = ps.executeQuery();
			int numPratiche = 0;
			while (rs.next()) {
				numPratiche = rs.getInt("numeroPratiche");
			}
			dataForm.setNumPratiche(String.valueOf(numPratiche));

			String sql = "SELECT A.oid,A.editable_process_id,A.user_id,A.people_protocol_id,A.commune_protocol_id,A.submitted_time, B.content_name, c.oggetto , b.process_name FROM submitted_process AS A  LEFT OUTER JOIN detail_process AS C ON A.PEOPLE_PROTOCOL_ID=C.process_data_id INNER JOIN pending_process AS B ON A.editable_process_id=B.oid INNER JOIN submitted_process_history AS D on A.oid = D.sbmt_process_id WHERE A.user_id='"
					+ userID + "' AND D.status_id='1'  AND A.commune_id='" + communeId + "' ";
			if ((dataForm.getCodicePratica() != null) && (!dataForm.getCodicePratica().equalsIgnoreCase(""))) {
				sql = sql + " and A.people_protocol_id= ?";
			}
			if ((dataForm.getDataInvioDa() != null) && (!dataForm.getDataInvioDa().equalsIgnoreCase(""))) {
				sql = sql + "and A.submitted_time>=? ";
			}
			if ((dataForm.getDataInvioA() != null) && (!dataForm.getDataInvioA().equalsIgnoreCase(""))) {
				sql = sql + "and A.submitted_time<=? ";
			}
			if ((dataForm.getOggetto() != null) && (!dataForm.getOggetto().equalsIgnoreCase(""))) {
				sql = sql + "and oggetto LIKE ?  ";
			}
			sql = sql + " ORDER BY A.submitted_time " + dataForm.getOrdinamentoPraticheInviate();

			sql = sql + " LIMIT " + dataForm.getNumPratichePerPag() + " OFFSET " + offset;

			ps = getLegacyDbConnection().prepareStatement(sql);
			int i = 1;
			if ((dataForm.getCodicePratica() != null) && (!dataForm.getCodicePratica().equalsIgnoreCase(""))) {
				ps.setString(i, dataForm.getCodicePratica());
				i++;
			}
			if ((dataForm.getDataInvioDa() != null) && (!dataForm.getDataInvioDa().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataInvioDa());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(i, dataString);
				i++;
			}
			if ((dataForm.getDataInvioA() != null) && (!dataForm.getDataInvioA().equalsIgnoreCase(""))) {
				Date d = createDate(dataForm.getDataInvioA());
				String dataString = new SimpleDateFormat("yyyy-MM-dd").format(d);

				ps.setString(i, dataString + " 23:59:59");
				i++;
			}
			if ((dataForm.getOggetto() != null) && (!dataForm.getOggetto().equalsIgnoreCase(""))) {
				ps.setString(i, "%" + dataForm.getOggetto() + "%");
				i++;
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				PraticaBean sp = new PraticaBean();
				sp.setOid(String.valueOf(rs.getLong("oid")));
				sp.setEditableProcessId(String.valueOf(rs.getLong("editable_process_id")));

				sp.setPeopleProtocollId(rs.getString("people_protocol_id"));
				sp.setProcessDataID(sp.getPeopleProtocollId());
				sp.setUserID(userID);
				sp.setContentName(rs.getString("content_name"));
				String oggetto = rs.getString("oggetto");
				if (oggetto == null) {
					sp.setOggetto("");
				} else {
					sp.setOggetto(oggetto);
				}
				String sql2 = "SELECT * FROM submitted_process_history WHERE sbmt_process_id='" + sp.getOid() + "'";

				ps2 = getLegacyDbConnection().prepareStatement(sql2);
				rs2 = ps2.executeQuery();
				ArrayList listaAttivita = new ArrayList();
				while (rs2.next()) {
					ActivityBean bb = new ActivityBean();
					Timestamp timeStamp = rs2.getTimestamp("transaction_time");

					int status_id = rs2.getInt("status_id");

					bb.setCodice(String.valueOf(status_id));
					bb.setTimestamp(timeStamp);

					listaAttivita.add(bb);
				}
				sp.setActivityHistory(listaAttivita);

				datiEstesiListMyPage(process, request, rs.getString("process_name"), ps, sp,
						Long.valueOf(rs.getLong("editable_process_id")));

				listaPratiche.add(sp);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				rs2.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				ps2.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}
		return listaPratiche;
	}

	public ArrayList getListaPraticheErroreInvio(AbstractPplProcess process, IRequestWrapper request) {
		ProcessData dataForm = (ProcessData) process.getData();
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ArrayList listaPratiche = new ArrayList();
		try {
			PeopleContext pplContext = PeopleContext.create(request.getUnwrappedRequest());

			Integer offset = Integer.valueOf(0);
			if ((request.getParameter("pager.offset") != null) && (!"".equals(request.getParameter("pager.offset")))) {
				offset = Integer.valueOf(Integer.parseInt(request.getParameter("pager.offset")));
			}
			String communeId = pplContext.getCommune().getKey();
			open();
			String userID = pplContext.getUser().getUserID();

			String sqlCount = "SELECT count(*) numeroPratiche FROM submitted_process AS A  LEFT OUTER JOIN detail_process AS C ON A.PEOPLE_PROTOCOL_ID=C.process_data_id INNER JOIN pending_process AS B ON A.editable_process_id=B.oid WHERE A.oid in ("
					+ getOidClause(dataForm.getFailedSendOids()) + ")";

			ps = getLegacyDbConnection().prepareStatement(sqlCount);
			rs = ps.executeQuery();
			int numPratiche = 0;
			while (rs.next()) {
				numPratiche = rs.getInt("numeroPratiche");
			}
			dataForm.setNumPratiche(String.valueOf(numPratiche));

			String sql = "SELECT B.process_name, A.oid,A.editable_process_id,A.user_id,A.people_protocol_id,A.commune_protocol_id,A.submitted_time, B.content_name, c.oggetto , b.process_name FROM submitted_process AS A  LEFT OUTER JOIN detail_process AS C ON A.PEOPLE_PROTOCOL_ID=C.process_data_id INNER JOIN pending_process AS B ON A.editable_process_id=B.oid WHERE A.oid in ("
					+ getOidClause(dataForm.getFailedSendOids()) + ")";

			sql = sql + " ORDER BY A.submitted_time " + dataForm.getOrdinamentoPraticheInviate();

			sql = sql + " LIMIT " + dataForm.getNumPratichePerPag() + " OFFSET " + offset;

			ps = getLegacyDbConnection().prepareStatement(sql);
			int i = 1;
			PraticaBean sp;
			for (rs = ps.executeQuery(); rs.next(); listaPratiche.add(sp)) {
				sp = new PraticaBean();
				sp.setOid(String.valueOf(rs.getLong("oid")));
				sp.setEditableProcessId(String.valueOf(rs.getLong("editable_process_id")));

				sp.setPeopleProtocollId(rs.getString("people_protocol_id"));
				sp.setProcessDataID(sp.getPeopleProtocollId());
				sp.setUserID(userID);
				sp.setContentName(rs.getString("content_name"));
				sp.setSendError(true);
				sp.setProcessName(rs.getString("process_name"));
				String oggetto = rs.getString("oggetto");
				if (oggetto == null) {
					sp.setOggetto("");
				} else {
					sp.setOggetto(oggetto);
				}
				String sql2 = "SELECT * FROM submitted_process_history WHERE sbmt_process_id='" + sp.getOid() + "'";

				ps2 = getLegacyDbConnection().prepareStatement(sql2);
				rs2 = ps2.executeQuery();
				ArrayList listaAttivita = new ArrayList();
				ActivityBean bb;
				for (; rs2.next(); listaAttivita.add(bb)) {
					bb = new ActivityBean();
					Timestamp timeStamp = rs2.getTimestamp("transaction_time");
					int status_id = rs2.getInt("status_id");
					bb.setCodice(String.valueOf(status_id));
					bb.setTimestamp(timeStamp);
				}
				sp.setActivityHistory(listaAttivita);

				datiEstesiListMyPage(process, request, rs.getString("process_name"), ps, sp,
						Long.valueOf(rs.getLong("editable_process_id")));
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		try {
			rs.close();
		} catch (Exception exception1) {
		}
		try {
			rs2.close();
		} catch (Exception exception2) {
		}
		try {
			ps.close();
		} catch (Exception exception3) {
		}
		try {
			ps2.close();
		} catch (Exception exception4) {
		}
		try {
			closeConnections();
		} catch (Exception exception5) {
		}
		try {
			rs.close();
		} catch (Exception exception6) {
		}
		try {
			rs2.close();
		} catch (Exception exception7) {
		}
		try {
			ps.close();
		} catch (Exception exception8) {
		}
		try {
			ps2.close();
		} catch (Exception exception9) {
		}
		try {
			closeConnections();
		} catch (Exception exception10) {
		}
		try {
			rs.close();
		} catch (Exception exception11) {
		}
		try {
			rs2.close();
		} catch (Exception exception12) {
		}
		try {
			ps.close();
		} catch (Exception exception13) {
		}
		try {
			ps2.close();
		} catch (Exception exception14) {
		}
		try {
			closeConnections();
		} catch (Exception exception15) {
		}
		return listaPratiche;
	}

	public PraticaBeanExtended getDettaglioPratica(IRequestWrapper request, AbstractPplProcess process, int oid) {
		PraticaBeanExtended pratica = new PraticaBeanExtended();
		StatusConverter statusConverter = new StatusConverter();
		ResultSet rs = null;
		ProcessData dataForm = (ProcessData) process.getData();
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		ResultSet rs2 = null;
		ArrayList listaPratiche = new ArrayList();
		try {
			PeopleContext pplContext = PeopleContext.create(request.getUnwrappedRequest());

			String communeId = pplContext.getCommune().getKey();
			open();
			String userID = pplContext.getUser().getUserID();
			String sql = "SELECT " + getLegacyDbDatabaseName() + "pending_process.oid," + getLegacyDbDatabaseName()
					+ "pending_process.status," + getLegacyDbDatabaseName() + "pending_process.process_name,"
					+ getLegacyDbDatabaseName() + "pending_process.process_data_id AS process_data, "
					+ getLegacyDbDatabaseName() + "pending_process.process_classname," + getLegacyDbDatabaseName()
					+ "pending_process.process_value, " + getLegacyDbDatabaseName() + "pending_process.commune_id, "
					+ getLegacyDbDatabaseName() + "pending_process.content_name, " + getLegacyDbDatabaseName()
					+ "pending_process.user_id, " + getLegacyDbDatabaseName() + "pending_process.creation_time,"
					+ getLegacyDbDatabaseName() + "pending_process.last_modified_time," + getLegacyDbDatabaseName()
					+ "detail_process.oggetto," + getLegacyDbDatabaseName() + "detail_process.descrizione, "
					+ getLegacyDbDatabaseName() + "submitted_process.editable_process_id," + getLegacyDbDatabaseName()
					+ "submitted_process.commune_protocol_id," + getLegacyDbDatabaseName()
					+ "submitted_process.submitted_time, " + getFedbDatabaseName()
					+ "process_status.status as paymentStatus " + " FROM " + getLegacyDbDatabaseName()
					+ "pending_process " + " LEFT OUTER JOIN " + getLegacyDbDatabaseName() + "detail_process ON "
					+ getLegacyDbDatabaseName() + "pending_process.process_data_id=" + getLegacyDbDatabaseName()
					+ "detail_process.process_data_id " + " LEFT OUTER JOIN " + getLegacyDbDatabaseName()
					+ "submitted_process ON " + getLegacyDbDatabaseName() + "pending_process.process_data_id="
					+ getLegacyDbDatabaseName() + "submitted_process.PEOPLE_PROTOCOL_ID " + " LEFT OUTER JOIN "
					+ getFedbDatabaseName() + "process_status ON " + getLegacyDbDatabaseName()
					+ "pending_process.process_data_id=" + getFedbDatabaseName() + "process_status.peopleid "
					+ " WHERE ";
			if (dataForm.isInCompilazione()) {
				sql = sql + " " + getLegacyDbDatabaseName() + "pending_process.oid=?";
			} else {
				sql = sql + " " + getLegacyDbDatabaseName() + "submitted_process.oid=?";
			}
			ps = getLegacyDbConnection().prepareStatement(sql);
			ps.setInt(1, oid);
			rs = ps.executeQuery();
			if (rs.next()) {
				pratica.setProcessDataID(rs.getString("process_data"));
				pratica.setContentName(rs.getString("content_name"));
				pratica.setUserID(rs.getString("user_id"));

				Timestamp tmp = rs.getTimestamp("creation_time");
				String s = DateFormatter.format(tmp);
				pratica.setCreation_time(s);

				tmp = rs.getTimestamp("submitted_time");
				if (tmp != null) {
					String sss = DateFormatter.format(tmp);
					pratica.setSubmitted_time(sss);
				}
				tmp = rs.getTimestamp("last_modified_time");
				if (tmp != null) {
					String ss = DateFormatter.format(tmp);
					pratica.setLast_modified_time(ss);
				}
				pratica.setProcessName(rs.getString("process_name"));
				pratica.setStatus((String) statusConverter.sqlToJava(new Integer(rs.getInt("status"))));

				pratica.setOid(String.valueOf(rs.getLong("oid")));
				String oggetto = rs.getString("oggetto");
				if (oggetto == null) {
					pratica.setOggetto("");
				} else {
					pratica.setOggetto(oggetto);
				}
				String descrizione = rs.getString("descrizione");
				if (descrizione == null) {
					pratica.setDescrizione("");
				} else {
					pratica.setDescrizione(descrizione);
				}
				String paymentStatus = rs.getString("paymentStatus");
				if (paymentStatus == null) {
					pratica.setPaymentStatus("");
				} else {
					int paymentStatusCode = Integer.parseInt(paymentStatus);
					switch (paymentStatusCode) {
					case 10:
						pratica.setPaymentStatus(MessageBundleHelper.message(
								PaymentStatusEnum.paymentPending.getDescriptionKey(), null, process.getProcessName(),
								process.getCommune().getKey(), process.getContext().getLocale()));

						break;
					case 11:
						pratica.setPaymentStatus("");
						break;
					case 12:
						pratica.setPaymentStatus(MessageBundleHelper.message(
								PaymentStatusEnum.paymentAborted.getDescriptionKey(), null, process.getProcessName(),
								process.getCommune().getKey(), process.getContext().getLocale()));

						break;
					default:
						pratica.setPaymentStatus("");
					}
				}
				String xmlPratica = rs.getString("process_value");
				String id = rs.getString("process_data");
				if ((xmlPratica != null) && (!xmlPratica.equalsIgnoreCase(""))) {
					pratica.setXMLprocessData(xmlPratica);
					AbstractPplProcess pplProc = null;
					PersistenceManager persMgr = null;
					try {
						persMgr = PersistenceManagerFactory.getInstance().get(PplPersistentData.class,
								PersistenceManager.Mode.READ);

						Collection process_ = null;
						Criteria crtr = new Criteria();
						crtr.addEqualTo("processDataID", id);

						process_ = persMgr.get(QueryFactory.newQuery(PplPersistentData.class, crtr));
						if ((process_ != null) && (!process_.isEmpty())) {
							Iterator iter = process_.iterator();
							AbstractPplProcess p_actionForm = new GenericProcess();
							PplPersistentData ppd = (PplPersistentData) iter.next();

							pplProc = ProcessManager.getInstance()
									.create(PeopleContext.create(request.getUnwrappedRequest()), ppd.getProcessClass());

							IWorkFlowFactory factory = new WorkFlowFactory();
							IWorkFlow wFlow = factory.createWorkFlow();
							HttpSession session = null;
							if ((request != null) && (request.getUnwrappedRequest() != null)) {
								session = request.getUnwrappedRequest().getSession();
							}
							IProcess process__ = wFlow.getProcess(session, ppd.getProcessName(), ppd.getCommune());

							pplProc.setProcessWorkflow(process__);
							pplProc.setProcessName(ppd.getProcessName());
							pplProc.setOid(ppd.getOid());
							pplProc.setOwner(ppd.getUserID());

							pplProc.setCurrentContentID(ppd.getContentID());
							pplProc.setCurrentContent(ppd.getContentName());
							pplProc.setCommune(ppd.getCommune());
							pplProc.initialize(PeopleContext.create(request.getUnwrappedRequest()), ppd.getCommune(),
									Device.HTML);

							pplProc.setCreationTime(ppd.getCreationTime());
							pplProc.setLastModifiedTime(ppd.getLastModifiedTime());

							pplProc.setSent(ppd.getSent());
							pplProc.setStatus(ppd.getStatus());
							pplProc.setPrincipal(ppd.getPrincipal());
							pplProc.setDelegate(ppd.getDelegate());
							pplProc.setMarshalledData(xmlPratica);
							pratica.setProcessDataPratica((AbstractData) pplProc.getData());
						}
					} catch (Exception e) {
						System.out.println("Err");
					} catch (peopleException e) {
						System.out.println("Err");
					} finally {
						if (persMgr != null) {
							persMgr.close();
						}
					}
				}
				if ((pratica.getSubmitted_time() != null) && (!pratica.getSubmitted_time().equalsIgnoreCase(""))) {
					String sql2 = "SELECT * FROM submitted_process_history WHERE sbmt_process_id=?";
					ps2 = getLegacyDbConnection().prepareStatement(sql2);
					ps2.setInt(1, oid);
					rs2 = ps2.executeQuery();
					ArrayList listaAttivita = new ArrayList();
					while (rs2.next()) {
						ActivityBean bb = new ActivityBean();
						Timestamp timeStamp = rs2.getTimestamp("transaction_time");

						int status_id = rs2.getInt("status_id");
						bb.setCodice(String.valueOf(status_id));
						bb.setTimestamp(timeStamp);
						listaAttivita.add(bb);
					}
					pratica.setActivityHistory(listaAttivita);
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				rs2.close();
			} catch (Exception e) {
			}
			try {
				ps2.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}
		return pratica;
	}

	public ArrayList getAltriDichiaranti(IRequestWrapper request, AbstractPplProcess process,
			PraticaBeanExtended pratica) {
		ResultSet rs = null;
		ProcessData dataForm = (ProcessData) process.getData();
		PreparedStatement ps = null;
		ArrayList listaAltriDichiaranti = new ArrayList();
		try {
			PeopleContext pplContext = PeopleContext.create(request.getUnwrappedRequest());

			String communeId = pplContext.getCommune().getKey();
			open();
			String userID = pplContext.getUser().getUserID();
			String sql = "";
			if (dataForm.isInCompilazione()) {
				sql = "select titolari_process.* from titolari_process inner JOIN pending_process on pending_process.process_data_id=titolari_process.process_data_id where pending_process.oid="
						+ dataForm.getOid();
			} else {
				sql = "SELECT titolari_process.* FROM titolari_process INNER JOIN submitted_process ON submitted_process.people_protocol_id=titolari_process.process_data_id WHERE submitted_process.oid="
						+ dataForm.getOid();
			}
			sql = "SELECT * FROM titolari_process WHERE process_data_id like '%" + pratica.getProcessDataID() + "%'";

			ps = getLegacyDbConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				String xmlDichiarante = rs.getString("xml_info");
				Titolare titolare = new Titolare();

				String pfString = rs.getString("persona_fisica");
				if ((pfString != null) && (pfString.equalsIgnoreCase("S"))) {
					PersonaFisica pf = new PersonaFisica();
					pf.setNome(rs.getString("nome"));
					pf.setCognome(rs.getString("cognome"));
					pf.setCodiceFiscale(rs.getString("codice_fiscale"));
					titolare.setPersonaFisica(pf);
				} else {
					PersonaGiuridica pg = new PersonaGiuridica();
					pg.setDenominazione(rs.getString("denominazione"));
					pg.setPartitaIVA(rs.getString("partita_iva"));
					titolare.setPersonaGiuridica(pg);
				}
				listaAltriDichiaranti.add(titolare);
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}
		return listaAltriDichiaranti;
	}

	public ArrayList getListaCertificati(IRequestWrapper request, AbstractPplProcess process,
			PraticaBeanExtended pratica) {
		ArrayList listaCertificati = new ArrayList();
		try {
			String basePathName = PeopleProperties.UPLOAD_DIRECTORY.getValueString(process.getCommune().getKey());

			File rootDirNodo = new File(basePathName + File.separator + process.getCommune().getKey());
			if (rootDirNodo.exists()) {
				File rootDirPratica = new File(basePathName + File.separator + process.getCommune().getKey()
						+ File.separator + pratica.getProcessDataID());
				if (rootDirPratica.exists()) {
					basePathName = basePathName + File.separator + process.getCommune().getKey() + File.separator
							+ pratica.getProcessDataID();

					File rootDirCertificatiPratica = new File(basePathName + File.separator + "cert");
					if (rootDirCertificatiPratica.exists()) {
						File[] listaF = rootDirCertificatiPratica.listFiles();
						if (listaF != null) {
							for (int i = 0; i < listaF.length; i++) {
								if (listaF[i].isFile()) {
									String nomeFile = listaF[i].getName();
									int indexSplit = nomeFile.indexOf("_");
									if (indexSplit != -1) {
										Attachment attach = new Attachment();
										String codice = nomeFile.substring(0, indexSplit);

										attach.setDescrizione(codice);
										String nome = nomeFile.substring(indexSplit + 1, nomeFile.length());

										attach.setName(nome);
										attach.setPath(listaF[i].getAbsolutePath());

										listaCertificati.add(attach);
									}
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		return listaCertificati;
	}

	public List getAltriFileAllegati(AbstractPplProcess process, PraticaBeanExtended pratica) {
		List result = new ArrayList();
		try {
			String basePath = PeopleProperties.UPLOAD_DIRECTORY.getValueString(process.getCommune().getKey());

			File rootDirNodo = new File(basePath + File.separator + process.getCommune().getKey());
			if (rootDirNodo.exists()) {
				File rootDirPratica = new File(basePath + File.separator + process.getCommune().getKey()
						+ File.separator + pratica.getProcessDataID());
				if (rootDirPratica.exists()) {
					File[] files = rootDirPratica.listFiles(new NoCertFolderFilter());
					if (files != null) {
						for (int index = 0; index < files.length; index++) {
							if (files[index].isFile()) {
								String nomeFile = files[index].getName();
								int indexSplit = nomeFile.indexOf("_");
								if (indexSplit != -1) {
									Attachment attach = new Attachment();
									String codice = nomeFile.substring(0, indexSplit);

									attach.setDescrizione(codice);
									String nome = nomeFile.substring(indexSplit + 1, nomeFile.length());

									attach.setName(nome);
									attach.setPath(files[index].getAbsolutePath());

									attach.setFileLenght(new Long(files[index].length()).intValue());

									result.add(attach);
								}
							}
						}
					}
				}
			}
		} catch (PropertyFormatException e) {
			e.printStackTrace();
		}
		return result;
	}

	private List listAllSubfiles(File source) {
		List lists = new ArrayList();
		addSubFilesToList(lists, source);
		return lists;
	}

	private void addSubFilesToList(List list, File source) {
		File[] files = source.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					list.add(files[i]);
				}
				addSubFilesToList(list, files[i]);
			}
		}
	}

	public InfoBoBean getInfoBO(ProcessData dataForm) {
		ResultSet rs = null;
		PreparedStatement ps = null;
		InfoBoBean info = null;
		try {
			String processDataID = Utilities.NVL(dataForm.getDettaglioPratica().getProcessDataID(), "");

			open();
			String sql = "select * from eventi_process where process_data_id='" + processDataID + "'";

			ps = getLegacyDbConnection().prepareStatement(sql);
			rs = ps.executeQuery();
			if (rs.next()) {
				info = new InfoBoBean();
				info.setAltreInfo(rs.getString("note"));
				info.setDescrizione_evento(rs.getString("DESCRIPTION_EVENT"));
				info.setId_bo(rs.getString("ID_BO"));
				info.setProcess_data_id(rs.getString("PROCESS_DATA_ID"));

				Timestamp ts = rs.getTimestamp("DATE_RECEIVED");
				if (ts != null) {
					info.setTimestamp_evento(ts.getTime());
				}
				info.setUrl_visura(rs.getString("URL_VISURA"));
				info.setVisibilita(Utilities.DB2Checkbox(rs.getString("VISIBILE")));
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}
		return info;
	}

	private Connection getFedbConnection() {
		return this.fedbConnection;
	}

	private void setFedbConnection(Connection fedbConnection) {
		this.fedbConnection = fedbConnection;
	}

	private Connection getLegacyDbConnection() {
		return this.legacyDbConnection;
	}

	private void setLegacyDbConnection(Connection legacyDbConnection) {
		this.legacyDbConnection = legacyDbConnection;
	}

	private String getFedbDatabaseName() {
		return this.fedbDatabaseName;
	}

	private void setFedbDatabaseName(String fedbDatabaseName) {
		this.fedbDatabaseName = (fedbDatabaseName != null ? fedbDatabaseName.toLowerCase() + "." : "");
	}

	private String getLegacyDbDatabaseName() {
		return this.legacyDbDatabaseName;
	}

	private void setLegacyDbDatabaseName(String legacyDbDatabaseName) {
		this.legacyDbDatabaseName = (legacyDbDatabaseName != null ? legacyDbDatabaseName.toLowerCase() + "." : "");
	}

	private void closeConnections() throws SQLException {
		if (getLegacyDbConnection() != null) {
			getLegacyDbConnection().close();
		}
		if (getFedbConnection() != null) {
			getFedbConnection().close();
		}
	}

	private String getUserIds(String userId, Vector<String> categoryAssociationAccreditations) {
		return "= '" + userId + "' ";
	}

	private String getOidClause(Vector oids) {
		StringBuilder result = new StringBuilder();
		if (!oids.isEmpty()) {
			for (int index = 0; index < oids.size() - 1; index++) {
				result.append("'").append(oids.get(index)).append("',");
			}
			result.append("'").append(oids.get(oids.size() - 1)).append("'");
		}
		return result.toString();
	}

	private void datiEstesiListMyPage(AbstractPplProcess process, IRequestWrapper request, String pack,
			PreparedStatement ps, PraticaBean ppd, Long oid) throws Exception {
		ResultSet rsExtended = null;
		String xml = null;
		String sql = "select process_value from pending_process where oid= ?";
		try {
			ps = getLegacyDbConnection().prepareStatement(sql);
			ps.setLong(1, oid.longValue());
			rsExtended = ps.executeQuery();
			if (rsExtended.next()) {
				xml = rsExtended.getString("process_value");
			}
			if ((xml != null) && (!"".equals(xml))) {
				String xmlOutput = "";
				String resourcePath = System.getProperty("file.separator") + pack.replaceAll("\\.", "/")
						+ System.getProperty("file.separator") + "risorse" + System.getProperty("file.separator");

				String communeXslFileName = "xsltDatiEstesiMyPage_" + process.getCommune().getOid() + ".xsl";

				URL is = getClass().getClassLoader().getResource(resourcePath + communeXslFileName);
				File templateIn = null;
				if (is == null) {
					is = getClass().getClassLoader().getResource(resourcePath + "xsltDatiEstesiMyPage.xsl");
				}
				if (is != null) {
					templateIn = new File(is.toURI());
				}
				if (templateIn != null) {
					String encoding = request.getUnwrappedRequest().getCharacterEncoding();
					OutputStream os = new ByteArrayOutputStream();
					ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes(encoding));
					OutputStreamWriter osWriter = new OutputStreamWriter(os, encoding);
					TransformerFactory tFactory = TransformerFactory.newInstance();
					Transformer transformer = tFactory.newTransformer(new StreamSource(templateIn));
					transformer.setOutputProperty("encoding", encoding);
					transformer.transform(new StreamSource(bais), new StreamResult(osWriter));

					xmlOutput = ((ByteArrayOutputStream) os).toString(encoding);
					DatiEstesiMyPageDocument a = DatiEstesiMyPageDocument.Factory.parse(xmlOutput);
					DatiEstesiMyPageDocument.DatiEstesiMyPage b = a.getDatiEstesiMyPage();
					DatiEstesiMyPageDocument.DatiEstesiMyPage.Deleganti deleganti = b.getDeleganti();
					DatiEstesiMyPage datiEstesiMyPage = new DatiEstesiMyPage();
					ArrayList<DeleganteMyPage> delegantiMyPage = new ArrayList();
					for (DatiEstesiMyPageDocument.DatiEstesiMyPage.Deleganti.Delegante delegante : deleganti
							.getDeleganteArray()) {
						DeleganteMyPage amp = new DeleganteMyPage();
						amp.setCognome(delegante.getCognome());
						amp.setNome(delegante.getNome());
						amp.setCodiceFiscale(delegante.getCodiceFiscale());
						amp.setDenominazione(delegante.getDenominazione());
						amp.setPartitaIva(delegante.getPartitaIva());
						delegantiMyPage.add(amp);
					}
					datiEstesiMyPage.setDelegantiMyPage(delegantiMyPage);
					DatiEstesiMyPageDocument.DatiEstesiMyPage.Indirizzi indirizziList = b.getIndirizzi();
					ArrayList<IndirizzoMyPage> indirizziMyPage = new ArrayList();
					for (DatiEstesiMyPageDocument.DatiEstesiMyPage.Indirizzi.Indirizzo indirizzo : indirizziList
							.getIndirizzoArray()) {
						IndirizzoMyPage imp = new IndirizzoMyPage();
						imp.setComune(indirizzo.getComune());
						imp.setVia(indirizzo.getVia());
						imp.setCivico(indirizzo.getCivico());
						imp.setColore(indirizzo.getColore());
						imp.setLettera(indirizzo.getLettera());
						imp.setCap(indirizzo.getCap());
						imp.setScala(indirizzo.getScala());
						imp.setInterno(indirizzo.getInterno());
						imp.setInternoLettera(indirizzo.getInternoLettera());
						imp.setPiano(indirizzo.getPiano());
						indirizziMyPage.add(imp);
					}
					datiEstesiMyPage.setIndirizziMyPage(indirizziMyPage);
					ppd.setDatiEstesiMyPage(datiEstesiMyPage);
				}
			}
		} finally {
			try {
				rsExtended.close();
			} catch (Exception e) {
			}
		}
	}


	public List<Attachment> getElencoAllegati(AbstractPplProcess process, IRequestWrapper request) {
		ResultSet rs = null;
		ProcessData dataForm = (ProcessData) process.getData();
		PreparedStatement ps = null;
		List<Attachment> listaAllegati = new ArrayList<Attachment>();
		try {
			open();
			String sql = "SELECT * FROM " + getLegacyDbDatabaseName()
					+ "allegati_interop where identificativo_pratica = ? and stato_invio = 0;";
			
			ps = getLegacyDbConnection().prepareStatement(sql);

			if ((dataForm.getDettaglioPratica() != null)
					&& (!dataForm.getDettaglioPratica().getProcessDataID().equalsIgnoreCase(""))) {

				ps.setString(1, dataForm.getDettaglioPratica().getProcessDataID());

			}
			
			rs = ps.executeQuery();
			while (rs.next()) {
				Attachment allegato = new Attachment();
				allegato.setName(rs.getString("file_name"));
				allegato.setDescrizione(rs.getString("descrizione_allegato"));
				allegato.setData(rs.getString("file"));
				listaAllegati.add(allegato);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}

		return listaAllegati;
	}


	public void cancellaAllegato(String nomefile) throws SQLException{
		ResultSet rs = null;
	    Connection conn = null;
	    PreparedStatement ps = null;
	    String sql = "delete from "+ getLegacyDbDatabaseName()
		+"allegati_interop where file_name = ?";
	    try {
            open();
            ps = getLegacyDbConnection().prepareStatement(sql);
            ps.setString(1, nomefile);
            ps.execute();
        } catch (SQLException e) {
            throw e;
        }finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}
		
	}
	
	public List<TipologiaEventoIntegrazione> getTipologiaEventi(AbstractPplProcess process, IRequestWrapper request) {
		ResultSet rs = null;
		ProcessData dataForm = (ProcessData) process.getData();
		PreparedStatement ps = null;
		List<TipologiaEventoIntegrazione> listaTipologiaEventi = new ArrayList<TipologiaEventoIntegrazione>();
		try {
			open();
//			String sql = "SELECT opencross.processi_eventi.cod_evento, opencross.processi_eventi.des_evento " + 
//					"FROM opencross.pratica " + 
//					"inner join opencross.processi_eventi on opencross.processi_eventi.id_processo = opencross.pratica.id_processo " + 
//					"where opencross.pratica.identificativo_pratica = ? and opencross.processi_eventi.verso ='I' and des_evento like '%(amm)%';";
			String sql = "SELECT opencross.processi_eventi.cod_evento, opencross.processi_eventi.des_evento " + 
					"FROM opencross.pratica " + 
					"inner join opencross.processi_eventi on opencross.processi_eventi.id_processo = opencross.pratica.id_processo " + 
					"where opencross.pratica.identificativo_pratica = ? and opencross.processi_eventi.flg_integrazione = 'S';";
			
			ps = getLegacyDbConnection().prepareStatement(sql);

			if ((dataForm.getDettaglioPratica() != null)
					&& (!dataForm.getDettaglioPratica().getProcessDataID().equalsIgnoreCase(""))) {

				ps.setString(1, dataForm.getDettaglioPratica().getProcessDataID());

			}
			
			rs = ps.executeQuery();
			while (rs.next()) {
				TipologiaEventoIntegrazione tipoEvento = new TipologiaEventoIntegrazione();
				tipoEvento.setCodiceEvento(rs.getString("cod_evento"));
				tipoEvento.setDescrizioneEvento(rs.getString("des_evento"));
				listaTipologiaEventi.add(tipoEvento);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}

		return listaTipologiaEventi;
	}


	public void updateStatoAllegatoInterop(AbstractPplProcess process, IRequestWrapper request) {
		ProcessData dataForm = (ProcessData) process.getData();
		PreparedStatement ps = null;
		try {
			open();
			String sqlSave = "UPDATE people_bari.allegati_interop " + 
					"SET people_bari.allegati_interop.stato_invio = 1 " + 
					"WHERE people_bari.allegati_interop.identificativo_pratica= ?;";
			ps = getLegacyDbConnection().prepareStatement(sqlSave);
			if(dataForm.getDettaglioPratica() != null && dataForm.getDettaglioPratica().getProcessDataID() != null 
					&& dataForm.getDettaglioPratica().getProcessDataID().trim().length() != 0)
				ps.setString(1, dataForm.getDettaglioPratica().getProcessDataID());
			
						
			ps.executeUpdate();
			
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}
		
	}
	
	public String getCodiceEnteByIdentificativoPratica(AbstractPplProcess process, IRequestWrapper request) {
		ResultSet rs = null;
		ProcessData dataForm = (ProcessData) process.getData();
		PreparedStatement ps = null;
		String codiceEnte = null;
		try {
			open();
			String sql = "SELECT opencross.enti.cod_ente " + 
					"FROM opencross.pratica " + 
					"INNER JOIN opencross.procedimenti_enti on opencross.procedimenti_enti.id_proc_ente = opencross.pratica.id_proc_ente " + 
					"INNER JOIN opencross.enti on opencross.enti.id_ente = opencross.procedimenti_enti.id_ente " + 
					"WHERE opencross.pratica.identificativo_pratica = ?";
			
			ps = getLegacyDbConnection().prepareStatement(sql);

			if ((dataForm.getDettaglioPratica() != null)
					&& (!dataForm.getDettaglioPratica().getProcessDataID().equalsIgnoreCase(""))) {

				ps.setString(1, dataForm.getDettaglioPratica().getProcessDataID());

			}
			
			rs = ps.executeQuery();
			while (rs.next()) {
				codiceEnte = rs.getString("cod_ente");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				rs.close();
			} catch (Exception e) {
			}
			try {
				ps.close();
			} catch (Exception e) {
			}
			try {
				closeConnections();
			} catch (Exception e) {
			}
		}

		return codiceEnte;
	}
}
