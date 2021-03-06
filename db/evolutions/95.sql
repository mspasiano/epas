# ---!Ups

alter table person_days add column out_opening integer not null default 0;
-- imposta il valore del lavoro fuori fascia ripetendo il calcolo java
update person_days set out_opening = subquery.case
from (select id, CASE
         WHEN stamping_time IS NULL THEN 0 
         WHEN time_at_work IS NULL THEN 0
         WHEN stamping_time - time_at_work < 0 THEN 0  -- presenze automatiche
         ELSE stamping_time 
              - time_at_work 
              - coalesce(decurted, 0) 
              - coalesce(justified_time_meal, 0) 
              - coalesce(justified_time_no_meal, 0) 
         END from person_days) AS subquery
where person_days.id = subquery.id ;

-- Query che popola il campo out_opening dell'ultima revisione pari al valore corrente in person_days
alter table person_days_history add column out_opening integer not null default 0;
update person_days_history set out_opening = subquery.out_to_history 
from
  ( select distinct on (pd.id) 
      pd.id as pdid, pdh._revision as pdhr, 
      pdh._revision_type, pd.out_opening as out_to_history, pdh.out_opening, pdh.id 
    from person_days pd left outer join person_days_history pdh on pd.id = pdh.id 
    order by pd.id, pdh._revision desc ) as subquery
where person_days_history.id = subquery.pdid and person_days_history._revision = subquery.pdhr;


alter table person_days add column approved_out_opening integer not null default 0;
alter table person_days_history add column approved_out_opening integer not null default 0;

alter table person_days add column on_holiday integer not null default 0;
alter table person_days_history add column on_holiday integer not null default 0;
alter table person_days add column approved_on_holiday integer not null default 0;
alter table person_days_history add column approved_on_holiday integer not null default 0;

alter table person_days rename column decurted to decurted_meal;
alter table person_days_history rename column decurted to decurted_meal;

-- Riparo l'inconsistenza (prima del 2015) sull'approvazione lavoro festivo 
-- nei giorni non festivi. Il parametro settato a true in quel caso è inutile.
update person_days set accepted_holiday_working_time = false where is_holiday = false;
update person_days_history set accepted_holiday_working_time = false where is_holiday = false;

-- Nei giorni festivi copio il valore di time_at_work nel field on_holiday
update person_days set on_holiday = time_at_work where is_holiday = true;
update person_days_history set on_holiday = time_at_work where is_holiday = true;

-- Nei giorni festivi non approvati il time_at_work è zero 
update person_days set time_at_work = 0 where is_holiday = true and accepted_holiday_working_time = false;
update person_days_history set time_at_work = 0 where is_holiday = true and accepted_holiday_working_time = false;

-- Nei giorni festivi approvati on_holiday va trasferito in approved_on_holiday
-- (Perchè al momento di applicare l'evoluzione o si approva tutto o non si approva niente).
update person_days set approved_on_holiday = on_holiday where is_holiday = true and accepted_holiday_working_time = true;
update person_days_history set approved_on_holiday = on_holiday where is_holiday = true and accepted_holiday_working_time = true;

-- Butto via accepted_holiday_working_time perchè a questo punto l'informazione 
-- è in approved_on_holiday (accettato tutto o in parte)
alter table person_days drop column accepted_holiday_working_time;
alter table person_days_history drop column accepted_holiday_working_time;


# ---!Downs

alter table person_days add column accepted_holiday_working_time boolean;
alter table person_days_history add column accepted_holiday_working_time boolean;

update person_days set accepted_holiday_working_time = approved_on_holiday > 0;
update person_days_history set accepted_holiday_working_time = approved_on_holiday > 0;

alter table person_days rename column decurted_meal to decurted;
alter table person_days_history rename column decurted_meal to decurted;

alter table person_days drop column on_holiday;
alter table person_days_history drop column on_holiday;
alter table person_days drop column approved_on_holiday;
alter table person_days_history drop column approved_on_holiday;
alter table person_days drop column out_opening;
alter table person_days_history drop column out_opening;
alter table person_days drop column approved_out_opening;
alter table person_days_history drop column approved_out_opening;