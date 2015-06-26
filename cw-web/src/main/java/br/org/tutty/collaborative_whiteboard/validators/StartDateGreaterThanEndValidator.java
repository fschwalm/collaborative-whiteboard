package br.org.tutty.collaborative_whiteboard.validators;

import br.org.tutty.collaborative_whiteboard.cw_web.utils.FacesMessageUtil;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import java.io.IOException;
import java.util.Date;

/**
 * Created by drferreira on 26/06/15.
 */
@FacesValidator("startDateGreaterThanEndValidator")
public class StartDateGreaterThanEndValidator implements Validator{
    private static final String RESOURCE_NAME = "/properties/validation_pt_BR.properties";
    private static final String MESSAGE_KEY = "date.start_greater_than_end";
    private static final String MESSAGE_DETAIL_KEY = "date.start_greater_than_end.detail";

    @Inject
    protected FacesMessageUtil facesMessageUtil;

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if (value == null) {
            return;
        }

        Object startDateValue = component.getAttributes().get("startDate");
        if (startDateValue == null) {
            return;
        }

        Date startDate = (Date) startDateValue;
        Date endDate = (Date) value;
        if (endDate.before(startDate)) {
            try{
                facesMessageUtil.setResourceName(RESOURCE_NAME);
                throw new ValidatorException(facesMessageUtil.getFacesMessage(FacesMessage.SEVERITY_ERROR, MESSAGE_KEY , MESSAGE_DETAIL_KEY));
            }catch (IOException e){
                e.printStackTrace();
            }

        }
    }
}
