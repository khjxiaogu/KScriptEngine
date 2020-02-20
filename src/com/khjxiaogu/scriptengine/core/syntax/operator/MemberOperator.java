package com.khjxiaogu.scriptengine.core.syntax.operator;

import java.util.List;

import com.khjxiaogu.scriptengine.core.Exception.KSException;
import com.khjxiaogu.scriptengine.core.Object.KEnvironment;

public interface MemberOperator {
	public void getClassPath(KEnvironment env,List<String> dest) throws KSException;
}
