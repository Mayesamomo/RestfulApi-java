/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TEST;

import REST.PostResource;

/**
 *
 * @author micha
 */
public class MakePost {
     public static void main(String[] args)
    {
        String content = "{\n" +
"    \"userId\": 35,\n" +
"    \"postTitle\": \"I am not lazy \",\n" +
"    \"postDesc\": \"The More you do, the more you learn and the better you get\",\n" +
"    \"media\": \"jjj\"\n" +
"}";
        PostResource ur = new PostResource();
        System.out.println(ur.makePost(content));
    }
}
