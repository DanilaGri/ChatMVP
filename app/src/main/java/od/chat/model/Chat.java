package od.chat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by danila on 18.09.16.
 */
public class Chat {
    @SerializedName("0")
    @Expose
    private String _0;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("1")
    @Expose
    private String _1;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("2")
    @Expose
    private String _2;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("3")
    @Expose
    private String _3;
    @SerializedName("timestamp")
    @Expose
    private String timestamp;
    @SerializedName("4")
    @Expose
    private String _4;
    @SerializedName("user_name")
    @Expose
    private String userName;
    @SerializedName("5")
    @Expose
    private String _5;
    @SerializedName("user_surname")
    @Expose
    private String userSurname;

    /**
     * @return The _0
     */
    public String get0() {
        return _0;
    }

    /**
     * @param _0 The 0
     */
    public void set0(String _0) {
        this._0 = _0;
    }

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
     * @return The _1
     */
    public String get1() {
        return _1;
    }

    /**
     * @param _1 The 1
     */
    public void set1(String _1) {
        this._1 = _1;
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
     * @return The _2
     */
    public String get2() {
        return _2;
    }

    /**
     * @param _2 The 2
     */
    public void set2(String _2) {
        this._2 = _2;
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
     * @return The _3
     */
    public String get3() {
        return _3;
    }

    /**
     * @param _3 The 3
     */
    public void set3(String _3) {
        this._3 = _3;
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
     * @return The _4
     */
    public String get4() {
        return _4;
    }

    /**
     * @param _4 The 4
     */
    public void set4(String _4) {
        this._4 = _4;
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
     * @return The _5
     */
    public String get5() {
        return _5;
    }

    /**
     * @param _5 The 5
     */
    public void set5(String _5) {
        this._5 = _5;
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
}
