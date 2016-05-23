# M��rrittelydokumentti

## Tavoite

### Loppupelin teko�ly

Aikomuksena on luoda pelilleni WaffeGame 2:lle teko�ly jonka tulee p�ihitt�� minut omassa peliss�ni! Ensimm�iseksi luon loppupelille teko�lyn, joka aktivoituu kun peliss� ei en��n ole "piillotettua" tietoa. T�m� tapahtuu silloin, kun pakka loppuu ja pelaajan kortit ovat n�kyvill� p�yd�ll�.

T�m� loppupelin teko�ly on laskettava mit� korttikombinaatioita kannattaa ly�d� niin, ett� riippumatta pelaajan vastauksesta teko�ly voittaa (jos mahdollista). Teko�lyn on pelattava hyvin my�s silloin, vaikka se h�vi�isi.

L�yt��kseni voittavan kombinaation k�yt�n rekursiota, joka k�y l�pi kaikki kombinaatiot. Luen t�st� viel� lis�� ja otan esimerkki� yksinkertaisista shakkiteko�lyist�. Korttikombinaatiot (eli korttikokoelmat, joita pystyy ly�d�) kasvavat ylilineaarisesti korttien lukum��r�n suhteen, joten niit� on pystytt�v� karsita jotenkin. Olen laskenut, ett� 9 kortista saa keskim��rin ~40 eri komb., v�hint��n 16 ja enint��n 2^9-1 = 511 komb.

Esim. loppupelist�: (kannattaa ehk� kaivata korttipakka esiin visualisoimiseksi)

Teko�ly: pJ (pata j�tk�), ri8, ru5, ru4, ru2, h6, hQ

Pelaaja: pA, p8, p7

T�ss� tapauksessa, kun on teko�lyn vuoro pelata, huomataan kaksi ongelmakorttia:

- pJ, koska jos sen ly� yksin�in, pelaaja voittaa heti ly�m�ll� samaa maata.

- ri8, koska silloin pelaaja pystyy muodostaa parin p8:nsa avulla, jota teko�ly ei voi vastata. Pelaaja voittaa seuraavalla aloituksella.

Teko�ly kuitenkin voittaa, kun lasketaan seuraava kombinaatio:

- Ensin suora: hQ, pJ. Pelaaja ei voi vastata. Uusi aloitus

- Toinen suora: h6, pelaajan paras vastaus on p7. Teko�ly pelaa ru5. Pelaajan on pakko pelata p8 jolloin vastataan ru4. Pelaaja ei voi vastata joten on taas uusi aloitus

- Teko�ly pelaa ri8. Uusi aloitus

- Teko�ly voittaa pelaamalla viimeisen korttinsa ru2.

### Alkupelin teko�ly

Alkupeliss� on tarkoitus est�� vastustajaa voittamasta/ker��m�st� hyv�n k�den ja lis�ksi rakentaa omaa k�tt�. Koska kyseess� ei ole t�ydellisen informaation peli on teko�lyn luominen haastavampaa. Voin kokeilla pseudo-geneettist� koodia, miss� luon parametreja kuten "korttipihi" tai "varovainen" jotka tietokone sitten hienos��t��. N�ist� voi synty� monta erillaisesti pelaavia vastustajia.


