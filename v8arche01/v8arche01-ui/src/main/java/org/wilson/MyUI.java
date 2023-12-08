package org.wilson;

import javax.servlet.annotation.WebServlet;

import com.vaadin.ui.TabSheet;
import org.wilson.samples.about.RestaurantInventory;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("org.wilson.MyAppWidgetset")
public class MyUI extends UI {


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("My");
            showMainView();
    }



    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        TabSheet ts = new TabSheet();
        ts.addTab(new RestaurantInventory("Furniture"), "Furniture");
        ts.addTab(new RestaurantInventory("Glassware"), "Glassware");
        ts.addTab(new RestaurantInventory("Cleaning Products"), "Cleaning Products");
        ts.addTab(new RestaurantInventory("Food"), "Food");
        ts.addTab(new RestaurantInventory("Uniforms"), "Uniforms");
        ts.addTab(new RestaurantInventory("Interior Decorations"), "Interior Decorations");
        setContent(ts);
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }


    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
