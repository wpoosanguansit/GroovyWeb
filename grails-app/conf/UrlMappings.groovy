class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }

      "/resource/$action/$id?"(controller:"resource",action:"index")

	  "500"(view:'/error')
	}
}
