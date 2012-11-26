package org.andreschnabel.jprojectinspector;

import com.google.gson.Gson;

public class JsonTesting {
	class Person {
		public String name;
		public int age;
		public float height;

		public Person(String name, int age, float height) {
			super();
			this.name = name;
			this.age = age;
			this.height = height;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + ", height="
					+ height + "]";
		}
	}

	public JsonTesting() {
	}

	public void runTests() {
		Gson gson = new Gson();
		System.out.println(gson.toJson(new Person("Peter", 23, 1.85f)));

		Person hans = gson.fromJson(
				"{\"name\":\"Hans\",\"age\":53,\"height\":2.05}", Person.class);
		System.out.println("Hans = " + hans);
	}
}
