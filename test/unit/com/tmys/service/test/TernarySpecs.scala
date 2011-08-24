package com.pdmaf.service.test

import com.pdmaf.utils.service._
import org.specs._

object TernarySpecs extends Specification {
  import Ternary._
  
  "coalescing operator" should {
    "use predicate when not null" in {
      "success" ?? "failure" mustEqual "success"
    }
    
    "use alternative when null" in {
      val test: String = null
      test ?? "success" mustEqual "success"
    }
    
    "type correctly" in {		// if it compiles, then we're fine
      val str: String = "success" ?? "failure"
      val i: Int = 123 ?? 321
      
      str mustEqual "success"
      i mustEqual 123
    }
    
    "infer join of types" in {
      val res: CharSequence = "success" ?? new java.lang.StringBuilder("failure")	// must compile
      res mustEqual "success"
    }
    
    "only eval alternative when null" in {
      var a = "success"
      def alt = {
        a = "failure"
        a
      }
      
      "non-null" ?? alt
      a mustEqual "success"
    }
  }
  
  "elvis operator" should {
    "use predicate when not null" in {
      "success" ?: "failure" mustEqual "success"
    }
    
    "use alternative when null" in {
      val test: String = null
      test ?: "success" mustEqual "success"
    }
    
    "type correctly" in {		// if it compiles, then we're fine
      val str: String = "success" ?: "failure"
      val i: Int = 123 ?: 321
      
      str mustEqual "success"
      i mustEqual 123
    }
    
    "infer join of types" in {
      val res: CharSequence = "success" ?: new java.lang.StringBuilder("failure")	// must compile
      res mustEqual "success"
    }
    
    "only eval alternative when null" in {
      var a = "success"
      def alt = {
        a = "failure"
        a
      }
      
      "non-null" ?: alt
      a mustEqual "success"
    }
  }
}
