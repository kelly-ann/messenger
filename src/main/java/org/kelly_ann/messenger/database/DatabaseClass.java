package org.kelly_ann.messenger.database;

import java.util.HashMap;
import java.util.Map;

import org.kelly_ann.messenger.model.Message;
import org.kelly_ann.messenger.model.Profile;

/*
 * This allows for a faux database interaction.
 * Note: This is not thread safe since it is being used on 1 developer's PC only.
 */
public class DatabaseClass {
	
	private static Map<Long, Message> messages = new HashMap<>();
	private static Map<Long, Profile> profiles = new HashMap<>();
	
	
	public static Map<Long, Message> getMessages() {
		return messages;
	}
	
	public static Map<Long, Profile> getProfiles() {
		return profiles;
	}
}
