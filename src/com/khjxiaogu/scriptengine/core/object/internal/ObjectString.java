package com.khjxiaogu.scriptengine.core.object.internal;

import java.util.Collections;
import java.util.regex.Pattern;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.object.ExtendableClosure;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.KOctet;
import com.khjxiaogu.scriptengine.core.object.NativeClassClosure;
import com.khjxiaogu.scriptengine.core.object.NativeMethod;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class ObjectString  extends NativeClassClosure<String>{

	@Override
	protected ExtendableClosure getNewInstance() throws KSException {
		return new ObjectString();
	}
	public ObjectString() {
		super(String.class,"String");
		super.registerProperty("length",str->KVariant.valueOf(str.length()),null);
		super.registerFunction("charAt",(str,args)->getMemberByNum(args[0].getInt(),KEnvironment.MUSTEXIST));
		super.registerFunction("indexOf",(str,args)->KVariant.valueOf(str.indexOf(args[0].toString())));
		super.registerFunction("toLowerCase",(str,args)->KVariant.valueOf(str.toLowerCase()));
		super.registerFunction("toUpperCase",(str,args)->KVariant.valueOf(str.toUpperCase()));
		NativeMethod<String> substr=(str,args)->args.length>1?KVariant.valueOf(str.substring(args[0].getInt(),args[1].getInt())):KVariant.valueOf(str.substring(args[0].getInt()));
		super.registerFunction("substring",substr);
		super.registerFunction("substr",substr);
		super.registerFunction("sprintf",(str,args)->KVariant.valueOf(String.format(str,(Object[])args)));
		super.registerFunction("split",(str,args)->{
			boolean b=true;
			if(args.length>2) {
				b=!args[2].asBoolean();
			}
			String[] ss=toString().split("["+Pattern.quote("|^")+"]");
			KObject arr=ObjectArray.createArray();
			int i=0;
			for(String s:ss) {
				if(b||s!="") {
					arr.setMemberByNum(i++,KVariant.valueOf(s),KEnvironment.DEFAULT);
				}
			}
			return KVariant.valueOf(arr);
		});
		super.registerFunction("encode",(str,arr)->{
			try {
				return KVariant.valueOf(new KOctet(str.getBytes(arr[0].asString())));
			}catch(Exception e) {
				throw new ScriptException(e);
			}
		});
		super.registerFunction("decode",(str,arr)->{
			try {
				return KVariant.valueOf(new String(arr[0].asType(KOctet.class).getBytes(),arr[1].asString()));
			}catch(Exception e) {
				throw new ScriptException(e);
			}
		});
		super.registerFunction("replace",(str,arr)->KVariant.valueOf(str.replace(arr[0].asString(),arr[1].asString())));
		super.registerFunction("escape",(str,arr)->KVariant.valueOf(str.replace("\\", "\\\\").replace("\'", "\\'").replace("\"", "\\\"")));
		super.registerFunction("trim",(str,arr)->KVariant.valueOf(str.trim()));
		super.registerFunction("reverse",(str,arr)->KVariant.valueOf(new StringBuilder(str).reverse().toString()));
		super.registerFunction("repeat",(str,arr)->KVariant.valueOf(String.join("", Collections.nCopies(arr[0].getInt(), str))));
		super.registerFunction("startsWith",(str,arr)->KVariant.valueOf(str.startsWith(arr[0].toString())));
		super.registerFunction("endsWith",(str,arr)->KVariant.valueOf(str.endsWith(arr[0].toString())));
	}
	@Override
	public KVariant getMemberByVariant(KVariant var, int flag) throws KSException {
		if(var.getType().getType()==Long.class)
			return this.getMemberByNum(var.asInt(), flag);
		return super.getMemberByVariant(var, flag);
	}
	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		if(var.getType().getType()==Long.class)
			return this.setMemberByNum(var.asInt(),val, flag);
		return super.setMemberByVariant(var, val, flag);
	}
	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		if(var.getType().getType()==Long.class)
			return this.hasMemberByNum(var.asInt());
		return super.hasMemberByVariant(var);
	}
	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		if(var.getType().getType()==Long.class)
			return this.deleteMemberByNum(var.asInt());
		return super.deleteMemberByVariant(var);
	}
	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return super.doOperationByVariant(op, var, opr);
	}
	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		String ni=super.getNativeInstance();
		if(num>=ni.length()) {
			if((flag|KEnvironment.MUSTEXIST)!=0)
				throw new MemberNotFoundException(num);
			return KVariant.valueOf("");
		}
		return KVariant.valueOf(String.valueOf(super.getNativeInstance().charAt(num)));
	}
	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		String ni=super.getNativeInstance();
		if(num>=ni.length())
			throw new MemberNotFoundException(num);
		StringBuilder sb=new StringBuilder();
		sb.setCharAt(num, val.asString().charAt(0));
		return KVariant.valueOf(sb.toString());
	}
	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		return num<super.getNativeInstance().length();
	}
	@Override
	public boolean deleteMemberByNum(int num) throws KSException {
		throw new ScriptException("不支持的操作");
	}
	@Override
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		throw new ScriptException("不支持的操作");
	}
	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KEnvironment objthis, int flag) throws KSException {
		throw new ScriptException("不支持的操作");
	}
}
