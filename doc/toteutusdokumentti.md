Notaatiosta:

K = korttien lkm
C = kombinaatioiden lkm, joka on keskim��rin K^2, korkeintaan 2^K ja v�hint��n K

Ohjelman yleisrakenne sek� saavutetut aika- ja tilavaativuudet (m.m. O-analyysit pseudokoodista)
	Mainissa k�ytt�liittym�
	Teko�ly generoi itselleen minimaxpuun:
		Minimaxpuu alustaa ensin "StateConverter"-mapin, jonka avulla muutetaan peliss� olevat kortit longiksi. Long-arvoa k�ytet��n seuraavassa mapissa
		Minimaxpuu k�ytt�� my�s toista mappia, nimelt� NodeMap. T�m�n avulla StateConverterist� saatu longin avulla saadan selville solmu, joka on kuin tilanne joka loi longin.
		Eli toisin sanoin, StateConverterin ja NodeMapin avulla l�ydet��n nopeasti [O(K), miss� K on korttien lkm] samanlaisia solmuja.
		N�iden alustettua aloitetaan minimax:
			Minimaxin alussa katsotaan, onko solmu voittanut pelin. T�m� tarkistetaan kokeilemalla lis�t� solmun kortit p�yt��n. Jos p�yt�kortteien ja solmukorttien yhdestelm� on validi, solmu on voittanut. Korttikokoelman validisuustarkistus suorittuu ajassa O(K + c).
			Jos solmu ei voita, tarkistetaan seuraavaksi NodeMapist� l�ytyyk� samanlaisia solmuja. (metodi addPreExistingSuccessors)
				addPreExistingSuccessors laskee ensin pelitilanteen long-muotoon. Tapahtuu ajassa O(K). Huomiotavaa on se, ett� meit� kiinnostaa vain omien korttejen sek� p�yd�ll� olevien korttien tallentaminen NodeMappiin. T�m� selitet��n alla.
				Jos NodeMapist� l�ytyy samanlainen sisarsolmu, tarkistetaan jos se on identtinen solmun kanssa. Jos se on identtinen, voimme lopettaa solmun minimax metodi palauttamalla sisarsolmun arvon.
				Jos NodeMapist� l�ytyy samanlainen sisarsolmu joka ei ole identtinen solmun kanssa, tied�mme ett� solmun kortit ja p�yt�kortit ovat identtiset, mutta vastustajan kortit voi vaihdella. T�m�nlaiset solmut ovat kuitenkin meille hy�dyksi, sill� saadaan nopeasti laskettua solmun seuraajat sisarsolmusta.
					Tarkistetaan sisarsolmun seuraajat:
					
								Solmu				Sisarsolmu
					Kortit		A					A
					V.Kortit	B					D
					P�yt� K.	C					C
					
								Lapsi				Sisarenlapsi
					Kortit		X					X
					V.Kortit	B					D
					P�yt� K.	Z					Z
					
					Taulukko esitt�� solmun ja sisarsolmun kortteja.
					
					N�hd��n, ett� jos yhdistet��n solmun vastustajan kortit sek� sisarenlapsen kortit ja p�yt�kortit, luodaan solmulle sen lapsi. Ennen lapsen luomista tarkistetaan viel� NodeMapist� jos sielt� l�ytyisi identtinen lapsi.
					T�m�n avulla karsitaan noin 50% kaikkien seuraajien laskemisesta. Toimii lineaarisessa ajassa sisarlapsista riippuen
				Jos NodeMapist� ei l�ydy sisarsolmua, lis�t��n solmu NodeMappiin ja jatketaan minimax:ia.
			Takaisin minimaxissa. T�ss� vaiheessa, jos solmu ei voita, eik� l�ytynyt identtist� sisarsolmua, voidaan arvioida solmun pistem��r�, jos ohjelman rajoitus tulee vastaan. [1]
			Lasketaan seuraajat, jos niit� ei lis�tty addPreExistingSuccessorsissa [2]. T�m�n aikavaatimusta en saannut tarkalleen arvoitua. Metodi on suuremmaksi osaksi O(K + C), koska kortteja iteroidaan monenkertaisesti (muttei ikin� sis�kk�in) ja jokaista kombinaatiota kohtaan luodaan uusi solmu. Joitakin O(K^2) ja O(2^K) metodejakin l�ytyy, mutta niiden aikavaatimus on kuitenkin pienempi. PS. Seuraajien lukum��r� on rajoitettu 200:aan.
			Jokaista seuraaja kohti lasketaan sen arvo minimaxilla
				Koska minimax puussa valitaan aina vuorotellen paras/huonoin vaihtoehto, voimme alfa-beeta karsinnalla p��tt�� minimaxin ennen kaikkien seuraajien arvojen laskemista.
				T�m� johtuu siit�, kun l�ydet��n solmu jonka arvo on sen verran hyv�, ett� se p�ihitt�� solmun edelt�j�n arvon, edelt�j� tulee aina valita jonkun muun solmun (koska edelt�j� haluaa huonoimman solmun).
				Alfa-beetta karsinnasta: https://www.cs.cornell.edu/courses/cs312/2002sp/lectures/rec21.htm
			T�h�n loppuu minimax
		Puun generoitua saadaan puun juuresta paras ly�ntivaihtoehto. Lis�ksi NodeMapist� voimme l�yt�� (muttei aina) seuraavankin ly�nnin.
		
