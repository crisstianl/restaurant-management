<!-- JOB -->
INSERT INTO JOB (Id, Title, Description, Hours, Paycheck, Rank) VALUES ('1', 'Administrator', 'Gestioneaza baza de date;Verifica stocuri;Monitorizeaza comenzile;Gestionea angajati;', '8', '3000.00', '10');
INSERT INTO JOB (Id, Title, Description, Hours, Paycheck, Rank) VALUES ('2', 'Chelner', 'Serveste clienti;Face curatenie;', '10', '1000.00', '5');
INSERT INTO JOB (Id, Title, Description, Hours, Paycheck, Rank) VALUES ('3', 'Bucatar', 'Prepara meniul', '10', '2000.00', '8');

<!-- ADMIN -->
INSERT INTO ADMIN (Id, Name, Username, Password, Gender, Birthdate, Education, Email, Experience, Facebook, Languages, Phone, Skills, HireDate, JobId, Permissions) 
VALUES ('10', 'Rusu Andrei', 'rusuandrei', 'rusua', 'MALE', '1991-07-12', 'Facultatea de inginerie tehnica', 'andrei_rusu@yahoo.com', 'Administrator baza de date la Adicom', 'www.facebook.com/andrei.rusu.338', 'Engleza;Franceza;Romana', '+4075102349', 'Proeficient inginer software', '2013-03-01', '1', '2');

<!-- WORKER -->
INSERT INTO WORKER (Id, Name, Username, Password, Gender, Birthdate, Education, Email, Experience, Facebook, Languages, Phone, Skills, HireDate, JobId) 
VALUES ('20', 'Ursu Bogdan', 'ursubogdan', 'ursub', 'MALE', '1990-11-24', 'Facultatea de sociologie', 'bogdan_ursu@yahoo.com', 'Ospatar la cafeneaua Cafe MO', 'www.facebook.com/bogdan.ursu.104', 'Engleza;Romana', '+4075394409', 'Punctual, respectuos, amabil, sociabil', '2013-05-31', '2');
INSERT INTO WORKER (Id, Name, Username, Password, Gender, Birthdate, Education, Email, Experience, Facebook, Languages, Phone, Skills, HireDate, JobId) 
VALUES ('21', 'Coman Mihaela', 'comanmihaela', 'comanm', 'FEMALE', '1988-11-24', 'Facultatea de geografie', 'mihaelac@yahoo.com', 'Chelner la cafeneaua El Barin;Ospatar la restaurant Mamma Mia', 'www.facebook.com/mihaela.coman.1800', 'Engleza;Romana', '+4075377088', 'Punctuala, sociabila, spirit de observatie', '2010-08-30', '2');
INSERT INTO WORKER (Id, Name, Username, Password, Gender, Birthdate, Education, Email, Experience, Facebook, Languages, Phone, Skills, HireDate, JobId) 
VALUES ('22', 'Alexandra Larisa', 'alexandralarisa', 'alexandral', 'FEMALE', '1994-02-22', 'Facultatea de sport', 'alexa_larisa@yahoo.com', 'Chelner/Barman la restaurant Rustic', 'www.facebook.com/larisel.94', 'Engleza;Romana', '+4076630447', 'Sociabila, simpatica, vesela, cu initiativa', '2011-01-11', '2');
INSERT INTO WORKER (Id, Name, Username, Password, Gender, Birthdate, Education, Email, Experience, Facebook, Languages, Phone, Skills, HireDate, JobId) 
VALUES ('23', 'Gavril Daniel', 'gavrildaniel', 'gravild', 'MALE', '1977-10-14', 'Scoala de bucatari Horeca', 'gavril_dany@yahoo.com', '5 ani experienta ca bucatar la restaurant Mamma Mia', 'www.facebook.com/daniel.gavril.1', 'Italiana;Romana', '+4077044230', 'Bucatar iscusit, respectabil, carismatic', '2015-04-13', '3');
INSERT INTO WORKER (Id, Name, Username, Password, Gender, Birthdate, Education, Email, Experience, Facebook, Languages, Phone, Skills, HireDate, JobId) 
VALUES ('24', 'Stan Stefan', 'stanstefan', 'stans', 'MALE', '1968-03-05', 'Scoala de bucatari Horia Virlan', 'stan_stefan@yahoo.com', '10 ani experienta ca bucatar la diverse restaurante din Bucuresti', NULL, 'Romana', '+40770770233', 'Disciplat, respectuos, onorabil', '2010-06-05', '3');

