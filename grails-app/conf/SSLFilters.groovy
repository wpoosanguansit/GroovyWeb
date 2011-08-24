
import org.codehaus.groovy.grails.commons.ConfigurationHolder

/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 18, 2009
 * Time: 8:09:41 PM
 * 
 */



class SSLFilters {
    // Set these to your HTTP/HTTPS ports
    def defaultPortHTTP = 80
    def defaultPortHTTPS = 443

    static def SSLRequiredMap = ConfigurationHolder.config.SSLRequiredMap

    def filters = {

        sslFilter(controller: "*", action: "*")
                {
                    before =
                        {
                            def controller = controllerName
                            def action = actionName
                            log.debug("SSLFilter: ${params?.controller}.${params?.action}")

                            // SSL off by default
                            def useSSL = shouldUseSSL(controllerName,actionName)

                            log.debug("${controller}.${action}, useSSL:$useSSL")

                            // Get redirected url
                            def url = getRedirectURL(request, params, useSSL);

                            // If redirect url returned, redirect to it
                            if (url) {
                                redirect(url: url)
                                return false;
                            }


                            return true;
                        }
                }
    }

    def shouldUseSSL(controllerName, actionName) {
        // User actions use SSL
        if (SSLRequiredMap[controllerName]!= null) {

            //If * then all actions are secured
            if (SSLRequiredMap[controllerName][0] == '*')
                return true

            //else look for specific action
            if (SSLRequiredMap[controllerName].contains(actionName))
                return true
        }

        false
    }

    def getRedirectURL(request, params, ssl) {

        log.debug("getRedirectURL: $ssl")

        def protocol
        def port

        // Are we there already?
        if (request.isSecure() == ssl)
          return null;

        // Otherwise we need to flip
        if (ssl) {
            protocol = 'https'

            // If using the standard ports, don't include them on the URL
            port = defaultPortHTTPS
            if (port == 443)
                port = ":8443"
            else
                port = ":${port}"
        }
        else {
            protocol = 'http'
            // If using the standard ports, don't include them on the URL
            port = defaultPortHTTP
            if (port == 80)
                port = ":8080"
            else
                port = ":${port}"
        }

        log.debug("Flip protocol to $protocol")

        def url = request.forwardURI
        def server = request.serverName
        def args = paramsAsUrl(params)
        url = "$protocol://$server$port$url$args"

        log.debug("getRedirectURL - redirect to $url")

        return url;
    }

    def paramsAsUrl =
    {params ->
        int ii = 0
        String args = ""
        params.each
        {k, v ->
            if (['controller', 'action', 'id'].find {k == it})
                return;

            if (ii)
                args += "&"
            else
                args += "?"

            args += "$k=$v"
            ii++
        }

        return args

    }
}