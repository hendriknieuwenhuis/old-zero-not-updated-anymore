package com.bono.zero.util;

import java.util.HashMap;
import com.bono.zero.util.Observer;

@Deprecated
public abstract class Observable implements ObservableInterface {
	
	protected HashMap<String, Observer> observers;
	
	public Observable() {
		observers = new HashMap<String, Observer>();
	}
	
	public void addObserver(String key, Observer observer) {
		observers.put(key, observer);
	}
	
	public void deleteObserver(String key) {
		observers.remove(key);
	}
	
	public void notifyObserver(String key, String update, Object arg) {
		observers.get(key).update(update, arg);
	}

}