<!-- UNIT -->
INSERT INTO UNIT (Id, Description) VALUES ('KG', 'Kilograme');
INSERT INTO UNIT (Id, Description) VALUES ('L', 'Litrii');
INSERT INTO UNIT (Id, Description) VALUES ('G', 'Grame');

<!-- BASE_CURRENCY -->
INSERT INTO BASE_CURRENCY (Id, Name, ConvFactor, CurrencyType) VALUES ('RON', 'Lei', NULL, 'BASE_CURRENCY');
INSERT INTO BASE_CURRENCY (Id, Name, ConvFactor, CurrencyType) VALUES ('EUR', '\u20AC', '4.5', 'CURRENCY');
INSERT INTO BASE_CURRENCY (Id, Name, ConvFactor, CurrencyType) VALUES ('USD', '\u0024', '3.3', 'CURRENCY');

<!-- DOC_STATUS -->
INSERT INTO DOC_STATUS (Id, Description) VALUES ('N', 'Nou');
INSERT INTO DOC_STATUS (Id, Description) VALUES ('P', 'In asteptare');
INSERT INTO DOC_STATUS (Id, Description) VALUES ('S', 'Trimise');
INSERT INTO DOC_STATUS (Id, Description) VALUES ('R', 'Pregatite');
INSERT INTO DOC_STATUS (Id, Description) VALUES ('C', 'Incheiate');

<!-- CATEGORY -->
INSERT INTO CATEGORY (Id, Description) VALUES ('001', 'Ciorbe');
INSERT INTO CATEGORY (Id, Description) VALUES ('002', 'Garnituri si salate');
INSERT INTO CATEGORY (Id, Description) VALUES ('003', 'Gratar');
INSERT INTO CATEGORY (Id, Description) VALUES ('004', 'Mic dejun');
INSERT INTO CATEGORY (Id, Description) VALUES ('005', 'Paste');
INSERT INTO CATEGORY (Id, Description) VALUES ('006', 'Platouri 2 persoane');
INSERT INTO CATEGORY (Id, Description) VALUES ('007', 'Preparate de peste');
INSERT INTO CATEGORY (Id, Description) VALUES ('008', 'Preparate de porc');
INSERT INTO CATEGORY (Id, Description) VALUES ('009', 'Preparate de post');
INSERT INTO CATEGORY (Id, Description) VALUES ('010', 'Preparate de pui');
INSERT INTO CATEGORY (Id, Description) VALUES ('011', 'Produse traditionale');
INSERT INTO CATEGORY (Id, Description) VALUES ('012', 'Salate antreu');
INSERT INTO CATEGORY (Id, Description) VALUES ('013', 'Specialitatea bucatarului');
INSERT INTO CATEGORY (Id, Description) VALUES ('014', 'Sosuri');

<!-- TAG -->
INSERT INTO TAG (Id, Description, Background, Foreground) VALUES ('H', 'Important', '4294901760', '4294967295');
INSERT INTO TAG (Id, Description, Background, Foreground) VALUES ('N', 'Normal', '4294927872', '4294967295');
INSERT INTO TAG (Id, Description, Background, Foreground) VALUES ('L', 'Neimportant', '4294967040', '4294967295');
INSERT INTO TAG (Id, Description, Background, Foreground) VALUES ('NEW', 'Nou', '4281558783', '4294967295');
INSERT INTO TAG (Id, Description, Background, Foreground) VALUES ('SP', 'Special', '4288217343', '4294967295');
INSERT INTO TAG (Id, Description, Background, Foreground) VALUES ('PROMO', 'Promotie', '4278255360', '4294967295');
INSERT INTO TAG (Id, Description, Background, Foreground) VALUES ('SOLD', 'Vandut', '4294901760', '4294967295');

<!-- CONTACT -->
INSERT INTO CONTACT (Id, Name, Priority, ConvFactor) VALUES ('100', 'Masa 1', 'HIGH', '10');
INSERT INTO CONTACT (Id, Name, Priority, ConvFactor) VALUES ('101', 'Masa 2', 'HIGH', '8');
INSERT INTO CONTACT (Id, Name, Priority, ConvFactor) VALUES ('102', 'Masa 3', 'MEDIUM', '8');
INSERT INTO CONTACT (Id, Name, Priority, ConvFactor) VALUES ('103', 'Masa 4', 'MEDIUM', '7');
INSERT INTO CONTACT (Id, Name, Priority, ConvFactor) VALUES ('104', 'Masa 5', 'MEDIUM', '6');
INSERT INTO CONTACT (Id, Name, Priority, ConvFactor) VALUES ('105', 'Masa 6', 'LOW', '5');
INSERT INTO CONTACT (Id, Name, Priority, ConvFactor) VALUES ('106', 'Masa 7', 'LOW', '4');
INSERT INTO CONTACT (Id, Name, Priority, ConvFactor) VALUES ('107', 'Masa 8', 'LOW', '1');

