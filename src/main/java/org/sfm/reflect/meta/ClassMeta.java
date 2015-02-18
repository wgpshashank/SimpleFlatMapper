package org.sfm.reflect.meta;

import org.sfm.reflect.ReflectionService;
import org.sfm.utils.Predicate;

import java.lang.reflect.Type;

public interface ClassMeta<T> {

	public ReflectionService getReflectionService();

	public PropertyFinder<T> newPropertyFinder();

	public Type getType();

	String[] generateHeaders();
}