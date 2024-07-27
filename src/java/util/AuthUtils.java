/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import javax.servlet.http.HttpServletRequest;
import model.UserDTO;
import model.UserType;

/**
 *
 * @author berka
 */
public class AuthUtils {
    public static boolean checkUserType(HttpServletRequest request, UserType requiredType) {
        UserDTO user = (UserDTO) request.getSession().getAttribute("user");
        return user != null && user.getUserType() == requiredType.getValue();
    }
}