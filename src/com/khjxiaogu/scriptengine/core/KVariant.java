/*
 * file: KVariant.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
import com.khjxiaogu.scriptengine.core.object.KEnvironment;
import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.AssignOperation;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.typeconvert.ConversionException;
import com.khjxiaogu.scriptengine.core.typeconvert.ConversionManager;
import com.khjxiaogu.scriptengine.core.typeconvert.Converter;
import com.khjxiaogu.scriptengine.core.typeconvert.TypeInfo;

// TODO: Auto-generated Javadoc
/**
 * Class KVariant.
 *
 * @author khjxiaogu
 * @time 2019年8月23日 file:KVariant.java
 */
public class KVariant implements Cloneable {

	/**
	 * The type.<br />
	 * 变量类型.
	 */
	private final TypeInfo type;

	/**
	 * The value.<br />
	 * 变量值.
	 */
	private final Object value;



	private final static KVariant EMPTY_STR = new KVariant("");
	
	private final static KVariant VOID = new KVariant();
	private final static KVariant[] INT=new KVariant[509];
	private static boolean initedInts=false;
	private static void initInts(){
		if(initedInts)return;
		initedInts=true;
		for(int i=-254;i<255;i++) {
			INT[i+254]=new KVariant(i);
		}
	}
	/**
	 * Constant TRUE.<br />
	 * 常量 TRUE.
	 */
	public final static KVariant TRUE = KVariant.valueOf(true);
	/**
	 * Constant FALSE.<br />
	 * 常量 FALSE.
	 */
	public final static KVariant FALSE = KVariant.valueOf(false);
	/**
	 * Instantiates a new KVariant.<br />
	 * 新建一个KVariant类<br />
	 */
	private KVariant() {
		type = TypeInfo.forTypeConstant(Void.class);
		value = null;
	}

	/**
	 * Copy construct KVariant.<br />
	 * 复制一个KVariant类<br />
	 *
	 * @param ref the class to copy<br />
	 */
	private KVariant(KVariant ref) {
		type = ref.type;
		value = ref.value;
	}

	/**
	 * Instantiates a new KVariant with a KObject object as value.<br />
	 * 使用一个KObject作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	private KVariant(KObject val) {
		type = TypeInfo.forTypeConstant(KObject.class);
		value = val;
	}

	/**
	 * Instantiates a new KVariant with a Double object as value.<br />
	 * 使用一个Double作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	private KVariant(Double val) {
		value=val;
		type = TypeInfo.forTypeConstant(Double.class);
		
	}

	/**
	 * Instantiates a new KVariant with a double object as value.<br />
	 * 使用一个double作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	private KVariant(double val) {
		value=val;
		type = TypeInfo.forTypeConstant(Double.class);
	}

	/**
	 * Instantiates a new KVariant with a Long object as value.<br />
	 * 使用一个Long作为值新建一个KVariant类<br />
	 *
	 * @param val the value <br />
	 */
	private KVariant(Long val) {
		type = TypeInfo.forTypeConstant(Long.class);
		value = val;
	}

	/**
	 * Instantiates a new KVariant with a longeger value.<br />
	 * 使用一个整数作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	private KVariant(long val) {
		type = TypeInfo.forTypeConstant(Long.class);
		value = Long.valueOf(val);
	}

	/**
	 * Instantiates a new KVariant with a String object as value.<br />
	 * 使用一个String作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	private KVariant(String val) {
		type = TypeInfo.forTypeConstant(String.class);
		value = val;
	}

	/**
	 * Instantiates a new KVariant with a boolean object as value.<br />
	 * 使用一个boolean作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	private KVariant(boolean val) {
		type = TypeInfo.forTypeConstant(Long.class);
		value = val ? 1L : 0L;
	}

	/**
	 * Instantiates a new KVariant with a Object object as value,which would
	 * automatic infer its type.<br />
	 * 使用一个Object作为值新建一个KVariant类，并自动推断object的类型<br />
	 *
	 * @param val the val<br />
	 *
	 */
	private KVariant(Object val) {
		type = TypeInfo.forType(val.getClass());
		value = val;
	}

