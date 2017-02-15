import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import mapper.PersonMapper;
import model.PersonEntity;
import model.PersonJSF;
import services.PersonService;

import java.util.List;

/**
 * This UI is the application entry point. A UI may either represent a browser window
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@CDIUI("testcdi")
public class MyUI extends UI {

    @Inject
    PersonService personService;
    @Inject
    PersonMapper personMapper;

    @Override
    protected void init(VaadinRequest vaadinRequest) {

        List<PersonJSF> persons = personMapper.fromDatabaseModel(personService.findAll());

        final VerticalLayout layout = new VerticalLayout();

        final TextField name = new TextField();
        name.setValue(persons.get(0).getName());
        name.setCaption("Type your name here:");

        Button button = new Button("Click Me");
        button.addClickListener( e -> {
            PersonJSF fP = persons.get(0);
            fP.setName(name.getValue());
            personService.update(persons.get(0));
            layout.addComponent(new Label("Thanks " + name.getValue()
                    + ", it works!"));
        });

        layout.addComponents(name, button);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }
}
