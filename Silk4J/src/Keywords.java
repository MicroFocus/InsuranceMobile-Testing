
/**
 * Copyright 2013 Micro Focus. All rights reserved. Portions Copyright 1992-2009 Borland Software Corporation (a Micro
 * Focus company).
 */

import org.junit.Assert;

import com.borland.silk.keyworddriven.annotations.Keyword;
import com.borland.silktest.jtf.Desktop;
import com.borland.silktest.jtf.MobileBaseState;
import com.borland.silktest.jtf.common.types.FindOptions;
import com.microfocus.silktest.jtf.mobile.MobileDevice;
import com.microfocus.silktest.jtf.mobile.MobileObject;
import com.microfocus.silktest.jtf.mobile.MobileTextField;
import com.microfocus.silktest.jtf.mobile.common.types.MobileOperatingSystem;

public class Keywords {

  private Desktop desktop = new Desktop();

  @Keyword(value = "Start application", isBaseState = true)
  public void start_application() {
    MobileBaseState baseState = new MobileBaseState();
    baseState.execute(desktop);
  }

  @Keyword("Login default user")
  public void login_default_user() {
    login("john.smith@gmail.com", "john");
  }

  @Keyword("Login")
  public void login(String email, String password) {
    this.<MobileTextField> find("Email").setText(email);
    this.<MobileTextField> find("Password").setText(password);
    find("Login").click();
  }

  @Keyword("Logout")
  public void logout() {
    find("Menu").click();
    find("Logout").click();
  }

  @Keyword("Go to agent lookup")
  public void go_to_Agent_Lookup() {
    find("Menu").click();
    find("Agent Lookup").click();
  }

  @Keyword("Search for all agents")
  public void search_for_all_Agents() {
    find("Search by Location").click();
  }

  @Keyword("Search agents by zip code")
  public void search_Agents_by_Zip_Code(String zipCode) {
    this.<MobileTextField> find("Zip Code").setText(zipCode);
    find("Search by Location").click();
  }

  @Keyword("Verify username")
  public void verify_Username(String username) {

  }

  @Keyword("Search agents by name")
  public void search_Agents_by_Name(String name) {
    this.<MobileTextField> find("Agent Search Name").setText(name);
    this.<MobileObject> find("Search by Name").click();
  }

  // @Keyword("Verify 6 agents found")
  // public void verify_6_agents_found() {
  // verify_number_of_agents_found(6);
  // }

  @Keyword("Verify number of agents found")
  public void verify_number_of_agents_found(int agents) {
    MobileObject agentsFound = desktop.find("//MobileObject[@Text='" + agents + " agents found']",
        new FindOptions(false, 1000));
    Assert.assertNotNull(agentsFound);

    go_Home();
  }

  @Keyword("Verify agent exists")
  public void verify_agent_exists(String name) {
    MobileObject agentName = find("Agent Name");
    Assert.assertEquals(name, agentName.getText());
  }

  @Keyword("Go home")
  public void go_Home() {
    find("Back").click();
    if (isAndroid()) {
      find("Back").click();
    }
  }

  private <T extends MobileObject> T find(String objectMapID) {
    if (isAndroid()) {
      return desktop.<T> find("Device." + objectMapID + " (Android)");
    }
    else {
      return desktop.<T> find("Device." + objectMapID + " (iOS)");
    }
  }

  private boolean isAndroid() {
    MobileDevice device = desktop.<MobileDevice> find("//MobileDevice");
    if (device.getOperatingSystem() == MobileOperatingSystem.ANDROID) {
      return true;
    }
    return false;
  }

}