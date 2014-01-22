

import java.util.Map;

import org.apache.log4j.Logger;

import com.ambasadoro.Ambasadoro;
import com.ambasadoro.engine.IEngine;
import com.ambasadoro.engine.IEngineFactory;
import com.ambasadoro.engine.tp.bigbluebutton.BigBlueButtonEngine;

public class EngineFactory implements IEngineFactory {

    private static final Logger log = Logger.getLogger(EngineFactory.class);

    private static final EngineFactory INSTANCE = new EngineFactory();

    private EngineFactory() {}

    public static EngineFactory getInstance() {
        return INSTANCE;
    }

    public IEngine createEngine(Ambasadoro ambasadoro, Map<String, String> params, String endpoint) throws Exception {
        IEngine toolProviderEngine = null;
        String toolCode = ambasadoro.getTpVendorCode();
        try {
            toolProviderEngine = new BigBlueButtonEngine(ambasadoro, params, endpoint);
        } catch ( Exception e ){
            throw e;
        }
        
        return toolProviderEngine;
    }
    
    public Object getEngineClass(Ambasadoro ambasadoro) throws Exception {
        Object engineClass = null;
        String toolCode = ambasadoro.getTpVendorCode();
        try {
            engineClass = BigBlueButtonEngine.class;
        } catch ( Exception e ){
            log.debug("Exception: " + e.getLocalizedMessage());
            throw e;
        }
        
        return engineClass;
    }
}
