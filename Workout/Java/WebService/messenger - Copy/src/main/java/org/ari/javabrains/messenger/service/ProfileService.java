package org.ari.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ari.javabrains.messenger.database.StorageContainer;
import org.ari.javabrains.messenger.model.Profile;

public class ProfileService {
	public ProfileService() {
		profiles.put("ari", new Profile(1L, "ari","Aravind", "Jayakumar"));
	}
	
	private Map<String, Profile> profiles = StorageContainer.getProfiles();
	
	public List<Profile> getAllProfile() {
		return new ArrayList<Profile>(profiles.values());
	}
	
	public Profile getProfile(String profileName) {
		return profiles.get(profileName);
	}
	
	public Profile addProfile(Profile message) {
		message.setId(profiles.size() + 1);
		profiles.put(message.getProfileName(), message);
		return message;
	}
	
	public Profile updateProfile(Profile message) {
		if(message.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(message.getProfileName(), message);
		return message;
	}
	
	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
