package org.sfm.csv.impl.primitive;

import org.sfm.csv.impl.DelayedCellSetter;
import org.sfm.csv.impl.ParsingContext;
import org.sfm.csv.impl.cellreader.IntegerCellValueReader;
import org.sfm.reflect.primitive.IntSetter;

public class IntDelayedCellSetter<T> implements DelayedCellSetter<T, Integer> {

	private final IntSetter<T> setter;
	private final IntegerCellValueReader reader;
	private int value;
    private boolean isNull;

	public IntDelayedCellSetter(IntSetter<T> setter, IntegerCellValueReader reader) {
		this.setter = setter;
		this.reader = reader;
	}

	@Override
	public Integer consumeValue() {
		return isNull ?  null : consumeInt();
	}

    @Override
    public Integer peekValue() {
        return isNull ? null : value;
    }

    public int consumeInt() {
		int v = value;
		value = 0;
        isNull = true;
		return v;
	}
	
	@Override
	public void set(T t) throws Exception {
		setter.setInt(t, consumeInt());
	}

	@Override
	public boolean isSettable() {
		return setter != null;
	}

	@Override
	public void set(char[] chars, int offset, int length, ParsingContext parsingContext) throws Exception {
        isNull = length == 0;
		this.value = reader.readInt(chars, offset, length, parsingContext);
	}

    @Override
    public String toString() {
        return "IntDelayedCellSetter{" +
                "setter=" + setter +
                ", reader=" + reader +
                '}';
    }
}
