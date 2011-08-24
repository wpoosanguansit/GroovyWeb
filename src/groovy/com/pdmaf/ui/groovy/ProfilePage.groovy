package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt
 * Date: May 11, 2009
 * Time: 2:15:57 PM
 *  
 */

import com.pdmaf.business.models.User

public class ProfilePage extends MainPage {

  User user


  def protected secondaryHeaderInclude = {}


  def protected pageBodyHeader = {
    div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: ' ')
            }
    }

  }

  def protected pageBodyMainContent = {
    div(class: 'span-24') { mkp.yieldUnescaped '&nbsp' }
    div(class: 'span-24') {
        mkp.yieldUnescaped """
           	<div class="span-24">
                <!-- Tabbed bar code-->
                <ul class="tab">
                    <li  class="active"><a href="#"><span>Profile</span></a></li>
                    <li><a href="#"><span>Service Request</span></a></li>
                    <li><a href="${url}profile/serviceProvider"><span>Service Provider</span></a></li>
                        <li><a href="#"><span>Donation</span></a></li>
                </ul><br>
                <div class="span-24">
                  <!-- Tabbed side bar code-->
                  <div class="span-4">
                        <div class="span-4"> &nbsp;</div>
                        <div class="span-4"> &nbsp;</div>
                        <div class="span-4"> &nbsp;</div>
                        <ul id="navmenu-v">
                      <li><a href="#">User Information</a></li>
                      <li><a class="active" href="#">Point Balance</a></li>
                        </ul>
                  </div>
                  <div class="span-20 prepend-2 append-2">
                      <div id=service-provider-form"></div>
                  </div>
                </div>
            </div>
        """
    }
  }
}
