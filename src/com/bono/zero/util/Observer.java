package com.bono.zero.util;

import com.bono.zero.util.Observable;

@Deprecated
public interface Observer {
	
	void update(String update, Object arg);

}
