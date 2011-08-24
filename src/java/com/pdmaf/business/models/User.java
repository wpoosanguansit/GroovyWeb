package com.pdmaf.business.models;

import com.pdmaf.utils.enums.EmailType;
import com.pdmaf.utils.enums.InstanceStatus;
import com.pdmaf.utils.enums.UserType;
import com.pdmaf.utils.generators.Base64;
import java.util.List;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.Random;



/**
 * This is used as a placed holder for users to interface with the Directory, authenticate
 * them and used in the security implementation in general.
 * This can be used to initialized with just username and password.  In this case
 * it is in the state where it is to be used to authenticate the user.
 * 
 * It is also can be initialized with full information which assume the user has at least
 * one address, one email and one telephone provided.
 * 
 * The status of the object is ACTIVE when created, SAVED when it is persisted and
 * DELETED when deleted from the directory.
 * 
 * @author watt
 *
 */
public class User {
	
	private String displayname;
	private String username;
	private String password;
    private Date birthdate;
	private Address address;
	private Email email;
	private List<Telephone> telephones;
    private byte[] photo;
    private UserType type;
    private InstanceStatus status;
    private String serviceProviderProfileID;
	/**
	 * This form is used for authentication only.
	 * It will throw an exception if it is used to persist 
	 * the instance to the directory.
	 * 
	 * @param username
	 * @param password
	 */
	public User(String username, String password) {
	    if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
	          throw new IllegalArgumentException("User : username or password passed can not be null.");    
	    }
        this.email = new Email(EmailType.PERSONAL, username);
		this.username = username;
		this.password = encrypt(password);
		this.displayname = username;
        this.type = UserType.USER;
		this.status = InstanceStatus.TRANSCIENT;
	}

    /**
	 * This form is used for administrator to login
	 *
	 * @param username
	 * @param password
     * @param type
	 */
    public User(String username, String password, UserType type) {
	    if (username == null || username.isEmpty() || password == null || password.isEmpty() || type == null) {
	          throw new IllegalArgumentException("User : username or password or user type passed can not be null.");
	    }
        this.email = new Email(EmailType.PERSONAL, username);
		this.username = username;
		this.password = encrypt(password);
		this.displayname = username;
        this.type = type;
		this.status = InstanceStatus.TRANSCIENT;
	}
	/**
	 * 
	 * @param username
	 * @param password
	 * @param address
	 * @param email
	 * @param phones
	 */
	public User(String username, String password, Date birthdate, Address address, Email email, List<Telephone> phones) {
	    if (username == null || username.isEmpty() || password == null || password.isEmpty() || birthdate == null ||
	    		address == null || email == null) {
	          throw new IllegalArgumentException("User : username or password or birthdate or address or email passed can not be null.");
	    }
	    this.displayname = username;
		this.username = username;
		this.password = encrypt(password);
		this.address = address;
		this.email = email;
		this.telephones = phones;
        this.birthdate = birthdate;
        this.type = UserType.USER;
		this.status = InstanceStatus.TRANSCIENT;
	}

    	/**
	 * @param displayname
	 * @param username
	 * @param password
	 * @param address
	 * @param email
	 * @param phones
	 */
	public User(String displayname, String username, String password, Date birthdate, Address address, Email email,
                List<Telephone> phones) {
	    if (username == null || username.isEmpty() || password == null || password.isEmpty() ||
	    		birthdate == null || address == null || email == null) {
	          throw new IllegalArgumentException("User : displayname or username or password or birthdate or address or email passed can not be null.");
	    }
	    if (displayname == null || displayname.isEmpty()) {
	    	this.displayname = username;
	    } else {
	    	this.displayname = displayname;
	    }
		this.username = username;
		this.password = encrypt(password);
		this.address = address;
		this.email = email;
		this.telephones = phones;
        this.birthdate = birthdate;
        this.type = UserType.USER;
		this.status = InstanceStatus.TRANSCIENT;
	}

	/**
	 * @param displayname
	 * @param username
	 * @param password
	 * @param address
	 * @param email
	 * @param phones
     * @param photo
	 */
	public User(String displayname, String username, String password, Date birthdate, Address address, Email email,
                List<Telephone> phones, byte[] photo) {
	    if (username == null || username.isEmpty() || password == null || password.isEmpty() || 
	    		birthdate == null || address == null || email == null) {
	          throw new IllegalArgumentException("User : displayname or username or password or birthdate or address or email passed can not be null.");  
	    }
	    if (displayname == null || displayname.isEmpty()) {
	    	this.displayname = username;
	    } else {
	    	this.displayname = displayname;
	    }
		this.username = username;
		this.password = encrypt(password);
		this.address = address;
		this.email = email;
		this.telephones = phones;
        this.birthdate = birthdate;
        this.type = UserType.USER;
		this.status = InstanceStatus.TRANSCIENT;
        this.photo = photo;
	}

    	/**
	 * @param displayname
	 * @param username
	 * @param password
	 * @param address
	 * @param email
	 * @param phones
     * @param photo
     * @param serviceProviderProfileID
	 */
	public User(String displayname, String username, String password, Date birthdate, Address address, Email email,
                List<Telephone> phones, byte[] photo, String serviceProviderProfileID) {
	    if (username == null || username.isEmpty() || password == null || password.isEmpty() ||
	    		birthdate == null || address == null || email == null) {
	          throw new IllegalArgumentException("User : displayname or username or password or birthdate or address or email passed can not be null.");
	    }
	    if (displayname == null || displayname.isEmpty()) {
	    	this.displayname = username;
	    } else {
	    	this.displayname = displayname;
	    }
		this.username = username;
		this.password = encrypt(password);
		this.address = address;
		this.email = email;
		this.telephones = phones;
        this.birthdate = birthdate;
        this.type = UserType.USER;
        this.photo = photo;
        this.serviceProviderProfileID = serviceProviderProfileID;
        this.status = InstanceStatus.TRANSCIENT;    
	}

    public final String resetPassword() {
        Random random = new Random();
        String tempPassword = Integer.toString(Math.abs(random.nextInt()));
        this.password = encrypt(tempPassword);
        return tempPassword;
    }

    public void updatePassword(String newPassword) {
        this.password = encrypt(newPassword);        
    }

	public final String displayname() {
		return this.displayname;
	}
	
	public final String username() {
		return this.username;
	}
	
	public final String password() {
		return this.password;
	}

    public final String status() {
        return this.status.toString();
    }

    public final Date birthdate() {
        return birthdate;
    }
    
	public final Address address() {
		if (this.address == null) {
			throw new IllegalStateException("User : the instance is not properly initialized from the directory. Please check" +
					"that the instance is retrieved from the directory before use.");
		}
		return this.address;
	}
	
	public final Email email() {
		if (this.email == null) {
			throw new IllegalStateException("User : the instance is not properly initialized from the directory. Please check" +
					"that the instance is retrieved from the directory before use.");
		}
		return this.email;
	}
	
	public final List<Telephone> telephones() {
		if (this.telephones == null || telephones.isEmpty()) {
			throw new IllegalStateException("User : the instance is not properly initialized from the directory. Please check" +
					"that the instance is retrieved from the directory before use.");
		}
		return this.telephones;
	}

    public final byte[] photo()  {
        return this.photo;
    }

    public void addPhoto(byte[] photo) {
        this.photo = photo;
    }

    public final boolean isActive() {
        return this.status == InstanceStatus.ACTIVE; 
    }

    public final boolean hasDefaultPhoto() {
        return this.photo == null;
    }

    public final boolean isAdministrator() {
        return this.type == UserType.ADMINISTRATOR;
    }

    public final String serviceProviderProfileID() {
        return this.serviceProviderProfileID;
    }
    
    public final void addServiceProviderProfileID(String profileID) {
        this.serviceProviderProfileID = profileID;
    }

    public final boolean hasServiceProviderProfile() {
        return this.serviceProviderProfileID != null && !this.serviceProviderProfileID.isEmpty();
    }

	/**
	 * this is to do one time hash the password and store it
	 * to the database as hashed string.
	 * 
	 * step 1: The registration servlet will interface with our PasswordService class using this static getInstance() method. 
	 * Whenever it is invoked, a check will be made to see if an instance of this service class already exists. 
	 * If so, it will be returned back to the caller (registration servlet). Otherwise, a new instance will be created.
	 * 
	 * step 2: We are asking Java security API to obtain an instance of a message digest object using the algorithm supplied 
	 * (in this case, SHA-1 message digest algorithm will be used. Both SHA and SHA-1 refer to the same thing, a revised SHA algorithm). 
	 * Sun JDK includes JCA (Java Cryptography Architecture) which includes support for SHA algorithm. If your environment does not support SHA, 
	 * NoSuchAlgorithmException will be thrown.
	 * 
	 * step 3: Feed the data: a) convert the plaintext password (eg, "jsmith") into a byte-representation using UTF-8 encoding format.
	 * 						  b) apply this array to the message digest object created earlier. This array will be used as a source for 
	 * the message digest object to operate on.
	 * 
	 * step 3: Do the transformation: generate an array of bytes that represent the digested (encrypted) password value.
	 * 
	 * step 4: Create a String representation of the byte array representing the digested password value. This is needed to be able to 
	 * store the password in the database. At this point, the hash value of the plaintext "jsmith" is "5yfRRkrhJDbomacm2lsvEdg4GyY=".
	 * step 5: Return the String representation of the newly generated hash back to our registration servlet so that it can be stored in the database. 
	 * The user.getPassword() method now returns "5yfRRkrhJDbomacm2lsvEdg4GyY=" 
	 *
	 * @param password
	 * @return encrypted password string
	 */
	public static final String encrypt(String password) {
		   
		MessageDigest md = null;
		    
		try { 
			md = MessageDigest.getInstance("SHA"); //step 2    
		} catch(NoSuchAlgorithmException e) {
			throw new IllegalStateException("User : encrypting the password does not have the algorithm in the env.", e);    
		}
		    
		try {  
			md.update(password.getBytes("UTF-8")); //step 3    
		} catch(UnsupportedEncodingException e) {  
			throw new IllegalStateException("User : encrypting the password with unsupport encoding in the env.", e);    
		}

        byte raw[] = md.digest(); //step 4
        String hash = Base64.encodeBytes(raw); //step 5
        return hash; //step 6
	}
}
