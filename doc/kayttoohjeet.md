.jar tiedosto löytyy kansiosta WG2-tekoaly/WaffeGame2-tekoaly/dist

- Aja tiedosto.
- Kun valikko esiintyy, kirjoita valintasi numerona ja syötä se.
- Jos valitset 1, eli analyysin, lisää kortteja seuraavassa muodossa:

		Kortti lisätään kirjoittamalla kaksi kirjainta
		
		Ensimmäinen kirjain on kortin arvo. Arvo voi olla numero 2-9 tai A, T (kymppi), J, Q, K.
		
		Toinen kirjain on kortin maa. Maa voi olla H (hertta), D (ruutu), C (risti) tai S (pata).

		Joten esim. Pata ässä olisi AS, risti kymppi olisi TC ja nelonen hertta 4H.
		
		Satunnaisia kortteja voi lisätä nopeasti kirjoittamalla R#, missä # on korttien määrä (1 - 30).
		
		Lisäksi voi nopeasti lisätä kortti joukkoja, kirjoittamalla S#, missä # on numero 1-6. Kortti joukoista lisää ohjelman Helpissä
		
- Kun pelaat tietokonetta vastaan, valitse kortit kirjoittamalla valintasi kaikkien korttien luvut välilyönnillä erotettuna.

		Esim. Jos haluat lyöda kortit 2, 3 ja 4. Kirjoita '2 3 4'.

		[1]: 2 of Heart
		[2]: 7 of Diamonds
		[3]: 8 of Clubs
		[4]: 9 of Diamonds
		
		Jos et pysty lyödä voit passata jättämällä syötteen tyhjäksi.
		
- Big test: Ohjelma laskee 90 ison testin tulokset. Tätä testiä käytin vertailuissa.

- Jos haluat muuttaa ohjelman tekoälyn tarkkuutta, muuta soft limitiä. VAROITUS! Ohjelman muisti voi loppua kesken jos tätä nostaa liikaa!