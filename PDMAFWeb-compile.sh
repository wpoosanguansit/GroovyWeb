#!/bin/sh
APPDIR=.;
echo "Compilation started at" `date +%H:%M:%S`
time1=`date +%s`
java  -Xmx526M -cp "$APPDIR/src/java/:$APPDIR/lib/gwt/validation-api.jar:$APPDIR/lib/junit-4.4.jar:$APPDIR/lib/gwt/gwt-validation-1.0.1b.jar:$APPDIR/lib/aspectjweaver.jar:$APPDIR/lib/aspectjtools.jar:$APPDIR/lib/aspectjrt.jar:$APPDIR/lib/gwt/org.restlet.GWT.jar:$APPDIR/lib/gwt/gwtent.jar:$APPDIR/lib/gwt/smartgwt.jar:$APPDIR/lib/gwt/smartgwt-skins.jar:$APPDIR/web-app/WEB-INF/classes:$GWT_HOME/gwt-user.jar:$GWT_HOME/gwt-incubator.jar:$GWT_HOME/gwt-dev-linux.jar" com.google.gwt.dev.Compiler -war "$APPDIR/web-app/gwt" "$@" com.pdmaf.ui.gwt.PDMAFWeb;
time2=`date +%s`
et=`echo "scale=5 ; $time2 - $time1" | bc`
echo "Compilation ended at" `date +%H:%M:%S`", and took" $et "seconds."
