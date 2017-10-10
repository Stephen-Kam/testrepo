package uk.gov.hmrc.scalatest.util

import java.awt.Robot

import org.openqa.selenium.support.ui.{ExpectedCondition, ExpectedConditions, WebDriverWait}
import org.openqa.selenium.{By, WebDriver, WebElement}
import org.scalatest.concurrent.{Eventually, IntegrationPatience}
import org.scalatest.selenium.WebBrowser
import org.scalatest.{Assertions, Matchers}
import uk.gov.hmrc.scalatest.pages.BasePage


trait NavigationSugar extends WebBrowser
  with Eventually
  with Assertions
  with Matchers
  with IntegrationPatience {

  val robot = new Robot()

  def on(page: BasePage)(implicit webDriver: WebDriver) = {
    val wait = new WebDriverWait(webDriver, 5)
    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")))
    assert(page.isCurrentPage, s"Page was not loaded: ${page.currentUrl}")
  }
}