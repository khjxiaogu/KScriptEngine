package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class KObjectClosure extends KAbstractObject {

	public KObjectClosure(KObject func, KObject objthis) {
		super();
		this.func = func;
		this.objthis = objthis;
	}
	KObject func;
	KObject objthis;
	@Override
	public String getInstanceName() {
		if(func instanceof KAbstractObject)
			return ((KAbstractObject) func).getInstanceName();
		return "Object";
	}
	@Override
	public String toString() {
		if(objthis!=null)
			return "("+this.getInstanceName()+")(0x"+Integer.toHexString(func.hashCode())+")@(0x"+Integer.toHexString(objthis.hashCode())+")";
		return "("+this.getInstanceName()+")(0x"+Integer.toHexString(func.hashCode())+")@static";
	}
	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		
		return func.getMemberByName(name, flag, this.objthis);
	}
	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		
		return func.getMemberByNum(num, flag);
	}
	@Override
	public KVariant getMemberByVariant(KVariant var, int flag, KObject objthis) throws KSException {
		
		return func.getMemberByVariant(var, flag, this.objthis);
	}
	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		
		return func.setMemberByName(name, val, flag);
	}
	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		
		return func.setMemberByNum(num, val, flag);
	}
	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		
		return func.setMemberByVariant(var, val, flag);
	}
	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		
		return func.hasMemberByName(name, flag);
	}
	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		
		return func.hasMemberByNum(num);
	}
	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		
		return func.hasMemberByVariant(var);
	}
	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		
		return func.deleteMemberByName(name);
	}
	@Override
	public boolean deleteMemberByNum(int num) throws KSException {
		
		return func.deleteMemberByNum(num);
	}
	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		
		return func.deleteMemberByVariant(var);
	}
	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		
		return func.doOperationByName(op, name, opr);
	}
	@Override
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		
		return func.doOperationByNum(op, num, opr);
	}
	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		
		return func.doOperationByVariant(op, var, opr);
	}
	@Override
	public boolean isInstanceOf(String str) throws KSException {
		
		return func.isInstanceOf(str);
	}
	@Override
	public void callConstructor(KVariant[] args, KObject objthis) throws KSException {
		
		func.callConstructor(args, objthis);
	}
	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KObject objthis, int flag) throws KSException {
		
		return func.funcCallByNum(num, args, this.objthis, flag);
	}
	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KObject objthis, int flag) throws KSException {
		
		return func.funcCallByName(name, args, this.objthis, flag);
	}
	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		
		func.EnumMembers(cosumer, flag);
	}
	@Override
	public <T> T getNativeInstance(Class<T> cls) throws KSException {
		
		return func.getNativeInstance(cls);
	}
	@Override
	public boolean hasNativeInstance(Class<?> cls) throws KSException {
		
		return func.hasNativeInstance(cls);
	}
	@Override
	public void putNativeInstance(Object nis) throws KSException {
		
		func.putNativeInstance(nis);
	}
}
