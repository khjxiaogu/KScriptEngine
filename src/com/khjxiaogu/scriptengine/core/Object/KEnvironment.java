package com.khjxiaogu.scriptengine.core.Object;

import java.util.List;

import com.khjxiaogu.scriptengine.core.Exception.KSException;

public interface KEnvironment {
	public KVariant getMemberByName(String name) throws KSException;
	public KVariant getMemberByNum(int num) throws KSException;
	public KVariant getMemberByVariant(KVariant var) throws KSException;
	public KVariant getMemberByPath(List<String> path) throws KSException;
	public KVariant setMemberByName(String name,KVariant val) throws KSException;
	public KVariant setMemberByNum(int num,KVariant val) throws KSException;
	public KVariant setMemberByVariant(KVariant var,KVariant val) throws KSException;
	public KVariant setMemberByPath(List<String> path,KVariant val) throws KSException;
	public boolean deleteMemberByName(String name) throws KSException;
	public boolean deleteMemberByNum(int num) throws KSException;
	public boolean deleteMemberByPath(List<String> path) throws KSException;
	public KVariant getMemberByNameEnsure(String name) throws KSException;
	public void Break() throws KSException;
	public void Return(KVariant val) throws KSException;
}
