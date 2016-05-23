# Määrrittelydokumentti

## Tavoite

### Loppupelin tekoäly

Aikomuksena on luoda pelilleni WaffeGame 2:lle tekoäly jonka tulee päihittää minut omassa pelissäni! Ensimmäiseksi luon loppupelille tekoälyn, joka aktivoituu kun pelissä ei enään ole "piillotettua" tietoa. Tämä tapahtuu silloin, kun pakka loppuu ja pelaajan kortit ovat näkyvillä pöydällä.

Tämä loppupelin tekoäly on laskettava mitä korttikombinaatioita kannattaa lyödä niin, että riippumatta pelaajan vastauksesta tekoäly voittaa (jos mahdollista). Tekoälyn on pelattava hyvin myös silloin, vaikka se häviäisi.

Löytääkseni voittavan kombinaation käytän rekursiota, joka käy läpi kaikki kombinaatiot. Luen tästä vielä lisää ja otan esimerkkiä yksinkertaisista shakkitekoälyistä. Korttikombinaatiot (eli korttikokoelmat, joita pystyy lyödä) kasvavat ylilineaarisesti korttien lukumäärän suhteen, joten niitä on pystyttävä karsita jotenkin. Olen laskenut, että 9 kortista saa keskimäärin ~40 eri komb., vähintään 16 ja enintään 2^9-1 = 511 komb.

Esim. loppupelistä: (kannattaa ehkä kaivata korttipakka esiin visualisoimiseksi)

Tekoäly: pJ (pata jätkä), ri8, ru5, ru4, ru2, h6, hQ

Pelaaja: pA, p8, p7

Tässä tapauksessa, kun on tekoälyn vuoro pelata, huomataan kaksi ongelmakorttia:

- pJ, koska jos sen lyö yksinäin, pelaaja voittaa heti lyömällä samaa maata.

- ri8, koska silloin pelaaja pystyy muodostaa parin p8:nsa avulla, jota tekoäly ei voi vastata. Pelaaja voittaa seuraavalla aloituksella.

Tekoäly kuitenkin voittaa, kun lasketaan seuraava kombinaatio:

- Ensin suora: hQ, pJ. Pelaaja ei voi vastata. Uusi aloitus

- Toinen suora: h6, pelaajan paras vastaus on p7. Tekoäly pelaa ru5. Pelaajan on pakko pelata p8 jolloin vastataan ru4. Pelaaja ei voi vastata joten on taas uusi aloitus

- Tekoäly pelaa ri8. Uusi aloitus

- Tekoäly voittaa pelaamalla viimeisen korttinsa ru2.

### Alkupelin tekoäly

Alkupelissä on tarkoitus estää vastustajaa voittamasta/keräämästä hyvän käden ja lisäksi rakentaa omaa kättä. Koska kyseessä ei ole täydellisen informaation peli on tekoälyn luominen haastavampaa. Voin kokeilla pseudo-geneettistä koodia, missä luon parametreja kuten "korttipihi" tai "varovainen" jotka tietokone sitten hienosäätää. Näistä voi syntyä monta erillaisesti pelaavia vastustajia.