	/**
	 * Instantiates a new KVariant,specified type and value<br />
	 * 新建一个指定类型和值KVariant类。<br />
	 * .
	 *
	 * @param val  the value<br />
	 * @param type the type<br />
	 */
	private KVariant(Object val, TypeInfo type) {
		this.type = type;
		value = val;
	}

	/**
	 * Instantiates a new KVariant,specified type and value<br />
	 * 新建一个指定类型和值KVariant类。<br />
	 * .
	 *
	 * @param val  the value<br />
	 * @param type the type<br />
	 */
	private KVariant(Object val, String type) {
		this.type = TypeInfo.forName(type);
		value = val;
	}
	public static KVariant valueOf() {
		return VOID;
	}
	public static KVariant valueOf(Double d) {
		if(d==null)return valueOf();
		return valueOf(d.doubleValue());
	}
	public static KVariant valueOf(double d) {
		if(d==(long)d)
			return valueOf((long)d);
		return new KVariant(d);
	}

	public static KVariant valueOf(Long d) {
		if(d==null)
			return valueOf();
		return valueOf(d.doubleValue());
	}
	public static KVariant valueOf(long d) {
		if(d>-255&&d<255) {
			initInts();
			return INT[(int) (d+254)];
		}
		return new KVariant(d);
	}
	public static KVariant valueOf(String s) {
		if(s.isEmpty())return EMPTY_STR;
		return new KVariant(s);
	}
	public static KVariant valueOf(boolean b) {
		return valueOf(b?1L:0L);
	}
	public static KVariant valueOf(Object obj) {
		return new KVariant(obj);
	}
	public static KVariant valueOf(Object obj,TypeInfo ti) {
		return new KVariant(obj,ti);
	}
	public static KVariant valueOf(KVariant val) {
		return val;
	}
	/**
	 * Gets the type.<br />
	 * 获取类型.
	 *
	 * @return type<br />
	 */
	public TypeInfo getType() {
		return type;
	}

	/**
	 * Gets the value.<br />
	 * 获取值.
	 *
	 * @return value<br />
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * copy value from another variant.<br />
	 * 从其他变量复制值
	 *
	 * @param ref the variant to copy<br />
	 * @return return this variant <br />
	 *         返回本对象
	 */
	/*public KVariant setValue(KVariant ref) {
		type = ref.type;
		value = ref.value;
		return this;
	}*/

	/**
	 * Set value to an object,which would automatic infer its type.<br />
	 * 设置值，自动推断类型
	 *
	 * @param ref the value<br />
	 * @return return this variant<br />
	 *         返回本对象
	 */
	/*public KVariant set(Object ref) {
		type = TypeInfo.forType(ref.getClass());
		value = ref;
		return this;
	}*/

	/**
	 * Convert variant to number.<br />
	 * 转换为数字
	 *
	 * @return return new type <br />
	 *         返回类型
	 * @throws KSException 
	 */
	/*public TypeInfo asNumber() throws KSException {
		if (value instanceof Long || value instanceof Double)
			return type;
		try {
			value = ConversionManager.getConversion(Double.class).from(this);
		} catch (ConversionException e) {
			e.setType(type.getName(), "Number");
			throw e;
		}
		double realV = (Double) value;
		if (realV == (long) realV) {
			value = Long.valueOf((long) realV);
			return type = TypeInfo.forTypeConstant(Long.class);
		}
		return type = TypeInfo.forTypeConstant(Double.class);
	}*/

	/**
	 * Gets the number value,does NOT change this variant.<br />
	 * 获取数值，不转换变量.
	 *
	 * @return number<br />
	 * @throws KSException 
	 */
	public Double asNumber() throws KSException {
		try {
			if (!(value instanceof Double))
				return (Double) ConversionManager.getConversion(Double.class).from(this);
			return (Double) value;
		} catch (ConversionException e) {
			e.setType(type.getName(), "Number");
			throw e;
		}
	}

