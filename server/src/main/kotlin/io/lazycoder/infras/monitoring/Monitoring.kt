package io.lazycoder.infras.monitoring

import dev.hayden.KHealth
import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.callid.CallId
import io.ktor.server.plugins.callid.callIdMdc
import io.ktor.server.plugins.calllogging.CallLogging

fun Application.configureMonitoring() {
    install(CallId) {
        header(HttpHeaders.XRequestId)
        verify { callId: String -> callId.isNotEmpty() }
    }
    install(KHealth)
//    install(DropwizardMetrics) {
//        Slf4jReporter.forRegistry(registry).outputTo(this@configureMonitoring.log)
//            .convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build()
//            .start(10, TimeUnit.SECONDS)
//    }
    install(CallLogging) {
        callIdMdc("call-id")
    }
}