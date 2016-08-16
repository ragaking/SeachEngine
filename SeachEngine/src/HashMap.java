public class HashMap {

	private int size;
	private int capacity;
	private Node[] table;

	public HashMap(int capacity) {

		// this.capacity = capacity < 50 ? 50 : capacity;
		this.capacity = capacity;
		table = new Node[this.capacity];
		size = 0;
	}

	public void add(String key) {

		int hash = hashCode(key);
		if (isContain(key)) {

			Node temp = table[hash];
			while (temp != null) {

				HashObject object = (HashObject) temp.data;
				if (object.key.equals(key)) {
					object.frequency++;
				}
				// move to the next
				temp = temp.link;
			}

		} else {
			size++;

			HashObject data = new HashObject(key);
			Node temp = new Node(data);
			temp.link = table[hash];
			table[hash] = temp;
		}

		// Check load factor
		if (loadFactor()) {
			resizeTemp();
		}
	}

	public HashObject getObject(String key) {

		int hash = hashCode(key);

		// if there is some hashed variables
		if (table[hash] != null) {

			Node temp = table[hash];
			while (temp != null) {
				// Check
				HashObject object = (HashObject) temp.data;
				if (object.key.equals(key)) {
					return object;
				}
				//
				temp = temp.link;
			}
		}

		return null;
	}

	public void delete(String key) {

		int hash = hashCode(key);

		// if there is some hashed variables
		if (table[hash] != null) {

			Node temp = table[hash];
			if (((HashObject) temp.data).key.equals(key)) {

				table[hash] = temp.link;
			} else {

				while (temp != null && temp.link != null) {
					// Check
					HashObject object = (HashObject) temp.link.data;
					if (object.key.equals(key)) {
						temp.link = temp.link.link;
					}
					//
					temp = temp.link;
				}
			}
		}

	}

	public boolean isContain(String key) {

		int hash = hashCode(key);

		// if there is some hashed variables
		if (table[hash] != null) {

			Node temp = table[hash];
			while (temp != null) {
				// Check
				HashObject object = (HashObject) temp.data;
				if (object.key.equals(key)) {
					return true;
				}
				//
				temp = temp.link;
			}
		}

		return false;
	}

	public int hashCode(String key) {

		int code = 0;

		for (int i = 0; i < key.length(); i++) {
			code += key.charAt(i);
		}

		return code % capacity;
	}

	public boolean loadFactor() {

		float f = size / capacity;

		if (1.0 < f) {
			return true;
		}
		return false;
	}

	public void resize() {

		Node[] tempTable = new Node[this.capacity * 2];
		this.capacity *= 2;

		for (int i = 0; i < table.length; i++) {
			Node temp = table[i];
			while (temp != null) {

				HashObject object = (HashObject) temp.data;
				int hash = hashCode(object.key);

				Node newNode = new Node(object);

				newNode.link = tempTable[hash];
				tempTable[hash] = newNode;
				// get next
				temp = temp.link;
			}
		}

		table = tempTable;
	}

	public void resizeTemp() {

		System.out.println("---Before---");
		print();

		int newSize = getNextPrime(this.capacity * 2);

		HashMap newMap = new HashMap(newSize);
		for (int i = 0; i < capacity; i++) {

			Node temp = table[i];
			while (temp != null) {

				HashObject object = (HashObject) temp.data;
				for (int j = 0; j < object.frequency; j++) {
					newMap.add(object.key);
				}
				// get next
				temp = temp.link;
			}
		}

		this.table = newMap.table;
		this.capacity = newMap.capacity;
		this.size = newMap.size;

		System.out.println("---After---");
		print();
	}

	public int getCollision() {

		int collision = 0;

		for (int i = 0; i < table.length; i++) {

			Node temp = table[i];
			while (temp != null && temp.link != null) {

				collision++;

				temp = temp.link;
			}
		}

		return collision;
	}

	public void print() {

		System.out.println("Size: " + size + " Capacity: " + capacity
				+ " Collision: " + getCollision());
		for (int i = 0; i < capacity; i++) {

			System.out.print(i + " ");

			Node temp = table[i];
			while (temp != null) {

				HashObject object = (HashObject) temp.data;
				System.out.print(" [Key:" + object.key + " Hash: "
						+ hashCode(object.key) + " Frequency: "
						+ object.frequency + "]");

				temp = temp.link;
			}
			System.out.println();
		}
	}

	public int getNextPrime(int number) {

		if (number < 2) {
			return 2;
		}

		do {

			boolean flag = true;

			for (int i = 2; i < number; i++) {

				if (number % i == 0) {
					flag = false;
					break;
				}
			}

			if (flag) {
				break;
			}

			number++;

		} while (true);

		return number;
	}

}
