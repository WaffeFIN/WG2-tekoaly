Notaatiosta:

K = korttien lkm
C = kombinaatioiden lkm, joka on keskimäärin K^2, korkeintaan 2^K ja vähintään K

Ohjelman yleisrakenne sekä saavutetut aika- ja tilavaativuudet (m.m. O-analyysit pseudokoodista)
	Mainissa käyttöliittymä
	Tekoäly generoi itselleen minimaxpuun:
		Minimaxpuu alustaa ensin "StateConverter"-mapin, jonka avulla muutetaan pelissä olevat kortit longiksi. Long-arvoa käytetään seuraavassa mapissa
		Minimaxpuu käyttää myös toista mappia, nimeltä NodeMap. Tämän avulla StateConverteristä saatu longin avulla saadan selville solmu, joka on kuin tilanne joka loi longin.
		Eli toisin sanoin, StateConverterin ja NodeMapin avulla löydetään nopeasti [O(K), missä K on korttien lkm] samanlaisia solmuja.
		Näiden alustettua aloitetaan minimax:
			Minimaxin alussa katsotaan, onko solmu voittanut pelin. Tämä tarkistetaan kokeilemalla lisätä solmun kortit pöytään. Jos pöytäkortteien ja solmukorttien yhdestelmä on validi, solmu on voittanut. Korttikokoelman validisuustarkistus suorittuu ajassa O(K + c).
			Jos solmu ei voita, tarkistetaan seuraavaksi NodeMapistä löytyykö samanlaisia solmuja. (metodi addPreExistingSuccessors)
				addPreExistingSuccessors laskee ensin pelitilanteen long-muotoon. Tapahtuu ajassa O(K). Huomiotavaa on se, että meitä kiinnostaa vain omien korttejen sekä pöydällä olevien korttien tallentaminen NodeMappiin. Tämä selitetään alla.
				Jos NodeMapistä löytyy samanlainen sisarsolmu, tarkistetaan jos se on identtinen solmun kanssa. Jos se on identtinen, voimme lopettaa solmun minimax metodi palauttamalla sisarsolmun arvon.
				Jos NodeMapistä löytyy samanlainen sisarsolmu joka ei ole identtinen solmun kanssa, tiedämme että solmun kortit ja pöytäkortit ovat identtiset, mutta vastustajan kortit voi vaihdella. Tämänlaiset solmut ovat kuitenkin meille hyödyksi, sillä saadaan nopeasti laskettua solmun seuraajat sisarsolmusta.
					Tarkistetaan sisarsolmun seuraajat:
					
								Solmu				Sisarsolmu
					Kortit		A					A
					V.Kortit	B					D
					Pöytä K.	C					C
					
								Lapsi				Sisarenlapsi
					Kortit		X					X
					V.Kortit	B					D
					Pöytä K.	Z					Z
					
					Taulukko esittää solmun ja sisarsolmun kortteja.
					
					Nähdään, että jos yhdistetään solmun vastustajan kortit sekä sisarenlapsen kortit ja pöytäkortit, luodaan solmulle sen lapsi. Ennen lapsen luomista tarkistetaan vielä NodeMapistä jos sieltä löytyisi identtinen lapsi.
					Tämän avulla karsitaan noin 50% kaikkien seuraajien laskemisesta. Toimii lineaarisessa ajassa sisarlapsista riippuen
				Jos NodeMapistä ei löydy sisarsolmua, lisätään solmu NodeMappiin ja jatketaan minimax:ia.
			Takaisin minimaxissa. Tässä vaiheessa, jos solmu ei voita, eikä löytynyt identtistä sisarsolmua, voidaan arvioida solmun pistemäärä, jos ohjelman rajoitus tulee vastaan. [1]
			Lasketaan seuraajat, jos niitä ei lisätty addPreExistingSuccessorsissa [2]. Tämän aikavaatimusta en saannut tarkalleen arvoitua. Metodi on suuremmaksi osaksi O(K + C), koska kortteja iteroidaan monenkertaisesti (muttei ikinä sisäkkäin) ja jokaista kombinaatiota kohtaan luodaan uusi solmu. Joitakin O(K^2) ja O(2^K) metodejakin löytyy, mutta niiden aikavaatimus on kuitenkin pienempi. PS. Seuraajien lukumäärä on rajoitettu 200:aan.
			Jokaista seuraaja kohti lasketaan sen arvo minimaxilla
				Koska minimax puussa valitaan aina vuorotellen paras/huonoin vaihtoehto, voimme alfa-beeta karsinnalla päättää minimaxin ennen kaikkien seuraajien arvojen laskemista.
				Tämä johtuu siitä, kun löydetään solmu jonka arvo on sen verran hyvä, että se päihittää solmun edeltäjän arvon, edeltäjä tulee aina valita jonkun muun solmun (koska edeltäjä haluaa huonoimman solmun).
				Alfa-beetta karsinnasta: https://www.cs.cornell.edu/courses/cs312/2002sp/lectures/rec21.htm
			Tähän loppuu minimax
		Puun generoitua saadaan puun juuresta paras lyöntivaihtoehto. Lisäksi NodeMapistä voimme löytää (muttei aina) seuraavankin lyönnin.
		
