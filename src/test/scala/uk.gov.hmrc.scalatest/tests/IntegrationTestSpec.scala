package uk.gov.hmrc.scalatest.tests

import uk.gov.hmrc.scalatest.integration.{IntegrationActions, IntegrationSpec}
import uk.gov.hmrc.scalatest.pages.{BasePage, GooglePage}
import uk.gov.hmrc.scalatest.tags.RunOnlyInDev
import uk.gov.hmrc.scalatest.util.Env


class IntegrationTestSpec extends IntegrationSpec with IntegrationActions with Env {

  feature("An example feature") {
    scenario("An example scenario", RunOnlyInDev) {
      When("I visit the Google site")
      val response = getGoogle

      Then("The response status should be 200")
      response.status shouldBe 200
    }
  }
}