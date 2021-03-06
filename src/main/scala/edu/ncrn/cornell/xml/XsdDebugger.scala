package edu.ncrn.cornell.xml

import edu.ncrn.cornell.Util._

import scala.collection.mutable
//import cats._
//import cats.instances.all._
//import cats.syntax.eq._

import scala.xml.Node
/**
  * @author Brandon Barker
  *         10/18/2016
  */

/**
  * This class is intended to be used to help with debugging XpathXsdEnumerator
  * or its variants; it is not part of the public API.
  */
@SuppressWarnings(Array("org.wartremover.warts.MutableDataStructures",
  "org.wartremover.warts.NonUnitStatements", "org.wartremover.warts.Var"
))
private[xml] class XsdDebugger {
  val pathCounter: mutable.Map[String, Int] = mutable.Map()
  var progressCounter: Long = 0

  def addPath(xpath: String): Unit = {
    if (pathCounter.contains(xpath)) {
      val currentCount: Int = pathCounter(xpath)
      if (currentCount % 1000 === 0) {
        println(s"$xpath count is $currentCount")
      }
      pathCounter += (xpath -> (currentCount + 1))
    }
    else {
      println(s"::new xpath:: $xpath")
      pathCounter += (xpath -> 0)
    }
  }

  def addPathNode(xpath: String, node: NodeWrap): Unit = {
    if (pathCounter.contains(xpath)) {
      val currentCount: Int = pathCounter(xpath)
      if (currentCount > 20) {
        println(s"$xpath count is $currentCount")
      }
      pathCounter += (xpath -> (currentCount + 1))
    }
    else {
      //if (node.node.prefix =!= "xs") {
//        println(s"::new xpath:: $xpath with non-default namespace ${node.node.namespace}" +
//        s" prefix = ${node.node.prefix}")
      //}
      if (node.node.prefix =!= "xs") {
        println(s"::new prefix:: = ${node.node.prefix}")
      }
      pathCounter += (xpath -> 0)
    }
  }

  def progressCount(printInterval: Long): Unit = {
    progressCounter += 1
    if (progressCounter % printInterval === 0) {
      println(s"progress: $progressCounter")
    }
  }

  def printOnProgressCount(message: String, printInterval: Long): Unit = {
    if (progressCounter % printInterval === 0) {
      println(message)
    }
  }


}
