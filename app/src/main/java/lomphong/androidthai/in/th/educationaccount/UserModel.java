package lomphong.androidthai.in.th.educationaccount;

import android.os.Parcel;
import android.os.Parcelable;

public class UserModel implements Parcelable {


    private String avatarString, messageString, nameString, uidString;

    public UserModel() {

    } //getter

    public UserModel(String avatarString,
                     String messageString,
                     String nameString,
                     String uidString) {
        this.avatarString = avatarString;
        this.messageString = messageString;
        this.nameString = nameString;
        this.uidString = uidString;
    }

    protected UserModel(Parcel in) {
        avatarString = in.readString();
        messageString = in.readString();
        nameString = in.readString();
        uidString = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel in) {
            return new UserModel(in);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };

    public String getAvatarString() {
        return avatarString;
    }

    public void setAvatarString(String avatarString) {
        this.avatarString = avatarString;
    }

    public String getMessageString() {
        return messageString;
    }

    public void setMessageString(String messageString) {
        this.messageString = messageString;
    }

    public String getNameString() {
        return nameString;
    }

    public void setNameString(String nameString) {
        this.nameString = nameString;
    }

    public String getUidString() {
        return uidString;
    }

    public void setUidString(String uidString) {
        this.uidString = uidString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(avatarString);
        dest.writeString(messageString);
        dest.writeString(nameString);
        dest.writeString(uidString);
    }
}
