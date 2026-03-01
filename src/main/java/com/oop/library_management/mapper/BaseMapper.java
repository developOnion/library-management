package com.oop.library_management.mapper;

public abstract class BaseMapper<E, D> {

	public abstract D toDTO(E entity);
}
