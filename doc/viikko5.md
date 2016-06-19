# Viikkoraportti 5, 20.6.2016

Aikaa käytetty ainakin 30 tuntia.

#### Mitä olen tehnyt tällä viikolla? / Miten ohjelma on edistynyt?

Kirjoitin valmiiksi Minimax-puun, jonka jälkeen alkoi debug-helvetti. Laittomia pelitilanteita syntyi, tuli StackOverfloweja satunnaisesti yms. Kun luulin korjanneeni nämä ongelmat sain ohjelman suoritusnopeutta parannettua 4-10-kertaisesti käyttämällä jo laskettujen lapsia hyväksi. Rajoitin myös turhien oksien laskemista, ja hienosäädin ohjelman rajoittamista. 

Vielä tämän jälkeen tuli taas laittomia pelitilanteita yllättäen. Kahden tunnin debuggauksen jälkeen huomasin, että virhe syntyi rekursion poistamisen sekä optimoinnin yhteisenä seuraksena. Virheen sain korjattua poistamalla kelvottomien lasten uudelleenkäyttö.

Lisäsin vielä testausta. En ehtinyt saada Successor-luokan testejä valmiiksi täksi viikoksi...

Vielä lisäksi pyöritin testejä parantaakseni "tilannearviontia" tai pelitilanteen arvioivaa pisteyttämistä.

#### Mitä opin tällä viikolla / tänään?

Stackien seurantaa debugissa.
Debuggaus tekniikkaa, mistä löytää virhe.
Vaikka sitä jo teinkin, huomasin että kannatti kirjoittaa lyhyitä kommentteja huomattavista asioista

#### Mikä jäi epäselväksi tai tuottanut vaikeuksia?

Rekursiivien metodien debuggaus tuotti koko viikon aikana päänsärkyjä. Kun poistin turhat rekursiot niin kaikki tuntui sujuvan paremmin

#### Mitä teen seuraavaksi?

On vuorossa List:in, Set:in ja Map:in toteutus. Oli aikomus tehdä lista valmiiksi täksi viikoksi, mutta on ollut kiireitä minimax-puun kanssa.