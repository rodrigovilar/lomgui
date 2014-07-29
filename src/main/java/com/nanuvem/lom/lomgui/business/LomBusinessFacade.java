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

	public void addClazz(Clazz clazz){
		this.classes.put(clazz.getId(), clazz);
	}

	public Clazz getClazz(String name){
		for(Clazz clazz : this.classes.values()){
			if(clazz.getName().equals(name)){
				return clazz;
			}
		}
		return null;
	}

	public Clazz getClazz(Long id){
		return this.classes.get(id);
	}

	public Collection<Clazz> getAllClazz(){
		return classes.values();
	}

	public boolean removeClazz(String name){
		for(Clazz clazz : this.classes.values()){
			if(clazz.getName().equals(name)){
				return removeClazz(clazz.getId());
			}
		}
		return false;
	}

	public boolean removeClazz(Long id){
		return this.classes.remove(id) != null;
	}

	public void removeAllClazz(){
		this.classes.clear();
	}


}