/*
 * file: KVariant.java
 * @author khjxiaogu
 * time: 2020年3月29日
 */
package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.typeconvert.Converter;
import com.khjxiaogu.scriptengine.core.typeconvert.ConversionException;
import com.khjxiaogu.scriptengine.core.typeconvert.ConversionManager;
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
	 * 成员 type.
	 */
	private TypeInfo type;
	
	/**
	 * The value.<br />
	 * 成员 value.
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
	 * Instantiates a new k variant.<br />
	 * 新建一个k variant类<br />
	 */
	public KVariant() {
		type = TypeInfo.forTypeConstant(Void.class);
		value = null;
	}
	
	/**
	 * Instantiates a new k variant with a k variant class.<br />
	 * 使用一个k variant新建一个k variant类<br />
	 *
	 * @param ref the ref<br />
	 */
	public KVariant(KVariant ref) {
		type = ref.type;
		value = ref.value;
	}
	
	/**
	 * Instantiates a new k variant with a k object class.<br />
	 * 使用一个k object新建一个k variant类<br />
	 *
	 * @param val the val<br />
	 */
	public KVariant(KObject val) {
		type = TypeInfo.forTypeConstant(KObject.class);
		value = val;
	}

	/**
	 * Instantiates a new k variant with a double class.<br />
	 * 使用一个double新建一个k variant类<br />
	 *
	 * @param val the val<br />
	 */
	public KVariant(Double val) {
		try {
			setNumber(val);
		} catch (ConversionException e) {
			type = TypeInfo.forTypeConstant(Integer.class);
			value = 0;
		}
	}
	/**
	 * Instantiates a new k variant with a double class.<br />
	 * 使用一个double新建一个k variant类<br />
	 *
	 * @param val the val<br />
	 */
	public KVariant(double val) {
		try {
			setNumber(val);
		} catch (ConversionException e) {
			type = TypeInfo.forTypeConstant(Integer.class);
			value = 0;
		}
	}
	/**
	 * Instantiates a new k variant with a integer class.<br />
	 * 使用一个integer新建一个k variant类<br />
	 *
	 * @param val the val<br />
	 */
	public KVariant(Integer val) {
		type = TypeInfo.forTypeConstant(Integer.class);
		value = val;
	}

	/**
	 * Instantiates a new k variant with a int class.<br />
	 * 使用一个int新建一个k variant类<br />
	 *
	 * @param val the val<br />
	 */
	public KVariant(int val) {
		type = TypeInfo.forTypeConstant(Integer.class);
		value = Integer.valueOf(val);
	}

	/**
	 * Instantiates a new k variant with a string class.<br />
	 * 使用一个string新建一个k variant类<br />
	 *
	 * @param val the val<br />
	 */
	public KVariant(String val) {
		type = TypeInfo.forTypeConstant(String.class);
		value = val;
	}

	/**
	 * Instantiates a new k variant with a boolean class.<br />
	 * 使用一个boolean新建一个k variant类<br />
	 *
	 * @param val the val<br />
	 */
	public KVariant(boolean val) {
		type = TypeInfo.forTypeConstant(Integer.class);
		value = val ? 1 : 0;
	}

	/**
	 * Instantiates a new k variant with a object class.<br />
	 * 使用一个object新建一个k variant类<br />
	 *
	 * @param val the val<br />
	 */
	public KVariant(Object val) {
		type = TypeInfo.forTypeConstant(val.getClass());
		value = val;
	}
	/**
	 * Instantiates a new k variant.<br />
	 * 新建一个k variant类<br />
	 *
	 * @param val  the val<br />
	 * @param type the type<br />
	 */
	public KVariant(Object val, TypeInfo type) {
		this.type = type;
		value = val;
	}

	/**
	 * Instantiates a new k variant.<br />
	 * 新建一个k variant类<br />
	 *
	 * @param val  the val<br />
	 * @param type the type<br />
	 */
	public KVariant(Object val, String type) {
		this.type = TypeInfo.forTypeConstant(Integer.class);
		value = val;
	}

	/**
	 * Gets the type.<br />
	 * 获取 type.
	 *
	 * @return type<br />
	 */
	public TypeInfo getType() {
		return type;
	}

	/**
	 * Gets the value.<br />
	 * 获取 value.
	 *
	 * @return value<br />
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * Set value.<br />
	 *
	 * @param ref the ref<br />
	 * @return return set value <br />
	 *         返回 k variant
	 */
	public KVariant setValue(KVariant ref) {
		type = ref.type;
		value = ref.value;
		return this;
	}

	/**
	 * Set.<br />
	 *
	 * @param ref the ref<br />
	 * @return return set <br />
	 *         返回 k variant
	 */
	public KVariant set(Object ref) {
		type = TypeInfo.forType(ref.getClass());
		value = ref;
		return this;
	}

	/**
	 * As number.<br />
	 * 转换为数字
	 * @return return as number <br />
	 *         返回类型
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public TypeInfo asNumber() throws ConversionException {
		if (value instanceof Integer || value instanceof Double)
			return type;
		try {
			value = ConversionManager.getConversion(Double.class).from(this);
		} catch (ConversionException e) {
			e.setType(type.getName(), "Number");
			throw e;
		}
		double realV = (Double) value;
		if (realV == (int) realV) {
			value = Integer.valueOf((int) realV);
			return type = TypeInfo.forTypeConstant(Integer.class);
		}
		return type = TypeInfo.forTypeConstant(Double.class);
	}

	/**
	 * Gets the number.<br />
	 * 获取 number.
	 *
	 * @return number<br />
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public Double getNumber() throws ConversionException {
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
	 * Gets the int.<br />
	 * 获取 int.
	 *
	 * @return int<br />
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public Integer getInt() throws ConversionException {
		if (!(value instanceof Integer))
			return (Integer) ConversionManager.getConversion(Integer.class).from(this);
		return (Integer) value;
	}

	/**
	 * set number.<br />
	 * 设置 number.
	 *
	 * @param val 设置为 number<br />
	 *            set to number.
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public void setNumber(Double val) throws ConversionException {
		value = val;
		double realV = (Double) value;
		if (realV == (int) realV) {
			value = Integer.valueOf((int) realV);
			type = TypeInfo.forTypeConstant(Integer.class);
		} else {
			type = TypeInfo.forTypeConstant(Double.class);
		}
	}

	/**
	 * set number.<br />
	 * 设置 number.
	 *
	 * @param val 设置为 number<br />
	 *            set to number.
	 */
	public void setNumber(int val) {
		value = val;
		type = TypeInfo.forTypeConstant(Integer.class);
	}

	/**
	 * get the variant to specific type,changes the original variant.
	 *
	 * @param <T>    the generic type<br />
	 *               泛型参数
	 * @param toType the to type<br />
	 * @return return as type <br />
	 *         返回 t
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	@SuppressWarnings("unchecked")
	public <T> T asType(Class<T> toType) throws ConversionException {
		if (toType.isInstance(value))
			return (T) value;
		Converter conv = ConversionManager.getConversion(toType);
		type = conv.getOutTypeInfo();
		return (T) (value = conv.from(this));
	}

	/**
	 * get the variant to specific type.
	 *
	 * @param <T>    the generic type<br />
	 *               泛型参数
	 * @param toType the to type<br />
	 * @return return to type <br />
	 *         返回 t
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	@SuppressWarnings("unchecked")
	public <T> T toType(Class<T> toType) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public Object toType(String name) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public Object asType(String name) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant selfIncrement(Associative dir) throws ConversionException {
		asNumber();
		if (dir == Associative.LEFT) {
			if (value instanceof Integer) {
				value = Integer.valueOf((Integer) value + 1);
			} else if (value instanceof Double) {
				value = Double.valueOf((Double) value + 1);
			}
			return new KVariant(this);
		}
		KVariant ret = new KVariant(this);
		if (value instanceof Integer) {
			value = Integer.valueOf((Integer) value + 1);
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant selfDecrement(Associative dir) throws ConversionException {
		asNumber();
		if (dir == Associative.LEFT) {
			if (value instanceof Integer) {
				value = Integer.valueOf((Integer) value - 1);
			} else if (value instanceof Double) {
				value = Double.valueOf((Double) value - 1);
			}
			return new KVariant(this);
		}
		KVariant ret = new KVariant(this);
		if (value instanceof Integer) {
			value = Integer.valueOf((Integer) value - 1);
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant multiply(KVariant By) throws ConversionException {
		return new KVariant(getNumber() * By.getNumber());

	}

	/**
	 * Mod.<br />
	 *
	 * @param By the by<br />
	 * @return return mod <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant mod(KVariant By) throws ConversionException {
		return new KVariant(Math.floorMod(getInt(), By.getInt()));
	}

	/**
	 * Floor divide.<br />
	 *
	 * @param By the by<br />
	 * @return return floor divide <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant floorDivide(KVariant By) throws ConversionException {
		return new KVariant(Math.floorDiv(getInt(), By.getInt()));
	}

	/**
	 * Divide.<br />
	 *
	 * @param By the by<br />
	 * @return return divide <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant divide(KVariant By) throws ConversionException {
		return new KVariant(getNumber() / By.getNumber());
	}

	/**
	 * Add.<br />
	 *
	 * @param By the by<br />
	 * @return return add <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant add(KVariant By) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant minus(KVariant By) throws ConversionException {
		return new KVariant(getNumber() - By.getNumber());
	}

	/**
	 * Rsh.<br />
	 *
	 * @param by the by<br />
	 * @return return rsh <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant RSH(int by) throws ConversionException {
		Integer value = getInt();
		return new KVariant(value >> by);
	}

	/**
	 * Lsh.<br />
	 *
	 * @param by the by<br />
	 * @return return lsh <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant LSH(int by) throws ConversionException {
		Integer value = getInt();
		return new KVariant(value << by);
	}

	/**
	 * Arsh.<br />
	 *
	 * @param by the by<br />
	 * @return return arsh <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant ARSH(int by) throws ConversionException {
		Integer value = getInt();
		return new KVariant(value >>> by);
	}

	/**
	 * Lt.<br />
	 *
	 * @param by the by<br />
	 * @return return lt <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant LT(KVariant by) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant GT(KVariant by) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant LOET(KVariant by) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant GOET(KVariant by) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BAND(KVariant by) throws ConversionException {
		Integer value = getInt();
		return new KVariant(value & by.getInt());
	}

	/**
	 * Bor.<br />
	 *
	 * @param by the by<br />
	 * @return return bor <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BOR(KVariant by) throws ConversionException {
		Integer value = getInt();
		return new KVariant(value | by.getInt());
	}

	/**
	 * Bxor.<br />
	 *
	 * @param by the by<br />
	 * @return return bxor <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BXOR(KVariant by) throws ConversionException {
		Integer value = getInt();
		return new KVariant(value ^ by.getInt());
	}

	/**
	 * As boolean.<br />
	 *
	 * @return true, if <br />
	 *         如果，返回true。
	 */
	public boolean asBoolean() {
		if (value.equals(0))
			return false;
		return value != null;
	}

	/**
	 * Multiplyby.<br />
	 *
	 * @param By the by<br />
	 * @return return multiplyby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant multiplyby(KVariant By) throws ConversionException {
		setNumber(getNumber() * By.getNumber());
		return this;
	}

	/**
	 * Modby.<br />
	 *
	 * @param By the by<br />
	 * @return return modby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant modby(KVariant By) throws ConversionException {
		setNumber(Math.floorMod(getInt(), By.getInt()));
		return this;
	}

	/**
	 * Floor divideby.<br />
	 *
	 * @param By the by<br />
	 * @return return floor divideby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant floorDivideby(KVariant By) throws ConversionException {
		setNumber(Math.floorDiv(getInt(), By.getInt()));
		return this;
	}

	/**
	 * Divideby.<br />
	 *
	 * @param By the by<br />
	 * @return return divideby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant divideby(KVariant By) throws ConversionException {
		setNumber(getNumber() / By.getNumber());
		return this;
	}

	/**
	 * Addby.<br />
	 *
	 * @param By the by<br />
	 * @return return addby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant addby(KVariant By) throws ConversionException {
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
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant minusby(KVariant By) throws ConversionException {
		setNumber(getNumber() - By.getNumber());
		return this;
	}

	/**
	 * RS hby.<br />
	 *
	 * @param by the by<br />
	 * @return return RS hby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant RSHby(int by) throws ConversionException {
		setNumber(getInt() >> by);
		return this;
	}

	/**
	 * LS hby.<br />
	 *
	 * @param by the by<br />
	 * @return return LS hby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant LSHby(int by) throws ConversionException {
		setNumber(getInt() << by);
		return this;
	}

	/**
	 * ARS hby.<br />
	 *
	 * @param by the by<br />
	 * @return return ARS hby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant ARSHby(int by) throws ConversionException {
		setNumber(getInt() >>> by);
		return this;
	}

	/**
	 * BAN dby.<br />
	 *
	 * @param by the by<br />
	 * @return return BAN dby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BANDby(KVariant by) throws ConversionException {
		setNumber(getInt() & by.getInt());
		return this;
	}

	/**
	 * BO rby.<br />
	 *
	 * @param by the by<br />
	 * @return return BO rby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BORby(KVariant by) throws ConversionException {
		setNumber(getInt() | by.getInt());
		return this;
	}

	/**
	 * BXO rby.<br />
	 *
	 * @param by the by<br />
	 * @return return BXO rby <br />
	 *         返回 k variant
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public KVariant BXORby(KVariant by) throws ConversionException {
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
		} catch (ConversionException e) {
			return false;
		}
	}

	/**
	 * Hash code.<br />
	 *
	 * @return return hash code <br />
	 *         返回 int
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
		} catch (ConversionException e) {
			return "(" + type.getName() + ")" + hashCode();
		}
	}

	/**
	 * As string.<br />
	 *
	 * @return return as string <br />
	 *         返回 string
	 * @throws ConversionException if an convertion exception occured.<br />
	 *                             如果convertion exception发生了
	 */
	public String asString() throws ConversionException {
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
}
