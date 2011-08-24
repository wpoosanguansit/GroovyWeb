// locations to search for config files that get merged into the main config
// config files can either be Java properties files or ConfigSlurper scripts

// grails.config.locations = [ "classpath:${appName}-config.properties",
//                             "classpath:${appName}-config.groovy",
//                             "file:${userHome}/.grails/${appName}-config.properties",
//                             "file:${userHome}/.grails/${appName}-config.groovy"]

// if(System.properties["${appName}.config.location"]) {
//    grails.config.locations << "file:" + System.properties["${appName}.config.location"]
// }
grails.mime.file.extensions = true // enables the parsing of file extensions from URLs into the request format
grails.mime.use.accept.header = false
grails.mime.types = [ html: ['text/html','application/xhtml+xml'],
                      xml: ['text/xml', 'application/xml'],
                      text: 'text/plain',
                      js: 'text/javascript',
                      rss: 'application/rss+xml',
                      atom: 'application/atom+xml',
                      css: 'text/css',
                      csv: 'text/csv',
                      all: '*/*',
                      json: ['application/json','text/json'],
                      form: 'application/x-www-form-urlencoded',
                      multipartForm: 'multipart/form-data'
                    ]
// The default codec used to encode data with ${}
grails.views.default.codec="none" // none, html, base64
grails.views.gsp.encoding="UTF-8"
grails.converters.encoding="UTF-8"

// enabled native2ascii conversion of i18n properties files
grails.enable.native2ascii = true

//for mail plugin, the account that will send out emails
grails.mail.host = "smtp.gmail.com"
grails.mail.port = 465
grails.mail.username = "Watt.Poosanguansit@gmail.com"
grails.mail.password = "watt2000"
grails.mail.props = ["mail.smtp.auth":"true",
                        "mail.smtp.socketFactory.port":"465",
                        "mail.smtp.socketFactory.class":"javax.net.ssl.SSLSocketFactory",
                        "mail.smtp.socketFactory.fallback":"false"]

// set per-environment serverURL stem for creating absolute links
environments {
    production {
        grails.serverURL = "http://localhost:8080/PDMAFWeb/"
        grails.serverSecureURL = "https://localhost:8443/PDMAFWeb/"
    }

    development {
        log4j {
            appender.stdout = "stdout"
            rootLogger="error,logfile,stdout"
            logger {
                grails {
                    app {
                        controller="info"
                    }
                }
            }
        }
        //this sets up the url for hrefs in forms etc, todo: find a way to dynamically determine the deployed url
        grails.serverURL = "http://localhost:8080/PDMAFWeb/"
        grails.serverSecureURL = "https://localhost:8443/PDMAFWeb/"

    }
}

// log4j configuration
log4j.appender.stdout = "org.apache.log4j.ConsoleAppender"
log4j.appender."stdout.layout"="org.apache.log4j.PatternLayout"
log4j.rootLogger="error,stdout"
log4j.logger.org.springframework="info,stdout"
log4j.additivity.org.springframework=false

//this is for ldap setup. jsecurity uses this info for login in ldap realm
ldap.server.url = "ldap://localhost:389"
ldap.search.base = "ou=users,dc=pdmaf,dc=com"
ldap.search.user = "cn=admin,dc=pdmaf,dc=com"
ldap.search.pass = "imagine"
ldap.getUsernameTextBox.attribute = "uid"
ldap.skip.authentication = false
ldap.skip.credentialsCheck = false
ldap.allowEmptyPasswords = false

//this is for email that the systems will use
email.administrator = ""
email.feedback = "wpoosanguansit@yahoo.com"

//set the ssl port on the controllers for SSLFilters, SSL Secured paths
SSLRequiredMap=[siteRPC:['signIn']]
