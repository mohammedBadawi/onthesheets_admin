package hospital.code.com.foesheets.Models;

public class Subjects {
    private String id;
    private String name;
    private String logo;

    public Subjects(String id,String name,String logo){
        this.id=id;
        this.name=name;
        this.logo=logo;
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

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLogo() {
        return logo;
    }
}
