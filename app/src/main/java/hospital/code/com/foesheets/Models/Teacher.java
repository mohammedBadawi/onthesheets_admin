package hospital.code.com.foesheets.Models;

public class Teacher {
    private String id;
    private String name;
    private String id_subject;

    public Teacher(String id,String name,String id_subject){
        this.id=id;
        this.name=name;
        this.id_subject=id_subject;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId_subject(String id_subject) {
        this.id_subject = id_subject;
    }

    public String getId_subject() {
        return id_subject;
    }
}
