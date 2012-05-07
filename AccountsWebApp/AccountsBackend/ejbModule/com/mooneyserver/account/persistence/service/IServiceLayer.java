package com.mooneyserver.account.persistence.service;

import java.util.List;

public interface IServiceLayer <E, I> {
	
	public void create(E entity);
	
	public void modify(E entity);
	
	public void delete(E entity);
	
	public E findById(I id);
	
	public List<E> findAll();

}
