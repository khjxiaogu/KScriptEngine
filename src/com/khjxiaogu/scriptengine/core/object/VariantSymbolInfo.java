package com.khjxiaogu.scriptengine.core.object;

import com.khjxiaogu.scriptengine.core.KVariant;

public class VariantSymbolInfo {
	KVariant val;
	int flag;
	public VariantSymbolInfo(KVariant val, int flag) {
		super();
		this.val = val;
		setFlag(flag);
	}
	public KVariant getVal() {
		return val;
	}
	public void setVal(KVariant val) {
		this.val = val;
	}
	public int getFlag() {
		return flag<<16;
	}
	public void setFlag(int flag) {
		this.flag = (flag&KEnvironment.VAR_MODI_MASK)>>16;
	}
}
