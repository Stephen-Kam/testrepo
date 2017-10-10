package uk.gov.hmrc.scalatest.util

import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.proxy.CaptureType
import net.lightbody.bmp.{BrowserMobProxy, BrowserMobProxyServer}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.{CapabilityType, DesiredCapabilities}
import org.openqa.selenium.{Proxy, WebDriver}

trait Env {

  val environmentProperty = Option(System.getProperty("environment", "local"))

  val baseUrl: String = environmentProperty match {
    case Some("local") => ""
    case Some("dev") => "https://www-dev.tax.service.gov.uk"
    case Some("qa") => "https://www-qa.tax.service.gov.uk"
    case Some("staging") => "https://www-staging.tax.service.gov.uk"
    case _ => throw new IllegalArgumentException(s"Environment '$environmentProperty' not known")
  }
}