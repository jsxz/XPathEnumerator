package edu.ncrn.cornell.xml

import XpathEnumerator._
import org.specs2._

import scala.xml.{Node, XML}
import edu.ncrn.cornell.xml.Util._


/**
  * @author Brandon Barker
  *         9/8/2016
  */


class ShipOrderXsdSpec extends Specification { def is = s2"""

 Testing simple ShipOrder XSD
   Passed basics checks for Russian Doll variant           $readAndFindRD
   Passed basics checks for Salami Slice variant           $readAndFindSS
   Passed basics checks for Venetian Blind variant         $readAndFindVB
   Passed basics checks for Venetian Blind Group variant   $readAndFindVBG
                                 """

  val unwantedPrefixes = List("xsi")

  //TODO: better way to do this based on real namespace lookup in Scala XML?
  def xmlXpathFilter(xpath: String, badList: List[String]) = !badList.exists(x =>
    xpath.contains("/" + x + ":") || xpath.contains("/@" + x + ":")
  )

  val xpathXmlEnumerator = new XpathXmlEnumerator(XML.load(
    this.getClass.getResourceAsStream("/shiporder.xml")
  ).toList)

  val xsdRussianDollFile = "/shiporder.xsd"
  val xsdSalamiSliceFile = "/shiporder2.xsd"
  val xsdVenetianBlindFile = "/shiporder3.xsd"
  val xsdVbWithGroupsFile = "/shiporder4.xsd"

  val xmlTestData = xpathXmlEnumerator.enumSimple
    .filter(x => xmlXpathFilter(x._1, unwantedPrefixes))

  val xmlTestDataNonUniq = toWildCard(xmlTestData.map{x => x._1})

  val readAndFindFromFile = makePairedTester(xmlTestDataNonUniq)

  val readAndFindRD = readAndFindFromFile(xsdRussianDollFile, noFilter) must beTrue
  val readAndFindSS = readAndFindFromFile(xsdSalamiSliceFile, noFilter) must beTrue
  val readAndFindVB = readAndFindFromFile(xsdVenetianBlindFile, noFilter) must beTrue
  val readAndFindVBG = readAndFindFromFile(xsdVbWithGroupsFile, noFilter) must beTrue


}



