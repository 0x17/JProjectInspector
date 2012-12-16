package org.andreschnabel.jprojectinspector.model.code;

public class Method {
	
	public Clazz clazz;
	
	public String name;
	public String body;
	
	public Clazz[] referencedClasses;
	public Method[] referencedMethods;
	
}
