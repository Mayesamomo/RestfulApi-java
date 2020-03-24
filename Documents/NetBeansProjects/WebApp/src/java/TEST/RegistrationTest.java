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
public class RegistrationTest {
      public static void main(String[] args)
    {
        String content = "{\"fullName\":\"john Doe\",,\"username\":\"johnDoe\",\"password\":\"password\",\"email\":\"johndoe@gmail.com\"}";
        UserResource ur = new UserResource();
        System.out.println(ur.register(content));
    }
}
