# Skapar tabeller
CREATE TABLE arbetspass(
  # Dag måste ändras
  id int(10) AUTO_INCREMENT UNIQUE PRIMARY KEY,
  start_time varchar(5),
  end_time varchar(5),
  scen_id int(10),
  person varchar(12)
);

CREATE TABLE band(
  id int(10) AUTO_INCREMENT UNIQUE PRIMARY KEY,
  bio varchar(1000),
  namn varchar(100),
  country varchar(50),
  kontaktperson varchar(12),
  genre int(10)
);

CREATE TABLE bandmedlem(
  id int(100) AUTO_INCREMENT UNIQUE PRIMARY KEY,
  namn varchar(30),
  spelar_i int(10),
  roll varchar(20)
);

# Tabell är en modifierad version av : https://github.com/raramuridesign/mysql-country-list/blob/master/mysql-country-list.sql
CREATE TABLE countries(
  namn varchar(50) UNIQUE PRIMARY KEY
);

CREATE TABLE genre(
  id int(10) AUTO_INCREMENT UNIQUE PRIMARY KEY,
  name varchar(20)
);

CREATE TABLE personal(
  personnr varchar(12) UNIQUE PRIMARY KEY,
  phonenbr varchar(13),
  namn varchar(30)
);

CREATE TABLE scen(
  id int(10) AUTO_INCREMENT UNIQUE PRIMARY KEY,
  namn varchar(30),
  kapacitet int(5)
);

CREATE TABLE spelning(
  start_time varchar(5),
  end_time varchar(5),
  scen int(1),
  band_id int(10),
  id int(100) AUTO_INCREMENT PRIMARY KEY UNIQUE,
  day enum('fredag', 'lördag', 'söndag')
);

# Främmande nycklar
ALTER TABLE arbetspass
ADD FOREIGN KEY (scen_id)
REFERENCES scen(id);

ALTER TABLE arbetspass
ADD FOREIGN KEY (person)
REFERENCES personal(personnr);

ALTER TABLE band
ADD FOREIGN KEY (country)
REFERENCES countries(namn);

ALTER TABLE band
ADD FOREIGN KEY (kontaktperson)
REFERENCES personal(personnr);

ALTER TABLE band
ADD FOREIGN KEY(genre)
REFERENCES genre(id);

ALTER TABLE bandmedlem
ADD FOREIGN KEY(spelar_i)
REFERENCES band(id);

ALTER TABLE spelning
ADD FOREIGN KEY(band_id)
REFERENCES band(id);

ALTER TABLE spelning
ADD FOREIGN KEY(scen)
REFERENCES scen(id);

