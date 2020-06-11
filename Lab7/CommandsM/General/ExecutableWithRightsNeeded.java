package Lab7.CommandsM.General;

import Lab7.Sercurity.User;

public interface ExecutableWithRightsNeeded extends Executable{

    void setUser(User user);

}
