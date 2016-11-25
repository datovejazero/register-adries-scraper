CREATE TABLE building_number
(
    database_operation VARCHAR(MAX) NOT NULL,
    building_number_id INT NOT NULL,
    version_id INT NOT NULL,
    created_reason VARCHAR(MAX) NOT NULL,
    valid_from DATE,
    valid_to DATE,
    effective_date DATE,
    building_index VARCHAR(MAX) NOT NULL,
    property_registration_id INT
);

CREATE TABLE building_unit
(
    database_operation VARCHAR(MAX) NOT NULL,
    building_unit_id INT NOT NULL,
    version_id INT NOT NULL,
    created_reason VARCHAR(MAX) NOT NULL,
    valid_from DATE,
    valid_to DATE,
    effective_date DATE,
    bulding_unit VARCHAR(MAX) NOT NULL,
    property_reg_number_id INT
);
CREATE TABLE country
(
    database_operation VARCHAR(MAX) NOT NULL,
    object_id INT NOT NULL,
    version_id INT NOT NULL,
    created_reason VARCHAR(MAX) NOT NULL,
    valid_from DATE,
    valid_to DATE,
    effective_date DATE,
    code_list_code VARCHAR(MAX) NOT NULL,
    item_list_code VARCHAR(MAX),
    item_name VARCHAR(MAX) NOT NULL
);
CREATE TABLE district
(
    database_operation VARCHAR(MAX) NOT NULL,
    district_id INT NOT NULL,
    version_id INT NOT NULL,
    created_reason VARCHAR(MAX) NOT NULL,
    valid_from DATE,
    valid_to DATE,
    effective_date DATE,
    code_list_code VARCHAR(MAX),
    item_list_code VARCHAR(MAX),
    street_name VARCHAR(MAX) NOT NULL,
    municipality_id INT
);
CREATE TABLE municipality
(
    database_operation VARCHAR(MAX) NOT NULL,
    municipality_id INT NOT NULL,
    version_id INT NOT NULL,
    created_reason VARCHAR(MAX) NOT NULL,
    valid_from DATE,
    valid_to DATE,
    effective_date DATE,
    code_list_code VARCHAR(MAX) NOT NULL,
    item_list_code VARCHAR(MAX),
    item_name VARCHAR(MAX) NOT NULL,
    country_id INT,
    status VARCHAR(MAX) NOT NULL
);
CREATE TABLE processed_davky
(
    id VARCHAR(MAX),
    url VARCHAR(254) PRIMARY KEY NOT NULL,
    date DATE NOT NULL
);
CREATE TABLE property_registration_number
(
    database_operation VARCHAR(MAX) NOT NULL,
    property_registration_id INT NOT NULL,
    version_id INT NOT NULL,
    created_reason VARCHAR(MAX) NOT NULL,
    valid_from DATE,
    valid_to DATE,
    effective_date DATE,
    property_registration_number INT NOT NULL,
    building_purpose_list_code VARCHAR(MAX) NOT NULL,
    building_type_item_code VARCHAR(MAX) NOT NULL,
    building_name VARCHAR(MAX),
    municipality_id INT,
    district_id INT
);
CREATE TABLE region
(
    database_operation VARCHAR(MAX) NOT NULL,
    region_id INT NOT NULL,
    version_id INT NOT NULL,
    created_reason VARCHAR(MAX) NOT NULL,
    valid_to DATE,
    valid_from DATE,
    effective_date DATE,
    code_list_code VARCHAR(MAX) NOT NULL,
    item_list_code VARCHAR(MAX),
    region_name VARCHAR(MAX) NOT NULL,
    region_identifier INT
);
CREATE TABLE street_name
(
    database_operation VARCHAR(MAX) NOT NULL,
    street_id INT NOT NULL,
    version_id INT NOT NULL,
    created_reason VARCHAR(MAX) NOT NULL,
    valid_from DATE,
    valid_to DATE,
    effective_date DATE,
    street_name VARCHAR(MAX),
    municipality_id INT,
    district_id INT
);