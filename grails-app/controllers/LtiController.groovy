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

import net.oauth.OAuth
import com.ambasadoro.AmbasadoroService

class LtiController {
    LtiService ltiService
	AmbasadoroService ambasadoroService

	IEngineFactory ltiEngineFactory = EngineFactory.getInstance()
	
    def index = { 
        log.debug "###############index###############"
        toolService.logParameters(params)
    }

    def tool = { 
        log.debug "###############tool##############"
        toolService.logParameters(params)
    }

    def tool_ui = { 
        log.debug "###############toolUI##############"
        toolService.logParameters(params)
    }
    
    def tool_cartridge = {
        log.debug "###############toolCartridge###############"
        toolService.logParameters(params)
		
		try {
			log.debug "  - Look for the corresponding Ambasadoro instance"
			Ambasadoro ambasadoro = ltiService.getAmbasadoroInstance(params)
			Object engineClass = ltiEngineFactory.getEngineClass(ambasadoro)
			render(text: ambasadoroService.getCartridgeXML(ambasadoro, engineClass), contentType: "text/xml", encoding: "UTF-8")
		} catch(Exception e) {
			log.debug "  - Exception: " + e.getMessage()
		}
		
    }        
}
