# Viikkoraportti 5, 20.6.2016

Aikaa k�ytetty ainakin 30 tuntia.

#### Mit� olen tehnyt t�ll� viikolla? / Miten ohjelma on edistynyt?

Kirjoitin valmiiksi Minimax-puun, jonka j�lkeen alkoi debug-helvetti. Laittomia pelitilanteita syntyi, tuli StackOverfloweja satunnaisesti yms. Kun luulin korjanneeni n�m� ongelmat sain ohjelman suoritusnopeutta parannettua 4-10-kertaisesti k�ytt�m�ll� jo laskettujen lapsia hyv�ksi. Rajoitin my�s turhien oksien laskemista, ja hienos��din ohjelman rajoittamista. 

Viel� t�m�n j�lkeen tuli taas laittomia pelitilanteita yll�tt�en. Kahden tunnin debuggauksen j�lkeen huomasin, ett� virhe syntyi rekursion poistamisen sek� optimoinnin yhteisen� seuraksena. Virheen sain korjattua poistamalla kelvottomien lasten uudelleenk�ytt�.

Lis�sin viel� testausta. En ehtinyt saada Successor-luokan testej� valmiiksi t�ksi viikoksi...

Viel� lis�ksi py�ritin testej� parantaakseni "tilannearviontia" tai pelitilanteen arvioivaa pisteytt�mist�.

#### Mit� opin t�ll� viikolla / t�n��n?

Stackien seurantaa debugissa.
Debuggaus tekniikkaa, mist� l�yt�� virhe.
Vaikka sit� jo teinkin, huomasin ett� kannatti kirjoittaa lyhyit� kommentteja huomattavista asioista

#### Mik� j�i ep�selv�ksi tai tuottanut vaikeuksia?

Rekursiivien metodien debuggaus tuotti koko viikon aikana p��ns�rkyj�. Kun poistin turhat rekursiot niin kaikki tuntui sujuvan paremmin

#### Mit� teen seuraavaksi?

On vuorossa List:in, Set:in ja Map:in toteutus. Oli aikomus tehd� lista valmiiksi t�ksi viikoksi, mutta on ollut kiireit� minimax-puun kanssa.