package com.khjxiaogu.scriptengine.core.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.khjxiaogu.scriptengine.KhjScriptEngine;
import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.GlobalEnvironment;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.internal.ObjectReflection;
import com.khjxiaogu.scriptengine.core.syntax.CodeNode;

public class ParserRunner {
	public static void main(String[] args) {
		KhjScriptEngine eg=new KhjScriptEngine();
		try {
			GlobalEnvironment.getGlobal().setMemberByName("Reflection",new KVariant(new ObjectReflection()),KEnvironment.DEFAULT);
		} catch (KSException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		CodeDialog cd = new CodeDialog();
		String s;
		while ((s = cd.showDialog()) != null) {
			final String ss=s;
			try {
				TimedExecute(()->{
					try {
						CodeNode cn=eg.parse(ss);
						System.out.println("语法解析结果：");
						System.out.println(cn.toString());
						System.out.println("在global上下文执行结果：");
						System.out.println(cn.eval(GlobalEnvironment.getGlobal()));
					} catch (Exception e) {
						System.out.println(e.getMessage());
						e.printStackTrace();
					}
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		// Lexer l = new Lexer(new CodeDialog());
	}
	public static void TimedExecute(Runnable r) throws Exception{
		Future<?> obj = null;
		ExecutorService sexec = Executors.newSingleThreadExecutor();
		try {
			obj=sexec.submit(r);
			obj.get(10,TimeUnit.SECONDS);
		}catch (TimeoutException e) {
			obj.cancel(true);
			
			throw e;
		}
	}
}
