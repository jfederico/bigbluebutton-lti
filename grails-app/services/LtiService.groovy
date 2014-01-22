import com.ambasadoro.Ambasadoro

class LtiService {
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
			ambasadoro.tpSecret = tpSecret
			ambasadoro.tpMeta = tpMeta
			ambasadoro.tcVendorCode = tcVendorCode
			ambasadoro.tcMeta = tcMeta
		}
		return ambasadoro
	}

}
