package com.pdmaf.ui.gwt.server.test;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * A servlet that returns a given value, following the JSONP protocol.
 * Expected url parameters:
 *
 * <ul>
 *   <li>action: one of the following values:
 *     <ul>
 *       <li>TIMEOUT: don't respond anything to simulate a timeout
 *       <li>SUCCESS: return a JSON value
 *       <li>FAILURE: return an error
 *     </ul>
 *   <li>value: the JSON value to return if action == "SUCCESS"
 *   <li>error: the error message to return if action == "FAILURE"
 * </ul>
 */
public class EchoServlet extends HttpServlet {

  private enum Action {
    SUCCESS,
    FAILURE,
    TIMEOUT
  }

  @Override
  protected void service(HttpServletRequest req, HttpServletResponse res)
      throws ServletException, IOException {
    switch (Action.valueOf(req.getParameter("action"))) {
      case SUCCESS: {
        String callback = req.getParameter("callback");
        String value = req.getParameter("value");
        if (value == null) {
          value = "";
        }
        res.getWriter().println(callback + "(" + value + ");");
        break;
      }

      case FAILURE: {
        String failureCallback = req.getParameter("failureCallback");
        String error = req.getParameter("error");
        if (failureCallback != null) {
          res.getWriter().println(failureCallback + "('" + error + "');");
        } else {
          // If no failure callback is defined, send the error through the
          // success callback.
          String callback = req.getParameter("callback");
          res.getWriter().println(callback + "('" + error + "');");
        }
        break;
      }

      case TIMEOUT:
        // Don't respond anything so that a timeout happens.
    }
  }
}
