package com.khjxiaogu.scriptengine.core;

import com.khjxiaogu.scriptengine.core.Object.KObject;
import com.khjxiaogu.scriptengine.core.syntax.operator.Associative;
import com.khjxiaogu.scriptengine.core.typeconvert.Converter;
import com.khjxiaogu.scriptengine.core.typeconvert.ConvertionException;
import com.khjxiaogu.scriptengine.core.typeconvert.ConvertionManager;
import com.khjxiaogu.scriptengine.core.typeconvert.TypeInfo;

/**
 * @author khjxiaogu
 * @time 2019年8月23日 file:KVariant.java
 */
public class KVariant implements Cloneable {
	private TypeInfo type;
	private Object value;
	public static KVariant FALSE = new KVariant(false);
	public static KVariant TRUE = new KVariant(true);

	public KVariant() {
		type = TypeInfo.forName("void");
		value = null;
	}

	public KVariant(KVariant ref) {
		type = ref.type;
		value = ref.value;
	}

	public KVariant(KObject val) {
		type = TypeInfo.forName("Object");
		value = val;
	}

	public KVariant(Double val) {
		try {
			setNumber(val);
		} catch (ConvertionException e) {
			type = TypeInfo.forName("Integer");
			value = 0;
		}
	}

	public KVariant(double val) {
		try {
			setNumber(val);
		} catch (ConvertionException e) {
			type = TypeInfo.forName("Integer");
			value = 0;
		}
	}

	public KVariant(Integer val) {
		type = TypeInfo.forName("Integer");
		value = val;
	}

	public KVariant(int val) {
		type = TypeInfo.forName("Integer");
		value = Integer.valueOf(val);
	}

	public KVariant(String val) {
		type = TypeInfo.forName("String");
		value = val;
	}

	public KVariant(boolean val) {
		type = TypeInfo.forName("Integer");
		value = val ? 1 : 0;
	}

	public KVariant(Object val) {
		type = TypeInfo.forType(val.getClass());
		value = val;
	}

	public KVariant(Object val, TypeInfo type) {
		this.type = type;
		value = val;
	}

	public KVariant(Object val, String type) {
		this.type = TypeInfo.forName(type);
		value = val;
	}

	public TypeInfo getType() {
		return type;
	}

	public Object getValue() {
		return value;
	}

	public KVariant setValue(KVariant ref) {
		type = ref.type;
		value = ref.value;
		return this;
	}

	public KVariant set(Object ref) {
		type = TypeInfo.forType(ref.getClass());
		value = ref;
		return this;
	}

	public TypeInfo asNumber() throws ConvertionException {
		if (value instanceof Integer || value instanceof Double)
			return type;
		try {
			value = ConvertionManager.getConvertion("Real").from(this);
		} catch (ConvertionException e) {
			e.setType(type.getName(), "Number");
			throw e;
		}
		double realV = (Double) value;
		if (realV == (int) realV) {
			value = Integer.valueOf((int) realV);
			return type = TypeInfo.forName("Integer");
		}
		return type = TypeInfo.forName("Real");
	}

	public Double getNumber() throws ConvertionException {
		try {
			if (!(value instanceof Double))
				return (Double) ConvertionManager.getConvertion("Real").from(this);
			return (Double) value;
		} catch (ConvertionException e) {
			e.setType(type.getName(), "Number");
			throw e;
		}
	}

	public Integer getInt() throws ConvertionException {
		if (!(value instanceof Integer))
			return (Integer) ConvertionManager.getConvertion("Integer").from(this);
		return (Integer) value;
	}

	public void setNumber(Double val) throws ConvertionException {
		value = val;
		double realV = (Double) value;
		if (realV == (int) realV) {
			value = Integer.valueOf((int) realV);
			type = TypeInfo.forName("Integer");
		} else {
			type = TypeInfo.forName("Real");
		}
	}

	public void setNumber(int val) {
		value = val;
		type = TypeInfo.forName("Integer");
	}

	/**
	 * get the variant to specific type,changes the original variant
	 *
	 * @param name
	 * @return
	 * @throws ConvertionException
	 */
	@SuppressWarnings("unchecked")
	public <T> T asType(Class<T> toType) throws ConvertionException {
		if (toType.isInstance(value))
			return (T) value;
		Converter conv = ConvertionManager.getConvertion(toType);
		type = conv.getOutTypeInfo();
		return (T) (value = conv.from(this));
	}

	/**
	 * get the variant to specific type
	 *
	 * @param name
	 * @return
	 * @throws ConvertionException
	 */
	@SuppressWarnings("unchecked")
	public <T> T toType(Class<T> toType) throws ConvertionException {
		if (toType.isInstance(value))
			return (T) value;
		return (T) ConvertionManager.getConvertion(toType).from(this);
	}

	/**
	 * get the variant to specific type
	 *
	 * @param name
	 * @return
	 * @throws ConvertionException
	 */
	public Object toType(String name) throws ConvertionException {
		if (type.getName().equals(name))
			return value;
		return ConvertionManager.getConvertion(name).from(this);
	}

	/**
	 * get the variant to specific type,changes the original variant
	 *
	 * @param name
	 * @return
	 * @throws ConvertionException
	 */
	public Object asType(String name) throws ConvertionException {
		if (type.getName().equals(name))
			return value;
		Converter conv = ConvertionManager.getConvertion(name);
		type = conv.getOutTypeInfo();
		return value = conv.from(this);
	}

