package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Apr 24, 2009
 * Time: 11:13:58 AM
 * 
 */

public class SuggestOranizationPage extends MakeDonationPage {
      //just to kick in the suggestionorganization.js and select the tab join our network.
      def secondaryHeaderInclude = {
            link(rel: "stylesheet", href: url + "css/smoothness/jquery-ui-1.7.1.custom.css", type: "text/css", media: "screen, projection")
            mkp.yieldUnescaped """
            <!--[if lte IE 7]>
			  <link rel="stylesheet" href="jquery.tabs-ie.css" type="text/css" media="projection, screen">
            <![endif]-->
            """
            script(language: "javascript", src: url + "js/flapjax.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery-1.3.1.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery.hint.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/jquery-ui-1.7.1.custom.min.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/makedonation.js") { mkp.yield ' '}
            script(language: "javascript", src: url + "js/suggestorganization.js") { mkp.yield ' '}
    }
}