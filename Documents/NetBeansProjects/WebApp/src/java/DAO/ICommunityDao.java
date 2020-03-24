/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DTO.Community;
import java.util.ArrayList;

/**
 *
 * @author micha
 */
public interface ICommunityDao {
    ArrayList<Community> getAllCommunity();
    ArrayList<Community> getCommunityByUser(int userId);
    ArrayList<Community> getCommunityById(int communityId);
     boolean createCommunity(int userId, String communityName);
     boolean updateCommunity(int communityId, String communityName);
      boolean deleteCommunity(int communityId);
}
