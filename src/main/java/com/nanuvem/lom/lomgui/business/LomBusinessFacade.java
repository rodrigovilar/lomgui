package com.nanuvem.lom.lomgui.business;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.nanuvem.lom.lomgui.resources.Attribute;
import com.nanuvem.lom.lomgui.resources.Clazz;
import com.nanuvem.lom.lomgui.resources.Instance;

public class LomBusinessFacade {

	private static LomBusinessFacade singleton;
	
	private static Long classesCounter = 0l;
	private static Long attributesCounter = 0l;
	private static Long instancesCounter = 0l;

	public static LomBusinessFacade getInstance(){
		if(LomBusinessFacade.singleton == null){
			LomBusinessFacade.singleton = new LomBusinessFacade();
		}
		return LomBusinessFacade.singleton;
	}

	private Map<Long, Clazz> classes;
	private Map<Long, Attribute> attributes;
	private Map<Long, Instance> instances;

	private LomBusinessFacade() {
		this.classes = new HashMap<Long, Clazz>();
		this.attributes = new HashMap<Long, Attribute>();
		this.instances = new HashMap<Long, Instance>();
		mock();
	}

	private void mock() {
		Clazz aClazz = new Clazz();
		aClazz.setName("Widget");
		addClass(aClazz);
		
		Attribute attributeName = new Attribute();
		attributeName.setClassID(aClazz.getId());
		attributeName.setName("nome");
		addAttribute(attributeName);
		
	}

	public Clazz addClass(Clazz clazz){
		clazz.setId(classesCounter++);
		this.classes.put(clazz.getId(), clazz);
		return clazz;
	}

	public Clazz getClass(String fullname){
		for(Clazz clazz : this.classes.values()){
			if(clazz.getFullName().equals(fullname)){
				return clazz;
			}
		}
		return null;
	}

	public Clazz getClass(Long id){
		return this.classes.get(id);
	}

	public Collection<Clazz> getAllClasses(){
		return classes.values();
	}

	public boolean removeClass(String name){
		for(Clazz clazz : this.classes.values()){
			if(clazz.getName().equals(name)){
				return removeClass(clazz.getId());
			}
		}
		return false;
	}

	public boolean removeClass(Long id){
		return this.classes.remove(id) != null;
	}

	public void removeAllClasses(){
		this.classes.clear();
	}
	
	public Attribute addAttribute(Attribute attribute){
		attribute.setId(attributesCounter++);
		this.attributes.put(attribute.getId(), attribute);
		return attribute;
	}
	
	public Attribute getAttribute(Long id){
		return attributes.get(id);
	}
	
	public List<Attribute> getAttributesByClassID(Long classID){
		ArrayList<Attribute> classAttributes = new ArrayList<Attribute>();
		for(Attribute attribute : attributes.values()){
			if(attribute.getClassID() == classID){
				classAttributes.add(attribute);
			}
		}
		return classAttributes;
	}
	
	public boolean removeAttribute(Long id){
		return this.attributes.remove(id) != null;
	}
	
	public Collection<Attribute> getAllAttributes(){
		return attributes.values();
	}
	
	public Instance addInstance(Instance instance){
		instance.setId(instancesCounter++);
		this.instances.put(instance.getId(), instance);
		return instance;
	}
	
	public Instance getInstance(Long id){
		return instances.get(id);
	}
	
	public List<Instance> getInstancesByClassID(Long classID){
		ArrayList<Instance> classInstances = new ArrayList<Instance>();
		for(Instance instance : instances.values()){
			if(instance.getClassID() == classID){
				classInstances.add(instance);
			}
		}
		return classInstances;
	}
	
	public boolean removeInstance(Long id){
		return this.instances.remove(id) != null;
	}
	
	public Collection<Instance> getAllInstances(){
		return instances.values();
	}


}