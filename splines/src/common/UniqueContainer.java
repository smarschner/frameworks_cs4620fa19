package common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class UniqueContainer<T extends ACUniqueObject> implements Iterable<T> {
	public static interface IAllocator<T> {
		T generate();
	}
	
	IAllocator<T> allocator;
	UUIDGenerator uuidGen;
	
	HashMap<Integer, T> objMapID;
	HashMap<String, T> objMapName;
	
	public UniqueContainer(IAllocator<T> alloc, String namePrefix) {	
		allocator = alloc;
		uuidGen = new UUIDGenerator(namePrefix);
		
		objMapID = new HashMap<>();
		objMapName = new HashMap<>();
	}
	
	public T addNew() {
		T o = allocator.generate();
		o.setID(uuidGen.generateNew());
		objMapName.put(o.getID().name, o);
		objMapID.put(o.getID().id, o);
		return o;
	}
	public T addNew(String name) {
		if(!objMapName.containsKey(name)) {
			T o = allocator.generate();
			o.setID(uuidGen.generateNew(name));
			objMapName.put(o.getID().name, o);
			objMapID.put(o.getID().id, o);
			return o;
		}
		return null;
	}
	public void add(T o) {
		o.setID(uuidGen.generateNew());
		objMapName.put(o.getID().name, o);
		objMapID.put(o.getID().id, o);
	}
	public void remove(T o) {
		if(o == null) return;
		
		objMapID.remove(o.getID().id);
		objMapName.remove(o.getID().name);
	}
	public void remove(String name) {
		remove(get(name));
	}
	public void remove(int id) {
		remove(get(id));
	}

	public void setName(T o, String name) {
		String oldName = o.getID().name;
		int oldID = o.getID().id;
		T o2 = get(oldName);
		if(o2 == o) {
			o2.setID(uuidGen.generateNew(name));
			objMapName.remove(oldName);
			objMapName.put(name, o);
			objMapID.remove(oldID);
			objMapID.put(o2.getID().id, o);
		}
	}
	public void setName(String old, String name) {
		T o = get(old);
		if(old != null) setName(o, name);
	}
	
	public T get(String name) {
		return objMapName.get(name);
	}
	public T get(int id) {
		return objMapID.get(id);
	}

	public UUIDGenerator.ID getID(String name) {
		return uuidGen.generateNew(name);
	}

	public int size() {
		return objMapName.size();
	}
	
	public Set<Integer> getIDs() {
		return objMapID.keySet();
	}
	public Set<String> getNames() {
		return objMapName.keySet();
	}
	@Override
	public Iterator<T> iterator() {
		return objMapName.values().iterator();
	}
	
	@Override
	public String toString() {
		return objMapName.toString();
	}

	
}
