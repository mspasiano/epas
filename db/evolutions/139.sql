# --- !Ups

ALTER TABLE general_setting ADD COLUMN start_daily_shift TEXT;
ALTER TABLE general_setting ADD COLUMN end_daily_shift TEXT;
ALTER TABLE general_setting ADD COLUMN start_nightly_shift TEXT;
ALTER TABLE general_setting ADD COLUMN end_nightly_shift TEXT;

ALTER TABLE general_setting_history ADD COLUMN start_daily_shift TEXT;
ALTER TABLE general_setting_history ADD COLUMN end_daily_shift TEXT;
ALTER TABLE general_setting_history ADD COLUMN start_nightly_shift TEXT;
ALTER TABLE general_setting_history ADD COLUMN end_nightly_shift TEXT;

UPDATE general_setting SET start_daily_shift = '6:00', 
	end_daily_shift = '19:00', start_nightly_shift = '19:00', 
	end_nightly_shift = '6:00';
	
ALTER TABLE shift_time_table ADD COLUMN calculation_type TEXT;
UPDATE shift_time_table SET calculation_type = 'standard_CNR';

CREATE TABLE organization_shift_time_table(
	id BIGSERIAL PRIMARY KEY,
	name TEXT,
	office_id BIGINT,
	calculation_type TEXT,
	created_at TIMESTAMP WITHOUT TIME ZONE,
	updated_at TIMESTAMP WITHOUT TIME ZONE,
	FOREIGN KEY (office_id) REFERENCES office (id),
	version INT DEFAULT 0);

CREATE TABLE organization_shift_time_table_history(
	id BIGINT NOT NULL,
	_revision INTEGER NOT NULL REFERENCES revinfo(rev),
    _revision_type SMALLINT NOT NULL,
    name TEXT,
	office_id BIGINT,
	calculation_type TEXT,
	PRIMARY KEY (id, _revision, _revision_type)
);

ALTER TABLE shift_type ADD COLUMN organization_shift_time_table_id BIGINT;
ALTER TABLE shift_type_history ADD COLUMN organization_shift_time_table_id BIGINT;
ALTER TABLE shift_type ADD FOREIGN KEY (organization_shift_time_table_id) REFERENCES organization_shift_time_table(id);

CREATE TABLE organization_shift_slot(
	id BIGSERIAL PRIMARY KEY,
	name TEXT, 
	shift_time_table_id BIGINT NOT NULL,
	begin_slot VARCHAR(64) NOT NULL,
	end_slot VARCHAR(64) NOT NULL,
	begin_meal_slot VARCHAR(64),
	end_meal_slot VARCHAR(64),
	payment_type TEXT,
	minutes_paid INTEGER,
	created_at TIMESTAMP WITHOUT TIME ZONE,
	updated_at TIMESTAMP WITHOUT TIME ZONE,
	FOREIGN KEY (shift_time_table_id) REFERENCES organization_shift_time_table(id),
	version INT DEFAULT 0
	);

CREATE TABLE organization_shift_slot_history(
	id BIGINT NOT NULL,
	_revision INTEGER NOT NULL REFERENCES revinfo(rev),
    _revision_type SMALLINT NOT NULL,
    name TEXT,
    shift_time_table_id BIGINT,
    begin_slot VARCHAR(64),
	end_slot VARCHAR(64),
	begin_meal_slot VARCHAR(64),
	end_meal_slot VARCHAR(64),
	payment_type TEXT,
	minutes_paid INTEGER,
	PRIMARY KEY (id, _revision, _revision_type)
    );
    

ALTER TABLE person_shift_days ADD COLUMN organization_shift_slot_id BIGINT REFERENCES organization_shift_slot(id);
ALTER TABLE person_shift_days_history ADD COLUMN organization_shift_slot_id BIGINT;



# --- !Downs
-- non è necessaria una down