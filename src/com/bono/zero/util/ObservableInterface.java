package com.bono.zero.util;


@Deprecated
public interface ObservableInterface {

	void addObserver(String key, Observer observer);
	
	void deleteObserver(String key);
	
	void notifyObserver(String key, String update, Object arg);
	
}
