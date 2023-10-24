package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public class KEnvironmentReference implements KVariantReference {
	KEnvironment env;
	String key;
	int idx;
	KVariant eval;
	int flag=KEnvironment.DEFAULT;
	public KEnvironmentReference(KEnvironment env, int idx) {
		super();
		this.env = env;
		this.idx = idx;
	}

	public KEnvironmentReference(KEnvironment env, String key) {
		super();
		this.env = env;
		this.key = key;
	}

	public KEnvironmentReference(KEnvironment env, String key, int idx) {
		super();
		this.env = env;
		if(idx<0)
			this.key = key;
		else
			this.idx = idx;
	}

	public KEnvironmentReference(KEnvironment env, KVariant eval) {
		this.env=env;
		this.eval=eval;
	}
	public KEnvironmentReference withFlag(int flag) {
		this.flag|=flag;
		return this;
	}
	@Override
	public KVariant setValue(KVariant newval,int cflag) throws KSException {
		if(eval!=null)
			env.setMemberByVariant(eval, newval, flag|cflag);
		else if(key!=null)
			env.setMemberByName(key, newval, flag|cflag);
		else
			env.setMemberByNum(idx, newval, flag|cflag);
		return newval;
	}

	@Override
	public KVariant getValue(int cflag) throws KSException {
		if(eval!=null)
			env.getMemberByVariant(eval, flag|cflag, null);
		else if(key!=null)
			return env.getMemberByName(key, flag|cflag, null);
		return env.getMemberByNum(idx, flag|cflag);
	}
	@Override
	public KVariant funcCall(KVariant[] args,int cflag) throws KSException {
		if(eval!=null)
			return env.funcCallByName(eval.asString(), args, (env instanceof KObject)?(KObject)env:null, flag|cflag);
		if(key!=null)
			return env.funcCallByName(key, args, (env instanceof KObject)?(KObject)env:null, flag|cflag);
		return env.funcCallByNum(idx, args, (env instanceof KObject)?(KObject)env:null, cflag|flag);
	}
	@Override
	public boolean delete(int cflag) throws KSException {
		if(eval!=null)
			env.deleteMemberByVariant(eval);
		else if(key!=null)
			return env.deleteMemberByName(key);
		return env.deleteMemberByNum(idx);
	}

}
