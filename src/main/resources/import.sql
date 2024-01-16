truncate table address cascade;
truncate table company cascade;
truncate table joboffer cascade;
truncate table applicant cascade;

insert into address values(
                              4040,
                              5,
                              'Linz',
                              'Austria',
                              'Upper Austria',
                              'Feldner Straße',
                              13
                          );

insert into address values(
                              4190,
                              6,
                              'Bad Leonfelden',
                              'Österreich',
                              'Oberösterreich',
                              'Glashausstraße',
                              2
                          );

insert into address values(
                              10600,
                              7,
                              'Prag',
                              'Tschechien',
                              'obkghew',
                              'Radlweg',
                              4
                          );

insert into address values(
                              1220,
                              100,
                              'Wien',
                              'Österreich',
                              'Wien',
                              'Kirchenplatz',
                              2
                          );

insert into address values(
                              4770,
                              101,
                              'Andorf',
                              'Österreich',
                              'Oberösterreich',
                              'Radlern',
                              4
                          );

insert into company values(
                              7,
                              1,
                              'Kartografie',
                              'Nami Kartografie',
                              'namiKun@gmail.com',
                              null,
                              null,
                              'nami-kartography.com'
                          );

insert into company values(
                              6,
                              2,
                              'Gastwirtschaft',
                              'Sanjis Kochstube',
                              'sanj@gmail.com',
                              null,
                              null,
                              'sanji.com'
                          );

insert into company values(
                              5,
                              3,
                              'Grafik & Design',
                              'Crop 7 GmbH',
                              'crop7-design@gmail.com',
                              null,
                              null,
                              'crops-seven.com'
                          );


insert into joboffer values(
                               2300,
                               3,
                               10,
                               'Grafik & Design',
                               'Erfahrung mit Photoshop, Illustrator & InDesign',
                               'Gestaltung von verschiedenster Oberflächen (Visitenkarten, Plakate, ...)',
                               'Mediendesign'
                           );

insert into joboffer values(
                               1800,
                               2,
                               11,
                               'Gastronomie',
                               'Erfahrung in Restaurants gesammelt',
                               'Wir suchen: Koch an Wochenenden',
                               'Koch an Wochenenden'
                           );

insert into joboffer values(
                               1500,
                               2,
                               12,
                               'Gastronomie',
                               'Erfahrung in Restaurants gesammelt',
                               'Wir suchen: Kellner an Wochenenden',
                               'Kellner an Wochenenden'
                           );

insert into joboffer values(
                               3000,
                               1,
                               13,
                               'Kartografie',
                               'langjährige Erfahrung als Kartografe',
                               'Zeichner für unterschiedliche terreale Unterwassergebiete',
                               'Kartografe (Meerebene)'
                           );

insert into applicant values(
                                false,
                                25,
                                100,
                                14,
--                                 '2008-10-5',
                                '30 jährige Erfahrung als Koch im Restauraunt La Button. Organisiert und ehrgeizig zur vollen Kundenzufriedenheit',
                                'ludPram@gmail.com',
                                'Ludwig',
                                null,
                                'herausfordernde Gerichte zubereiten',
                                'Gastronomie',
                                'Koch',
                                'Pramek',
                                'DPWUOAFHNCPOWIUHNCAWFC.AQC)(/QNR=)(QZP)(QZBWCR/ZCB',
                                'Koch',
                                null,
                                'Meister in der Küche (hinterfragen Sies erst garnicht)'
                         );

insert into applicant --(id, firstname, lastname, jobbranche)
                        values(
                                true,
                                15,
                                101,
                                15,
--                                '2015-2-13',
                                'Für verschiedenste Unternehmen in dieser Branche gearbeitet',
                                'georgsonlk@gmail.com',
                                'Georg',
                                null,
                                'kreative Freiheit',
                                'Mediendesign',
                                'Illustrator',
                                'Sonkl',
                                'AKJSDBAKWJCKAWDJBDKAWJ.dlawcn938rzx293qe2x',
                                'Banner und Produkt Design',
                                null,
                                'Autodidakt'
                         );

insert into action values(
                              '2023-12-03',
                              14,
                              2,
                              1,
                              'favorable'
                         );

insert into application values(
                               14,
                               1,
                               12
                              );