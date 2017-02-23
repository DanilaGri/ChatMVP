package od.chat.event;

import od.chat.model.User;

/**
 * Created by danila on 23.02.17.
 */

public class UpdateUser {
    private User user;

    public UpdateUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
