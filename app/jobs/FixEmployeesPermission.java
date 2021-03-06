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

package jobs;

import injection.StaticInject;
import java.util.List;
import javax.inject.Inject;
import manager.OfficeManager;
import models.Person;
import models.Role;
import models.UsersRolesOffices;

@StaticInject
public class FixEmployeesPermission {

  @Inject
  static OfficeManager officeManager;

  /**
   * Esecuzione del job.
   */
  public static void doJob() {

    /* Procedura un pò esagerata per la riassociazione dei ruoli e permessi corretti
     *
     * Crea una copia di tutte le triple degli userRoleOffice per poi
     * cancellarli tutti dal db in modo da poter cancellare i permessi
     * e i ruoli.
     * Poi reimporta dal file yaml i permessi e i ruoli corretti e ricrea tutti gli
     * userRoleOffice in base alle informazioni precedentemente salvate
     *
     */

    // Sistema i permessi per tutte le persone
    List<Person> persons = Person.findAll();
    Role employeeRole = Role.find("byName", Role.EMPLOYEE).first();
    for (Person p : persons) {

      boolean exist = false;
      //Cerco se esiste già e controllo che sia relativo all'office di appartentenza

      for (UsersRolesOffices uro : p.user.usersRolesOffices) {
        //Rimuovo ruolo role se non appartiene più all'office
        if (uro.role.name.equals(employeeRole.name)) {
          if (uro.office.codeId.equals(p.office.codeId)) {
            exist = true;
          } else {
            uro.delete();
          }
        }
      }

      if (!exist) {
        officeManager.setUro(p.user, p.office, employeeRole);
      }
    }
  }

}
