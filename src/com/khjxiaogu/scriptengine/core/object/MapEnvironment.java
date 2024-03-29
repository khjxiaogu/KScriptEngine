package com.khjxiaogu.scriptengine.core.object;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.InvalidSuperClassException;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.exceptions.MemberNotFoundException;
import com.khjxiaogu.scriptengine.core.exceptions.NotImplementedException;
import com.khjxiaogu.scriptengine.core.exceptions.ScriptException;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;

public class MapEnvironment implements KEnvironment {
	private Map<String, VariantSymbolInfo> map = new ConcurrentHashMap<>();

	public MapEnvironment() {
	}
	public KVariant getMemberOfFlag(String key,int flag) throws KSException {
		VariantSymbolInfo sym=map.get(key);
		if((flag&KEnvironment.STATIC_MEMBER)!=0) {
			if((sym.getFlag()&KEnvironment.STATIC_MEMBER)!=0) {
				return sym.getVal();
			}
			if ((flag & KEnvironment.MUSTEXIST) != 0)
				throw new MemberNotFoundException(key);
			map.put(key, new VariantSymbolInfo(KVariant.valueOf(),0));
			return KVariant.valueOf();
		}
		return sym.getVal();
	}
	@Override
	public KVariant getMemberByName(String name, int flag, KObject objthis) throws KSException {
		// TODO Auto-generated method stub
		if(name==null||name.length()==0)
			return KVariant.valueOf(this);
		KVariant res = getMemberOfFlag(name,flag);
		if ((flag & KEnvironment.IGNOREPROP) == 0) {
			if (res != null && res.isObject())
				return res.asObject().getMemberByName(null, flag, objthis);
		}
		return res;
	}

	@Override
	public KVariant getMemberByNum(int num, int flag) throws KSException {
		// TODO Auto-generated method stub
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant getMemberByVariant(KVariant var, int flag, KObject objthis) throws KSException {
		// TODO Auto-generated method stub
		KVariant res = null;
		String name = var.toString();
		if(name.length()==0)
			return KVariant.valueOf(this);
		res = getMemberOfFlag(name,flag);
		if ((flag & KEnvironment.IGNOREPROP) == 0) {
			if (res != null && res.isObject())
				return res.asObject().getMemberByName(null, flag, objthis);
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
			KVariant va = getMemberOfFlag(name,flag);
			if (va != null && va.isObject()) {
				try {
					return va.asObject().setMemberByName(null, val, flag);
				}catch(NotImplementedException ignored) {}
			}
		}
		map.put(name, new VariantSymbolInfo(val,flag));
		return val;
	}

	@Override
	public KVariant setMemberByNum(int num, KVariant val, int flag) throws KSException {
		// TODO Auto-generated method stub
		String name = Integer.toString(num);
		if ((flag & KEnvironment.IGNOREPROP) == 0) {
			KVariant va = getMemberOfFlag(name,flag);
			if (va != null && va.isObject()) {
				try {
					return va.asObject().setMemberByName(null, val, flag);
				}catch(NotImplementedException ignored) {}
			}
		}
		map.put(name, new VariantSymbolInfo(val,flag));
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
			KVariant va = getMemberOfFlag(name,flag);
			if (va != null && va.isObject()) {
				try {
					return va.asObject().setMemberByName(null, val, flag);
				}catch(NotImplementedException ignored) {}
			}
		}
		map.put(name, new VariantSymbolInfo(val,flag));
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
		KVariant v=this.getMemberByName(name,KEnvironment.DEFAULT, null);
		return v.doOperation(op, opr,new KEnvironmentReference(this,name));
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
	public KVariant funcCallByNum(int num, KVariant[] args, KObject objthis, int flag) throws KSException {
		throw new MemberNotFoundException("%" + num);
	}

	@Override
	public KVariant funcCallByName(String name, KVariant[] args, KObject objthis, int flag) throws KSException {
		KVariant res = this.getMemberByName(name, flag, null);
		if (res == null)
			throw new MemberNotFoundException(name);
		KObject obj = res.asObject();
		if (obj instanceof CallableFunction)
			return ((CallableFunction) obj).FuncCall(args,objthis);
		else
			throw new ScriptException("呼叫的对象不是函数");
	}

	@Override
	public void EnumMembers(Enumerator cosumer, int flag) throws KSException {
		for (Entry<String, VariantSymbolInfo> me : map.entrySet()) {
			VariantSymbolInfo va = me.getValue();
			KVariant kv=va.getVal();
			if ((flag & KEnvironment.IGNOREPROP) == 0) {
				if (va != null && kv.isObject()) {
					kv = kv.asObject().getMemberByName(null, flag, null);
				}
			}
			if (!cosumer.execute(KVariant.valueOf(me.getKey()),va.getFlag(), kv)) {
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

	@Override
	public boolean hasNativeInstance(Class<?> cls) throws KSException {
		return false;
	}

}
