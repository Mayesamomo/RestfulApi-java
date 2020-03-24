/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package REST;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author micha
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
       
       
        resources.add(REST.CommentResource.class);
        resources.add(REST.CommunityResource.class);
        resources.add(REST.CrossOriginResourceSharingFilter.class);
        resources.add(REST.PostResource.class);
        resources.add(REST.UserResource.class);
        resources.add(TEST.CommentResource.class);
        
    }

}