[1] Pisteiden arviointi
	Laskin korttikokoelman pist�m��r�n melko naivisesti k�ytt�m�ll� "hy�kk�yspisteit�" ja "puolustuspisteit�".
		Hy�kk�yspisteiden kaava on seuraava: Summaa suurimpien kombinaatioiden neli�t ja jaa se korttien lukum��r�n neli�ll�.
		T�ll�in jos on isoja kombinaatioita k�dess� saa paljon pisteit�.
		Puolustuspisteet lasketaan seuraavasti: Ota korttien arvojen lukum��r� kertaa v�rijakauma + suoranmuunnos bonus
		T�m� ei kuitenkaan ota huomioon ryhmi�, joten sit� voisi viel� viilata.
		Ideana kuitenin on se, ett� korteilla on vaikeampaa puolustaa jos on jokin heikkous. Siksi, jos on joko huonosti arvoja tai maata k�dess� saa v�hemm�n puolustuspisteit�.
	Huomattavaa on my�s se, ett� jos vastustaja voittaa seuraavalla ly�nnill��n, pisteit� v�hennet��n rajusti.

[2] Seuraajien luonti:
	Seuraajia voi olla seuraavanlaisia:
		Yksin�isi� kortteja
		Maata
		Suoraa
		Ryhmi�
		Maan muuttaminen suoraksi
		Lyhyen suoran muuttamisen ryhmiksi
		Ryhmien muuttaminen suuremmiksi
		Ryhmien muuttaminen isoksi, sykliseksi suoraksi
	N�ist� voisi viel� jatkaa pari sivua, mutta lyhenn�n sen toimintaa t�ss�. Kaikilla metodeilla on sama periaate, etsit��n tietynlaista seuraajaa ja jos sellainen l�ytyy, rakennetaan sen avulla lis�� seuraajia. Esimerkiksi jos muutetaan maa pareiksi, jatketaan parien lis��mist� uusiin seuraajiin.
	Kun kaikki seuraajat on laskettu j�rjestet��n ne suuruusj�rjestykseen niin, ett� ensin tulevat ne solmut, jossa l�ymme isoimman kombinaation. T�m� parantaa algoritmia huomattavasti.
		
Algoritmin aikavaatimusta on vaikea arvioida. Minimax metodissa luodaan tyhj�lle kent�lle C uutta solmua, ja ep�tyhj�lle p�yd�lle 1..C solmua. Sill� C ~ K^2 voimme arvioida aikavaativuuden exponentiaaliseksi korttien m��r�n suhteen. Huomataan my�s k�yt�nn�ss� korttien lis�tess�, ett� aikavaatimus nousee hyvin nopeasti.
Tilavaatimus on karkeasti solmujen m��r� sek� NodeMapin taulukon koko. T�m� kasvaa exponentiaalisesti aikavaatimuksen kanssa.
			
Suorituskyky- ja O-analyysivertailu

	Alla on taulukko, miss� eri parametreill� on suoritettu metodi estimationTestSetBig
	T�t� metodia olen k�ytt�nyt perustestin�.
	T�ss� analysoidaan kaikkien 90 eri nelj�n korttijoukon peli�.
	
	Taulukon vasemmalla puolella on merkitty 'softLimit / maxSuccessors'. MaxSuccessors rajaa nimen mukaan seuraajien m��r��.
	Sarakkeet W, L ja U tarkoittavat voittojen, h�vi�iden ja m��rittelem�tt�mien pelien m��ri�.
	
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
		
	10M/200		8:58	|	61	1	28		T�ss� j�rjestettiin seuraajat k��netisess� j�rjestyksess�
	10M/200		6:44	|	66	2	22		T�m� testi suoritettiin ennen kuin poistettiin turhat seuraajasolmut niiden arvojen laskemisen j�lkeen
	10M/200		5:02	|	68	2	20		T�ss� ei j�rjestetty seuraajia ollenkaan
	10M/200		5:16	|	77	3	10		
	10M/200		7:47	|	73	2	15		T�ss� testiss� olen korvannut kaikki tietorakenteet omillani.

	40M/200		N/A		|					N�m� kaikki kaatui muistin loppuessa
	40M/200		N/A		|					
	40M/200		N/A		|					Omilla tietorakenteilla
	40M/200		N/A		|	78	7	5		T�ss� on paras mahdollinen tulos lis��m�tt� muistia. Laskettu osissa.
	
	Taulukosta huomaamme, ett� softLimitill� on suora yhteys aikavaatimukseen. 

Ty�n mahdolliset puutteet ja parannusehdotukset

Kombinaatioiden m��r�� voisi pienent�� joukoilla, miss� kaikki maat ja korttikokoelmien luomat suorat olisivat omissa joukoissan yms. T�m�n avulla kombinaatioiden kelvollisuus voisi tarkistaa enemm�n "analyyttisesti", ja ehk� tuottaa paljon nopeamman algoritmin. Idea tuli kuitenkin sen verran my�h��n, etten olisi ehtinyt sen toteuttamista, mik�li se toimisi.

Pelitilanteen nopea arviointi voisi my�s parantaa. Testattuani arvioinnin oikeellisuutta, karkea arviointi tuottaa v�h�n yli 50%:sesti oikean tuloksen. Mutta, arviointi algoritmissa on huomattavasti parempi ja tuottaa melko usein (~70%) oikean arvion. T�m� ei kuitenkaan ole kovin tarkkaa.

L�hteet
	https://www.cs.cornell.edu/courses/cs312/2002sp/lectures/rec21.htm
	Javan omat tietorakenteet, mm. ArrayList, HashSet, HashMap, AbstractCollection sek� AbstractList.
	
	
	
	
	
	
	
	