package com.pdmaf.ui.groovy
/**
 * Created by IntelliJ IDEA.
 * User: watt poosanguansit
 * Date: Mar 31, 2009
 * Time: 8:34:02 AM
 *
 */

public class HowItWorksPage extends MainPage {

    def protected pageBodyHeader = {
        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/0.png', class: 'top pull-1 left', alt: 'white space')
            }
        }

    }

    def protected pageBodyMainContent = {

        div(class: 'span-10 prepend-1') {
            p {
                img(src: url + 'images/howitworks.jpg', alt: 'How it works! banner.')

            }
        }

        div(class: 'span-11 prepend-1') {
            h3('Make it Simple!')
        }

        div(class: 'span-11 prepend-1') {
                p { mkp.yieldUnescaped("""<blockquote>
                                            There is one simple rule we go by: "Do good, and be rewarded".
                                          </blockquote>
                                          <p>This is the simplest flow of events that can happen</p>
                                          <li>A registered user would need some favor from the commnity
                                              to perform some tasks, let's say walking a dog. The user can
                                              provide some incentives in the forms of PDMAF.com's points as
                                              a reward to the one who can delivered the result.
                                          </li>
                                          <li>He or she posts it online with PDMAF.com</li>
                                          <li>Another registered user who fits the requirements - let's say
                                              live near the requester apartment with good background, replied to the post.
                                          </li>
                                          <li>The service requester can then select the service provider.</li>
                                          <li>The service provider performs the tasks.</li>
                                          <li>The requester check the tasks performed and direct PDMAF.com to issue
                                              the reward.</li>
                                          <li>The one with points can cash out the points from PDMAF.com at any time.</li>""")
                }
        }

        div(class: 'span-10 prepend-1') {

                p { mkp.yieldUnescaped("""<br><h6>How point system works</h6><br>  It is simple.  This is how -
                                          PDMAF.com will maintain the point to dollar exchange rate and publish it on the
                                          site at all time.  The site is leagally bound and will honor the points issued
                                          allowing conversion at prevailing rate at any point in time to those who
                                          rightfully earn or purchase them.  Points can be had by three means, donate
                                          to your favorite charities through PDMAF.com, purchase them out right or earn
                                          them by providing services to the community.""")
                                a(href:  url + 'application/pointSystem', ' more detail ...-> ')
                }

        }

        div(class: 'span-11 prepend-1') {

                p { mkp.yieldUnescaped("""<br><h6>How do I get rewarded</h6><br>  First and foremost, we would like to
                                          state that everybody gets rewarded in the end.  Be it the charity
                                          organizations.  Hopefully, this will bring in more donations to their budgets.
                                          The donor can have the deep sense of satisfaction of giving plus the merit of
                                          community help when they need, resulting from thier good karma, hopefully
                                          instantly.  The service provider can feel proud that they help out the community
                                          while, at the same time, be rewarded monetarily for their skills and work.
                                          Finally, we at PDMAF.com can feel the most satisfaction in knowing that our work
                                          sets in motion a virtuous circle in the lives of our community members.""")
                }
        }
    }

}