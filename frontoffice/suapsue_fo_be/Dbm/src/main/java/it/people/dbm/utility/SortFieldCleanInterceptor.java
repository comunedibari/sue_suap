package it.people.dbm.utility;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

public class SortFieldCleanInterceptor {

	private static final String SORT_PARAM_NAME = "sort";
	private static final String LANG_SUFFIX = "_it";

	public HttpServletRequest doSortFieldCleaning(HttpServletRequest request) {
		String sortParam = request.getParameter(SORT_PARAM_NAME);
		if (sortParam != null && sortParam.endsWith(LANG_SUFFIX)) {
			sortParam = sortParam.substring(0, sortParam.lastIndexOf(LANG_SUFFIX));
			Map<String, String[]> param = new TreeMap<String, String[]>();
			param.put(SORT_PARAM_NAME, new String[]{sortParam});
			ExtraParameterHttpServletRequestWrapper requestWrapper = new ExtraParameterHttpServletRequestWrapper(request, param);
			request = requestWrapper;
		}
		return request;
	}
	
}
