/*
 * Copyright (C) 2021  Consiglio Nazionale delle Ricerche
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Affero General Public License as
 *     published by the Free Software Foundation, either version 3 of the
 *     License, or (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Affero General Public License for more details.
 *
 *     You should have received a copy of the GNU Affero General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package controllers;

import com.google.common.collect.Maps;
import java.util.Map;
import models.Office;
import models.Person;
import play.mvc.Controller;
import play.mvc.Router;
import play.mvc.With;

@With(Resecure.class)
public class SwitchTemplate extends Controller {

  public static final String USERNAME_SESSION_KEY = "username";

  private static void executeAction(String action) {

    Integer year = Integer.parseInt(session.get("yearSelected"));
    Integer month = Integer.parseInt(session.get("monthSelected"));
    Integer day = Integer.parseInt(session.get("daySelected"));
    Long personId = Long.parseLong(session.get("personSelected"));
    Long officeId = Long.parseLong(session.get("officeSelected"));

    Map<String, Object> args = Maps.newHashMap();
    args.put("year", year);
    args.put("month", month);
    args.put("day", day);
    args.put("personId", personId);
    args.put("officeId", officeId);


    if (action.equals("Certifications.processAll")) {
      Certifications.certifications(officeId, year, month); //Voluto. Lo switch non processa.
    }
    
    if (action.equals("Certifications.emptyCertifications")) {
      Certifications.certifications(officeId, year, month); //Voluto. Lo switch non svuota.
    }

    redirect(Router.reverse(action, args).url);
  }

  /**
   * Aggiorna in sessione il giorno selezionato nel menu.
   */
  public static void updateDay(
      Integer day, final String actionSelected) throws Throwable {
    
    if (actionSelected == null || session.isEmpty()) {
      flash.error("La sessione è scaduta. Effettuare nuovamente login.");
      Secure.login();
    }
    
    session.put("daySelected", day);

    executeAction(actionSelected);

  }

  /**
   * Aggiorna in sessione la persona selezionata nel menu.
   */
  public static void updatePerson(
      Person person, final String actionSelected) throws Throwable {

    if (actionSelected == null || session.isEmpty()) {
      flash.error("La sessione è scaduta. Effettuare nuovamente login.");
      Secure.login();
    }

    if (person == null) {
      Application.index();
    }

    session.put("personSelected", person.id);

    executeAction(actionSelected);
  }

  /**
   * Aggiorna in sessione l'ufficio selezionato nel menu.
   */
  public static void updateOffice(
      Office office, final String actionSelected) throws Throwable {

    if (actionSelected == null || session.isEmpty()) {
      flash.error("La sessione è scaduta. Effettuare nuovamente login.");
      Secure.login();
    }

    if (office == null) {
      Application.index();
    }

    session.put("officeSelected", office.id);
    executeAction(actionSelected);
  }

}


