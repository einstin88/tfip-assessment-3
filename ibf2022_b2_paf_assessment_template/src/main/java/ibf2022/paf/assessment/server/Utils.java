package ibf2022.paf.assessment.server;

import ibf2022.paf.assessment.server.models.User;

public class Utils {
    public static User createNewUser(String username){
        User user = new User();
        user.setUsername(username);
        user.setName(username);

        return user;
    }
}
