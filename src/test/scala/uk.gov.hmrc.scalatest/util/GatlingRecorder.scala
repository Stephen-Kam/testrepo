package uk.gov.hmrc.scalatest.util

import net.lightbody.bmp.core.har.{HarEntry, HarRequest}
import org.scalatest._
import play.api.libs.json.{JsArray, JsObject, JsString}

trait GatlingRecorder
  extends FeatureSpec
    with BeforeAndAfterAllConfigMap
    with BeforeAndAfterEachTestData
    with ImplicitWebDriverSugar
    with NavigationSugar
    with Env {

  override protected def beforeEach(testData: TestData): Unit = proxy.newHar(testData.name)


  val fileExtensionBlacklist = Seq("js", "css", "png", "gif", "htm", "ico")

  override protected def afterEach(testData: TestData): Unit = {
    if (!withHar) return
    import scala.collection.JavaConversions._
    println(JsObject(Seq("scenarioRequests" -> JsArray(proxy.getHar.getLog.getEntries
      .filter(_.getRequest.getHeaders.exists(_.getValue.contains("text/html")))
      .filter(_.getRequest.getUrl.contains(baseUrl))
      .filterNot(harEntry => {
        val url: String = harEntry.getRequest.getUrl
        fileExtensionBlacklist.contains(url.substring(url.lastIndexOf(".") + 1))
      })
      .filterNot(_.getRequest.getUrl.contains("gg/sign-in"))
      .map { entry: HarEntry =>
        val request: HarRequest = entry.getRequest
        val url: String = request.getUrl
        val parsedUrl = if (url.contains("return")) {
          s"/return${url.split("return")(1)}"
        }
        else {
          url.substring(baseUrl.length)
        }
        JsObject(Seq(
          "method" -> JsString(request.getMethod),
          "elapsed" -> JsString(entry.getTime.toString),
          "url" -> JsString(parsedUrl),
          "headers" -> JsObject(request.getHeaders
            .filterNot(_.getName.contains("Cookie"))
            .map(header => header.getName -> JsString(header.getValue)))
        ).++(if (request.getMethod == "POST") {
          Seq("body" -> JsObject(request.getPostData.getParams
            .filterNot(_.getName.contains("version"))
            .map(param => param.getName -> JsString(param.getValue))))
        } else Seq.empty))
      }))))
  }
}
