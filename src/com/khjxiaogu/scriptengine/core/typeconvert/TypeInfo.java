package com.khjxiaogu.scriptengine.core.typeconvert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.object.KOctet;

public final class TypeInfo {
	private final String name;
	private final Class<?> type;
	private static Map<String, TypeInfo> types = new ConcurrentHashMap<>();
	private static Map<Class<?>, TypeInfo> typeclasses = new ConcurrentHashMap<>();
	static {
		new TypeInfo("Integer", Long.class);
		new TypeInfo("Real", Double.class);
		new TypeInfo("String", String.class);
		new TypeInfo("Object", KObject.class);
		new TypeInfo("Octet", KOctet.class);
		new TypeInfo("void", Void.class);
	}

	/**
	 * 注册一个类型信息
	 *
	 * @param name 类型名
	 * @param type 实际类型
	 */
	public TypeInfo(String name, Class<?> type) {
		this.name = name;
		this.type = type;
		TypeInfo.types.put(name, this);
		TypeInfo.typeclasses.put(type, this);
	}

	/**
	 * 根据名字获取一个存在的类型信息
	 *
	 * @param name 类型名
	 * @return 对应类型信息，如不存在，则为Null
	 */
	public static TypeInfo forName(String name) {
		return TypeInfo.types.get(name);
	}

	public static TypeInfo forType(Class<?> type) {
		TypeInfo ti = null;
		if ((ti = TypeInfo.forTypeConstant(type)) != null)
			return ti;
		for (Map.Entry<Class<?>, TypeInfo> entry : TypeInfo.typeclasses.entrySet()) {
			if (entry.getKey().isAssignableFrom(type))
				return entry.getValue();
		}
		return TypeInfo.forTypeConstant(Void.class);
	}

	public static TypeInfo forTypeConstant(Class<?> type) {
		return TypeInfo.typeclasses.get(type);
	}

	public static void registClassAlias(Class<?> type, String orig) {
		TypeInfo.typeclasses.put(type, TypeInfo.types.get(orig));
	}

	/**
	 * 根据名字获取一个存在的类型信息 如不存在，创建这个类型并返回
	 *
	 * @param name     类型名
	 * @param expected 对应的类型
	 * @return 对应的类型信息
	 */
	public static TypeInfo forName(String name, Class<?> expected) {
		TypeInfo ret;
		if ((ret = TypeInfo.forName(name)) != null)
			return ret;
		return new TypeInfo(name, expected);
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	@Override
	public String toString() {
		return name + "(" + type.getName() + ")";
	}

	@Override
	public boolean equals(Object another) {
		if (another == null)
			return false;
		if (another instanceof TypeInfo) {
			if (another == this)
				return true;
			if (((TypeInfo) another).name.equals(name))
				return true;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
