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

package cnr.sync.dto.v3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import helpers.JodaConverters;
import injection.StaticInject;
import java.time.LocalDate;
import javax.inject.Inject;
import lombok.Data;
import lombok.val;
import models.absences.Absence;
import org.modelmapper.ModelMapper;

/**
 * DTO per l'esportazione via REST delle informazioni 
 * principali di un'assenza.
 *
 * @since versione 3 dell'API REST
 * @author Cristian Lucchesi
 *
 */
@StaticInject
@Data
public class AbsenceShowTerseDto {

  private Long id;
  private LocalDate date;
  private String code;
  private Integer justifiedTime;
  private String justifiedType;
  private String note;
  
  @JsonIgnore
  @Inject
  static ModelMapper modelMapper;
  
  /**
   * Nuova instanza di un AbsenceShowTerseDto contenente i valori 
   * dell'oggetto absence passato.
   */
  public static AbsenceShowTerseDto build(Absence absence) {
    val absenceDto = modelMapper.map(absence, AbsenceShowTerseDto.class);
    absenceDto.setJustifiedTime(absence.justifiedTime());
    absenceDto.setJustifiedType(absence.justifiedType.name.name());
    absenceDto.setDate(JodaConverters.jodaToJavaLocalDate(absence.getAbsenceDate()));
    return absenceDto;
  }
}