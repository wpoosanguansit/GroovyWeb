@java -Xmx516M -cp "lib/gwt/validation-api.jar;lib/junit-4.4.jar;lib/gwt/gwt-validation-1.0.1b.jar;lib/aspectjweaver.jar;lib/aspectjtools.jar;lib/aspectjrt.jar;lib/gwt/gwtent.jar;lib/gwt/org.restlet.GWT.jar;lib/gwt/smartgwt.jar;lib/gwt/smartgwt-skins;%~dp0\src\java;%~dp0\web-app\WEB-INF\classes;C:/Program Files/gwt/gwt-user.jar;C:/Program Files/gwt/gwt-dev-windows.jar" com.google.gwt.dev.HostedMode -war "%~dp0/web-app/WEB-INF/classes" %* com.pdmaf.ui.gwt.PDMAFWeb %* 