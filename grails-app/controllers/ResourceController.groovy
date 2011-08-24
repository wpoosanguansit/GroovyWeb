import grails.converters.JSON
import grails.converters.XML

class ResourceController {

    def index = {
        switch(request.method){
           case "POST":
             render "Create\n"
             break
           case "GET":
             def writer = new StringWriter();
             new grails.util.JSonBuilder(writer).json {
                 identifier 1234
                 "players" {
                     
                 }
             }
             render writer.toString()
             break
           case "PUT":
             render "Update\n"
             break
           case "DELETE":
             render "Delete\n"
             break
         }   
    }
}