	public KVariant selfIncrement(Associative dir) throws ConvertionException {
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

	public KVariant selfDecrement(Associative dir) throws ConvertionException {
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

	public KVariant multiply(KVariant By) throws ConvertionException {
		return new KVariant(getNumber() * By.getNumber());

	}

	public KVariant mod(KVariant By) throws ConvertionException {
		return new KVariant(Math.floorMod(getInt(), By.getInt()));
	}

	public KVariant floorDivide(KVariant By) throws ConvertionException {
		return new KVariant(Math.floorDiv(getInt(), By.getInt()));
	}

	public KVariant divide(KVariant By) throws ConvertionException {
		return new KVariant(getNumber() / By.getNumber());
	}

	public KVariant add(KVariant By) throws ConvertionException {
		if (type.getType() != String.class && By.getType().getType() != String.class)
			return new KVariant(getNumber() + By.getNumber());
		else
			return new KVariant(toString() + By.toString());
	}

	public KVariant minus(KVariant By) throws ConvertionException {
		return new KVariant(getNumber() - By.getNumber());
	}

	public KVariant RSH(int by) throws ConvertionException {
		Integer value = getInt();
		return new KVariant(value >> by);
	}

	public KVariant LSH(int by) throws ConvertionException {
		Integer value = getInt();
		return new KVariant(value << by);
	}

	public KVariant ARSH(int by) throws ConvertionException {
		Integer value = getInt();
		return new KVariant(value >>> by);
	}

	public KVariant LT(KVariant by) throws ConvertionException {
		if (type.getType() == String.class && by.type.getType() == String.class)
			return new KVariant(((String) value).compareTo((String) by.asType("String")) < 0);
		Double value = getNumber();
		return new KVariant(value < by.getNumber());
	}

	public KVariant GT(KVariant by) throws ConvertionException {
		if (type.getType() == String.class && by.type.getType() == String.class)
			return new KVariant(((String) value).compareTo((String) by.asType("String")) > 0);
		Double value = getNumber();
		return new KVariant(value > by.getNumber());
	}

	public KVariant LOET(KVariant by) throws ConvertionException {
		if (type.getType() == String.class && by.type.getType() == String.class)
			return new KVariant(((String) value).compareTo((String) by.asType("String")) <= 0);
		Double value = getNumber();
		return new KVariant(value <= by.getNumber());
	}

	public KVariant GOET(KVariant by) throws ConvertionException {
		if (type.getType() == String.class && by.type.getType() == String.class)
			return new KVariant(((String) value).compareTo((String) by.asType("String")) >= 0);
		Double value = getNumber();
		return new KVariant(value >= by.getNumber());
	}

	public boolean ExactEquals(KVariant another) {
		if (another == this)
			return true;
		if (type.getName() != another.type.getName())
			return false;
		return value.equals(another.value);
	}

	public KVariant BAND(KVariant by) throws ConvertionException {
		Integer value = getInt();
		return new KVariant(value & by.getInt());
	}

	public KVariant BOR(KVariant by) throws ConvertionException {
		Integer value = getInt();
		return new KVariant(value | by.getInt());
	}

	public KVariant BXOR(KVariant by) throws ConvertionException {
		Integer value = getInt();
		return new KVariant(value ^ by.getInt());
	}

	public boolean asBoolean() {
		if (value.equals(0))
			return false;
		return value != null;
	}

	public KVariant multiplyby(KVariant By) throws ConvertionException {
		setNumber(getNumber() * By.getNumber());
		return this;
	}

	public KVariant modby(KVariant By) throws ConvertionException {
		setNumber(Math.floorMod(getInt(), By.getInt()));
		return this;
	}

	public KVariant floorDivideby(KVariant By) throws ConvertionException {
		setNumber(Math.floorDiv(getInt(), By.getInt()));
		return this;
	}

	public KVariant divideby(KVariant By) throws ConvertionException {
		setNumber(getNumber() / By.getNumber());
		return this;
	}

	public KVariant addby(KVariant By) throws ConvertionException {
		if (type.getType() != String.class && By.getType().getType() != String.class) {
			setNumber(getNumber() + By.getNumber());
			return this;
		} else {
			value = asString() + By.toString();
			return this;
		}
	}

	public KVariant minusby(KVariant By) throws ConvertionException {
		setNumber(getNumber() - By.getNumber());
		return this;
	}

	public KVariant RSHby(int by) throws ConvertionException {
		setNumber(getInt() >> by);
		return this;
	}

	public KVariant LSHby(int by) throws ConvertionException {
		setNumber(getInt() << by);
		return this;
	}

	public KVariant ARSHby(int by) throws ConvertionException {
		setNumber(getInt() >>> by);
		return this;
	}

	public KVariant BANDby(KVariant by) throws ConvertionException {
		setNumber(getInt() & by.getInt());
		return this;
	}

	public KVariant BORby(KVariant by) throws ConvertionException {
		setNumber(getInt() | by.getInt());
		return this;
	}

	public KVariant BXORby(KVariant by) throws ConvertionException {
		setNumber(getInt() ^ by.getInt());
		return this;
	}

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
		} catch (ConvertionException e) {
			return false;
		}
	}

	@Override
	public int hashCode() {
		if (value != null)
			return value.hashCode();
		else
			return 0;
	}

	@Override
	public String toString() {
		try {
			return (String) toType("String");
		} catch (ConvertionException e) {
			return "(" + type.getName() + ")" + hashCode();
		}
	}

	public String asString() throws ConvertionException {
		return (String) asType("String");
	}

	public boolean isNull() {
		if (value == null || value instanceof String && ((String) value).length() == 0)
			return true;
		return false;
	}
}
