package com.khjxiaogu.scriptengine.core.Object;

import java.util.function.BiConsumer;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.AccessDeniedException;
import com.khjxiaogu.scriptengine.core.exceptions.ContextException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

/**
 * ScriptClosure for inheritance and Script Object
 * backed by map,Closure is the 'this' environment.
 * sbclasses is the super environment/
 * @author khjxiaogu
 * @time 2020年3月6日
 * project:khjScriptEngine
 */
public class ScriptClosure extends Closure {


	private String clsname;
	private ScriptClosure[] sbclasses;

	public ScriptClosure(String name,ScriptClosure[] inheritance) {
		super(new MapEnvironment(GlobalEnvironment.getGlobal()));
		clsname = name;
		sbclasses=inheritance;
	}
	public ScriptClosure(String name) {
		super(new MapEnvironment(GlobalEnvironment.getGlobal()));
		clsname = name;
		sbclasses=null;
	}
	@Override
	public KVariant getMemberByName(String name) throws KSException {
		if (name != null && name.equals("this"))
			return new KVariant(this);
		else if(!Closure.hasMemberByName(name))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByName(name))
					return sc.getMemberByName(name);
			}
		return Closure.getMemberByName(name);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var) throws KSException {
		if(!Closure.hasMemberByVariant(var))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByVariant(var))
					return sc.getMemberByVariant(var);
			}
		return Closure.getMemberByVariant(var);
	}

	@Override
	public KVariant getMemberByNameEnsure(String name) throws KSException {
		if (name != null && name.equals("this"))
			return new KVariant(this);
		if(!Closure.hasMemberByName(name))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByName(name))
					return sc.getMemberByName(name);
			}
		return Closure.getMemberByNameEnsure(name);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val) throws KSException {
		if (name != null && name.equals("this"))
			throw new AccessDeniedException();
		if(!Closure.hasMemberByName(name))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByName(name))
					return sc.setMemberByName(name,val);
			}
		return Closure.setMemberByName(name, val);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val) throws KSException {
		if(!Closure.hasMemberByVariant(var))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByVariant(var))
					return sc.setMemberByVariant(var,val);
			}
		return Closure.setMemberByVariant(var, val);
	}

	@Override
	public KVariant setMemberByNameEnsure(String name, KVariant val) throws KSException {
		if (name != null && name.equals("this"))
			throw new AccessDeniedException();
		if(!Closure.hasMemberByName(name))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByName(name))
					return sc.setMemberByName(name,val);
			}
		return Closure.setMemberByNameEnsure(name, val);
	}
	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KEnvironment objthis) throws KSException {
		if (name == null)
			return ((CallableFunction)this).FuncCall(args,objthis);
		if(!Closure.hasMemberByName(name))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByName(name))
					return sc.funcCallByName(name, args, objthis);
			}
		return Closure.funcCallByName(name, args, objthis);
	}
	@Override
	public boolean hasMemberByName(String name) throws KSException {
		if (name != null && name.equals("this"))
			return true;
		if(!Closure.hasMemberByName(name))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByName(name))
					return true;
			}
		return false;
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		if(!Closure.hasMemberByVariant(var))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByVariant(var))
					return true;
			}
		return false;
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		if (name != null && name.equals("this"))
			throw new MemberNotFoundException("this");
		if(!Closure.hasMemberByName(name))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByName(name))
					return sc.deleteMemberByName(name);
			}
		return Closure.deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		if(!Closure.hasMemberByVariant(var))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByVariant(var))
					return sc.deleteMemberByVariant(var);
			}
		return Closure.deleteMemberByVariant(var);
	}

	@Override
	public KVariant DoOperatonByName(AssignOperation op, String name, KVariant opr) throws KSException {
		if (name != null && name.equals("this"))
			throw new AccessDeniedException();
		if(!Closure.hasMemberByName(name))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByName(name))
					return sc.DoOperatonByName(op, name, opr);
			}
		return Closure.DoOperatonByName(op, name, opr);
	}

	@Override
	public KVariant DoOperatonByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		if(!Closure.hasMemberByVariant(var))
			for(ScriptClosure sc:sbclasses) {
				if(sc.hasMemberByVariant(var))
					return sc.DoOperatonByVariant(op, var, opr);
			}
		return Closure.DoOperatonByVariant(op, var, opr);
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		if (str.equals(clsname))
			return true;
		else if (Closure != null && Closure instanceof KObject && ((KObject) Closure).isInstanceOf(str))
			return true;
		return false;
	}

	@Override
	public boolean isValid() throws KSException {
		return Closure == null;
	}

	@Override
	public boolean invalidate() throws KSException {
		if (Closure != null) {
			Closure = null;
			return true;
		}
		return false;
	}

	@Override
	public KObject newInstance() throws KSException {
		if(sbclasses.length>0) {
			ScriptClosure[] sbcls=new ScriptClosure[sbclasses.length];
			for(int i=0;i<sbclasses.length;i++) {
				sbcls[i]=(ScriptClosure) sbclasses[i].newInstance();
			}
			ScriptClosure newInst=new ScriptClosure(clsname,sbcls);
			Closure.EnumMembers((k,v)->{
				try {
					if(v.getType().getType()==KObject.class) {
						KObject obj=(KObject) v.toType("Object");
						if(obj instanceof KProperty) {
							v=new KVariant(new PropertyClosure((KProperty) obj,newInst));
						}else if(obj instanceof CallableFunction) {
							v=new KVariant(new FunctionClosure((CallableFunction) obj,newInst));
						}else
							return;
						newInst.setMemberByVariant(k,v);
					}
				} catch (KSException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
			return newInst;
		}
		ScriptClosure newInst=new ScriptClosure(clsname);
		Closure.EnumMembers((k,v)->{
			try {
				newInst.setMemberByVariant(k,v);
			} catch (KSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return newInst;
	}
	@Override
	public void EnumMembers(BiConsumer<KVariant, KVariant> cosumer) throws KSException {
		Closure.EnumMembers(cosumer);
	}

}
