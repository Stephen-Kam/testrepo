package uk.gov.hmrc.scalatest.pages

import java.io.IOException
import java.net.URI
import java.util

import org.apache.http.NameValuePair
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.HttpPut
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.message.BasicNameValuePair
import org.openqa.selenium.{Keys, WebElement}
import org.scalatest._
import org.scalatest.concurrent.{Eventually, PatienceConfiguration}
import org.scalatest.selenium.WebBrowser
import org.scalatest.time.{Millis, Seconds, Span}
import uk.gov.hmrc.scalatest.util.{Env, ImplicitWebDriverSugar, NavigationSugar, SingletonDriver}


trait BasePage extends FeatureSpec
  with Matchers
  with NavigationSugar
  with WebBrowser
  with Eventually
  with PatienceConfiguration
  with Assertions
  with ImplicitWebDriverSugar
  with Env
  with GivenWhenThen
  with org.scalatest.selenium.Page {

  override val url = ""

  override implicit val patienceConfig: PatienceConfig = PatienceConfig(timeout = scaled(Span(5, Seconds)), interval = scaled(Span(500, Millis)))

  def navigateTo(): Unit = go to s"$baseUrl/$url"

  def isCurrentPage: Boolean = false

  def back(): Unit = click on find(xpath(".//*[@class='back-link']")).get

  def buttonNext(): Unit = clickOn("ButtonNext")

  def submit(): Unit = click on find(xpath(".//*[@type='submit' and contains(text(),'Submit')]")).get

  def continue(): Unit = click on find(xpath(".//*[@type='submit' and contains(text(),'Continue')]")).get

  def agreeAndContinue(): Unit = click on find(xpath(".//*[@type='submit' and contains(text(),'Agree and continue')]")).get

  def confirm(): Unit = click on find(xpath(".//*[@type='submit' and contains(text(),'Confirm')]")).get

  def confirmPayment(): Unit = click on find(xpath(".//*[@type='submit' and contains(text(),'Confirm payments')]")).get

  def signIn(): Unit = click on find(xpath(".//*[@type='submit' and contains(text(),'Sign in')]")).get

  def recalculate(): Unit = click on find(xpath(".//*[@type='submit' and contains(text(),'Recalculate')]")).get


  def textField(id: String, value: String): Unit = {
    val elem = find(id)
    if (elem.isDefined) {
      val e = new TextField(elem.get.underlying)
      if (e.isDisplayed) e.value = value
    }
  }

  def numberField(id: String, value: String): Unit = {
    val elem = find(id)
    if (elem.isDefined) {
      val e = new NumberField(elem.get.underlying)
      if (e.isDisplayed) e.value = value
    }
  }

  def pressKeys(value: Keys): Unit = {
    val e: WebElement = webDriver.switchTo.activeElement
    e.sendKeys(value)
  }

  def singleSel(id: String, value: String): Unit = {
    val elem = find(id)
    if (elem.isDefined) {
      val e = new SingleSel(elem.get.underlying)
      if (e.isDisplayed) e.value = value
    }
  }

  def checkHeader(heading: String, text: String): Unit = {
    find(cssSelector(heading)).exists(_.text == text)
  }

  protected def afterAll(configMap: ConfigMap): Unit = if (System.getProperty("browser") == "browserstack") webDriver.quit()


  private def takeScreenShot(testMethodName: String) {
    println(s"$testMethodName : FAILED")
    println(s"Taking screenshot of '$testMethodName'")
    setCaptureDir("target/screenshots")
    try {
      captureTo(testMethodName)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

//  private def markBrowserstackTestAsFailed(test: String) = {
//    val sessionID = SingletonDriver
//    println(sessionID)
//    val uri: URI = new URI(s"https://{browserstackUrl}@www.browserstack.com/automate/sessions/$sessionID.json")
//    println(uri)
//    val putRequest: HttpPut = new HttpPut(uri)
//
//    val nameValuePairs: util.ArrayList[NameValuePair] = new util.ArrayList[NameValuePair]()
//    nameValuePairs.add(new BasicNameValuePair("status", "failed"))
//    nameValuePairs.add(new BasicNameValuePair("reason", test))
//    putRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs))
//
//    HttpClientBuilder.create().build().execute(putRequest)
//  }

  override def withFixture(test: NoArgTest) = {
    super.withFixture(test) match {
      case f: Failed => {
        takeScreenShot(test.name)
//        if (System.getProperty("browser") == "browserstack") markBrowserstackTestAsFailed(test.name)
        f
      }
      case otherOutcome => otherOutcome
    }
  }

  def isSystemError: Boolean = find(cssSelector("h1")).get.text == "System error"

  def hasValidationErrors: Boolean = findAll(cssSelector("div[class=field] > div[class=error]")).exists { error: Element => error.isDisplayed }



}
