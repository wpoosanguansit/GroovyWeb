package com.pdmaf.utils.generators;

/**
 * This will return the id in the format of <model-name>-<UUID>
 * using a java.rmi.server.UID. The Serializable identifiers generated 
 * by this class are unique on the host on which they are generated, provided that
 * 
 * 1. the host takes more than one millisecond to reboot
 * 2. the host's clock is never set to run backwards
 */
public class IDGenerator {

	public static final String generateIDFor(String model) {
		StringBuilder builder = new StringBuilder();
		builder.append(model);
		builder.append("-");
		builder.append(java.util.UUID.randomUUID());
		return builder.toString();
	}
}
