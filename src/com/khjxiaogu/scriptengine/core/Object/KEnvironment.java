package com.khjxiaogu.scriptengine.core.Object;

import java.util.function.BiConsumer;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public interface KEnvironment {
	public KVariant getMemberByName(String name) throws KSException;

	public KVariant getMemberByNum(int num) throws KSException;

	public KVariant getMemberByVariant(KVariant var) throws KSException;

	public KVariant getMemberByNameEnsure(String name) throws KSException;

	public KVariant setMemberByName(String name, KVariant val) throws KSException;

	public KVariant setMemberByNum(int num, KVariant val) throws KSException;

	public KVariant setMemberByVariant(KVariant var, KVariant val) throws KSException;

	public KVariant setMemberByNameEnsure(String name, KVariant val) throws KSException;

	public KVariant funcCallByNum(int num, KVariant[] args,KEnvironment objthis) throws KSException;

	public KVariant funcCallByName(String name, KVariant[] args,KEnvironment objthis) throws KSException;
	
	public boolean hasMemberByName(String name) throws KSException;

	public boolean hasMemberByNum(int num) throws KSException;

	public boolean hasMemberByVariant(KVariant var) throws KSException;

	public boolean deleteMemberByName(String name) throws KSException;

	public boolean deleteMemberByNum(int num) throws KSException;

	public boolean deleteMemberByVariant(KVariant var) throws KSException;

	public KVariant DoOperatonByName(AssignOperation op, String name, KVariant opr) throws KSException;

	public KVariant DoOperatonByNum(AssignOperation op, int num, KVariant opr) throws KSException;

	public KVariant DoOperatonByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException;

	public void EnumMembers(BiConsumer<KVariant,KVariant> cosumer)throws KSException;
	
	public <T> T getNativeInstance(Class<T> cls)throws KSException;
	
	public void putNativeInstance(Object nis)throws KSException;
	
}
