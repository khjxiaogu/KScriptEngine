package com.khjxiaogu.scriptengine.core.object.internal;

import com.khjxiaogu.scriptengine.core.KVariant;
import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.ExtendableClosure;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.NativeProperty;
import com.khjxiaogu.scriptengine.core.object.PropertyClosure;

public class Exception extends ExtendableClosure {
	public Exception(KSException ex) throws KSException {
		super("Exception");
		super.putNativeInstance(ex);
		this.setMemberByName("message",
			new KVariant(
				new PropertyClosure(
					new NativeProperty<KSException>(
						KSException.class,
						(e)->{
							return new KVariant(e.getMessage());
						},null
					)
				)
			),KEnvironment.DEFAULT
		);
	}

}
