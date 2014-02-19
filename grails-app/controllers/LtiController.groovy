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
        try {
            log.debug "  - Look for the corresponding Ambasadoro instance"
            Ambasadoro ambasadoro = ltiService.getAmbasadoroInstance()
            def endpoint = ltiService.getEndpoint(params.get("controller"), "tool")
            String endpoint_url = (request.isSecure()?"https":"http") + endpoint.get("server") + "/" + endpoint.get("app") + "/" + endpoint.get("controller") + "/" + endpoint.get("action") + ( params.get("id")? "/" + params.get("id"): "") 
/*
            log.debug "  - Initializing ltiEngine"
            ambasadoroService.sanitizePrameters(params)
            IEngine engine = ltiEngineFactory.createEngine(ambasadoro, params, endpoint)
            log.debug "  - Initialized ltiEngine. code [" + engine.getToolVendorCode() + "]"
            
            log.debug "  - Parameters after override"
            ambasadoroService.logParameters(engine.getParameters())
            
            LtiLaunch ltiLaunch = ambasadoroService.saveLtiLaunch(engine)

            if( engine.hasExtraParameters() ) {
                if ( !ambasadoroService.hasExtraParameterSet(ltiLaunch) ){
                    session["parameters"] = engine.getParameters()
                    def isLearner = true
                    //if( !LTIRoles.isLearner(engine.getParameter("roles"), LTIRoles.EXCLUSIVE) && !LTIRoles.isStudent(engine.getParameter("roles"), LTIRoles.EXCLUSIVE)) {
                    if( !LTIRoles.isLearner(engine.getParameter("roles"), true) && !LTIRoles.isStudent(engine.getParameter("roles"), true)) {
                        ///Present interface for setting up extraParameters
                        log.debug "<<<< Present interface for setting up extraParameters >>>>"
                        isLearner = false
                    } else {
                        ///Present error message telling learners that extraParameters are not set yet
                        log.debug "<<<< Present error message telling learners that extraParameters are not set yet >>>>"
                    }
                    def nonce = TimeStamp.getCurrentTime()
                    session["nonce"] = nonce.toString()
                    session["lti_launch_id"] = ltiLaunch.getId()
                    render( view: "tool_ui", model: ['extraParameters': ambasadoroService.getExtraParameters(engine), 'params': session["parameters"], 'isLearner': isLearner, 'nonce': nonce.toString()] )
                    return
                } else {
                    ambasadoroService.addExtraParameters(engine, ltiLaunch)
                }
            }
            ///Go for the launch
            log.debug "<<<< Go for the launch >>>>"
            def ssoURL = engine.getSSOURL()
            redirect(url: ssoURL)
*/
        } catch(AmbasadoroException e) {
            log.debug "  - AmbasadoroException: " + e.getErrorCode() + ":" + e.getLocalizedMessage()
            render(view: "error", model: ['resultMessageKey': e.getErrorCode(), 'resultMessage': e.getLocalizedMessage()])
        } catch(Exception e) {
            log.debug "  - Exception: " + e.getLocalizedMessage()
            render(view: "error", model: ['resultMessageKey': 'GeneralError', 'resultMessage': e.getLocalizedMessage()])
        }

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
            Ambasadoro ambasadoro = ltiService.getAmbasadoroInstance()
            Object engineClass = ltiEngineFactory.getEngineClass(ambasadoro)
            render(text: ambasadoroService.getCartridgeXML(ambasadoro, engineClass, ltiService.getEndpoint(params.get("controller"), "tool")), contentType: "text/xml", encoding: "UTF-8")
        } catch(Exception e) {
            log.debug "  - Exception: " + e.getMessage()
        }
    }
}
