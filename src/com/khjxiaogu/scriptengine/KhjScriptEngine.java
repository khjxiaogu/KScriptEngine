package com.khjxiaogu.scriptengine;

import java.util.Date;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.Parser;
import com.khjxiaogu.scriptengine.core.StringParseReader;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.GlobalEnvironment;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.NativeFunction;
import com.khjxiaogu.scriptengine.core.object.internal.JavaClassWrapper;
import com.khjxiaogu.scriptengine.core.object.internal.ObjectArray;
import com.khjxiaogu.scriptengine.core.object.internal.ObjectDictionary;
import com.khjxiaogu.scriptengine.core.object.internal.ObjectException;
import com.khjxiaogu.scriptengine.core.object.internal.ObjectScripts;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;
import com.khjxiaogu.scriptengine.core.syntax.block.GlobalCodeBlock;

/**
 * @author khjxiaogu
 * @time 2019年8月23日 file:KhjScriptEngine.java
 */
public class KhjScriptEngine {
	Parser p = Parser.getParser();
	public KhjScriptEngine() {
		try {
			GlobalEnvironment.getGlobal().setMemberByName("print",
					KVariant.valueOf(new NativeFunction<Object>((objthis, params) -> {
						if (params.length > 0) {
							System.out.println(params[0].toString());
						}
						return null;
					})), KEnvironment.DEFAULT);
			GlobalEnvironment.getGlobal().setMemberByName("Exception",KVariant.valueOf(new ObjectException()),KEnvironment.DEFAULT);
			GlobalEnvironment.getGlobal().setMemberByName("Math",KVariant.valueOf(JavaClassWrapper.getWrapper(Math.class)),KEnvironment.DEFAULT);
			GlobalEnvironment.getGlobal().setMemberByName("Date",KVariant.valueOf(JavaClassWrapper.getWrapper(Date.class)),KEnvironment.DEFAULT);
			GlobalEnvironment.getGlobal().setMemberByName("Scripts",KVariant.valueOf(new ObjectScripts(this)),KEnvironment.DEFAULT);
			GlobalEnvironment.getGlobal().setMemberByName("Array",KVariant.valueOf(new ObjectArray()),KEnvironment.DEFAULT);
			GlobalEnvironment.getGlobal().setMemberByName("Dictionary",KVariant.valueOf(new ObjectDictionary()),KEnvironment.DEFAULT);
		} catch (KSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public CodeNode parse(String s) throws KSException {
		GlobalCodeBlock cn = new GlobalCodeBlock();
		if(!s.endsWith(";"))
			cn.parse(new StringParseReader(s+";"));
		else
			cn.parse(new StringParseReader(s));
		return cn;
	}
	public KVariant eval(String s) throws KSException {
		GlobalCodeBlock cn = new GlobalCodeBlock();
		if(!s.endsWith(";"))
			cn.parse(new StringParseReader(s+";"));
		else
			cn.parse(new StringParseReader(s));
		return cn.eval();
	}
	public KVariant eval(String s,KEnvironment env) throws KSException {
		GlobalCodeBlock cn = new GlobalCodeBlock();
		if(!s.endsWith(";"))
			cn.parse(new StringParseReader(s+";"));
		else
			cn.parse(new StringParseReader(s));
		return cn.eval(env);
	}
}
