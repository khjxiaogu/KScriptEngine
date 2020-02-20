package com.khjxiaogu.scriptengine.core.Object;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.Exception.KSException;

public class MapEnvironment implements KEnvironment {
	private Map<String,KVariant> map=new ConcurrentHashMap<>();
	KEnvironment parent=null;
	public MapEnvironment(KEnvironment parent) {
		// TODO Auto-generated constructor stub
		this.parent=parent;
	}

	@Override
	public KVariant getMemberByName(String name) throws KSException {
		// TODO Auto-generated method stub
		KVariant res=map.get(name);
		if(res==null) {
			if(parent!=null)
				res=parent.getMemberByName(name);
		}
		if(res==null)
			return new KVariant();
		return res;
	}
	@Override
	public KVariant getMemberByNameEnsure(String name) throws KSException {
		// TODO Auto-generated method stub
		KVariant res=map.get(name);
		if(res==null) {
			if(parent!=null)
				res=parent.getMemberByName(name);
		}
		if(res==null)
			throw new MemberNotFoundException(name);
		return res;
	}
	@Override
	public KVariant getMemberByNum(int num) throws KSException {
		// TODO Auto-generated method stub
		KVariant res=map.get(Integer.toString(num));
		if(res==null) {
			if(parent!=null)
				res=parent.getMemberByNum(num);
		}
		if(res==null)
			return new KVariant();
		return res;
	}

	@Override
	public KVariant getMemberByVariant(KVariant var) throws KSException {
		// TODO Auto-generated method stub
		String name=(String) var.asType("String");
		KVariant res=map.get(name);
		if(res==null) {
			if(parent!=null)
				res=parent.getMemberByName(name);
		}
		if(res==null)
			return new KVariant();
		return res;
	}

	@Override
	public KVariant getMemberByPath(List<String> path) throws KSException {
		// TODO Auto-generated method stub
		String ot=path.remove(0);
		KVariant ol=map.get(ot);
		if(path.isEmpty())
		return ol;
		return ((KObject)ol.asType("Object")).getMemberByPath(path);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public KVariant setMemberByPath(List<String> path, KVariant val) throws KSException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMemberByNum(int num) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMemberByPath(List<String> path) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void Break() throws KSException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Return(KVariant val) throws KSException {
		// TODO Auto-generated method stub
		
	}

}