<!-- CONTACT_TAG -->
INSERT INTO CONTACT_TAG (ContactId, TagId) VALUES ('100', 'H');
INSERT INTO CONTACT_TAG (ContactId, TagId) VALUES ('101', 'H');
INSERT INTO CONTACT_TAG (ContactId, TagId) VALUES ('102', 'N');
INSERT INTO CONTACT_TAG (ContactId, TagId) VALUES ('103', 'N');
INSERT INTO CONTACT_TAG (ContactId, TagId) VALUES ('105', 'L');
INSERT INTO CONTACT_TAG (ContactId, TagId) VALUES ('107', 'L');

<!-- ITEM -->
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('1000', 'Coaste de porc cu varză', 'ceapă albă, coaste de porc, morcov, pâine, varză, mărar, sos roşii', '0', '16.50', '013', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('1001', 'Cotlet cu parmezan', 'cotlet porc, ciuperci, pâine, parmesan, smântână', '0', '22.90', '013', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('1002', 'Gulaş', 'ardei gras, carne de vită, ceapă albă, pâine, usturoi, cartofi, pătrunjel, pastă ardei iute', '0', '15.90', '013', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('1003', 'Meniu dublu quesadilla', 'carne, coleslaw, sos de brânză, cartofi prăjiţi, sos picant, tortilla', '0', '20.90', '013', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('1004', 'Meniu Quesadilla', 'carne, coleslaw, sos de brânză, cartofi prăjiţi, sos picant, tortilla', '0', '14.00', '013', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('1005', 'Piept de pui cu sos Teriyaki', 'pâine, piept de pui, ardei gras, ciuperci, ceapă albă, tăiţei, sos teriyaki', '0', '21.00', '013', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('1006', 'Quesadilla', 'sos picant, coleslaw, sos de brânză, tortilla', '0', '10.00', '013', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('2000', 'Caşcaval pane', 'caşcaval, ouă, pâine, pesmet', '0', '13.40', '004', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('2001', 'Meniu Mamma Mia', 'cartofi prăjiţi, caşcaval, pâine, roşii, şuncă presată, ochiuri', '0', '16.00', '004', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('2002', 'Ochiuri cu kaizer', 'ouă, kaizer, pâine', '0', '12.00', '004', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('2003', 'Omletă cu roşii şi brânză feta', 'ouă, roşii, brânză feta, pâine', '0', '10.00', '004', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('2004', 'Omletă Mamma Mia', 'caşcaval, ciuperci, gogoşari în oţet, ouă, pâine, pastramă porc, kaizer', '0', '12.40', '004', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('2005', 'Omletă simplă', 'ouă, pâine', '0', '6.50', '004', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('2006', 'Platou de brânzeturi', 'caşcaval, brânză feta, gorgonzola, pâine, mozzarella, sos pesto, roşii cherry, măsline verzi, ulei măsline', '0', '14.50', '004', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('3000', 'Dorada la grătar', 'dorada întreagă, cartofi natur, lămâie, pâine', '0', '27.50', '007', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('3001', 'Păstrăv la grătar', 'păstrăv întreg, mămăligă, mujdei', '0', '19.90', '007', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('3002', 'Saramură de păstrăv', 'mămăligă, păstrăv întreg, saramură', '0', '21.40', '007', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('3003', 'Somon cu cartofi copţi şi sos de lămâie', 'pâine, cartofi copţi, file de somon, sos de lămâie', '0', '36.00', '007', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4000', 'Salată creată', 'vizualizează această pagină pentru a crea propria salată.', '0', '10.90', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4001', 'Salată asortată', 'ardei gras, castraveţi verzi, ceapă albă, pâine, roşii, salată verde, vinegreta', '0', '8.40', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4002', 'Salată Bulgărească', 'ardei gras, telemea, şuncă presată, salată verde, roşii, pâine, ouă, măsline negre, castraveţi verzi, vinegreta', '0', '16.90', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4003', 'Salată corsaire', 'ardei gras, salată verde, roşii, porumb, piept de pui, pâine, maioneză, lămâie, castraveţi verzi, vinegreta', '0', '15.00', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4004', 'Salată cu feta şi crutoane', 'ardei gras, castraveţi verzi, crutoane, brânză feta, morcov confiat, pâine, roşii, salată verde, vinegreta', '0', '13.90', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4005', 'Salată cu fructe de mare', 'ardei gras, ceapă rosie, pâine, roşii, salată verde, creveţi, măsline verzi, midii, vinegreta', '0', '22.90', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4006', 'Salată cu piept de pui aromat', 'ardei gras, castraveţi verzi, ceapă rosie, pâine, roşii, salată verde, vinegreta, piept de pui aromat', '0', '13.90', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4007', 'Salată cu piept de pui crocant', 'ardei gras, castraveţi verzi, fulgi porumb, pâine, piept de pui, roşii, salată verde, sos cocktail, vinegreta', '0', '16.40', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4008', 'Salată cu piept de pui și porumb', 'castraveţi verzi, mozzarella, salată verde, porumb, piept de pui, pâine, morcov, măsline negre, lămâie, vinegreta', '0', '16.40', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4009', 'Salată cu ton', 'castraveţi verzi, ton, salată verde, roşii, porumb, pâine, măsline negre, lămâie, ceapă albă, vinegreta', '0', '17.40', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4010', 'Salată Grecească', 'ardei gras, castraveţi verzi, ceapă albă, brânză feta, măsline negre, pâine, roşii, salată verde, vinegreta', '0', '14.90', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4011', 'Salată grecească cu pui', 'ardei gras, salată verde, roşii, piept de pui, pâine, măsline negre, brânză feta, ceapă albă, castraveţi verzi, vinegreta', '0', '16.40', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4012', 'Salată Mamma Mia', 'castraveţi verzi, varză, salată verde, roşii, piept de pui, pâine, morcov, maioneză, ceapă albă, vinegreta', '0', '15.40', '012', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('4013', 'Salată mediteraneană', 'ardei gras, creveţi, somon afumat, cartofi copţi, sos cocktail, salată verde, pâine, morcov, lămâie, ceapă albă, vinegreta', '0', '26.40', '012', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('5000', 'Penne Arabicate', 'ardei iute, kaizer, parmesan, penne, pastramă de pui afumată, sos napoli', '0', '16.70', '005', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('5001', 'Penne con pollo', 'ciuperci, parmesan, penne, pastramă de pui afumată, smântână, mozzarella', '0', '18.90', '005', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('5002', 'Penne cu creveţi', 'parmesan, penne, smântână, creveţi, sos roşii', '0', '27.00', '005', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('5003', 'Penne cu şuncă şi smântână la cuptor', 'penne, smântână, şuncă presată, mozzarella', '0', '20.80', '005', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('5004', 'Penne Napoli', 'ardei gras, ceapă albă, ciuperci, parmesan, penne, roşii, sos napoli', '0', '14.40', '005', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('5005', 'Spaghete Carbonara', 'kaizer, parmesan, smântână, spaghetti, mozzarella', '0', '19.50', '005', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('5006', 'Spaghetti da Bruno', 'măsline negre, parmesan, smântână, spaghetti, creveţi, midii, sos roşii', '0', '26.50', '005', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6000', 'Ciorbă de burtă', 'morcov, oţet, ouă, pâine, smântână, ţelină, usturoi, burtă vită, ceapă albă', '0', '13.00', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6001', 'Ciorbă de fasole', 'amestec de legume, ţelină, roşii, pâine, morcov, fasole, ceapă rosie, ceapă albă, ardei gras, pătrunjel', '0', '7.50', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6002', 'Ciorbă de fasole cu afumătură', 'scăriţă afumată , ţelină, roşii, pâine, morcov, fasole, ceapă rosie, ceapă albă, ardei gras, pătrunjel, amestec de legume', '0', '12.00', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6003', 'Ciorbă de legume', 'amestec de legume, ardei gras, ceapă albă, pâine, roşii, ţelină, cartofi, pătrunjel, sfeclă roşie', '0', '6.50', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6004', 'Ciorbă de văcuță', 'amestec de legume, ardei gras, ceapă albă, pâine, roşii, ţelină, pătrunjel, pulpă de viţel, cartofi', '0', '11.50', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6005', 'Ciorbă moldovenească de găină', 'ardei gras, morcov, pâine, roşii, ţelină, pătrunjel, mărar, carne de găină, ceapă albă', '0', '10.00', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6006', 'Ciorbă rădăuțeană', 'morcov, oţet, ouă, pâine, piept de pui, smântână, ţelină, ceapă albă', '0', '12.00', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6007', 'Mujdei usturoi', '', '0', '2.50', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6008', 'Ardei iute', 'ardei iute', '0', '1.50', '001', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('6009', 'Smantana', '', '0', '2.00', '001', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('7000', 'Brânză cu smântână şi mămăliguţă', 'brânză vaci, smântână, mămăligă', '0', '8.90', '011', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('7001', 'Coaste de porc cu fasole', 'ceapă albă, coaste de porc, fasole, morcov, pâine, mărar, sos roşii', '0', '18.60', '011', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('7002', 'Coaste de porc cu varză', 'ceapă albă, coaste de porc, morcov, pâine, varză, mărar, sos roşii', '0', '16.50', '011', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('7003', 'Gulaş', 'ardei gras, carne de vită, ceapă albă, pâine, usturoi, cartofi, pătrunjel, pastă ardei iute', '0', '15.90', '011', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('7004', 'Pârjoale moldoveneşti', 'carne de vită, pâine, piure, mărar, carne de porc', '0', '18.90', '011', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('7005', 'Saramură de pui', 'ardei gras, ardei iute, mămăligă, piept de pui, roşii', '0', '19.90', '011', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('7006', 'Sarmale moldoveneşti', 'mămăligă, morcov, orez, smântână, carne de porc, frunză de varză, carne de vită, ceapă albă', '0', '15.90', '011', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('7007', 'Tochitură moldovenească', 'pulpă porc, kaizer, cârnăciori, brânză burduf, mămăligă, ouă', '0', '24.90', '011', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('8000', 'Ceafă de porc la grătar', 'ceafă, pâine', '0', '15.90', '003', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('8001', 'Coaste la grătar', 'coaste de porc, cartofi prăjiţi, pâine, sos tzatziki', '0', '24.90', '003', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('8002', 'Cotlet de porc la grătar', 'cotlet porc, pâine', '0', '24.90', '003', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('8003', 'Frigărui de piept de pui', 'ardei gras, pâine, piept de pui, roşii', '0', '15.40', '003', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('8004', 'Frigărui de porc', 'ceapă rosie, cotlet porc, kaizer, pâine', '0', '16.40', '003', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('8005', 'Piept de pui la grătar', 'pâine, piept de pui', '0', '13.90', '003', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('8006', 'Platou gratar', 'cotlet porc, cârnăciori, cartofi prăjiţi, pâine, piept de pui, caşcaval pane', '0', '30.00', '003', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('8007', 'Pulpe de pui la grătar', 'pâine, pulpe de pui', '0', '14.00', '003', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('9000', 'Platou Carnivorous', 'ceafă, cotlet porc, pâine, pulpe de pui, ruladă de ceafă, piept de pui la grătar', '0', '64.00', '006', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('9001', 'Platou Tradiţional', 'cârnăciori, coaste de porc, ficaţei, pâine, cartofi ţărăneşti, minifripturi de porc, pârjoale moldoveneşti, murături asortate', '0', '59.00', '006', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10000', 'Aripioare aromate', 'aripioare, cartofi wedges, pâine, sos barbeque, susan', '0', '24.00', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10001', 'Aripioare Mamma Mia', 'aripioare, cartofi prăjiţi, pâine, salată de varză, sos tzatziki', '0', '22.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10002', 'Copănele Mamma Mia', 'copanele, cartofi prăjiţi, salată de varză, maioneză cu usturoi, pâine', '0', '22.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10003', 'Cordon bleu', 'piept de pui, piure, roşii, şuncă presată, mozzarella, pâine', '0', '20.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10004', 'Crispy la lipie', 'cartofi prăjiţi, roşii, sos cocktail, lipie, piept de pui uşor picant', '0', '13.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10005', 'Crispy Mamma Mia', 'cartofi prăjiţi, piept de pui uşor picant, salată de varză, maioneză cu usturoi, pâine', '0', '18.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10006', 'Ficăței la tigaie', 'ceapă albă, ficaţei, mămăligă, usturoi, brânză burduf', '0', '16.50', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10007', 'Ficăței Mamma Mia', 'ficaţei, usturoi, cartofi, salată de varză, maioneză cu usturoi, pâine', '0', '17.00', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10008', 'Frigărui de piept de pui', 'ardei gras, pâine, piept de pui, roşii', '0', '15.40', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10009', 'Piept de pui cu sos Teriyaki', 'pâine, piept de pui, ardei gras, ciuperci, ceapă albă, tăiţei, sos teriyaki', '0', '21.00', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10010', 'Piept de pui la grătar', 'pâine, piept de pui', '0', '13.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10011', 'Pui cu gorgonzola', 'gorgonzola, orez, pâine, piept de pui, smântână', '0', '25.50', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10012', 'Pui cu legume', 'ardei gras, cartofi, sos napoli, smântână, roşii, piept de pui, pâine, morcov, ceapă albă, mozzarella', '0', '20.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10013', 'Pui cu smântână', 'ceapă albă, piept de pui, smântână, mămăligă', '0', '18.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10014', 'Pui la cuptor', 'ardei gras, roşii, piept de pui, pâine, morcov baby, dovleac, conopidă, ciuperci, broccoli, usturoi', '0', '18.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10015', 'Pui Shanghai', 'ciuperci, orez, pâine, piept de pui, smântână, sos soia, curry, gogoşari în oţet', '0', '19.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10016', 'Pui vienez', 'ceapă albă, ciuperci, pâine, piept de pui, smântână, cartofi, mozzarella', '0', '10.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10017', 'Pulpe de pui la grătar', 'pâine, pulpe de pui', '0', '14.00', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10018', 'Saramură de pui', 'ardei gras, ardei iute, mămăligă, piept de pui, roşii', '0', '19.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10019', 'Shawarma la lipie', 'cartofi prăjiţi, castraveţi muraţi, sos cocktail, lipie, salată de varză, piept de pui uşor picant', '0', '15.40', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10020', 'Shawarma Mamma Mia', 'piept de pui uşor picant, cartofi prăjiţi, salată de varză, castraveţi muraţi, maioneză cu usturoi, pâine', '0', '18.90', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10021', 'Șnițel de pui', 'ouă, pâine, piept de pui', '0', '11.40', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10022', 'Șnițel Mamma Mia de pui', 'piept de pui, cartofi prăjiţi, salată de varză, maioneză cu usturoi, pâine', '0', '20.00', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10023', 'Şniţel parma', 'pâine, piept de pui, sos napoli, spaghetti, mozzarella', '0', '20.00', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10024', 'Specialitate Mamma Mia de pui', 'piept de pui, ciuperci, kaizer, ceapă albă, ardei gras, roşii, şuncă presată, sos demi glace, cartofi natur, mozzarella, pâine', '0', '25.00', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10025', 'Steak mexican de pui', 'ardei gras, sos napoli, roşii, porumb, piept de pui, pâine, dovleac, ciuperci, ardei iute, sos tabasco', '0', '23.40', '010', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('10026', 'Tochitură de pui', 'piept de pui, mămăligă, brânză burduf, usturoi, vin, ochiuri', '0', '20.90', '010', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('11000', 'Meniu de post', 'cartofi prăjiţi, pâine, salată de varză, ciuperci sote', '0', '14.40', '009', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('11001', 'Meniu Şniţel Soia', 'pâine, şniţel soia, cartofi copţi, salată de varză', '0', '14.40', '009', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('11002', 'Salată de vinete', 'vinete coapte, roşii, ceapă albă, pâine', '0', '9.50', '009', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('11003', 'Şniţel de legume', 'amestec de legume, cartofi prăjiţi, pâine, salată de varză', '0', '13.90', '009', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('11004', 'Tocăniţă cu legume şi ciuperci', 'amestec de legume, ardei gras, ceapă albă, ciuperci, morcov, pâine, sos napoli', '0', '14.40', '009', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('11005', 'Zacuscă', 'ceapă albă, pâine, ardei copţi, vinete coapte, gogoşari copţi', '0', '12.50', '009', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12000', 'Ceafă de porc la grătar', 'ceafă, pâine', '0', '15.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12001', 'Coaste de porc cu fasole', 'ceapă albă, coaste de porc, fasole, morcov, pâine, mărar, sos roşii', '0', '18.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12002', 'Coaste de porc cu varză', 'ceapă albă, coaste de porc, morcov, pâine, varză, mărar, sos roşii', '0', '16.50', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12003', 'Coaste de porc picante', 'ardei iute, coaste de porc, pâine, smântână, sos pimento, usturoi, cartofi, sos demi glace', '0', '25.00', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12004', 'Coaste la grătar', 'coaste de porc, cartofi prăjiţi, pâine, sos tzatziki', '0', '24.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12005', 'Cotlet cu parmezan', 'cotlet porc, ciuperci, pâine, parmesan, smântână', '0', '22.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12006', 'Cotlet de porc la grătar', 'cotlet porc, pâine', '0', '24.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12007', 'Frigărui de porc', 'ceapă rosie, cotlet porc, kaizer, pâine', '0', '16.40', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12008', 'Pârjoale moldoveneşti', 'carne de vită, pâine, piure, mărar, carne de porc', '0', '18.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12009', 'Purceluş ţărănesc', 'ceapă albă, ciuperci, cotlet porc, pâine, smântână, sos napoli, cartofi, mozzarella', '0', '22.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12010', 'Șnițel de porc', 'cotlet porc, ouă, pâine', '0', '13.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12011', 'Şniţel Mamma Mia de porc', 'cartofi prăjiţi, cotlet porc, salată de varză, maioneză cu usturoi, pâine', '0', '20.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12012', 'Specialitate Mamma Mia de porc', 'şuncă presată, cotlet porc, muşchi de porc, kaizer, cartofi natur, mozzarella, ardei gras, roşii, pâine, ciuperci, ceapă albă, sos demi glace', '0', '27.00', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12013', 'Specialitate Toscană', 'cotlet porc, pâine, piept de pui, smântână, usturoi, cartofi', '0', '23.00', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12014', 'Steak mexican de porc', 'ardei gras, sos napoli, roşii, porumb, pâine, dovleac, cotlet porc, ciuperci, ardei iute, sos tabasco', '0', '24.90', '008', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('12015', 'Tochitură moldovenească', 'pulpă porc, kaizer, cârnăciori, brânză burduf, mămăligă, ouă', '0', '24.90', '008', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13000', 'Broccoli sote', 'broccoli, parmesan, unt', '0', '9.00', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13001', 'Cartofi la cuptor', 'usturoi, cartofi, rozmarin', '0', '7.50', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13002', 'Cartofi nature', 'cartofi, pătrunjel, unt', '0', '5.90', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13003', 'Cartofi prăjiţi', 'cartofi', '0', '5.90', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13004', 'Cartofi țărănești', 'ceapă albă, kaizer, usturoi, cartofi', '0', '8.50', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13005', 'Ciuperci cu smântână', 'ceapă albă, ciuperci, smântână', '0', '9.40', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13006', 'Ciuperci sote', 'ceapă albă, ciuperci, usturoi', '0', '8.00', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13007', 'Fasole sote', 'ceapă albă, păstăi, usturoi', '0', '8.50', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13008', 'Focaccia cu mozzarella', 'mozzarella, oregano', '0', '5.00', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13009', 'Focaccia simplă', 'oregano, ulei măsline', '0', '3.00', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13010', 'Legume la grătar', 'ardei gras, ciuperci, dovleac, vinete', '0', '7.90', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13011', 'Mămăliguţă', '', '0', '2.00', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13012', 'Orez cu ciuperci', 'ceapă albă, ciuperci, orez, smântână', '0', '7.00', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13013', 'Orez cu legume', 'amestec de legume, orez', '0', '6.50', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13014', 'Pâine', '', '0', '1.50', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13015', 'Piure de cartofi', 'lapte, cartofi, unt', '0', '5.90', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13016', 'Salată castraveți murați', 'castraveţi muraţi', '0', '5.50', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13017', 'Salată de ardei copţi', 'oţet, ulei măsline, ardei copţi', '0', '8.50', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13018', 'Salată de roșii cu ceapă', 'ceapă albă, roşii, vinegreta', '0', '6.50', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13019', 'Salată de roșii cu feta', 'brânză feta, roşii, vinegreta', '0', '9.00', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13020', 'Salată de varză', 'oţet, varză, ulei', '0', '5.00', '002', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('13021', 'Salată gogoșari în oțet', 'gogoşari în oţet', '0', '6.50', '002', 'RON', NULL);

INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('14000', 'Sos cocktail', 'ketchup, maioneză', '0', '4.00', '014', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('14001', 'Sos maioneză', 'lămâie, muştar, ouă, ulei', '0', '3.00', '014', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('14002', 'Sos maioneză cu usturoi', 'lămâie, muştar, ouă, usturoi, ulei', '0', '3.00', '014', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('14003', 'Sos pizza dulce', 'busuioc, roşii decojite, usturoi, cimbru, vin, zahăr', '0', '2.50', '014', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('14004', 'Sos pizza picant', 'busuioc, chilly, roşii decojite, usturoi, cimbru, vin, zahăr', '0', '2.50', '014', 'RON', NULL);
INSERT INTO ITEM (Id, Title, Description, Discount, Price, CategoryId, CurrencyId, UnitId) VALUES ('14005', 'Sos tzatziki', 'castraveţi verzi, usturoi, iaurt, mărar', '0', '2.40', '014', 'RON', NULL);

<!-- ITEM_TAG -->
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1000', 'SP');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1001', 'SP');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1002', 'SP');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1003', 'SP');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1004', 'SP');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1005', 'SP');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1006', 'SP');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1000', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('3001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('4001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('5001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('6001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('7001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('8001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('9001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('11001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('12001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('13001', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10002', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10003', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10011', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10012', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10013', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('13010', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('13012', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('13016', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('13017', 'NEW');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('2004', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('2005', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('3002', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('4003', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('4004', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('4005', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('4006', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('4010', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('4011', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('5002', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('5005', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('6000', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('6004', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('6008', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('7003', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('7005', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('8006', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10001', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10002', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10005', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('10022', 'PROMO');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('1002', 'SOLD');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('6008', 'SOLD');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('6009', 'SOLD');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('7006', 'SOLD');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('12011', 'SOLD');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('12012', 'SOLD');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('13002', 'SOLD');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('14002', 'SOLD');
INSERT INTO ITEM_TAG (ItemId, TagId) VALUES ('14005', 'SOLD');

<!-- ORDERS -->
INSERT INTO DOC_ORDER (Id, DocStatusId, ContactId, EmployeeId, EmployeeName, Total, Discount, CurrencyId, Number, CreationDate) VALUES ('20ORD20200510144410', 'N', '100', '20', 'ursubogdan', '44', '0', 'RON', '1', '2020-05-06');
INSERT INTO DOC_ORDER (Id, DocStatusId, ContactId, EmployeeId, EmployeeName, Total, Discount, CurrencyId, Number, CreationDate) VALUES ('20ORD20200511144411', 'C', '106', '20', 'ursubogdan', '73', '0', 'RON', '1', '2020-01-06');
INSERT INTO DOC_ORDER (Id, DocStatusId, ContactId, EmployeeId, EmployeeName, Total, Discount, CurrencyId, Number, CreationDate) VALUES ('21ORD20200512144412', 'C', '101', '21', 'comanmihaela', '87', '0', 'RON', '2', '2020-05-06');
INSERT INTO DOC_ORDER (Id, DocStatusId, ContactId, EmployeeId, EmployeeName, Total, Discount, CurrencyId, Number, CreationDate) VALUES ('22ORD20200513144413', 'P', '102', '22', 'alexandralarisa', '34', '0', 'RON', '3', '2020-05-06');
INSERT INTO DOC_ORDER (Id, DocStatusId, ContactId, EmployeeId, EmployeeName, Total, Discount, CurrencyId, Number, CreationDate) VALUES ('23ORD20200514144414', 'S', '103', '23', 'gavrildaniel', '63', '0', 'RON', '4', '2020-05-06');
INSERT INTO DOC_ORDER (Id, DocStatusId, ContactId, EmployeeId, EmployeeName, Total, Discount, CurrencyId, Number, CreationDate) VALUES ('24ORD20200515144415', 'R', '104', '24', 'stanstefan', '86', '0', 'RON', '5', '2020-05-06');

<!-- ORDER ROWS -->
INSERT INTO ORDER_ROW (Id, OrderId, ItemId, UnitId, Total, Discount, Quantity, CurrencyId) VALUES ('20ORD202005101444101000', '20ORD20200510144410', '8000', 'KG', '44', '0', '1', 'RON');
INSERT INTO ORDER_ROW (Id, OrderId, ItemId, UnitId, Total, Discount, Quantity, CurrencyId) VALUES ('20ORD202005101444101001', '20ORD20200510144410', '8001', 'KG', '88', '0', '1', 'RON');
INSERT INTO ORDER_ROW (Id, OrderId, ItemId, UnitId, Total, Discount, Quantity, CurrencyId) VALUES ('20ORD202005111444111002', '20ORD20200511144411', '8002', 'KG', '66', '0', '1', 'RON');
INSERT INTO ORDER_ROW (Id, OrderId, ItemId, UnitId, Total, Discount, Quantity, CurrencyId) VALUES ('21ORD202005121444121003', '21ORD20200512144412', '8003', 'KG', '22', '0', '1', 'RON');
INSERT INTO ORDER_ROW (Id, OrderId, ItemId, UnitId, Total, Discount, Quantity, CurrencyId) VALUES ('22ORD202005131444131004', '22ORD20200513144413', '8004', 'KG', '45', '0', '1', 'RON');
INSERT INTO ORDER_ROW (Id, OrderId, ItemId, UnitId, Total, Discount, Quantity, CurrencyId) VALUES ('23ORD202005141444141005', '23ORD20200514144414', '8005', 'KG', '87', '0', '1', 'RON');
INSERT INTO ORDER_ROW (Id, OrderId, ItemId, UnitId, Total, Discount, Quantity, CurrencyId) VALUES ('24ORD202005151444151006', '24ORD20200515144415', '8006', 'KG', '41', '0', '1', 'RON');