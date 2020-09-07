package bro.chat.repositroy;



public class ChatRoomForm {
    private String roomTitle;
    private String userName;
    private int roomPassword;

    public String getRoomTitle() {
        return roomTitle;
    }

    public void setRoomTitle(String roomTitle) {
        this.roomTitle = roomTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getRoomPassword() {
        return roomPassword;
    }

    public void setRoomPassword(int roomPassword) {
        this.roomPassword = roomPassword;
    }
}