	/**
	 * Gets the longeger value,does NOT change this variant.<br />
	 * 获取整数值，不转换变量.
	 *
	 * @return result<br />
	 * @throws KSException 
	 */
	public int asInt() throws KSException {
		if (!(value instanceof Long))
			return (int) ConversionManager.getConversion(Long.class).from(this);
		return (int)(long) value;
	}
	/**
	 * Gets the longeger value,does NOT change this variant.<br />
	 * 获取整数值，不转换变量.
	 *
	 * @return result<br />
	 * @throws KSException 
	 */
	public long asLong() throws KSException {
		if (!(value instanceof Long))
			return (Long) ConversionManager.getConversion(Long.class).from(this);
		return (Long) value;
	}
	/**
	 * set as number,would turn double to longeger if possible,if double type should
	 * be keep,use {@link #setDouble(Double)} instead<br />
	 * 设置值为数值，会把没有小数部分的实数转换为整数，如果需要保留类型，请使用{@link #setDouble(Double)}.
	 *
	 * @param val number to set to.<br />
	 *            设置为的值
	 * @throws KSException if convert fails<br />
	 *                             如果转换失败
	 */
	/*public void setNumber(Double val) throws KSException {
		value = val;
		double realV = (Double) value;
		if (realV == (long) realV) {
			value = Long.valueOf((long) realV);
			type = TypeInfo.forTypeConstant(Long.class);
		} else {
			type = TypeInfo.forTypeConstant(Double.class);
		}
	}
	public void setNumber(Long val) throws KSException {
		value = val;
		type = TypeInfo.forTypeConstant(Long.class);
	}*/
	/**
	 * set value to double.<br />
	 * 设置小数值.
	 *
	 * @param val value to set to.<br />
	 *            设置为的值
	 * @throws KSException if convert fails<br />
	 *                             如果转换失败
	 */
	/*public void setDouble(Double val) throws KSException {
		value = val;
		type = TypeInfo.forTypeConstant(Double.class);
	}*/

	/**
	 * set number to a longeger value.<br />
	 * 设置值为整数.
	 *
	 * @param val number to set<br />
	 *            要设置的数值.
	 */
	/*public void setNumber(long val) {
		value = val;
		type = TypeInfo.forTypeConstant(Long.class);
	}*/

	/**
	 * get the variant as a specific type,changes to the original variant.
	 * 转换变量类型为目标类型。
	 *
	 * @param <T>    the target type<br />
	 *               目标类型
	 * @param toType the to type<br />
	 *               目标类型
	 * @return return value converted <br />
	 *         返回转换后的值
	 * @throws KSException if convert fails<br />
	 *                             如果转换失败
	 */
	/*@SuppressWarnings("unchecked")
	public <T> T asType(Class<T> toType) throws KSException {
		if (toType.isInstance(value))
			return (T) value;
		Converter conv = ConversionManager.getConversion(toType);
		type = conv.getOutTypeInfo();
		return (T) (value = conv.from(this));
	}*/

	/**
	 * get the value as a specific type.
	 * 获得对应变量的目标类型的值
	 *
	 * @param <T>    the generic type<br />
	 *               泛型参数
	 * @param toType the to type<br />
	 * @return return to type <br />
	 *         返回 t
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	@SuppressWarnings("unchecked")
	public <T> T asType(Class<T> toType) throws KSException {
		if (toType.isInstance(value))
			return (T) value;
		return (T) ConversionManager.getConversion(toType).from(this);
	}

	/**
	 * get the variant to specific type.
	 *
	 * @param name the name<br />
	 * @return return to type <br />
	 *         返回 object
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	/*public Object toType(String name) throws KSException {
		if (type.getName().equals(name))
			return value;
		return ConversionManager.getConversion(name).from(this);
	}*/

