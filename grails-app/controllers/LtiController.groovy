import java.util.Properties;

import net.oauth.OAuth

import net.oauth.OAuthMessage
import net.oauth.signature.OAuthSignatureMethod
import net.oauth.signature.HMAC_SHA1

import com.ambasadoro.Ambasadoro
import com.ambasadoro.AmbasadoroService
import EngineFactory
import com.ambasadoro.engine.IEngine
import com.ambasadoro.engine.IEngineFactory
import com.ambasadoro.engine.VendorCodes
import com.ambasadoro.exceptions.AmbasadoroException

import org.lti.api.LTIRoles

class LtiController {
    LtiService ltiService
    AmbasadoroService ambasadoroService

    IEngineFactory ltiEngineFactory = EngineFactory.getInstance()

    def index = {
        log.debug "###############index###############"
        ambasadoroService.logParameters(params)
    }

    def tool = {
        log.debug "###############tool##############"
        ambasadoroService.logParameters(params)
    }

    def tool_ui = {
        log.debug "###############toolUI##############"
        ambasadoroService.logParameters(params)
    }

    def tool_cartridge = {
        log.debug "###############toolCartridge###############"
        ambasadoroService.logParameters(params)

        try {
            log.debug "  - Look for the corresponding Ambasadoro instance"
            Ambasadoro ambasadoro = ltiService.getAmbasadoroInstance(params)
            ambasadoroService.setEndpoint(ltiService.getEndpoint(params))
            Object engineClass = ltiEngineFactory.getEngineClass(ambasadoro)
            render(text: ambasadoroService.getCartridgeXML(ambasadoro, engineClass), contentType: "text/xml", encoding: "UTF-8")
        } catch(Exception e) {
            log.debug "  - Exception: " + e.getMessage()
        }
    }
}