[1] Pisteiden arviointi
	Laskin korttikokoelman pistämäärän melko naivisesti käyttämällä "hyökkäyspisteitä" ja "puolustuspisteitä".
		Hyökkäyspisteiden kaava on seuraava: Summaa suurimpien kombinaatioiden neliöt ja jaa se korttien lukumäärän neliöllä.
		Tällöin jos on isoja kombinaatioita kädessä saa paljon pisteitä.
		Puolustuspisteet lasketaan seuraavasti: Ota korttien arvojen lukumäärä kertaa värijakauma + suoranmuunnos bonus
		Tämä ei kuitenkaan ota huomioon ryhmiä, joten sitä voisi vielä viilata.
		Ideana kuitenin on se, että korteilla on vaikeampaa puolustaa jos on jokin heikkous. Siksi, jos on joko huonosti arvoja tai maata kädessä saa vähemmän puolustuspisteitä.
	Huomattavaa on myös se, että jos vastustaja voittaa seuraavalla lyönnillään, pisteitä vähennetään rajusti.

[2] Seuraajien luonti:
	Seuraajia voi olla seuraavanlaisia:
		Yksinäisiä kortteja
		Maata
		Suoraa
		Ryhmiä
		Maan muuttaminen suoraksi
		Lyhyen suoran muuttamisen ryhmiksi
		Ryhmien muuttaminen suuremmiksi
		Ryhmien muuttaminen isoksi, sykliseksi suoraksi
	Näistä voisi vielä jatkaa pari sivua, mutta lyhennän sen toimintaa tässä. Kaikilla metodeilla on sama periaate, etsitään tietynlaista seuraajaa ja jos sellainen löytyy, rakennetaan sen avulla lisää seuraajia. Esimerkiksi jos muutetaan maa pareiksi, jatketaan parien lisäämistä uusiin seuraajiin.
	Kun kaikki seuraajat on laskettu järjestetään ne suuruusjärjestykseen niin, että ensin tulevat ne solmut, jossa löymme isoimman kombinaation. Tämä parantaa algoritmia huomattavasti.
		
Algoritmin aikavaatimusta on vaikea arvioida. Minimax metodissa luodaan tyhjälle kentälle C uutta solmua, ja epätyhjälle pöydälle 1..C solmua. Sillä C ~ K^2 voimme arvioida aikavaativuuden exponentiaaliseksi korttien määrän suhteen. Huomataan myös käytännössä korttien lisätessä, että aikavaatimus nousee hyvin nopeasti.
Tilavaatimus on karkeasti solmujen määrä sekä NodeMapin taulukon koko. Tämä kasvaa exponentiaalisesti aikavaatimuksen kanssa.
			
Suorituskyky- ja O-analyysivertailu

	Alla on taulukko, missä eri parametreillä on suoritettu metodi estimationTestSetBig
	Tätä metodia olen käyttänyt perustestinä.
	Tässä analysoidaan kaikkien 90 eri neljän korttijoukon peliä.
	
	Taulukon vasemmalla puolella on merkitty 'softLimit / maxSuccessors'. MaxSuccessors rajaa nimen mukaan seuraajien määrää.
	Sarakkeet W, L ja U tarkoittavat voittojen, häviöiden ja määrittelemättömien pelien määriä.
	
				Time	|	W	L	U
	40K/16		0:06	|	4	0	86
	40K/70		0:07	|	9	0	81
	40K/200		0:08	|	12	0	78
	
	400K/16		0:28	|	19	0	71
	400K/70		0:37	|	27	0	63
	400K/200	0:44	|	27	0	63
	
	4M/16		2:17	|	42	14	34
	4M/70		3:05	|	52	3	35
	4M/200		3:15	|	55	0	35
	4M/200		3:39	|	64	0	26		Omilla tietorakenteilla
		
	10M/200		8:58	|	61	1	28		Tässä järjestettiin seuraajat käänetisessä järjestyksessä
	10M/200		6:44	|	66	2	22		Tämä testi suoritettiin ennen kuin poistettiin turhat seuraajasolmut niiden arvojen laskemisen jälkeen
	10M/200		5:02	|	68	2	20		Tässä ei järjestetty seuraajia ollenkaan
	10M/200		5:16	|	77	3	10		
	10M/200		7:47	|	73	2	15		Tässä testissä olen korvannut kaikki tietorakenteet omillani.

	40M/200		N/A		|					Nämä kaikki kaatui muistin loppuessa
	40M/200		N/A		|					
	40M/200		N/A		|					Omilla tietorakenteilla
	40M/200		N/A		|	78	7	5		Tässä on paras mahdollinen tulos lisäämättä muistia. Laskettu osissa.
	
	Taulukosta huomaamme, että softLimitillä on suora yhteys aikavaatimukseen. 

Työn mahdolliset puutteet ja parannusehdotukset

Kombinaatioiden määrää voisi pienentää joukoilla, missä kaikki maat ja korttikokoelmien luomat suorat olisivat omissa joukoissan yms. Tämän avulla kombinaatioiden kelvollisuus voisi tarkistaa enemmän "analyyttisesti", ja ehkä tuottaa paljon nopeamman algoritmin. Idea tuli kuitenkin sen verran myöhään, etten olisi ehtinyt sen toteuttamista, mikäli se toimisi.

Pelitilanteen nopea arviointi voisi myös parantaa. Testattuani arvioinnin oikeellisuutta, karkea arviointi tuottaa vähän yli 50%:sesti oikean tuloksen. Mutta, arviointi algoritmissa on huomattavasti parempi ja tuottaa melko usein (~70%) oikean arvion. Tämä ei kuitenkaan ole kovin tarkkaa.

Lähteet
	https://www.cs.cornell.edu/courses/cs312/2002sp/lectures/rec21.htm
	Javan omat tietorakenteet, mm. ArrayList, HashSet, HashMap, AbstractCollection sekä AbstractList.
	
	
	
	
	
	
	
	