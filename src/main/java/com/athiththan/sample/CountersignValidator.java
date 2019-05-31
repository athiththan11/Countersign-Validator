package com.athiththan.sample;

import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.wso2.carbon.identity.mgt.policy.AbstractPasswordPolicyEnforcer;

/**
 * a simple custom password policy extension implementation for wso2 identity
 * server
 */
public class CountersignValidator extends AbstractPasswordPolicyEnforcer {

	private ArrayList<String> vals = new ArrayList<>();

	/**
	 * loads all defined properties for this particular extension from the
	 * identity-mgt.properties file inside the wso2 identity server
	 * 
	 * @param params contains properties related to this extension
	 */
	@Override
	public void init(Map<String, String> params) {

		initDictionaryVals();

		if (params != null && params.size() > 0) {
			if (StringUtils.isNotEmpty(params.get("faultMsg"))) {
				errorMessage = params.get("faultMsg");
			}
		}
	}

	/**
	 * method to validate the password against the set of rules defined
	 * 
	 * @param agrs object array containing the username and password. args[0]:
	 *             password & args[1]: username
	 * @return boolean
	 */
	@Override
	public boolean enforce(Object... args) {

		if (args != null) {

			String password = args[0].toString();
			String username = args[1].toString();

			if (username.equals(password)) {
				errorMessage = "The Username and Password cannot be the same";
				return false;
			} else if (validatePassword(password)) {
				errorMessage = "The Password cannot contain any common dictionary values";
				return false;
			}
		}

		return true;
	}

	private void initDictionaryVals() {
		vals.add("admin");
		vals.add("password");
		vals.add("root");
		vals.add("123");
	}

	private boolean validatePassword(String password) {
		return vals.stream().anyMatch(v -> {
			return password.contains(v);
		});
	}

}
