package uk.gov.hmrc.scalatest.integration

import org.scalatest.concurrent.Eventually
import org.scalatest.{FeatureSpec, GivenWhenThen, Matchers}
import org.scalatestplus.play.OneServerPerSuite

trait IntegrationSpec
  extends FeatureSpec
    with GivenWhenThen
    with OneServerPerSuite
    with Eventually
    with Matchers
    {

}
