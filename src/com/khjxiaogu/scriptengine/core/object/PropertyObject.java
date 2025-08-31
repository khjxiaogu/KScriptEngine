package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.NotImplementedException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class PropertyObject extends KAbstractObject{
	protected KProperty backed;
	public PropertyObject(KProperty prop) {
		super();
		backed = prop;
	}

	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		if((flag&KEnvironment.IGNOREPROP)==0) {
			if (name == null)
				return getProp(null);
			return getProp(null).asObject().getMemberByName(name, flag, objthis);
		}
		if(name==null)return KVariant.valueOf(this);
		throw new MemberNotFoundException(name);
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		if((flag&KEnvironment.IGNOREPROP)==0) {
			if (name == null) {
				setProp(val, null);
				return val;
			}
			return getProp(null).asObject().setMemberByName(name, val, flag);
		}
		throw new NotImplementedException();
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		if((flag&KEnvironment.IGNOREPROP)==0) {
			if (name == null)
				return true;
			return getProp(null).asObject().hasMemberByName(name, flag);
		}
		return name==null;
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KObject objthis, int flag) throws KSException {
		if((flag&KEnvironment.IGNOREPROP)!=0) throw new ScriptException("错误的函数调用");
		return getProp(null).asObject().funcCallByName(name, args, objthis, flag);
	}

	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		if((flag&KEnvironment.IGNOREPROP)!=0) throw new MemberNotFoundException(num);
		return getProp(null).asObject().getMemberByNum(num, flag);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag, KObject objthis) throws KSException {
		return getProp(null).asObject().getMemberByVariant(var, flag, objthis);
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		return getProp(null).asObject().setMemberByNum(num, val, flag);
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		return getProp(null).asObject().setMemberByVariant(var, val, flag);
	}

	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		return getProp(null).asObject().hasMemberByNum(num);
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		return getProp(null).asObject().hasMemberByVariant(var);
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		return getProp(null).asObject().deleteMemberByName(name);
	}

	@Override
	public boolean deleteMemberByNum(int num) throws KSException {
		return getProp(null).asObject().deleteMemberByNum(num);
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		return getProp(null).asObject().deleteMemberByVariant(var);
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		return getProp(null).asObject().doOperationByName(op, name, opr);
	}

	@Override
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		return getProp(null).asObject().doOperationByNum(op, num, opr);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return getProp(null).asObject().doOperationByVariant(op, var, opr);
	}

	@Override
	public boolean isInstanceOf(String str) throws KSException {
		return getProp(null).asObject().equals(str);
	}

	@Override
	public boolean invalidate() throws KSException {
		return getProp(null).asObject().invalidate();
	}

	@Override
	public KObject newInstance() throws KSException {
		return getProp(null).asObject().newInstance();
	}

	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KObject objthis, int flag) throws KSException {
		return getProp(null).asObject().funcCallByNum(num, args, objthis, flag);
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		getProp(null).asObject().EnumMembers(cosumer, flag);
	}

	public void setProp(KVariant x, KObject env) throws KSException {
		if (env == null) {
			backed.setProp(x, null);
		} else {
			backed.setProp(x, env);
		}
	}

	public KVariant getProp(KObject env) throws KSException {
		if (env == null)
			return backed.getProp(null);
		return backed.getProp(env);
	}

	@Override
	public String getInstanceName() {
		return "Property";
	}

}
