package little.ant.weixin.validator;

import org.apache.log4j.Logger;

import com.jfinal.core.Controller;
import com.jfinal.validate.Validator;

public class KeywordValidator extends Validator {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(KeywordValidator.class);
	
	protected void validate(Controller controller) {
		
	}
	
	protected void handleError(Controller controller) {
		controller.keepModel(KeywordValidator.class);
		
		String actionKey = getActionKey();
		if (actionKey.equals("/jf/wx/keyword/save")){
			
		} else if (actionKey.equals("/jf/wx/keyword/update")){
			
		}
			
	}
}
