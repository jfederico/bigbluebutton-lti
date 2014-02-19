import com.ambasadoro.Ambasadoro

class LtiService {
    def ltiEndPointServer
    def ltiEndPointApp
    def ltiEndPointController
    def ltiEndPointAction
    def ltiKey
    def ltiSecret

    def tpTitle
    def tpDescription
    def tpVendorCode
    def tpEndpoint
    def tpKey
    def tpSecret
    def tpMeta

    def tcVendorCode
    def tcMeta

    def ambasadoro = null

    def logParameters(params) {
        log.debug "----------------------------------"
        for( param in params ) log.debug "${param.getKey()}=${param.getValue()}"
        log.debug "----------------------------------"
    }

    def getAmbasadoroInstance(params) {
        if ( !ambasadoro ){
            ambasadoro = new Ambasadoro()
            ambasadoro.ltiKey = ltiKey
            ambasadoro.ltiSecret = ltiSecret

            ambasadoro.tpTitle = tpTitle
            ambasadoro.tpDescription = tpDescription
            ambasadoro.tpVendorCode = tpVendorCode
            ambasadoro.tpEndpoint = !tpEndpoint?"http://test-install.blindsidenetworks.com/bigbluebutton/": tpEndpoint
            ambasadoro.tpKey = !tpKey? "": tpKey
            ambasadoro.tpSecret = !tpSecret? "8cd8ef52e8e101574e400365b55e11a6": tpSecret
            ambasadoro.tpMeta = tpMeta

            ambasadoro.tcVendorCode = tcVendorCode
            ambasadoro.tcMeta = tcMeta
        }
        return ambasadoro
    }

    def getEndpoint(params){
        def endpoint = [:]

        endpoint.put("server", ltiEndPointServer)
        endpoint.put("app", ltiEndPointApp)
        endpoint.put("controller", ltiEndPointController)
        endpoint.put("action", ltiEndPointAction)

        return endpoint
    }
}
