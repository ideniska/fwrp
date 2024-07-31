package util;

import javax.servlet.http.HttpServletRequest;
import model.UserDTO;
import model.UserType;

/**
 * AuthUtils provides utility methods for authentication and authorization.
 * 
 * author Berkay
 */
public class AuthUtils {

    /**
     * Checks if the user has the required user type.
     *
     * @param request the HttpServletRequest object that contains the request the client made to the servlet
     * @param requiredType the required UserType for the action
     * @return true if the user has the required user type, false otherwise
     */
    public static boolean checkUserType(HttpServletRequest request, UserType requiredType) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        return user != null && user.getUserType() == requiredType.getValue();
    }
}