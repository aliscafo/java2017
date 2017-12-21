package testData;

public abstract class SimpleClass<T,V> {
	private T[] firstField;
	private V secondField;
	public SimpleClass(T arg0, String arg1) {}
	public SimpleClass(V arg0, Integer arg1) {}
	
	public void simpleMethod() {
	}
	
	private T returnGeneric(V arg0, int arg1) {
		return null;
	}

}