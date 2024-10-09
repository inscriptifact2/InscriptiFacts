package isf.common.utils;

/**
 * Created by IntelliJ IDEA.
 * User: kamesh
 * Date: Dec 27, 2003
 * Time: 5:51:18 PM
 * To change this template use Options | File Templates.
 */
public interface LogoutListener {
    public void loggedout(String msg);

    public void disconnected();

    public void raiseError(String msg);
}
