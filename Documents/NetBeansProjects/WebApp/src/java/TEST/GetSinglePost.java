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
public class GetSinglePost {
    public static void main(String[] args) {
        PostResource pr = new PostResource();
        System.out.println(pr.getOnePost(20));
    }
}
