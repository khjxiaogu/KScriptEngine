package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;

public interface KVariantReference {
	KVariant setValue(KVariant newval,int flag) throws KSException;
	KVariant getValue(int flag) throws KSException;
	boolean delete(int flag) throws KSException;
	default KVariant setValue(KVariant newval) throws KSException{
		return setValue(newval,KEnvironment.DEFAULT);
	};
	default KVariant getValue() throws KSException{
		return getValue(KEnvironment.DEFAULT);
	};
	default boolean delete() throws KSException{
		return delete(KEnvironment.DEFAULT);
	}
	KVariant funcCall(KVariant[] args, int cflag) throws KSException;;
}
