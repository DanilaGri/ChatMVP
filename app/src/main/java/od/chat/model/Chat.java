package od.chat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by danila on 18.09.16.
 */
public class Chat {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("user_surname")
    @Expose
    private String userSurname;
    @SerializedName("user_avatar")
    @Expose
    private String userAvatar;

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp The timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return The userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName The user_name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return The userSurname
     */
    public String getUserSurname() {
        return userSurname;
    }

    /**
     * @param userSurname The user_surname
     */
    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    /**
     * @return The userAvatar
     */
    public String getUserAvatar() {
        return userAvatar;
    }

    /**
     * @param userAvatar The user_avatar
     */
    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

}
