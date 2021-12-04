/*
 * file: KVariant.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.exceptions.KSException;
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
	private TypeInfo type;

	/**
	 * The value.<br />
	 * 变量值.
	 */
	private Object value;

	/**
	 * Constant FALSE.<br />
	 * 常量 FALSE.
	 */
	public final static KVariant FALSE = new KVariant(false);

	/**
	 * Constant TRUE.<br />
	 * 常量 TRUE.
	 */
	public final static KVariant TRUE = new KVariant(true);

	/**
	 * Instantiates a new KVariant.<br />
	 * 新建一个KVariant类<br />
	 */
	public KVariant() {
		type = TypeInfo.forTypeConstant(Void.class);
		value = null;
	}

	/**
	 * Copy construct KVariant.<br />
	 * 复制一个KVariant类<br />
	 *
	 * @param ref the class to copy<br />
	 */
	public KVariant(KVariant ref) {
		type = ref.type;
		value = ref.value;
	}

	/**
	 * Instantiates a new KVariant with a KObject object as value.<br />
	 * 使用一个KObject作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	public KVariant(KObject val) {
		type = TypeInfo.forTypeConstant(KObject.class);
		value = val;
	}

	/**
	 * Instantiates a new KVariant with a Double object as value.<br />
	 * 使用一个Double作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	public KVariant(Double val) {
		try {
			setNumber(val);
		} catch (KSException e) {
			type = TypeInfo.forTypeConstant(Long.class);
			value = 0;
		}
	}

	/**
	 * Instantiates a new KVariant with a double object as value.<br />
	 * 使用一个double作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	public KVariant(double val) {
		try {
			setNumber(val);
		} catch (KSException e) {
			type = TypeInfo.forTypeConstant(Long.class);
			value = 0;
		}
	}

	/**
	 * Instantiates a new KVariant with a Long object as value.<br />
	 * 使用一个Long作为值新建一个KVariant类<br />
	 *
	 * @param val the value <br />
	 */
	public KVariant(Long val) {
		type = TypeInfo.forTypeConstant(Long.class);
		value = val;
	}

	/**
	 * Instantiates a new KVariant with a longeger value.<br />
	 * 使用一个整数作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	public KVariant(long val) {
		type = TypeInfo.forTypeConstant(Long.class);
		value = Long.valueOf(val);
	}

	/**
	 * Instantiates a new KVariant with a String object as value.<br />
	 * 使用一个String作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	public KVariant(String val) {
		type = TypeInfo.forTypeConstant(String.class);
		value = val;
	}

	/**
	 * Instantiates a new KVariant with a boolean object as value.<br />
	 * 使用一个boolean作为值新建一个KVariant类<br />
	 *
	 * @param val the value<br />
	 */
	public KVariant(boolean val) {
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
	public KVariant(Object val) {
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
	public KVariant(Object val, TypeInfo type) {
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
	public KVariant(Object val, String type) {
		this.type = TypeInfo.forName(type);
		value = val;
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
	public KVariant setValue(KVariant ref) {
		type = ref.type;
		value = ref.value;
		return this;
	}

	/**
	 * Set value to an object,which would automatic infer its type.<br />
	 * 设置值，自动推断类型
	 *
	 * @param ref the value<br />
	 * @return return this variant<br />
	 *         返回本对象
	 */
	public KVariant set(Object ref) {
		type = TypeInfo.forType(ref.getClass());
		value = ref;
		return this;
	}

	/**
	 * Convert variant to number.<br />
	 * 转换为数字
	 *
	 * @return return new type <br />
	 *         返回类型
	 * @throws KSException 
	 */
	public TypeInfo asNumber() throws KSException {
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
	}

	/**
	 * Gets the number value,does NOT change this variant.<br />
	 * 获取数值，不转换变量.
	 *
	 * @return number<br />
	 * @throws KSException 
	 */
	public Double getNumber() throws KSException {
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
	public int getInt() throws KSException {
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
	public long getLong() throws KSException {
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
	public void setNumber(Double val) throws KSException {
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
	}
	/**
	 * set value to double.<br />
	 * 设置小数值.
	 *
	 * @param val value to set to.<br />
	 *            设置为的值
	 * @throws KSException if convert fails<br />
	 *                             如果转换失败
	 */
	public void setDouble(Double val) throws KSException {
		value = val;
		type = TypeInfo.forTypeConstant(Double.class);
	}

	/**
	 * set number to a longeger value.<br />
	 * 设置值为整数.
	 *
	 * @param val number to set<br />
	 *            要设置的数值.
	 */
	public void setNumber(long val) {
		value = val;
		type = TypeInfo.forTypeConstant(Long.class);
	}

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
	@SuppressWarnings("unchecked")
	public <T> T asType(Class<T> toType) throws KSException {
		if (toType.isInstance(value))
			return (T) value;
		Converter conv = ConversionManager.getConversion(toType);
		type = conv.getOutTypeInfo();
		return (T) (value = conv.from(this));
	}

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
	public <T> T toType(Class<T> toType) throws KSException {
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
	public Object toType(String name) throws KSException {
		if (type.getName().equals(name))
			return value;
		return ConversionManager.getConversion(name).from(this);
	}

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
		type = conv.getOutTypeInfo();
		return value = conv.from(this);
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
	public KVariant selfIncrement(Associative dir) throws KSException {
		asNumber();
		if (dir == Associative.LEFT) {
			if (value instanceof Long) {
				value = Long.valueOf((Long) value + 1);
			} else if (value instanceof Double) {
				value = Double.valueOf((Double) value + 1);
			}
			return new KVariant(this);
		}
		KVariant ret = new KVariant(this);
		if (value instanceof Long) {
			value = Long.valueOf((Long) value + 1);
		} else if (value instanceof Double) {
			value = Double.valueOf((Double) value + 1);
		}
		return ret;
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
	public KVariant selfDecrement(Associative dir) throws KSException {
		asNumber();
		if (dir == Associative.LEFT) {
			if (value instanceof Long) {
				value = Long.valueOf((Long) value - 1);
			} else if (value instanceof Double) {
				value = Double.valueOf((Double) value - 1);
			}
			return new KVariant(this);
		}
		KVariant ret = new KVariant(this);
		if (value instanceof Long) {
			value = Long.valueOf((Long) value - 1);
		} else if (value instanceof Double) {
			value = Double.valueOf((Double) value - 1);
		}
		return ret;
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
		return new KVariant(getNumber() * By.getNumber());

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
		return new KVariant(Math.floorMod(getInt(), By.getInt()));
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
		return new KVariant(Math.floorDiv(getInt(), By.getInt()));
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
		return new KVariant(getNumber() / By.getNumber());
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
			return new KVariant(getNumber() + By.getNumber());
		else
			return new KVariant(toString() + By.toString());
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
		return new KVariant(getNumber() - By.getNumber());
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
		Long value = getLong();
		return new KVariant(value >> by);
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
		Long value = getLong();
		return new KVariant(value << by);
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
		Long value = getLong();
		return new KVariant(value >>> by);
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
			return new KVariant(((String) value).compareTo(by.toType(String.class)) < 0);
		Double value = getNumber();
		return new KVariant(value < by.getNumber());
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
			return new KVariant(((String) value).compareTo(by.toType(String.class)) > 0);
		Double value = getNumber();
		return new KVariant(value > by.getNumber());
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
			return new KVariant(((String) value).compareTo(by.toType(String.class)) <= 0);
		Double value = getNumber();
		return new KVariant(value <= by.getNumber());
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
			return new KVariant(((String) value).compareTo(by.toType(String.class)) >= 0);
		Double value = getNumber();
		return new KVariant(value >= by.getNumber());
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
		Long value = getLong();
		return new KVariant(value & by.getLong());
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
		Long value = getLong();
		return new KVariant(value | by.getInt());
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
		Long value = getLong();
		return new KVariant(value ^ by.getInt());
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
	 * Multiplyby.<br />
	 *
	 * @param By the by<br />
	 * @return return multiplyby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant multiplyby(KVariant By) throws KSException {
		setNumber(getNumber() * By.getNumber());
		return this;
	}

	/**
	 * Modby.<br />
	 *
	 * @param By the by<br />
	 * @return return modby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant modby(KVariant By) throws KSException {
		setNumber(Math.floorMod(getInt(), By.getInt()));
		return this;
	}

	/**
	 * Floor divideby.<br />
	 *
	 * @param By the by<br />
	 * @return return floor divideby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant floorDivideby(KVariant By) throws KSException {
		setNumber(Math.floorDiv(getInt(), By.getInt()));
		return this;
	}

	/**
	 * Divideby.<br />
	 *
	 * @param By the by<br />
	 * @return return divideby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant divideby(KVariant By) throws KSException {
		setNumber(getNumber() / By.getNumber());
		return this;
	}

	/**
	 * Addby.<br />
	 *
	 * @param By the by<br />
	 * @return return addby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant addby(KVariant By) throws KSException {
		if (type.getType() != String.class && By.getType().getType() != String.class) {
			setNumber(getNumber() + By.getNumber());
			return this;
		}
		value = asString() + By.toString();
		return this;
	}

	/**
	 * Minusby.<br />
	 *
	 * @param By the by<br />
	 * @return return minusby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant minusby(KVariant By) throws KSException {
		setNumber(getNumber() - By.getNumber());
		return this;
	}

	/**
	 * RS hby.<br />
	 *
	 * @param by the by<br />
	 * @return return RS hby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant RSHby(long by) throws KSException {
		setNumber(getInt() >> by);
		return this;
	}

	/**
	 * LS hby.<br />
	 *
	 * @param by the by<br />
	 * @return return LS hby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant LSHby(long by) throws KSException {
		setNumber(getInt() << by);
		return this;
	}

	/**
	 * ARS hby.<br />
	 *
	 * @param by the by<br />
	 * @return return ARS hby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant ARSHby(long by) throws KSException {
		setNumber(getInt() >>> by);
		return this;
	}

	/**
	 * BAN dby.<br />
	 *
	 * @param by the by<br />
	 * @return return BAN dby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BANDby(KVariant by) throws KSException {
		setNumber(getInt() & by.getInt());
		return this;
	}

	/**
	 * BO rby.<br />
	 *
	 * @param by the by<br />
	 * @return return BO rby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BORby(KVariant by) throws KSException {
		setNumber(getInt() | by.getInt());
		return this;
	}

	/**
	 * BXO rby.<br />
	 *
	 * @param by the by<br />
	 * @return return BXO rby <br />
	 *         返回 k variant
	 * @throws KSException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BXORby(KVariant by) throws KSException {
		setNumber(getInt() ^ by.getInt());
		return this;
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
			return getNumber().equals(by.getNumber());
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
			return toType(String.class);
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
	public KVariant doOperation(AssignOperation op,KVariant opr) throws KSException {
		switch (op) {
		case ADD:
			this.addby(opr);
			break;
		case ARSH:
			this.ARSHby(opr.getInt());
			break;
		case BAND:
			this.BANDby(opr);
			break;
		case BOR:
			this.BOR(opr);
			break;
		case BXOR:
			this.BXORby(opr);
			break;
		case DIV:
			this.divideby(opr);
			break;
		case EQ:
			this.setValue(opr);
			break;
		case FDIV:
			this.floorDivideby(opr);
			break;
		case LAND:
			this.set(this.asBoolean() && opr.asBoolean());
			break;
		case LOR:
			this.set(this.asBoolean() || opr.asBoolean());
			break;
		case LSH:
			this.LSHby(opr.getInt());
			break;
		case MIN:
			this.minusby(opr);
			break;
		case MOD:
			this.modby(opr);
			break;
		case MUL:
			this.multiplyby(opr);
			break;
		case RSH:
			this.RSHby(opr.getInt());
			break;
		}
		return this;
	}
}
