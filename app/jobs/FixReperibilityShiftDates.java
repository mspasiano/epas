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

import com.google.common.base.Optional;
import dao.CompetenceCodeDao;
import dao.PersonShiftDayDao;
import java.util.List;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import models.CompetenceCode;
import models.PersonCompetenceCodes;
import models.PersonReperibility;
import models.PersonShift;
import org.joda.time.LocalDate;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@Slf4j
@OnApplicationStart(async = true)
public class FixReperibilityShiftDates extends Job<Void> {
  
  @Inject
  static PersonShiftDayDao dao;
  @Inject
  static CompetenceCodeDao codeDao;
  
  static final String T1 = "T1";
  static final String Fer = "207";
  
  
  @Override
  public void doJob() {
    
    //in modo da inibire l'esecuzione dei job in base alla configurazione
    if (!"true".equals(Play.configuration.getProperty(Bootstrap.JOBS_CONF))) {
      log.info("{} interrotto. Disattivato dalla configurazione.", getClass().getName());
      return;
    }
    
    CompetenceCode t1 = codeDao.getCompetenceCodeByCode(T1);
    CompetenceCode fer = codeDao.getCompetenceCodeByCode(Fer);
    
    List<PersonShift> list = PersonShift.findAll();
    for (PersonShift ps : list) {
      Optional<PersonCompetenceCodes> pcc = 
          codeDao.getByPersonAndCodeAndDate(ps.person, t1, LocalDate.now());
      if (pcc.isPresent()) {
        ps.beginDate = pcc.get().beginDate;
        ps.endDate = pcc.get().endDate;
        ps.disabled = false;
        
      } else {
        ps.disabled = true;
        ps.beginDate = LocalDate.now().minusMonths(1).dayOfMonth().withMinimumValue();
        ps.endDate = LocalDate.now().dayOfMonth().withMaximumValue();
      }
      log.info("Aggiornata situazione date di abilitazione ai turni di {}", ps.person.fullName());
      ps.save();
    }
    
    List<PersonReperibility> reperibilities = PersonReperibility.findAll();
    for (PersonReperibility pr : reperibilities) {
      Optional<PersonCompetenceCodes> pcc = 
          codeDao.getByPersonAndCodeAndDate(pr.person, fer, LocalDate.now());
      if (pcc.isPresent() && pr.startDate == null) {
        pr.startDate = pcc.get().beginDate;
      }
      log.info("Aggiornata situazione date di abilitazione alla reperibilità di {}", 
          pr.person.fullName());
      pr.save();
    }
        
  }

}
