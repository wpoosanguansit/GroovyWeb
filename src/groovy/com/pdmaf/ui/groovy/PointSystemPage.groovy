package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 31, 2009
 * Time: 2:29:44 PM
 *
 */

public class PointSystemPage extends MainPage {

    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'white space')
            }
        }

    }

    def protected pageBodyMainContent = {
      div(class: 'span-24') {
        div(class: 'span-10 prepend-1 append-1') {
                        p { mkp.yieldUnescaped("""<h6>Why Point System?</h6>
                                                  We believe point system is the critical and central element that would
                                                  help us achieve our goal of bulding this community into a fun
                                                  perpetual charity giving machine. Here are a few of the components
                                                  in the system.  Total point outstanding directly reflects the sum of money
                                                  donated to both charity organizations and PDMAF.com.  In accounting term,
                                                  it is all the total outstanding lability in our community. While cash
                                                  reserve reflects the the sum of money donated to our community, PDMAF.com.
                                                  So in essence donors and service providers gets the incentives to contribute their
                                                  parts in thier generous givings however big or small they may be.""")
                        }
                        p { mkp.yieldUnescaped("""<br>Simple rules we go by : <br>  
                                                  <li>When user donates to charity organization of choice, he/she will
                                                  earn a point for every dollar they give at PDMAF.com.
                                                  </li>
                                                  <li>When user donates to PDMF.com, he/she effectively increases the cash reserve,
                                                  hence the incentive for other people to provide services to other donors.
                                                  </li>
                                                  </li>
                                                  <li>When user purchases points from PDMAF.com, he/she increases the cash reserve and
                                                  total point outstanding in the system.
                                                  </li>
                                                  <li>When user cash out their points, he/she decreases the cash reserve and
                                                  total point outstanding in the system.
                                                  </li>
                                                  <li>The offical exchange rate is calculated in real time and will be publicly posted on
                                                  PDMF.com.
                                                  </li>
                                                  Or when user purchase the points, the money from the purchase will go directly
                                                  into the reserve for point/cash conversion.  This will improve the exhange rate
                                                  for the users who would like to cash them.  Finally, the user can earn the points
                                                  from the services they are able to provide to the community.<br>""")
                        }
           
        }

        div(class: 'span-11 append-1 last') {
            div(class: 'span-11') { mkp.yieldUnescaped ' <br> ' }
            p {
                img(src: url + 'images/pointsystem.jpg', alt: 'Point System banner')

            }
        }


        div(class: 'span-11') {

                p { mkp.yieldUnescaped("""<br><h6>How sound is the system?</h6><br>  We are a community site, thus, we
                                          rely heavily on the mutual trust we build with the members.  It is in our
                                          very core belief and interest to act ethically and be the most transparent
                                          to earn and build on that trust.  And as a community site, we rely on
                                          our member to get involve in all aspects of the site and help us safe guard the site
                                          against the abuse and misuse of the site in everyway.  We strongly believe
                                          community freedom and self-regulation. Period!""")
                }
        }
     }
    }
}