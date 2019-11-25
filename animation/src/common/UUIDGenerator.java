package common;

public class UUIDGenerator {
	public static class ID {
		public final int id;
		public final String name;

		public ID(int i, String n) {
			id = i;
			name = n;
		}
	}

	private int id;
	public final String reservedPrefix;
	
	public UUIDGenerator(String nameReservedPrefix) {
		id = 0;
		reservedPrefix = nameReservedPrefix;
	}
	
	public ID generateNew() {
		// Increment The ID To Make IDs Unique
		id++;
		
		// Send ID
		return new ID(id, reservedPrefix + id);
	}
	public ID generateNew(String name) {
		// Increment The ID To Make IDs Unique
		id++;
		
		// Send ID
		return new ID(id, name);
	}
}
