package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public interface KVariantReference {
	KVariant setValue(KVariant newval) throws KSException;
	KVariant getValue() throws KSException;
	boolean delete() throws KSException;
}
