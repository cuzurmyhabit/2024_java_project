public class Student extends User {
    public Student(String room, String grade, String classRoom, String name) {
        super(room, grade, classRoom, name);
    }
    
    @Override
    public boolean authenticate(String room, String grade, String classRoom, String name) {
        return this.room.equals(room) &&
                this.grade.equals(grade) &&
                this.classRoom.equals(classRoom) &&
                this.name.equals(name);
    }
    
}
