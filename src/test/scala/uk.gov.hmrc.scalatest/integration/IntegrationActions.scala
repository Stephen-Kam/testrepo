package uk.gov.hmrc.scalatest.integration

import play.api.Play.current
import play.api.libs.ws.{WS, WSResponse}


trait IntegrationActions extends ActionsSupport {

  def getGoogle: WSResponse =
    WS
      .url("http://www.google.com")
      .get()
      .futureValue

}