	/**
	 * get the variant to specific type,changes the original variant.
	 *
	 * @param name the name<br />
	 * @return return as type <br />
	 *         返回 object
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public Object asType(String name) throws KSException {
		if (type.getName().equals(name))
			return value;
		Converter conv = ConversionManager.getConversion(name);
		return conv.from(this);
	}

	/**
	 * Self increment.<br />
	 *
	 * @param dir the dir<br />
	 * @return return self increment <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant selfIncrement(Associative dir,KVariantReference ref) throws KSException {
		asNumber();
		if (dir == Associative.LEFT) {
			if (value instanceof Long) {
				ref.setValue(valueOf((Long) value + 1));
			} else if (value instanceof Double) {
				ref.setValue(valueOf((Double) value + 1));
			}
			return this;
		}
		if (value instanceof Long) {
			ref.setValue(valueOf((Long) value + 1));
		} else if (value instanceof Double) {
			ref.setValue(valueOf((Double) value + 1));
		}
		return this;
	}

	/**
	 * Self decrement.<br />
	 *
	 * @param dir the dir<br />
	 * @return return self decrement <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant selfDecrement(Associative dir,KVariantReference ref) throws KSException {
		asNumber();
		if (dir == Associative.LEFT) {
			if (value instanceof Long) {
				ref.setValue(valueOf((Long) value - 1));
			} else if (value instanceof Double) {
				ref.setValue(valueOf((Double) value - 1));
			}
			return this;
		}
		if (value instanceof Long) {
			ref.setValue(valueOf((Long) value - 1));
		} else if (value instanceof Double) {
			ref.setValue(valueOf((Double) value - 1));
		}
		return this;
	}

	/**
	 * Multiply.<br />
	 *
	 * @param By the by<br />
	 * @return return multiply <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant multiply(KVariant By) throws KSException {
		return valueOf(asNumber() * By.asNumber());

	}

	/**
	 * Mod.<br />
	 *
	 * @param By the by<br />
	 * @return return mod <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant mod(KVariant By) throws KSException {
		return valueOf(Math.floorMod(asLong(), By.asLong()));
	}

	/**
	 * Floor divide.<br />
	 *
	 * @param By the by<br />
	 * @return return floor divide <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant floorDivide(KVariant By) throws KSException {
		return valueOf(Math.floorDiv(asLong(), By.asLong()));
	}

	/**
	 * Divide.<br />
	 *
	 * @param By the by<br />
	 * @return return divide <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant divide(KVariant By) throws KSException {
		return valueOf(asNumber() / By.asNumber());
	}

	/**
	 * Add.<br />
	 *
	 * @param By the by<br />
	 * @return return add <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant add(KVariant By) throws KSException {
		if (type.getType() != String.class && By.getType().getType() != String.class)
			return valueOf(asNumber() + By.asNumber());
		else
			return valueOf(toString() + By.toString());
	}

	/**
	 * Minus.<br />
	 *
	 * @param By the by<br />
	 * @return return minus <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant minus(KVariant By) throws KSException {
		return valueOf(asNumber() - By.asNumber());
	}

	/**
	 * Rsh.<br />
	 *
	 * @param by the by<br />
	 * @return return rsh <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant RSH(long by) throws KSException {
		Long value = asLong();
		return valueOf(value >> by);
	}

	/**
	 * Lsh.<br />
	 *
	 * @param by the by<br />
	 * @return return lsh <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant LSH(long by) throws KSException {
		Long value = asLong();
		return valueOf(value << by);
	}

	/**
	 * Arsh.<br />
	 *
	 * @param by the by<br />
	 * @return return arsh <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant ARSH(long by) throws KSException {
		Long value = asLong();
		return valueOf(value >>> by);
	}

	/**
	 * Lt.<br />
	 *
	 * @param by the by<br />
	 * @return return lt <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant LT(KVariant by) throws KSException {
		if (type.getType() == String.class && by.type.getType() == String.class)
			return valueOf(((String) value).compareTo(by.asType(String.class)) < 0);
		Double value = asNumber();
		return valueOf(value < by.asNumber());
	}

	/**
	 * Gt.<br />
	 *
	 * @param by the by<br />
	 * @return return gt <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant GT(KVariant by) throws KSException {
		if (type.getType() == String.class && by.type.getType() == String.class)
			return valueOf(((String) value).compareTo(by.asType(String.class)) > 0);
		Double value = asNumber();
		return valueOf(value > by.asNumber());
	}

	/**
	 * Loet.<br />
	 *
	 * @param by the by<br />
	 * @return return loet <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant LOET(KVariant by) throws KSException {
		if (type.getType() == String.class && by.type.getType() == String.class)
			return valueOf(((String) value).compareTo(by.asType(String.class)) <= 0);
		Double value = asNumber();
		return valueOf(value <= by.asNumber());
	}

	/**
	 * Goet.<br />
	 *
	 * @param by the by<br />
	 * @return return goet <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant GOET(KVariant by) throws KSException {
		if (type.getType() == String.class && by.type.getType() == String.class)
			return valueOf(((String) value).compareTo(by.asType(String.class)) >= 0);
		Double value = asNumber();
		return valueOf(value >= by.asNumber());
	}

	/**
	 * Exact equals.<br />
	 *
	 * @param another the another<br />
	 * @return true, if <br />
	 *         如果，返回true。
	 */
	public boolean ExactEquals(KVariant another) {
		if (another == this)
			return true;
		if (type.getName() != another.type.getName())
			return false;
		return value.equals(another.value);
	}

