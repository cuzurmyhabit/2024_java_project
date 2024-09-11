public abstract class User {
    protected String room;
    protected String grade;
    protected String classRoom;
    protected String name;

    public User(String room, String grade, String classRoom, String name) {
        this.room = room;
        this.grade = grade;
        this.classRoom = classRoom;
        this.name = name;
    }

    public abstract boolean authenticate(String room, String grade, String classRoom, String name);

}
