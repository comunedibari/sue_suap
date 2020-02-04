package it.people.console.web.controllers;

import static it.people.console.web.servlet.tags.TagsConstants.LIST_HOLDER_TABLE_PREFIX;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.rpc.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import it.people.console.beans.support.IFilters;
import it.people.console.domain.AbstractBaseBean;
import it.people.console.domain.UserNotificationProcessBean;
import it.people.console.domain.exceptions.PagedListHoldersCacheException;
import it.people.console.persistence.beans.support.EditableRowInputData;
import it.people.console.persistence.beans.support.FilterProperties;
import it.people.console.persistence.beans.support.ILazyPagedListHolder;
import it.people.console.persistence.beans.support.LazyPagedListHolderFactory;
import it.people.console.persistence.exceptions.LazyPagedListHolderException;
import it.people.console.persistence.jdbc.support.CalendarConverter;
import it.people.console.persistence.jdbc.support.Decodable;
import it.people.console.security.AbstractCommand.CommandActions;
import it.people.console.utils.Constants;
import it.people.console.web.servlet.mvc.AbstractListableController;
import it.people.console.web.utils.DataUtils;
import it.people.feservice.FEInterface;
import it.people.feservice.beans.ProcessFilter;
import it.people.feservice.beans.UserNotificationBean;
import it.people.feservice.client.FEInterfaceServiceLocator;

/**
 * @author gguidi - Jun 19, 2013
 * 
 */
@Controller
@RequestMapping("/NodiFe")
@SessionAttributes({ "userNotificationProcess", "codicecomune",
		Constants.ControllerUtils.DETAILS_STATUSES_KEY })
