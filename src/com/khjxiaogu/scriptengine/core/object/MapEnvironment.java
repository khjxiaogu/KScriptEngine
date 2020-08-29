package com.khjxiaogu.scriptengine.core.object;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class MapEnvironment implements KEnvironment {
	private Map<String, KVariant> map = new ConcurrentHashMap<>();

	public MapEnvironment() {
	}

	@Override
	public KVariant getMemberByName(String name, int flag) throws KSException {
		// TODO Auto-generated method stub
		if(name==null||name.length()==0)
			return new KVariant(this);
		KVariant res = map.get(name);
		if (res == null) {
			if ((flag & KEnvironment.MUSTEXIST) != 0)
				throw new MemberNotFoundException(name);
			return new KVariant();
		}
		if ((flag & KEnvironment.IGNOREPROP) == 0) {
			if (res != null && res.getType().getType() == KObject.class && res.getValue() instanceof KProperty)
				return ((KProperty) res.getValue()).getProp(null);
		}
		return res;
	}

	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		// TODO Auto-generated method stub
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag) throws KSException {
		// TODO Auto-generated method stub
		KVariant res = null;
		String name = var.toString();
		if(name.length()==0)
			return new KVariant(this);
		res = map.get(name);
		if (res == null) {
			res = new KVariant();
		}
		if ((flag & KEnvironment.IGNOREPROP) == 0) {
			if (res != null && res.getType().getType() == KObject.class && res.getValue() instanceof KProperty)
				return ((KProperty) res.getValue()).getProp(null);
		}
		return res;
	}

	@Override
	public KVariant setMemberByName(String name, KVariant val, int flag) throws KSException {
		// TODO Auto-generated method stub
		if(name==null||name.length()==0)
			throw new MemberNotFoundException("");
		if ((flag & KEnvironment.MUSTEXIST) != 0)
			if (!map.containsKey(name))
				throw new MemberNotFoundException(name);
		if ((flag & KEnvironment.IGNOREPROP) == 0) {
			KVariant va = map.get(name);
			if (va != null && va.getType().getType() == KObject.class && va.getValue() instanceof KProperty) {
				((KProperty) va.getValue()).setProp(val, null);
				return val;
			}
		}
		map.put(name, val);
		return val;
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		// TODO Auto-generated method stub
		String name = Integer.toString(num);
		if ((flag & KEnvironment.IGNOREPROP) == 0) {
			KVariant va = map.get(name);
			if (va != null && va.getType().getType() == KObject.class && va.getValue() instanceof KProperty) {
				((KProperty) va.getValue()).setProp(val, null);
				return val;
			}
		}
		map.put(name, val);
		return val;
	}

	@Override
	public KVariant setMemberByVariant(KVariant var, KVariant val, int flag) throws KSException {
		// TODO Auto-generated method stub
		String name = null;
		name = var.toString();
		if(name==null||name.length()==0)
			throw new MemberNotFoundException("");
		if ((flag & KEnvironment.IGNOREPROP) == 0) {
			KVariant va = map.get(name);
			if (va != null && va.getType().getType() == KObject.class && va.getValue() instanceof KProperty) {
				((KProperty) va.getValue()).setProp(val, null);
				return val;
			}
		}
		map.put(name, val);
		return val;
	}

	@Override
	public boolean deleteMemberByName(String name) throws KSException {
		// TODO Auto-generated method stub
		return map.remove(name) != null;
	}

	@Override
	public boolean deleteMemberByNum(int num) throws KSException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteMemberByVariant(KVariant var) throws KSException {
		return map.remove(var.toString()) != null;
	}

	@Override
	public KVariant doOperationByName(AssignOperation op, String name, KVariant opr) throws KSException {
		KVariant v=this.getMemberByName(name,KEnvironment.DEFAULT);
		return v.doOperation(op, opr);
	}

	@Override
	public KVariant doOperationByNum(AssignOperation op, int num, KVariant opr) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant doOperationByVariant(AssignOperation op, KVariant var, KVariant opr) throws KSException {
		return doOperationByName(op, var.toString(), opr);
	}

	@Override
	public boolean hasMemberByName(String name, int flag) throws KSException {
		return map.containsKey(name);
	}

	@Override
	public boolean hasMemberByNum(int num) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public boolean hasMemberByVariant(KVariant var) throws KSException {
		return map.containsKey(var.toString());
	}

	@Override
	public KVariant funcCallByNum(int num, KVariant[] args, KEnvironment objthis, int flag) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KEnvironment objthis, int flag) throws KSException {
		KVariant res = this.getMemberByName(name, flag);
		if (res == null)
			throw new MemberNotFoundException(name);
		KObject obj = res.toType(KObject.class);
		if (obj instanceof CallableFunction)
			return ((CallableFunction) obj).FuncCall(args, objthis == null ? this : objthis);
		else
			throw new ScriptException("呼叫的对象不是函数");
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		for (Map.Entry<String, KVariant> me : map.entrySet()) {
			KVariant va = me.getValue();
			if ((flag & KEnvironment.IGNOREPROP) == 0) {
				if (va != null && va.getType().getType() == KObject.class && va.getValue() instanceof KProperty) {
					va = ((KProperty) va.getValue()).getProp(null);
				}
			}
			if (!cosumer.execute(new KVariant(me.getKey()), va)) {
				break;
			}
		}
	}

	@Override
	public <T> T getNativeInstance(Class<T> cls) throws KSException {
		return null;
	}

	@Override
	public void putNativeInstance(Object nis) throws KSException {
	}

	@Override
	public KEnvironment getThis() throws ScriptException {
		throw new ScriptException("无法定位this的类。");
	}
	@Override
	public String toString() {
		return "(Object)(0x"+super.hashCode()+")";
	}
	@Override
	public KEnvironment getSuper() throws InvalidSuperClassException {
		throw new InvalidSuperClassException();
	}

}
