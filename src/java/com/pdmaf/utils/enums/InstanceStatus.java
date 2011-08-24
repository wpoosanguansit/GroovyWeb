package com.pdmaf.utils.enums;

/**
 * 
 * @author watt
 * The status of the object - TRANSCIENT is when the object is updated buy not yet saved
 * 							  similar to the dirty keyword.
 * 							  ACTIVE is when it is newly created and not yet saved.
 */
public enum InstanceStatus {
	INACTIVE, ACTIVE, DELETED, SAVED, TRANSCIENT, EXPIRED
}
