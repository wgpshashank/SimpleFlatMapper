package org.sfm.map;

public class MapperBuildingException extends MappingException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7035826084211560802L;


	public MapperBuildingException(String message) {
		super(message);
	}
	
	public MapperBuildingException(String message, Throwable t) {
		super(message, t);
	}
}
