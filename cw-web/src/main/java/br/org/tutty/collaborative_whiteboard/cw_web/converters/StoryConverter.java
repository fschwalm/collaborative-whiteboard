package br.org.tutty.collaborative_whiteboard.cw_web.converters;

import backlog_manager.entities.Story;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 * Created by drferreira on 30/06/15.
 */
@FacesConverter(value = "storyConverterToPickList")
public class StoryConverter implements Converter {
    @Override
    public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
        Object ret = null;
        if (arg1 instanceof PickList) {
            Object dualList = ((PickList) arg1).getValue();
            DualListModel dl = (DualListModel) dualList;
            for (Object obj : dl.getSource()) {
                String code = ((Story) obj).getCode();
                if (arg2.equals(code)) {
                    ret = obj;
                    break;
                }
            }
            if (ret == null)
                for (Object o : dl.getTarget()) {
                    String code = ((Story) o).getCode();
                    if (arg2.equals(code)) {
                        ret = o;
                        break;
                    }
                }
        }
        return ret;
    }

    @Override
    public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
        String str = "";
        if (arg2 instanceof Story) {
            str = ((Story) arg2).getCode();
        }
        return str;
    }
}
