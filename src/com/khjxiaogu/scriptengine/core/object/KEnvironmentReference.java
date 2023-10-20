package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.KVariantReference;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;

public class KEnvironmentReference implements KVariantReference {
	KEnvironment env;
	String key;
	int idx;
	KVariant eval;
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

	@Override
	public KVariant setValue(KVariant newval) throws KSException {
		if(eval!=null)
			env.setMemberByVariant(eval, newval, KEnvironment.DEFAULT);
		else if(key!=null)
			env.setMemberByName(key, newval, KEnvironment.DEFAULT);
		else
			env.setMemberByNum(idx, newval, KEnvironment.DEFAULT);
		return newval;
	}

	@Override
	public KVariant getValue() throws KSException {
		if(eval!=null)
			env.getMemberByVariant(eval, KEnvironment.DEFAULT);
		else if(key!=null)
			return env.getMemberByName(key, KEnvironment.DEFAULT);
		return env.getMemberByNum(idx, KEnvironment.DEFAULT);
	}

	@Override
	public boolean delete() throws KSException {
		if(eval!=null)
			env.deleteMemberByVariant(eval);
		else if(key!=null)
			return env.deleteMemberByName(key);
		return env.deleteMemberByNum(idx);
	}

}
