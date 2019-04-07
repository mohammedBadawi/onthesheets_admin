package hospital.code.com.foesheets.Models;

public class Sheet {
    private String id;
    private String name;
    private String created_at;
    private String id_teacher;
    private String lesson;
    private String drive;
    private String id_subject;

    public Sheet(String id, String name, String created_at, String id_teacher,String lesson,String drive,String id_subject) {
        this.id = id;
        this.name = name;
        this.created_at = created_at;
        this.id_teacher = id_teacher;
        this.lesson = lesson;
        this.drive = drive;
        this.id_subject = id_subject;
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

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setId_teacher(String id_teacher) {
        this.id_teacher = id_teacher;
    }

    public String getId_teacher() {
        return id_teacher;
    }

    public void setLesson(String lesson) {
        this.lesson = lesson;
    }

    public String getLesson() {
        return lesson;
    }

    public void setDrive(String drive) {
        this.drive = drive;
    }

    public String getDrive() {
        return drive;
    }

    public void setId_subject(String id_subject) {
        this.id_subject = id_subject;
    }

    public String getId_subject() {
        return id_subject;
    }
}
