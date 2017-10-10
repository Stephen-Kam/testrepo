package uk.gov.hmrc.scalatest.util

import net.lightbody.bmp.client.ClientUtil
import net.lightbody.bmp.proxy.CaptureType
import net.lightbody.bmp.{BrowserMobProxy, BrowserMobProxyServer}
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.{CapabilityType, DesiredCapabilities}
import org.openqa.selenium.{Proxy, WebDriver}

trait ImplicitWebDriverSugar {

  val withHar: Boolean = Option(System.getProperty("withHar")).isDefined

  //    val withHar = true

  lazy val driver: WebDriver = SingletonDriver.getInstance()

  val proxy: BrowserMobProxy = new BrowserMobProxyServer()
  lazy val driverWithProxy: WebDriver = {
    proxy.start()
    val seleniumProxy: Proxy = ClientUtil.createSeleniumProxy(proxy)
    val capabilities: DesiredCapabilities = DesiredCapabilities.chrome()
    capabilities.setCapability(CapabilityType.PROXY, seleniumProxy)
    val driver: WebDriver = new ChromeDriver(capabilities)
    proxy.enableHarCaptureTypes(CaptureType.REQUEST_CONTENT, CaptureType.REQUEST_HEADERS, CaptureType.RESPONSE_HEADERS)
    driver
  }

  implicit val webDriver: WebDriver = if (withHar) driverWithProxy else driver

}