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
                                30,
                               2300,
                               3,
                               10,
                               'Grafik & Design',
                               'Erfahrung mit Photoshop, Illustrator & InDesign',
                               'Gestaltung von verschiedenster Oberflächen (Visitenkarten, Plakate, ...)',
                               'Mediendesign'
                           );

insert into joboffer values(
                            25,
                               1800,
                               2,
                               11,
                               'Gastronomie',
                               'Erfahrung in Restaurants gesammelt',
                               'Wir suchen: Koch an Wochenenden',
                               'Koch an Wochenenden'
                           );

insert into joboffer values(
                            10,
                               1500,
                               2,
                               12,
                               'Gastronomie',
                               'Erfahrung in Restaurants gesammelt',
                               'Wir suchen: Kellner an Wochenenden',
                               'Kellner an Wochenenden'
                           );

insert into joboffer values(
                            38.5,
                               3000,
                               1,
                               13,
                               'Kartografie',
                               'langjährige Erfahrung als Kartografe',
                               'Zeichner für unterschiedliche terreale Unterwassergebiete',
                               'Kartografe (Meerebene)'
                           );


insert into applicant (firstname, lastname, email, password_login, resumeurl, descr, skilldescr, interestdescr, jobfield, jobbranche, preferablework, hoursperweek, commute, imageurl, address_id)
values(
          --                              15,
          'Georg',
          'Sonkl',
          'georgsonlk@gmail.com',
          'AKJSDBAKWJCKAWDJBDKAWJ.dlawcn938rzx293qe2x',
          null,
          'Für verschiedenste Unternehmen in dieser Branche gearbeitet',
          'Autodidakt',
          'kreative Freiheit',
          'Illustrator',
          'Mediendesign',
          'Banner und Produkt Design',
          15,
          true,
          null,
          101
      ),(
    'Ludwig',
    'Pramek',
    'ludWIGPramek@gmail.com',
    'DPWUOAFHNCPOWIUHNCAWFC.AQC)(/QNR=)(QZP)(QZBWCR/ZCB',
         null,
    '30 jährige Erfahrung als Koch im Restauraunt La Button. Organisiert und ehrgeizig zur vollen Kundenzufriedenheit',
    'Meister in der Küche (hinterfragen Sies erst garnicht)',
    'herausfordernde Gerichte zubereiten',
    'Koch',
    'Gastronomie',
    'Koch',
          25,
         false,
         null,
          100
      );
select * from applicant;
insert into action (applicant_id, company_id, actionname, actiondate) values(
--                              100,
                              2,
                              2,
                              'favorable',
                            '2023-12-03 15:06:24'
                         );

insert into action (applicant_id, company_id, actionname, actiondate) values(
--                              101,
                              2,
                              2,
                              'like',
                              '2024-01-03 00:00:00'

                         );

insert into application (applicant_id, joboffer_id, statustext) values(
--                               1,
                               2,
                               12,
                               'work in progress...'
                              );

insert into application (applicant_id, joboffer_id, statustext) values(
--                                2,
                               1,
                               12,
                               'seen and processed'
                              );