public class FENodeUserNotificationsController extends
		AbstractListableController {

	@Autowired
	private DataUtils dataUtils;

	private URL reference;
	// Default pagination settings
	private int defaultPageSize = 10;
	private int defaultLowerPageLimit = 0;
	// Default date settings
	private DateFormat defaultDateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private DateFormat defaultDateFormatFilter = new SimpleDateFormat(
			"dd-MM-yyyy");

	private Map<String, String> detailsStatuses = new HashMap<String, String>();

	// @RequestMapping(value = "/userNotificationsSuggestion.do", method =
	// RequestMethod.GET)
	@RequestMapping(value = { "/userNotificationsSuggestion.do",
			"/userNotificationsError.do" }, method = RequestMethod.GET)
	public String setupForm(ModelMap model,
			@RequestParam(value="codicecomune", required = false) String codicecomune,
			@RequestParam(value="id", required = false) String id,
			@RequestParam(value="action", required = false) String action,
			@RequestParam(value="column", required = false) String column,
			@RequestParam(value="sort", required = false) String sort,
			@RequestParam(value="plh", required = false) String plh,
			HttpServletRequest request) {

		// User suggestion or User Error notification ?
		boolean isUserSuggestion = request.getServletPath().indexOf(
				"userNotificationsSuggestion") != -1;

		UserNotificationProcessBean userNotificationProcess = (UserNotificationProcessBean) request
				.getSession().getAttribute("userNotificationProcess");
		if (userNotificationProcess == null || plh == null) {
			userNotificationProcess = new UserNotificationProcessBean();
		}

		if (codicecomune == null) {
			codicecomune = (String) request.getSession().getAttribute(
					"codicecomune");
		}

		// UserNotificationDataVO userNotifications = getUserNotifications(null,
		// 0, 0, "xxx", "xxx", codicecomune, null, null);

		// Empty filter and lastSelectedBean
		ProcessFilter defaultFilter = new ProcessFilter();
		// request.getSession().removeAttribute("lastSelectedBean");

		if (plh != null && !"".equals(plh)) {

			try {
				applyColumnSorting(request, userNotificationProcess);
			} catch (LazyPagedListHolderException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			boolean columnSorting = false;
			// sort sulle colonne della pagelistholder
			if (column != null && !"".equals(column)) {
				if ("receivedDateFormatted".equalsIgnoreCase(column)) {
					column = "receivedDate";
				}
				userNotificationProcess.setColumn(column);
				columnSorting = true;
			}
			if (sort != null && !"".equals(sort)) {
				userNotificationProcess.setSort(sort);
				columnSorting = true;
			}
			ILazyPagedListHolder plh_now = prepareIndicatorsPagedListFiltered(
					userNotificationProcess,
					codicecomune,
					userNotificationProcess
							.getPagedListHolder(Constants.PagedListHoldersIds.USER_NOTIFICATION_LIST),
					isUserSuggestion ? UserNotificationBean.USER_NOTIFICATION_TYPE_SUGGESTION
							: UserNotificationBean.USER_NOTIFICATION_TYPE_ERROR,
					columnSorting);
			if (plh_now != null) {
				// userNotificationProcess.addPagedListHolder(plh_now);
				try {
					//applyColumnSorting(request, userNotificationProcess);
					userNotificationProcess.updatePagedListHolder(plh_now);
				} catch (PagedListHoldersCacheException e) {
					logger.error("PagedListHoldersCacheException updating paged list holder");
					e.printStackTrace();
				} 
//				catch (LazyPagedListHolderException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			*/

		} else {

			// Create PagedListHolder
			try {
				userNotificationProcess
						.addPagedListHolder(prepareIndicatorsPagedList(
								defaultFilter,
								codicecomune,
								defaultLowerPageLimit,
								defaultPageSize,
								isUserSuggestion ? UserNotificationBean.USER_NOTIFICATION_TYPE_SUGGESTION
										: UserNotificationBean.USER_NOTIFICATION_TYPE_ERROR,
								"xxx", null, null, null, null, null,
								null, null));
			} catch (LazyPagedListHolderException e) {
				logger.error("LazyPagedListHolderException adding paged list holder");
			} catch (PagedListHoldersCacheException e) {
				logger.error("PagedListHoldersCacheException adding paged list holder");
			}
		}

		if (request.getSession().getAttribute(
				Constants.ControllerUtils.APPLIED_FILTERS_KEY) != null) {
			List<FilterProperties> appliedFilters = (List<FilterProperties>) request
					.getSession().getAttribute(
							Constants.ControllerUtils.APPLIED_FILTERS_KEY);
			if (logger.isDebugEnabled()) {
				logger.debug("Applying " + appliedFilters.size()
						+ " active filters...");
			}
			try {
				userNotificationProcess.getPagedListHolder(
						Constants.PagedListHoldersIds.USER_NOTIFICATION_LIST)
						.applyFilters(appliedFilters);
			} catch (LazyPagedListHolderException e) {
				logger.error("PagedListHoldersCacheException applying filters paged list holder");
				e.printStackTrace();
			}
			if (logger.isDebugEnabled()) {
				logger.debug("Active filters applied.");
			}
		}

		model.put("includeTopbarLinks", true);
		model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
		model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY,
				detailsStatuses);
		model.addAttribute("userNotificationProcess", userNotificationProcess);
		model.addAttribute("codicecomune", codicecomune);

		setRowsPerPageDefaultModelAttributes(model);

		if (isUserSuggestion) {
			this.setPageInfo(model, "admin.userNotification.suggestion.title",
					null, "userNotificationProcess");
		} else {
			this.setPageInfo(model, "admin.userNotification.error.title", null,
					"userNotificationProcess");
		}

		return getStaticProperty("fenodes.userNotifications.view");

	}

	@RequestMapping(value = { "/userNotificationsSuggestion.do",
			"/userNotificationsError.do" }, method = RequestMethod.POST)
	public String processSubmit(
			@ModelAttribute("userNotificationProcess") UserNotificationProcessBean userNotificationProcess,
			BindingResult result,
			ModelMap model,
			@RequestParam(value="codicecomune", required = false) String codicecomune,
			@RequestParam(value="id", required = false) String id,
			HttpServletRequest request) {

		// User suggestion or User Error notification ?
		boolean isUserSuggestion = request.getServletPath().indexOf(
				"userNotificationsSuggestion") != -1;

		boolean isSearchFilter = isParamInRequest(request, "searchFilter");
		boolean isCancel = isPrefixParamInRequest(request, "cancel");

		if (isCancel) {
			return "redirect:" + getStaticProperty("fenodes.listAndAdd.action");
		}

		if (codicecomune == null) {
			codicecomune = (String) request.getSession().getAttribute(
					"codicecomune");
		}

		if (this.isPrefixParamInRequest(request, LIST_HOLDER_TABLE_PREFIX)) {
			processListHoldersRequests(request, userNotificationProcess, model
					//,result
					);
			// savePageSizeInSession(userNotificationProcess, request);

//			Object requestDelete = request
//					.getAttribute(Constants.ControllerUtils.DELETE_CONFIRMATION_REQUIRED);
//			if (requestDelete != null && (Boolean) requestDelete) {
//				return "redirect:/NodiFe/conferma.do";
//			}
		}

		if (isSearchFilter) {
			// resetto gli eventuali filtri di colonna
			userNotificationProcess.setColumn(null);
			userNotificationProcess.setSort(null);

			try {
				ILazyPagedListHolder plh_now = prepareIndicatorsPagedListFiltered(
						userNotificationProcess,
						codicecomune,
						userNotificationProcess
								.getPagedListHolder(Constants.PagedListHoldersIds.USER_NOTIFICATION_LIST),
						isUserSuggestion ? UserNotificationBean.USER_NOTIFICATION_TYPE_SUGGESTION
								: UserNotificationBean.USER_NOTIFICATION_TYPE_ERROR,
						true);
				if (plh_now != null) {
					userNotificationProcess.updatePagedListHolder(plh_now);
				}
				// if (request.getParameter("_urppv_#@@#usernotificationList")
				// != null) {
				// pageSize =
				// Integer.parseInt(request.getParameter("_urppv_#@@#usernotificationList"));
				// }
			} catch (PagedListHoldersCacheException e) {
				logger.error("PagedListHoldersCacheException adding paged list holder");
			}
		}

		model.put("includeTopbarLinks", true);
		model.put("sidebar", "/WEB-INF/jsp/fenodes/sidebar.jsp");
		model.addAttribute(Constants.ControllerUtils.DETAILS_STATUSES_KEY,
				detailsStatuses);
		model.addAttribute("userNotificationProcess", userNotificationProcess);
		model.addAttribute("codicecomune", codicecomune);

		setRowsPerPageDefaultModelAttributes(model);

		if (isUserSuggestion) {
			this.setPageInfo(model, "admin.userNotification.suggestion.title",
					null, "userNotificationProcess");
		} else {
			this.setPageInfo(model, "admin.userNotification.error.title", null,
					"userNotificationProcess");
		}

		return getStaticProperty("fenodes.userNotifications.view");

	}

	/**
	 * @param feNode
	 * @param request
	 */
	private void savePageSizeInSession(AbstractBaseBean process,
			HttpServletRequest request) {
		int pageSize = process.getPagedListHolder(
				Constants.PagedListHoldersIds.USER_NOTIFICATION_LIST)
				.getPageSize();
		request.getSession().setAttribute("userNotificationProcessPageSize",
				pageSize);
	}

	protected FEInterface getFEInterface(URL reference) throws ServiceException {
		FEInterfaceServiceLocator locator = new FEInterfaceServiceLocator();
		FEInterface feInterface = locator.getFEInterface(reference);
		return feInterface;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.people.console.web.servlet.mvc.AbstractListableController#prepareFilters
	 * ()
	 */
	@Override
	protected IFilters prepareFilters() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * it.people.console.web.servlet.mvc.AbstractListableController#processAction
	 * (java.lang.String,
	 * it.people.console.security.AbstractCommand.CommandActions,
	 * it.people.console.persistence.beans.support.EditableRowInputData,
	 * javax.servlet.http.HttpServletRequest, org.springframework.ui.ModelMap)
	 */
	@Override
	protected void processAction(String pagedListHolderId,
			CommandActions action, EditableRowInputData editableRowInputData,
			HttpServletRequest request, ModelMap modelMap
			) {
	}

	// applicazioni dei filtri sul page list holder
	public ILazyPagedListHolder prepareIndicatorsPagedListFiltered(
			UserNotificationProcessBean unp, String codicecomune,
			ILazyPagedListHolder plh, String type, //String codicecomune,
			boolean resetPageStart) {
		try {
			// recupero i valori del filtro per impostare la selezione della
			// query
			String firstName = unp.getName();
			String lastName = unp.getLastname();
			String email = unp.getEmail();
//			String municipalityCode = unp.getMunicipalityCode();
			String fromDate = unp.getFrom();
			String toDate = unp.getTo();
			Calendar fromDateCal = null;
			Calendar toDateCal = null;
			Date date = null;
			String sortType = unp.getSort();
			String sortColumn = unp.getColumn();

			if (fromDate != null && !"".equals(fromDate)) {
				try {
					date = defaultDateFormatFilter.parse(fromDate);
					fromDateCal = Calendar.getInstance();
					fromDateCal.setTime(date);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}
			if (toDate != null && !"".equals(toDate)) {
				try {
					date = defaultDateFormatFilter.parse(toDate);
					toDateCal = Calendar.getInstance();
					toDateCal.setTime(date);
				} catch (Exception e) {
					// e.printStackTrace();
				}
			}

			ProcessFilter defaultFilter = new ProcessFilter();

			int pageSize = defaultPageSize;
			int pageStart = defaultLowerPageLimit;

			if (plh != null) {
				if (!resetPageStart) {
					pageStart = (plh.getPage() * plh.getPageSize())
							- plh.getPageSize();
				}
				pageSize = plh.getPageSize();
			}

			return prepareIndicatorsPagedList(defaultFilter,codicecomune, 
					pageStart,
					pageSize,
					type, "XXX", firstName, lastName, email,
					//municipalityCode,
					fromDateCal, toDateCal, sortType, sortColumn);

		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
	}

	/**
	 * Build a lazy paged list holder
	 * 
	 * @param filter
	 * @param lowerPageLimit
	 * @param pageSize
	 * @param selectedUsers
	 * @param selectedNodes
	 * @return
	 * @throws LazyPagedListHolderException
	 */
	private ILazyPagedListHolder prepareIndicatorsPagedList(
			ProcessFilter filter, String codicecomune, int lowerPageLimit, int pageSize,
			String type, String userId, String firstname, String lastname,
			String email, Calendar from, Calendar to,
			String sortType, String sortColumn)
			throws LazyPagedListHolderException {

		ILazyPagedListHolder processDeletionPagedListHolder = null;

		// Columns to extract (using fields get/set)
		List<String> rowColumnsIdentifiers = new ArrayList<String>();
		//rowColumnsIdentifiers.add("id");
		rowColumnsIdentifiers.add("subject");
		rowColumnsIdentifiers.add("description");
		//rowColumnsIdentifiers.add("userId");
		rowColumnsIdentifiers.add("firstName");
		rowColumnsIdentifiers.add("lastName");
		rowColumnsIdentifiers.add("email");
		rowColumnsIdentifiers.add("communeId");
		//rowColumnsIdentifiers.add("receivedDate");
		rowColumnsIdentifiers.add("receivedDateFormatted");
		
		// Visible Columns
		List<String> visibleColumnsIdentifiers = new ArrayList<String>();
		visibleColumnsIdentifiers.add("subject");
		visibleColumnsIdentifiers.add("description");
		visibleColumnsIdentifiers.add("firstName");
		visibleColumnsIdentifiers.add("lastName");
		visibleColumnsIdentifiers.add("email");
		visibleColumnsIdentifiers.add("communeId");
		// visibleColumnsIdentifiers.add("receivedDate");
		visibleColumnsIdentifiers.add("receivedDateFormatted");

		// Visible columns labels
		List<String> visibleColumnsLabels = new ArrayList<String>();
		visibleColumnsLabels.add("Oggetto");
		visibleColumnsLabels.add("Descrizione");
		visibleColumnsLabels.add("Nome");
		visibleColumnsLabels.add("Cognome");
		visibleColumnsLabels.add("email");
		visibleColumnsLabels.add("Comune ID");
		visibleColumnsLabels.add("Data");

		// (String type, String userId, String communeId, Calendar from,
		// Calendar to
		// Params to call method on source object
		Class paramtypes[] = new Class[13];
		paramtypes[0] = ProcessFilter.class;
		paramtypes[1] = Integer.TYPE;
		paramtypes[2] = Integer.TYPE;
		paramtypes[3] = String.class;
		paramtypes[4] = String.class;
		paramtypes[5] = String.class;
		paramtypes[6] = String.class;
		paramtypes[7] = String.class;
		paramtypes[8] = String.class;
		paramtypes[9] = Calendar.class;
		paramtypes[10] = Calendar.class;
		paramtypes[11] = String.class;
		paramtypes[12] = String.class;

		// Paging params
		Object arglist[] = new Object[13];
		arglist[0] = filter;
		arglist[1] = new Integer(lowerPageLimit);
		arglist[2] = new Integer(pageSize);
		arglist[3] = type;
		arglist[4] = userId;
		arglist[5] = firstname;
		arglist[6] = lastname;
		arglist[7] = email;
		arglist[8] = codicecomune;
		arglist[9] = from;
		arglist[10] = to;
		arglist[11] = sortType;
		arglist[12] = sortColumn;

		// Applico un converter per la colonna della data (Calendar)
		Map<String, Decodable> converters = new HashMap<String, Decodable>();
		converters.put("receivedDate", new CalendarConverter("dd/MM/yyyy"));

		try {
			if (reference == null) {
				// Trovo il reference del web service
				String[] reference_communeid = dataUtils
						.getReferenceCommuneFromCodiceComune(codicecomune);
				reference = new URL(reference_communeid[0]);
			}
			FEInterface feInterface = getFEInterface(reference);

			processDeletionPagedListHolder = LazyPagedListHolderFactory
					.getLazyPagedListHolder(
							Constants.PagedListHoldersIds.USER_NOTIFICATION_LIST,
							feInterface, "getUserNotifications", paramtypes,
							arglist, 1, 2, rowColumnsIdentifiers,
							visibleColumnsIdentifiers, visibleColumnsLabels);

			// TODO fare in modo che il convertitore funzioni
			// al momento alla funzione decode del converter
			// viene passata la string toString del Calendar
			// processDeletionPagedListHolder.setConverters(converters);

		} catch (MalformedURLException e) {
			logger.error(
					"MalformedURLException while preparing IndicatorsPagedList",
					e);
			e.printStackTrace();
		} catch (ServiceException e) {
			logger.error(
					"ServiceException while preparing IndicatorsPagedList", e);
			e.printStackTrace();
		}

		return processDeletionPagedListHolder;
	}

}
