# Rockfestival Uppgifts beskrivining
<p>Blomstermåla Rockfestival går av stapeln torsdag till lördag, med band från hela världen som uppträder från tidiga eftermiddagen till sena natten.
Festivalen har nu hållit på i snart tio år, och har växt så pass att den behöver ett datasystem för att hålla reda på alla som jobbar, spelscheman, band och
bandmedlemmar och liknande information.
 Mycket av arbetet för att hålla reda på alla jobbares person- och arbetsuppgifter, upprätta listor över ansvariga chefer,
  sammanställa programmet och liknande har hittills gjorts för hand, och kanslipersonalen ser med stor tillförsikt fram emot den hjälp de kommer att få av ert system. Här nedan följer några beskrivningar av uppgifter som vi hoppas att datorsystemet ska klara av.</p>


### 1 Spelscheman
Den huvudsakliga informationen som allmänheten vill ha från kansliet är ju spelschemana för de olika banden:
    Vilket band som spelar på vilken scen vid vilken tidpunkt.
    Festivalen har ett flertal scener, från den stora Mallorcascenen nere vid Blomstermålaån, framför vilken hela festivalpubliken kan studsa runt om det behövs
      till den minsta scenen - Dieseltältet, där det knappast går in mer än 150 personer om man ska undvika allt för allvarligt blodvite.
      För det mesta spelar ett band bara en gång under festivalen, men det händer att särskilt populära band får flera scentider.
      På festivalen -98 spelade till exempel Ani DiFranco med band varenda kväll vid midnatt på Forumscenen.
      Att hålla reda på när de olika banden ska gå på och av de olika scenerna är mycket tidsödande om man försöker göra det med kylskåpsmagneter och papperslappar,
       så vi hoppas mycket på datorns hjälp just här!
       Praktiska listor för varje scen med bandnamn, tider, ursprungsland och kanske musikstil vore fantastiskt bra att ha.

###2 Säkerhetsansvar
Vi har ett krav på oss från brandmyndigheterna att vid varje tidpunkt ha en person som är säkerhetsansvarig för varje scen.
 Det löser vi som så att vi er särskilt ansvarskännande och rutinerade festivaljobbare ansvar för de olika scenerna i fyratimmarspass under kvällarna.
  Ibland blir det lite krångel när det ska bytas säkerhetsansvar mitt under en spelning, men det fixar vi,
   vi kan inte hålla på och anpassa ansvarstiderna efter spelningarna.
   Vanligtvis är det ett lugnt arbete,
    men om det skulle inträffa en incident med till exempel pyrotekniken så är det viktigt att det finns någon som vet vem man ringer och hur man löser situationerna
     effektivt,
      och då är det viktigt att inte den personen är säkerhetsansvarig för en annan scen samtidigt.
       Både för vår egen skull och för att brandmyndigheterna kräver det,
        vill vi ha möjligheter att få ut listor på vilka som har säkerhetsansvaret för de olika scenerna olika kvällar.
         Listorna måste innehålla jobbarnas personnummer, eftersom brandchefen måste kunna kontrollera åldern på dem.
###3 Kontaktpersoner
Varje band som spelar på festivalen får sig tilldelad en kontaktperson bland jobbarna.
 Denna person är ansvarig för att hjälpa ”sitt” band till loger och scener, lösa småproblem som kan dyka upp, osv.
  Samtidigt är de festivalledningens kanal till banden om det är någon information som behöver komma ut snabbt.
   En person kan ha ansvar för flera band, men vi försöker se till att det inte blir för många bandmedlemmar att ta hand om.
    Helst skulle kansliet vilja kunna få fram både namnet på kontaktpersonen för ett givet band, en lista över alla bands kontaktpersoner och omvänt
    (för att kolla att ingen jobbare blir överbelastad) en lista över alla jobbare som är kontaktpersoner och hur många bandmedlemmar de är ansvariga för.

###4 Bandinformation
Något som vi vill göra för att ge besökarna nya impulser är att ha ständigt rullande information om de spelande banden i kansliets tält.
 Vi vill kunna på ett lätt sätt ange ett bandnamn och få ut, säg, varifrån bandet kommer, vad de spelar för typ av musik,
 vilka som är med i bandet och kanske något annat intressant om dem så vore det trevligt.
 Gå in och titta på specifik information on enskilda medlemmar, kanske? Allt för att besökarna ska kunna få reda på allt om banden,
  och därmed lockas att gå på oväntade konserter!

Så, det var lite tankar från kansliet om vad vi skulle vilja ha hjälp med. Vi hoppas mycket på er och ert datasystem!

###5 För Godkänt:

Implementera en databas som stödjer festivalens informationsbehov. Bygg en applikation för besökare, där man kan få se spelscheman och bandinformation,
 och en applikation för kansliet, där man kan boka band, ge kontaktpersoner till banden, och boka spelningar till banden.
###6 För Väl Godkänt:

Implementera möjligheten att hantera säkerhetsansvar, både i databasen och applikationen.
 Implementera de mer avancerade listorna för antal bandmedlemmar per kontaktperson och liknande.
  Se till att all inmatning som rör i databasen redan existerande data (att välja band och scen till en spelning, till exempel)
   sker med menyval (så att man inte kan stava fel).