
package com.pdmaf.service.directory

import business.models.User
import com.pdmaf.service.directory._
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * This will be the interface point for the the code at the higher layer to interact with the directory.
 * It serves as a facade between the two layers.
 *
 */

class DirectoryService {

    val directory = new LDAP()

    import directory._

    def save(user: User) : User = {
        require(user ne null)

        user.save
    }

    def update(user: User) : User = {
        require(user ne null)
        
        user.update
    }

    def remove(user: User) : User = {
        require(user ne null)
        
        user.remove
    }

    def retrieve(user: User) : User = {
        require(user ne null)
        
        retrieve(user.username)
    }

    def retrieve(username: String) : User = {
        require(username ne null)
        
        val User = classOf[User]
        User.retrieveById(username)
    }

    def authenticate(user: User) : Boolean = {
        require(user ne null)
        
        authenticate(user.username, user.password)
    }

    def authenticate(username: String, password: String) : Boolean = {
        require(username ne null)
        require(password ne null)
        
        val User = classOf[User]
        User.authenticate(username, password)
    }

    def createServiceRequestEmail(hashKeyUID: String, email: String) : Unit = {
        require(hashKeyUID ne null)
        require(email ne null)

        val User = classOf[User]
        User.createServiceRequestEmail(hashKeyUID, email)
    }

    def removeServiceRequestEmail(hashKeyUID: String)  : Unit = {
        require(hashKeyUID ne null)

        val User = classOf[User]
        User.removeServiceRequestEmail(hashKeyUID)
    }

    def unbind(id: String) : Unit = {
        require(id ne null)

        val User = classOf[User]
        User.unbind(id)
    }
}