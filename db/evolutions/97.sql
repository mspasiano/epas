# ---!Ups

ALTER TABLE shift_time_table ADD COLUMN office_id BIGINT;
ALTER TABLE shift_time_table ADD FOREIGN KEY (office_id) REFERENCES office(id);
ALTER TABLE shift_time_table ADD COLUMN start_evening VARCHAR;
ALTER TABLE shift_time_table ADD COLUMN end_evening VARCHAR;
ALTER TABLE shift_time_table ADD COLUMN start_evening_lunch_time VARCHAR;
ALTER TABLE shift_time_table ADD COLUMN end_evening_lunch_time VARCHAR;

ALTER TABLE shift_type ADD COLUMN entrance_tolerance INT;
ALTER TABLE shift_type ADD COLUMN entrance_max_tolerance INT;
ALTER TABLE shift_type ADD COLUMN exit_tolerance INT;
ALTER TABLE shift_type ADD COLUMN exit_max_tolerance INT;
ALTER TABLE shift_type ADD COLUMN break_in_shift INT;
ALTER TABLE shift_type ADD COLUMN break_max_in_shift INT;
ALTER TABLE shift_type ADD COLUMN max_tolerance_allowed INT;

ALTER TABLE shift_type_history ADD COLUMN entrance_tolerance INT;
ALTER TABLE shift_type_history ADD COLUMN entrance_max_tolerance INT;
ALTER TABLE shift_type_history ADD COLUMN exit_tolerance INT;
ALTER TABLE shift_type_history ADD COLUMN exit_max_tolerance INT;
ALTER TABLE shift_type_history ADD COLUMN break_max_in_shift INT;
ALTER TABLE shift_type_history ADD COLUMN break_in_shift INT;
ALTER TABLE shift_type_history ADD COLUMN max_tolerance_allowed INT;

ALTER TABLE person_shift DROP COLUMN jolly;

ALTER TABLE person_shift_shift_type ADD COLUMN jolly BOOLEAN;

ALTER TABLE person_shift_days ADD COLUMN exceeded_thresholds INT;

UPDATE shift_type SET max_tolerance_allowed = 0, entrance_tolerance = 0, entrance_max_tolerance = 0, exit_tolerance = 0, exit_max_tolerance = 0, break_max_in_shift = 0, break_in_shift = 0;
UPDATE person_shift_shift_type SET jolly = false;
UPDATE shift_time_table SET start_evening = null, end_evening = null, start_evening_lunch_time = null, end_evening_lunch_time = null;
UPDATE person_shift_days SET exceeded_thresholds = 0;

CREATE TABLE shift_categories_persons (
	categories_id BIGINT NOT NULL,
	managers_id BIGINT NOT NULL,
	FOREIGN KEY (categories_id) REFERENCES shift_categories (id),
	FOREIGN KEY (managers_id) REFERENCES persons (id)
);

CREATE TABLE shift_categories_persons_history (
	
    _revision INTEGER NOT NULL REFERENCES revinfo(rev),
    _revision_type SMALLINT NOT NULL,

    categories_id BIGINT,
    managers_id BIGINT,
    
    PRIMARY KEY (_revision, _revision_type, categories_id, managers_id)
);

CREATE TABLE person_shift_days_history(
  id BIGINT NOT NULL,
  _revision INTEGER NOT NULL REFERENCES revinfo(rev),
  _revision_type SMALLINT NOT NULL,
  "date" DATE,
  person_shift_id BIGINT,
  shift_type_id BIGINT,
  shift_slot TEXT,
  exceeded_thresholds INT,
  
  PRIMARY KEY (id, _revision, _revision_type)
);

CREATE TABLE person_shift_day_in_trouble (
	id BIGSERIAL PRIMARY KEY,
	cause TEXT NOT NULL,
	person_shift_day_id BIGINT NOT NULL REFERENCES person_shift_days(id),
	email_sent boolean,
	CONSTRAINT only_one_type_in_a_day UNIQUE(cause, person_shift_day_id)
);

CREATE TABLE person_shift_day_in_trouble_history (
  id BIGINT,
  _revision INTEGER NOT NULL REFERENCES revinfo(rev),
  _revision_type SMALLINT NOT NULL,
  cause TEXT,
	email_sent boolean,
	person_shift_day_id BIGINT,
  PRIMARY KEY (id, _revision, _revision_type)
);

CREATE TABLE shift_type_month (
	id BIGSERIAL PRIMARY KEY,
	version INTEGER,
	created_at timestamp without time zone,
  updated_at timestamp without time zone,
	year_month TEXT NOT NULL,
	shift_type_id BIGINT NOT NULL REFERENCES shift_type(id),
	approved boolean,
	CONSTRAINT unique_in_a_month UNIQUE(year_month, shift_type_id)
);

CREATE TABLE shift_type_month_history (
  id BIGINT NOT NULL,
  _revision INTEGER NOT NULL REFERENCES revinfo(rev),
  _revision_type SMALLINT NOT NULL,
  version INTEGER,
	year_month TEXT,
	shift_type_id BIGINT,
	approved boolean,
  PRIMARY KEY (id, _revision, _revision_type)
);

INSERT INTO person_shift_days_history (id, _revision, _revision_type, "date", person_shift_id, shift_type_id, shift_slot)
  SELECT id, r.rev, 0, "date", person_shift_id, shift_type_id, shift_slot FROM person_shift_days,
    (SELECT MAX(rev) AS rev FROM revinfo) AS r;

# ---!Downs

ALTER TABLE shift_time_table DROP CONSTRAINT shift_time_table_office_id_fkey;
ALTER TABLE shift_time_table DROP COLUMN office_id;

ALTER TABLE shift_type_history DROP COLUMN entrance_tolerance;
ALTER TABLE shift_type_history DROP COLUMN entrance_max_tolerance;
ALTER TABLE shift_type_history DROP COLUMN exit_tolerance;
ALTER TABLE shift_type_history DROP COLUMN exit_max_tolerance;
ALTER TABLE shift_type_history DROP COLUMN break_max_in_shift;
ALTER TABLE shift_type_history DROP COLUMN break_in_shift;
ALTER TABLE shift_type_history DROP COLUMN max_tolerance_allowed;

ALTER TABLE shift_type DROP COLUMN entrance_max_tolerance;
ALTER TABLE shift_type DROP COLUMN entrance_tolerance;
ALTER TABLE shift_type DROP COLUMN exit_tolerance;
ALTER TABLE shift_type DROP COLUMN exit_max_tolerance;
ALTER TABLE shift_type DROP COLUMN break_max_in_shift;
ALTER TABLE shift_type DROP COLUMN break_in_shift;
ALTER TABLE shift_type DROP COLUMN max_tolerance_allowed;

ALTER TABLE person_shift_shift_type DROP COLUMN jolly;
ALTER TABLE person_shift ADD COLUMN jolly BOOLEAN;

ALTER TABLE person_shift_days DROP COLUMN exceeded_thresholds;

UPDATE person_shift SET jolly = FALSE;

DROP TABLE shift_categories_persons;
DROP TABLE shift_categories_persons_history;

DROP TABLE person_shift_days_history;
DROP TABLE person_shift_day_in_trouble_history;
DROP TABLE person_shift_day_in_trouble;
DROP TABLE shift_type_month;
DROP TABLE shift_type_month_history;