	/**
	 * Band.<br />
	 *
	 * @param by the by<br />
	 * @return return band <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BAND(KVariant by) throws KSException {
		Long value = asLong();
		return valueOf(value & by.asLong());
	}

	/**
	 * Bor.<br />
	 *
	 * @param by the by<br />
	 * @return return bor <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BOR(KVariant by) throws KSException {
		Long value = asLong();
		return valueOf(value | by.asLong());
	}

	/**
	 * Bxor.<br />
	 *
	 * @param by the by<br />
	 * @return return bxor <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BXOR(KVariant by) throws KSException {
		Long value = asLong();
		return valueOf(value ^ by.asLong());
	}

	/**
	 * As boolean.<br />
	 *
	 * @return true, if <br />
	 *         如果，返回true。
	 */
	public boolean asBoolean() {
		if(value != null) {
			if (value instanceof Number&&((Number) value).doubleValue()==0D)
				return false;
			return true;
		}
		return false;
	}
	/**
	 * Equals.<br />
	 *
	 * @param another the another<br />
	 * @return true, if <br />
	 *         如果，返回true。
	 */
	@Override
	public boolean equals(Object another) {
		if (another == this)
			return true;
		if (!(another instanceof KVariant))
			return false;
		KVariant by = (KVariant) another;
		if (type.getName() == by.type.getName())
			return value.equals(by.value);
		try {
			return asNumber().equals(by.asNumber());
		} catch (KSException e) {
			return false;
		}
	}

	/**
	 * Hash code.<br />
	 *
	 * @return return hash code <br />
	 *         返回 long
	 */
	@Override
	public int hashCode() {
		if (value != null)
			return value.hashCode();
		else
			return 0;
	}

	/**
	 * To string.<br />
	 *
	 * @return return to string <br />
	 *         返回 string
	 */
	@Override
	public String toString() {
		try {
			return asType(String.class);
		} catch (KSException e) {
			return "(" + type.getName() + ")" + hashCode();
		}
	}

	/**
	 * As string.<br />
	 *
	 * @return return as string <br />
	 *         返回 string
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public String asString() throws KSException {
		return asType(String.class);
	}

	/**
	 * Checks if is null.<br />
	 * 是否 null.
	 *
	 * @return 如果是null，返回true<br />
	 *         if is null,true.
	 */
	public boolean isNull() {
		if (value == null || value instanceof String && ((String) value).length() == 0)
			return true;
		return false;
	}
	public KVariant withStance(KEnvironment env) {
		return null;
		
	}
	public KVariant doOperation(AssignOperation op,KVariant opr,KVariantReference ref) throws KSException {
		KVariant result=this;
		switch (op) {
		case ADD:
			result=this.add(opr);
		case ARSH:
			result=this.ARSH(opr.asInt());
		case BAND:
			result=this.BAND(opr);
		case BOR:
			result=this.BOR(opr);
		case BXOR:
			result=this.BXOR(opr);
		case DIV:
			result=this.divide(opr);
		case EQ:
			result=opr;
		case FDIV:
			result=this.floorDivide(opr);
		case LAND:
			result=valueOf(this.asBoolean() && opr.asBoolean());
		case LOR:
			result=valueOf(this.asBoolean() || opr.asBoolean());
		case LSH:
			result=this.LSH(opr.asInt());
		case MIN:
			result=this.minus(opr);
		case MOD:
			result=this.mod(opr);
		case MUL:
			result=this.multiply(opr);
		case RSH:
			result=this.RSH(opr.asInt());
		}
		ref.setValue(result);
		return result;
	}
}
