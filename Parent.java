public class Parent extends User  {
    private String childGrade;
    private String childClassRoom;
    private String childName;

    public Parent(String room, String childGrade, String childClassRoom, String childName) {
        super(room, childGrade, childClassRoom, childName);
        this.childGrade = childGrade;
        this.childClassRoom = childClassRoom;
        this.childName = childName;
    }

    @Override
    public boolean authenticate(String room, String grade, String classRoom, String name) {
        return this.room.equals(room) &&
                this.childGrade.equals(grade) &&
                this.childClassRoom.equals(classRoom) &&
                this.childName.equals(name);
    }
}
