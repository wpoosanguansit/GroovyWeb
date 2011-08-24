"fulltext": {
   "all": {
       "defaults": {
           "store": "yes"
       },
       "index": "function(doc) { var ret = new Document();function idx(obj) { for (var key in obj) { switch (typeof obj[key]) { case 'object': idx(obj[key]); break; case 'function': break; default: ret.field(key, obj[key]);ret.field('all', obj[key]); break; } }} idx(doc); return ret; }"
   }
}

/**
 * query that works jQuery.get('http://localhost:5984/tmys/_fti/ServiceRequest/selected?q=description:fd* ', null, null)
 */
{
   "all": {
       "defaults": {
           "store": "yes"
       },
       "index": "function(doc) { var ret = new Document(); function idx(obj) { for (var key in obj) { switch (typeof obj[key]) { case 'object': idx(obj[key]); break; case 'function': break; default: ret.add(obj[key]); break; } } }; idx(doc); if (doc._attachments) { for (var i in doc._attachments) { ret.attachment('attachment', i); } } return ret; }"
   },
   "selected": {
       "default": {
           "store": "yes"
       },
       "index": "function(doc) { var result = new Document(); result.add(doc.title, {'field':'description', 'store':'yes'}); result.add(doc.description, {'field':'description'}); result.add({'field':'indexed_at'}); return result; } "
   }
   "selected": {
       "default": {
           "store": "yes"
       },
       "index": "function(doc) {var result = new Document();result.add(doc.title, {'field':'title', 'store':'yes'});result.add(doc.postedDate, {'field':'postedDate', 'store':'yes'}); result.add(doc.specificLocation, {'field':'specificLocation', 'store':'yes'});result.add(doc.city, {'field':'city', 'store':'yes'}); result.add(doc.state, {'field':'state', 'store':'yes'}); result.add(doc.country, {'field':'country', 'store':'yes'});result.add(doc.pointsOffered, {'field':'pointsOffered', 'store':'yes'}); result.add(doc.otherCompensation, {'field':'otherCompensation', 'store':'yes'});return result;}"
   }

}

{
   "all": {
       "defaults": {
           "store": "yes"
       },
       "index": "function(doc) { var ret = new Document(); function idx(obj) { for (var key in obj) { switch (typeof obj[key]) { case 'object': idx(obj[key]); break; case 'function': break; default: ret.add(obj[key]); break; } } }; idx(doc); if (doc._attachments) { for (var i in doc._attachments) { ret.attachment('attachment', i); } } return ret; }"
   },
   "selected": {
       "default": {
           "store": "yes"
       },
       "index": "function(doc) { var result = new Document(); result.add(doc.title, {'field':'description', 'store':'yes'}); result.add(doc.city, {'field':'city'}); result.add(doc.category, {'field':'category'}); result.add(doc.description, {'field':'description'}); result.add(doc.poster, {'field':'poster'}); result.add({'field':'indexed_at'}); return result; } "
   }
}