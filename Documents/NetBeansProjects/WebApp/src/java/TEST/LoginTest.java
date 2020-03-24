/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import REST.UserResource;

/**
 *
 * @author micha
 */
public class LoginTest {
    public static void main(String[] args) {
        String content = "{\"username\":\"Jane\",\"password\":\"password\"}";
        UserResource ur = new UserResource();
        System.out.println(ur.login(content));
    }
}
