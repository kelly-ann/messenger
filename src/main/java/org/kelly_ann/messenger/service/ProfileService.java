package org.kelly_ann.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kelly_ann.messenger.database.DatabaseClass;
import org.kelly_ann.messenger.model.Profile;

// this class and the MessageService class are very similar except for the fact that the lookup for ProfileService is the 
// profile's name vs. the id as it is in the MessageService.
public class ProfileService {
	
	private Map<String, Profile> profiles = DatabaseClass.getProfiles();
	
	public ProfileService() {
		profiles.put("specialk1st", new Profile(1L, "specialk1st", "Kelly-Ann", "P"));
	}
	
	public List<Profile> getAllProfiles() {
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile updateProfile(Profile profile) {
		if(profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
