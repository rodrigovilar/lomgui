package com.nanuvem.lom.lomgui.business;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.nanuvem.lom.lomgui.resources.Clazz;

public class LomBusinessFacade {

	private static LomBusinessFacade singleton;

	public static LomBusinessFacade getInstance(){
		if(LomBusinessFacade.singleton == null){
			LomBusinessFacade.singleton = new LomBusinessFacade();
		}
		return LomBusinessFacade.singleton;
	}

	private Map<Long, Clazz> classes;

	private LomBusinessFacade() {
		this.classes = new HashMap<Long, Clazz>();
	}

	public void addClass(Clazz clazz){
		this.classes.put(clazz.getId(), clazz);
	}

	public Clazz getClass(String name){
		for(Clazz clazz : this.classes.values()){
			if(clazz.getName().equals(name)){
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


}