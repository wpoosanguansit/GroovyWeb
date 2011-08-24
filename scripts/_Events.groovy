/**
 * to introject scala compiling and post compile aspectj
 * after the groovy compile and java compile 
 */

	
ant.path(id: 'lib') {
		fileset(dir: "${basedir}/lib/", includes: "*.jar" )
        pathelement(path: '${basedir}/lib/gwt/org.restlet.gwt.jar')
        pathelement(path: '${basedir}/lib/gwt/smartgwt.jar')
        pathelement(path: '${basedir}/lib/gwt/smartgwt-skins.jar')
        pathelement(path: '/opt/gwt/gwt-user.jar')
        pathelement(path: '${basedir}/lib/scala-library.jar')
        pathelement(path: '${basedir}/lib/scalatest-0.9.5.jar')
}	 

eventCompileStart = { s ->

    ant.sequential {
        println "Compiling Java ...."
    	javac(destdir: "${basedir}/web-app/WEB-INF/classes", srcdir: "${basedir}/src/java", classpathRef: "lib")

        println "Compiling AspectJ ...."
		taskdef (resource : 'org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties' ) {
			classpath {
					pathelement(location: "${basedir}/lib/aspectjtools.jar")
					pathelement(location: "${basedir}/lib/aspectjweaver.jar")
				}
		}

		iajc (
				source: "1.5",
				target: "1.5",
				compliance: "1.5",
				destdir: "${basedir}/web-app/WEB-INF/classes",
				sourceroots: "${basedir}/src/aspectj",
				classpathRef: "lib"
			 )

        println "Compiling Scala ...."
		taskdef (resource : 'scala/tools/ant/antlib.xml' ) {
			classpath {
					pathelement(location: "${basedir}/lib/scala-compiler.jar")
					pathelement(location: "${basedir}/lib/scala.library.jar")
				}
		}

        scalac (
    				srcdir: "${basedir}/src",
    				destdir: "${basedir}/web-app/WEB-INF/classes",
    				classpathref: "lib",
    				force: "changed"

    		    ) {
    				include(name: "scala/**/*.scala")
    				exclude(name: "")

    	}

//        println "Compiling Actioncript....."
//        taskdef(resource : 'flexTasks.tasks', classpath : '${basedir}/lib/flexTasks.jar')
//        property(name: 'FLEX_HOME', value: 'C:/Program Files/flex')
//        property(name: 'APP_ROOT', value: 'src/actionscript')
//
//        mxmlc (
//                 file : "${basedir}/src/PDMAFWeb.mxml", 'keep-generated-actionscript' : "true",
//                 output: "${basedir}/web-app/PDMAFWeb.swf"
//                ) {
//            'load-config'(filename: "C:/Program Files/flex/frameworks/flex-config.xml")
//            'source-path'('path-element': "C:/Program Files/flex/frameworks")
//        }
//
//        'html-wrapper'(
//                        title: 'Welcome to PDMAFWeb!',
//                        file: 'start.html',
//                        height: '100%',
//                        width: '100%',
//                        bgcolor: 'white',
//                        swf: 'PDMAFWeb',
//                        'version-major': '1.0',
//                        'version-minor': '0.0',
//                        'version-revision': '0.0',
//                        history: 'true',
//                        template: 'express-installation',
//                        output: '${basedir}/web-app/'
//                       )

    }
}

eventCompileEnd = { s ->
//	we copy resource to the right place
	println "Copy resources to WEB-INF ...."
	ant.mkdir(dir: "${basedir}/web-app/WEB-INF/classes/WEB-INF/couchdb-views")
	ant.copy(todir: "${basedir}/web-app/WEB-INF/classes/WEB-INF") {
	    fileset(dir: "${basedir}/web-app/WEB-INF/") {
	        include(name: '*.xml')
	        include(name: '*.properties')
	    }
	}
    ant.sequential {

    	println "Compiling AspectJ ...."
		taskdef (resource : 'org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties' ) {
			classpath {
					pathelement(location: "${basedir}/lib/aspectjtools.jar")
					pathelement(location: "${basedir}/lib/aspectjweaver.jar")
				}
		}

		iajc (
				source: "1.5",
				target: "1.5",
				compliance: "1.5",
				destdir: "${basedir}/web-app/WEB-INF/classes",
				sourceroots: "${basedir}/src/aspectj",
				classpathRef: "lib"
			 )
    }
}

/**
 *This is for the testing that shoud include scala tests as well
 *
 **/
 
 eventTestCompileEnd = { msg ->

    ant.sequential {
      println "Compiling Java Test ...."
       javac(destdir: "${basedir}/web-app/WEB-INF/classes", srcdir: "${basedir}/test/", classpathRef: "lib")

        println "Compiling Scala Test ...."
		taskdef (resource : 'scala/tools/ant/antlib.xml' ) {
			classpath {
					pathelement(location: "${basedir}/lib/scala-compiler.jar")
					pathelement(location: "${basedir}/lib/scala.library.jar")
				}
		}

        scalac (
    				srcdir: "${basedir}/test",
    				destdir: "${basedir}/web-app/WEB-INF/classes",
    				classpathref: "lib",
    				force: "changed"

    		    ) {
    				include(name: "**/*.scala")
    				exclude(name: "")

    	}

      taskdef (name : 'scalatest', classname: 'org.scalatest.tools.ScalaTestTask' ) {
			classpath(refid: 'lib')
	  }

      scalatest(runpath: "${basedir}/web-app/WEB-INF/classes") {
        includes {
          'ServiceRequestPersistenceTest'
        }
      }
    }



    ant.junit(haltonfailure: "true", showoutput: "true") {
				classpath {
					path(location: "${basedir}/lib")
					path(location: "${basedir}/web-app/WEB-INF/classes")
				}
				formatter(type: "brief", usefile: "false")
 				batchtest(fork: "no") {
 							fileset(dir: "${basedir}/web-app/WEB-INF/classes", includes: "**/*Example.class" )
 				}
 	}

 	ant.junitreport(	todir: "${basedir}/test/reports") {
 			  			fileset(dir: "${basedir}/test/reports"
 			  	    ) { include(name: 'TEST-*.xml')
 			  			}
 			  			report(format: 'frames', todir: "${basedir}/test/reports")
 	}
 }

