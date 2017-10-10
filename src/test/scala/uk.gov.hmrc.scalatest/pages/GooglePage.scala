package uk.gov.hmrc.scalatest.pages

object GooglePage extends BasePage {

//  override val url = ""

  def navigateToGoogle(): Unit = go to "https://www.google.co.uk"
}
