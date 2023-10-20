package com.khjxiaogu.scriptengine.core.object.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.object.CallableFunction;
import com.khjxiaogu.scriptengine.core.object.ExtendableClosure;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KEnvironmentReference;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.KOctet;
import com.khjxiaogu.scriptengine.core.object.NativeClassClosure;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class ObjectArray extends NativeClassClosure<ArrayList<KVariant>>{
	private static ArrayList<KVariant> arr=new ArrayList<>();
	@Override
	public ExtendableClosure getNewInstance() throws KSException {
		return new ObjectArray(null);
	}
	private static ObjectArray arrcls;
	/**
	 * @param nul signal param
	 */
	@SuppressWarnings("unchecked")
	public ObjectArray(Object nul) {
		super((Class<ArrayList<KVariant>>) arr.getClass(),"Array");
	}
	@SuppressWarnings("unchecked")
	public ObjectArray() {
		super((Class<ArrayList<KVariant>>) arr.getClass(),"Array");
		arrcls=this;
		super.registerConstructor((env,args)->new ArrayList<KVariant>());
		super.registerFunction("add",(obj,args)->{obj.add(args[0]);return KVariant.valueOf(obj.size()-1);});
		super.registerFunction("clear",(obj,args)->{obj.clear();return KVariant.valueOf();});
		super.registerFunction("find",(obj,args)->KVariant.valueOf(obj.indexOf(args[0])));
		super.registerFunction("join",(obj,args)->{
			StringBuilder sb=new StringBuilder();
			Iterator<KVariant> itv=obj.iterator();
			while(itv.hasNext()) {
				sb.append(itv.next().toString());
				if(itv.hasNext())
					sb.append(args[0].toString());
			}
			return KVariant.valueOf(sb.toString());
			});
		super.registerProperty("length",obj->KVariant.valueOf(obj.size()),null);
		super.registerProperty("count",obj->KVariant.valueOf(obj.size()),null);
		super.registerFunction("load",(obj,args)->{
			try(FileReader fis=new FileReader(args[0].asString());BufferedReader rd=new BufferedReader(fis)){
				;
				obj.clear();
				String s;
				while((s=rd.readLine())!=null) {
					obj.add(KVariant.valueOf(s));
				}
			} catch (IOException e) {
				obj.clear();
			}
			return KVariant.valueOf(this);
		});
		super.registerFunction("save",(obj,args)->{
			try(OutputStream os=new FileOutputStream(new File(args[0].toString()));
				PrintStream ps=new PrintStream(os,true,StandardCharsets.UTF_16LE);
					){
				for(KVariant a:obj) {
					ps.println(a.toString());;
				}
			} catch (IOException e) {
				obj.clear();
			}
			return KVariant.valueOf(this);
		});
		super.registerFunction("asOctet",(obj,args)->{
			byte[] octs=new byte[obj.size()];
			for(int i=0;i<obj.size();i++) {
				octs[i]=(byte) obj.get(i).asInt();
				System.out.println(octs[i]);
			}
			return KVariant.valueOf(new KOctet(octs));
		});
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
	public static KObject createArray() throws KSException {
		ExtendableClosure sar=(ExtendableClosure) arrcls.newInstance();
		sar.callConstructor(null,sar);
		return sar;
	}
	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		ArrayList<KVariant> al=super.getNativeInstance();
		if((flag&KEnvironment.MUSTEXIST)==0)while(num>=al.size())
				al.add(KVariant.valueOf());
		else if(num>=al.size())
			throw new MemberNotFoundException(Integer.toString(num));
		return al.get(num);
	}
	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		ArrayList<KVariant> al=super.getNativeInstance();
		if((flag&KEnvironment.MUSTEXIST)==0) {
			if(num==al.size()) {
				al.add(val);
				return val;
			}while(num>=al.size())
				al.add(KVariant.valueOf());
		}else if(num>=al.size())
			throw new MemberNotFoundException(Integer.toString(num));
		al.set(num,val);
		return val;
	}
	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		return num<super.getNativeInstance().size();
	}
	@Override
	public boolean deleteMemberByNum(int num) throws KSException {
		ArrayList<KVariant> al=super.getNativeInstance();
		if(al.size()<num) {
			al.set(num,KVariant.valueOf());
			return true;
		}return false;
		
	}
	@Override
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		ArrayList<KVariant> al=super.getNativeInstance();
		while(num>=al.size())
			al.add(KVariant.valueOf());
		return al.get(num).doOperation(op, opr,new KEnvironmentReference(this,num));
	}
	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KEnvironment objthis, int flag) throws KSException {
		KVariant res = this.getMemberByNum(num, flag);
		if (res == null)
			throw new MemberNotFoundException(Integer.toString(num));
		KObject obj = res.asType(KObject.class);
		if (obj instanceof CallableFunction)
			return ((CallableFunction) obj).FuncCall(args, objthis == null ? this : objthis);
		throw new ScriptException("呼叫的对象不是函数");
	}
	
}
