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

package helpers.validators;

import play.data.validation.Check;

/**
 * Controlla che una stringa non sia composta solo da caratteri
 * che non formano una parola o fraase di senso compiuto.
 *
 * @author Dario Tagliaferri
 *
 */
public class StringIsValid extends Check {

  @Override
  public boolean isSatisfied(Object validatedObject, Object value) {

    if (value == null) {
      return true;
    }    
    if (!(value instanceof String)) {
      return false;
    }
    final String string = (String) value;    

    if (!string.matches("^[A-Za-zÀ-ÖØ-öø-ÿ0-9 _]*$")) {
      setMessage("Inserisci una stringa valida");
      return false;
    }
    if (string.matches("^[0-9 ]+$")) {
      setMessage("Non ha senso inserire solo numeri e spazi in questo campo");
      return false;
    }
    return true;
  }

}
