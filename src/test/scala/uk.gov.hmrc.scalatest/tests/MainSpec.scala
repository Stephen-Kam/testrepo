package uk.gov.hmrc.scalatest.tests

import uk.gov.hmrc.scalatest.pages.{BasePage, GooglePage}
import uk.gov.hmrc.scalatest.tags.RunOnlyInDev


class MainSpec extends BasePage {

  feature("Some feature") {
    scenario("Some scenario", RunOnlyInDev) {
      Given("I am on the Google home page")
      GooglePage.navigateToGoogle()
    }
  }
}
