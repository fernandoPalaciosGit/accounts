package com.mooneyserver.account.ui.widget;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.NoSuchElementException;

class PeekableLinkedHashSet<E> extends LinkedHashSet<E> {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Adding standard peek functionality to
	 * PeekableLinkedHashSet
	 * 
	 * @return
	 * 	The Last Element
	 */
	public E peek() {
		Iterator<E> itr = this.iterator();
		E lastElement = null;
		while (itr.hasNext()) {
			lastElement = itr.next();
		}
		
		return lastElement;
	}
	
	/**
	 * Standard pop functionality added
	 * to PeekableLinkedHashSet
	 * 
	 * @return
	 * 	The last element (now removed)
	 */
	public E pop() {
		E element = this.peek();
		this.remove(element);
		
		return element;
	}
	
	/**
	 * Remove all elements after the param
	 * newLastElement. This will result in the
	 * passed parameter becoming the newLastElement
	 */
	public void truncate(E newLastElement) {
		Iterator<E> itr = this.iterator();
		boolean delete = false;
		while (itr.hasNext()) {
			E element = itr.next();
			
			if (delete)
				this.remove(element);
			
			if (element.equals(newLastElement))
				delete = true;
		}
	}
	
	/**
	 * Tries to return the element which
	 * is backwardly linked to the passed
	 * param. If the passed param is first
	 * or does not exist, null is returned.
	 * 
	 * @param element
	 * 	E element that you want backwardly linked
	 *  next element.
	 *  
	 * @return
	 *  E element
	 */
	public E previous(E element) {
		Iterator<E> itr = this.iterator();
		E prevElement = null;
		while (itr.hasNext()) {
			E currElement = itr.next();
			if (element.equals(currElement))
				return prevElement;
			
			prevElement = currElement;
		}
		
		return null;
	}
	
	/**
	 * Tries to return the element which
	 * is forwardly linked to the passed
	 * param. If the passed param is last
	 * or does not exist, null is returned.
	 * 
	 * @param element
	 * 	E element that you want forwardly linked
	 *  next element.
	 *  
	 * @return
	 *  E element
	 */
	public E next(E element) {
		Iterator<E> itr = this.iterator();
		while (itr.hasNext()) {
			if (itr.next().equals(element))
				break;
		}
		
		try {
			return itr.next();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
}