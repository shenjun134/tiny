/**
 * EnhancedHttpRequest.java
 *
 *
 */
package com.tiny.web.controller.http.request;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * @author e521907
 * @version 1.0
 *
 */
public class EnhancedHttpRequest extends HttpServletRequestWrapper {

	/**
	 * 
	 */
	private final Map<String, String[]>	modifiableParameters;
	/**
	 * 
	 */
	private Map<String, String[]>		allParameters	= null;

	public EnhancedHttpRequest(final HttpServletRequest request, final Map<String, String[]> additionalParams) {
		super(request);
		modifiableParameters = new TreeMap<String, String[]>();
		modifiableParameters.putAll(additionalParams);
	}

	@Override
	public String getParameter(final String name) {
		String[] strings = getParameterMap().get(name);
		if (strings != null) {
			return strings[0];
		}
		return super.getParameter(name);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String[]> getParameterMap() {
		if (allParameters == null) {
			allParameters = new TreeMap<String, String[]>();
			allParameters.putAll(super.getParameterMap());
			allParameters.putAll(modifiableParameters);
		}
		// Return an unmodifiable collection because we need to uphold the interface contract.
		return Collections.unmodifiableMap(allParameters);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return Collections.enumeration(getParameterMap().keySet());
	}

	@Override
	public String[] getParameterValues(final String name) {
		return getParameterMap().get(name);
	}

}
