package com.mooneyserver.account.businesslogic.validate;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;

public class ClassFieldValidator {

	private final Object obj;
	private final Set<String> fieldNames;
	
	public ClassFieldValidator(Object obj, Set<String> fieldNames) {
		this.obj = obj;
		this.fieldNames = fieldNames;
	}
	
	/**
	 * Check if any of the exposed fields
	 * (am assuming (a$$, you, me... etc) exposed 
	 * through a getter) exposed by this object are
	 * null
	 * 
	 * @return
	 * 		<b>boolean</b>
	 * 		true if any of the fields return null
	 * @throws AccountsBaseException 
	 */
	public boolean areAnyFieldsNull() throws AccountsBaseException {
		try {
			for (PropertyDescriptor pd : Introspector.getBeanInfo(obj.getClass()).getPropertyDescriptors()) {
				if (pd.getReadMethod() != null && !"class".equals(pd.getName()))
					if (pd.getReadMethod().invoke(obj) == null && isRequiredMethod(pd.getReadMethod())) {
						return true;
					}
			}
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | IntrospectionException e) {
			throw new AccountsBaseException("Failed While inspecting object for null ["+obj.getClass()+"]", e);
		}
		
		return false;
	}
	
	
	private boolean isRequiredMethod(Method m) {
		for (String fieldName : fieldNames) {
			if (m.getName().equalsIgnoreCase("get" + fieldName))
				return true;
		}
		
		return false;
	}
}