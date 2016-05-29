# Viikkoraportti 2, 29.5.2016

Aikaa käytetty 15 tuntia.

#### Mitä olen tehnyt tällä viikolla? / Miten ohjelma on edistynyt?

Importtasin vanhoja luokkia ohjelmointi projektistani. Huomasin heti ongelmia tekoälyn implementoinnissa (vaikka se oli mielessä ohjelmoidessani ohjelmaa), joten suuri osa ajasta meni vanhojen luokkien korjaamiseen.  Koska kyseessä on oma peli, jota parantelen jatkuvasti, piti myös päivittää vanhat säännöt uusiksi. Isoin ongelma tuli kuitenkin vastaan kun ohjelma ei kerta kaikkiaan toiminnut tekstikäyttöliittymän kanssa, joten joudun luultavasti luomaan uuden sellaisen (sitten myöhemmin).

Lopuksi lisäsin dokumentaatiota. Testejä en ole vielä luonnut, sillä ei ole mitään testattavaa (lukuunottamatta importatut luokat)

#### Mitä opin tällä viikolla / tänään?

- Aina ei pysty ajatella kaikkea tulevaa kun ohjelmoi.
- Enumeja ei voi extendata.
- Interfacejä ei voi switchata.
- Minimax puu sekä alfa-beeta karsinta: https://www.cs.cornell.edu/courses/cs312/2002sp/lectures/rec21.htm
- Keksin hyvän alkupeli strategian, jota luultavasti tulen implementoimaan. Tämä ei kuitenkaan ole helppoa, sillä tämä käyttää edelleen "pehmeitä muuttujia".

#### Mikä jäi epäselväksi tai tuottanut vaikeuksia?

Korttikombinaatioiden laskeminen tuottaa luultavasti kovaa hidastumista. Lisäksi on vaikeaa yrittää vähentää muistivaatimusta uhraamatta laskunopeutta. Yritän pähkäillä nopeita metodeja minimoida validien kombinaatioiden kokeilujen määrää.

Kysymys: Jos importtaan luokkia, tulen varmaan myös testaa niitä uudelleen? Voin varmaan importata vanhat testitkin, jos näin on

#### Mitä teen seuraavaksi?

Jatkan minimax algoritmiä ja lisään alfa-beeta karsinnan