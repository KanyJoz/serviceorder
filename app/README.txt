1) Általános tömör leírás a programról:
- a program egy egyszerű CRUD Android program, Java-ban írva
- Service Order objektumokat modellezz
- az elképzelt szituáció úgy néz ki, hogy ha valaki rendel online valami terméket vagy termékeket, akkor ez a rendelés beérkezzik egy admin-hoz és
    a vásárló adatai alapján (ezek képzletbeliek) ő fogja rögzíteni ebben az alkalamzásban az adatokat
- ezen kívül különböző módon lsitázni és módosítani és törölni és szűrni tudja ezeket a megrendeléseket vagy Service Ordereket
- az alkalamzás angol nyelvű felhasználót feltételez

2) Feature lista:
- a főképernyőn 4 gomb fogad minket melyekkel fentről lefelé
    --> lehetőségünk van felvinni egy új megrendelést, mely a C betű a CRUD-ből
    --> itt meg kell adni a kategóriát pl.: Eletronics, leírást pl.: Eletronic machines for cleaning
    --> ezt követően meg kell adni a megrendelő e-mail címét, ez a kontaktszemély elérése
    --> ki kell választaunk a prioritását a szállítmánynak, mely a háttérben befolyásolja azt hogy mikorra várható a rendelés kiszállítása
    --> meg kell adni, hogy milyen állapotban van egy Spinner segítésgével, ezt az enumerationt a dokumentum szabdta meg
    --> végül meg kell adni, hogy pontosan milyen árucikkek szerepelnek, milyen szállítók vesznek részt ezen megrendelésben és milyen kiegészítő információk vannak
        --> ezeket az információkat ENTER-el kell tagolni úgy, hogy az uoltós sor végére nem ütünk ENTER-t és a háttérben majd a stringkezelő függvények dolgoznak és listát készítenek
    --> a CREATE Service Order gombbal létrejön a Firestore adatbázisban az entry, míg a Cancel gomb visszalép az előző képernyőre

    --> a második, harmadik és negyedik gomb valósítja meg a CRUD Read lehetőségét, mindhárom egy Recycler view segítségével listázza az adatbázisban szereplő elelemket
    --> a msáodik gombm inden elemet, a harmadik gomb csak a visszavont megrendeléseket, míg a negyedik gomb a már telejsített megrendeléseket
    --> ezeken a Recycler view-kon felül van egy Searchbar, amibel KATEGÓRIA alapján filterezhetjük a emgrendeléseket szűrés céljából
    --> illetve van egy Back gomb ami visszadob a főképernyőre

    --> az egyes elemeken belül a Modify gomb vlaósítja meg az adott Service ORderre vonatkozo CRUD U betűt

    --> az egyes elemekn belül a Delete gomb valósítja meg az egyes Service ORdereken belül a CRUD D betűt

    --> illetve még a Cancel gomb az kér egy Reason-t és beállítja az aktuálsi dátumot és Reason a cancellation mögé, és így az order bekerül a visszavont elemek közé
    --> az áruk akkor kerülnek a kész elemek közé ha módosítjuk az álálapotot Completed-re

3) Extra szükséges beállítások:
- nem igazán van a dependencyket letölti a gép, a google.service.json be kell hogy legyen illesztve ugye és a config áfjlok frissítvwe
- de ezeket a gyakvezetők biztos tudják

4) Fordítás/futtatáshoz szükséges lépések:
https://github.com/KanyJoz/serviceorder/tree/master/app
Itt hoztam létre neki a GitHub-ot, ez egyelőre privát, de majd amikor szükség lesz rá akkor publikálom egy üzenetet követően