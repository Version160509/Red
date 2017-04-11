import java.io.Serializable;
public class STUDENT implements Serializable{
	private String id,name;
	private int age;

	    public STUDENT(String id, String name, int age) {
	        this.id = id;
	        this.name = name;
	        this.age = age;
	    }

	    public STUDENT() {
	    }

	    public int getAge() {
	        return age;
	    }

	    public void setAge(int age) {
	        this.age = age;
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getName() {
	        return name;
	    }

	    public void setName(String name) {
	        this.name = name;
	    }

}
