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

package manager.recaps.personstamping;

import com.google.common.collect.Lists;
import java.util.List;
import manager.cache.StampTypeManager;
import models.StampModificationType;
import models.StampModificationTypeCode;
import models.Stamping;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * Oggetto che modella la singola timbratura nelle viste personStamping e stampings.
 *
 * @author Alessandro Martelli
 */
public class StampingTemplate {

  public Stamping stamping;
  public Long stampingId;
  public String colour;
  public int pairId;
  public String pairPosition;            //left center right none
  public LocalDateTime date;

  public String way;
  public String hour = "";

  List<StampModificationType> stampModificationTypes = Lists.newArrayList();

  public boolean valid;

  private static final String STAMPING_FORMAT = "HH:mm";

  /**
   * Costruttore per la StampingTemplate.
   *
   * @param stampTypeManager injected
   * @param stamping         la timbratura formato BaseModel.
   * @param position         la posizione della timbratura all'interno della sua coppia [left,
   *                         center, right, none] (none per nessuna coppia)
   */
  public StampingTemplate(StampTypeManager stampTypeManager, Stamping stamping, String position) {

    this.stamping = stamping;
    this.stampingId = stamping.id;
    this.pairId = stamping.pairId;

    this.pairPosition = position;

    //stamping nulle o exiting now non sono visualizzate
    if (stamping.date == null || stamping.exitingNow) {
      this.valid = true;
      setColor(stamping);
      return;
    }

    this.date = stamping.date;

    this.way = stamping.way.getDescription();

    this.hour = stamping.date.toString(STAMPING_FORMAT);

    //timbratura modificata dall'amministatore
    if (stamping.markedByAdmin) {
      stampModificationTypes.add(stampTypeManager.getStampMofificationType(
          StampModificationTypeCode.MARKED_BY_ADMIN));
    }

    //timbratura modificata dal dipendente
    if (stamping.markedByEmployee) {
      stampModificationTypes.add(stampTypeManager.getStampMofificationType(
          StampModificationTypeCode.MARKED_BY_EMPLOYEE));
    }

    //timbratura di mezzanotte
    if (stamping.stampModificationType != null) {
      stampModificationTypes.add(stamping.stampModificationType);
    }

    //timbratura valida (colore cella)
    LocalDate today = new LocalDate();
    LocalDate stampingDate = new LocalDate(this.date.getYear(),
        this.date.getMonthOfYear(), this.date.getDayOfMonth());
    if (today.isEqual(stampingDate)) {
      this.valid = true;
    } else {
      this.valid = stamping.isValid();
    }

    setColor(stamping);
  }

  protected void setColor(Stamping stamping) {
    this.colour = stamping.way.description;
    if (!this.valid) {
      this.colour = "warn";
    }
  }

  /**
   * Se stampare il popover sulla stampingTemplate.
   */
  public boolean showPopover() {
    if (!stampModificationTypes.isEmpty() || stamping.stampType != null) {
      return true;
    }
    return false;
  }

}