# Skriven in nödvändig data i tabeller modifierat från
# https://github.com/raramuridesign/mysql-country-list/blob/master/mysql-country-list.sql
INSERT INTO `countries` VALUES ('Afghanistan');
INSERT INTO `countries` VALUES ('Albania');
INSERT INTO `countries` VALUES ('Algeria');
INSERT INTO `countries` VALUES ('American Samoa');
INSERT INTO `countries` VALUES ('Andorra');
INSERT INTO `countries` VALUES ('Angola');
INSERT INTO `countries` VALUES ('Anguilla');
INSERT INTO `countries` VALUES ('Antarctica');
INSERT INTO `countries` VALUES ('Antigua and Barbuda');
INSERT INTO `countries` VALUES ('Argentina');
INSERT INTO `countries` VALUES ('Armenia');
INSERT INTO `countries` VALUES ('Aruba');
INSERT INTO `countries` VALUES ('Australia');
INSERT INTO `countries` VALUES ('Austria');
INSERT INTO `countries` VALUES ('Azerbaijan');
INSERT INTO `countries` VALUES ('Bahamas');
INSERT INTO `countries` VALUES ('Bahrain');
INSERT INTO `countries` VALUES ('Bangladesh');
INSERT INTO `countries` VALUES ('Barbados');
INSERT INTO `countries` VALUES ('Belarus');
INSERT INTO `countries` VALUES ('Belgium');
INSERT INTO `countries` VALUES ('Belize');
INSERT INTO `countries` VALUES ('Benin');
INSERT INTO `countries` VALUES ('Bermuda');
INSERT INTO `countries` VALUES ('Bhutan');
INSERT INTO `countries` VALUES ('Bolivia');
INSERT INTO `countries` VALUES ('Bosnia and Herzegovina');
INSERT INTO `countries` VALUES ('Botswana');
INSERT INTO `countries` VALUES ('Bouvet Island');
INSERT INTO `countries` VALUES ('Brazil');
INSERT INTO `countries` VALUES ('British Indian Ocean Territory');
INSERT INTO `countries` VALUES ('Brunei Darussalam');
INSERT INTO `countries` VALUES ('Bulgaria');
INSERT INTO `countries` VALUES ('Burkina Faso');
INSERT INTO `countries` VALUES ('Burundi');
INSERT INTO `countries` VALUES ('Cambodia');
INSERT INTO `countries` VALUES ('Cameroon');
INSERT INTO `countries` VALUES ('Canada');
INSERT INTO `countries` VALUES ('Cape Verde');
INSERT INTO `countries` VALUES ('Cayman Islands');
INSERT INTO `countries` VALUES ('Central African Republic');
INSERT INTO `countries` VALUES ('Chad');
INSERT INTO `countries` VALUES ('Chile');
INSERT INTO `countries` VALUES ('China');
INSERT INTO `countries` VALUES ('Christmas Island');
INSERT INTO `countries` VALUES ('Cocos (Keeling) Islands');
INSERT INTO `countries` VALUES ('Colombia');
INSERT INTO `countries` VALUES ('Comoros');
INSERT INTO `countries` VALUES ('Congo');
INSERT INTO `countries` VALUES ('Cook Islands');
INSERT INTO `countries` VALUES ('Costa Rica');
INSERT INTO `countries` VALUES ('Croatia (Hrvatska)');
INSERT INTO `countries` VALUES ('Cuba');
INSERT INTO `countries` VALUES ('Cyprus');
INSERT INTO `countries` VALUES ('Czech Republic');
INSERT INTO `countries` VALUES ('Denmark');
INSERT INTO `countries` VALUES ('Djibouti');
INSERT INTO `countries` VALUES ('Dominica');
INSERT INTO `countries` VALUES ('Dominican Republic');
INSERT INTO `countries` VALUES ('East Timor');
INSERT INTO `countries` VALUES ('Ecuador');
INSERT INTO `countries` VALUES ('Egypt');
INSERT INTO `countries` VALUES ('El Salvador');
INSERT INTO `countries` VALUES ('Equatorial Guinea');
INSERT INTO `countries` VALUES ('Eritrea');
INSERT INTO `countries` VALUES ('Estonia');
INSERT INTO `countries` VALUES ('Ethiopia');
INSERT INTO `countries` VALUES ('Falkland Islands (Malvinas)');
INSERT INTO `countries` VALUES ('Faroe Islands');
INSERT INTO `countries` VALUES ('Fiji');
INSERT INTO `countries` VALUES ('Finland');
INSERT INTO `countries` VALUES ('France');
INSERT INTO `countries` VALUES ('France, Metropolitan');
INSERT INTO `countries` VALUES ('French Guiana');
INSERT INTO `countries` VALUES ('French Polynesia');
INSERT INTO `countries` VALUES ('French Southern Territories');
INSERT INTO `countries` VALUES ('Gabon');
INSERT INTO `countries` VALUES ('Gambia');
INSERT INTO `countries` VALUES ('Georgia');
INSERT INTO `countries` VALUES ('Germany');
INSERT INTO `countries` VALUES ('Ghana');
INSERT INTO `countries` VALUES ('Gibraltar');
INSERT INTO `countries` VALUES ('Guernsey');
INSERT INTO `countries` VALUES ('Greece');
INSERT INTO `countries` VALUES ('Greenland');
INSERT INTO `countries` VALUES ('Grenada');
INSERT INTO `countries` VALUES ('Guadeloupe');
INSERT INTO `countries` VALUES ('Guam');
INSERT INTO `countries` VALUES ('Guatemala');
INSERT INTO `countries` VALUES ('Guinea');
INSERT INTO `countries` VALUES ('Guinea-Bissau');
INSERT INTO `countries` VALUES ('Guyana');
INSERT INTO `countries` VALUES ('Haiti');
INSERT INTO `countries` VALUES ('Heard and Mc Donald Islands');
INSERT INTO `countries` VALUES ('Honduras');
INSERT INTO `countries` VALUES ('Hong Kong');
INSERT INTO `countries` VALUES ('Hungary');
INSERT INTO `countries` VALUES ('Iceland');
INSERT INTO `countries` VALUES ('India');
INSERT INTO `countries` VALUES ('Isle of Man');
INSERT INTO `countries` VALUES ('Indonesia');
INSERT INTO `countries` VALUES ('Iran (Islamic Republic of)');
INSERT INTO `countries` VALUES ('Iraq');
INSERT INTO `countries` VALUES ('Ireland');
INSERT INTO `countries` VALUES ('Israel');
INSERT INTO `countries` VALUES ('Italy');
INSERT INTO `countries` VALUES ('Ivory Coast');
INSERT INTO `countries` VALUES ('Jersey');
INSERT INTO `countries` VALUES ('Jamaica');
INSERT INTO `countries` VALUES ('Japan');
INSERT INTO `countries` VALUES ('Jordan');
INSERT INTO `countries` VALUES ('Kazakhstan');
INSERT INTO `countries` VALUES ('Kenya');
INSERT INTO `countries` VALUES ('Kiribati');
INSERT INTO `countries` VALUES ('Korea, Democratic People''s Republic of');
INSERT INTO `countries` VALUES ('Korea, Republic of');
INSERT INTO `countries` VALUES ('Kosovo');
INSERT INTO `countries` VALUES ('Kuwait');
INSERT INTO `countries` VALUES ('Kyrgyzstan');
INSERT INTO `countries` VALUES ('Lao People''s Democratic Republic');
INSERT INTO `countries` VALUES ('Latvia');
INSERT INTO `countries` VALUES ('Lebanon');
INSERT INTO `countries` VALUES ('Lesotho');
INSERT INTO `countries` VALUES ('Liberia');
INSERT INTO `countries` VALUES ('Libyan Arab Jamahiriya');
INSERT INTO `countries` VALUES ('Liechtenstein');
INSERT INTO `countries` VALUES ('Lithuania');
INSERT INTO `countries` VALUES ('Luxembourg');
INSERT INTO `countries` VALUES ('Macau');
INSERT INTO `countries` VALUES ('Macedonia');
INSERT INTO `countries` VALUES ('Madagascar');
INSERT INTO `countries` VALUES ('Malawi');
INSERT INTO `countries` VALUES ('Malaysia');
INSERT INTO `countries` VALUES ('Maldives');
INSERT INTO `countries` VALUES ('Mali');
INSERT INTO `countries` VALUES ('Malta');
INSERT INTO `countries` VALUES ('Marshall Islands');
INSERT INTO `countries` VALUES ('Martinique');
INSERT INTO `countries` VALUES ('Mauritania');
INSERT INTO `countries` VALUES ('Mauritius');
INSERT INTO `countries` VALUES ('Mayotte');
INSERT INTO `countries` VALUES ('Mexico');
INSERT INTO `countries` VALUES ('Micronesia, Federated States of');
INSERT INTO `countries` VALUES ('Moldova, Republic of');
INSERT INTO `countries` VALUES ('Monaco');
INSERT INTO `countries` VALUES ('Mongolia');
INSERT INTO `countries` VALUES ('Montenegro');
INSERT INTO `countries` VALUES ('Montserrat');
INSERT INTO `countries` VALUES ('Morocco');
INSERT INTO `countries` VALUES ('Mozambique');
INSERT INTO `countries` VALUES ('Myanmar');
INSERT INTO `countries` VALUES ('Namibia');
INSERT INTO `countries` VALUES ('Nauru');
INSERT INTO `countries` VALUES ('Nepal');
INSERT INTO `countries` VALUES ('Netherlands');
INSERT INTO `countries` VALUES ('Netherlands Antilles');
INSERT INTO `countries` VALUES ('New Caledonia');
INSERT INTO `countries` VALUES ('New Zealand');
INSERT INTO `countries` VALUES ('Nicaragua');
INSERT INTO `countries` VALUES ('Niger');
INSERT INTO `countries` VALUES ('Nigeria');
INSERT INTO `countries` VALUES ('Niue');
INSERT INTO `countries` VALUES ('Norfolk Island');
INSERT INTO `countries` VALUES ('Northern Mariana Islands');
INSERT INTO `countries` VALUES ('Norway');
INSERT INTO `countries` VALUES ('Oman');
INSERT INTO `countries` VALUES ('Pakistan');
INSERT INTO `countries` VALUES ('Palau');
INSERT INTO `countries` VALUES ('Palestine');
INSERT INTO `countries` VALUES ('Panama');
INSERT INTO `countries` VALUES ('Papua New Guinea');
INSERT INTO `countries` VALUES ('Paraguay');
INSERT INTO `countries` VALUES ('Peru');
INSERT INTO `countries` VALUES ('Philippines');
INSERT INTO `countries` VALUES ('Pitcairn');
INSERT INTO `countries` VALUES ('Poland');
INSERT INTO `countries` VALUES ('Portugal');
INSERT INTO `countries` VALUES ('Puerto Rico');
INSERT INTO `countries` VALUES ('Qatar');
INSERT INTO `countries` VALUES ('Reunion');
INSERT INTO `countries` VALUES ('Romania');
INSERT INTO `countries` VALUES ('Russian Federation');
INSERT INTO `countries` VALUES ('Rwanda');
INSERT INTO `countries` VALUES ('Saint Kitts and Nevis');
INSERT INTO `countries` VALUES ('Saint Lucia');
INSERT INTO `countries` VALUES ('Saint Vincent and the Grenadines');
INSERT INTO `countries` VALUES ('Samoa');
INSERT INTO `countries` VALUES ('San Marino');
INSERT INTO `countries` VALUES ('Sao Tome and Principe');
INSERT INTO `countries` VALUES ('Saudi Arabia');
INSERT INTO `countries` VALUES ('Senegal');
INSERT INTO `countries` VALUES ('Serbia');
INSERT INTO `countries` VALUES ('Seychelles');
INSERT INTO `countries` VALUES ('Sierra Leone');
INSERT INTO `countries` VALUES ('Singapore');
INSERT INTO `countries` VALUES ('Slovakia');
INSERT INTO `countries` VALUES ('Slovenia');
INSERT INTO `countries` VALUES ('Solomon Islands');
INSERT INTO `countries` VALUES ('Somalia');
INSERT INTO `countries` VALUES ('South Africa');
INSERT INTO `countries` VALUES ('South Georgia South Sandwich Islands');
INSERT INTO `countries` VALUES ('Spain');
INSERT INTO `countries` VALUES ('Sri Lanka');
INSERT INTO `countries` VALUES ('St. Helena');
INSERT INTO `countries` VALUES ('St. Pierre and Miquelon');
INSERT INTO `countries` VALUES ('Sudan');
INSERT INTO `countries` VALUES ('Suriname');
INSERT INTO `countries` VALUES ('Svalbard and Jan Mayen Islands');
INSERT INTO `countries` VALUES ('Swaziland');
INSERT INTO `countries` VALUES ('Sweden');
INSERT INTO `countries` VALUES ('Switzerland');
INSERT INTO `countries` VALUES ('Syrian Arab Republic');
INSERT INTO `countries` VALUES ('Taiwan');
INSERT INTO `countries` VALUES ('Tajikistan');
INSERT INTO `countries` VALUES ('Tanzania, United Republic of');
INSERT INTO `countries` VALUES ('Thailand');
INSERT INTO `countries` VALUES ('Togo');
INSERT INTO `countries` VALUES ('Tokelau');
INSERT INTO `countries` VALUES ('Tonga');
INSERT INTO `countries` VALUES ('Trinidad and Tobago');
INSERT INTO `countries` VALUES ('Tunisia');
INSERT INTO `countries` VALUES ('Turkey');
INSERT INTO `countries` VALUES ('Turkmenistan');
INSERT INTO `countries` VALUES ('Turks and Caicos Islands');
INSERT INTO `countries` VALUES ('Tuvalu');
INSERT INTO `countries` VALUES ('Uganda');
INSERT INTO `countries` VALUES ('Ukraine');
INSERT INTO `countries` VALUES ('United Arab Emirates');
INSERT INTO `countries` VALUES ('United Kingdom');
INSERT INTO `countries` VALUES ('United States');
INSERT INTO `countries` VALUES ('United States minor outlying islands');
INSERT INTO `countries` VALUES ('Uruguay');
INSERT INTO `countries` VALUES ('Uzbekistan');
INSERT INTO `countries` VALUES ('Vanuatu');
INSERT INTO `countries` VALUES ('Vatican City State');
INSERT INTO `countries` VALUES ('Venezuela');
INSERT INTO `countries` VALUES ('Vietnam');
INSERT INTO `countries` VALUES ('Virgin Islands (British)');
INSERT INTO `countries` VALUES ('Virgin Islands (U.S.)');
INSERT INTO `countries` VALUES ('Wallis and Futuna Islands');
INSERT INTO `countries` VALUES ('Western Sahara');
INSERT INTO `countries` VALUES ('Yemen');
INSERT INTO `countries` VALUES ('Yugoslavia');
INSERT INTO `countries` VALUES ('Zaire');
INSERT INTO `countries` VALUES ('Zambia');
INSERT INTO `countries` VALUES ('Zimbabwe');
