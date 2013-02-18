package com.mooneyserver.account.persistence.service.user;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mooneyserver.account.businesslogic.exception.AccountsBaseException;
import com.mooneyserver.account.persistence.entity.AccountsUser;

public class AccountsUserMergedDetails {
	
	private AccountsUser oldEntity, newEntity;
	
	public AccountsUserMergedDetails(AccountsUser oldEntity, AccountsUser newEntity) {
		this.oldEntity = oldEntity;
		this.newEntity = newEntity;
	}

	public List<String> getUpdates() throws AccountsBaseException {	
		try {
			List<String> updates = new ArrayList<>();
			Map<String, Method> getters = new HashMap<>();
			
			for(PropertyDescriptor propertyDescriptor : 
			    Introspector.getBeanInfo(oldEntity.getClass()).getPropertyDescriptors()){
				getters.put(propertyDescriptor.getName(), propertyDescriptor.getReadMethod());
			}
			
			for (String field : getters.keySet()) {
				Method getterMethod = getters.get(field);
				
				if (!getterMethod.invoke(oldEntity).equals(getterMethod.invoke(newEntity))) {
					updates.add("Updated: ["+field+"] {Old: "
							+getterMethod.invoke(oldEntity)+"} {"
							+getterMethod.invoke(newEntity)+"}");
				}
			}
			
			return updates;
		} catch (IntrospectionException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			throw new AccountsBaseException("Rethrowing wrapped exception", e);
		}		
	}
}