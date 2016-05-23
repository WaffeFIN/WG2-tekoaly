# WG2-tekoaly
Tekoäly omalle korttipelilleni. 

WaffeGame 2.3 (nimi työn alla) on kahden hengen korttipeli, missä voittaja on se joka on ensin ilman kortteja. Peliä pelataan kierroksissa, missä lyödään kortteja pöytään tiettyjen sääntöjen mukaan.
Pöydässä olevien korttien ovat oltava samaa lajia, eli samaa maata, arvoa, osa samaa suoraa tai samoissa ryhmissä. Seuraavat tyypit ovat valideja: maa, suora, parit, kolmoset, neloset. Suorassa kaikki kortit kuuluvat samaan yhtenäiseen suoraan.

Kortteja saa lyödä niin monta kuin haluaa, kunhan lopputulos on jotakin mainituista tyypeistä. Jos ei pysty/halua lyödä, niin asetetaan korttit näkyviin pöydälle, nostetaan 5 korttia käteen ja se on toisen vuoro aloittaa.

Huom! Jos pystyy lyödä jotain näkyvillä korteilla on pakko lyödä.

Aluksi jaetaan 8 korttia jokaiselle pelaajalle (9 jos on jokerit mukana) sekä yhden kortin pöydälle. Tämä pöytäkortti lyödään vain aivan alussa ja sen tarkoitus on tasoittaa aloittajan etua.

Huom! Suora on syklinen ja loputon. Tästä seuraa se, että täydellisessä suorassa (jossa on 13, 26 tai 39 korttia) saa lyödä minkä kortin tahansa. 

Alkupelille ja loppupelille tulee erilliset tekoälyt. Alkupelissä tekoälyn on rakennettava itselleen hyvä käsi loppupelille. Loppupeli on pelkkää laskemista. Tekoälystä lisää aihemäärrittelyssä.

Tässä esimerkki pelin pelaamisesta:

Pelaaja A pelaa: h3 (hertta 3) ja h6:n pöydälle. Pöydän tyyppi on tällä hetkellä "Maa".

[h3] [h6]

Siten pelaaja B voi esim. lyödä: p4 ja ru5, muuttaen pöydän tyyppiä suoraksi.

h3 [p4] [ru5] h6

Pelaaja A voi jatkaa suoraa ri2:lla... 

[ri2] h3 p4 ru5 h6

...tai jopa muuttaa kaikki arvot 3 -> 6 pareiksi, jos kortteja siihen löytyy.

h3 [p3] p4 [ri4] ru5 [ri5] h6 [ri6] 

Huom! Jos haluaa muuttaa kortit pareiksi niin ei saa jättää mitään korttia yksin. Kaikki tai ei mitään!