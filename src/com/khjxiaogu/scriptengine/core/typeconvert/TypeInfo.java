package com.khjxiaogu.scriptengine.core.typeconvert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.khjxiaogu.scriptengine.core.Object.KObject;

public class TypeInfo {
	private String name;
	private Class<?> type;
	private static Map<String,TypeInfo> types=new ConcurrentHashMap<>();
	private static Map<Class<?>,TypeInfo> typeclasses=new ConcurrentHashMap<>();
	static {
		new TypeInfo("Integer",Integer.class);
		new TypeInfo("Real",Double.class);
		new TypeInfo("String",String.class);
		new TypeInfo("Object",KObject.class);
		new TypeInfo("Octet",byte[].class);
		new TypeInfo("void",Void.class);
	}
	/**
	 * 注册一个类型信息
	 * @param name 类型名
	 * @param type 实际类型
	 */
	public TypeInfo(String name,Class<?> type) {
		this.name=name;
		this.type=type;
		types.put(name,this);
		typeclasses.put(type,this);
		// TODO Auto-generated constructor stub
	}
	/**
	 * 根据名字获取一个存在的类型信息
	 * @param name 类型名
	 * @return 对应类型信息，如不存在，则为Null
	 */
	public static TypeInfo forName(String name) {
		return types.get(name);
		// TODO Auto-generated constructor stub
	}
	public static TypeInfo forType(Class<?> type) {
			/*if(type.isAssignableFrom(KObject.class))
				return forName("Object");
			else
				return typeclasses.get(type);*/
			for(Map.Entry<Class<?>, TypeInfo> entry:typeclasses.entrySet()) {
				if(type.isAssignableFrom(entry.getKey()))
					return entry.getValue();
			}
			return forName("void");
		// TODO Auto-generated constructor stub
	}
	public static void registClassAlias(Class<?> type,String orig) {
		typeclasses.put(type,types.get(orig));
	}
	/**
	 *  根据名字获取一个存在的类型信息
	 *  如不存在，创建这个类型并返回
	 * @param name 类型名
	 * @param expected 对应的类型
	 * @return 对应的类型信息
	 */
	public static TypeInfo forName(String name,Class<?> expected) {
		TypeInfo ret;
		if((ret=forName(name))!=null)
			return ret;
		return new TypeInfo(name,expected);
	}
	public String getName() {
		return name;
	}
	public Class<?> getType(){
		return type;
	}
	@Override
	public String toString() {
		return name+"("+type.getName()+")";
	}
	@Override
	public boolean equals(Object another) {
		if(another==null)return false;
		if(another instanceof TypeInfo) {
			if(another==this)
				return true;
			if(((TypeInfo) another).name==this.name)
				return true;
		}
		return false;
	}
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
